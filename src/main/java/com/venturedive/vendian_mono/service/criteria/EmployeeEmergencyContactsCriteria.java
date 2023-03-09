package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.EmployeeEmergencyContacts} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.EmployeeEmergencyContactsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-emergency-contacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeEmergencyContactsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fullname;

    private StringFilter relationship;

    private StringFilter contactno;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter employeeId;

    private Boolean distinct;

    public EmployeeEmergencyContactsCriteria() {}

    public EmployeeEmergencyContactsCriteria(EmployeeEmergencyContactsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fullname = other.fullname == null ? null : other.fullname.copy();
        this.relationship = other.relationship == null ? null : other.relationship.copy();
        this.contactno = other.contactno == null ? null : other.contactno.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeEmergencyContactsCriteria copy() {
        return new EmployeeEmergencyContactsCriteria(this);
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

    public StringFilter getFullname() {
        return fullname;
    }

    public StringFilter fullname() {
        if (fullname == null) {
            fullname = new StringFilter();
        }
        return fullname;
    }

    public void setFullname(StringFilter fullname) {
        this.fullname = fullname;
    }

    public StringFilter getRelationship() {
        return relationship;
    }

    public StringFilter relationship() {
        if (relationship == null) {
            relationship = new StringFilter();
        }
        return relationship;
    }

    public void setRelationship(StringFilter relationship) {
        this.relationship = relationship;
    }

    public StringFilter getContactno() {
        return contactno;
    }

    public StringFilter contactno() {
        if (contactno == null) {
            contactno = new StringFilter();
        }
        return contactno;
    }

    public void setContactno(StringFilter contactno) {
        this.contactno = contactno;
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
        final EmployeeEmergencyContactsCriteria that = (EmployeeEmergencyContactsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(fullname, that.fullname) &&
            Objects.equals(relationship, that.relationship) &&
            Objects.equals(contactno, that.contactno) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullname, relationship, contactno, createdat, updatedat, employeeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeEmergencyContactsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (fullname != null ? "fullname=" + fullname + ", " : "") +
            (relationship != null ? "relationship=" + relationship + ", " : "") +
            (contactno != null ? "contactno=" + contactno + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
