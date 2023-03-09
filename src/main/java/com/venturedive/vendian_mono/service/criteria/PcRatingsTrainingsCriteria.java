package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.PcRatingsTrainings} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.PcRatingsTrainingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pc-ratings-trainings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PcRatingsTrainingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter strength;

    private StringFilter developmentArea;

    private StringFilter careerAmbition;

    private StringFilter shortCourse;

    private StringFilter technicalTraining;

    private StringFilter softSkillsTraining;

    private BooleanFilter criticalPosition;

    private BooleanFilter highPotential;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private LongFilter successorForId;

    private LongFilter ratingId;

    private Boolean distinct;

    public PcRatingsTrainingsCriteria() {}

    public PcRatingsTrainingsCriteria(PcRatingsTrainingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.strength = other.strength == null ? null : other.strength.copy();
        this.developmentArea = other.developmentArea == null ? null : other.developmentArea.copy();
        this.careerAmbition = other.careerAmbition == null ? null : other.careerAmbition.copy();
        this.shortCourse = other.shortCourse == null ? null : other.shortCourse.copy();
        this.technicalTraining = other.technicalTraining == null ? null : other.technicalTraining.copy();
        this.softSkillsTraining = other.softSkillsTraining == null ? null : other.softSkillsTraining.copy();
        this.criticalPosition = other.criticalPosition == null ? null : other.criticalPosition.copy();
        this.highPotential = other.highPotential == null ? null : other.highPotential.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.successorForId = other.successorForId == null ? null : other.successorForId.copy();
        this.ratingId = other.ratingId == null ? null : other.ratingId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PcRatingsTrainingsCriteria copy() {
        return new PcRatingsTrainingsCriteria(this);
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

    public StringFilter getDevelopmentArea() {
        return developmentArea;
    }

    public StringFilter developmentArea() {
        if (developmentArea == null) {
            developmentArea = new StringFilter();
        }
        return developmentArea;
    }

    public void setDevelopmentArea(StringFilter developmentArea) {
        this.developmentArea = developmentArea;
    }

    public StringFilter getCareerAmbition() {
        return careerAmbition;
    }

    public StringFilter careerAmbition() {
        if (careerAmbition == null) {
            careerAmbition = new StringFilter();
        }
        return careerAmbition;
    }

    public void setCareerAmbition(StringFilter careerAmbition) {
        this.careerAmbition = careerAmbition;
    }

    public StringFilter getShortCourse() {
        return shortCourse;
    }

    public StringFilter shortCourse() {
        if (shortCourse == null) {
            shortCourse = new StringFilter();
        }
        return shortCourse;
    }

    public void setShortCourse(StringFilter shortCourse) {
        this.shortCourse = shortCourse;
    }

    public StringFilter getTechnicalTraining() {
        return technicalTraining;
    }

    public StringFilter technicalTraining() {
        if (technicalTraining == null) {
            technicalTraining = new StringFilter();
        }
        return technicalTraining;
    }

    public void setTechnicalTraining(StringFilter technicalTraining) {
        this.technicalTraining = technicalTraining;
    }

    public StringFilter getSoftSkillsTraining() {
        return softSkillsTraining;
    }

    public StringFilter softSkillsTraining() {
        if (softSkillsTraining == null) {
            softSkillsTraining = new StringFilter();
        }
        return softSkillsTraining;
    }

    public void setSoftSkillsTraining(StringFilter softSkillsTraining) {
        this.softSkillsTraining = softSkillsTraining;
    }

    public BooleanFilter getCriticalPosition() {
        return criticalPosition;
    }

    public BooleanFilter criticalPosition() {
        if (criticalPosition == null) {
            criticalPosition = new BooleanFilter();
        }
        return criticalPosition;
    }

    public void setCriticalPosition(BooleanFilter criticalPosition) {
        this.criticalPosition = criticalPosition;
    }

    public BooleanFilter getHighPotential() {
        return highPotential;
    }

    public BooleanFilter highPotential() {
        if (highPotential == null) {
            highPotential = new BooleanFilter();
        }
        return highPotential;
    }

    public void setHighPotential(BooleanFilter highPotential) {
        this.highPotential = highPotential;
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

    public LongFilter getSuccessorForId() {
        return successorForId;
    }

    public LongFilter successorForId() {
        if (successorForId == null) {
            successorForId = new LongFilter();
        }
        return successorForId;
    }

    public void setSuccessorForId(LongFilter successorForId) {
        this.successorForId = successorForId;
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
        final PcRatingsTrainingsCriteria that = (PcRatingsTrainingsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(strength, that.strength) &&
            Objects.equals(developmentArea, that.developmentArea) &&
            Objects.equals(careerAmbition, that.careerAmbition) &&
            Objects.equals(shortCourse, that.shortCourse) &&
            Objects.equals(technicalTraining, that.technicalTraining) &&
            Objects.equals(softSkillsTraining, that.softSkillsTraining) &&
            Objects.equals(criticalPosition, that.criticalPosition) &&
            Objects.equals(highPotential, that.highPotential) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(successorForId, that.successorForId) &&
            Objects.equals(ratingId, that.ratingId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            strength,
            developmentArea,
            careerAmbition,
            shortCourse,
            technicalTraining,
            softSkillsTraining,
            criticalPosition,
            highPotential,
            createdAt,
            updatedAt,
            deletedAt,
            version,
            successorForId,
            ratingId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PcRatingsTrainingsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (strength != null ? "strength=" + strength + ", " : "") +
            (developmentArea != null ? "developmentArea=" + developmentArea + ", " : "") +
            (careerAmbition != null ? "careerAmbition=" + careerAmbition + ", " : "") +
            (shortCourse != null ? "shortCourse=" + shortCourse + ", " : "") +
            (technicalTraining != null ? "technicalTraining=" + technicalTraining + ", " : "") +
            (softSkillsTraining != null ? "softSkillsTraining=" + softSkillsTraining + ", " : "") +
            (criticalPosition != null ? "criticalPosition=" + criticalPosition + ", " : "") +
            (highPotential != null ? "highPotential=" + highPotential + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (successorForId != null ? "successorForId=" + successorForId + ", " : "") +
            (ratingId != null ? "ratingId=" + ratingId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
