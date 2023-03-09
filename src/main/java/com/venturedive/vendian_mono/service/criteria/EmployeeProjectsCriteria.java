package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.EmployeeProjects} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.EmployeeProjectsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-projects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeProjectsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter status;

    private StringFilter type;

    private InstantFilter startdate;

    private InstantFilter enddate;

    private BooleanFilter allocation;

    private StringFilter billed;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private IntegerFilter roleid;

    private StringFilter notes;

    private InstantFilter extendedenddate;

    private StringFilter probability;

    private LongFilter employeeId;

    private LongFilter projectId;

    private LongFilter assignorId;

    private LongFilter employeeprojectrolesEmployeeprojectId;

    private LongFilter zemployeeprojectsEmployeeprojectId;

    private Boolean distinct;

    public EmployeeProjectsCriteria() {}

    public EmployeeProjectsCriteria(EmployeeProjectsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.startdate = other.startdate == null ? null : other.startdate.copy();
        this.enddate = other.enddate == null ? null : other.enddate.copy();
        this.allocation = other.allocation == null ? null : other.allocation.copy();
        this.billed = other.billed == null ? null : other.billed.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.roleid = other.roleid == null ? null : other.roleid.copy();
        this.notes = other.notes == null ? null : other.notes.copy();
        this.extendedenddate = other.extendedenddate == null ? null : other.extendedenddate.copy();
        this.probability = other.probability == null ? null : other.probability.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
        this.assignorId = other.assignorId == null ? null : other.assignorId.copy();
        this.employeeprojectrolesEmployeeprojectId =
            other.employeeprojectrolesEmployeeprojectId == null ? null : other.employeeprojectrolesEmployeeprojectId.copy();
        this.zemployeeprojectsEmployeeprojectId =
            other.zemployeeprojectsEmployeeprojectId == null ? null : other.zemployeeprojectsEmployeeprojectId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeProjectsCriteria copy() {
        return new EmployeeProjectsCriteria(this);
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

    public BooleanFilter getStatus() {
        return status;
    }

    public BooleanFilter status() {
        if (status == null) {
            status = new BooleanFilter();
        }
        return status;
    }

    public void setStatus(BooleanFilter status) {
        this.status = status;
    }

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
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

    public BooleanFilter getAllocation() {
        return allocation;
    }

    public BooleanFilter allocation() {
        if (allocation == null) {
            allocation = new BooleanFilter();
        }
        return allocation;
    }

    public void setAllocation(BooleanFilter allocation) {
        this.allocation = allocation;
    }

    public StringFilter getBilled() {
        return billed;
    }

    public StringFilter billed() {
        if (billed == null) {
            billed = new StringFilter();
        }
        return billed;
    }

    public void setBilled(StringFilter billed) {
        this.billed = billed;
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

    public IntegerFilter getRoleid() {
        return roleid;
    }

    public IntegerFilter roleid() {
        if (roleid == null) {
            roleid = new IntegerFilter();
        }
        return roleid;
    }

    public void setRoleid(IntegerFilter roleid) {
        this.roleid = roleid;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public StringFilter notes() {
        if (notes == null) {
            notes = new StringFilter();
        }
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
    }

    public InstantFilter getExtendedenddate() {
        return extendedenddate;
    }

    public InstantFilter extendedenddate() {
        if (extendedenddate == null) {
            extendedenddate = new InstantFilter();
        }
        return extendedenddate;
    }

    public void setExtendedenddate(InstantFilter extendedenddate) {
        this.extendedenddate = extendedenddate;
    }

    public StringFilter getProbability() {
        return probability;
    }

    public StringFilter probability() {
        if (probability == null) {
            probability = new StringFilter();
        }
        return probability;
    }

    public void setProbability(StringFilter probability) {
        this.probability = probability;
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

    public LongFilter getProjectId() {
        return projectId;
    }

    public LongFilter projectId() {
        if (projectId == null) {
            projectId = new LongFilter();
        }
        return projectId;
    }

    public void setProjectId(LongFilter projectId) {
        this.projectId = projectId;
    }

    public LongFilter getAssignorId() {
        return assignorId;
    }

    public LongFilter assignorId() {
        if (assignorId == null) {
            assignorId = new LongFilter();
        }
        return assignorId;
    }

    public void setAssignorId(LongFilter assignorId) {
        this.assignorId = assignorId;
    }

    public LongFilter getEmployeeprojectrolesEmployeeprojectId() {
        return employeeprojectrolesEmployeeprojectId;
    }

    public LongFilter employeeprojectrolesEmployeeprojectId() {
        if (employeeprojectrolesEmployeeprojectId == null) {
            employeeprojectrolesEmployeeprojectId = new LongFilter();
        }
        return employeeprojectrolesEmployeeprojectId;
    }

    public void setEmployeeprojectrolesEmployeeprojectId(LongFilter employeeprojectrolesEmployeeprojectId) {
        this.employeeprojectrolesEmployeeprojectId = employeeprojectrolesEmployeeprojectId;
    }

    public LongFilter getZemployeeprojectsEmployeeprojectId() {
        return zemployeeprojectsEmployeeprojectId;
    }

    public LongFilter zemployeeprojectsEmployeeprojectId() {
        if (zemployeeprojectsEmployeeprojectId == null) {
            zemployeeprojectsEmployeeprojectId = new LongFilter();
        }
        return zemployeeprojectsEmployeeprojectId;
    }

    public void setZemployeeprojectsEmployeeprojectId(LongFilter zemployeeprojectsEmployeeprojectId) {
        this.zemployeeprojectsEmployeeprojectId = zemployeeprojectsEmployeeprojectId;
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
        final EmployeeProjectsCriteria that = (EmployeeProjectsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(type, that.type) &&
            Objects.equals(startdate, that.startdate) &&
            Objects.equals(enddate, that.enddate) &&
            Objects.equals(allocation, that.allocation) &&
            Objects.equals(billed, that.billed) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(roleid, that.roleid) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(extendedenddate, that.extendedenddate) &&
            Objects.equals(probability, that.probability) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(projectId, that.projectId) &&
            Objects.equals(assignorId, that.assignorId) &&
            Objects.equals(employeeprojectrolesEmployeeprojectId, that.employeeprojectrolesEmployeeprojectId) &&
            Objects.equals(zemployeeprojectsEmployeeprojectId, that.zemployeeprojectsEmployeeprojectId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            status,
            type,
            startdate,
            enddate,
            allocation,
            billed,
            createdat,
            updatedat,
            roleid,
            notes,
            extendedenddate,
            probability,
            employeeId,
            projectId,
            assignorId,
            employeeprojectrolesEmployeeprojectId,
            zemployeeprojectsEmployeeprojectId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeProjectsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (startdate != null ? "startdate=" + startdate + ", " : "") +
            (enddate != null ? "enddate=" + enddate + ", " : "") +
            (allocation != null ? "allocation=" + allocation + ", " : "") +
            (billed != null ? "billed=" + billed + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (roleid != null ? "roleid=" + roleid + ", " : "") +
            (notes != null ? "notes=" + notes + ", " : "") +
            (extendedenddate != null ? "extendedenddate=" + extendedenddate + ", " : "") +
            (probability != null ? "probability=" + probability + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (projectId != null ? "projectId=" + projectId + ", " : "") +
            (assignorId != null ? "assignorId=" + assignorId + ", " : "") +
            (employeeprojectrolesEmployeeprojectId != null ? "employeeprojectrolesEmployeeprojectId=" + employeeprojectrolesEmployeeprojectId + ", " : "") +
            (zemployeeprojectsEmployeeprojectId != null ? "zemployeeprojectsEmployeeprojectId=" + zemployeeprojectsEmployeeprojectId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
