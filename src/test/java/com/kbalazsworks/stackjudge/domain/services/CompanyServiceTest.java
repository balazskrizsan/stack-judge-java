package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.db.tables.records.AddressRecord;
import com.kbalazsworks.stackjudge.db.tables.records.CompanyRecord;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.fakes.AddressFakeBuilder;
import com.kbalazsworks.stackjudge.domain.fakes.CompanyFakeBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class CompanyServiceTest extends AbstractIntegrationTest
{
    @Autowired
    private CompanyService companyService;

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql", "classpath:test/sqls/preset_add_one_company.sql"}
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void delete_deletingExistingRecord_perfect()
    {
        // Arrange - In preset
        long testedCompanyId = 164985367;

        // Act
        companyService.delete(testedCompanyId);

        // Assert
        Company company = getQueryBuilder()
            .selectFrom(companyTable)
            .where(companyTable.ID.eq(testedCompanyId))
            .fetchOneInto(Company.class);

        Assert.assertNull(company);
    }

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql", "classpath:test/sqls/preset_add_one_company.sql"}
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void get_findTheInsertedCompany_perfect()
    {
        // Arrange - In preset
        long testedCompanyId = 164985367;
        Company expectedCompany = new CompanyFakeBuilder().build();

        // Act
        companyService.get(testedCompanyId);

        // Assert
        Company company = getQueryBuilder()
            .selectFrom(companyTable)
            .where(companyTable.ID.eq(testedCompanyId))
            .fetchOneInto(Company.class);

        Assert.assertEquals(company, expectedCompany);
    }

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql", "classpath:test/sqls/preset_add_3_companies.sql"}
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void search_findAllRecords_perfect()
    {
        // Arrange - In preset
        List<Company> expectedList = new ArrayList<>(){};
        expectedList.add(
            new CompanyFakeBuilder()
                .setId(164985367L)
                .setName("a company 1")
                .setCompanySizeId((short) 1)
                .setItSizeId((short) 2)
                .setCreatedAt(LocalDateTime.of(2021, 1, 2, 1, 2, 3))
                .setCreatedBy(111L)
                .build()
        );
        expectedList.add(
            new CompanyFakeBuilder()
                .setId(245678965L)
                .setName("a company 2")
                .setCompanySizeId((short) 3)
                .setItSizeId((short) 4)
                .setCreatedAt(LocalDateTime.of(2022, 3, 4, 4, 5, 6))
                .setCreatedBy(222L)
                .build()
        );
        expectedList.add(
            new CompanyFakeBuilder()
                .setId(854621354L)
                .setName("a company 3")
                .setCompanySizeId((short) 5)
                .setItSizeId((short) 6)
                .setCreatedAt(LocalDateTime.of(2023, 5, 6, 7, 8, 9))
                .setCreatedBy(333L)
                .build()
        );

        // Act
        List<Company> companyList = companyService.search(1, 1);

        // Assert
        Assert.assertEquals(companyList, expectedList);
    }

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void create_insertOneCompanyWithOneAddress_perfect()
    {
        // Arrange
        Company testedCompany   = new CompanyFakeBuilder().build();
        Address testedAddress   = new AddressFakeBuilder().build();
        long expectedCompanyId  = 23455487L;
        Company expectedCompany = new CompanyFakeBuilder().setId(expectedCompanyId).build();
        long expectedAddressId  = 24562647L;
        Address expectedAddress  = new AddressFakeBuilder()
            .setId(expectedAddressId)
            .setCompanyId(expectedCompanyId)
            .build();

        // Act
        companyService.create(testedCompany, testedAddress);

        // Assert
        CompanyRecord actualCompany = getQueryBuilder()
            .selectFrom(companyTable)
            .fetchOne();
        actualCompany.setId(expectedCompanyId);

        AddressRecord actualAddress = getQueryBuilder()
            .selectFrom(addressTable)
            .fetchOne();
        actualAddress.setId(expectedAddressId);
        actualAddress.setCompanyId(expectedCompanyId);

        Assert.assertEquals(actualCompany.into(Company.class), expectedCompany);
        Assert.assertEquals(actualAddress.into(Address.class), expectedAddress);
    }
}