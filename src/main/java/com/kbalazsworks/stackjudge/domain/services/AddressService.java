package com.kbalazsworks.stackjudge.domain.services;

import com.google.common.collect.Lists;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.exceptions.CompanyHttpException;
import com.kbalazsworks.stackjudge.domain.exceptions.ExceptionResponseInfo;
import com.kbalazsworks.stackjudge.domain.repositories.AddressRepository;
import lombok.NonNull;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressService
{
    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    private AddressRepository addressRepository;

    @Autowired
    public void setAddressRepository(AddressRepository addressRepository)
    {
        this.addressRepository = addressRepository;
    }

    public void create(@NonNull Address address)
    {
        try
        {
            addressRepository.create(address);
        }
        catch (DataAccessException e)
        {
            if (e.getCause().toString().contains("fk__address_company_id__company_id__on_delete_cascade"))
            {
                logger.error(
                    ExceptionResponseInfo.CompanyNotFoundMsg
                        .concat(" id%")
                        .concat(address.companyId().toString())
                );

                throw new CompanyHttpException(ExceptionResponseInfo.CompanyNotFoundMsg)
                    .withErrorCode(ExceptionResponseInfo.CompanyNotFoundErrorCode)
                    .withStatusCode(HttpStatus.BAD_REQUEST);
            }

            throw e;
        }
    }

    public Map<Long, List<Address>> search(List<Long> companyIds)
    {
        Map<Long, List<Address>> addressesForResponse = new HashMap<>();
        addressRepository.search(companyIds).forEach(
            address ->
            {
                long companyId = address.companyId();

                List<Address> addresses = addressesForResponse.get(companyId);
                if (null == addresses)
                {
                    addressesForResponse.put(companyId, Lists.newArrayList(address));

                    return;
                }

                addresses.add(address);
            });

        return addressesForResponse;
    }
}
