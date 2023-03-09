package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Deals} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.DealsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /deals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DealsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter dealnumber;

    private StringFilter dealname;

    private StringFilter businessunit;

    private StringFilter clientname;

    private StringFilter dealowner;

    private StringFilter proposaltype;

    private IntegerFilter projectid;

    private InstantFilter expectedstartdate;

    private StringFilter stage;

    private DoubleFilter probability;

    private DoubleFilter projectduration;

    private StringFilter type;

    private StringFilter status;

    private InstantFilter closedat;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private InstantFilter deletedat;

    private BooleanFilter resourcingenteredinvendians;

    private LongFilter dealrequirementsDealId;

    private Boolean distinct;

    public DealsCriteria() {}

    public DealsCriteria(DealsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dealnumber = other.dealnumber == null ? null : other.dealnumber.copy();
        this.dealname = other.dealname == null ? null : other.dealname.copy();
        this.businessunit = other.businessunit == null ? null : other.businessunit.copy();
        this.clientname = other.clientname == null ? null : other.clientname.copy();
        this.dealowner = other.dealowner == null ? null : other.dealowner.copy();
        this.proposaltype = other.proposaltype == null ? null : other.proposaltype.copy();
        this.projectid = other.projectid == null ? null : other.projectid.copy();
        this.expectedstartdate = other.expectedstartdate == null ? null : other.expectedstartdate.copy();
        this.stage = other.stage == null ? null : other.stage.copy();
        this.probability = other.probability == null ? null : other.probability.copy();
        this.projectduration = other.projectduration == null ? null : other.projectduration.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.closedat = other.closedat == null ? null : other.closedat.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.deletedat = other.deletedat == null ? null : other.deletedat.copy();
        this.resourcingenteredinvendians = other.resourcingenteredinvendians == null ? null : other.resourcingenteredinvendians.copy();
        this.dealrequirementsDealId = other.dealrequirementsDealId == null ? null : other.dealrequirementsDealId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DealsCriteria copy() {
        return new DealsCriteria(this);
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

    public StringFilter getDealnumber() {
        return dealnumber;
    }

    public StringFilter dealnumber() {
        if (dealnumber == null) {
            dealnumber = new StringFilter();
        }
        return dealnumber;
    }

    public void setDealnumber(StringFilter dealnumber) {
        this.dealnumber = dealnumber;
    }

    public StringFilter getDealname() {
        return dealname;
    }

    public StringFilter dealname() {
        if (dealname == null) {
            dealname = new StringFilter();
        }
        return dealname;
    }

    public void setDealname(StringFilter dealname) {
        this.dealname = dealname;
    }

    public StringFilter getBusinessunit() {
        return businessunit;
    }

    public StringFilter businessunit() {
        if (businessunit == null) {
            businessunit = new StringFilter();
        }
        return businessunit;
    }

    public void setBusinessunit(StringFilter businessunit) {
        this.businessunit = businessunit;
    }

    public StringFilter getClientname() {
        return clientname;
    }

    public StringFilter clientname() {
        if (clientname == null) {
            clientname = new StringFilter();
        }
        return clientname;
    }

    public void setClientname(StringFilter clientname) {
        this.clientname = clientname;
    }

    public StringFilter getDealowner() {
        return dealowner;
    }

    public StringFilter dealowner() {
        if (dealowner == null) {
            dealowner = new StringFilter();
        }
        return dealowner;
    }

    public void setDealowner(StringFilter dealowner) {
        this.dealowner = dealowner;
    }

    public StringFilter getProposaltype() {
        return proposaltype;
    }

    public StringFilter proposaltype() {
        if (proposaltype == null) {
            proposaltype = new StringFilter();
        }
        return proposaltype;
    }

    public void setProposaltype(StringFilter proposaltype) {
        this.proposaltype = proposaltype;
    }

    public IntegerFilter getProjectid() {
        return projectid;
    }

    public IntegerFilter projectid() {
        if (projectid == null) {
            projectid = new IntegerFilter();
        }
        return projectid;
    }

    public void setProjectid(IntegerFilter projectid) {
        this.projectid = projectid;
    }

    public InstantFilter getExpectedstartdate() {
        return expectedstartdate;
    }

    public InstantFilter expectedstartdate() {
        if (expectedstartdate == null) {
            expectedstartdate = new InstantFilter();
        }
        return expectedstartdate;
    }

    public void setExpectedstartdate(InstantFilter expectedstartdate) {
        this.expectedstartdate = expectedstartdate;
    }

    public StringFilter getStage() {
        return stage;
    }

    public StringFilter stage() {
        if (stage == null) {
            stage = new StringFilter();
        }
        return stage;
    }

    public void setStage(StringFilter stage) {
        this.stage = stage;
    }

    public DoubleFilter getProbability() {
        return probability;
    }

    public DoubleFilter probability() {
        if (probability == null) {
            probability = new DoubleFilter();
        }
        return probability;
    }

    public void setProbability(DoubleFilter probability) {
        this.probability = probability;
    }

    public DoubleFilter getProjectduration() {
        return projectduration;
    }

    public DoubleFilter projectduration() {
        if (projectduration == null) {
            projectduration = new DoubleFilter();
        }
        return projectduration;
    }

    public void setProjectduration(DoubleFilter projectduration) {
        this.projectduration = projectduration;
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

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public InstantFilter getClosedat() {
        return closedat;
    }

    public InstantFilter closedat() {
        if (closedat == null) {
            closedat = new InstantFilter();
        }
        return closedat;
    }

    public void setClosedat(InstantFilter closedat) {
        this.closedat = closedat;
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

    public InstantFilter getDeletedat() {
        return deletedat;
    }

    public InstantFilter deletedat() {
        if (deletedat == null) {
            deletedat = new InstantFilter();
        }
        return deletedat;
    }

    public void setDeletedat(InstantFilter deletedat) {
        this.deletedat = deletedat;
    }

    public BooleanFilter getResourcingenteredinvendians() {
        return resourcingenteredinvendians;
    }

    public BooleanFilter resourcingenteredinvendians() {
        if (resourcingenteredinvendians == null) {
            resourcingenteredinvendians = new BooleanFilter();
        }
        return resourcingenteredinvendians;
    }

    public void setResourcingenteredinvendians(BooleanFilter resourcingenteredinvendians) {
        this.resourcingenteredinvendians = resourcingenteredinvendians;
    }

    public LongFilter getDealrequirementsDealId() {
        return dealrequirementsDealId;
    }

    public LongFilter dealrequirementsDealId() {
        if (dealrequirementsDealId == null) {
            dealrequirementsDealId = new LongFilter();
        }
        return dealrequirementsDealId;
    }

    public void setDealrequirementsDealId(LongFilter dealrequirementsDealId) {
        this.dealrequirementsDealId = dealrequirementsDealId;
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
        final DealsCriteria that = (DealsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dealnumber, that.dealnumber) &&
            Objects.equals(dealname, that.dealname) &&
            Objects.equals(businessunit, that.businessunit) &&
            Objects.equals(clientname, that.clientname) &&
            Objects.equals(dealowner, that.dealowner) &&
            Objects.equals(proposaltype, that.proposaltype) &&
            Objects.equals(projectid, that.projectid) &&
            Objects.equals(expectedstartdate, that.expectedstartdate) &&
            Objects.equals(stage, that.stage) &&
            Objects.equals(probability, that.probability) &&
            Objects.equals(projectduration, that.projectduration) &&
            Objects.equals(type, that.type) &&
            Objects.equals(status, that.status) &&
            Objects.equals(closedat, that.closedat) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(deletedat, that.deletedat) &&
            Objects.equals(resourcingenteredinvendians, that.resourcingenteredinvendians) &&
            Objects.equals(dealrequirementsDealId, that.dealrequirementsDealId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            dealnumber,
            dealname,
            businessunit,
            clientname,
            dealowner,
            proposaltype,
            projectid,
            expectedstartdate,
            stage,
            probability,
            projectduration,
            type,
            status,
            closedat,
            createdat,
            updatedat,
            deletedat,
            resourcingenteredinvendians,
            dealrequirementsDealId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dealnumber != null ? "dealnumber=" + dealnumber + ", " : "") +
            (dealname != null ? "dealname=" + dealname + ", " : "") +
            (businessunit != null ? "businessunit=" + businessunit + ", " : "") +
            (clientname != null ? "clientname=" + clientname + ", " : "") +
            (dealowner != null ? "dealowner=" + dealowner + ", " : "") +
            (proposaltype != null ? "proposaltype=" + proposaltype + ", " : "") +
            (projectid != null ? "projectid=" + projectid + ", " : "") +
            (expectedstartdate != null ? "expectedstartdate=" + expectedstartdate + ", " : "") +
            (stage != null ? "stage=" + stage + ", " : "") +
            (probability != null ? "probability=" + probability + ", " : "") +
            (projectduration != null ? "projectduration=" + projectduration + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (closedat != null ? "closedat=" + closedat + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (deletedat != null ? "deletedat=" + deletedat + ", " : "") +
            (resourcingenteredinvendians != null ? "resourcingenteredinvendians=" + resourcingenteredinvendians + ", " : "") +
            (dealrequirementsDealId != null ? "dealrequirementsDealId=" + dealrequirementsDealId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
