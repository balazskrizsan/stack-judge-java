/*
 * This file is generated by jOOQ.
 */
package com.kbalazsworks.stackjudge.db.tables.records;


import com.kbalazsworks.stackjudge.db.tables.Address;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AddressRecord extends TableRecordImpl<AddressRecord> implements Record9<Long, Long, String, BigDecimal, BigDecimal, BigDecimal, BigDecimal, LocalDateTime, Long> {

    private static final long serialVersionUID = -838444175;

    /**
     * Setter for <code>public.address.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.address.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.address.company_id</code>.
     */
    public void setCompanyId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.address.company_id</code>.
     */
    public Long getCompanyId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>public.address.full_address</code>.
     */
    public void setFullAddress(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.address.full_address</code>.
     */
    public String getFullAddress() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.address.marker_lat</code>.
     */
    public void setMarkerLat(BigDecimal value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.address.marker_lat</code>.
     */
    public BigDecimal getMarkerLat() {
        return (BigDecimal) get(3);
    }

    /**
     * Setter for <code>public.address.marker_lng</code>.
     */
    public void setMarkerLng(BigDecimal value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.address.marker_lng</code>.
     */
    public BigDecimal getMarkerLng() {
        return (BigDecimal) get(4);
    }

    /**
     * Setter for <code>public.address.manual_marker_lat</code>.
     */
    public void setManualMarkerLat(BigDecimal value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.address.manual_marker_lat</code>.
     */
    public BigDecimal getManualMarkerLat() {
        return (BigDecimal) get(5);
    }

    /**
     * Setter for <code>public.address.manual_marker_lng</code>.
     */
    public void setManualMarkerLng(BigDecimal value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.address.manual_marker_lng</code>.
     */
    public BigDecimal getManualMarkerLng() {
        return (BigDecimal) get(6);
    }

    /**
     * Setter for <code>public.address.created_at</code>.
     */
    public void setCreatedAt(LocalDateTime value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.address.created_at</code>.
     */
    public LocalDateTime getCreatedAt() {
        return (LocalDateTime) get(7);
    }

    /**
     * Setter for <code>public.address.created_by</code>.
     */
    public void setCreatedBy(Long value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.address.created_by</code>.
     */
    public Long getCreatedBy() {
        return (Long) get(8);
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row9<Long, Long, String, BigDecimal, BigDecimal, BigDecimal, BigDecimal, LocalDateTime, Long> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    @Override
    public Row9<Long, Long, String, BigDecimal, BigDecimal, BigDecimal, BigDecimal, LocalDateTime, Long> valuesRow() {
        return (Row9) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Address.ADDRESS.ID;
    }

    @Override
    public Field<Long> field2() {
        return Address.ADDRESS.COMPANY_ID;
    }

    @Override
    public Field<String> field3() {
        return Address.ADDRESS.FULL_ADDRESS;
    }

    @Override
    public Field<BigDecimal> field4() {
        return Address.ADDRESS.MARKER_LAT;
    }

    @Override
    public Field<BigDecimal> field5() {
        return Address.ADDRESS.MARKER_LNG;
    }

    @Override
    public Field<BigDecimal> field6() {
        return Address.ADDRESS.MANUAL_MARKER_LAT;
    }

    @Override
    public Field<BigDecimal> field7() {
        return Address.ADDRESS.MANUAL_MARKER_LNG;
    }

    @Override
    public Field<LocalDateTime> field8() {
        return Address.ADDRESS.CREATED_AT;
    }

    @Override
    public Field<Long> field9() {
        return Address.ADDRESS.CREATED_BY;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Long component2() {
        return getCompanyId();
    }

    @Override
    public String component3() {
        return getFullAddress();
    }

    @Override
    public BigDecimal component4() {
        return getMarkerLat();
    }

    @Override
    public BigDecimal component5() {
        return getMarkerLng();
    }

    @Override
    public BigDecimal component6() {
        return getManualMarkerLat();
    }

    @Override
    public BigDecimal component7() {
        return getManualMarkerLng();
    }

    @Override
    public LocalDateTime component8() {
        return getCreatedAt();
    }

    @Override
    public Long component9() {
        return getCreatedBy();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Long value2() {
        return getCompanyId();
    }

    @Override
    public String value3() {
        return getFullAddress();
    }

    @Override
    public BigDecimal value4() {
        return getMarkerLat();
    }

    @Override
    public BigDecimal value5() {
        return getMarkerLng();
    }

    @Override
    public BigDecimal value6() {
        return getManualMarkerLat();
    }

    @Override
    public BigDecimal value7() {
        return getManualMarkerLng();
    }

    @Override
    public LocalDateTime value8() {
        return getCreatedAt();
    }

    @Override
    public Long value9() {
        return getCreatedBy();
    }

    @Override
    public AddressRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public AddressRecord value2(Long value) {
        setCompanyId(value);
        return this;
    }

    @Override
    public AddressRecord value3(String value) {
        setFullAddress(value);
        return this;
    }

    @Override
    public AddressRecord value4(BigDecimal value) {
        setMarkerLat(value);
        return this;
    }

    @Override
    public AddressRecord value5(BigDecimal value) {
        setMarkerLng(value);
        return this;
    }

    @Override
    public AddressRecord value6(BigDecimal value) {
        setManualMarkerLat(value);
        return this;
    }

    @Override
    public AddressRecord value7(BigDecimal value) {
        setManualMarkerLng(value);
        return this;
    }

    @Override
    public AddressRecord value8(LocalDateTime value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public AddressRecord value9(Long value) {
        setCreatedBy(value);
        return this;
    }

    @Override
    public AddressRecord values(Long value1, Long value2, String value3, BigDecimal value4, BigDecimal value5, BigDecimal value6, BigDecimal value7, LocalDateTime value8, Long value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AddressRecord
     */
    public AddressRecord() {
        super(Address.ADDRESS);
    }

    /**
     * Create a detached, initialised AddressRecord
     */
    public AddressRecord(Long id, Long companyId, String fullAddress, BigDecimal markerLat, BigDecimal markerLng, BigDecimal manualMarkerLat, BigDecimal manualMarkerLng, LocalDateTime createdAt, Long createdBy) {
        super(Address.ADDRESS);

        set(0, id);
        set(1, companyId);
        set(2, fullAddress);
        set(3, markerLat);
        set(4, markerLng);
        set(5, manualMarkerLat);
        set(6, manualMarkerLng);
        set(7, createdAt);
        set(8, createdBy);
    }
}
