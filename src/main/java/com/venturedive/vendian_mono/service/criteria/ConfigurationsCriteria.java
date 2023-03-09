package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Configurations} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.ConfigurationsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /configurations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConfigurationsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter group;

    private IntegerFilter intValue;

    private StringFilter stringValue;

    private DoubleFilter doubleValue;

    private InstantFilter dateValue;

    private StringFilter jsonValue;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter deletedAt;

    private IntegerFilter version;

    private Boolean distinct;

    public ConfigurationsCriteria() {}

    public ConfigurationsCriteria(ConfigurationsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.group = other.group == null ? null : other.group.copy();
        this.intValue = other.intValue == null ? null : other.intValue.copy();
        this.stringValue = other.stringValue == null ? null : other.stringValue.copy();
        this.doubleValue = other.doubleValue == null ? null : other.doubleValue.copy();
        this.dateValue = other.dateValue == null ? null : other.dateValue.copy();
        this.jsonValue = other.jsonValue == null ? null : other.jsonValue.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ConfigurationsCriteria copy() {
        return new ConfigurationsCriteria(this);
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

    public IntegerFilter getIntValue() {
        return intValue;
    }

    public IntegerFilter intValue() {
        if (intValue == null) {
            intValue = new IntegerFilter();
        }
        return intValue;
    }

    public void setIntValue(IntegerFilter intValue) {
        this.intValue = intValue;
    }

    public StringFilter getStringValue() {
        return stringValue;
    }

    public StringFilter stringValue() {
        if (stringValue == null) {
            stringValue = new StringFilter();
        }
        return stringValue;
    }

    public void setStringValue(StringFilter stringValue) {
        this.stringValue = stringValue;
    }

    public DoubleFilter getDoubleValue() {
        return doubleValue;
    }

    public DoubleFilter doubleValue() {
        if (doubleValue == null) {
            doubleValue = new DoubleFilter();
        }
        return doubleValue;
    }

    public void setDoubleValue(DoubleFilter doubleValue) {
        this.doubleValue = doubleValue;
    }

    public InstantFilter getDateValue() {
        return dateValue;
    }

    public InstantFilter dateValue() {
        if (dateValue == null) {
            dateValue = new InstantFilter();
        }
        return dateValue;
    }

    public void setDateValue(InstantFilter dateValue) {
        this.dateValue = dateValue;
    }

    public StringFilter getJsonValue() {
        return jsonValue;
    }

    public StringFilter jsonValue() {
        if (jsonValue == null) {
            jsonValue = new StringFilter();
        }
        return jsonValue;
    }

    public void setJsonValue(StringFilter jsonValue) {
        this.jsonValue = jsonValue;
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
        final ConfigurationsCriteria that = (ConfigurationsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(group, that.group) &&
            Objects.equals(intValue, that.intValue) &&
            Objects.equals(stringValue, that.stringValue) &&
            Objects.equals(doubleValue, that.doubleValue) &&
            Objects.equals(dateValue, that.dateValue) &&
            Objects.equals(jsonValue, that.jsonValue) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(version, that.version) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            group,
            intValue,
            stringValue,
            doubleValue,
            dateValue,
            jsonValue,
            createdAt,
            updatedAt,
            deletedAt,
            version,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfigurationsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (group != null ? "group=" + group + ", " : "") +
            (intValue != null ? "intValue=" + intValue + ", " : "") +
            (stringValue != null ? "stringValue=" + stringValue + ", " : "") +
            (doubleValue != null ? "doubleValue=" + doubleValue + ", " : "") +
            (dateValue != null ? "dateValue=" + dateValue + ", " : "") +
            (jsonValue != null ? "jsonValue=" + jsonValue + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
