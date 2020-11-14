package com.kbalazsworks.stackjudge.domain.mocks;

import com.kbalazsworks.stackjudge.domain.entities.Address;

import java.time.LocalDateTime;

public class AddressFakeBuilder
{
    private Long          id              = 111L;
    private Long          companyId       = 222L;
    private String        fullAddress     = "Full address 1, 123, 4";
    private Double        markerLat       = 11.11;
    private Double        markerLng       = 22.22;
    private Double        manualMarkerLat = 33.33;
    private Double        manualMarkerLng = 44.44;
    private LocalDateTime createdAt       = LocalDateTime.of(2020, 11, 22, 11, 22, 33);
    private Long          createdBy       = 333L;

    public Address build()
    {
        return new Address(
            id,
            companyId,
            fullAddress,
            markerLat,
            markerLng,
            manualMarkerLat,
            manualMarkerLng,
            createdAt,
            createdBy
        );
    }

    public AddressFakeBuilder setId(Long id)
    {
        this.id = id;

        return this;
    }

    public AddressFakeBuilder setCompanyId(Long companyId)
    {
        this.companyId = companyId;

        return this;
    }

    public AddressFakeBuilder setFullAddress(String fullAddress)
    {
        this.fullAddress = fullAddress;

        return this;
    }

    public AddressFakeBuilder setMarkerLat(Double markerLat)
    {
        this.markerLat = markerLat;

        return this;
    }

    public AddressFakeBuilder setMarkerLng(Double markerLng)
    {
        this.markerLng = markerLng;

        return this;
    }

    public AddressFakeBuilder setManualMarkerLat(Double manualMarkerLat)
    {
        this.manualMarkerLat = manualMarkerLat;

        return this;
    }

    public AddressFakeBuilder setManualMarkerLng(Double manualMarkerLng)
    {
        this.manualMarkerLng = manualMarkerLng;

        return this;
    }

    public AddressFakeBuilder setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;

        return this;
    }

    public AddressFakeBuilder setCreatedBy(Long createdBy)
    {
        this.createdBy = createdBy;

        return this;
    }
}
