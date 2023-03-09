package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Documents} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.DocumentsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter designationjobdescriptionsDocumentId;

    private LongFilter employeecommentsDocumentId;

    private LongFilter employeedocumentsDocumentId;

    private Boolean distinct;

    public DocumentsCriteria() {}

    public DocumentsCriteria(DocumentsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.designationjobdescriptionsDocumentId =
            other.designationjobdescriptionsDocumentId == null ? null : other.designationjobdescriptionsDocumentId.copy();
        this.employeecommentsDocumentId = other.employeecommentsDocumentId == null ? null : other.employeecommentsDocumentId.copy();
        this.employeedocumentsDocumentId = other.employeedocumentsDocumentId == null ? null : other.employeedocumentsDocumentId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DocumentsCriteria copy() {
        return new DocumentsCriteria(this);
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

    public LongFilter getDesignationjobdescriptionsDocumentId() {
        return designationjobdescriptionsDocumentId;
    }

    public LongFilter designationjobdescriptionsDocumentId() {
        if (designationjobdescriptionsDocumentId == null) {
            designationjobdescriptionsDocumentId = new LongFilter();
        }
        return designationjobdescriptionsDocumentId;
    }

    public void setDesignationjobdescriptionsDocumentId(LongFilter designationjobdescriptionsDocumentId) {
        this.designationjobdescriptionsDocumentId = designationjobdescriptionsDocumentId;
    }

    public LongFilter getEmployeecommentsDocumentId() {
        return employeecommentsDocumentId;
    }

    public LongFilter employeecommentsDocumentId() {
        if (employeecommentsDocumentId == null) {
            employeecommentsDocumentId = new LongFilter();
        }
        return employeecommentsDocumentId;
    }

    public void setEmployeecommentsDocumentId(LongFilter employeecommentsDocumentId) {
        this.employeecommentsDocumentId = employeecommentsDocumentId;
    }

    public LongFilter getEmployeedocumentsDocumentId() {
        return employeedocumentsDocumentId;
    }

    public LongFilter employeedocumentsDocumentId() {
        if (employeedocumentsDocumentId == null) {
            employeedocumentsDocumentId = new LongFilter();
        }
        return employeedocumentsDocumentId;
    }

    public void setEmployeedocumentsDocumentId(LongFilter employeedocumentsDocumentId) {
        this.employeedocumentsDocumentId = employeedocumentsDocumentId;
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
        final DocumentsCriteria that = (DocumentsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(designationjobdescriptionsDocumentId, that.designationjobdescriptionsDocumentId) &&
            Objects.equals(employeecommentsDocumentId, that.employeecommentsDocumentId) &&
            Objects.equals(employeedocumentsDocumentId, that.employeedocumentsDocumentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            createdat,
            updatedat,
            designationjobdescriptionsDocumentId,
            employeecommentsDocumentId,
            employeedocumentsDocumentId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (designationjobdescriptionsDocumentId != null ? "designationjobdescriptionsDocumentId=" + designationjobdescriptionsDocumentId + ", " : "") +
            (employeecommentsDocumentId != null ? "employeecommentsDocumentId=" + employeecommentsDocumentId + ", " : "") +
            (employeedocumentsDocumentId != null ? "employeedocumentsDocumentId=" + employeedocumentsDocumentId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
