package com.kbalazsworks.stackjudge.domain.services;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.kbalazsworks.stackjudge.api.enums.CompanyRequestRelationsEnum;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.entities.Review;
import com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.enums.paginator.NavigationEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.CompanyHttpException;
import com.kbalazsworks.stackjudge.domain.exceptions.ExceptionResponseInfo;
import com.kbalazsworks.stackjudge.domain.exceptions.RepositoryNotFoundException;
import com.kbalazsworks.stackjudge.domain.repositories.CompanyRepository;
import com.kbalazsworks.stackjudge.domain.services.company_services.SearchService;
import com.kbalazsworks.stackjudge.domain.value_objects.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompanyService
{
    private CompanyRepository companyRepository;
    private AddressService    addressService;
    private PaginatorService  paginatorService;
    private JooqService       jooqService;
    private CdnService        cdnService;
    private SearchService     searchService;
    private ReviewService     reviewService;

    @Autowired
    public void setJooqService(JooqService jooqService)
    {
        this.jooqService = jooqService;
    }

    @Autowired
    public void setCompanyRepository(CompanyRepository companyRepository)
    {
        this.companyRepository = companyRepository;
    }

    @Autowired
    public void setAddressService(AddressService addressService)
    {
        this.addressService = addressService;
    }

    @Autowired
    public void setPaginatorService(PaginatorService paginatorService)
    {
        this.paginatorService = paginatorService;
    }

    @Autowired
    public void setCdnService(CdnService cdnService)
    {
        this.cdnService = cdnService;
    }

    @Autowired
    public void setSearchService(SearchService searchService)
    {
        this.searchService = searchService;
    }

    @Autowired
    public void setReviewService(ReviewService reviewServiceMock)
    {
        this.reviewService = reviewServiceMock;
    }

    public void delete(long companyId)
    {
        companyRepository.delete(companyId);
    }

    public CompanyGetServiceResponse get(long companyId, List<Short> requestRelationIds)
    throws RepositoryNotFoundException
    {
        CompanySearchServiceResponse searchResponse = search(
            companyId,
            1,
            requestRelationIds,
            NavigationEnum.EXACTLY_ONE_RECORD
        );

        return new CompanyGetServiceResponse(
            searchResponse.companies().get(0),
            searchResponse.companyStatistics().get(companyId),
            searchResponse.companyGroups().get(companyId),
            searchResponse.companyAddresses().get(companyId),
            searchResponse.companyReviews().get(companyId)
        );
    }

    public List<Company> search(long seekId, int limit, NavigationEnum navigation)
    {
        if (navigation == null)
        {
            return companyRepository.search(seekId, limit);
        }

        return switch (navigation)
            {
                case FIRST -> companyRepository.search(0, limit);
                case LAST_MINUS_1, LAST, SECOND -> companyRepository.search(navigation, limit);
                case CURRENT_PLUS_1, CURRENT_PLUS_2, CURRENT_MINUS_1, CURRENT_MINUS_2 -> companyRepository.search(
                    seekId,
                    navigation,
                    limit
                );
                case EXACTLY_ONE_RECORD -> List.of(companyRepository.get(seekId));
                default -> companyRepository.search(seekId, limit);
            };
    }

    public CompanySearchServiceResponse search(
        long seekId,
        int limit,
        List<Short> requestRelationIds,
        NavigationEnum navigation
    )
    {
        List<Company> companies = search(seekId, limit, navigation);

        Map<Long, CompanyStatistic>         companyStatistics = new HashMap<>();
        Map<Long, List<RecursiveGroupTree>> companyGroups     = new HashMap<>();
        List<PaginatorItem>                 paginator         = new ArrayList<>();
        Long                                newSeekId         = null;
        Map<Long, List<Address>>            companyAddresses  = new HashMap<>();
        Map<Long, Map<Long, List<Review>>>  companyReviews    = new HashMap<>();

        if (requestRelationIds != null)
        {
            List<Long> companyIds = companies.stream().map(Company::id).collect(Collectors.toList());

            if (requestRelationIds.contains(CompanyRequestRelationsEnum.STATISTIC.getValue()))
            {
                companyStatistics = searchService.getStatistic(companyIds);
            }

            if (requestRelationIds.contains(CompanyRequestRelationsEnum.GROUP.getValue()))
            {
                companyGroups = searchService.getCompanyGroups(companyIds);
            }

            if (requestRelationIds.contains(CompanyRequestRelationsEnum.PAGINATOR.getValue()))
            {
                newSeekId = companies.get(0).id();
                paginator = paginatorService.generate(countRecordsBeforeId(newSeekId), countRecords(), limit);
            }

            if (requestRelationIds.contains(CompanyRequestRelationsEnum.ADDRESS.getValue()))
            {
                companyAddresses = addressService.search(companyIds);
            }

            if (requestRelationIds.contains(CompanyRequestRelationsEnum.REVIEW.getValue()))
            {
                companyReviews = reviewService.search(companyIds);
            }

        }

        return new CompanySearchServiceResponse(
            companies,
            companyGroups,
            paginator,
            newSeekId,
            companyStatistics,
            companyAddresses,
            companyReviews
        );
    }

    public long countRecords()
    {
        return companyRepository.countRecords();
    }

    public long countRecordsBeforeId(long seekId)
    {
        return companyRepository.countRecordsBeforeId(seekId);
    }

    public void create(@NonNull Company company, @NonNull Address address, MultipartFile companyLogo)
    {
        boolean success = jooqService.getDbContext().transactionResult(
            (Configuration config) ->
            {
                Long newId = companyRepository.create(company);
                addressService.create(
                    new Address(
                        null,
                        newId,
                        address.fullAddress(),
                        address.markerLat(),
                        address.markerLng(),
                        address.manualMarkerLat(),
                        address.manualMarkerLng(),
                        address.createdAt(),
                        address.createdBy()
                    )
                );

                if (companyLogo != null && !companyLogo.isEmpty())
                {
                    try
                    {
                        CdnServicePutResponse cdnServicePutResponse = cdnService.put(
                            CdnNamespaceEnum.COMPANY_LOGOS,
                            newId.toString(),
                            "jpg",
                            companyLogo
                        );
                        updateLogoPath(newId, cdnServicePutResponse.path());
                    }
                    catch (AmazonS3Exception e) //@todo: test
                    {
                        log.error("Amazon S3 upload failed.", e);
                    }
                }

                return true;
            }
        );

        if (!success) //@todo: test
        {
            throw new CompanyHttpException(ExceptionResponseInfo.CompanyCreationFailedMsg)
                .withErrorCode(ExceptionResponseInfo.CompanyCreationFailedErrorCode)
                .withStatusCode(HttpStatus.BAD_REQUEST);
        }
    }

    private void updateLogoPath(long companyId, String logoPath)
    {
        companyRepository.updateLogoPath(companyId, logoPath);
    }
}
