package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.EmployeeSkills} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.EmployeeSkillsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-skills?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeSkillsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private IntegerFilter expertise;

    private LongFilter employeeId;

    private LongFilter skillId;

    private Boolean distinct;

    public EmployeeSkillsCriteria() {}

    public EmployeeSkillsCriteria(EmployeeSkillsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.expertise = other.expertise == null ? null : other.expertise.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.skillId = other.skillId == null ? null : other.skillId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeSkillsCriteria copy() {
        return new EmployeeSkillsCriteria(this);
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

    public IntegerFilter getExpertise() {
        return expertise;
    }

    public IntegerFilter expertise() {
        if (expertise == null) {
            expertise = new IntegerFilter();
        }
        return expertise;
    }

    public void setExpertise(IntegerFilter expertise) {
        this.expertise = expertise;
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

    public LongFilter getSkillId() {
        return skillId;
    }

    public LongFilter skillId() {
        if (skillId == null) {
            skillId = new LongFilter();
        }
        return skillId;
    }

    public void setSkillId(LongFilter skillId) {
        this.skillId = skillId;
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
        final EmployeeSkillsCriteria that = (EmployeeSkillsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(expertise, that.expertise) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(skillId, that.skillId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdat, updatedat, expertise, employeeId, skillId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeSkillsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (expertise != null ? "expertise=" + expertise + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (skillId != null ? "skillId=" + skillId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
