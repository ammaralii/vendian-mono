package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Configs} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.ConfigsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /configs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConfigsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter group;

    private IntegerFilter intvalue;

    private StringFilter stringvalue;

    private BigDecimalFilter decimalvalue;

    private StringFilter jsonvalue;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private Boolean distinct;

    public ConfigsCriteria() {}

    public ConfigsCriteria(ConfigsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.group = other.group == null ? null : other.group.copy();
        this.intvalue = other.intvalue == null ? null : other.intvalue.copy();
        this.stringvalue = other.stringvalue == null ? null : other.stringvalue.copy();
        this.decimalvalue = other.decimalvalue == null ? null : other.decimalvalue.copy();
        this.jsonvalue = other.jsonvalue == null ? null : other.jsonvalue.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ConfigsCriteria copy() {
        return new ConfigsCriteria(this);
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

    public IntegerFilter getIntvalue() {
        return intvalue;
    }

    public IntegerFilter intvalue() {
        if (intvalue == null) {
            intvalue = new IntegerFilter();
        }
        return intvalue;
    }

    public void setIntvalue(IntegerFilter intvalue) {
        this.intvalue = intvalue;
    }

    public StringFilter getStringvalue() {
        return stringvalue;
    }

    public StringFilter stringvalue() {
        if (stringvalue == null) {
            stringvalue = new StringFilter();
        }
        return stringvalue;
    }

    public void setStringvalue(StringFilter stringvalue) {
        this.stringvalue = stringvalue;
    }

    public BigDecimalFilter getDecimalvalue() {
        return decimalvalue;
    }

    public BigDecimalFilter decimalvalue() {
        if (decimalvalue == null) {
            decimalvalue = new BigDecimalFilter();
        }
        return decimalvalue;
    }

    public void setDecimalvalue(BigDecimalFilter decimalvalue) {
        this.decimalvalue = decimalvalue;
    }

    public StringFilter getJsonvalue() {
        return jsonvalue;
    }

    public StringFilter jsonvalue() {
        if (jsonvalue == null) {
            jsonvalue = new StringFilter();
        }
        return jsonvalue;
    }

    public void setJsonvalue(StringFilter jsonvalue) {
        this.jsonvalue = jsonvalue;
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
        final ConfigsCriteria that = (ConfigsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(group, that.group) &&
            Objects.equals(intvalue, that.intvalue) &&
            Objects.equals(stringvalue, that.stringvalue) &&
            Objects.equals(decimalvalue, that.decimalvalue) &&
            Objects.equals(jsonvalue, that.jsonvalue) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, group, intvalue, stringvalue, decimalvalue, jsonvalue, createdat, updatedat, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfigsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (group != null ? "group=" + group + ", " : "") +
            (intvalue != null ? "intvalue=" + intvalue + ", " : "") +
            (stringvalue != null ? "stringvalue=" + stringvalue + ", " : "") +
            (decimalvalue != null ? "decimalvalue=" + decimalvalue + ", " : "") +
            (jsonvalue != null ? "jsonvalue=" + jsonvalue + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
