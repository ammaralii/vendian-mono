package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Competencies} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.CompetenciesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /competencies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompetenciesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter employeejobinfoCompetencyId;

    private LongFilter employeesCompetencyId;

    private LongFilter tracksCompetencyId;

    private Boolean distinct;

    public CompetenciesCriteria() {}

    public CompetenciesCriteria(CompetenciesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.employeejobinfoCompetencyId = other.employeejobinfoCompetencyId == null ? null : other.employeejobinfoCompetencyId.copy();
        this.employeesCompetencyId = other.employeesCompetencyId == null ? null : other.employeesCompetencyId.copy();
        this.tracksCompetencyId = other.tracksCompetencyId == null ? null : other.tracksCompetencyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CompetenciesCriteria copy() {
        return new CompetenciesCriteria(this);
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

    public LongFilter getEmployeejobinfoCompetencyId() {
        return employeejobinfoCompetencyId;
    }

    public LongFilter employeejobinfoCompetencyId() {
        if (employeejobinfoCompetencyId == null) {
            employeejobinfoCompetencyId = new LongFilter();
        }
        return employeejobinfoCompetencyId;
    }

    public void setEmployeejobinfoCompetencyId(LongFilter employeejobinfoCompetencyId) {
        this.employeejobinfoCompetencyId = employeejobinfoCompetencyId;
    }

    public LongFilter getEmployeesCompetencyId() {
        return employeesCompetencyId;
    }

    public LongFilter employeesCompetencyId() {
        if (employeesCompetencyId == null) {
            employeesCompetencyId = new LongFilter();
        }
        return employeesCompetencyId;
    }

    public void setEmployeesCompetencyId(LongFilter employeesCompetencyId) {
        this.employeesCompetencyId = employeesCompetencyId;
    }

    public LongFilter getTracksCompetencyId() {
        return tracksCompetencyId;
    }

    public LongFilter tracksCompetencyId() {
        if (tracksCompetencyId == null) {
            tracksCompetencyId = new LongFilter();
        }
        return tracksCompetencyId;
    }

    public void setTracksCompetencyId(LongFilter tracksCompetencyId) {
        this.tracksCompetencyId = tracksCompetencyId;
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
        final CompetenciesCriteria that = (CompetenciesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(employeejobinfoCompetencyId, that.employeejobinfoCompetencyId) &&
            Objects.equals(employeesCompetencyId, that.employeesCompetencyId) &&
            Objects.equals(tracksCompetencyId, that.tracksCompetencyId) &&
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
            employeejobinfoCompetencyId,
            employeesCompetencyId,
            tracksCompetencyId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompetenciesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (employeejobinfoCompetencyId != null ? "employeejobinfoCompetencyId=" + employeejobinfoCompetencyId + ", " : "") +
            (employeesCompetencyId != null ? "employeesCompetencyId=" + employeesCompetencyId + ", " : "") +
            (tracksCompetencyId != null ? "tracksCompetencyId=" + tracksCompetencyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
