package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Projects} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.ProjectsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /projects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter isactive;

    private BooleanFilter isdelete;

    private InstantFilter startdate;

    private InstantFilter enddate;

    private StringFilter colorcode;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private IntegerFilter deliverymanagerid;

    private IntegerFilter architectid;

    private IntegerFilter isdeleted;

    private IntegerFilter approvedresources;

    private InstantFilter releaseat;

    private LongFilter projectmanagerId;

    private LongFilter businessunitId;

    private LongFilter employeeprojectsProjectId;

    private LongFilter interviewsProjectId;

    private LongFilter projectcyclesProjectId;

    private LongFilter projectleadershipProjectId;

    private LongFilter questionsProjectId;

    private LongFilter questionsfrequencyperclienttrackProjectId;

    private LongFilter worklogdetailsProjectId;

    private LongFilter zemployeeprojectsProjectId;

    private Boolean distinct;

    public ProjectsCriteria() {}

    public ProjectsCriteria(ProjectsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.isactive = other.isactive == null ? null : other.isactive.copy();
        this.isdelete = other.isdelete == null ? null : other.isdelete.copy();
        this.startdate = other.startdate == null ? null : other.startdate.copy();
        this.enddate = other.enddate == null ? null : other.enddate.copy();
        this.colorcode = other.colorcode == null ? null : other.colorcode.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.deliverymanagerid = other.deliverymanagerid == null ? null : other.deliverymanagerid.copy();
        this.architectid = other.architectid == null ? null : other.architectid.copy();
        this.isdeleted = other.isdeleted == null ? null : other.isdeleted.copy();
        this.approvedresources = other.approvedresources == null ? null : other.approvedresources.copy();
        this.releaseat = other.releaseat == null ? null : other.releaseat.copy();
        this.projectmanagerId = other.projectmanagerId == null ? null : other.projectmanagerId.copy();
        this.businessunitId = other.businessunitId == null ? null : other.businessunitId.copy();
        this.employeeprojectsProjectId = other.employeeprojectsProjectId == null ? null : other.employeeprojectsProjectId.copy();
        this.interviewsProjectId = other.interviewsProjectId == null ? null : other.interviewsProjectId.copy();
        this.projectcyclesProjectId = other.projectcyclesProjectId == null ? null : other.projectcyclesProjectId.copy();
        this.projectleadershipProjectId = other.projectleadershipProjectId == null ? null : other.projectleadershipProjectId.copy();
        this.questionsProjectId = other.questionsProjectId == null ? null : other.questionsProjectId.copy();
        this.questionsfrequencyperclienttrackProjectId =
            other.questionsfrequencyperclienttrackProjectId == null ? null : other.questionsfrequencyperclienttrackProjectId.copy();
        this.worklogdetailsProjectId = other.worklogdetailsProjectId == null ? null : other.worklogdetailsProjectId.copy();
        this.zemployeeprojectsProjectId = other.zemployeeprojectsProjectId == null ? null : other.zemployeeprojectsProjectId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProjectsCriteria copy() {
        return new ProjectsCriteria(this);
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

    public BooleanFilter getIsdelete() {
        return isdelete;
    }

    public BooleanFilter isdelete() {
        if (isdelete == null) {
            isdelete = new BooleanFilter();
        }
        return isdelete;
    }

    public void setIsdelete(BooleanFilter isdelete) {
        this.isdelete = isdelete;
    }

    public InstantFilter getStartdate() {
        return startdate;
    }

    public InstantFilter startdate() {
        if (startdate == null) {
            startdate = new InstantFilter();
        }
        return startdate;
    }

    public void setStartdate(InstantFilter startdate) {
        this.startdate = startdate;
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

    public StringFilter getColorcode() {
        return colorcode;
    }

    public StringFilter colorcode() {
        if (colorcode == null) {
            colorcode = new StringFilter();
        }
        return colorcode;
    }

    public void setColorcode(StringFilter colorcode) {
        this.colorcode = colorcode;
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

    public IntegerFilter getDeliverymanagerid() {
        return deliverymanagerid;
    }

    public IntegerFilter deliverymanagerid() {
        if (deliverymanagerid == null) {
            deliverymanagerid = new IntegerFilter();
        }
        return deliverymanagerid;
    }

    public void setDeliverymanagerid(IntegerFilter deliverymanagerid) {
        this.deliverymanagerid = deliverymanagerid;
    }

    public IntegerFilter getArchitectid() {
        return architectid;
    }

    public IntegerFilter architectid() {
        if (architectid == null) {
            architectid = new IntegerFilter();
        }
        return architectid;
    }

    public void setArchitectid(IntegerFilter architectid) {
        this.architectid = architectid;
    }

    public IntegerFilter getIsdeleted() {
        return isdeleted;
    }

    public IntegerFilter isdeleted() {
        if (isdeleted == null) {
            isdeleted = new IntegerFilter();
        }
        return isdeleted;
    }

    public void setIsdeleted(IntegerFilter isdeleted) {
        this.isdeleted = isdeleted;
    }

    public IntegerFilter getApprovedresources() {
        return approvedresources;
    }

    public IntegerFilter approvedresources() {
        if (approvedresources == null) {
            approvedresources = new IntegerFilter();
        }
        return approvedresources;
    }

    public void setApprovedresources(IntegerFilter approvedresources) {
        this.approvedresources = approvedresources;
    }

    public InstantFilter getReleaseat() {
        return releaseat;
    }

    public InstantFilter releaseat() {
        if (releaseat == null) {
            releaseat = new InstantFilter();
        }
        return releaseat;
    }

    public void setReleaseat(InstantFilter releaseat) {
        this.releaseat = releaseat;
    }

    public LongFilter getProjectmanagerId() {
        return projectmanagerId;
    }

    public LongFilter projectmanagerId() {
        if (projectmanagerId == null) {
            projectmanagerId = new LongFilter();
        }
        return projectmanagerId;
    }

    public void setProjectmanagerId(LongFilter projectmanagerId) {
        this.projectmanagerId = projectmanagerId;
    }

    public LongFilter getBusinessunitId() {
        return businessunitId;
    }

    public LongFilter businessunitId() {
        if (businessunitId == null) {
            businessunitId = new LongFilter();
        }
        return businessunitId;
    }

    public void setBusinessunitId(LongFilter businessunitId) {
        this.businessunitId = businessunitId;
    }

    public LongFilter getEmployeeprojectsProjectId() {
        return employeeprojectsProjectId;
    }

    public LongFilter employeeprojectsProjectId() {
        if (employeeprojectsProjectId == null) {
            employeeprojectsProjectId = new LongFilter();
        }
        return employeeprojectsProjectId;
    }

    public void setEmployeeprojectsProjectId(LongFilter employeeprojectsProjectId) {
        this.employeeprojectsProjectId = employeeprojectsProjectId;
    }

    public LongFilter getInterviewsProjectId() {
        return interviewsProjectId;
    }

    public LongFilter interviewsProjectId() {
        if (interviewsProjectId == null) {
            interviewsProjectId = new LongFilter();
        }
        return interviewsProjectId;
    }

    public void setInterviewsProjectId(LongFilter interviewsProjectId) {
        this.interviewsProjectId = interviewsProjectId;
    }

    public LongFilter getProjectcyclesProjectId() {
        return projectcyclesProjectId;
    }

    public LongFilter projectcyclesProjectId() {
        if (projectcyclesProjectId == null) {
            projectcyclesProjectId = new LongFilter();
        }
        return projectcyclesProjectId;
    }

    public void setProjectcyclesProjectId(LongFilter projectcyclesProjectId) {
        this.projectcyclesProjectId = projectcyclesProjectId;
    }

    public LongFilter getProjectleadershipProjectId() {
        return projectleadershipProjectId;
    }

    public LongFilter projectleadershipProjectId() {
        if (projectleadershipProjectId == null) {
            projectleadershipProjectId = new LongFilter();
        }
        return projectleadershipProjectId;
    }

    public void setProjectleadershipProjectId(LongFilter projectleadershipProjectId) {
        this.projectleadershipProjectId = projectleadershipProjectId;
    }

    public LongFilter getQuestionsProjectId() {
        return questionsProjectId;
    }

    public LongFilter questionsProjectId() {
        if (questionsProjectId == null) {
            questionsProjectId = new LongFilter();
        }
        return questionsProjectId;
    }

    public void setQuestionsProjectId(LongFilter questionsProjectId) {
        this.questionsProjectId = questionsProjectId;
    }

    public LongFilter getQuestionsfrequencyperclienttrackProjectId() {
        return questionsfrequencyperclienttrackProjectId;
    }

    public LongFilter questionsfrequencyperclienttrackProjectId() {
        if (questionsfrequencyperclienttrackProjectId == null) {
            questionsfrequencyperclienttrackProjectId = new LongFilter();
        }
        return questionsfrequencyperclienttrackProjectId;
    }

    public void setQuestionsfrequencyperclienttrackProjectId(LongFilter questionsfrequencyperclienttrackProjectId) {
        this.questionsfrequencyperclienttrackProjectId = questionsfrequencyperclienttrackProjectId;
    }

    public LongFilter getWorklogdetailsProjectId() {
        return worklogdetailsProjectId;
    }

    public LongFilter worklogdetailsProjectId() {
        if (worklogdetailsProjectId == null) {
            worklogdetailsProjectId = new LongFilter();
        }
        return worklogdetailsProjectId;
    }

    public void setWorklogdetailsProjectId(LongFilter worklogdetailsProjectId) {
        this.worklogdetailsProjectId = worklogdetailsProjectId;
    }

    public LongFilter getZemployeeprojectsProjectId() {
        return zemployeeprojectsProjectId;
    }

    public LongFilter zemployeeprojectsProjectId() {
        if (zemployeeprojectsProjectId == null) {
            zemployeeprojectsProjectId = new LongFilter();
        }
        return zemployeeprojectsProjectId;
    }

    public void setZemployeeprojectsProjectId(LongFilter zemployeeprojectsProjectId) {
        this.zemployeeprojectsProjectId = zemployeeprojectsProjectId;
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
        final ProjectsCriteria that = (ProjectsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(isactive, that.isactive) &&
            Objects.equals(isdelete, that.isdelete) &&
            Objects.equals(startdate, that.startdate) &&
            Objects.equals(enddate, that.enddate) &&
            Objects.equals(colorcode, that.colorcode) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(deliverymanagerid, that.deliverymanagerid) &&
            Objects.equals(architectid, that.architectid) &&
            Objects.equals(isdeleted, that.isdeleted) &&
            Objects.equals(approvedresources, that.approvedresources) &&
            Objects.equals(releaseat, that.releaseat) &&
            Objects.equals(projectmanagerId, that.projectmanagerId) &&
            Objects.equals(businessunitId, that.businessunitId) &&
            Objects.equals(employeeprojectsProjectId, that.employeeprojectsProjectId) &&
            Objects.equals(interviewsProjectId, that.interviewsProjectId) &&
            Objects.equals(projectcyclesProjectId, that.projectcyclesProjectId) &&
            Objects.equals(projectleadershipProjectId, that.projectleadershipProjectId) &&
            Objects.equals(questionsProjectId, that.questionsProjectId) &&
            Objects.equals(questionsfrequencyperclienttrackProjectId, that.questionsfrequencyperclienttrackProjectId) &&
            Objects.equals(worklogdetailsProjectId, that.worklogdetailsProjectId) &&
            Objects.equals(zemployeeprojectsProjectId, that.zemployeeprojectsProjectId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            isactive,
            isdelete,
            startdate,
            enddate,
            colorcode,
            createdat,
            updatedat,
            deliverymanagerid,
            architectid,
            isdeleted,
            approvedresources,
            releaseat,
            projectmanagerId,
            businessunitId,
            employeeprojectsProjectId,
            interviewsProjectId,
            projectcyclesProjectId,
            projectleadershipProjectId,
            questionsProjectId,
            questionsfrequencyperclienttrackProjectId,
            worklogdetailsProjectId,
            zemployeeprojectsProjectId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (isactive != null ? "isactive=" + isactive + ", " : "") +
            (isdelete != null ? "isdelete=" + isdelete + ", " : "") +
            (startdate != null ? "startdate=" + startdate + ", " : "") +
            (enddate != null ? "enddate=" + enddate + ", " : "") +
            (colorcode != null ? "colorcode=" + colorcode + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (deliverymanagerid != null ? "deliverymanagerid=" + deliverymanagerid + ", " : "") +
            (architectid != null ? "architectid=" + architectid + ", " : "") +
            (isdeleted != null ? "isdeleted=" + isdeleted + ", " : "") +
            (approvedresources != null ? "approvedresources=" + approvedresources + ", " : "") +
            (releaseat != null ? "releaseat=" + releaseat + ", " : "") +
            (projectmanagerId != null ? "projectmanagerId=" + projectmanagerId + ", " : "") +
            (businessunitId != null ? "businessunitId=" + businessunitId + ", " : "") +
            (employeeprojectsProjectId != null ? "employeeprojectsProjectId=" + employeeprojectsProjectId + ", " : "") +
            (interviewsProjectId != null ? "interviewsProjectId=" + interviewsProjectId + ", " : "") +
            (projectcyclesProjectId != null ? "projectcyclesProjectId=" + projectcyclesProjectId + ", " : "") +
            (projectleadershipProjectId != null ? "projectleadershipProjectId=" + projectleadershipProjectId + ", " : "") +
            (questionsProjectId != null ? "questionsProjectId=" + questionsProjectId + ", " : "") +
            (questionsfrequencyperclienttrackProjectId != null ? "questionsfrequencyperclienttrackProjectId=" + questionsfrequencyperclienttrackProjectId + ", " : "") +
            (worklogdetailsProjectId != null ? "worklogdetailsProjectId=" + worklogdetailsProjectId + ", " : "") +
            (zemployeeprojectsProjectId != null ? "zemployeeprojectsProjectId=" + zemployeeprojectsProjectId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
