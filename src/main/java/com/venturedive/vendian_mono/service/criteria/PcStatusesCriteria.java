package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.PcStatuses} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.PcStatusesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pc-statuses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PcStatusesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter group;

    private StringFilter systemKey;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private LongFilter pcratingsStatusId;

    private Boolean distinct;

    public PcStatusesCriteria() {}

    public PcStatusesCriteria(PcStatusesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.group = other.group == null ? null : other.group.copy();
        this.systemKey = other.systemKey == null ? null : other.systemKey.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.pcratingsStatusId = other.pcratingsStatusId == null ? null : other.pcratingsStatusId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PcStatusesCriteria copy() {
        return new PcStatusesCriteria(this);
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

    public StringFilter getGroup() {
        return group;
    }

    public StringFilter group() {
        if (group == null) {
            group = new StringFilter();
        }
        return group;
    }

    public void setGroup(StringFilter group) {
        this.group = group;
    }

    public StringFilter getSystemKey() {
        return systemKey;
    }

    public StringFilter systemKey() {
        if (systemKey == null) {
            systemKey = new StringFilter();
        }
        return systemKey;
    }

    public void setSystemKey(StringFilter systemKey) {
        this.systemKey = systemKey;
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

    public LongFilter getPcratingsStatusId() {
        return pcratingsStatusId;
    }

    public LongFilter pcratingsStatusId() {
        if (pcratingsStatusId == null) {
            pcratingsStatusId = new LongFilter();
        }
        return pcratingsStatusId;
    }

    public void setPcratingsStatusId(LongFilter pcratingsStatusId) {
        this.pcratingsStatusId = pcratingsStatusId;
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
        final PcStatusesCriteria that = (PcStatusesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(group, that.group) &&
            Objects.equals(systemKey, that.systemKey) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(pcratingsStatusId, that.pcratingsStatusId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, group, systemKey, createdAt, updatedAt, deletedAt, version, pcratingsStatusId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PcStatusesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (group != null ? "group=" + group + ", " : "") +
            (systemKey != null ? "systemKey=" + systemKey + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (pcratingsStatusId != null ? "pcratingsStatusId=" + pcratingsStatusId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
