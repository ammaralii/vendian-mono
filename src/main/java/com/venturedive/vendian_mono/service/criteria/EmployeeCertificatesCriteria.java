package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.EmployeeCertificates} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.EmployeeCertificatesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-certificates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeCertificatesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter certificateno;

    private StringFilter issuingbody;

    private InstantFilter date;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private InstantFilter validtill;

    private StringFilter certificatecompetency;

    private InstantFilter deletedat;

    private LongFilter employeeId;

    private Boolean distinct;

    public EmployeeCertificatesCriteria() {}

    public EmployeeCertificatesCriteria(EmployeeCertificatesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.certificateno = other.certificateno == null ? null : other.certificateno.copy();
        this.issuingbody = other.issuingbody == null ? null : other.issuingbody.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.validtill = other.validtill == null ? null : other.validtill.copy();
        this.certificatecompetency = other.certificatecompetency == null ? null : other.certificatecompetency.copy();
        this.deletedat = other.deletedat == null ? null : other.deletedat.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeCertificatesCriteria copy() {
        return new EmployeeCertificatesCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getCertificateno() {
        return certificateno;
    }

    public StringFilter certificateno() {
        if (certificateno == null) {
            certificateno = new StringFilter();
        }
        return certificateno;
    }

    public void setCertificateno(StringFilter certificateno) {
        this.certificateno = certificateno;
    }

    public StringFilter getIssuingbody() {
        return issuingbody;
    }

    public StringFilter issuingbody() {
        if (issuingbody == null) {
            issuingbody = new StringFilter();
        }
        return issuingbody;
    }

    public void setIssuingbody(StringFilter issuingbody) {
        this.issuingbody = issuingbody;
    }

    public InstantFilter getDate() {
        return date;
    }

    public InstantFilter date() {
        if (date == null) {
            date = new InstantFilter();
        }
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
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

    public InstantFilter getValidtill() {
        return validtill;
    }

    public InstantFilter validtill() {
        if (validtill == null) {
            validtill = new InstantFilter();
        }
        return validtill;
    }

    public void setValidtill(InstantFilter validtill) {
        this.validtill = validtill;
    }

    public StringFilter getCertificatecompetency() {
        return certificatecompetency;
    }

    public StringFilter certificatecompetency() {
        if (certificatecompetency == null) {
            certificatecompetency = new StringFilter();
        }
        return certificatecompetency;
    }

    public void setCertificatecompetency(StringFilter certificatecompetency) {
        this.certificatecompetency = certificatecompetency;
    }

    public InstantFilter getDeletedat() {
        return deletedat;
    }

    public InstantFilter deletedat() {
        if (deletedat == null) {
            deletedat = new InstantFilter();
        }
        return deletedat;
    }

    public void setDeletedat(InstantFilter deletedat) {
        this.deletedat = deletedat;
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
        final EmployeeCertificatesCriteria that = (EmployeeCertificatesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(certificateno, that.certificateno) &&
            Objects.equals(issuingbody, that.issuingbody) &&
            Objects.equals(date, that.date) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(validtill, that.validtill) &&
            Objects.equals(certificatecompetency, that.certificatecompetency) &&
            Objects.equals(deletedat, that.deletedat) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            certificateno,
            issuingbody,
            date,
            createdat,
            updatedat,
            validtill,
            certificatecompetency,
            deletedat,
            employeeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCertificatesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (certificateno != null ? "certificateno=" + certificateno + ", " : "") +
            (issuingbody != null ? "issuingbody=" + issuingbody + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (validtill != null ? "validtill=" + validtill + ", " : "") +
            (certificatecompetency != null ? "certificatecompetency=" + certificatecompetency + ", " : "") +
            (deletedat != null ? "deletedat=" + deletedat + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
