package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.EmployeeEducation} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.EmployeeEducationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-educations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeEducationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter institute;

    private StringFilter major;

    private StringFilter degree;

    private StringFilter value;

    private StringFilter city;

    private StringFilter province;

    private StringFilter country;

    private InstantFilter datefrom;

    private InstantFilter dateto;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter qualificationtypeId;

    private LongFilter employeeId;

    private Boolean distinct;

    public EmployeeEducationCriteria() {}

    public EmployeeEducationCriteria(EmployeeEducationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.institute = other.institute == null ? null : other.institute.copy();
        this.major = other.major == null ? null : other.major.copy();
        this.degree = other.degree == null ? null : other.degree.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.province = other.province == null ? null : other.province.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.datefrom = other.datefrom == null ? null : other.datefrom.copy();
        this.dateto = other.dateto == null ? null : other.dateto.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.qualificationtypeId = other.qualificationtypeId == null ? null : other.qualificationtypeId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeEducationCriteria copy() {
        return new EmployeeEducationCriteria(this);
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

    public StringFilter getInstitute() {
        return institute;
    }

    public StringFilter institute() {
        if (institute == null) {
            institute = new StringFilter();
        }
        return institute;
    }

    public void setInstitute(StringFilter institute) {
        this.institute = institute;
    }

    public StringFilter getMajor() {
        return major;
    }

    public StringFilter major() {
        if (major == null) {
            major = new StringFilter();
        }
        return major;
    }

    public void setMajor(StringFilter major) {
        this.major = major;
    }

    public StringFilter getDegree() {
        return degree;
    }

    public StringFilter degree() {
        if (degree == null) {
            degree = new StringFilter();
        }
        return degree;
    }

    public void setDegree(StringFilter degree) {
        this.degree = degree;
    }

    public StringFilter getValue() {
        return value;
    }

    public StringFilter value() {
        if (value == null) {
            value = new StringFilter();
        }
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
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

    public InstantFilter getDatefrom() {
        return datefrom;
    }

    public InstantFilter datefrom() {
        if (datefrom == null) {
            datefrom = new InstantFilter();
        }
        return datefrom;
    }

    public void setDatefrom(InstantFilter datefrom) {
        this.datefrom = datefrom;
    }

    public InstantFilter getDateto() {
        return dateto;
    }

    public InstantFilter dateto() {
        if (dateto == null) {
            dateto = new InstantFilter();
        }
        return dateto;
    }

    public void setDateto(InstantFilter dateto) {
        this.dateto = dateto;
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

    public LongFilter getQualificationtypeId() {
        return qualificationtypeId;
    }

    public LongFilter qualificationtypeId() {
        if (qualificationtypeId == null) {
            qualificationtypeId = new LongFilter();
        }
        return qualificationtypeId;
    }

    public void setQualificationtypeId(LongFilter qualificationtypeId) {
        this.qualificationtypeId = qualificationtypeId;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
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
        final EmployeeEducationCriteria that = (EmployeeEducationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(institute, that.institute) &&
            Objects.equals(major, that.major) &&
            Objects.equals(degree, that.degree) &&
            Objects.equals(value, that.value) &&
            Objects.equals(city, that.city) &&
            Objects.equals(province, that.province) &&
            Objects.equals(country, that.country) &&
            Objects.equals(datefrom, that.datefrom) &&
            Objects.equals(dateto, that.dateto) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(qualificationtypeId, that.qualificationtypeId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            institute,
            major,
            degree,
            value,
            city,
            province,
            country,
            datefrom,
            dateto,
            createdat,
            updatedat,
            qualificationtypeId,
            employeeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeEducationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (institute != null ? "institute=" + institute + ", " : "") +
            (major != null ? "major=" + major + ", " : "") +
            (degree != null ? "degree=" + degree + ", " : "") +
            (value != null ? "value=" + value + ", " : "") +
            (city != null ? "city=" + city + ", " : "") +
            (province != null ? "province=" + province + ", " : "") +
            (country != null ? "country=" + country + ", " : "") +
            (datefrom != null ? "datefrom=" + datefrom + ", " : "") +
            (dateto != null ? "dateto=" + dateto + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (qualificationtypeId != null ? "qualificationtypeId=" + qualificationtypeId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
