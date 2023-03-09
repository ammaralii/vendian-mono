package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Addresses} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.AddressesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AddressesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter address;

    private StringFilter city;

    private StringFilter province;

    private StringFilter country;

    private StringFilter postalcode;

    private BooleanFilter isdeleted;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter employeeaddressesAddressId;

    private Boolean distinct;

    public AddressesCriteria() {}

    public AddressesCriteria(AddressesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.province = other.province == null ? null : other.province.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.postalcode = other.postalcode == null ? null : other.postalcode.copy();
        this.isdeleted = other.isdeleted == null ? null : other.isdeleted.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.employeeaddressesAddressId = other.employeeaddressesAddressId == null ? null : other.employeeaddressesAddressId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AddressesCriteria copy() {
        return new AddressesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getCity() {
        return city;
    }

    public StringFilter city() {
        if (city == null) {
            city = new StringFilter();
        }
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getProvince() {
        return province;
    }

    public StringFilter province() {
        if (province == null) {
            province = new StringFilter();
        }
        return province;
    }

    public void setProvince(StringFilter province) {
        this.province = province;
    }

    public StringFilter getCountry() {
        return country;
    }

    public StringFilter country() {
        if (country == null) {
            country = new StringFilter();
        }
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public StringFilter getPostalcode() {
        return postalcode;
    }

    public StringFilter postalcode() {
        if (postalcode == null) {
            postalcode = new StringFilter();
        }
        return postalcode;
    }

    public void setPostalcode(StringFilter postalcode) {
        this.postalcode = postalcode;
    }

    public BooleanFilter getIsdeleted() {
        return isdeleted;
    }

    public BooleanFilter isdeleted() {
        if (isdeleted == null) {
            isdeleted = new BooleanFilter();
        }
        return isdeleted;
    }

    public void setIsdeleted(BooleanFilter isdeleted) {
        this.isdeleted = isdeleted;
    }

    public InstantFilter getCreatedat() {
        return createdat;
    }

    public InstantFilter createdat() {
        if (createdat == null) {
            createdat = new InstantFilter();
        }
        return createdat;
    }

    public void setCreatedat(InstantFilter createdat) {
        this.createdat = createdat;
    }

    public InstantFilter getUpdatedat() {
        return updatedat;
    }

    public InstantFilter updatedat() {
        if (updatedat == null) {
            updatedat = new InstantFilter();
        }
        return updatedat;
    }

    public void setUpdatedat(InstantFilter updatedat) {
        this.updatedat = updatedat;
    }

    public LongFilter getEmployeeaddressesAddressId() {
        return employeeaddressesAddressId;
    }

    public LongFilter employeeaddressesAddressId() {
        if (employeeaddressesAddressId == null) {
            employeeaddressesAddressId = new LongFilter();
        }
        return employeeaddressesAddressId;
    }

    public void setEmployeeaddressesAddressId(LongFilter employeeaddressesAddressId) {
        this.employeeaddressesAddressId = employeeaddressesAddressId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AddressesCriteria that = (AddressesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(address, that.address) &&
            Objects.equals(city, that.city) &&
            Objects.equals(province, that.province) &&
            Objects.equals(country, that.country) &&
            Objects.equals(postalcode, that.postalcode) &&
            Objects.equals(isdeleted, that.isdeleted) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(employeeaddressesAddressId, that.employeeaddressesAddressId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            address,
            city,
            province,
            country,
            postalcode,
            isdeleted,
            createdat,
            updatedat,
            employeeaddressesAddressId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (city != null ? "city=" + city + ", " : "") +
            (province != null ? "province=" + province + ", " : "") +
            (country != null ? "country=" + country + ", " : "") +
            (postalcode != null ? "postalcode=" + postalcode + ", " : "") +
            (isdeleted != null ? "isdeleted=" + isdeleted + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (employeeaddressesAddressId != null ? "employeeaddressesAddressId=" + employeeaddressesAddressId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
