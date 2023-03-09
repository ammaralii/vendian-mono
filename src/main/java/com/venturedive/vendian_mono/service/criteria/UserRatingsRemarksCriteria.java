package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.UserRatingsRemarks} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.UserRatingsRemarksResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-ratings-remarks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserRatingsRemarksCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter isPromotion;

    private StringFilter strength;

    private StringFilter areaOfImprovement;

    private StringFilter promotionJustification;

    private StringFilter newGrade;

    private BooleanFilter isRedesignation;

    private IntegerFilter recommendedSalary;

    private StringFilter status;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private StringFilter otherComments;

    private LongFilter designationAfterPromotionId;

    private LongFilter designationAfterRedesignationId;

    private LongFilter raterId;

    private LongFilter pcEmployeeRatingId;

    private Boolean distinct;

    public UserRatingsRemarksCriteria() {}

    public UserRatingsRemarksCriteria(UserRatingsRemarksCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.isPromotion = other.isPromotion == null ? null : other.isPromotion.copy();
        this.strength = other.strength == null ? null : other.strength.copy();
        this.areaOfImprovement = other.areaOfImprovement == null ? null : other.areaOfImprovement.copy();
        this.promotionJustification = other.promotionJustification == null ? null : other.promotionJustification.copy();
        this.newGrade = other.newGrade == null ? null : other.newGrade.copy();
        this.isRedesignation = other.isRedesignation == null ? null : other.isRedesignation.copy();
        this.recommendedSalary = other.recommendedSalary == null ? null : other.recommendedSalary.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.otherComments = other.otherComments == null ? null : other.otherComments.copy();
        this.designationAfterPromotionId = other.designationAfterPromotionId == null ? null : other.designationAfterPromotionId.copy();
        this.designationAfterRedesignationId =
            other.designationAfterRedesignationId == null ? null : other.designationAfterRedesignationId.copy();
        this.raterId = other.raterId == null ? null : other.raterId.copy();
        this.pcEmployeeRatingId = other.pcEmployeeRatingId == null ? null : other.pcEmployeeRatingId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UserRatingsRemarksCriteria copy() {
        return new UserRatingsRemarksCriteria(this);
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

    public BooleanFilter getIsPromotion() {
        return isPromotion;
    }

    public BooleanFilter isPromotion() {
        if (isPromotion == null) {
            isPromotion = new BooleanFilter();
        }
        return isPromotion;
    }

    public void setIsPromotion(BooleanFilter isPromotion) {
        this.isPromotion = isPromotion;
    }

    public StringFilter getStrength() {
        return strength;
    }

    public StringFilter strength() {
        if (strength == null) {
            strength = new StringFilter();
        }
        return strength;
    }

    public void setStrength(StringFilter strength) {
        this.strength = strength;
    }

    public StringFilter getAreaOfImprovement() {
        return areaOfImprovement;
    }

    public StringFilter areaOfImprovement() {
        if (areaOfImprovement == null) {
            areaOfImprovement = new StringFilter();
        }
        return areaOfImprovement;
    }

    public void setAreaOfImprovement(StringFilter areaOfImprovement) {
        this.areaOfImprovement = areaOfImprovement;
    }

    public StringFilter getPromotionJustification() {
        return promotionJustification;
    }

    public StringFilter promotionJustification() {
        if (promotionJustification == null) {
            promotionJustification = new StringFilter();
        }
        return promotionJustification;
    }

    public void setPromotionJustification(StringFilter promotionJustification) {
        this.promotionJustification = promotionJustification;
    }

    public StringFilter getNewGrade() {
        return newGrade;
    }

    public StringFilter newGrade() {
        if (newGrade == null) {
            newGrade = new StringFilter();
        }
        return newGrade;
    }

    public void setNewGrade(StringFilter newGrade) {
        this.newGrade = newGrade;
    }

    public BooleanFilter getIsRedesignation() {
        return isRedesignation;
    }

    public BooleanFilter isRedesignation() {
        if (isRedesignation == null) {
            isRedesignation = new BooleanFilter();
        }
        return isRedesignation;
    }

    public void setIsRedesignation(BooleanFilter isRedesignation) {
        this.isRedesignation = isRedesignation;
    }

    public IntegerFilter getRecommendedSalary() {
        return recommendedSalary;
    }

    public IntegerFilter recommendedSalary() {
        if (recommendedSalary == null) {
            recommendedSalary = new IntegerFilter();
        }
        return recommendedSalary;
    }

    public void setRecommendedSalary(IntegerFilter recommendedSalary) {
        this.recommendedSalary = recommendedSalary;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public InstantFilter createdAt() {
        if (createdAt == null) {
            createdAt = new InstantFilter();
        }
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public InstantFilter getUpdatedAt() {
        return updatedAt;
    }

    public InstantFilter updatedAt() {
        if (updatedAt == null) {
            updatedAt = new InstantFilter();
        }
        return updatedAt;
    }

    public void setUpdatedAt(InstantFilter updatedAt) {
        this.updatedAt = updatedAt;
    }

    public InstantFilter getDeletedAt() {
        return deletedAt;
    }

    public InstantFilter deletedAt() {
        if (deletedAt == null) {
            deletedAt = new InstantFilter();
        }
        return deletedAt;
    }

    public void setDeletedAt(InstantFilter deletedAt) {
        this.deletedAt = deletedAt;
    }

    public IntegerFilter getVersion() {
        return version;
    }

    public IntegerFilter version() {
        if (version == null) {
            version = new IntegerFilter();
        }
        return version;
    }

    public void setVersion(IntegerFilter version) {
        this.version = version;
    }

    public StringFilter getOtherComments() {
        return otherComments;
    }

    public StringFilter otherComments() {
        if (otherComments == null) {
            otherComments = new StringFilter();
        }
        return otherComments;
    }

    public void setOtherComments(StringFilter otherComments) {
        this.otherComments = otherComments;
    }

    public LongFilter getDesignationAfterPromotionId() {
        return designationAfterPromotionId;
    }

    public LongFilter designationAfterPromotionId() {
        if (designationAfterPromotionId == null) {
            designationAfterPromotionId = new LongFilter();
        }
        return designationAfterPromotionId;
    }

    public void setDesignationAfterPromotionId(LongFilter designationAfterPromotionId) {
        this.designationAfterPromotionId = designationAfterPromotionId;
    }

    public LongFilter getDesignationAfterRedesignationId() {
        return designationAfterRedesignationId;
    }

    public LongFilter designationAfterRedesignationId() {
        if (designationAfterRedesignationId == null) {
            designationAfterRedesignationId = new LongFilter();
        }
        return designationAfterRedesignationId;
    }

    public void setDesignationAfterRedesignationId(LongFilter designationAfterRedesignationId) {
        this.designationAfterRedesignationId = designationAfterRedesignationId;
    }

    public LongFilter getRaterId() {
        return raterId;
    }

    public LongFilter raterId() {
        if (raterId == null) {
            raterId = new LongFilter();
        }
        return raterId;
    }

    public void setRaterId(LongFilter raterId) {
        this.raterId = raterId;
    }

    public LongFilter getPcEmployeeRatingId() {
        return pcEmployeeRatingId;
    }

    public LongFilter pcEmployeeRatingId() {
        if (pcEmployeeRatingId == null) {
            pcEmployeeRatingId = new LongFilter();
        }
        return pcEmployeeRatingId;
    }

    public void setPcEmployeeRatingId(LongFilter pcEmployeeRatingId) {
        this.pcEmployeeRatingId = pcEmployeeRatingId;
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
        final UserRatingsRemarksCriteria that = (UserRatingsRemarksCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(isPromotion, that.isPromotion) &&
            Objects.equals(strength, that.strength) &&
            Objects.equals(areaOfImprovement, that.areaOfImprovement) &&
            Objects.equals(promotionJustification, that.promotionJustification) &&
            Objects.equals(newGrade, that.newGrade) &&
            Objects.equals(isRedesignation, that.isRedesignation) &&
            Objects.equals(recommendedSalary, that.recommendedSalary) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(otherComments, that.otherComments) &&
            Objects.equals(designationAfterPromotionId, that.designationAfterPromotionId) &&
            Objects.equals(designationAfterRedesignationId, that.designationAfterRedesignationId) &&
            Objects.equals(raterId, that.raterId) &&
            Objects.equals(pcEmployeeRatingId, that.pcEmployeeRatingId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            isPromotion,
            strength,
            areaOfImprovement,
            promotionJustification,
            newGrade,
            isRedesignation,
            recommendedSalary,
            status,
            createdAt,
            updatedAt,
            deletedAt,
            version,
            otherComments,
            designationAfterPromotionId,
            designationAfterRedesignationId,
            raterId,
            pcEmployeeRatingId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserRatingsRemarksCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (isPromotion != null ? "isPromotion=" + isPromotion + ", " : "") +
            (strength != null ? "strength=" + strength + ", " : "") +
            (areaOfImprovement != null ? "areaOfImprovement=" + areaOfImprovement + ", " : "") +
            (promotionJustification != null ? "promotionJustification=" + promotionJustification + ", " : "") +
            (newGrade != null ? "newGrade=" + newGrade + ", " : "") +
            (isRedesignation != null ? "isRedesignation=" + isRedesignation + ", " : "") +
            (recommendedSalary != null ? "recommendedSalary=" + recommendedSalary + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (otherComments != null ? "otherComments=" + otherComments + ", " : "") +
            (designationAfterPromotionId != null ? "designationAfterPromotionId=" + designationAfterPromotionId + ", " : "") +
            (designationAfterRedesignationId != null ? "designationAfterRedesignationId=" + designationAfterRedesignationId + ", " : "") +
            (raterId != null ? "raterId=" + raterId + ", " : "") +
            (pcEmployeeRatingId != null ? "pcEmployeeRatingId=" + pcEmployeeRatingId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
