package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.EmployeeTalents} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.EmployeeTalentsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-talents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeTalentsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter criticalposition;

    private BooleanFilter highpotential;

    private StringFilter successorfor;

    private BooleanFilter criticalexperience;

    private StringFilter promotionreadiness;

    private StringFilter leadershipqualities;

    private StringFilter careerambitions;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter employeeId;

    private Boolean distinct;

    public EmployeeTalentsCriteria() {}

    public EmployeeTalentsCriteria(EmployeeTalentsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.criticalposition = other.criticalposition == null ? null : other.criticalposition.copy();
        this.highpotential = other.highpotential == null ? null : other.highpotential.copy();
        this.successorfor = other.successorfor == null ? null : other.successorfor.copy();
        this.criticalexperience = other.criticalexperience == null ? null : other.criticalexperience.copy();
        this.promotionreadiness = other.promotionreadiness == null ? null : other.promotionreadiness.copy();
        this.leadershipqualities = other.leadershipqualities == null ? null : other.leadershipqualities.copy();
        this.careerambitions = other.careerambitions == null ? null : other.careerambitions.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeTalentsCriteria copy() {
        return new EmployeeTalentsCriteria(this);
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

    public BooleanFilter getCriticalposition() {
        return criticalposition;
    }

    public BooleanFilter criticalposition() {
        if (criticalposition == null) {
            criticalposition = new BooleanFilter();
        }
        return criticalposition;
    }

    public void setCriticalposition(BooleanFilter criticalposition) {
        this.criticalposition = criticalposition;
    }

    public BooleanFilter getHighpotential() {
        return highpotential;
    }

    public BooleanFilter highpotential() {
        if (highpotential == null) {
            highpotential = new BooleanFilter();
        }
        return highpotential;
    }

    public void setHighpotential(BooleanFilter highpotential) {
        this.highpotential = highpotential;
    }

    public StringFilter getSuccessorfor() {
        return successorfor;
    }

    public StringFilter successorfor() {
        if (successorfor == null) {
            successorfor = new StringFilter();
        }
        return successorfor;
    }

    public void setSuccessorfor(StringFilter successorfor) {
        this.successorfor = successorfor;
    }

    public BooleanFilter getCriticalexperience() {
        return criticalexperience;
    }

    public BooleanFilter criticalexperience() {
        if (criticalexperience == null) {
            criticalexperience = new BooleanFilter();
        }
        return criticalexperience;
    }

    public void setCriticalexperience(BooleanFilter criticalexperience) {
        this.criticalexperience = criticalexperience;
    }

    public StringFilter getPromotionreadiness() {
        return promotionreadiness;
    }

    public StringFilter promotionreadiness() {
        if (promotionreadiness == null) {
            promotionreadiness = new StringFilter();
        }
        return promotionreadiness;
    }

    public void setPromotionreadiness(StringFilter promotionreadiness) {
        this.promotionreadiness = promotionreadiness;
    }

    public StringFilter getLeadershipqualities() {
        return leadershipqualities;
    }

    public StringFilter leadershipqualities() {
        if (leadershipqualities == null) {
            leadershipqualities = new StringFilter();
        }
        return leadershipqualities;
    }

    public void setLeadershipqualities(StringFilter leadershipqualities) {
        this.leadershipqualities = leadershipqualities;
    }

    public StringFilter getCareerambitions() {
        return careerambitions;
    }

    public StringFilter careerambitions() {
        if (careerambitions == null) {
            careerambitions = new StringFilter();
        }
        return careerambitions;
    }

    public void setCareerambitions(StringFilter careerambitions) {
        this.careerambitions = careerambitions;
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
        final EmployeeTalentsCriteria that = (EmployeeTalentsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(criticalposition, that.criticalposition) &&
            Objects.equals(highpotential, that.highpotential) &&
            Objects.equals(successorfor, that.successorfor) &&
            Objects.equals(criticalexperience, that.criticalexperience) &&
            Objects.equals(promotionreadiness, that.promotionreadiness) &&
            Objects.equals(leadershipqualities, that.leadershipqualities) &&
            Objects.equals(careerambitions, that.careerambitions) &&
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
            criticalposition,
            highpotential,
            successorfor,
            criticalexperience,
            promotionreadiness,
            leadershipqualities,
            careerambitions,
            createdat,
            updatedat,
            employeeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeTalentsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (criticalposition != null ? "criticalposition=" + criticalposition + ", " : "") +
            (highpotential != null ? "highpotential=" + highpotential + ", " : "") +
            (successorfor != null ? "successorfor=" + successorfor + ", " : "") +
            (criticalexperience != null ? "criticalexperience=" + criticalexperience + ", " : "") +
            (promotionreadiness != null ? "promotionreadiness=" + promotionreadiness + ", " : "") +
            (leadershipqualities != null ? "leadershipqualities=" + leadershipqualities + ", " : "") +
            (careerambitions != null ? "careerambitions=" + careerambitions + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
