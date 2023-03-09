package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Permissions} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.PermissionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /permissions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PermissionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter groupName;

    private BooleanFilter isactive;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter rolepermissionsPermissionId;

    private Boolean distinct;

    public PermissionsCriteria() {}

    public PermissionsCriteria(PermissionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.groupName = other.groupName == null ? null : other.groupName.copy();
        this.isactive = other.isactive == null ? null : other.isactive.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.rolepermissionsPermissionId = other.rolepermissionsPermissionId == null ? null : other.rolepermissionsPermissionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PermissionsCriteria copy() {
        return new PermissionsCriteria(this);
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

    public StringFilter getGroupName() {
        return groupName;
    }

    public StringFilter groupName() {
        if (groupName == null) {
            groupName = new StringFilter();
        }
        return groupName;
    }

    public void setGroupName(StringFilter groupName) {
        this.groupName = groupName;
    }

    public BooleanFilter getIsactive() {
        return isactive;
    }

    public BooleanFilter isactive() {
        if (isactive == null) {
            isactive = new BooleanFilter();
        }
        return isactive;
    }

    public void setIsactive(BooleanFilter isactive) {
        this.isactive = isactive;
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

    public LongFilter getRolepermissionsPermissionId() {
        return rolepermissionsPermissionId;
    }

    public LongFilter rolepermissionsPermissionId() {
        if (rolepermissionsPermissionId == null) {
            rolepermissionsPermissionId = new LongFilter();
        }
        return rolepermissionsPermissionId;
    }

    public void setRolepermissionsPermissionId(LongFilter rolepermissionsPermissionId) {
        this.rolepermissionsPermissionId = rolepermissionsPermissionId;
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
        final PermissionsCriteria that = (PermissionsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(groupName, that.groupName) &&
            Objects.equals(isactive, that.isactive) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(rolepermissionsPermissionId, that.rolepermissionsPermissionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, groupName, isactive, createdat, updatedat, rolepermissionsPermissionId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PermissionsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (groupName != null ? "groupName=" + groupName + ", " : "") +
            (isactive != null ? "isactive=" + isactive + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (rolepermissionsPermissionId != null ? "rolepermissionsPermissionId=" + rolepermissionsPermissionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
