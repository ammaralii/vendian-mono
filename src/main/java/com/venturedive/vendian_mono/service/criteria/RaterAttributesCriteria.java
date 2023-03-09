package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.RaterAttributes} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.RaterAttributesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rater-attributes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RaterAttributesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter title;

    private StringFilter description;

    private InstantFilter effdate;

    private InstantFilter enddate;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter raterattributemappingsRaterattributeId;

    private Boolean distinct;

    public RaterAttributesCriteria() {}

    public RaterAttributesCriteria(RaterAttributesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.effdate = other.effdate == null ? null : other.effdate.copy();
        this.enddate = other.enddate == null ? null : other.enddate.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.raterattributemappingsRaterattributeId =
            other.raterattributemappingsRaterattributeId == null ? null : other.raterattributemappingsRaterattributeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RaterAttributesCriteria copy() {
        return new RaterAttributesCriteria(this);
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

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public InstantFilter getEffdate() {
        return effdate;
    }

    public InstantFilter effdate() {
        if (effdate == null) {
            effdate = new InstantFilter();
        }
        return effdate;
    }

    public void setEffdate(InstantFilter effdate) {
        this.effdate = effdate;
    }

    public InstantFilter getEnddate() {
        return enddate;
    }

    public InstantFilter enddate() {
        if (enddate == null) {
            enddate = new InstantFilter();
        }
        return enddate;
    }

    public void setEnddate(InstantFilter enddate) {
        this.enddate = enddate;
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

    public LongFilter getRaterattributemappingsRaterattributeId() {
        return raterattributemappingsRaterattributeId;
    }

    public LongFilter raterattributemappingsRaterattributeId() {
        if (raterattributemappingsRaterattributeId == null) {
            raterattributemappingsRaterattributeId = new LongFilter();
        }
        return raterattributemappingsRaterattributeId;
    }

    public void setRaterattributemappingsRaterattributeId(LongFilter raterattributemappingsRaterattributeId) {
        this.raterattributemappingsRaterattributeId = raterattributemappingsRaterattributeId;
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
        final RaterAttributesCriteria that = (RaterAttributesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(effdate, that.effdate) &&
            Objects.equals(enddate, that.enddate) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(raterattributemappingsRaterattributeId, that.raterattributemappingsRaterattributeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            title,
            description,
            effdate,
            enddate,
            createdat,
            updatedat,
            raterattributemappingsRaterattributeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RaterAttributesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (effdate != null ? "effdate=" + effdate + ", " : "") +
            (enddate != null ? "enddate=" + enddate + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (raterattributemappingsRaterattributeId != null ? "raterattributemappingsRaterattributeId=" + raterattributemappingsRaterattributeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
