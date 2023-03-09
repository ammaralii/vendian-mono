package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.AttributePermissions} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.AttributePermissionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /attribute-permissions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttributePermissionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter method;

    private StringFilter route;

    private StringFilter responsepermissions;

    private StringFilter requestpermissions;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private Boolean distinct;

    public AttributePermissionsCriteria() {}

    public AttributePermissionsCriteria(AttributePermissionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.method = other.method == null ? null : other.method.copy();
        this.route = other.route == null ? null : other.route.copy();
        this.responsepermissions = other.responsepermissions == null ? null : other.responsepermissions.copy();
        this.requestpermissions = other.requestpermissions == null ? null : other.requestpermissions.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AttributePermissionsCriteria copy() {
        return new AttributePermissionsCriteria(this);
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

    public StringFilter getMethod() {
        return method;
    }

    public StringFilter method() {
        if (method == null) {
            method = new StringFilter();
        }
        return method;
    }

    public void setMethod(StringFilter method) {
        this.method = method;
    }

    public StringFilter getRoute() {
        return route;
    }

    public StringFilter route() {
        if (route == null) {
            route = new StringFilter();
        }
        return route;
    }

    public void setRoute(StringFilter route) {
        this.route = route;
    }

    public StringFilter getResponsepermissions() {
        return responsepermissions;
    }

    public StringFilter responsepermissions() {
        if (responsepermissions == null) {
            responsepermissions = new StringFilter();
        }
        return responsepermissions;
    }

    public void setResponsepermissions(StringFilter responsepermissions) {
        this.responsepermissions = responsepermissions;
    }

    public StringFilter getRequestpermissions() {
        return requestpermissions;
    }

    public StringFilter requestpermissions() {
        if (requestpermissions == null) {
            requestpermissions = new StringFilter();
        }
        return requestpermissions;
    }

    public void setRequestpermissions(StringFilter requestpermissions) {
        this.requestpermissions = requestpermissions;
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
        final AttributePermissionsCriteria that = (AttributePermissionsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(method, that.method) &&
            Objects.equals(route, that.route) &&
            Objects.equals(responsepermissions, that.responsepermissions) &&
            Objects.equals(requestpermissions, that.requestpermissions) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, method, route, responsepermissions, requestpermissions, createdat, updatedat, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttributePermissionsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (method != null ? "method=" + method + ", " : "") +
            (route != null ? "route=" + route + ", " : "") +
            (responsepermissions != null ? "responsepermissions=" + responsepermissions + ", " : "") +
            (requestpermissions != null ? "requestpermissions=" + requestpermissions + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
