package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.ClaimRequestViews} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.ClaimRequestViewsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /claim-request-views?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClaimRequestViewsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter costcenter;

    private StringFilter comments;

    private BigDecimalFilter amountreleased;

    private StringFilter designation;

    private StringFilter department;

    private StringFilter location;

    private StringFilter manager;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter employeeId;

    private Boolean distinct;

    public ClaimRequestViewsCriteria() {}

    public ClaimRequestViewsCriteria(ClaimRequestViewsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.costcenter = other.costcenter == null ? null : other.costcenter.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.amountreleased = other.amountreleased == null ? null : other.amountreleased.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.department = other.department == null ? null : other.department.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.manager = other.manager == null ? null : other.manager.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClaimRequestViewsCriteria copy() {
        return new ClaimRequestViewsCriteria(this);
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

    public StringFilter getCostcenter() {
        return costcenter;
    }

    public StringFilter costcenter() {
        if (costcenter == null) {
            costcenter = new StringFilter();
        }
        return costcenter;
    }

    public void setCostcenter(StringFilter costcenter) {
        this.costcenter = costcenter;
    }

    public StringFilter getComments() {
        return comments;
    }

    public StringFilter comments() {
        if (comments == null) {
            comments = new StringFilter();
        }
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public BigDecimalFilter getAmountreleased() {
        return amountreleased;
    }

    public BigDecimalFilter amountreleased() {
        if (amountreleased == null) {
            amountreleased = new BigDecimalFilter();
        }
        return amountreleased;
    }

    public void setAmountreleased(BigDecimalFilter amountreleased) {
        this.amountreleased = amountreleased;
    }

    public StringFilter getDesignation() {
        return designation;
    }

    public StringFilter designation() {
        if (designation == null) {
            designation = new StringFilter();
        }
        return designation;
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
    }

    public StringFilter getDepartment() {
        return department;
    }

    public StringFilter department() {
        if (department == null) {
            department = new StringFilter();
        }
        return department;
    }

    public void setDepartment(StringFilter department) {
        this.department = department;
    }

    public StringFilter getLocation() {
        return location;
    }

    public StringFilter location() {
        if (location == null) {
            location = new StringFilter();
        }
        return location;
    }

    public void setLocation(StringFilter location) {
        this.location = location;
    }

    public StringFilter getManager() {
        return manager;
    }

    public StringFilter manager() {
        if (manager == null) {
            manager = new StringFilter();
        }
        return manager;
    }

    public void setManager(StringFilter manager) {
        this.manager = manager;
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
        final ClaimRequestViewsCriteria that = (ClaimRequestViewsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(costcenter, that.costcenter) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(amountreleased, that.amountreleased) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(department, that.department) &&
            Objects.equals(location, that.location) &&
            Objects.equals(manager, that.manager) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            costcenter,
            comments,
            amountreleased,
            designation,
            department,
            location,
            manager,
            createdat,
            updatedat,
            employeeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClaimRequestViewsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (costcenter != null ? "costcenter=" + costcenter + ", " : "") +
            (comments != null ? "comments=" + comments + ", " : "") +
            (amountreleased != null ? "amountreleased=" + amountreleased + ", " : "") +
            (designation != null ? "designation=" + designation + ", " : "") +
            (department != null ? "department=" + department + ", " : "") +
            (location != null ? "location=" + location + ", " : "") +
            (manager != null ? "manager=" + manager + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
