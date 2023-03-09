package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.DesignationJobDescriptions} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.DesignationJobDescriptionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /designation-job-descriptions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DesignationJobDescriptionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter documentId;

    private LongFilter designationId;

    private LongFilter employeejobinfoJobdescriptionId;

    private Boolean distinct;

    public DesignationJobDescriptionsCriteria() {}

    public DesignationJobDescriptionsCriteria(DesignationJobDescriptionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.documentId = other.documentId == null ? null : other.documentId.copy();
        this.designationId = other.designationId == null ? null : other.designationId.copy();
        this.employeejobinfoJobdescriptionId =
            other.employeejobinfoJobdescriptionId == null ? null : other.employeejobinfoJobdescriptionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DesignationJobDescriptionsCriteria copy() {
        return new DesignationJobDescriptionsCriteria(this);
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

    public LongFilter getDocumentId() {
        return documentId;
    }

    public LongFilter documentId() {
        if (documentId == null) {
            documentId = new LongFilter();
        }
        return documentId;
    }

    public void setDocumentId(LongFilter documentId) {
        this.documentId = documentId;
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

    public LongFilter getEmployeejobinfoJobdescriptionId() {
        return employeejobinfoJobdescriptionId;
    }

    public LongFilter employeejobinfoJobdescriptionId() {
        if (employeejobinfoJobdescriptionId == null) {
            employeejobinfoJobdescriptionId = new LongFilter();
        }
        return employeejobinfoJobdescriptionId;
    }

    public void setEmployeejobinfoJobdescriptionId(LongFilter employeejobinfoJobdescriptionId) {
        this.employeejobinfoJobdescriptionId = employeejobinfoJobdescriptionId;
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
        final DesignationJobDescriptionsCriteria that = (DesignationJobDescriptionsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(documentId, that.documentId) &&
            Objects.equals(designationId, that.designationId) &&
            Objects.equals(employeejobinfoJobdescriptionId, that.employeejobinfoJobdescriptionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdat, updatedat, documentId, designationId, employeejobinfoJobdescriptionId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DesignationJobDescriptionsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (documentId != null ? "documentId=" + documentId + ", " : "") +
            (designationId != null ? "designationId=" + designationId + ", " : "") +
            (employeejobinfoJobdescriptionId != null ? "employeejobinfoJobdescriptionId=" + employeejobinfoJobdescriptionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
