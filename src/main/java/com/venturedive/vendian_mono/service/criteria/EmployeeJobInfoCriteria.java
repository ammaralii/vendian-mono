package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.EmployeeJobInfo} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.EmployeeJobInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-job-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeJobInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter grade;

    private StringFilter subgrade;

    private InstantFilter startdate;

    private InstantFilter enddate;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private IntegerFilter location;

    private LongFilter employeeId;

    private LongFilter designationId;

    private LongFilter reviewmanagerId;

    private LongFilter managerId;

    private LongFilter departmentId;

    private LongFilter employmenttypeId;

    private LongFilter jobdescriptionId;

    private LongFilter divisionId;

    private LongFilter businessunitId;

    private LongFilter competencyId;

    private Boolean distinct;

    public EmployeeJobInfoCriteria() {}

    public EmployeeJobInfoCriteria(EmployeeJobInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.grade = other.grade == null ? null : other.grade.copy();
        this.subgrade = other.subgrade == null ? null : other.subgrade.copy();
        this.startdate = other.startdate == null ? null : other.startdate.copy();
        this.enddate = other.enddate == null ? null : other.enddate.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.designationId = other.designationId == null ? null : other.designationId.copy();
        this.reviewmanagerId = other.reviewmanagerId == null ? null : other.reviewmanagerId.copy();
        this.managerId = other.managerId == null ? null : other.managerId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.employmenttypeId = other.employmenttypeId == null ? null : other.employmenttypeId.copy();
        this.jobdescriptionId = other.jobdescriptionId == null ? null : other.jobdescriptionId.copy();
        this.divisionId = other.divisionId == null ? null : other.divisionId.copy();
        this.businessunitId = other.businessunitId == null ? null : other.businessunitId.copy();
        this.competencyId = other.competencyId == null ? null : other.competencyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeJobInfoCriteria copy() {
        return new EmployeeJobInfoCriteria(this);
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

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
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

    public StringFilter getSubgrade() {
        return subgrade;
    }

    public StringFilter subgrade() {
        if (subgrade == null) {
            subgrade = new StringFilter();
        }
        return subgrade;
    }

    public void setSubgrade(StringFilter subgrade) {
        this.subgrade = subgrade;
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

    public IntegerFilter getLocation() {
        return location;
    }

    public IntegerFilter location() {
        if (location == null) {
            location = new IntegerFilter();
        }
        return location;
    }

    public void setLocation(IntegerFilter location) {
        this.location = location;
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

    public LongFilter getDesignationId() {
        return designationId;
    }

    public LongFilter designationId() {
        if (designationId == null) {
            designationId = new LongFilter();
        }
        return designationId;
    }

    public void setDesignationId(LongFilter designationId) {
        this.designationId = designationId;
    }

    public LongFilter getReviewmanagerId() {
        return reviewmanagerId;
    }

    public LongFilter reviewmanagerId() {
        if (reviewmanagerId == null) {
            reviewmanagerId = new LongFilter();
        }
        return reviewmanagerId;
    }

    public void setReviewmanagerId(LongFilter reviewmanagerId) {
        this.reviewmanagerId = reviewmanagerId;
    }

    public LongFilter getManagerId() {
        return managerId;
    }

    public LongFilter managerId() {
        if (managerId == null) {
            managerId = new LongFilter();
        }
        return managerId;
    }

    public void setManagerId(LongFilter managerId) {
        this.managerId = managerId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public LongFilter departmentId() {
        if (departmentId == null) {
            departmentId = new LongFilter();
        }
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }

    public LongFilter getEmploymenttypeId() {
        return employmenttypeId;
    }

    public LongFilter employmenttypeId() {
        if (employmenttypeId == null) {
            employmenttypeId = new LongFilter();
        }
        return employmenttypeId;
    }

    public void setEmploymenttypeId(LongFilter employmenttypeId) {
        this.employmenttypeId = employmenttypeId;
    }

    public LongFilter getJobdescriptionId() {
        return jobdescriptionId;
    }

    public LongFilter jobdescriptionId() {
        if (jobdescriptionId == null) {
            jobdescriptionId = new LongFilter();
        }
        return jobdescriptionId;
    }

    public void setJobdescriptionId(LongFilter jobdescriptionId) {
        this.jobdescriptionId = jobdescriptionId;
    }

    public LongFilter getDivisionId() {
        return divisionId;
    }

    public LongFilter divisionId() {
        if (divisionId == null) {
            divisionId = new LongFilter();
        }
        return divisionId;
    }

    public void setDivisionId(LongFilter divisionId) {
        this.divisionId = divisionId;
    }

    public LongFilter getBusinessunitId() {
        return businessunitId;
    }

    public LongFilter businessunitId() {
        if (businessunitId == null) {
            businessunitId = new LongFilter();
        }
        return businessunitId;
    }

    public void setBusinessunitId(LongFilter businessunitId) {
        this.businessunitId = businessunitId;
    }

    public LongFilter getCompetencyId() {
        return competencyId;
    }

    public LongFilter competencyId() {
        if (competencyId == null) {
            competencyId = new LongFilter();
        }
        return competencyId;
    }

    public void setCompetencyId(LongFilter competencyId) {
        this.competencyId = competencyId;
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
        final EmployeeJobInfoCriteria that = (EmployeeJobInfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(grade, that.grade) &&
            Objects.equals(subgrade, that.subgrade) &&
            Objects.equals(startdate, that.startdate) &&
            Objects.equals(enddate, that.enddate) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(location, that.location) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(designationId, that.designationId) &&
            Objects.equals(reviewmanagerId, that.reviewmanagerId) &&
            Objects.equals(managerId, that.managerId) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(employmenttypeId, that.employmenttypeId) &&
            Objects.equals(jobdescriptionId, that.jobdescriptionId) &&
            Objects.equals(divisionId, that.divisionId) &&
            Objects.equals(businessunitId, that.businessunitId) &&
            Objects.equals(competencyId, that.competencyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            grade,
            subgrade,
            startdate,
            enddate,
            createdat,
            updatedat,
            location,
            employeeId,
            designationId,
            reviewmanagerId,
            managerId,
            departmentId,
            employmenttypeId,
            jobdescriptionId,
            divisionId,
            businessunitId,
            competencyId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeJobInfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (grade != null ? "grade=" + grade + ", " : "") +
            (subgrade != null ? "subgrade=" + subgrade + ", " : "") +
            (startdate != null ? "startdate=" + startdate + ", " : "") +
            (enddate != null ? "enddate=" + enddate + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (location != null ? "location=" + location + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (designationId != null ? "designationId=" + designationId + ", " : "") +
            (reviewmanagerId != null ? "reviewmanagerId=" + reviewmanagerId + ", " : "") +
            (managerId != null ? "managerId=" + managerId + ", " : "") +
            (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            (employmenttypeId != null ? "employmenttypeId=" + employmenttypeId + ", " : "") +
            (jobdescriptionId != null ? "jobdescriptionId=" + jobdescriptionId + ", " : "") +
            (divisionId != null ? "divisionId=" + divisionId + ", " : "") +
            (businessunitId != null ? "businessunitId=" + businessunitId + ", " : "") +
            (competencyId != null ? "competencyId=" + competencyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
