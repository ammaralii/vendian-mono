package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.ZEmployeeProjects} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.ZEmployeeProjectsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /z-employee-projects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ZEmployeeProjectsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter status;

    private StringFilter type;

    private InstantFilter startdate;

    private InstantFilter enddate;

    private StringFilter name;

    private BooleanFilter allocation;

    private StringFilter billed;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private IntegerFilter deliverymanagerid;

    private IntegerFilter architectid;

    private StringFilter notes;

    private InstantFilter previousenddate;

    private StringFilter data;

    private InstantFilter extendedenddate;

    private StringFilter probability;

    private LongFilter eventId;

    private LongFilter employeeId;

    private LongFilter projectId;

    private LongFilter employeeprojectId;

    private LongFilter assignorId;

    private LongFilter projectmanagerId;

    private Boolean distinct;

    public ZEmployeeProjectsCriteria() {}

    public ZEmployeeProjectsCriteria(ZEmployeeProjectsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.startdate = other.startdate == null ? null : other.startdate.copy();
        this.enddate = other.enddate == null ? null : other.enddate.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.allocation = other.allocation == null ? null : other.allocation.copy();
        this.billed = other.billed == null ? null : other.billed.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.deliverymanagerid = other.deliverymanagerid == null ? null : other.deliverymanagerid.copy();
        this.architectid = other.architectid == null ? null : other.architectid.copy();
        this.notes = other.notes == null ? null : other.notes.copy();
        this.previousenddate = other.previousenddate == null ? null : other.previousenddate.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.extendedenddate = other.extendedenddate == null ? null : other.extendedenddate.copy();
        this.probability = other.probability == null ? null : other.probability.copy();
        this.eventId = other.eventId == null ? null : other.eventId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
        this.employeeprojectId = other.employeeprojectId == null ? null : other.employeeprojectId.copy();
        this.assignorId = other.assignorId == null ? null : other.assignorId.copy();
        this.projectmanagerId = other.projectmanagerId == null ? null : other.projectmanagerId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ZEmployeeProjectsCriteria copy() {
        return new ZEmployeeProjectsCriteria(this);
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

    public IntegerFilter getDeliverymanagerid() {
        return deliverymanagerid;
    }

    public IntegerFilter deliverymanagerid() {
        if (deliverymanagerid == null) {
            deliverymanagerid = new IntegerFilter();
        }
        return deliverymanagerid;
    }

    public void setDeliverymanagerid(IntegerFilter deliverymanagerid) {
        this.deliverymanagerid = deliverymanagerid;
    }

    public IntegerFilter getArchitectid() {
        return architectid;
    }

    public IntegerFilter architectid() {
        if (architectid == null) {
            architectid = new IntegerFilter();
        }
        return architectid;
    }

    public void setArchitectid(IntegerFilter architectid) {
        this.architectid = architectid;
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

    public InstantFilter getPreviousenddate() {
        return previousenddate;
    }

    public InstantFilter previousenddate() {
        if (previousenddate == null) {
            previousenddate = new InstantFilter();
        }
        return previousenddate;
    }

    public void setPreviousenddate(InstantFilter previousenddate) {
        this.previousenddate = previousenddate;
    }

    public StringFilter getData() {
        return data;
    }

    public StringFilter data() {
        if (data == null) {
            data = new StringFilter();
        }
        return data;
    }

    public void setData(StringFilter data) {
        this.data = data;
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

    public LongFilter getEventId() {
        return eventId;
    }

    public LongFilter eventId() {
        if (eventId == null) {
            eventId = new LongFilter();
        }
        return eventId;
    }

    public void setEventId(LongFilter eventId) {
        this.eventId = eventId;
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

    public LongFilter getEmployeeprojectId() {
        return employeeprojectId;
    }

    public LongFilter employeeprojectId() {
        if (employeeprojectId == null) {
            employeeprojectId = new LongFilter();
        }
        return employeeprojectId;
    }

    public void setEmployeeprojectId(LongFilter employeeprojectId) {
        this.employeeprojectId = employeeprojectId;
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
        final ZEmployeeProjectsCriteria that = (ZEmployeeProjectsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(type, that.type) &&
            Objects.equals(startdate, that.startdate) &&
            Objects.equals(enddate, that.enddate) &&
            Objects.equals(name, that.name) &&
            Objects.equals(allocation, that.allocation) &&
            Objects.equals(billed, that.billed) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(deliverymanagerid, that.deliverymanagerid) &&
            Objects.equals(architectid, that.architectid) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(previousenddate, that.previousenddate) &&
            Objects.equals(data, that.data) &&
            Objects.equals(extendedenddate, that.extendedenddate) &&
            Objects.equals(probability, that.probability) &&
            Objects.equals(eventId, that.eventId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(projectId, that.projectId) &&
            Objects.equals(employeeprojectId, that.employeeprojectId) &&
            Objects.equals(assignorId, that.assignorId) &&
            Objects.equals(projectmanagerId, that.projectmanagerId) &&
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
            name,
            allocation,
            billed,
            createdat,
            updatedat,
            deliverymanagerid,
            architectid,
            notes,
            previousenddate,
            data,
            extendedenddate,
            probability,
            eventId,
            employeeId,
            projectId,
            employeeprojectId,
            assignorId,
            projectmanagerId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ZEmployeeProjectsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (startdate != null ? "startdate=" + startdate + ", " : "") +
            (enddate != null ? "enddate=" + enddate + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (allocation != null ? "allocation=" + allocation + ", " : "") +
            (billed != null ? "billed=" + billed + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (deliverymanagerid != null ? "deliverymanagerid=" + deliverymanagerid + ", " : "") +
            (architectid != null ? "architectid=" + architectid + ", " : "") +
            (notes != null ? "notes=" + notes + ", " : "") +
            (previousenddate != null ? "previousenddate=" + previousenddate + ", " : "") +
            (data != null ? "data=" + data + ", " : "") +
            (extendedenddate != null ? "extendedenddate=" + extendedenddate + ", " : "") +
            (probability != null ? "probability=" + probability + ", " : "") +
            (eventId != null ? "eventId=" + eventId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (projectId != null ? "projectId=" + projectId + ", " : "") +
            (employeeprojectId != null ? "employeeprojectId=" + employeeprojectId + ", " : "") +
            (assignorId != null ? "assignorId=" + assignorId + ", " : "") +
            (projectmanagerId != null ? "projectmanagerId=" + projectmanagerId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
