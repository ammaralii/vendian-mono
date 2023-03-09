package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Designations} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.DesignationsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /designations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DesignationsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private InstantFilter deletedat;

    private LongFilter designationjobdescriptionsDesignationId;

    private LongFilter employeejobinfoDesignationId;

    private LongFilter employeesDesignationId;

    private LongFilter userratingsremarksDesignationafterpromotionId;

    private LongFilter userratingsremarksDesignationafterredesignationId;

    private Boolean distinct;

    public DesignationsCriteria() {}

    public DesignationsCriteria(DesignationsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.deletedat = other.deletedat == null ? null : other.deletedat.copy();
        this.designationjobdescriptionsDesignationId =
            other.designationjobdescriptionsDesignationId == null ? null : other.designationjobdescriptionsDesignationId.copy();
        this.employeejobinfoDesignationId = other.employeejobinfoDesignationId == null ? null : other.employeejobinfoDesignationId.copy();
        this.employeesDesignationId = other.employeesDesignationId == null ? null : other.employeesDesignationId.copy();
        this.userratingsremarksDesignationafterpromotionId =
            other.userratingsremarksDesignationafterpromotionId == null ? null : other.userratingsremarksDesignationafterpromotionId.copy();
        this.userratingsremarksDesignationafterredesignationId =
            other.userratingsremarksDesignationafterredesignationId == null
                ? null
                : other.userratingsremarksDesignationafterredesignationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DesignationsCriteria copy() {
        return new DesignationsCriteria(this);
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

    public LongFilter getDesignationjobdescriptionsDesignationId() {
        return designationjobdescriptionsDesignationId;
    }

    public LongFilter designationjobdescriptionsDesignationId() {
        if (designationjobdescriptionsDesignationId == null) {
            designationjobdescriptionsDesignationId = new LongFilter();
        }
        return designationjobdescriptionsDesignationId;
    }

    public void setDesignationjobdescriptionsDesignationId(LongFilter designationjobdescriptionsDesignationId) {
        this.designationjobdescriptionsDesignationId = designationjobdescriptionsDesignationId;
    }

    public LongFilter getEmployeejobinfoDesignationId() {
        return employeejobinfoDesignationId;
    }

    public LongFilter employeejobinfoDesignationId() {
        if (employeejobinfoDesignationId == null) {
            employeejobinfoDesignationId = new LongFilter();
        }
        return employeejobinfoDesignationId;
    }

    public void setEmployeejobinfoDesignationId(LongFilter employeejobinfoDesignationId) {
        this.employeejobinfoDesignationId = employeejobinfoDesignationId;
    }

    public LongFilter getEmployeesDesignationId() {
        return employeesDesignationId;
    }

    public LongFilter employeesDesignationId() {
        if (employeesDesignationId == null) {
            employeesDesignationId = new LongFilter();
        }
        return employeesDesignationId;
    }

    public void setEmployeesDesignationId(LongFilter employeesDesignationId) {
        this.employeesDesignationId = employeesDesignationId;
    }

    public LongFilter getUserratingsremarksDesignationafterpromotionId() {
        return userratingsremarksDesignationafterpromotionId;
    }

    public LongFilter userratingsremarksDesignationafterpromotionId() {
        if (userratingsremarksDesignationafterpromotionId == null) {
            userratingsremarksDesignationafterpromotionId = new LongFilter();
        }
        return userratingsremarksDesignationafterpromotionId;
    }

    public void setUserratingsremarksDesignationafterpromotionId(LongFilter userratingsremarksDesignationafterpromotionId) {
        this.userratingsremarksDesignationafterpromotionId = userratingsremarksDesignationafterpromotionId;
    }

    public LongFilter getUserratingsremarksDesignationafterredesignationId() {
        return userratingsremarksDesignationafterredesignationId;
    }

    public LongFilter userratingsremarksDesignationafterredesignationId() {
        if (userratingsremarksDesignationafterredesignationId == null) {
            userratingsremarksDesignationafterredesignationId = new LongFilter();
        }
        return userratingsremarksDesignationafterredesignationId;
    }

    public void setUserratingsremarksDesignationafterredesignationId(LongFilter userratingsremarksDesignationafterredesignationId) {
        this.userratingsremarksDesignationafterredesignationId = userratingsremarksDesignationafterredesignationId;
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
        final DesignationsCriteria that = (DesignationsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(deletedat, that.deletedat) &&
            Objects.equals(designationjobdescriptionsDesignationId, that.designationjobdescriptionsDesignationId) &&
            Objects.equals(employeejobinfoDesignationId, that.employeejobinfoDesignationId) &&
            Objects.equals(employeesDesignationId, that.employeesDesignationId) &&
            Objects.equals(userratingsremarksDesignationafterpromotionId, that.userratingsremarksDesignationafterpromotionId) &&
            Objects.equals(userratingsremarksDesignationafterredesignationId, that.userratingsremarksDesignationafterredesignationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            createdat,
            updatedat,
            deletedat,
            designationjobdescriptionsDesignationId,
            employeejobinfoDesignationId,
            employeesDesignationId,
            userratingsremarksDesignationafterpromotionId,
            userratingsremarksDesignationafterredesignationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DesignationsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (deletedat != null ? "deletedat=" + deletedat + ", " : "") +
            (designationjobdescriptionsDesignationId != null ? "designationjobdescriptionsDesignationId=" + designationjobdescriptionsDesignationId + ", " : "") +
            (employeejobinfoDesignationId != null ? "employeejobinfoDesignationId=" + employeejobinfoDesignationId + ", " : "") +
            (employeesDesignationId != null ? "employeesDesignationId=" + employeesDesignationId + ", " : "") +
            (userratingsremarksDesignationafterpromotionId != null ? "userratingsremarksDesignationafterpromotionId=" + userratingsremarksDesignationafterpromotionId + ", " : "") +
            (userratingsremarksDesignationafterredesignationId != null ? "userratingsremarksDesignationafterredesignationId=" + userratingsremarksDesignationafterredesignationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
