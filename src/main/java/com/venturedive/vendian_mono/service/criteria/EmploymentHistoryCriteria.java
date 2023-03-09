package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.EmploymentHistory} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.EmploymentHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employment-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmploymentHistoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter positiontitle;

    private StringFilter companyname;

    private StringFilter grade;

    private StringFilter jobdescription;

    private StringFilter city;

    private StringFilter country;

    private InstantFilter startdate;

    private InstantFilter enddate;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private StringFilter reasonforleaving;

    private LongFilter employeeId;

    private Boolean distinct;

    public EmploymentHistoryCriteria() {}

    public EmploymentHistoryCriteria(EmploymentHistoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.positiontitle = other.positiontitle == null ? null : other.positiontitle.copy();
        this.companyname = other.companyname == null ? null : other.companyname.copy();
        this.grade = other.grade == null ? null : other.grade.copy();
        this.jobdescription = other.jobdescription == null ? null : other.jobdescription.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.startdate = other.startdate == null ? null : other.startdate.copy();
        this.enddate = other.enddate == null ? null : other.enddate.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.reasonforleaving = other.reasonforleaving == null ? null : other.reasonforleaving.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmploymentHistoryCriteria copy() {
        return new EmploymentHistoryCriteria(this);
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

    public StringFilter getPositiontitle() {
        return positiontitle;
    }

    public StringFilter positiontitle() {
        if (positiontitle == null) {
            positiontitle = new StringFilter();
        }
        return positiontitle;
    }

    public void setPositiontitle(StringFilter positiontitle) {
        this.positiontitle = positiontitle;
    }

    public StringFilter getCompanyname() {
        return companyname;
    }

    public StringFilter companyname() {
        if (companyname == null) {
            companyname = new StringFilter();
        }
        return companyname;
    }

    public void setCompanyname(StringFilter companyname) {
        this.companyname = companyname;
    }

    public StringFilter getGrade() {
        return grade;
    }

    public StringFilter grade() {
        if (grade == null) {
            grade = new StringFilter();
        }
        return grade;
    }

    public void setGrade(StringFilter grade) {
        this.grade = grade;
    }

    public StringFilter getJobdescription() {
        return jobdescription;
    }

    public StringFilter jobdescription() {
        if (jobdescription == null) {
            jobdescription = new StringFilter();
        }
        return jobdescription;
    }

    public void setJobdescription(StringFilter jobdescription) {
        this.jobdescription = jobdescription;
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

    public InstantFilter getStartdate() {
        return startdate;
    }

    public InstantFilter startdate() {
        if (startdate == null) {
            startdate = new InstantFilter();
        }
        return startdate;
    }

    public void setStartdate(InstantFilter startdate) {
        this.startdate = startdate;
    }

    public InstantFilter getEnddate() {
        return enddate;
    }

    public InstantFilter enddate() {
        if (enddate == null) {
            enddate = new InstantFilter();
        }
        return enddate;
    }

    public void setEnddate(InstantFilter enddate) {
        this.enddate = enddate;
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

    public StringFilter getReasonforleaving() {
        return reasonforleaving;
    }

    public StringFilter reasonforleaving() {
        if (reasonforleaving == null) {
            reasonforleaving = new StringFilter();
        }
        return reasonforleaving;
    }

    public void setReasonforleaving(StringFilter reasonforleaving) {
        this.reasonforleaving = reasonforleaving;
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
        final EmploymentHistoryCriteria that = (EmploymentHistoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(positiontitle, that.positiontitle) &&
            Objects.equals(companyname, that.companyname) &&
            Objects.equals(grade, that.grade) &&
            Objects.equals(jobdescription, that.jobdescription) &&
            Objects.equals(city, that.city) &&
            Objects.equals(country, that.country) &&
            Objects.equals(startdate, that.startdate) &&
            Objects.equals(enddate, that.enddate) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(reasonforleaving, that.reasonforleaving) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            positiontitle,
            companyname,
            grade,
            jobdescription,
            city,
            country,
            startdate,
            enddate,
            createdat,
            updatedat,
            reasonforleaving,
            employeeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmploymentHistoryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (positiontitle != null ? "positiontitle=" + positiontitle + ", " : "") +
            (companyname != null ? "companyname=" + companyname + ", " : "") +
            (grade != null ? "grade=" + grade + ", " : "") +
            (jobdescription != null ? "jobdescription=" + jobdescription + ", " : "") +
            (city != null ? "city=" + city + ", " : "") +
            (country != null ? "country=" + country + ", " : "") +
            (startdate != null ? "startdate=" + startdate + ", " : "") +
            (enddate != null ? "enddate=" + enddate + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (reasonforleaving != null ? "reasonforleaving=" + reasonforleaving + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
