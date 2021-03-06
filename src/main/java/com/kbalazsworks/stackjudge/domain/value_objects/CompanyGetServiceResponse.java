package com.kbalazsworks.stackjudge.domain.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.entities.Review;

import java.util.List;
import java.util.Map;

public record CompanyGetServiceResponse(
    @JsonProperty Company company,
    @JsonProperty CompanyStatistic companyStatistic,
    @JsonProperty List<RecursiveGroupTree> companyGroups,
    @JsonProperty List<Address> companyAddresses,
    @JsonProperty Map<Long, List<Review>> companyReviews
)
{
}
