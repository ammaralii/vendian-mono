package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.DealResources} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.DealResourcesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /deal-resources?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DealResourcesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstname;

    private StringFilter lastname;

    private InstantFilter joiningdate;

    private IntegerFilter externalexpyears;

    private IntegerFilter externalexpmonths;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private StringFilter type;

    private BooleanFilter isactive;

    private LongFilter employeeId;

    private LongFilter dealrequirementmatchingresourcesResourceId;

    private LongFilter dealresourceskillsResourceId;

    private Boolean distinct;

    public DealResourcesCriteria() {}

    public DealResourcesCriteria(DealResourcesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstname = other.firstname == null ? null : other.firstname.copy();
        this.lastname = other.lastname == null ? null : other.lastname.copy();
        this.joiningdate = other.joiningdate == null ? null : other.joiningdate.copy();
        this.externalexpyears = other.externalexpyears == null ? null : other.externalexpyears.copy();
        this.externalexpmonths = other.externalexpmonths == null ? null : other.externalexpmonths.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.isactive = other.isactive == null ? null : other.isactive.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.dealrequirementmatchingresourcesResourceId =
            other.dealrequirementmatchingresourcesResourceId == null ? null : other.dealrequirementmatchingresourcesResourceId.copy();
        this.dealresourceskillsResourceId = other.dealresourceskillsResourceId == null ? null : other.dealresourceskillsResourceId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DealResourcesCriteria copy() {
        return new DealResourcesCriteria(this);
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

    public StringFilter getFirstname() {
        return firstname;
    }

    public StringFilter firstname() {
        if (firstname == null) {
            firstname = new StringFilter();
        }
        return firstname;
    }

    public void setFirstname(StringFilter firstname) {
        this.firstname = firstname;
    }

    public StringFilter getLastname() {
        return lastname;
    }

    public StringFilter lastname() {
        if (lastname == null) {
            lastname = new StringFilter();
        }
        return lastname;
    }

    public void setLastname(StringFilter lastname) {
        this.lastname = lastname;
    }

    public InstantFilter getJoiningdate() {
        return joiningdate;
    }

    public InstantFilter joiningdate() {
        if (joiningdate == null) {
            joiningdate = new InstantFilter();
        }
        return joiningdate;
    }

    public void setJoiningdate(InstantFilter joiningdate) {
        this.joiningdate = joiningdate;
    }

    public IntegerFilter getExternalexpyears() {
        return externalexpyears;
    }

    public IntegerFilter externalexpyears() {
        if (externalexpyears == null) {
            externalexpyears = new IntegerFilter();
        }
        return externalexpyears;
    }

    public void setExternalexpyears(IntegerFilter externalexpyears) {
        this.externalexpyears = externalexpyears;
    }

    public IntegerFilter getExternalexpmonths() {
        return externalexpmonths;
    }

    public IntegerFilter externalexpmonths() {
        if (externalexpmonths == null) {
            externalexpmonths = new IntegerFilter();
        }
        return externalexpmonths;
    }

    public void setExternalexpmonths(IntegerFilter externalexpmonths) {
        this.externalexpmonths = externalexpmonths;
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

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
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

    public LongFilter getDealrequirementmatchingresourcesResourceId() {
        return dealrequirementmatchingresourcesResourceId;
    }

    public LongFilter dealrequirementmatchingresourcesResourceId() {
        if (dealrequirementmatchingresourcesResourceId == null) {
            dealrequirementmatchingresourcesResourceId = new LongFilter();
        }
        return dealrequirementmatchingresourcesResourceId;
    }

    public void setDealrequirementmatchingresourcesResourceId(LongFilter dealrequirementmatchingresourcesResourceId) {
        this.dealrequirementmatchingresourcesResourceId = dealrequirementmatchingresourcesResourceId;
    }

    public LongFilter getDealresourceskillsResourceId() {
        return dealresourceskillsResourceId;
    }

    public LongFilter dealresourceskillsResourceId() {
        if (dealresourceskillsResourceId == null) {
            dealresourceskillsResourceId = new LongFilter();
        }
        return dealresourceskillsResourceId;
    }

    public void setDealresourceskillsResourceId(LongFilter dealresourceskillsResourceId) {
        this.dealresourceskillsResourceId = dealresourceskillsResourceId;
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
        final DealResourcesCriteria that = (DealResourcesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(firstname, that.firstname) &&
            Objects.equals(lastname, that.lastname) &&
            Objects.equals(joiningdate, that.joiningdate) &&
            Objects.equals(externalexpyears, that.externalexpyears) &&
            Objects.equals(externalexpmonths, that.externalexpmonths) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(type, that.type) &&
            Objects.equals(isactive, that.isactive) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(dealrequirementmatchingresourcesResourceId, that.dealrequirementmatchingresourcesResourceId) &&
            Objects.equals(dealresourceskillsResourceId, that.dealresourceskillsResourceId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            firstname,
            lastname,
            joiningdate,
            externalexpyears,
            externalexpmonths,
            createdat,
            updatedat,
            type,
            isactive,
            employeeId,
            dealrequirementmatchingresourcesResourceId,
            dealresourceskillsResourceId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealResourcesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (firstname != null ? "firstname=" + firstname + ", " : "") +
            (lastname != null ? "lastname=" + lastname + ", " : "") +
            (joiningdate != null ? "joiningdate=" + joiningdate + ", " : "") +
            (externalexpyears != null ? "externalexpyears=" + externalexpyears + ", " : "") +
            (externalexpmonths != null ? "externalexpmonths=" + externalexpmonths + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (isactive != null ? "isactive=" + isactive + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (dealrequirementmatchingresourcesResourceId != null ? "dealrequirementmatchingresourcesResourceId=" + dealrequirementmatchingresourcesResourceId + ", " : "") +
            (dealresourceskillsResourceId != null ? "dealresourceskillsResourceId=" + dealresourceskillsResourceId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
