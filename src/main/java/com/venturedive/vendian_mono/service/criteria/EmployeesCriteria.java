package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.Employees} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.EmployeesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstname;

    private StringFilter lastname;

    private StringFilter phonenumber;

    private InstantFilter dateofbirth;

    private StringFilter email;

    private StringFilter skype;

    private StringFilter employeeDesignation;

    private InstantFilter joiningdate;

    private StringFilter area;

    private InstantFilter leavingdate;

    private StringFilter notes;

    private BooleanFilter isactive;

    private StringFilter googleid;

    private StringFilter oracleid;

    private StringFilter deptt;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private StringFilter genderid;

    private BooleanFilter onprobation;

    private StringFilter employeeCompetency;

    private StringFilter resourcetype;

    private StringFilter grade;

    private StringFilter subgrade;

    private StringFilter imageurl;

    private InstantFilter resignationdate;

    private LocalDateFilter graduationdate;

    private LocalDateFilter careerstartdate;

    private IntegerFilter externalexpyears;

    private BigDecimalFilter externalexpmonths;

    private StringFilter placeofbirth;

    private InstantFilter hireddate;

    private InstantFilter lasttrackingupdate;

    private StringFilter middlename;

    private StringFilter mobilenumber;

    private StringFilter resignationtype;

    private StringFilter primaryreasonforleaving;

    private StringFilter secondaryreasonforleaving;

    private IntegerFilter noticeperiodduration;

    private IntegerFilter noticeperiodserved;

    private IntegerFilter probationperiodduration;

    private LongFilter locationId;

    private LongFilter roleId;

    private LongFilter managerId;

    private LongFilter leaveId;

    private LongFilter departmentId;

    private LongFilter businessunitId;

    private LongFilter divisionId;

    private LongFilter competencyId;

    private LongFilter employmentstatusId;

    private LongFilter employmenttypeId;

    private LongFilter designationId;

    private LongFilter claimrequestviewsEmployeeId;

    private LongFilter claimrequestsEmployeeId;

    private LongFilter dealresourcesEmployeeId;

    private LongFilter employeeaddressesEmployeeId;

    private LongFilter employeebirthdaysEmployeeId;

    private LongFilter employeecertificatesEmployeeId;

    private LongFilter employeecommentsCommenterId;

    private LongFilter employeecommentsEmployeeId;

    private LongFilter employeecompensationEmployeeId;

    private LongFilter employeecontactsEmployeeId;

    private LongFilter employeedetailsEmployeeId;

    private LongFilter employeedocumentsEmployeeId;

    private LongFilter employeeeducationEmployeeId;

    private LongFilter employeeemergencycontactsEmployeeId;

    private LongFilter employeefamilyinfoEmployeeId;

    private LongFilter employeejobinfoEmployeeId;

    private LongFilter employeejobinfoReviewmanagerId;

    private LongFilter employeejobinfoManagerId;

    private LongFilter employeeprofilehistorylogsEmployeeId;

    private LongFilter employeeprojectratingsProjectarchitectId;

    private LongFilter employeeprojectratingsProjectmanagerId;

    private LongFilter employeeprojectratingsEmployeeId;

    private LongFilter employeeprojectsEmployeeId;

    private LongFilter employeeprojectsAssignorId;

    private LongFilter employeeskillsEmployeeId;

    private LongFilter employeetalentsEmployeeId;

    private LongFilter employeeworksEmployeeId;

    private LongFilter employeesManagerId;

    private LongFilter employmenthistoryEmployeeId;

    private LongFilter feedbackquestionsEmployeeId;

    private LongFilter feedbackrequestsEmployeeId;

    private LongFilter feedbackrequestsLinemanagerId;

    private LongFilter feedbackrespondentsEmployeeId;

    private LongFilter interviewsEmployeeId;

    private LongFilter leavedetailadjustmentlogsAdjustedbyId;

    private LongFilter leaverequestapproversUserId;

    private LongFilter leaverequestsoldsManagerId;

    private LongFilter leaverequestsoldsEmployeeId;

    private LongFilter leavesUserId;

    private LongFilter notificationsentemaillogsRecipientId;

    private LongFilter pcratingsEmployeeId;

    private LongFilter pcratingstrainingsSuccessorforId;

    private LongFilter performancecycleemployeeratingEmployeeId;

    private LongFilter performancecycleemployeeratingManagerId;

    private LongFilter projectcyclesAllowedafterduedatebyId;

    private LongFilter projectcyclesArchitectId;

    private LongFilter projectcyclesProjectmanagerId;

    private LongFilter projectleadershipEmployeeId;

    private LongFilter projectsProjectmanagerId;

    private LongFilter ratingsRaterId;

    private LongFilter userattributesUserId;

    private LongFilter usergoalsUserId;

    private LongFilter userratingsUserId;

    private LongFilter userratingsReviewmanagerId;

    private LongFilter userratingsremarksRaterId;

    private LongFilter userrelationsUserId;

    private LongFilter userrelationsRelateduserId;

    private LongFilter worklogsEmployeeId;

    private LongFilter zemployeeprojectsEmployeeId;

    private LongFilter zemployeeprojectsAssignorId;

    private LongFilter zemployeeprojectsProjectmanagerId;

    private Boolean distinct;

    public EmployeesCriteria() {}

    public EmployeesCriteria(EmployeesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstname = other.firstname == null ? null : other.firstname.copy();
        this.lastname = other.lastname == null ? null : other.lastname.copy();
        this.phonenumber = other.phonenumber == null ? null : other.phonenumber.copy();
        this.dateofbirth = other.dateofbirth == null ? null : other.dateofbirth.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.skype = other.skype == null ? null : other.skype.copy();
        this.employeeDesignation = other.employeeDesignation == null ? null : other.employeeDesignation.copy();
        this.joiningdate = other.joiningdate == null ? null : other.joiningdate.copy();
        this.area = other.area == null ? null : other.area.copy();
        this.leavingdate = other.leavingdate == null ? null : other.leavingdate.copy();
        this.notes = other.notes == null ? null : other.notes.copy();
        this.isactive = other.isactive == null ? null : other.isactive.copy();
        this.googleid = other.googleid == null ? null : other.googleid.copy();
        this.oracleid = other.oracleid == null ? null : other.oracleid.copy();
        this.deptt = other.deptt == null ? null : other.deptt.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.genderid = other.genderid == null ? null : other.genderid.copy();
        this.onprobation = other.onprobation == null ? null : other.onprobation.copy();
        this.employeeCompetency = other.employeeCompetency == null ? null : other.employeeCompetency.copy();
        this.resourcetype = other.resourcetype == null ? null : other.resourcetype.copy();
        this.grade = other.grade == null ? null : other.grade.copy();
        this.subgrade = other.subgrade == null ? null : other.subgrade.copy();
        this.imageurl = other.imageurl == null ? null : other.imageurl.copy();
        this.resignationdate = other.resignationdate == null ? null : other.resignationdate.copy();
        this.graduationdate = other.graduationdate == null ? null : other.graduationdate.copy();
        this.careerstartdate = other.careerstartdate == null ? null : other.careerstartdate.copy();
        this.externalexpyears = other.externalexpyears == null ? null : other.externalexpyears.copy();
        this.externalexpmonths = other.externalexpmonths == null ? null : other.externalexpmonths.copy();
        this.placeofbirth = other.placeofbirth == null ? null : other.placeofbirth.copy();
        this.hireddate = other.hireddate == null ? null : other.hireddate.copy();
        this.lasttrackingupdate = other.lasttrackingupdate == null ? null : other.lasttrackingupdate.copy();
        this.middlename = other.middlename == null ? null : other.middlename.copy();
        this.mobilenumber = other.mobilenumber == null ? null : other.mobilenumber.copy();
        this.resignationtype = other.resignationtype == null ? null : other.resignationtype.copy();
        this.primaryreasonforleaving = other.primaryreasonforleaving == null ? null : other.primaryreasonforleaving.copy();
        this.secondaryreasonforleaving = other.secondaryreasonforleaving == null ? null : other.secondaryreasonforleaving.copy();
        this.noticeperiodduration = other.noticeperiodduration == null ? null : other.noticeperiodduration.copy();
        this.noticeperiodserved = other.noticeperiodserved == null ? null : other.noticeperiodserved.copy();
        this.probationperiodduration = other.probationperiodduration == null ? null : other.probationperiodduration.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
        this.roleId = other.roleId == null ? null : other.roleId.copy();
        this.managerId = other.managerId == null ? null : other.managerId.copy();
        this.leaveId = other.leaveId == null ? null : other.leaveId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.businessunitId = other.businessunitId == null ? null : other.businessunitId.copy();
        this.divisionId = other.divisionId == null ? null : other.divisionId.copy();
        this.competencyId = other.competencyId == null ? null : other.competencyId.copy();
        this.employmentstatusId = other.employmentstatusId == null ? null : other.employmentstatusId.copy();
        this.employmenttypeId = other.employmenttypeId == null ? null : other.employmenttypeId.copy();
        this.designationId = other.designationId == null ? null : other.designationId.copy();
        this.claimrequestviewsEmployeeId = other.claimrequestviewsEmployeeId == null ? null : other.claimrequestviewsEmployeeId.copy();
        this.claimrequestsEmployeeId = other.claimrequestsEmployeeId == null ? null : other.claimrequestsEmployeeId.copy();
        this.dealresourcesEmployeeId = other.dealresourcesEmployeeId == null ? null : other.dealresourcesEmployeeId.copy();
        this.employeeaddressesEmployeeId = other.employeeaddressesEmployeeId == null ? null : other.employeeaddressesEmployeeId.copy();
        this.employeebirthdaysEmployeeId = other.employeebirthdaysEmployeeId == null ? null : other.employeebirthdaysEmployeeId.copy();
        this.employeecertificatesEmployeeId =
            other.employeecertificatesEmployeeId == null ? null : other.employeecertificatesEmployeeId.copy();
        this.employeecommentsCommenterId = other.employeecommentsCommenterId == null ? null : other.employeecommentsCommenterId.copy();
        this.employeecommentsEmployeeId = other.employeecommentsEmployeeId == null ? null : other.employeecommentsEmployeeId.copy();
        this.employeecompensationEmployeeId =
            other.employeecompensationEmployeeId == null ? null : other.employeecompensationEmployeeId.copy();
        this.employeecontactsEmployeeId = other.employeecontactsEmployeeId == null ? null : other.employeecontactsEmployeeId.copy();
        this.employeedetailsEmployeeId = other.employeedetailsEmployeeId == null ? null : other.employeedetailsEmployeeId.copy();
        this.employeedocumentsEmployeeId = other.employeedocumentsEmployeeId == null ? null : other.employeedocumentsEmployeeId.copy();
        this.employeeeducationEmployeeId = other.employeeeducationEmployeeId == null ? null : other.employeeeducationEmployeeId.copy();
        this.employeeemergencycontactsEmployeeId =
            other.employeeemergencycontactsEmployeeId == null ? null : other.employeeemergencycontactsEmployeeId.copy();
        this.employeefamilyinfoEmployeeId = other.employeefamilyinfoEmployeeId == null ? null : other.employeefamilyinfoEmployeeId.copy();
        this.employeejobinfoEmployeeId = other.employeejobinfoEmployeeId == null ? null : other.employeejobinfoEmployeeId.copy();
        this.employeejobinfoReviewmanagerId =
            other.employeejobinfoReviewmanagerId == null ? null : other.employeejobinfoReviewmanagerId.copy();
        this.employeejobinfoManagerId = other.employeejobinfoManagerId == null ? null : other.employeejobinfoManagerId.copy();
        this.employeeprofilehistorylogsEmployeeId =
            other.employeeprofilehistorylogsEmployeeId == null ? null : other.employeeprofilehistorylogsEmployeeId.copy();
        this.employeeprojectratingsProjectarchitectId =
            other.employeeprojectratingsProjectarchitectId == null ? null : other.employeeprojectratingsProjectarchitectId.copy();
        this.employeeprojectratingsProjectmanagerId =
            other.employeeprojectratingsProjectmanagerId == null ? null : other.employeeprojectratingsProjectmanagerId.copy();
        this.employeeprojectratingsEmployeeId =
            other.employeeprojectratingsEmployeeId == null ? null : other.employeeprojectratingsEmployeeId.copy();
        this.employeeprojectsEmployeeId = other.employeeprojectsEmployeeId == null ? null : other.employeeprojectsEmployeeId.copy();
        this.employeeprojectsAssignorId = other.employeeprojectsAssignorId == null ? null : other.employeeprojectsAssignorId.copy();
        this.employeeskillsEmployeeId = other.employeeskillsEmployeeId == null ? null : other.employeeskillsEmployeeId.copy();
        this.employeetalentsEmployeeId = other.employeetalentsEmployeeId == null ? null : other.employeetalentsEmployeeId.copy();
        this.employeeworksEmployeeId = other.employeeworksEmployeeId == null ? null : other.employeeworksEmployeeId.copy();
        this.employeesManagerId = other.employeesManagerId == null ? null : other.employeesManagerId.copy();
        this.employmenthistoryEmployeeId = other.employmenthistoryEmployeeId == null ? null : other.employmenthistoryEmployeeId.copy();
        this.feedbackquestionsEmployeeId = other.feedbackquestionsEmployeeId == null ? null : other.feedbackquestionsEmployeeId.copy();
        this.feedbackrequestsEmployeeId = other.feedbackrequestsEmployeeId == null ? null : other.feedbackrequestsEmployeeId.copy();
        this.feedbackrequestsLinemanagerId =
            other.feedbackrequestsLinemanagerId == null ? null : other.feedbackrequestsLinemanagerId.copy();
        this.feedbackrespondentsEmployeeId =
            other.feedbackrespondentsEmployeeId == null ? null : other.feedbackrespondentsEmployeeId.copy();
        this.interviewsEmployeeId = other.interviewsEmployeeId == null ? null : other.interviewsEmployeeId.copy();
        this.leavedetailadjustmentlogsAdjustedbyId =
            other.leavedetailadjustmentlogsAdjustedbyId == null ? null : other.leavedetailadjustmentlogsAdjustedbyId.copy();
        this.leaverequestapproversUserId = other.leaverequestapproversUserId == null ? null : other.leaverequestapproversUserId.copy();
        this.leaverequestsoldsManagerId = other.leaverequestsoldsManagerId == null ? null : other.leaverequestsoldsManagerId.copy();
        this.leaverequestsoldsEmployeeId = other.leaverequestsoldsEmployeeId == null ? null : other.leaverequestsoldsEmployeeId.copy();
        this.leavesUserId = other.leavesUserId == null ? null : other.leavesUserId.copy();
        this.notificationsentemaillogsRecipientId =
            other.notificationsentemaillogsRecipientId == null ? null : other.notificationsentemaillogsRecipientId.copy();
        this.pcratingsEmployeeId = other.pcratingsEmployeeId == null ? null : other.pcratingsEmployeeId.copy();
        this.pcratingstrainingsSuccessorforId =
            other.pcratingstrainingsSuccessorforId == null ? null : other.pcratingstrainingsSuccessorforId.copy();
        this.performancecycleemployeeratingEmployeeId =
            other.performancecycleemployeeratingEmployeeId == null ? null : other.performancecycleemployeeratingEmployeeId.copy();
        this.performancecycleemployeeratingManagerId =
            other.performancecycleemployeeratingManagerId == null ? null : other.performancecycleemployeeratingManagerId.copy();
        this.projectcyclesAllowedafterduedatebyId =
            other.projectcyclesAllowedafterduedatebyId == null ? null : other.projectcyclesAllowedafterduedatebyId.copy();
        this.projectcyclesArchitectId = other.projectcyclesArchitectId == null ? null : other.projectcyclesArchitectId.copy();
        this.projectcyclesProjectmanagerId =
            other.projectcyclesProjectmanagerId == null ? null : other.projectcyclesProjectmanagerId.copy();
        this.projectleadershipEmployeeId = other.projectleadershipEmployeeId == null ? null : other.projectleadershipEmployeeId.copy();
        this.projectsProjectmanagerId = other.projectsProjectmanagerId == null ? null : other.projectsProjectmanagerId.copy();
        this.ratingsRaterId = other.ratingsRaterId == null ? null : other.ratingsRaterId.copy();
        this.userattributesUserId = other.userattributesUserId == null ? null : other.userattributesUserId.copy();
        this.usergoalsUserId = other.usergoalsUserId == null ? null : other.usergoalsUserId.copy();
        this.userratingsUserId = other.userratingsUserId == null ? null : other.userratingsUserId.copy();
        this.userratingsReviewmanagerId = other.userratingsReviewmanagerId == null ? null : other.userratingsReviewmanagerId.copy();
        this.userratingsremarksRaterId = other.userratingsremarksRaterId == null ? null : other.userratingsremarksRaterId.copy();
        this.userrelationsUserId = other.userrelationsUserId == null ? null : other.userrelationsUserId.copy();
        this.userrelationsRelateduserId = other.userrelationsRelateduserId == null ? null : other.userrelationsRelateduserId.copy();
        this.worklogsEmployeeId = other.worklogsEmployeeId == null ? null : other.worklogsEmployeeId.copy();
        this.zemployeeprojectsEmployeeId = other.zemployeeprojectsEmployeeId == null ? null : other.zemployeeprojectsEmployeeId.copy();
        this.zemployeeprojectsAssignorId = other.zemployeeprojectsAssignorId == null ? null : other.zemployeeprojectsAssignorId.copy();
        this.zemployeeprojectsProjectmanagerId =
            other.zemployeeprojectsProjectmanagerId == null ? null : other.zemployeeprojectsProjectmanagerId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeesCriteria copy() {
        return new EmployeesCriteria(this);
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

    public StringFilter getPhonenumber() {
        return phonenumber;
    }

    public StringFilter phonenumber() {
        if (phonenumber == null) {
            phonenumber = new StringFilter();
        }
        return phonenumber;
    }

    public void setPhonenumber(StringFilter phonenumber) {
        this.phonenumber = phonenumber;
    }

    public InstantFilter getDateofbirth() {
        return dateofbirth;
    }

    public InstantFilter dateofbirth() {
        if (dateofbirth == null) {
            dateofbirth = new InstantFilter();
        }
        return dateofbirth;
    }

    public void setDateofbirth(InstantFilter dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getSkype() {
        return skype;
    }

    public StringFilter skype() {
        if (skype == null) {
            skype = new StringFilter();
        }
        return skype;
    }

    public void setSkype(StringFilter skype) {
        this.skype = skype;
    }

    public StringFilter getEmployeeDesignation() {
        return employeeDesignation;
    }

    public StringFilter employeeDesignation() {
        if (employeeDesignation == null) {
            employeeDesignation = new StringFilter();
        }
        return employeeDesignation;
    }

    public void setEmployeeDesignation(StringFilter employeeDesignation) {
        this.employeeDesignation = employeeDesignation;
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

    public StringFilter getArea() {
        return area;
    }

    public StringFilter area() {
        if (area == null) {
            area = new StringFilter();
        }
        return area;
    }

    public void setArea(StringFilter area) {
        this.area = area;
    }

    public InstantFilter getLeavingdate() {
        return leavingdate;
    }

    public InstantFilter leavingdate() {
        if (leavingdate == null) {
            leavingdate = new InstantFilter();
        }
        return leavingdate;
    }

    public void setLeavingdate(InstantFilter leavingdate) {
        this.leavingdate = leavingdate;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public StringFilter notes() {
        if (notes == null) {
            notes = new StringFilter();
        }
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
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

    public StringFilter getGoogleid() {
        return googleid;
    }

    public StringFilter googleid() {
        if (googleid == null) {
            googleid = new StringFilter();
        }
        return googleid;
    }

    public void setGoogleid(StringFilter googleid) {
        this.googleid = googleid;
    }

    public StringFilter getOracleid() {
        return oracleid;
    }

    public StringFilter oracleid() {
        if (oracleid == null) {
            oracleid = new StringFilter();
        }
        return oracleid;
    }

    public void setOracleid(StringFilter oracleid) {
        this.oracleid = oracleid;
    }

    public StringFilter getDeptt() {
        return deptt;
    }

    public StringFilter deptt() {
        if (deptt == null) {
            deptt = new StringFilter();
        }
        return deptt;
    }

    public void setDeptt(StringFilter deptt) {
        this.deptt = deptt;
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

    public StringFilter getGenderid() {
        return genderid;
    }

    public StringFilter genderid() {
        if (genderid == null) {
            genderid = new StringFilter();
        }
        return genderid;
    }

    public void setGenderid(StringFilter genderid) {
        this.genderid = genderid;
    }

    public BooleanFilter getOnprobation() {
        return onprobation;
    }

    public BooleanFilter onprobation() {
        if (onprobation == null) {
            onprobation = new BooleanFilter();
        }
        return onprobation;
    }

    public void setOnprobation(BooleanFilter onprobation) {
        this.onprobation = onprobation;
    }

    public StringFilter getEmployeeCompetency() {
        return employeeCompetency;
    }

    public StringFilter employeeCompetency() {
        if (employeeCompetency == null) {
            employeeCompetency = new StringFilter();
        }
        return employeeCompetency;
    }

    public void setEmployeeCompetency(StringFilter employeeCompetency) {
        this.employeeCompetency = employeeCompetency;
    }

    public StringFilter getResourcetype() {
        return resourcetype;
    }

    public StringFilter resourcetype() {
        if (resourcetype == null) {
            resourcetype = new StringFilter();
        }
        return resourcetype;
    }

    public void setResourcetype(StringFilter resourcetype) {
        this.resourcetype = resourcetype;
    }

    public StringFilter getGrade() {
        return grade;
    }

    public StringFilter grade() {
        if (grade == null) {
            grade = new StringFilter();
        }
        return grade;
    }

    public void setGrade(StringFilter grade) {
        this.grade = grade;
    }

    public StringFilter getSubgrade() {
        return subgrade;
    }

    public StringFilter subgrade() {
        if (subgrade == null) {
            subgrade = new StringFilter();
        }
        return subgrade;
    }

    public void setSubgrade(StringFilter subgrade) {
        this.subgrade = subgrade;
    }

    public StringFilter getImageurl() {
        return imageurl;
    }

    public StringFilter imageurl() {
        if (imageurl == null) {
            imageurl = new StringFilter();
        }
        return imageurl;
    }

    public void setImageurl(StringFilter imageurl) {
        this.imageurl = imageurl;
    }

    public InstantFilter getResignationdate() {
        return resignationdate;
    }

    public InstantFilter resignationdate() {
        if (resignationdate == null) {
            resignationdate = new InstantFilter();
        }
        return resignationdate;
    }

    public void setResignationdate(InstantFilter resignationdate) {
        this.resignationdate = resignationdate;
    }

    public LocalDateFilter getGraduationdate() {
        return graduationdate;
    }

    public LocalDateFilter graduationdate() {
        if (graduationdate == null) {
            graduationdate = new LocalDateFilter();
        }
        return graduationdate;
    }

    public void setGraduationdate(LocalDateFilter graduationdate) {
        this.graduationdate = graduationdate;
    }

    public LocalDateFilter getCareerstartdate() {
        return careerstartdate;
    }

    public LocalDateFilter careerstartdate() {
        if (careerstartdate == null) {
            careerstartdate = new LocalDateFilter();
        }
        return careerstartdate;
    }

    public void setCareerstartdate(LocalDateFilter careerstartdate) {
        this.careerstartdate = careerstartdate;
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

    public BigDecimalFilter getExternalexpmonths() {
        return externalexpmonths;
    }

    public BigDecimalFilter externalexpmonths() {
        if (externalexpmonths == null) {
            externalexpmonths = new BigDecimalFilter();
        }
        return externalexpmonths;
    }

    public void setExternalexpmonths(BigDecimalFilter externalexpmonths) {
        this.externalexpmonths = externalexpmonths;
    }

    public StringFilter getPlaceofbirth() {
        return placeofbirth;
    }

    public StringFilter placeofbirth() {
        if (placeofbirth == null) {
            placeofbirth = new StringFilter();
        }
        return placeofbirth;
    }

    public void setPlaceofbirth(StringFilter placeofbirth) {
        this.placeofbirth = placeofbirth;
    }

    public InstantFilter getHireddate() {
        return hireddate;
    }

    public InstantFilter hireddate() {
        if (hireddate == null) {
            hireddate = new InstantFilter();
        }
        return hireddate;
    }

    public void setHireddate(InstantFilter hireddate) {
        this.hireddate = hireddate;
    }

    public InstantFilter getLasttrackingupdate() {
        return lasttrackingupdate;
    }

    public InstantFilter lasttrackingupdate() {
        if (lasttrackingupdate == null) {
            lasttrackingupdate = new InstantFilter();
        }
        return lasttrackingupdate;
    }

    public void setLasttrackingupdate(InstantFilter lasttrackingupdate) {
        this.lasttrackingupdate = lasttrackingupdate;
    }

    public StringFilter getMiddlename() {
        return middlename;
    }

    public StringFilter middlename() {
        if (middlename == null) {
            middlename = new StringFilter();
        }
        return middlename;
    }

    public void setMiddlename(StringFilter middlename) {
        this.middlename = middlename;
    }

    public StringFilter getMobilenumber() {
        return mobilenumber;
    }

    public StringFilter mobilenumber() {
        if (mobilenumber == null) {
            mobilenumber = new StringFilter();
        }
        return mobilenumber;
    }

    public void setMobilenumber(StringFilter mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public StringFilter getResignationtype() {
        return resignationtype;
    }

    public StringFilter resignationtype() {
        if (resignationtype == null) {
            resignationtype = new StringFilter();
        }
        return resignationtype;
    }

    public void setResignationtype(StringFilter resignationtype) {
        this.resignationtype = resignationtype;
    }

    public StringFilter getPrimaryreasonforleaving() {
        return primaryreasonforleaving;
    }

    public StringFilter primaryreasonforleaving() {
        if (primaryreasonforleaving == null) {
            primaryreasonforleaving = new StringFilter();
        }
        return primaryreasonforleaving;
    }

    public void setPrimaryreasonforleaving(StringFilter primaryreasonforleaving) {
        this.primaryreasonforleaving = primaryreasonforleaving;
    }

    public StringFilter getSecondaryreasonforleaving() {
        return secondaryreasonforleaving;
    }

    public StringFilter secondaryreasonforleaving() {
        if (secondaryreasonforleaving == null) {
            secondaryreasonforleaving = new StringFilter();
        }
        return secondaryreasonforleaving;
    }

    public void setSecondaryreasonforleaving(StringFilter secondaryreasonforleaving) {
        this.secondaryreasonforleaving = secondaryreasonforleaving;
    }

    public IntegerFilter getNoticeperiodduration() {
        return noticeperiodduration;
    }

    public IntegerFilter noticeperiodduration() {
        if (noticeperiodduration == null) {
            noticeperiodduration = new IntegerFilter();
        }
        return noticeperiodduration;
    }

    public void setNoticeperiodduration(IntegerFilter noticeperiodduration) {
        this.noticeperiodduration = noticeperiodduration;
    }

    public IntegerFilter getNoticeperiodserved() {
        return noticeperiodserved;
    }

    public IntegerFilter noticeperiodserved() {
        if (noticeperiodserved == null) {
            noticeperiodserved = new IntegerFilter();
        }
        return noticeperiodserved;
    }

    public void setNoticeperiodserved(IntegerFilter noticeperiodserved) {
        this.noticeperiodserved = noticeperiodserved;
    }

    public IntegerFilter getProbationperiodduration() {
        return probationperiodduration;
    }

    public IntegerFilter probationperiodduration() {
        if (probationperiodduration == null) {
            probationperiodduration = new IntegerFilter();
        }
        return probationperiodduration;
    }

    public void setProbationperiodduration(IntegerFilter probationperiodduration) {
        this.probationperiodduration = probationperiodduration;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public LongFilter locationId() {
        if (locationId == null) {
            locationId = new LongFilter();
        }
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public LongFilter getRoleId() {
        return roleId;
    }

    public LongFilter roleId() {
        if (roleId == null) {
            roleId = new LongFilter();
        }
        return roleId;
    }

    public void setRoleId(LongFilter roleId) {
        this.roleId = roleId;
    }

    public LongFilter getManagerId() {
        return managerId;
    }

    public LongFilter managerId() {
        if (managerId == null) {
            managerId = new LongFilter();
        }
        return managerId;
    }

    public void setManagerId(LongFilter managerId) {
        this.managerId = managerId;
    }

    public LongFilter getLeaveId() {
        return leaveId;
    }

    public LongFilter leaveId() {
        if (leaveId == null) {
            leaveId = new LongFilter();
        }
        return leaveId;
    }

    public void setLeaveId(LongFilter leaveId) {
        this.leaveId = leaveId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public LongFilter departmentId() {
        if (departmentId == null) {
            departmentId = new LongFilter();
        }
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
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

    public LongFilter getDivisionId() {
        return divisionId;
    }

    public LongFilter divisionId() {
        if (divisionId == null) {
            divisionId = new LongFilter();
        }
        return divisionId;
    }

    public void setDivisionId(LongFilter divisionId) {
        this.divisionId = divisionId;
    }

    public LongFilter getCompetencyId() {
        return competencyId;
    }

    public LongFilter competencyId() {
        if (competencyId == null) {
            competencyId = new LongFilter();
        }
        return competencyId;
    }

    public void setCompetencyId(LongFilter competencyId) {
        this.competencyId = competencyId;
    }

    public LongFilter getEmploymentstatusId() {
        return employmentstatusId;
    }

    public LongFilter employmentstatusId() {
        if (employmentstatusId == null) {
            employmentstatusId = new LongFilter();
        }
        return employmentstatusId;
    }

    public void setEmploymentstatusId(LongFilter employmentstatusId) {
        this.employmentstatusId = employmentstatusId;
    }

    public LongFilter getEmploymenttypeId() {
        return employmenttypeId;
    }

    public LongFilter employmenttypeId() {
        if (employmenttypeId == null) {
            employmenttypeId = new LongFilter();
        }
        return employmenttypeId;
    }

    public void setEmploymenttypeId(LongFilter employmenttypeId) {
        this.employmenttypeId = employmenttypeId;
    }

    public LongFilter getDesignationId() {
        return designationId;
    }

    public LongFilter designationId() {
        if (designationId == null) {
            designationId = new LongFilter();
        }
        return designationId;
    }

    public void setDesignationId(LongFilter designationId) {
        this.designationId = designationId;
    }

    public LongFilter getClaimrequestviewsEmployeeId() {
        return claimrequestviewsEmployeeId;
    }

    public LongFilter claimrequestviewsEmployeeId() {
        if (claimrequestviewsEmployeeId == null) {
            claimrequestviewsEmployeeId = new LongFilter();
        }
        return claimrequestviewsEmployeeId;
    }

    public void setClaimrequestviewsEmployeeId(LongFilter claimrequestviewsEmployeeId) {
        this.claimrequestviewsEmployeeId = claimrequestviewsEmployeeId;
    }

    public LongFilter getClaimrequestsEmployeeId() {
        return claimrequestsEmployeeId;
    }

    public LongFilter claimrequestsEmployeeId() {
        if (claimrequestsEmployeeId == null) {
            claimrequestsEmployeeId = new LongFilter();
        }
        return claimrequestsEmployeeId;
    }

    public void setClaimrequestsEmployeeId(LongFilter claimrequestsEmployeeId) {
        this.claimrequestsEmployeeId = claimrequestsEmployeeId;
    }

    public LongFilter getDealresourcesEmployeeId() {
        return dealresourcesEmployeeId;
    }

    public LongFilter dealresourcesEmployeeId() {
        if (dealresourcesEmployeeId == null) {
            dealresourcesEmployeeId = new LongFilter();
        }
        return dealresourcesEmployeeId;
    }

    public void setDealresourcesEmployeeId(LongFilter dealresourcesEmployeeId) {
        this.dealresourcesEmployeeId = dealresourcesEmployeeId;
    }

    public LongFilter getEmployeeaddressesEmployeeId() {
        return employeeaddressesEmployeeId;
    }

    public LongFilter employeeaddressesEmployeeId() {
        if (employeeaddressesEmployeeId == null) {
            employeeaddressesEmployeeId = new LongFilter();
        }
        return employeeaddressesEmployeeId;
    }

    public void setEmployeeaddressesEmployeeId(LongFilter employeeaddressesEmployeeId) {
        this.employeeaddressesEmployeeId = employeeaddressesEmployeeId;
    }

    public LongFilter getEmployeebirthdaysEmployeeId() {
        return employeebirthdaysEmployeeId;
    }

    public LongFilter employeebirthdaysEmployeeId() {
        if (employeebirthdaysEmployeeId == null) {
            employeebirthdaysEmployeeId = new LongFilter();
        }
        return employeebirthdaysEmployeeId;
    }

    public void setEmployeebirthdaysEmployeeId(LongFilter employeebirthdaysEmployeeId) {
        this.employeebirthdaysEmployeeId = employeebirthdaysEmployeeId;
    }

    public LongFilter getEmployeecertificatesEmployeeId() {
        return employeecertificatesEmployeeId;
    }

    public LongFilter employeecertificatesEmployeeId() {
        if (employeecertificatesEmployeeId == null) {
            employeecertificatesEmployeeId = new LongFilter();
        }
        return employeecertificatesEmployeeId;
    }

    public void setEmployeecertificatesEmployeeId(LongFilter employeecertificatesEmployeeId) {
        this.employeecertificatesEmployeeId = employeecertificatesEmployeeId;
    }

    public LongFilter getEmployeecommentsCommenterId() {
        return employeecommentsCommenterId;
    }

    public LongFilter employeecommentsCommenterId() {
        if (employeecommentsCommenterId == null) {
            employeecommentsCommenterId = new LongFilter();
        }
        return employeecommentsCommenterId;
    }

    public void setEmployeecommentsCommenterId(LongFilter employeecommentsCommenterId) {
        this.employeecommentsCommenterId = employeecommentsCommenterId;
    }

    public LongFilter getEmployeecommentsEmployeeId() {
        return employeecommentsEmployeeId;
    }

    public LongFilter employeecommentsEmployeeId() {
        if (employeecommentsEmployeeId == null) {
            employeecommentsEmployeeId = new LongFilter();
        }
        return employeecommentsEmployeeId;
    }

    public void setEmployeecommentsEmployeeId(LongFilter employeecommentsEmployeeId) {
        this.employeecommentsEmployeeId = employeecommentsEmployeeId;
    }

    public LongFilter getEmployeecompensationEmployeeId() {
        return employeecompensationEmployeeId;
    }

    public LongFilter employeecompensationEmployeeId() {
        if (employeecompensationEmployeeId == null) {
            employeecompensationEmployeeId = new LongFilter();
        }
        return employeecompensationEmployeeId;
    }

    public void setEmployeecompensationEmployeeId(LongFilter employeecompensationEmployeeId) {
        this.employeecompensationEmployeeId = employeecompensationEmployeeId;
    }

    public LongFilter getEmployeecontactsEmployeeId() {
        return employeecontactsEmployeeId;
    }

    public LongFilter employeecontactsEmployeeId() {
        if (employeecontactsEmployeeId == null) {
            employeecontactsEmployeeId = new LongFilter();
        }
        return employeecontactsEmployeeId;
    }

    public void setEmployeecontactsEmployeeId(LongFilter employeecontactsEmployeeId) {
        this.employeecontactsEmployeeId = employeecontactsEmployeeId;
    }

    public LongFilter getEmployeedetailsEmployeeId() {
        return employeedetailsEmployeeId;
    }

    public LongFilter employeedetailsEmployeeId() {
        if (employeedetailsEmployeeId == null) {
            employeedetailsEmployeeId = new LongFilter();
        }
        return employeedetailsEmployeeId;
    }

    public void setEmployeedetailsEmployeeId(LongFilter employeedetailsEmployeeId) {
        this.employeedetailsEmployeeId = employeedetailsEmployeeId;
    }

    public LongFilter getEmployeedocumentsEmployeeId() {
        return employeedocumentsEmployeeId;
    }

    public LongFilter employeedocumentsEmployeeId() {
        if (employeedocumentsEmployeeId == null) {
            employeedocumentsEmployeeId = new LongFilter();
        }
        return employeedocumentsEmployeeId;
    }

    public void setEmployeedocumentsEmployeeId(LongFilter employeedocumentsEmployeeId) {
        this.employeedocumentsEmployeeId = employeedocumentsEmployeeId;
    }

    public LongFilter getEmployeeeducationEmployeeId() {
        return employeeeducationEmployeeId;
    }

    public LongFilter employeeeducationEmployeeId() {
        if (employeeeducationEmployeeId == null) {
            employeeeducationEmployeeId = new LongFilter();
        }
        return employeeeducationEmployeeId;
    }

    public void setEmployeeeducationEmployeeId(LongFilter employeeeducationEmployeeId) {
        this.employeeeducationEmployeeId = employeeeducationEmployeeId;
    }

    public LongFilter getEmployeeemergencycontactsEmployeeId() {
        return employeeemergencycontactsEmployeeId;
    }

    public LongFilter employeeemergencycontactsEmployeeId() {
        if (employeeemergencycontactsEmployeeId == null) {
            employeeemergencycontactsEmployeeId = new LongFilter();
        }
        return employeeemergencycontactsEmployeeId;
    }

    public void setEmployeeemergencycontactsEmployeeId(LongFilter employeeemergencycontactsEmployeeId) {
        this.employeeemergencycontactsEmployeeId = employeeemergencycontactsEmployeeId;
    }

    public LongFilter getEmployeefamilyinfoEmployeeId() {
        return employeefamilyinfoEmployeeId;
    }

    public LongFilter employeefamilyinfoEmployeeId() {
        if (employeefamilyinfoEmployeeId == null) {
            employeefamilyinfoEmployeeId = new LongFilter();
        }
        return employeefamilyinfoEmployeeId;
    }

    public void setEmployeefamilyinfoEmployeeId(LongFilter employeefamilyinfoEmployeeId) {
        this.employeefamilyinfoEmployeeId = employeefamilyinfoEmployeeId;
    }

    public LongFilter getEmployeejobinfoEmployeeId() {
        return employeejobinfoEmployeeId;
    }

    public LongFilter employeejobinfoEmployeeId() {
        if (employeejobinfoEmployeeId == null) {
            employeejobinfoEmployeeId = new LongFilter();
        }
        return employeejobinfoEmployeeId;
    }

    public void setEmployeejobinfoEmployeeId(LongFilter employeejobinfoEmployeeId) {
        this.employeejobinfoEmployeeId = employeejobinfoEmployeeId;
    }

    public LongFilter getEmployeejobinfoReviewmanagerId() {
        return employeejobinfoReviewmanagerId;
    }

    public LongFilter employeejobinfoReviewmanagerId() {
        if (employeejobinfoReviewmanagerId == null) {
            employeejobinfoReviewmanagerId = new LongFilter();
        }
        return employeejobinfoReviewmanagerId;
    }

    public void setEmployeejobinfoReviewmanagerId(LongFilter employeejobinfoReviewmanagerId) {
        this.employeejobinfoReviewmanagerId = employeejobinfoReviewmanagerId;
    }

    public LongFilter getEmployeejobinfoManagerId() {
        return employeejobinfoManagerId;
    }

    public LongFilter employeejobinfoManagerId() {
        if (employeejobinfoManagerId == null) {
            employeejobinfoManagerId = new LongFilter();
        }
        return employeejobinfoManagerId;
    }

    public void setEmployeejobinfoManagerId(LongFilter employeejobinfoManagerId) {
        this.employeejobinfoManagerId = employeejobinfoManagerId;
    }

    public LongFilter getEmployeeprofilehistorylogsEmployeeId() {
        return employeeprofilehistorylogsEmployeeId;
    }

    public LongFilter employeeprofilehistorylogsEmployeeId() {
        if (employeeprofilehistorylogsEmployeeId == null) {
            employeeprofilehistorylogsEmployeeId = new LongFilter();
        }
        return employeeprofilehistorylogsEmployeeId;
    }

    public void setEmployeeprofilehistorylogsEmployeeId(LongFilter employeeprofilehistorylogsEmployeeId) {
        this.employeeprofilehistorylogsEmployeeId = employeeprofilehistorylogsEmployeeId;
    }

    public LongFilter getEmployeeprojectratingsProjectarchitectId() {
        return employeeprojectratingsProjectarchitectId;
    }

    public LongFilter employeeprojectratingsProjectarchitectId() {
        if (employeeprojectratingsProjectarchitectId == null) {
            employeeprojectratingsProjectarchitectId = new LongFilter();
        }
        return employeeprojectratingsProjectarchitectId;
    }

    public void setEmployeeprojectratingsProjectarchitectId(LongFilter employeeprojectratingsProjectarchitectId) {
        this.employeeprojectratingsProjectarchitectId = employeeprojectratingsProjectarchitectId;
    }

    public LongFilter getEmployeeprojectratingsProjectmanagerId() {
        return employeeprojectratingsProjectmanagerId;
    }

    public LongFilter employeeprojectratingsProjectmanagerId() {
        if (employeeprojectratingsProjectmanagerId == null) {
            employeeprojectratingsProjectmanagerId = new LongFilter();
        }
        return employeeprojectratingsProjectmanagerId;
    }

    public void setEmployeeprojectratingsProjectmanagerId(LongFilter employeeprojectratingsProjectmanagerId) {
        this.employeeprojectratingsProjectmanagerId = employeeprojectratingsProjectmanagerId;
    }

    public LongFilter getEmployeeprojectratingsEmployeeId() {
        return employeeprojectratingsEmployeeId;
    }

    public LongFilter employeeprojectratingsEmployeeId() {
        if (employeeprojectratingsEmployeeId == null) {
            employeeprojectratingsEmployeeId = new LongFilter();
        }
        return employeeprojectratingsEmployeeId;
    }

    public void setEmployeeprojectratingsEmployeeId(LongFilter employeeprojectratingsEmployeeId) {
        this.employeeprojectratingsEmployeeId = employeeprojectratingsEmployeeId;
    }

    public LongFilter getEmployeeprojectsEmployeeId() {
        return employeeprojectsEmployeeId;
    }

    public LongFilter employeeprojectsEmployeeId() {
        if (employeeprojectsEmployeeId == null) {
            employeeprojectsEmployeeId = new LongFilter();
        }
        return employeeprojectsEmployeeId;
    }

    public void setEmployeeprojectsEmployeeId(LongFilter employeeprojectsEmployeeId) {
        this.employeeprojectsEmployeeId = employeeprojectsEmployeeId;
    }

    public LongFilter getEmployeeprojectsAssignorId() {
        return employeeprojectsAssignorId;
    }

    public LongFilter employeeprojectsAssignorId() {
        if (employeeprojectsAssignorId == null) {
            employeeprojectsAssignorId = new LongFilter();
        }
        return employeeprojectsAssignorId;
    }

    public void setEmployeeprojectsAssignorId(LongFilter employeeprojectsAssignorId) {
        this.employeeprojectsAssignorId = employeeprojectsAssignorId;
    }

    public LongFilter getEmployeeskillsEmployeeId() {
        return employeeskillsEmployeeId;
    }

    public LongFilter employeeskillsEmployeeId() {
        if (employeeskillsEmployeeId == null) {
            employeeskillsEmployeeId = new LongFilter();
        }
        return employeeskillsEmployeeId;
    }

    public void setEmployeeskillsEmployeeId(LongFilter employeeskillsEmployeeId) {
        this.employeeskillsEmployeeId = employeeskillsEmployeeId;
    }

    public LongFilter getEmployeetalentsEmployeeId() {
        return employeetalentsEmployeeId;
    }

    public LongFilter employeetalentsEmployeeId() {
        if (employeetalentsEmployeeId == null) {
            employeetalentsEmployeeId = new LongFilter();
        }
        return employeetalentsEmployeeId;
    }

    public void setEmployeetalentsEmployeeId(LongFilter employeetalentsEmployeeId) {
        this.employeetalentsEmployeeId = employeetalentsEmployeeId;
    }

    public LongFilter getEmployeeworksEmployeeId() {
        return employeeworksEmployeeId;
    }

    public LongFilter employeeworksEmployeeId() {
        if (employeeworksEmployeeId == null) {
            employeeworksEmployeeId = new LongFilter();
        }
        return employeeworksEmployeeId;
    }

    public void setEmployeeworksEmployeeId(LongFilter employeeworksEmployeeId) {
        this.employeeworksEmployeeId = employeeworksEmployeeId;
    }

    public LongFilter getEmployeesManagerId() {
        return employeesManagerId;
    }

    public LongFilter employeesManagerId() {
        if (employeesManagerId == null) {
            employeesManagerId = new LongFilter();
        }
        return employeesManagerId;
    }

    public void setEmployeesManagerId(LongFilter employeesManagerId) {
        this.employeesManagerId = employeesManagerId;
    }

    public LongFilter getEmploymenthistoryEmployeeId() {
        return employmenthistoryEmployeeId;
    }

    public LongFilter employmenthistoryEmployeeId() {
        if (employmenthistoryEmployeeId == null) {
            employmenthistoryEmployeeId = new LongFilter();
        }
        return employmenthistoryEmployeeId;
    }

    public void setEmploymenthistoryEmployeeId(LongFilter employmenthistoryEmployeeId) {
        this.employmenthistoryEmployeeId = employmenthistoryEmployeeId;
    }

    public LongFilter getFeedbackquestionsEmployeeId() {
        return feedbackquestionsEmployeeId;
    }

    public LongFilter feedbackquestionsEmployeeId() {
        if (feedbackquestionsEmployeeId == null) {
            feedbackquestionsEmployeeId = new LongFilter();
        }
        return feedbackquestionsEmployeeId;
    }

    public void setFeedbackquestionsEmployeeId(LongFilter feedbackquestionsEmployeeId) {
        this.feedbackquestionsEmployeeId = feedbackquestionsEmployeeId;
    }

    public LongFilter getFeedbackrequestsEmployeeId() {
        return feedbackrequestsEmployeeId;
    }

    public LongFilter feedbackrequestsEmployeeId() {
        if (feedbackrequestsEmployeeId == null) {
            feedbackrequestsEmployeeId = new LongFilter();
        }
        return feedbackrequestsEmployeeId;
    }

    public void setFeedbackrequestsEmployeeId(LongFilter feedbackrequestsEmployeeId) {
        this.feedbackrequestsEmployeeId = feedbackrequestsEmployeeId;
    }

    public LongFilter getFeedbackrequestsLinemanagerId() {
        return feedbackrequestsLinemanagerId;
    }

    public LongFilter feedbackrequestsLinemanagerId() {
        if (feedbackrequestsLinemanagerId == null) {
            feedbackrequestsLinemanagerId = new LongFilter();
        }
        return feedbackrequestsLinemanagerId;
    }

    public void setFeedbackrequestsLinemanagerId(LongFilter feedbackrequestsLinemanagerId) {
        this.feedbackrequestsLinemanagerId = feedbackrequestsLinemanagerId;
    }

    public LongFilter getFeedbackrespondentsEmployeeId() {
        return feedbackrespondentsEmployeeId;
    }

    public LongFilter feedbackrespondentsEmployeeId() {
        if (feedbackrespondentsEmployeeId == null) {
            feedbackrespondentsEmployeeId = new LongFilter();
        }
        return feedbackrespondentsEmployeeId;
    }

    public void setFeedbackrespondentsEmployeeId(LongFilter feedbackrespondentsEmployeeId) {
        this.feedbackrespondentsEmployeeId = feedbackrespondentsEmployeeId;
    }

    public LongFilter getInterviewsEmployeeId() {
        return interviewsEmployeeId;
    }

    public LongFilter interviewsEmployeeId() {
        if (interviewsEmployeeId == null) {
            interviewsEmployeeId = new LongFilter();
        }
        return interviewsEmployeeId;
    }

    public void setInterviewsEmployeeId(LongFilter interviewsEmployeeId) {
        this.interviewsEmployeeId = interviewsEmployeeId;
    }

    public LongFilter getLeavedetailadjustmentlogsAdjustedbyId() {
        return leavedetailadjustmentlogsAdjustedbyId;
    }

    public LongFilter leavedetailadjustmentlogsAdjustedbyId() {
        if (leavedetailadjustmentlogsAdjustedbyId == null) {
            leavedetailadjustmentlogsAdjustedbyId = new LongFilter();
        }
        return leavedetailadjustmentlogsAdjustedbyId;
    }

    public void setLeavedetailadjustmentlogsAdjustedbyId(LongFilter leavedetailadjustmentlogsAdjustedbyId) {
        this.leavedetailadjustmentlogsAdjustedbyId = leavedetailadjustmentlogsAdjustedbyId;
    }

    public LongFilter getLeaverequestapproversUserId() {
        return leaverequestapproversUserId;
    }

    public LongFilter leaverequestapproversUserId() {
        if (leaverequestapproversUserId == null) {
            leaverequestapproversUserId = new LongFilter();
        }
        return leaverequestapproversUserId;
    }

    public void setLeaverequestapproversUserId(LongFilter leaverequestapproversUserId) {
        this.leaverequestapproversUserId = leaverequestapproversUserId;
    }

    public LongFilter getLeaverequestsoldsManagerId() {
        return leaverequestsoldsManagerId;
    }

    public LongFilter leaverequestsoldsManagerId() {
        if (leaverequestsoldsManagerId == null) {
            leaverequestsoldsManagerId = new LongFilter();
        }
        return leaverequestsoldsManagerId;
    }

    public void setLeaverequestsoldsManagerId(LongFilter leaverequestsoldsManagerId) {
        this.leaverequestsoldsManagerId = leaverequestsoldsManagerId;
    }

    public LongFilter getLeaverequestsoldsEmployeeId() {
        return leaverequestsoldsEmployeeId;
    }

    public LongFilter leaverequestsoldsEmployeeId() {
        if (leaverequestsoldsEmployeeId == null) {
            leaverequestsoldsEmployeeId = new LongFilter();
        }
        return leaverequestsoldsEmployeeId;
    }

    public void setLeaverequestsoldsEmployeeId(LongFilter leaverequestsoldsEmployeeId) {
        this.leaverequestsoldsEmployeeId = leaverequestsoldsEmployeeId;
    }

    public LongFilter getLeavesUserId() {
        return leavesUserId;
    }

    public LongFilter leavesUserId() {
        if (leavesUserId == null) {
            leavesUserId = new LongFilter();
        }
        return leavesUserId;
    }

    public void setLeavesUserId(LongFilter leavesUserId) {
        this.leavesUserId = leavesUserId;
    }

    public LongFilter getNotificationsentemaillogsRecipientId() {
        return notificationsentemaillogsRecipientId;
    }

    public LongFilter notificationsentemaillogsRecipientId() {
        if (notificationsentemaillogsRecipientId == null) {
            notificationsentemaillogsRecipientId = new LongFilter();
        }
        return notificationsentemaillogsRecipientId;
    }

    public void setNotificationsentemaillogsRecipientId(LongFilter notificationsentemaillogsRecipientId) {
        this.notificationsentemaillogsRecipientId = notificationsentemaillogsRecipientId;
    }

    public LongFilter getPcratingsEmployeeId() {
        return pcratingsEmployeeId;
    }

    public LongFilter pcratingsEmployeeId() {
        if (pcratingsEmployeeId == null) {
            pcratingsEmployeeId = new LongFilter();
        }
        return pcratingsEmployeeId;
    }

    public void setPcratingsEmployeeId(LongFilter pcratingsEmployeeId) {
        this.pcratingsEmployeeId = pcratingsEmployeeId;
    }

    public LongFilter getPcratingstrainingsSuccessorforId() {
        return pcratingstrainingsSuccessorforId;
    }

    public LongFilter pcratingstrainingsSuccessorforId() {
        if (pcratingstrainingsSuccessorforId == null) {
            pcratingstrainingsSuccessorforId = new LongFilter();
        }
        return pcratingstrainingsSuccessorforId;
    }

    public void setPcratingstrainingsSuccessorforId(LongFilter pcratingstrainingsSuccessorforId) {
        this.pcratingstrainingsSuccessorforId = pcratingstrainingsSuccessorforId;
    }

    public LongFilter getPerformancecycleemployeeratingEmployeeId() {
        return performancecycleemployeeratingEmployeeId;
    }

    public LongFilter performancecycleemployeeratingEmployeeId() {
        if (performancecycleemployeeratingEmployeeId == null) {
            performancecycleemployeeratingEmployeeId = new LongFilter();
        }
        return performancecycleemployeeratingEmployeeId;
    }

    public void setPerformancecycleemployeeratingEmployeeId(LongFilter performancecycleemployeeratingEmployeeId) {
        this.performancecycleemployeeratingEmployeeId = performancecycleemployeeratingEmployeeId;
    }

    public LongFilter getPerformancecycleemployeeratingManagerId() {
        return performancecycleemployeeratingManagerId;
    }

    public LongFilter performancecycleemployeeratingManagerId() {
        if (performancecycleemployeeratingManagerId == null) {
            performancecycleemployeeratingManagerId = new LongFilter();
        }
        return performancecycleemployeeratingManagerId;
    }

    public void setPerformancecycleemployeeratingManagerId(LongFilter performancecycleemployeeratingManagerId) {
        this.performancecycleemployeeratingManagerId = performancecycleemployeeratingManagerId;
    }

    public LongFilter getProjectcyclesAllowedafterduedatebyId() {
        return projectcyclesAllowedafterduedatebyId;
    }

    public LongFilter projectcyclesAllowedafterduedatebyId() {
        if (projectcyclesAllowedafterduedatebyId == null) {
            projectcyclesAllowedafterduedatebyId = new LongFilter();
        }
        return projectcyclesAllowedafterduedatebyId;
    }

    public void setProjectcyclesAllowedafterduedatebyId(LongFilter projectcyclesAllowedafterduedatebyId) {
        this.projectcyclesAllowedafterduedatebyId = projectcyclesAllowedafterduedatebyId;
    }

    public LongFilter getProjectcyclesArchitectId() {
        return projectcyclesArchitectId;
    }

    public LongFilter projectcyclesArchitectId() {
        if (projectcyclesArchitectId == null) {
            projectcyclesArchitectId = new LongFilter();
        }
        return projectcyclesArchitectId;
    }

    public void setProjectcyclesArchitectId(LongFilter projectcyclesArchitectId) {
        this.projectcyclesArchitectId = projectcyclesArchitectId;
    }

    public LongFilter getProjectcyclesProjectmanagerId() {
        return projectcyclesProjectmanagerId;
    }

    public LongFilter projectcyclesProjectmanagerId() {
        if (projectcyclesProjectmanagerId == null) {
            projectcyclesProjectmanagerId = new LongFilter();
        }
        return projectcyclesProjectmanagerId;
    }

    public void setProjectcyclesProjectmanagerId(LongFilter projectcyclesProjectmanagerId) {
        this.projectcyclesProjectmanagerId = projectcyclesProjectmanagerId;
    }

    public LongFilter getProjectleadershipEmployeeId() {
        return projectleadershipEmployeeId;
    }

    public LongFilter projectleadershipEmployeeId() {
        if (projectleadershipEmployeeId == null) {
            projectleadershipEmployeeId = new LongFilter();
        }
        return projectleadershipEmployeeId;
    }

    public void setProjectleadershipEmployeeId(LongFilter projectleadershipEmployeeId) {
        this.projectleadershipEmployeeId = projectleadershipEmployeeId;
    }

    public LongFilter getProjectsProjectmanagerId() {
        return projectsProjectmanagerId;
    }

    public LongFilter projectsProjectmanagerId() {
        if (projectsProjectmanagerId == null) {
            projectsProjectmanagerId = new LongFilter();
        }
        return projectsProjectmanagerId;
    }

    public void setProjectsProjectmanagerId(LongFilter projectsProjectmanagerId) {
        this.projectsProjectmanagerId = projectsProjectmanagerId;
    }

    public LongFilter getRatingsRaterId() {
        return ratingsRaterId;
    }

    public LongFilter ratingsRaterId() {
        if (ratingsRaterId == null) {
            ratingsRaterId = new LongFilter();
        }
        return ratingsRaterId;
    }

    public void setRatingsRaterId(LongFilter ratingsRaterId) {
        this.ratingsRaterId = ratingsRaterId;
    }

    public LongFilter getUserattributesUserId() {
        return userattributesUserId;
    }

    public LongFilter userattributesUserId() {
        if (userattributesUserId == null) {
            userattributesUserId = new LongFilter();
        }
        return userattributesUserId;
    }

    public void setUserattributesUserId(LongFilter userattributesUserId) {
        this.userattributesUserId = userattributesUserId;
    }

    public LongFilter getUsergoalsUserId() {
        return usergoalsUserId;
    }

    public LongFilter usergoalsUserId() {
        if (usergoalsUserId == null) {
            usergoalsUserId = new LongFilter();
        }
        return usergoalsUserId;
    }

    public void setUsergoalsUserId(LongFilter usergoalsUserId) {
        this.usergoalsUserId = usergoalsUserId;
    }

    public LongFilter getUserratingsUserId() {
        return userratingsUserId;
    }

    public LongFilter userratingsUserId() {
        if (userratingsUserId == null) {
            userratingsUserId = new LongFilter();
        }
        return userratingsUserId;
    }

    public void setUserratingsUserId(LongFilter userratingsUserId) {
        this.userratingsUserId = userratingsUserId;
    }

    public LongFilter getUserratingsReviewmanagerId() {
        return userratingsReviewmanagerId;
    }

    public LongFilter userratingsReviewmanagerId() {
        if (userratingsReviewmanagerId == null) {
            userratingsReviewmanagerId = new LongFilter();
        }
        return userratingsReviewmanagerId;
    }

    public void setUserratingsReviewmanagerId(LongFilter userratingsReviewmanagerId) {
        this.userratingsReviewmanagerId = userratingsReviewmanagerId;
    }

    public LongFilter getUserratingsremarksRaterId() {
        return userratingsremarksRaterId;
    }

    public LongFilter userratingsremarksRaterId() {
        if (userratingsremarksRaterId == null) {
            userratingsremarksRaterId = new LongFilter();
        }
        return userratingsremarksRaterId;
    }

    public void setUserratingsremarksRaterId(LongFilter userratingsremarksRaterId) {
        this.userratingsremarksRaterId = userratingsremarksRaterId;
    }

    public LongFilter getUserrelationsUserId() {
        return userrelationsUserId;
    }

    public LongFilter userrelationsUserId() {
        if (userrelationsUserId == null) {
            userrelationsUserId = new LongFilter();
        }
        return userrelationsUserId;
    }

    public void setUserrelationsUserId(LongFilter userrelationsUserId) {
        this.userrelationsUserId = userrelationsUserId;
    }

    public LongFilter getUserrelationsRelateduserId() {
        return userrelationsRelateduserId;
    }

    public LongFilter userrelationsRelateduserId() {
        if (userrelationsRelateduserId == null) {
            userrelationsRelateduserId = new LongFilter();
        }
        return userrelationsRelateduserId;
    }

    public void setUserrelationsRelateduserId(LongFilter userrelationsRelateduserId) {
        this.userrelationsRelateduserId = userrelationsRelateduserId;
    }

    public LongFilter getWorklogsEmployeeId() {
        return worklogsEmployeeId;
    }

    public LongFilter worklogsEmployeeId() {
        if (worklogsEmployeeId == null) {
            worklogsEmployeeId = new LongFilter();
        }
        return worklogsEmployeeId;
    }

    public void setWorklogsEmployeeId(LongFilter worklogsEmployeeId) {
        this.worklogsEmployeeId = worklogsEmployeeId;
    }

    public LongFilter getZemployeeprojectsEmployeeId() {
        return zemployeeprojectsEmployeeId;
    }

    public LongFilter zemployeeprojectsEmployeeId() {
        if (zemployeeprojectsEmployeeId == null) {
            zemployeeprojectsEmployeeId = new LongFilter();
        }
        return zemployeeprojectsEmployeeId;
    }

    public void setZemployeeprojectsEmployeeId(LongFilter zemployeeprojectsEmployeeId) {
        this.zemployeeprojectsEmployeeId = zemployeeprojectsEmployeeId;
    }

    public LongFilter getZemployeeprojectsAssignorId() {
        return zemployeeprojectsAssignorId;
    }

    public LongFilter zemployeeprojectsAssignorId() {
        if (zemployeeprojectsAssignorId == null) {
            zemployeeprojectsAssignorId = new LongFilter();
        }
        return zemployeeprojectsAssignorId;
    }

    public void setZemployeeprojectsAssignorId(LongFilter zemployeeprojectsAssignorId) {
        this.zemployeeprojectsAssignorId = zemployeeprojectsAssignorId;
    }

    public LongFilter getZemployeeprojectsProjectmanagerId() {
        return zemployeeprojectsProjectmanagerId;
    }

    public LongFilter zemployeeprojectsProjectmanagerId() {
        if (zemployeeprojectsProjectmanagerId == null) {
            zemployeeprojectsProjectmanagerId = new LongFilter();
        }
        return zemployeeprojectsProjectmanagerId;
    }

    public void setZemployeeprojectsProjectmanagerId(LongFilter zemployeeprojectsProjectmanagerId) {
        this.zemployeeprojectsProjectmanagerId = zemployeeprojectsProjectmanagerId;
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
        final EmployeesCriteria that = (EmployeesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(firstname, that.firstname) &&
            Objects.equals(lastname, that.lastname) &&
            Objects.equals(phonenumber, that.phonenumber) &&
            Objects.equals(dateofbirth, that.dateofbirth) &&
            Objects.equals(email, that.email) &&
            Objects.equals(skype, that.skype) &&
            Objects.equals(employeeDesignation, that.employeeDesignation) &&
            Objects.equals(joiningdate, that.joiningdate) &&
            Objects.equals(area, that.area) &&
            Objects.equals(leavingdate, that.leavingdate) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(isactive, that.isactive) &&
            Objects.equals(googleid, that.googleid) &&
            Objects.equals(oracleid, that.oracleid) &&
            Objects.equals(deptt, that.deptt) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(genderid, that.genderid) &&
            Objects.equals(onprobation, that.onprobation) &&
            Objects.equals(employeeCompetency, that.employeeCompetency) &&
            Objects.equals(resourcetype, that.resourcetype) &&
            Objects.equals(grade, that.grade) &&
            Objects.equals(subgrade, that.subgrade) &&
            Objects.equals(imageurl, that.imageurl) &&
            Objects.equals(resignationdate, that.resignationdate) &&
            Objects.equals(graduationdate, that.graduationdate) &&
            Objects.equals(careerstartdate, that.careerstartdate) &&
            Objects.equals(externalexpyears, that.externalexpyears) &&
            Objects.equals(externalexpmonths, that.externalexpmonths) &&
            Objects.equals(placeofbirth, that.placeofbirth) &&
            Objects.equals(hireddate, that.hireddate) &&
            Objects.equals(lasttrackingupdate, that.lasttrackingupdate) &&
            Objects.equals(middlename, that.middlename) &&
            Objects.equals(mobilenumber, that.mobilenumber) &&
            Objects.equals(resignationtype, that.resignationtype) &&
            Objects.equals(primaryreasonforleaving, that.primaryreasonforleaving) &&
            Objects.equals(secondaryreasonforleaving, that.secondaryreasonforleaving) &&
            Objects.equals(noticeperiodduration, that.noticeperiodduration) &&
            Objects.equals(noticeperiodserved, that.noticeperiodserved) &&
            Objects.equals(probationperiodduration, that.probationperiodduration) &&
            Objects.equals(locationId, that.locationId) &&
            Objects.equals(roleId, that.roleId) &&
            Objects.equals(managerId, that.managerId) &&
            Objects.equals(leaveId, that.leaveId) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(businessunitId, that.businessunitId) &&
            Objects.equals(divisionId, that.divisionId) &&
            Objects.equals(competencyId, that.competencyId) &&
            Objects.equals(employmentstatusId, that.employmentstatusId) &&
            Objects.equals(employmenttypeId, that.employmenttypeId) &&
            Objects.equals(designationId, that.designationId) &&
            Objects.equals(claimrequestviewsEmployeeId, that.claimrequestviewsEmployeeId) &&
            Objects.equals(claimrequestsEmployeeId, that.claimrequestsEmployeeId) &&
            Objects.equals(dealresourcesEmployeeId, that.dealresourcesEmployeeId) &&
            Objects.equals(employeeaddressesEmployeeId, that.employeeaddressesEmployeeId) &&
            Objects.equals(employeebirthdaysEmployeeId, that.employeebirthdaysEmployeeId) &&
            Objects.equals(employeecertificatesEmployeeId, that.employeecertificatesEmployeeId) &&
            Objects.equals(employeecommentsCommenterId, that.employeecommentsCommenterId) &&
            Objects.equals(employeecommentsEmployeeId, that.employeecommentsEmployeeId) &&
            Objects.equals(employeecompensationEmployeeId, that.employeecompensationEmployeeId) &&
            Objects.equals(employeecontactsEmployeeId, that.employeecontactsEmployeeId) &&
            Objects.equals(employeedetailsEmployeeId, that.employeedetailsEmployeeId) &&
            Objects.equals(employeedocumentsEmployeeId, that.employeedocumentsEmployeeId) &&
            Objects.equals(employeeeducationEmployeeId, that.employeeeducationEmployeeId) &&
            Objects.equals(employeeemergencycontactsEmployeeId, that.employeeemergencycontactsEmployeeId) &&
            Objects.equals(employeefamilyinfoEmployeeId, that.employeefamilyinfoEmployeeId) &&
            Objects.equals(employeejobinfoEmployeeId, that.employeejobinfoEmployeeId) &&
            Objects.equals(employeejobinfoReviewmanagerId, that.employeejobinfoReviewmanagerId) &&
            Objects.equals(employeejobinfoManagerId, that.employeejobinfoManagerId) &&
            Objects.equals(employeeprofilehistorylogsEmployeeId, that.employeeprofilehistorylogsEmployeeId) &&
            Objects.equals(employeeprojectratingsProjectarchitectId, that.employeeprojectratingsProjectarchitectId) &&
            Objects.equals(employeeprojectratingsProjectmanagerId, that.employeeprojectratingsProjectmanagerId) &&
            Objects.equals(employeeprojectratingsEmployeeId, that.employeeprojectratingsEmployeeId) &&
            Objects.equals(employeeprojectsEmployeeId, that.employeeprojectsEmployeeId) &&
            Objects.equals(employeeprojectsAssignorId, that.employeeprojectsAssignorId) &&
            Objects.equals(employeeskillsEmployeeId, that.employeeskillsEmployeeId) &&
            Objects.equals(employeetalentsEmployeeId, that.employeetalentsEmployeeId) &&
            Objects.equals(employeeworksEmployeeId, that.employeeworksEmployeeId) &&
            Objects.equals(employeesManagerId, that.employeesManagerId) &&
            Objects.equals(employmenthistoryEmployeeId, that.employmenthistoryEmployeeId) &&
            Objects.equals(feedbackquestionsEmployeeId, that.feedbackquestionsEmployeeId) &&
            Objects.equals(feedbackrequestsEmployeeId, that.feedbackrequestsEmployeeId) &&
            Objects.equals(feedbackrequestsLinemanagerId, that.feedbackrequestsLinemanagerId) &&
            Objects.equals(feedbackrespondentsEmployeeId, that.feedbackrespondentsEmployeeId) &&
            Objects.equals(interviewsEmployeeId, that.interviewsEmployeeId) &&
            Objects.equals(leavedetailadjustmentlogsAdjustedbyId, that.leavedetailadjustmentlogsAdjustedbyId) &&
            Objects.equals(leaverequestapproversUserId, that.leaverequestapproversUserId) &&
            Objects.equals(leaverequestsoldsManagerId, that.leaverequestsoldsManagerId) &&
            Objects.equals(leaverequestsoldsEmployeeId, that.leaverequestsoldsEmployeeId) &&
            Objects.equals(leavesUserId, that.leavesUserId) &&
            Objects.equals(notificationsentemaillogsRecipientId, that.notificationsentemaillogsRecipientId) &&
            Objects.equals(pcratingsEmployeeId, that.pcratingsEmployeeId) &&
            Objects.equals(pcratingstrainingsSuccessorforId, that.pcratingstrainingsSuccessorforId) &&
            Objects.equals(performancecycleemployeeratingEmployeeId, that.performancecycleemployeeratingEmployeeId) &&
            Objects.equals(performancecycleemployeeratingManagerId, that.performancecycleemployeeratingManagerId) &&
            Objects.equals(projectcyclesAllowedafterduedatebyId, that.projectcyclesAllowedafterduedatebyId) &&
            Objects.equals(projectcyclesArchitectId, that.projectcyclesArchitectId) &&
            Objects.equals(projectcyclesProjectmanagerId, that.projectcyclesProjectmanagerId) &&
            Objects.equals(projectleadershipEmployeeId, that.projectleadershipEmployeeId) &&
            Objects.equals(projectsProjectmanagerId, that.projectsProjectmanagerId) &&
            Objects.equals(ratingsRaterId, that.ratingsRaterId) &&
            Objects.equals(userattributesUserId, that.userattributesUserId) &&
            Objects.equals(usergoalsUserId, that.usergoalsUserId) &&
            Objects.equals(userratingsUserId, that.userratingsUserId) &&
            Objects.equals(userratingsReviewmanagerId, that.userratingsReviewmanagerId) &&
            Objects.equals(userratingsremarksRaterId, that.userratingsremarksRaterId) &&
            Objects.equals(userrelationsUserId, that.userrelationsUserId) &&
            Objects.equals(userrelationsRelateduserId, that.userrelationsRelateduserId) &&
            Objects.equals(worklogsEmployeeId, that.worklogsEmployeeId) &&
            Objects.equals(zemployeeprojectsEmployeeId, that.zemployeeprojectsEmployeeId) &&
            Objects.equals(zemployeeprojectsAssignorId, that.zemployeeprojectsAssignorId) &&
            Objects.equals(zemployeeprojectsProjectmanagerId, that.zemployeeprojectsProjectmanagerId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            firstname,
            lastname,
            phonenumber,
            dateofbirth,
            email,
            skype,
            employeeDesignation,
            joiningdate,
            area,
            leavingdate,
            notes,
            isactive,
            googleid,
            oracleid,
            deptt,
            createdat,
            updatedat,
            genderid,
            onprobation,
            employeeCompetency,
            resourcetype,
            grade,
            subgrade,
            imageurl,
            resignationdate,
            graduationdate,
            careerstartdate,
            externalexpyears,
            externalexpmonths,
            placeofbirth,
            hireddate,
            lasttrackingupdate,
            middlename,
            mobilenumber,
            resignationtype,
            primaryreasonforleaving,
            secondaryreasonforleaving,
            noticeperiodduration,
            noticeperiodserved,
            probationperiodduration,
            locationId,
            roleId,
            managerId,
            leaveId,
            departmentId,
            businessunitId,
            divisionId,
            competencyId,
            employmentstatusId,
            employmenttypeId,
            designationId,
            claimrequestviewsEmployeeId,
            claimrequestsEmployeeId,
            dealresourcesEmployeeId,
            employeeaddressesEmployeeId,
            employeebirthdaysEmployeeId,
            employeecertificatesEmployeeId,
            employeecommentsCommenterId,
            employeecommentsEmployeeId,
            employeecompensationEmployeeId,
            employeecontactsEmployeeId,
            employeedetailsEmployeeId,
            employeedocumentsEmployeeId,
            employeeeducationEmployeeId,
            employeeemergencycontactsEmployeeId,
            employeefamilyinfoEmployeeId,
            employeejobinfoEmployeeId,
            employeejobinfoReviewmanagerId,
            employeejobinfoManagerId,
            employeeprofilehistorylogsEmployeeId,
            employeeprojectratingsProjectarchitectId,
            employeeprojectratingsProjectmanagerId,
            employeeprojectratingsEmployeeId,
            employeeprojectsEmployeeId,
            employeeprojectsAssignorId,
            employeeskillsEmployeeId,
            employeetalentsEmployeeId,
            employeeworksEmployeeId,
            employeesManagerId,
            employmenthistoryEmployeeId,
            feedbackquestionsEmployeeId,
            feedbackrequestsEmployeeId,
            feedbackrequestsLinemanagerId,
            feedbackrespondentsEmployeeId,
            interviewsEmployeeId,
            leavedetailadjustmentlogsAdjustedbyId,
            leaverequestapproversUserId,
            leaverequestsoldsManagerId,
            leaverequestsoldsEmployeeId,
            leavesUserId,
            notificationsentemaillogsRecipientId,
            pcratingsEmployeeId,
            pcratingstrainingsSuccessorforId,
            performancecycleemployeeratingEmployeeId,
            performancecycleemployeeratingManagerId,
            projectcyclesAllowedafterduedatebyId,
            projectcyclesArchitectId,
            projectcyclesProjectmanagerId,
            projectleadershipEmployeeId,
            projectsProjectmanagerId,
            ratingsRaterId,
            userattributesUserId,
            usergoalsUserId,
            userratingsUserId,
            userratingsReviewmanagerId,
            userratingsremarksRaterId,
            userrelationsUserId,
            userrelationsRelateduserId,
            worklogsEmployeeId,
            zemployeeprojectsEmployeeId,
            zemployeeprojectsAssignorId,
            zemployeeprojectsProjectmanagerId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (firstname != null ? "firstname=" + firstname + ", " : "") +
            (lastname != null ? "lastname=" + lastname + ", " : "") +
            (phonenumber != null ? "phonenumber=" + phonenumber + ", " : "") +
            (dateofbirth != null ? "dateofbirth=" + dateofbirth + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (skype != null ? "skype=" + skype + ", " : "") +
            (employeeDesignation != null ? "employeeDesignation=" + employeeDesignation + ", " : "") +
            (joiningdate != null ? "joiningdate=" + joiningdate + ", " : "") +
            (area != null ? "area=" + area + ", " : "") +
            (leavingdate != null ? "leavingdate=" + leavingdate + ", " : "") +
            (notes != null ? "notes=" + notes + ", " : "") +
            (isactive != null ? "isactive=" + isactive + ", " : "") +
            (googleid != null ? "googleid=" + googleid + ", " : "") +
            (oracleid != null ? "oracleid=" + oracleid + ", " : "") +
            (deptt != null ? "deptt=" + deptt + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (genderid != null ? "genderid=" + genderid + ", " : "") +
            (onprobation != null ? "onprobation=" + onprobation + ", " : "") +
            (employeeCompetency != null ? "employeeCompetency=" + employeeCompetency + ", " : "") +
            (resourcetype != null ? "resourcetype=" + resourcetype + ", " : "") +
            (grade != null ? "grade=" + grade + ", " : "") +
            (subgrade != null ? "subgrade=" + subgrade + ", " : "") +
            (imageurl != null ? "imageurl=" + imageurl + ", " : "") +
            (resignationdate != null ? "resignationdate=" + resignationdate + ", " : "") +
            (graduationdate != null ? "graduationdate=" + graduationdate + ", " : "") +
            (careerstartdate != null ? "careerstartdate=" + careerstartdate + ", " : "") +
            (externalexpyears != null ? "externalexpyears=" + externalexpyears + ", " : "") +
            (externalexpmonths != null ? "externalexpmonths=" + externalexpmonths + ", " : "") +
            (placeofbirth != null ? "placeofbirth=" + placeofbirth + ", " : "") +
            (hireddate != null ? "hireddate=" + hireddate + ", " : "") +
            (lasttrackingupdate != null ? "lasttrackingupdate=" + lasttrackingupdate + ", " : "") +
            (middlename != null ? "middlename=" + middlename + ", " : "") +
            (mobilenumber != null ? "mobilenumber=" + mobilenumber + ", " : "") +
            (resignationtype != null ? "resignationtype=" + resignationtype + ", " : "") +
            (primaryreasonforleaving != null ? "primaryreasonforleaving=" + primaryreasonforleaving + ", " : "") +
            (secondaryreasonforleaving != null ? "secondaryreasonforleaving=" + secondaryreasonforleaving + ", " : "") +
            (noticeperiodduration != null ? "noticeperiodduration=" + noticeperiodduration + ", " : "") +
            (noticeperiodserved != null ? "noticeperiodserved=" + noticeperiodserved + ", " : "") +
            (probationperiodduration != null ? "probationperiodduration=" + probationperiodduration + ", " : "") +
            (locationId != null ? "locationId=" + locationId + ", " : "") +
            (roleId != null ? "roleId=" + roleId + ", " : "") +
            (managerId != null ? "managerId=" + managerId + ", " : "") +
            (leaveId != null ? "leaveId=" + leaveId + ", " : "") +
            (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            (businessunitId != null ? "businessunitId=" + businessunitId + ", " : "") +
            (divisionId != null ? "divisionId=" + divisionId + ", " : "") +
            (competencyId != null ? "competencyId=" + competencyId + ", " : "") +
            (employmentstatusId != null ? "employmentstatusId=" + employmentstatusId + ", " : "") +
            (employmenttypeId != null ? "employmenttypeId=" + employmenttypeId + ", " : "") +
            (designationId != null ? "designationId=" + designationId + ", " : "") +
            (claimrequestviewsEmployeeId != null ? "claimrequestviewsEmployeeId=" + claimrequestviewsEmployeeId + ", " : "") +
            (claimrequestsEmployeeId != null ? "claimrequestsEmployeeId=" + claimrequestsEmployeeId + ", " : "") +
            (dealresourcesEmployeeId != null ? "dealresourcesEmployeeId=" + dealresourcesEmployeeId + ", " : "") +
            (employeeaddressesEmployeeId != null ? "employeeaddressesEmployeeId=" + employeeaddressesEmployeeId + ", " : "") +
            (employeebirthdaysEmployeeId != null ? "employeebirthdaysEmployeeId=" + employeebirthdaysEmployeeId + ", " : "") +
            (employeecertificatesEmployeeId != null ? "employeecertificatesEmployeeId=" + employeecertificatesEmployeeId + ", " : "") +
            (employeecommentsCommenterId != null ? "employeecommentsCommenterId=" + employeecommentsCommenterId + ", " : "") +
            (employeecommentsEmployeeId != null ? "employeecommentsEmployeeId=" + employeecommentsEmployeeId + ", " : "") +
            (employeecompensationEmployeeId != null ? "employeecompensationEmployeeId=" + employeecompensationEmployeeId + ", " : "") +
            (employeecontactsEmployeeId != null ? "employeecontactsEmployeeId=" + employeecontactsEmployeeId + ", " : "") +
            (employeedetailsEmployeeId != null ? "employeedetailsEmployeeId=" + employeedetailsEmployeeId + ", " : "") +
            (employeedocumentsEmployeeId != null ? "employeedocumentsEmployeeId=" + employeedocumentsEmployeeId + ", " : "") +
            (employeeeducationEmployeeId != null ? "employeeeducationEmployeeId=" + employeeeducationEmployeeId + ", " : "") +
            (employeeemergencycontactsEmployeeId != null ? "employeeemergencycontactsEmployeeId=" + employeeemergencycontactsEmployeeId + ", " : "") +
            (employeefamilyinfoEmployeeId != null ? "employeefamilyinfoEmployeeId=" + employeefamilyinfoEmployeeId + ", " : "") +
            (employeejobinfoEmployeeId != null ? "employeejobinfoEmployeeId=" + employeejobinfoEmployeeId + ", " : "") +
            (employeejobinfoReviewmanagerId != null ? "employeejobinfoReviewmanagerId=" + employeejobinfoReviewmanagerId + ", " : "") +
            (employeejobinfoManagerId != null ? "employeejobinfoManagerId=" + employeejobinfoManagerId + ", " : "") +
            (employeeprofilehistorylogsEmployeeId != null ? "employeeprofilehistorylogsEmployeeId=" + employeeprofilehistorylogsEmployeeId + ", " : "") +
            (employeeprojectratingsProjectarchitectId != null ? "employeeprojectratingsProjectarchitectId=" + employeeprojectratingsProjectarchitectId + ", " : "") +
            (employeeprojectratingsProjectmanagerId != null ? "employeeprojectratingsProjectmanagerId=" + employeeprojectratingsProjectmanagerId + ", " : "") +
            (employeeprojectratingsEmployeeId != null ? "employeeprojectratingsEmployeeId=" + employeeprojectratingsEmployeeId + ", " : "") +
            (employeeprojectsEmployeeId != null ? "employeeprojectsEmployeeId=" + employeeprojectsEmployeeId + ", " : "") +
            (employeeprojectsAssignorId != null ? "employeeprojectsAssignorId=" + employeeprojectsAssignorId + ", " : "") +
            (employeeskillsEmployeeId != null ? "employeeskillsEmployeeId=" + employeeskillsEmployeeId + ", " : "") +
            (employeetalentsEmployeeId != null ? "employeetalentsEmployeeId=" + employeetalentsEmployeeId + ", " : "") +
            (employeeworksEmployeeId != null ? "employeeworksEmployeeId=" + employeeworksEmployeeId + ", " : "") +
            (employeesManagerId != null ? "employeesManagerId=" + employeesManagerId + ", " : "") +
            (employmenthistoryEmployeeId != null ? "employmenthistoryEmployeeId=" + employmenthistoryEmployeeId + ", " : "") +
            (feedbackquestionsEmployeeId != null ? "feedbackquestionsEmployeeId=" + feedbackquestionsEmployeeId + ", " : "") +
            (feedbackrequestsEmployeeId != null ? "feedbackrequestsEmployeeId=" + feedbackrequestsEmployeeId + ", " : "") +
            (feedbackrequestsLinemanagerId != null ? "feedbackrequestsLinemanagerId=" + feedbackrequestsLinemanagerId + ", " : "") +
            (feedbackrespondentsEmployeeId != null ? "feedbackrespondentsEmployeeId=" + feedbackrespondentsEmployeeId + ", " : "") +
            (interviewsEmployeeId != null ? "interviewsEmployeeId=" + interviewsEmployeeId + ", " : "") +
            (leavedetailadjustmentlogsAdjustedbyId != null ? "leavedetailadjustmentlogsAdjustedbyId=" + leavedetailadjustmentlogsAdjustedbyId + ", " : "") +
            (leaverequestapproversUserId != null ? "leaverequestapproversUserId=" + leaverequestapproversUserId + ", " : "") +
            (leaverequestsoldsManagerId != null ? "leaverequestsoldsManagerId=" + leaverequestsoldsManagerId + ", " : "") +
            (leaverequestsoldsEmployeeId != null ? "leaverequestsoldsEmployeeId=" + leaverequestsoldsEmployeeId + ", " : "") +
            (leavesUserId != null ? "leavesUserId=" + leavesUserId + ", " : "") +
            (notificationsentemaillogsRecipientId != null ? "notificationsentemaillogsRecipientId=" + notificationsentemaillogsRecipientId + ", " : "") +
            (pcratingsEmployeeId != null ? "pcratingsEmployeeId=" + pcratingsEmployeeId + ", " : "") +
            (pcratingstrainingsSuccessorforId != null ? "pcratingstrainingsSuccessorforId=" + pcratingstrainingsSuccessorforId + ", " : "") +
            (performancecycleemployeeratingEmployeeId != null ? "performancecycleemployeeratingEmployeeId=" + performancecycleemployeeratingEmployeeId + ", " : "") +
            (performancecycleemployeeratingManagerId != null ? "performancecycleemployeeratingManagerId=" + performancecycleemployeeratingManagerId + ", " : "") +
            (projectcyclesAllowedafterduedatebyId != null ? "projectcyclesAllowedafterduedatebyId=" + projectcyclesAllowedafterduedatebyId + ", " : "") +
            (projectcyclesArchitectId != null ? "projectcyclesArchitectId=" + projectcyclesArchitectId + ", " : "") +
            (projectcyclesProjectmanagerId != null ? "projectcyclesProjectmanagerId=" + projectcyclesProjectmanagerId + ", " : "") +
            (projectleadershipEmployeeId != null ? "projectleadershipEmployeeId=" + projectleadershipEmployeeId + ", " : "") +
            (projectsProjectmanagerId != null ? "projectsProjectmanagerId=" + projectsProjectmanagerId + ", " : "") +
            (ratingsRaterId != null ? "ratingsRaterId=" + ratingsRaterId + ", " : "") +
            (userattributesUserId != null ? "userattributesUserId=" + userattributesUserId + ", " : "") +
            (usergoalsUserId != null ? "usergoalsUserId=" + usergoalsUserId + ", " : "") +
            (userratingsUserId != null ? "userratingsUserId=" + userratingsUserId + ", " : "") +
            (userratingsReviewmanagerId != null ? "userratingsReviewmanagerId=" + userratingsReviewmanagerId + ", " : "") +
            (userratingsremarksRaterId != null ? "userratingsremarksRaterId=" + userratingsremarksRaterId + ", " : "") +
            (userrelationsUserId != null ? "userrelationsUserId=" + userrelationsUserId + ", " : "") +
            (userrelationsRelateduserId != null ? "userrelationsRelateduserId=" + userrelationsRelateduserId + ", " : "") +
            (worklogsEmployeeId != null ? "worklogsEmployeeId=" + worklogsEmployeeId + ", " : "") +
            (zemployeeprojectsEmployeeId != null ? "zemployeeprojectsEmployeeId=" + zemployeeprojectsEmployeeId + ", " : "") +
            (zemployeeprojectsAssignorId != null ? "zemployeeprojectsAssignorId=" + zemployeeprojectsAssignorId + ", " : "") +
            (zemployeeprojectsProjectmanagerId != null ? "zemployeeprojectsProjectmanagerId=" + zemployeeprojectsProjectmanagerId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
