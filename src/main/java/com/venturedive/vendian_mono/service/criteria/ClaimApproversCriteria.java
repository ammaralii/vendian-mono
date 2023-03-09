package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.ClaimApprovers} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.ClaimApproversResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /claim-approvers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClaimApproversCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter referenceid;

    private StringFilter designation;

    private IntegerFilter approveorder;

    private StringFilter reference;

    private StringFilter comments;

    private StringFilter approvedby;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter statusId;

    private LongFilter claimrequestId;

    private Boolean distinct;

    public ClaimApproversCriteria() {}

    public ClaimApproversCriteria(ClaimApproversCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.referenceid = other.referenceid == null ? null : other.referenceid.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.approveorder = other.approveorder == null ? null : other.approveorder.copy();
        this.reference = other.reference == null ? null : other.reference.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.approvedby = other.approvedby == null ? null : other.approvedby.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
        this.claimrequestId = other.claimrequestId == null ? null : other.claimrequestId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClaimApproversCriteria copy() {
        return new ClaimApproversCriteria(this);
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

    public IntegerFilter getReferenceid() {
        return referenceid;
    }

    public IntegerFilter referenceid() {
        if (referenceid == null) {
            referenceid = new IntegerFilter();
        }
        return referenceid;
    }

    public void setReferenceid(IntegerFilter referenceid) {
        this.referenceid = referenceid;
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

    public IntegerFilter getApproveorder() {
        return approveorder;
    }

    public IntegerFilter approveorder() {
        if (approveorder == null) {
            approveorder = new IntegerFilter();
        }
        return approveorder;
    }

    public void setApproveorder(IntegerFilter approveorder) {
        this.approveorder = approveorder;
    }

    public StringFilter getReference() {
        return reference;
    }

    public StringFilter reference() {
        if (reference == null) {
            reference = new StringFilter();
        }
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
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

    public StringFilter getApprovedby() {
        return approvedby;
    }

    public StringFilter approvedby() {
        if (approvedby == null) {
            approvedby = new StringFilter();
        }
        return approvedby;
    }

    public void setApprovedby(StringFilter approvedby) {
        this.approvedby = approvedby;
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

    public LongFilter getStatusId() {
        return statusId;
    }

    public LongFilter statusId() {
        if (statusId == null) {
            statusId = new LongFilter();
        }
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public LongFilter getClaimrequestId() {
        return claimrequestId;
    }

    public LongFilter claimrequestId() {
        if (claimrequestId == null) {
            claimrequestId = new LongFilter();
        }
        return claimrequestId;
    }

    public void setClaimrequestId(LongFilter claimrequestId) {
        this.claimrequestId = claimrequestId;
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
        final ClaimApproversCriteria that = (ClaimApproversCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(referenceid, that.referenceid) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(approveorder, that.approveorder) &&
            Objects.equals(reference, that.reference) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(approvedby, that.approvedby) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(statusId, that.statusId) &&
            Objects.equals(claimrequestId, that.claimrequestId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            referenceid,
            designation,
            approveorder,
            reference,
            comments,
            approvedby,
            createdat,
            updatedat,
            statusId,
            claimrequestId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClaimApproversCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (referenceid != null ? "referenceid=" + referenceid + ", " : "") +
            (designation != null ? "designation=" + designation + ", " : "") +
            (approveorder != null ? "approveorder=" + approveorder + ", " : "") +
            (reference != null ? "reference=" + reference + ", " : "") +
            (comments != null ? "comments=" + comments + ", " : "") +
            (approvedby != null ? "approvedby=" + approvedby + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (statusId != null ? "statusId=" + statusId + ", " : "") +
            (claimrequestId != null ? "claimrequestId=" + claimrequestId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
