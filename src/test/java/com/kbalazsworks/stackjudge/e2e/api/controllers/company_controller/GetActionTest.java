package com.kbalazsworks.stackjudge.e2e.api.controllers.company_controller;

import com.kbalazsworks.stackjudge.AbstractE2eTest;
import com.kbalazsworks.stackjudge.fake_builders.AddressFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.GroupFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.ReviewFakeBuilder;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetActionTest extends AbstractE2eTest
{
    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/preset_add_1_company.sql",
                    "classpath:test/sqls/preset_add_1_address.sql",
                    "classpath:test/sqls/preset_add_1_group.sql",
                    "classpath:test/sqls/preset_add_1_review.sql",
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void callMethodWithCorrectDbData_allReturnedFieldHasValues() throws Exception
    {
        // Arrange
        String testedUri       = "/company/{id}";
        long   testedCompanyId = CompanyFakeBuilder.defaultId1;
        MultiValueMap<String, String> testedParams = new LinkedMultiValueMap<>()
        {{
            add("requestRelationIds", "1");
            add("requestRelationIds", "2");
            add("requestRelationIds", "3");
            add("requestRelationIds", "4");
            add("requestRelationIds", "5");
        }};
        ResultMatcher expectedStatusCode            = status().isOk();
        long          expectedCompanyId             = CompanyFakeBuilder.defaultId1;
        long          expectedCompanyStatisticId    = CompanyFakeBuilder.defaultId1;
        long          expectedRecursiveGroupId      = GroupFakeBuilder.defaultId1;
        long          expectedCompanyAddressesId    = AddressFakeBuilder.defaultId1;
        long          expectedCompanyReviewsGroupId = GroupFakeBuilder.defaultId1;
        long          expectedCompanyReviewsId      = ReviewFakeBuilder.defaultId1;

        // Act
        ResultActions result = getMockMvc().perform(
            MockMvcRequestBuilders
                .get(testedUri, testedCompanyId)
                .params(testedParams)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // Assert
        result
            .andExpect(expectedStatusCode)
            .andExpect(jsonPath("$.data.company.id").value(expectedCompanyId))
            .andExpect(jsonPath("$.data.companyStatistic.companyId").value(expectedCompanyStatisticId))
            .andExpect(jsonPath("$.data.companyGroups[0].recursiveGroup.id").value(expectedRecursiveGroupId))
            .andExpect(jsonPath("$.data.companyAddresses[0].id").value(expectedCompanyAddressesId))
            .andExpect(
                jsonPath("$.data.companyReviews." + expectedCompanyReviewsGroupId + "[0].id")
                    .value(expectedCompanyReviewsId)
            );
    }

    @Test
    public void callNotExistingCompany_returnStdApiError() throws Exception
    {
        // Arrange
        String        testedUri                  = "/company/{id}";
        long          testedNotExistingCompanyId = 123456;
        String        expectedData               = "Company not found.";
        boolean       expectedSuccess            = false;
        int           expectedErrorCode          = 1001;
        ResultMatcher expectedStatusCode         = status().isNotFound();

        // Act
        ResultActions result = getMockMvc().perform(
            MockMvcRequestBuilders
                .get(testedUri, testedNotExistingCompanyId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // Assert
        result
            .andExpect(expectedStatusCode)
            .andExpect(jsonPath("$.data").value(expectedData))
            .andExpect(jsonPath("$.success").value(expectedSuccess))
            .andExpect(jsonPath("$.errorCode").value(expectedErrorCode));
    }
}
