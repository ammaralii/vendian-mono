package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.ProjectCycles} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.ProjectCyclesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /project-cycles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectCyclesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter isactive;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private InstantFilter allowedafterduedateat;

    private InstantFilter duedate;

    private StringFilter auditlogs;

    private InstantFilter deletedat;

    private LongFilter projectId;

    private LongFilter allowedafterduedatebyId;

    private LongFilter architectId;

    private LongFilter projectmanagerId;

    private LongFilter ratingId;

    private LongFilter employeeprojectratingsProjectcycleId;

    private LongFilter performancecycleId;

    private Boolean distinct;

    public ProjectCyclesCriteria() {}

    public ProjectCyclesCriteria(ProjectCyclesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.isactive = other.isactive == null ? null : other.isactive.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.allowedafterduedateat = other.allowedafterduedateat == null ? null : other.allowedafterduedateat.copy();
        this.duedate = other.duedate == null ? null : other.duedate.copy();
        this.auditlogs = other.auditlogs == null ? null : other.auditlogs.copy();
        this.deletedat = other.deletedat == null ? null : other.deletedat.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
        this.allowedafterduedatebyId = other.allowedafterduedatebyId == null ? null : other.allowedafterduedatebyId.copy();
        this.architectId = other.architectId == null ? null : other.architectId.copy();
        this.projectmanagerId = other.projectmanagerId == null ? null : other.projectmanagerId.copy();
        this.ratingId = other.ratingId == null ? null : other.ratingId.copy();
        this.employeeprojectratingsProjectcycleId =
            other.employeeprojectratingsProjectcycleId == null ? null : other.employeeprojectratingsProjectcycleId.copy();
        this.performancecycleId = other.performancecycleId == null ? null : other.performancecycleId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProjectCyclesCriteria copy() {
        return new ProjectCyclesCriteria(this);
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

    public BooleanFilter getIsactive() {
        return isactive;
    }

    public BooleanFilter isactive() {
        if (isactive == null) {
            isactive = new BooleanFilter();
        }
        return isactive;
    }

    public void setIsactive(BooleanFilter isactive) {
        this.isactive = isactive;
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

    public InstantFilter getAllowedafterduedateat() {
        return allowedafterduedateat;
    }

    public InstantFilter allowedafterduedateat() {
        if (allowedafterduedateat == null) {
            allowedafterduedateat = new InstantFilter();
        }
        return allowedafterduedateat;
    }

    public void setAllowedafterduedateat(InstantFilter allowedafterduedateat) {
        this.allowedafterduedateat = allowedafterduedateat;
    }

    public InstantFilter getDuedate() {
        return duedate;
    }

    public InstantFilter duedate() {
        if (duedate == null) {
            duedate = new InstantFilter();
        }
        return duedate;
    }

    public void setDuedate(InstantFilter duedate) {
        this.duedate = duedate;
    }

    public StringFilter getAuditlogs() {
        return auditlogs;
    }

    public StringFilter auditlogs() {
        if (auditlogs == null) {
            auditlogs = new StringFilter();
        }
        return auditlogs;
    }

    public void setAuditlogs(StringFilter auditlogs) {
        this.auditlogs = auditlogs;
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

    public LongFilter getAllowedafterduedatebyId() {
        return allowedafterduedatebyId;
    }

    public LongFilter allowedafterduedatebyId() {
        if (allowedafterduedatebyId == null) {
            allowedafterduedatebyId = new LongFilter();
        }
        return allowedafterduedatebyId;
    }

    public void setAllowedafterduedatebyId(LongFilter allowedafterduedatebyId) {
        this.allowedafterduedatebyId = allowedafterduedatebyId;
    }

    public LongFilter getArchitectId() {
        return architectId;
    }

    public LongFilter architectId() {
        if (architectId == null) {
            architectId = new LongFilter();
        }
        return architectId;
    }

    public void setArchitectId(LongFilter architectId) {
        this.architectId = architectId;
    }

    public LongFilter getProjectmanagerId() {
        return projectmanagerId;
    }

    public LongFilter projectmanagerId() {
        if (projectmanagerId == null) {
            projectmanagerId = new LongFilter();
        }
        return projectmanagerId;
    }

    public void setProjectmanagerId(LongFilter projectmanagerId) {
        this.projectmanagerId = projectmanagerId;
    }

    public LongFilter getRatingId() {
        return ratingId;
    }

    public LongFilter ratingId() {
        if (ratingId == null) {
            ratingId = new LongFilter();
        }
        return ratingId;
    }

    public void setRatingId(LongFilter ratingId) {
        this.ratingId = ratingId;
    }

    public LongFilter getEmployeeprojectratingsProjectcycleId() {
        return employeeprojectratingsProjectcycleId;
    }

    public LongFilter employeeprojectratingsProjectcycleId() {
        if (employeeprojectratingsProjectcycleId == null) {
            employeeprojectratingsProjectcycleId = new LongFilter();
        }
        return employeeprojectratingsProjectcycleId;
    }

    public void setEmployeeprojectratingsProjectcycleId(LongFilter employeeprojectratingsProjectcycleId) {
        this.employeeprojectratingsProjectcycleId = employeeprojectratingsProjectcycleId;
    }

    public LongFilter getPerformancecycleId() {
        return performancecycleId;
    }

    public LongFilter performancecycleId() {
        if (performancecycleId == null) {
            performancecycleId = new LongFilter();
        }
        return performancecycleId;
    }

    public void setPerformancecycleId(LongFilter performancecycleId) {
        this.performancecycleId = performancecycleId;
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
        final ProjectCyclesCriteria that = (ProjectCyclesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(isactive, that.isactive) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(allowedafterduedateat, that.allowedafterduedateat) &&
            Objects.equals(duedate, that.duedate) &&
            Objects.equals(auditlogs, that.auditlogs) &&
            Objects.equals(deletedat, that.deletedat) &&
            Objects.equals(projectId, that.projectId) &&
            Objects.equals(allowedafterduedatebyId, that.allowedafterduedatebyId) &&
            Objects.equals(architectId, that.architectId) &&
            Objects.equals(projectmanagerId, that.projectmanagerId) &&
            Objects.equals(ratingId, that.ratingId) &&
            Objects.equals(employeeprojectratingsProjectcycleId, that.employeeprojectratingsProjectcycleId) &&
            Objects.equals(performancecycleId, that.performancecycleId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            isactive,
            createdat,
            updatedat,
            allowedafterduedateat,
            duedate,
            auditlogs,
            deletedat,
            projectId,
            allowedafterduedatebyId,
            architectId,
            projectmanagerId,
            ratingId,
            employeeprojectratingsProjectcycleId,
            performancecycleId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectCyclesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (isactive != null ? "isactive=" + isactive + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (allowedafterduedateat != null ? "allowedafterduedateat=" + allowedafterduedateat + ", " : "") +
            (duedate != null ? "duedate=" + duedate + ", " : "") +
            (auditlogs != null ? "auditlogs=" + auditlogs + ", " : "") +
            (deletedat != null ? "deletedat=" + deletedat + ", " : "") +
            (projectId != null ? "projectId=" + projectId + ", " : "") +
            (allowedafterduedatebyId != null ? "allowedafterduedatebyId=" + allowedafterduedatebyId + ", " : "") +
            (architectId != null ? "architectId=" + architectId + ", " : "") +
            (projectmanagerId != null ? "projectmanagerId=" + projectmanagerId + ", " : "") +
            (ratingId != null ? "ratingId=" + ratingId + ", " : "") +
            (employeeprojectratingsProjectcycleId != null ? "employeeprojectratingsProjectcycleId=" + employeeprojectratingsProjectcycleId + ", " : "") +
            (performancecycleId != null ? "performancecycleId=" + performancecycleId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
