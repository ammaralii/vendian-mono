package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Employees.
 */
@Entity
@Table(name = "employees")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Employees implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "firstname", length = 255)
    private String firstname;

    @Size(max = 255)
    @Column(name = "lastname", length = 255)
    private String lastname;

    @Size(max = 255)
    @Column(name = "phonenumber", length = 255)
    private String phonenumber;

    @Column(name = "dateofbirth")
    private Instant dateofbirth;

    @Size(max = 255)
    @Column(name = "email", length = 255)
    private String email;

    @Size(max = 255)
    @Column(name = "skype", length = 255)
    private String skype;

    @Size(max = 255)
    @Column(name = "employee_designation", length = 255)
    private String employeeDesignation;

    @Column(name = "joiningdate")
    private Instant joiningdate;

    @Size(max = 255)
    @Column(name = "area", length = 255)
    private String area;

    @Column(name = "leavingdate")
    private Instant leavingdate;

    @Size(max = 65535)
    @Column(name = "notes", length = 65535)
    private String notes;

    @Column(name = "isactive")
    private Boolean isactive;

    @Size(max = 255)
    @Column(name = "googleid", length = 255)
    private String googleid;

    @Size(max = 255)
    @Column(name = "oracleid", length = 255)
    private String oracleid;

    @Size(max = 255)
    @Column(name = "deptt", length = 255)
    private String deptt;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @NotNull
    @Size(max = 6)
    @Column(name = "genderid", length = 6, nullable = false)
    private String genderid;

    @Column(name = "onprobation")
    private Boolean onprobation;

    @Size(max = 255)
    @Column(name = "employee_competency", length = 255)
    private String employeeCompetency;

    @Size(max = 12)
    @Column(name = "resourcetype", length = 12)
    private String resourcetype;

    @Size(max = 255)
    @Column(name = "grade", length = 255)
    private String grade;

    @Size(max = 255)
    @Column(name = "subgrade", length = 255)
    private String subgrade;

    @Size(max = 255)
    @Column(name = "imageurl", length = 255)
    private String imageurl;

    @Column(name = "resignationdate")
    private Instant resignationdate;

    @Column(name = "graduationdate")
    private LocalDate graduationdate;

    @Column(name = "careerstartdate")
    private LocalDate careerstartdate;

    @Column(name = "externalexpyears")
    private Integer externalexpyears;

    @Column(name = "externalexpmonths", precision = 21, scale = 2)
    private BigDecimal externalexpmonths;

    @Size(max = 255)
    @Column(name = "placeofbirth", length = 255)
    private String placeofbirth;

    @Column(name = "hireddate")
    private Instant hireddate;

    @Column(name = "lasttrackingupdate")
    private Instant lasttrackingupdate;

    @Size(max = 255)
    @Column(name = "middlename", length = 255)
    private String middlename;

    @Lob
    @Column(name = "grosssalary")
    private byte[] grosssalary;

    @Column(name = "grosssalary_content_type")
    private String grosssalaryContentType;

    @Lob
    @Column(name = "fuelallowance")
    private byte[] fuelallowance;

    @Column(name = "fuelallowance_content_type")
    private String fuelallowanceContentType;

    @Size(max = 255)
    @Column(name = "mobilenumber", length = 255)
    private String mobilenumber;

    @Size(max = 255)
    @Column(name = "resignationtype", length = 255)
    private String resignationtype;

    @Size(max = 255)
    @Column(name = "primaryreasonforleaving", length = 255)
    private String primaryreasonforleaving;

    @Size(max = 65535)
    @Column(name = "secondaryreasonforleaving", length = 65535)
    private String secondaryreasonforleaving;

    @Column(name = "noticeperiodduration")
    private Integer noticeperiodduration;

    @Column(name = "noticeperiodserved")
    private Integer noticeperiodserved;

    @Column(name = "probationperiodduration")
    private Integer probationperiodduration;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employeesLocations" }, allowSetters = true)
    private Locations location;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employeerolesRoles", "employeesRoles", "rolepermissionsRoles" }, allowSetters = true)
    private Roles role;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "location",
            "role",
            "manager",
            "leave",
            "department",
            "businessunit",
            "division",
            "competency",
            "employmentstatus",
            "employmenttype",
            "designation",
            "claimrequestviewsEmployees",
            "claimrequestsEmployees",
            "dealresourcesEmployees",
            "employeeaddressesEmployees",
            "employeebirthdaysEmployees",
            "employeecertificatesEmployees",
            "employeecommentsCommenters",
            "employeecommentsEmployees",
            "employeecompensationEmployees",
            "employeecontactsEmployees",
            "employeedetailsEmployees",
            "employeedocumentsEmployees",
            "employeeeducationEmployees",
            "employeeemergencycontactsEmployees",
            "employeefamilyinfoEmployees",
            "employeejobinfoEmployees",
            "employeejobinfoReviewmanagers",
            "employeejobinfoManagers",
            "employeeprofilehistorylogsEmployees",
            "employeeprojectratingsProjectarchitects",
            "employeeprojectratingsProjectmanagers",
            "employeeprojectratingsEmployees",
            "employeeprojectsEmployees",
            "employeeprojectsAssignors",
            "employeeskillsEmployees",
            "employeetalentsEmployees",
            "employeeworksEmployees",
            "employeesManagers",
            "employmenthistoryEmployees",
            "feedbackquestionsEmployees",
            "feedbackrequestsEmployees",
            "feedbackrequestsLinemanagers",
            "feedbackrespondentsEmployees",
            "interviewsEmployees",
            "leavedetailadjustmentlogsAdjustedbies",
            "leaverequestapproversUsers",
            "leaverequestsoldsManagers",
            "leaverequestsoldsEmployees",
            "leavesUsers",
            "notificationsentemaillogsRecipients",
            "pcratingsEmployees",
            "pcratingstrainingsSuccessorfors",
            "performancecycleemployeeratingEmployees",
            "performancecycleemployeeratingManagers",
            "projectcyclesAllowedafterduedatebies",
            "projectcyclesArchitects",
            "projectcyclesProjectmanagers",
            "projectleadershipEmployees",
            "projectsProjectmanagers",
            "ratingsRaters",
            "userattributesUsers",
            "usergoalsUsers",
            "userratingsUsers",
            "userratingsReviewmanagers",
            "userratingsremarksRaters",
            "userrelationsUsers",
            "userrelationsRelatedusers",
            "worklogsEmployees",
            "zemployeeprojectsEmployees",
            "zemployeeprojectsAssignors",
            "zemployeeprojectsProjectmanagers",
        },
        allowSetters = true
    )
    private Employees manager;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employeesLeaves" }, allowSetters = true)
    private LeavesOlds leave;

    @ManyToOne
    @JsonIgnoreProperties(value = { "division", "employeejobinfoDepartments", "employeesDepartments" }, allowSetters = true)
    private Departments department;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "employeejobinfoBusinessunits", "employeesBusinessunits", "projectsBusinessunits" },
        allowSetters = true
    )
    private BusinessUnits businessunit;

    @ManyToOne
    @JsonIgnoreProperties(value = { "departmentsDivisions", "employeejobinfoDivisions", "employeesDivisions" }, allowSetters = true)
    private Divisions division;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employeejobinfoCompetencies", "employeesCompetencies", "tracksCompetencies" }, allowSetters = true)
    private Competencies competency;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employeesEmploymentstatuses" }, allowSetters = true)
    private EmploymentStatuses employmentstatus;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employeejobinfoEmploymenttypes", "employeesEmploymenttypes" }, allowSetters = true)
    private EmploymentTypes employmenttype;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "designationjobdescriptionsDesignations",
            "employeejobinfoDesignations",
            "employeesDesignations",
            "userratingsremarksDesignationafterpromotions",
            "userratingsremarksDesignationafterredesignations",
        },
        allowSetters = true
    )
    private Designations designation;

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
    private Set<ClaimRequestViews> claimrequestviewsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(
        value = { "employee", "claimapproversClaimrequests", "claimdetailsClaimrequests", "claimimagesClaimrequests" },
        allowSetters = true
    )
    private Set<ClaimRequests> claimrequestsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(
        value = { "employee", "dealrequirementmatchingresourcesResources", "dealresourceskillsResources" },
        allowSetters = true
    )
    private Set<DealResources> dealresourcesEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "address", "employee" }, allowSetters = true)
    private Set<EmployeeAddresses> employeeaddressesEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
    private Set<EmployeeBirthdays> employeebirthdaysEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
    private Set<EmployeeCertificates> employeecertificatesEmployees = new HashSet<>();

    @OneToMany(mappedBy = "commenter")
    @JsonIgnoreProperties(value = { "document", "commenter", "employee" }, allowSetters = true)
    private Set<EmployeeComments> employeecommentsCommenters = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "document", "commenter", "employee" }, allowSetters = true)
    private Set<EmployeeComments> employeecommentsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee", "reason", "compensationbenefitsEmployeecompensations" }, allowSetters = true)
    private Set<EmployeeCompensation> employeecompensationEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
    private Set<EmployeeContacts> employeecontactsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
    private Set<EmployeeDetails> employeedetailsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "document", "employee" }, allowSetters = true)
    private Set<EmployeeDocuments> employeedocumentsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "qualificationtype", "employee" }, allowSetters = true)
    private Set<EmployeeEducation> employeeeducationEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
    private Set<EmployeeEmergencyContacts> employeeemergencycontactsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
    private Set<EmployeeFamilyInfo> employeefamilyinfoEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(
        value = {
            "employee",
            "designation",
            "reviewmanager",
            "manager",
            "department",
            "employmenttype",
            "jobdescription",
            "division",
            "businessunit",
            "competency",
        },
        allowSetters = true
    )
    private Set<EmployeeJobInfo> employeejobinfoEmployees = new HashSet<>();

    @OneToMany(mappedBy = "reviewmanager")
    @JsonIgnoreProperties(
        value = {
            "employee",
            "designation",
            "reviewmanager",
            "manager",
            "department",
            "employmenttype",
            "jobdescription",
            "division",
            "businessunit",
            "competency",
        },
        allowSetters = true
    )
    private Set<EmployeeJobInfo> employeejobinfoReviewmanagers = new HashSet<>();

    @OneToMany(mappedBy = "manager")
    @JsonIgnoreProperties(
        value = {
            "employee",
            "designation",
            "reviewmanager",
            "manager",
            "department",
            "employmenttype",
            "jobdescription",
            "division",
            "businessunit",
            "competency",
        },
        allowSetters = true
    )
    private Set<EmployeeJobInfo> employeejobinfoManagers = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
    private Set<EmployeeProfileHistoryLogs> employeeprofilehistorylogsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "projectarchitect")
    @JsonIgnoreProperties(value = { "projectarchitect", "projectmanager", "employee", "projectcycle" }, allowSetters = true)
    private Set<EmployeeProjectRatings> employeeprojectratingsProjectarchitects = new HashSet<>();

    @OneToMany(mappedBy = "projectmanager")
    @JsonIgnoreProperties(value = { "projectarchitect", "projectmanager", "employee", "projectcycle" }, allowSetters = true)
    private Set<EmployeeProjectRatings> employeeprojectratingsProjectmanagers = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "projectarchitect", "projectmanager", "employee", "projectcycle" }, allowSetters = true)
    private Set<EmployeeProjectRatings> employeeprojectratingsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(
        value = { "employee", "project", "assignor", "employeeprojectrolesEmployeeprojects", "zemployeeprojectsEmployeeprojects" },
        allowSetters = true
    )
    private Set<EmployeeProjects> employeeprojectsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "assignor")
    @JsonIgnoreProperties(
        value = { "employee", "project", "assignor", "employeeprojectrolesEmployeeprojects", "zemployeeprojectsEmployeeprojects" },
        allowSetters = true
    )
    private Set<EmployeeProjects> employeeprojectsAssignors = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee", "skill" }, allowSetters = true)
    private Set<EmployeeSkills> employeeskillsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
    private Set<EmployeeTalents> employeetalentsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee", "company" }, allowSetters = true)
    private Set<EmployeeWorks> employeeworksEmployees = new HashSet<>();

    @OneToMany(mappedBy = "manager")
    @JsonIgnoreProperties(
        value = {
            "location",
            "role",
            "manager",
            "leave",
            "department",
            "businessunit",
            "division",
            "competency",
            "employmentstatus",
            "employmenttype",
            "designation",
            "claimrequestviewsEmployees",
            "claimrequestsEmployees",
            "dealresourcesEmployees",
            "employeeaddressesEmployees",
            "employeebirthdaysEmployees",
            "employeecertificatesEmployees",
            "employeecommentsCommenters",
            "employeecommentsEmployees",
            "employeecompensationEmployees",
            "employeecontactsEmployees",
            "employeedetailsEmployees",
            "employeedocumentsEmployees",
            "employeeeducationEmployees",
            "employeeemergencycontactsEmployees",
            "employeefamilyinfoEmployees",
            "employeejobinfoEmployees",
            "employeejobinfoReviewmanagers",
            "employeejobinfoManagers",
            "employeeprofilehistorylogsEmployees",
            "employeeprojectratingsProjectarchitects",
            "employeeprojectratingsProjectmanagers",
            "employeeprojectratingsEmployees",
            "employeeprojectsEmployees",
            "employeeprojectsAssignors",
            "employeeskillsEmployees",
            "employeetalentsEmployees",
            "employeeworksEmployees",
            "employeesManagers",
            "employmenthistoryEmployees",
            "feedbackquestionsEmployees",
            "feedbackrequestsEmployees",
            "feedbackrequestsLinemanagers",
            "feedbackrespondentsEmployees",
            "interviewsEmployees",
            "leavedetailadjustmentlogsAdjustedbies",
            "leaverequestapproversUsers",
            "leaverequestsoldsManagers",
            "leaverequestsoldsEmployees",
            "leavesUsers",
            "notificationsentemaillogsRecipients",
            "pcratingsEmployees",
            "pcratingstrainingsSuccessorfors",
            "performancecycleemployeeratingEmployees",
            "performancecycleemployeeratingManagers",
            "projectcyclesAllowedafterduedatebies",
            "projectcyclesArchitects",
            "projectcyclesProjectmanagers",
            "projectleadershipEmployees",
            "projectsProjectmanagers",
            "ratingsRaters",
            "userattributesUsers",
            "usergoalsUsers",
            "userratingsUsers",
            "userratingsReviewmanagers",
            "userratingsremarksRaters",
            "userrelationsUsers",
            "userrelationsRelatedusers",
            "worklogsEmployees",
            "zemployeeprojectsEmployees",
            "zemployeeprojectsAssignors",
            "zemployeeprojectsProjectmanagers",
        },
        allowSetters = true
    )
    private Set<Employees> employeesManagers = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
    private Set<EmploymentHistory> employmenthistoryEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee", "feedbackresponsesQuestions" }, allowSetters = true)
    private Set<FeedbackQuestions> feedbackquestionsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee", "linemanager", "feedbackrespondentsRequests" }, allowSetters = true)
    private Set<FeedbackRequests> feedbackrequestsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "linemanager")
    @JsonIgnoreProperties(value = { "employee", "linemanager", "feedbackrespondentsRequests" }, allowSetters = true)
    private Set<FeedbackRequests> feedbackrequestsLinemanagers = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(
        value = { "employee", "request", "feedbackemailsRespondents", "feedbackresponsesRespondents" },
        allowSetters = true
    )
    private Set<FeedbackRespondents> feedbackrespondentsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee", "project", "track", "questionsInterviews" }, allowSetters = true)
    private Set<Interviews> interviewsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "adjustedBy")
    @JsonIgnoreProperties(value = { "leaveDetail", "adjustedBy" }, allowSetters = true)
    private Set<LeaveDetailAdjustmentLogs> leavedetailadjustmentlogsAdjustedbies = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = { "leaveRequest", "user", "status" }, allowSetters = true)
    private Set<LeaveRequestApprovers> leaverequestapproversUsers = new HashSet<>();

    @OneToMany(mappedBy = "manager")
    @JsonIgnoreProperties(value = { "leavetype", "manager", "employee" }, allowSetters = true)
    private Set<LeaveRequestsOlds> leaverequestsoldsManagers = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "leavetype", "manager", "employee" }, allowSetters = true)
    private Set<LeaveRequestsOlds> leaverequestsoldsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = { "user", "leavedetailsLeaves" }, allowSetters = true)
    private Set<Leaves> leavesUsers = new HashSet<>();

    @OneToMany(mappedBy = "recipient")
    @JsonIgnoreProperties(value = { "notificationTemplate", "recipient" }, allowSetters = true)
    private Set<NotificationSentEmailLogs> notificationsentemaillogsRecipients = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(
        value = {
            "status",
            "pcemployeerating",
            "employee",
            "pcraterattributesRatings",
            "pcratingstrainingsRatings",
            "usergoalraterattributesRatings",
        },
        allowSetters = true
    )
    private Set<PcRatings> pcratingsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "successorFor")
    @JsonIgnoreProperties(value = { "successorFor", "rating" }, allowSetters = true)
    private Set<PcRatingsTrainings> pcratingstrainingsSuccessorfors = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(
        value = { "performancecycle", "employee", "manager", "pcratingsPcemployeeratings", "userratingsremarksPcemployeeratings" },
        allowSetters = true
    )
    private Set<PerformanceCycleEmployeeRating> performancecycleemployeeratingEmployees = new HashSet<>();

    @OneToMany(mappedBy = "manager")
    @JsonIgnoreProperties(
        value = { "performancecycle", "employee", "manager", "pcratingsPcemployeeratings", "userratingsremarksPcemployeeratings" },
        allowSetters = true
    )
    private Set<PerformanceCycleEmployeeRating> performancecycleemployeeratingManagers = new HashSet<>();

    @OneToMany(mappedBy = "allowedafterduedateby")
    @JsonIgnoreProperties(
        value = {
            "project",
            "allowedafterduedateby",
            "architect",
            "projectmanager",
            "ratings",
            "employeeprojectratingsProjectcycles",
            "performancecycles",
        },
        allowSetters = true
    )
    private Set<ProjectCycles> projectcyclesAllowedafterduedatebies = new HashSet<>();

    @OneToMany(mappedBy = "architect")
    @JsonIgnoreProperties(
        value = {
            "project",
            "allowedafterduedateby",
            "architect",
            "projectmanager",
            "ratings",
            "employeeprojectratingsProjectcycles",
            "performancecycles",
        },
        allowSetters = true
    )
    private Set<ProjectCycles> projectcyclesArchitects = new HashSet<>();

    @OneToMany(mappedBy = "projectmanager")
    @JsonIgnoreProperties(
        value = {
            "project",
            "allowedafterduedateby",
            "architect",
            "projectmanager",
            "ratings",
            "employeeprojectratingsProjectcycles",
            "performancecycles",
        },
        allowSetters = true
    )
    private Set<ProjectCycles> projectcyclesProjectmanagers = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "projectrole", "project", "employee" }, allowSetters = true)
    private Set<ProjectLeadership> projectleadershipEmployees = new HashSet<>();

    @OneToMany(mappedBy = "projectmanager")
    @JsonIgnoreProperties(
        value = {
            "projectmanager",
            "businessunit",
            "employeeprojectsProjects",
            "interviewsProjects",
            "projectcyclesProjects",
            "projectleadershipProjects",
            "questionsProjects",
            "questionsfrequencyperclienttrackProjects",
            "worklogdetailsProjects",
            "zemployeeprojectsProjects",
        },
        allowSetters = true
    )
    private Set<Projects> projectsProjectmanagers = new HashSet<>();

    @OneToMany(mappedBy = "rater")
    @JsonIgnoreProperties(value = { "rater", "ratingattributesRatings", "performancecycles", "projectcycles" }, allowSetters = true)
    private Set<Ratings> ratingsRaters = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = { "attribute", "user" }, allowSetters = true)
    private Set<UserAttributes> userattributesUsers = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = { "user", "referenceGoal", "usergoalraterattributesUsergoals" }, allowSetters = true)
    private Set<UserGoals> usergoalsUsers = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = { "user", "reviewManager", "performanceCycle" }, allowSetters = true)
    private Set<UserRatings> userratingsUsers = new HashSet<>();

    @OneToMany(mappedBy = "reviewManager")
    @JsonIgnoreProperties(value = { "user", "reviewManager", "performanceCycle" }, allowSetters = true)
    private Set<UserRatings> userratingsReviewmanagers = new HashSet<>();

    @OneToMany(mappedBy = "rater")
    @JsonIgnoreProperties(
        value = { "designationAfterPromotion", "designationAfterRedesignation", "rater", "pcEmployeeRating" },
        allowSetters = true
    )
    private Set<UserRatingsRemarks> userratingsremarksRaters = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = { "user", "attribute", "relatedUser" }, allowSetters = true)
    private Set<UserRelations> userrelationsUsers = new HashSet<>();

    @OneToMany(mappedBy = "relatedUser")
    @JsonIgnoreProperties(value = { "user", "attribute", "relatedUser" }, allowSetters = true)
    private Set<UserRelations> userrelationsRelatedusers = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee", "worklogdetailsWorklogs" }, allowSetters = true)
    private Set<WorkLogs> worklogsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "event", "employee", "project", "employeeproject", "assignor", "projectmanager" }, allowSetters = true)
    private Set<ZEmployeeProjects> zemployeeprojectsEmployees = new HashSet<>();

    @OneToMany(mappedBy = "assignor")
    @JsonIgnoreProperties(value = { "event", "employee", "project", "employeeproject", "assignor", "projectmanager" }, allowSetters = true)
    private Set<ZEmployeeProjects> zemployeeprojectsAssignors = new HashSet<>();

    @OneToMany(mappedBy = "projectmanager")
    @JsonIgnoreProperties(value = { "event", "employee", "project", "employeeproject", "assignor", "projectmanager" }, allowSetters = true)
    private Set<ZEmployeeProjects> zemployeeprojectsProjectmanagers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employees id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public Employees firstname(String firstname) {
        this.setFirstname(firstname);
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public Employees lastname(String lastname) {
        this.setLastname(lastname);
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public Employees phonenumber(String phonenumber) {
        this.setPhonenumber(phonenumber);
        return this;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Instant getDateofbirth() {
        return this.dateofbirth;
    }

    public Employees dateofbirth(Instant dateofbirth) {
        this.setDateofbirth(dateofbirth);
        return this;
    }

    public void setDateofbirth(Instant dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getEmail() {
        return this.email;
    }

    public Employees email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkype() {
        return this.skype;
    }

    public Employees skype(String skype) {
        this.setSkype(skype);
        return this;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getEmployeeDesignation() {
        return this.employeeDesignation;
    }

    public Employees employeeDesignation(String employeeDesignation) {
        this.setEmployeeDesignation(employeeDesignation);
        return this;
    }

    public void setEmployeeDesignation(String employeeDesignation) {
        this.employeeDesignation = employeeDesignation;
    }

    public Instant getJoiningdate() {
        return this.joiningdate;
    }

    public Employees joiningdate(Instant joiningdate) {
        this.setJoiningdate(joiningdate);
        return this;
    }

    public void setJoiningdate(Instant joiningdate) {
        this.joiningdate = joiningdate;
    }

    public String getArea() {
        return this.area;
    }

    public Employees area(String area) {
        this.setArea(area);
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Instant getLeavingdate() {
        return this.leavingdate;
    }

    public Employees leavingdate(Instant leavingdate) {
        this.setLeavingdate(leavingdate);
        return this;
    }

    public void setLeavingdate(Instant leavingdate) {
        this.leavingdate = leavingdate;
    }

    public String getNotes() {
        return this.notes;
    }

    public Employees notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getIsactive() {
        return this.isactive;
    }

    public Employees isactive(Boolean isactive) {
        this.setIsactive(isactive);
        return this;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public String getGoogleid() {
        return this.googleid;
    }

    public Employees googleid(String googleid) {
        this.setGoogleid(googleid);
        return this;
    }

    public void setGoogleid(String googleid) {
        this.googleid = googleid;
    }

    public String getOracleid() {
        return this.oracleid;
    }

    public Employees oracleid(String oracleid) {
        this.setOracleid(oracleid);
        return this;
    }

    public void setOracleid(String oracleid) {
        this.oracleid = oracleid;
    }

    public String getDeptt() {
        return this.deptt;
    }

    public Employees deptt(String deptt) {
        this.setDeptt(deptt);
        return this;
    }

    public void setDeptt(String deptt) {
        this.deptt = deptt;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Employees createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Employees updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public String getGenderid() {
        return this.genderid;
    }

    public Employees genderid(String genderid) {
        this.setGenderid(genderid);
        return this;
    }

    public void setGenderid(String genderid) {
        this.genderid = genderid;
    }

    public Boolean getOnprobation() {
        return this.onprobation;
    }

    public Employees onprobation(Boolean onprobation) {
        this.setOnprobation(onprobation);
        return this;
    }

    public void setOnprobation(Boolean onprobation) {
        this.onprobation = onprobation;
    }

    public String getEmployeeCompetency() {
        return this.employeeCompetency;
    }

    public Employees employeeCompetency(String employeeCompetency) {
        this.setEmployeeCompetency(employeeCompetency);
        return this;
    }

    public void setEmployeeCompetency(String employeeCompetency) {
        this.employeeCompetency = employeeCompetency;
    }

    public String getResourcetype() {
        return this.resourcetype;
    }

    public Employees resourcetype(String resourcetype) {
        this.setResourcetype(resourcetype);
        return this;
    }

    public void setResourcetype(String resourcetype) {
        this.resourcetype = resourcetype;
    }

    public String getGrade() {
        return this.grade;
    }

    public Employees grade(String grade) {
        this.setGrade(grade);
        return this;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSubgrade() {
        return this.subgrade;
    }

    public Employees subgrade(String subgrade) {
        this.setSubgrade(subgrade);
        return this;
    }

    public void setSubgrade(String subgrade) {
        this.subgrade = subgrade;
    }

    public String getImageurl() {
        return this.imageurl;
    }

    public Employees imageurl(String imageurl) {
        this.setImageurl(imageurl);
        return this;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Instant getResignationdate() {
        return this.resignationdate;
    }

    public Employees resignationdate(Instant resignationdate) {
        this.setResignationdate(resignationdate);
        return this;
    }

    public void setResignationdate(Instant resignationdate) {
        this.resignationdate = resignationdate;
    }

    public LocalDate getGraduationdate() {
        return this.graduationdate;
    }

    public Employees graduationdate(LocalDate graduationdate) {
        this.setGraduationdate(graduationdate);
        return this;
    }

    public void setGraduationdate(LocalDate graduationdate) {
        this.graduationdate = graduationdate;
    }

    public LocalDate getCareerstartdate() {
        return this.careerstartdate;
    }

    public Employees careerstartdate(LocalDate careerstartdate) {
        this.setCareerstartdate(careerstartdate);
        return this;
    }

    public void setCareerstartdate(LocalDate careerstartdate) {
        this.careerstartdate = careerstartdate;
    }

    public Integer getExternalexpyears() {
        return this.externalexpyears;
    }

    public Employees externalexpyears(Integer externalexpyears) {
        this.setExternalexpyears(externalexpyears);
        return this;
    }

    public void setExternalexpyears(Integer externalexpyears) {
        this.externalexpyears = externalexpyears;
    }

    public BigDecimal getExternalexpmonths() {
        return this.externalexpmonths;
    }

    public Employees externalexpmonths(BigDecimal externalexpmonths) {
        this.setExternalexpmonths(externalexpmonths);
        return this;
    }

    public void setExternalexpmonths(BigDecimal externalexpmonths) {
        this.externalexpmonths = externalexpmonths;
    }

    public String getPlaceofbirth() {
        return this.placeofbirth;
    }

    public Employees placeofbirth(String placeofbirth) {
        this.setPlaceofbirth(placeofbirth);
        return this;
    }

    public void setPlaceofbirth(String placeofbirth) {
        this.placeofbirth = placeofbirth;
    }

    public Instant getHireddate() {
        return this.hireddate;
    }

    public Employees hireddate(Instant hireddate) {
        this.setHireddate(hireddate);
        return this;
    }

    public void setHireddate(Instant hireddate) {
        this.hireddate = hireddate;
    }

    public Instant getLasttrackingupdate() {
        return this.lasttrackingupdate;
    }

    public Employees lasttrackingupdate(Instant lasttrackingupdate) {
        this.setLasttrackingupdate(lasttrackingupdate);
        return this;
    }

    public void setLasttrackingupdate(Instant lasttrackingupdate) {
        this.lasttrackingupdate = lasttrackingupdate;
    }

    public String getMiddlename() {
        return this.middlename;
    }

    public Employees middlename(String middlename) {
        this.setMiddlename(middlename);
        return this;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public byte[] getGrosssalary() {
        return this.grosssalary;
    }

    public Employees grosssalary(byte[] grosssalary) {
        this.setGrosssalary(grosssalary);
        return this;
    }

    public void setGrosssalary(byte[] grosssalary) {
        this.grosssalary = grosssalary;
    }

    public String getGrosssalaryContentType() {
        return this.grosssalaryContentType;
    }

    public Employees grosssalaryContentType(String grosssalaryContentType) {
        this.grosssalaryContentType = grosssalaryContentType;
        return this;
    }

    public void setGrosssalaryContentType(String grosssalaryContentType) {
        this.grosssalaryContentType = grosssalaryContentType;
    }

    public byte[] getFuelallowance() {
        return this.fuelallowance;
    }

    public Employees fuelallowance(byte[] fuelallowance) {
        this.setFuelallowance(fuelallowance);
        return this;
    }

    public void setFuelallowance(byte[] fuelallowance) {
        this.fuelallowance = fuelallowance;
    }

    public String getFuelallowanceContentType() {
        return this.fuelallowanceContentType;
    }

    public Employees fuelallowanceContentType(String fuelallowanceContentType) {
        this.fuelallowanceContentType = fuelallowanceContentType;
        return this;
    }

    public void setFuelallowanceContentType(String fuelallowanceContentType) {
        this.fuelallowanceContentType = fuelallowanceContentType;
    }

    public String getMobilenumber() {
        return this.mobilenumber;
    }

    public Employees mobilenumber(String mobilenumber) {
        this.setMobilenumber(mobilenumber);
        return this;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getResignationtype() {
        return this.resignationtype;
    }

    public Employees resignationtype(String resignationtype) {
        this.setResignationtype(resignationtype);
        return this;
    }

    public void setResignationtype(String resignationtype) {
        this.resignationtype = resignationtype;
    }

    public String getPrimaryreasonforleaving() {
        return this.primaryreasonforleaving;
    }

    public Employees primaryreasonforleaving(String primaryreasonforleaving) {
        this.setPrimaryreasonforleaving(primaryreasonforleaving);
        return this;
    }

    public void setPrimaryreasonforleaving(String primaryreasonforleaving) {
        this.primaryreasonforleaving = primaryreasonforleaving;
    }

    public String getSecondaryreasonforleaving() {
        return this.secondaryreasonforleaving;
    }

    public Employees secondaryreasonforleaving(String secondaryreasonforleaving) {
        this.setSecondaryreasonforleaving(secondaryreasonforleaving);
        return this;
    }

    public void setSecondaryreasonforleaving(String secondaryreasonforleaving) {
        this.secondaryreasonforleaving = secondaryreasonforleaving;
    }

    public Integer getNoticeperiodduration() {
        return this.noticeperiodduration;
    }

    public Employees noticeperiodduration(Integer noticeperiodduration) {
        this.setNoticeperiodduration(noticeperiodduration);
        return this;
    }

    public void setNoticeperiodduration(Integer noticeperiodduration) {
        this.noticeperiodduration = noticeperiodduration;
    }

    public Integer getNoticeperiodserved() {
        return this.noticeperiodserved;
    }

    public Employees noticeperiodserved(Integer noticeperiodserved) {
        this.setNoticeperiodserved(noticeperiodserved);
        return this;
    }

    public void setNoticeperiodserved(Integer noticeperiodserved) {
        this.noticeperiodserved = noticeperiodserved;
    }

    public Integer getProbationperiodduration() {
        return this.probationperiodduration;
    }

    public Employees probationperiodduration(Integer probationperiodduration) {
        this.setProbationperiodduration(probationperiodduration);
        return this;
    }

    public void setProbationperiodduration(Integer probationperiodduration) {
        this.probationperiodduration = probationperiodduration;
    }

    public Locations getLocation() {
        return this.location;
    }

    public void setLocation(Locations locations) {
        this.location = locations;
    }

    public Employees location(Locations locations) {
        this.setLocation(locations);
        return this;
    }

    public Roles getRole() {
        return this.role;
    }

    public void setRole(Roles roles) {
        this.role = roles;
    }

    public Employees role(Roles roles) {
        this.setRole(roles);
        return this;
    }

    public Employees getManager() {
        return this.manager;
    }

    public void setManager(Employees employees) {
        this.manager = employees;
    }

    public Employees manager(Employees employees) {
        this.setManager(employees);
        return this;
    }

    public LeavesOlds getLeave() {
        return this.leave;
    }

    public void setLeave(LeavesOlds leavesOlds) {
        this.leave = leavesOlds;
    }

    public Employees leave(LeavesOlds leavesOlds) {
        this.setLeave(leavesOlds);
        return this;
    }

    public Departments getDepartment() {
        return this.department;
    }

    public void setDepartment(Departments departments) {
        this.department = departments;
    }

    public Employees department(Departments departments) {
        this.setDepartment(departments);
        return this;
    }

    public BusinessUnits getBusinessunit() {
        return this.businessunit;
    }

    public void setBusinessunit(BusinessUnits businessUnits) {
        this.businessunit = businessUnits;
    }

    public Employees businessunit(BusinessUnits businessUnits) {
        this.setBusinessunit(businessUnits);
        return this;
    }

    public Divisions getDivision() {
        return this.division;
    }

    public void setDivision(Divisions divisions) {
        this.division = divisions;
    }

    public Employees division(Divisions divisions) {
        this.setDivision(divisions);
        return this;
    }

    public Competencies getCompetency() {
        return this.competency;
    }

    public void setCompetency(Competencies competencies) {
        this.competency = competencies;
    }

    public Employees competency(Competencies competencies) {
        this.setCompetency(competencies);
        return this;
    }

    public EmploymentStatuses getEmploymentstatus() {
        return this.employmentstatus;
    }

    public void setEmploymentstatus(EmploymentStatuses employmentStatuses) {
        this.employmentstatus = employmentStatuses;
    }

    public Employees employmentstatus(EmploymentStatuses employmentStatuses) {
        this.setEmploymentstatus(employmentStatuses);
        return this;
    }

    public EmploymentTypes getEmploymenttype() {
        return this.employmenttype;
    }

    public void setEmploymenttype(EmploymentTypes employmentTypes) {
        this.employmenttype = employmentTypes;
    }

    public Employees employmenttype(EmploymentTypes employmentTypes) {
        this.setEmploymenttype(employmentTypes);
        return this;
    }

    public Designations getDesignation() {
        return this.designation;
    }

    public void setDesignation(Designations designations) {
        this.designation = designations;
    }

    public Employees designation(Designations designations) {
        this.setDesignation(designations);
        return this;
    }

    public Set<ClaimRequestViews> getClaimrequestviewsEmployees() {
        return this.claimrequestviewsEmployees;
    }

    public void setClaimrequestviewsEmployees(Set<ClaimRequestViews> claimRequestViews) {
        if (this.claimrequestviewsEmployees != null) {
            this.claimrequestviewsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (claimRequestViews != null) {
            claimRequestViews.forEach(i -> i.setEmployee(this));
        }
        this.claimrequestviewsEmployees = claimRequestViews;
    }

    public Employees claimrequestviewsEmployees(Set<ClaimRequestViews> claimRequestViews) {
        this.setClaimrequestviewsEmployees(claimRequestViews);
        return this;
    }

    public Employees addClaimrequestviewsEmployee(ClaimRequestViews claimRequestViews) {
        this.claimrequestviewsEmployees.add(claimRequestViews);
        claimRequestViews.setEmployee(this);
        return this;
    }

    public Employees removeClaimrequestviewsEmployee(ClaimRequestViews claimRequestViews) {
        this.claimrequestviewsEmployees.remove(claimRequestViews);
        claimRequestViews.setEmployee(null);
        return this;
    }

    public Set<ClaimRequests> getClaimrequestsEmployees() {
        return this.claimrequestsEmployees;
    }

    public void setClaimrequestsEmployees(Set<ClaimRequests> claimRequests) {
        if (this.claimrequestsEmployees != null) {
            this.claimrequestsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (claimRequests != null) {
            claimRequests.forEach(i -> i.setEmployee(this));
        }
        this.claimrequestsEmployees = claimRequests;
    }

    public Employees claimrequestsEmployees(Set<ClaimRequests> claimRequests) {
        this.setClaimrequestsEmployees(claimRequests);
        return this;
    }

    public Employees addClaimrequestsEmployee(ClaimRequests claimRequests) {
        this.claimrequestsEmployees.add(claimRequests);
        claimRequests.setEmployee(this);
        return this;
    }

    public Employees removeClaimrequestsEmployee(ClaimRequests claimRequests) {
        this.claimrequestsEmployees.remove(claimRequests);
        claimRequests.setEmployee(null);
        return this;
    }

    public Set<DealResources> getDealresourcesEmployees() {
        return this.dealresourcesEmployees;
    }

    public void setDealresourcesEmployees(Set<DealResources> dealResources) {
        if (this.dealresourcesEmployees != null) {
            this.dealresourcesEmployees.forEach(i -> i.setEmployee(null));
        }
        if (dealResources != null) {
            dealResources.forEach(i -> i.setEmployee(this));
        }
        this.dealresourcesEmployees = dealResources;
    }

    public Employees dealresourcesEmployees(Set<DealResources> dealResources) {
        this.setDealresourcesEmployees(dealResources);
        return this;
    }

    public Employees addDealresourcesEmployee(DealResources dealResources) {
        this.dealresourcesEmployees.add(dealResources);
        dealResources.setEmployee(this);
        return this;
    }

    public Employees removeDealresourcesEmployee(DealResources dealResources) {
        this.dealresourcesEmployees.remove(dealResources);
        dealResources.setEmployee(null);
        return this;
    }

    public Set<EmployeeAddresses> getEmployeeaddressesEmployees() {
        return this.employeeaddressesEmployees;
    }

    public void setEmployeeaddressesEmployees(Set<EmployeeAddresses> employeeAddresses) {
        if (this.employeeaddressesEmployees != null) {
            this.employeeaddressesEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employeeAddresses != null) {
            employeeAddresses.forEach(i -> i.setEmployee(this));
        }
        this.employeeaddressesEmployees = employeeAddresses;
    }

    public Employees employeeaddressesEmployees(Set<EmployeeAddresses> employeeAddresses) {
        this.setEmployeeaddressesEmployees(employeeAddresses);
        return this;
    }

    public Employees addEmployeeaddressesEmployee(EmployeeAddresses employeeAddresses) {
        this.employeeaddressesEmployees.add(employeeAddresses);
        employeeAddresses.setEmployee(this);
        return this;
    }

    public Employees removeEmployeeaddressesEmployee(EmployeeAddresses employeeAddresses) {
        this.employeeaddressesEmployees.remove(employeeAddresses);
        employeeAddresses.setEmployee(null);
        return this;
    }

    public Set<EmployeeBirthdays> getEmployeebirthdaysEmployees() {
        return this.employeebirthdaysEmployees;
    }

    public void setEmployeebirthdaysEmployees(Set<EmployeeBirthdays> employeeBirthdays) {
        if (this.employeebirthdaysEmployees != null) {
            this.employeebirthdaysEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employeeBirthdays != null) {
            employeeBirthdays.forEach(i -> i.setEmployee(this));
        }
        this.employeebirthdaysEmployees = employeeBirthdays;
    }

    public Employees employeebirthdaysEmployees(Set<EmployeeBirthdays> employeeBirthdays) {
        this.setEmployeebirthdaysEmployees(employeeBirthdays);
        return this;
    }

    public Employees addEmployeebirthdaysEmployee(EmployeeBirthdays employeeBirthdays) {
        this.employeebirthdaysEmployees.add(employeeBirthdays);
        employeeBirthdays.setEmployee(this);
        return this;
    }

    public Employees removeEmployeebirthdaysEmployee(EmployeeBirthdays employeeBirthdays) {
        this.employeebirthdaysEmployees.remove(employeeBirthdays);
        employeeBirthdays.setEmployee(null);
        return this;
    }

    public Set<EmployeeCertificates> getEmployeecertificatesEmployees() {
        return this.employeecertificatesEmployees;
    }

    public void setEmployeecertificatesEmployees(Set<EmployeeCertificates> employeeCertificates) {
        if (this.employeecertificatesEmployees != null) {
            this.employeecertificatesEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employeeCertificates != null) {
            employeeCertificates.forEach(i -> i.setEmployee(this));
        }
        this.employeecertificatesEmployees = employeeCertificates;
    }

    public Employees employeecertificatesEmployees(Set<EmployeeCertificates> employeeCertificates) {
        this.setEmployeecertificatesEmployees(employeeCertificates);
        return this;
    }

    public Employees addEmployeecertificatesEmployee(EmployeeCertificates employeeCertificates) {
        this.employeecertificatesEmployees.add(employeeCertificates);
        employeeCertificates.setEmployee(this);
        return this;
    }

    public Employees removeEmployeecertificatesEmployee(EmployeeCertificates employeeCertificates) {
        this.employeecertificatesEmployees.remove(employeeCertificates);
        employeeCertificates.setEmployee(null);
        return this;
    }

    public Set<EmployeeComments> getEmployeecommentsCommenters() {
        return this.employeecommentsCommenters;
    }

    public void setEmployeecommentsCommenters(Set<EmployeeComments> employeeComments) {
        if (this.employeecommentsCommenters != null) {
            this.employeecommentsCommenters.forEach(i -> i.setCommenter(null));
        }
        if (employeeComments != null) {
            employeeComments.forEach(i -> i.setCommenter(this));
        }
        this.employeecommentsCommenters = employeeComments;
    }

    public Employees employeecommentsCommenters(Set<EmployeeComments> employeeComments) {
        this.setEmployeecommentsCommenters(employeeComments);
        return this;
    }

    public Employees addEmployeecommentsCommenter(EmployeeComments employeeComments) {
        this.employeecommentsCommenters.add(employeeComments);
        employeeComments.setCommenter(this);
        return this;
    }

    public Employees removeEmployeecommentsCommenter(EmployeeComments employeeComments) {
        this.employeecommentsCommenters.remove(employeeComments);
        employeeComments.setCommenter(null);
        return this;
    }

    public Set<EmployeeComments> getEmployeecommentsEmployees() {
        return this.employeecommentsEmployees;
    }

    public void setEmployeecommentsEmployees(Set<EmployeeComments> employeeComments) {
        if (this.employeecommentsEmployees != null) {
            this.employeecommentsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employeeComments != null) {
            employeeComments.forEach(i -> i.setEmployee(this));
        }
        this.employeecommentsEmployees = employeeComments;
    }

    public Employees employeecommentsEmployees(Set<EmployeeComments> employeeComments) {
        this.setEmployeecommentsEmployees(employeeComments);
        return this;
    }

    public Employees addEmployeecommentsEmployee(EmployeeComments employeeComments) {
        this.employeecommentsEmployees.add(employeeComments);
        employeeComments.setEmployee(this);
        return this;
    }

    public Employees removeEmployeecommentsEmployee(EmployeeComments employeeComments) {
        this.employeecommentsEmployees.remove(employeeComments);
        employeeComments.setEmployee(null);
        return this;
    }

    public Set<EmployeeCompensation> getEmployeecompensationEmployees() {
        return this.employeecompensationEmployees;
    }

    public void setEmployeecompensationEmployees(Set<EmployeeCompensation> employeeCompensations) {
        if (this.employeecompensationEmployees != null) {
            this.employeecompensationEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employeeCompensations != null) {
            employeeCompensations.forEach(i -> i.setEmployee(this));
        }
        this.employeecompensationEmployees = employeeCompensations;
    }

    public Employees employeecompensationEmployees(Set<EmployeeCompensation> employeeCompensations) {
        this.setEmployeecompensationEmployees(employeeCompensations);
        return this;
    }

    public Employees addEmployeecompensationEmployee(EmployeeCompensation employeeCompensation) {
        this.employeecompensationEmployees.add(employeeCompensation);
        employeeCompensation.setEmployee(this);
        return this;
    }

    public Employees removeEmployeecompensationEmployee(EmployeeCompensation employeeCompensation) {
        this.employeecompensationEmployees.remove(employeeCompensation);
        employeeCompensation.setEmployee(null);
        return this;
    }

    public Set<EmployeeContacts> getEmployeecontactsEmployees() {
        return this.employeecontactsEmployees;
    }

    public void setEmployeecontactsEmployees(Set<EmployeeContacts> employeeContacts) {
        if (this.employeecontactsEmployees != null) {
            this.employeecontactsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employeeContacts != null) {
            employeeContacts.forEach(i -> i.setEmployee(this));
        }
        this.employeecontactsEmployees = employeeContacts;
    }

    public Employees employeecontactsEmployees(Set<EmployeeContacts> employeeContacts) {
        this.setEmployeecontactsEmployees(employeeContacts);
        return this;
    }

    public Employees addEmployeecontactsEmployee(EmployeeContacts employeeContacts) {
        this.employeecontactsEmployees.add(employeeContacts);
        employeeContacts.setEmployee(this);
        return this;
    }

    public Employees removeEmployeecontactsEmployee(EmployeeContacts employeeContacts) {
        this.employeecontactsEmployees.remove(employeeContacts);
        employeeContacts.setEmployee(null);
        return this;
    }

    public Set<EmployeeDetails> getEmployeedetailsEmployees() {
        return this.employeedetailsEmployees;
    }

    public void setEmployeedetailsEmployees(Set<EmployeeDetails> employeeDetails) {
        if (this.employeedetailsEmployees != null) {
            this.employeedetailsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employeeDetails != null) {
            employeeDetails.forEach(i -> i.setEmployee(this));
        }
        this.employeedetailsEmployees = employeeDetails;
    }

    public Employees employeedetailsEmployees(Set<EmployeeDetails> employeeDetails) {
        this.setEmployeedetailsEmployees(employeeDetails);
        return this;
    }

    public Employees addEmployeedetailsEmployee(EmployeeDetails employeeDetails) {
        this.employeedetailsEmployees.add(employeeDetails);
        employeeDetails.setEmployee(this);
        return this;
    }

    public Employees removeEmployeedetailsEmployee(EmployeeDetails employeeDetails) {
        this.employeedetailsEmployees.remove(employeeDetails);
        employeeDetails.setEmployee(null);
        return this;
    }

    public Set<EmployeeDocuments> getEmployeedocumentsEmployees() {
        return this.employeedocumentsEmployees;
    }

    public void setEmployeedocumentsEmployees(Set<EmployeeDocuments> employeeDocuments) {
        if (this.employeedocumentsEmployees != null) {
            this.employeedocumentsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employeeDocuments != null) {
            employeeDocuments.forEach(i -> i.setEmployee(this));
        }
        this.employeedocumentsEmployees = employeeDocuments;
    }

    public Employees employeedocumentsEmployees(Set<EmployeeDocuments> employeeDocuments) {
        this.setEmployeedocumentsEmployees(employeeDocuments);
        return this;
    }

    public Employees addEmployeedocumentsEmployee(EmployeeDocuments employeeDocuments) {
        this.employeedocumentsEmployees.add(employeeDocuments);
        employeeDocuments.setEmployee(this);
        return this;
    }

    public Employees removeEmployeedocumentsEmployee(EmployeeDocuments employeeDocuments) {
        this.employeedocumentsEmployees.remove(employeeDocuments);
        employeeDocuments.setEmployee(null);
        return this;
    }

    public Set<EmployeeEducation> getEmployeeeducationEmployees() {
        return this.employeeeducationEmployees;
    }

    public void setEmployeeeducationEmployees(Set<EmployeeEducation> employeeEducations) {
        if (this.employeeeducationEmployees != null) {
            this.employeeeducationEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employeeEducations != null) {
            employeeEducations.forEach(i -> i.setEmployee(this));
        }
        this.employeeeducationEmployees = employeeEducations;
    }

    public Employees employeeeducationEmployees(Set<EmployeeEducation> employeeEducations) {
        this.setEmployeeeducationEmployees(employeeEducations);
        return this;
    }

    public Employees addEmployeeeducationEmployee(EmployeeEducation employeeEducation) {
        this.employeeeducationEmployees.add(employeeEducation);
        employeeEducation.setEmployee(this);
        return this;
    }

    public Employees removeEmployeeeducationEmployee(EmployeeEducation employeeEducation) {
        this.employeeeducationEmployees.remove(employeeEducation);
        employeeEducation.setEmployee(null);
        return this;
    }

    public Set<EmployeeEmergencyContacts> getEmployeeemergencycontactsEmployees() {
        return this.employeeemergencycontactsEmployees;
    }

    public void setEmployeeemergencycontactsEmployees(Set<EmployeeEmergencyContacts> employeeEmergencyContacts) {
        if (this.employeeemergencycontactsEmployees != null) {
            this.employeeemergencycontactsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employeeEmergencyContacts != null) {
            employeeEmergencyContacts.forEach(i -> i.setEmployee(this));
        }
        this.employeeemergencycontactsEmployees = employeeEmergencyContacts;
    }

    public Employees employeeemergencycontactsEmployees(Set<EmployeeEmergencyContacts> employeeEmergencyContacts) {
        this.setEmployeeemergencycontactsEmployees(employeeEmergencyContacts);
        return this;
    }

    public Employees addEmployeeemergencycontactsEmployee(EmployeeEmergencyContacts employeeEmergencyContacts) {
        this.employeeemergencycontactsEmployees.add(employeeEmergencyContacts);
        employeeEmergencyContacts.setEmployee(this);
        return this;
    }

    public Employees removeEmployeeemergencycontactsEmployee(EmployeeEmergencyContacts employeeEmergencyContacts) {
        this.employeeemergencycontactsEmployees.remove(employeeEmergencyContacts);
        employeeEmergencyContacts.setEmployee(null);
        return this;
    }

    public Set<EmployeeFamilyInfo> getEmployeefamilyinfoEmployees() {
        return this.employeefamilyinfoEmployees;
    }

    public void setEmployeefamilyinfoEmployees(Set<EmployeeFamilyInfo> employeeFamilyInfos) {
        if (this.employeefamilyinfoEmployees != null) {
            this.employeefamilyinfoEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employeeFamilyInfos != null) {
            employeeFamilyInfos.forEach(i -> i.setEmployee(this));
        }
        this.employeefamilyinfoEmployees = employeeFamilyInfos;
    }

    public Employees employeefamilyinfoEmployees(Set<EmployeeFamilyInfo> employeeFamilyInfos) {
        this.setEmployeefamilyinfoEmployees(employeeFamilyInfos);
        return this;
    }

    public Employees addEmployeefamilyinfoEmployee(EmployeeFamilyInfo employeeFamilyInfo) {
        this.employeefamilyinfoEmployees.add(employeeFamilyInfo);
        employeeFamilyInfo.setEmployee(this);
        return this;
    }

    public Employees removeEmployeefamilyinfoEmployee(EmployeeFamilyInfo employeeFamilyInfo) {
        this.employeefamilyinfoEmployees.remove(employeeFamilyInfo);
        employeeFamilyInfo.setEmployee(null);
        return this;
    }

    public Set<EmployeeJobInfo> getEmployeejobinfoEmployees() {
        return this.employeejobinfoEmployees;
    }

    public void setEmployeejobinfoEmployees(Set<EmployeeJobInfo> employeeJobInfos) {
        if (this.employeejobinfoEmployees != null) {
            this.employeejobinfoEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employeeJobInfos != null) {
            employeeJobInfos.forEach(i -> i.setEmployee(this));
        }
        this.employeejobinfoEmployees = employeeJobInfos;
    }

    public Employees employeejobinfoEmployees(Set<EmployeeJobInfo> employeeJobInfos) {
        this.setEmployeejobinfoEmployees(employeeJobInfos);
        return this;
    }

    public Employees addEmployeejobinfoEmployee(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoEmployees.add(employeeJobInfo);
        employeeJobInfo.setEmployee(this);
        return this;
    }

    public Employees removeEmployeejobinfoEmployee(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoEmployees.remove(employeeJobInfo);
        employeeJobInfo.setEmployee(null);
        return this;
    }

    public Set<EmployeeJobInfo> getEmployeejobinfoReviewmanagers() {
        return this.employeejobinfoReviewmanagers;
    }

    public void setEmployeejobinfoReviewmanagers(Set<EmployeeJobInfo> employeeJobInfos) {
        if (this.employeejobinfoReviewmanagers != null) {
            this.employeejobinfoReviewmanagers.forEach(i -> i.setReviewmanager(null));
        }
        if (employeeJobInfos != null) {
            employeeJobInfos.forEach(i -> i.setReviewmanager(this));
        }
        this.employeejobinfoReviewmanagers = employeeJobInfos;
    }

    public Employees employeejobinfoReviewmanagers(Set<EmployeeJobInfo> employeeJobInfos) {
        this.setEmployeejobinfoReviewmanagers(employeeJobInfos);
        return this;
    }

    public Employees addEmployeejobinfoReviewmanager(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoReviewmanagers.add(employeeJobInfo);
        employeeJobInfo.setReviewmanager(this);
        return this;
    }

    public Employees removeEmployeejobinfoReviewmanager(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoReviewmanagers.remove(employeeJobInfo);
        employeeJobInfo.setReviewmanager(null);
        return this;
    }

    public Set<EmployeeJobInfo> getEmployeejobinfoManagers() {
        return this.employeejobinfoManagers;
    }

    public void setEmployeejobinfoManagers(Set<EmployeeJobInfo> employeeJobInfos) {
        if (this.employeejobinfoManagers != null) {
            this.employeejobinfoManagers.forEach(i -> i.setManager(null));
        }
        if (employeeJobInfos != null) {
            employeeJobInfos.forEach(i -> i.setManager(this));
        }
        this.employeejobinfoManagers = employeeJobInfos;
    }

    public Employees employeejobinfoManagers(Set<EmployeeJobInfo> employeeJobInfos) {
        this.setEmployeejobinfoManagers(employeeJobInfos);
        return this;
    }

    public Employees addEmployeejobinfoManager(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoManagers.add(employeeJobInfo);
        employeeJobInfo.setManager(this);
        return this;
    }

    public Employees removeEmployeejobinfoManager(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoManagers.remove(employeeJobInfo);
        employeeJobInfo.setManager(null);
        return this;
    }

    public Set<EmployeeProfileHistoryLogs> getEmployeeprofilehistorylogsEmployees() {
        return this.employeeprofilehistorylogsEmployees;
    }

    public void setEmployeeprofilehistorylogsEmployees(Set<EmployeeProfileHistoryLogs> employeeProfileHistoryLogs) {
        if (this.employeeprofilehistorylogsEmployees != null) {
            this.employeeprofilehistorylogsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employeeProfileHistoryLogs != null) {
            employeeProfileHistoryLogs.forEach(i -> i.setEmployee(this));
        }
        this.employeeprofilehistorylogsEmployees = employeeProfileHistoryLogs;
    }

    public Employees employeeprofilehistorylogsEmployees(Set<EmployeeProfileHistoryLogs> employeeProfileHistoryLogs) {
        this.setEmployeeprofilehistorylogsEmployees(employeeProfileHistoryLogs);
        return this;
    }

    public Employees addEmployeeprofilehistorylogsEmployee(EmployeeProfileHistoryLogs employeeProfileHistoryLogs) {
        this.employeeprofilehistorylogsEmployees.add(employeeProfileHistoryLogs);
        employeeProfileHistoryLogs.setEmployee(this);
        return this;
    }

    public Employees removeEmployeeprofilehistorylogsEmployee(EmployeeProfileHistoryLogs employeeProfileHistoryLogs) {
        this.employeeprofilehistorylogsEmployees.remove(employeeProfileHistoryLogs);
        employeeProfileHistoryLogs.setEmployee(null);
        return this;
    }

    public Set<EmployeeProjectRatings> getEmployeeprojectratingsProjectarchitects() {
        return this.employeeprojectratingsProjectarchitects;
    }

    public void setEmployeeprojectratingsProjectarchitects(Set<EmployeeProjectRatings> employeeProjectRatings) {
        if (this.employeeprojectratingsProjectarchitects != null) {
            this.employeeprojectratingsProjectarchitects.forEach(i -> i.setProjectarchitect(null));
        }
        if (employeeProjectRatings != null) {
            employeeProjectRatings.forEach(i -> i.setProjectarchitect(this));
        }
        this.employeeprojectratingsProjectarchitects = employeeProjectRatings;
    }

    public Employees employeeprojectratingsProjectarchitects(Set<EmployeeProjectRatings> employeeProjectRatings) {
        this.setEmployeeprojectratingsProjectarchitects(employeeProjectRatings);
        return this;
    }

    public Employees addEmployeeprojectratingsProjectarchitect(EmployeeProjectRatings employeeProjectRatings) {
        this.employeeprojectratingsProjectarchitects.add(employeeProjectRatings);
        employeeProjectRatings.setProjectarchitect(this);
        return this;
    }

    public Employees removeEmployeeprojectratingsProjectarchitect(EmployeeProjectRatings employeeProjectRatings) {
        this.employeeprojectratingsProjectarchitects.remove(employeeProjectRatings);
        employeeProjectRatings.setProjectarchitect(null);
        return this;
    }

    public Set<EmployeeProjectRatings> getEmployeeprojectratingsProjectmanagers() {
        return this.employeeprojectratingsProjectmanagers;
    }

    public void setEmployeeprojectratingsProjectmanagers(Set<EmployeeProjectRatings> employeeProjectRatings) {
        if (this.employeeprojectratingsProjectmanagers != null) {
            this.employeeprojectratingsProjectmanagers.forEach(i -> i.setProjectmanager(null));
        }
        if (employeeProjectRatings != null) {
            employeeProjectRatings.forEach(i -> i.setProjectmanager(this));
        }
        this.employeeprojectratingsProjectmanagers = employeeProjectRatings;
    }

    public Employees employeeprojectratingsProjectmanagers(Set<EmployeeProjectRatings> employeeProjectRatings) {
        this.setEmployeeprojectratingsProjectmanagers(employeeProjectRatings);
        return this;
    }

    public Employees addEmployeeprojectratingsProjectmanager(EmployeeProjectRatings employeeProjectRatings) {
        this.employeeprojectratingsProjectmanagers.add(employeeProjectRatings);
        employeeProjectRatings.setProjectmanager(this);
        return this;
    }

    public Employees removeEmployeeprojectratingsProjectmanager(EmployeeProjectRatings employeeProjectRatings) {
        this.employeeprojectratingsProjectmanagers.remove(employeeProjectRatings);
        employeeProjectRatings.setProjectmanager(null);
        return this;
    }

    public Set<EmployeeProjectRatings> getEmployeeprojectratingsEmployees() {
        return this.employeeprojectratingsEmployees;
    }

    public void setEmployeeprojectratingsEmployees(Set<EmployeeProjectRatings> employeeProjectRatings) {
        if (this.employeeprojectratingsEmployees != null) {
            this.employeeprojectratingsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employeeProjectRatings != null) {
            employeeProjectRatings.forEach(i -> i.setEmployee(this));
        }
        this.employeeprojectratingsEmployees = employeeProjectRatings;
    }

    public Employees employeeprojectratingsEmployees(Set<EmployeeProjectRatings> employeeProjectRatings) {
        this.setEmployeeprojectratingsEmployees(employeeProjectRatings);
        return this;
    }

    public Employees addEmployeeprojectratingsEmployee(EmployeeProjectRatings employeeProjectRatings) {
        this.employeeprojectratingsEmployees.add(employeeProjectRatings);
        employeeProjectRatings.setEmployee(this);
        return this;
    }

    public Employees removeEmployeeprojectratingsEmployee(EmployeeProjectRatings employeeProjectRatings) {
        this.employeeprojectratingsEmployees.remove(employeeProjectRatings);
        employeeProjectRatings.setEmployee(null);
        return this;
    }

    public Set<EmployeeProjects> getEmployeeprojectsEmployees() {
        return this.employeeprojectsEmployees;
    }

    public void setEmployeeprojectsEmployees(Set<EmployeeProjects> employeeProjects) {
        if (this.employeeprojectsEmployees != null) {
            this.employeeprojectsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employeeProjects != null) {
            employeeProjects.forEach(i -> i.setEmployee(this));
        }
        this.employeeprojectsEmployees = employeeProjects;
    }

    public Employees employeeprojectsEmployees(Set<EmployeeProjects> employeeProjects) {
        this.setEmployeeprojectsEmployees(employeeProjects);
        return this;
    }

    public Employees addEmployeeprojectsEmployee(EmployeeProjects employeeProjects) {
        this.employeeprojectsEmployees.add(employeeProjects);
        employeeProjects.setEmployee(this);
        return this;
    }

    public Employees removeEmployeeprojectsEmployee(EmployeeProjects employeeProjects) {
        this.employeeprojectsEmployees.remove(employeeProjects);
        employeeProjects.setEmployee(null);
        return this;
    }

    public Set<EmployeeProjects> getEmployeeprojectsAssignors() {
        return this.employeeprojectsAssignors;
    }

    public void setEmployeeprojectsAssignors(Set<EmployeeProjects> employeeProjects) {
        if (this.employeeprojectsAssignors != null) {
            this.employeeprojectsAssignors.forEach(i -> i.setAssignor(null));
        }
        if (employeeProjects != null) {
            employeeProjects.forEach(i -> i.setAssignor(this));
        }
        this.employeeprojectsAssignors = employeeProjects;
    }

    public Employees employeeprojectsAssignors(Set<EmployeeProjects> employeeProjects) {
        this.setEmployeeprojectsAssignors(employeeProjects);
        return this;
    }

    public Employees addEmployeeprojectsAssignor(EmployeeProjects employeeProjects) {
        this.employeeprojectsAssignors.add(employeeProjects);
        employeeProjects.setAssignor(this);
        return this;
    }

    public Employees removeEmployeeprojectsAssignor(EmployeeProjects employeeProjects) {
        this.employeeprojectsAssignors.remove(employeeProjects);
        employeeProjects.setAssignor(null);
        return this;
    }

    public Set<EmployeeSkills> getEmployeeskillsEmployees() {
        return this.employeeskillsEmployees;
    }

    public void setEmployeeskillsEmployees(Set<EmployeeSkills> employeeSkills) {
        if (this.employeeskillsEmployees != null) {
            this.employeeskillsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employeeSkills != null) {
            employeeSkills.forEach(i -> i.setEmployee(this));
        }
        this.employeeskillsEmployees = employeeSkills;
    }

    public Employees employeeskillsEmployees(Set<EmployeeSkills> employeeSkills) {
        this.setEmployeeskillsEmployees(employeeSkills);
        return this;
    }

    public Employees addEmployeeskillsEmployee(EmployeeSkills employeeSkills) {
        this.employeeskillsEmployees.add(employeeSkills);
        employeeSkills.setEmployee(this);
        return this;
    }

    public Employees removeEmployeeskillsEmployee(EmployeeSkills employeeSkills) {
        this.employeeskillsEmployees.remove(employeeSkills);
        employeeSkills.setEmployee(null);
        return this;
    }

    public Set<EmployeeTalents> getEmployeetalentsEmployees() {
        return this.employeetalentsEmployees;
    }

    public void setEmployeetalentsEmployees(Set<EmployeeTalents> employeeTalents) {
        if (this.employeetalentsEmployees != null) {
            this.employeetalentsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employeeTalents != null) {
            employeeTalents.forEach(i -> i.setEmployee(this));
        }
        this.employeetalentsEmployees = employeeTalents;
    }

    public Employees employeetalentsEmployees(Set<EmployeeTalents> employeeTalents) {
        this.setEmployeetalentsEmployees(employeeTalents);
        return this;
    }

    public Employees addEmployeetalentsEmployee(EmployeeTalents employeeTalents) {
        this.employeetalentsEmployees.add(employeeTalents);
        employeeTalents.setEmployee(this);
        return this;
    }

    public Employees removeEmployeetalentsEmployee(EmployeeTalents employeeTalents) {
        this.employeetalentsEmployees.remove(employeeTalents);
        employeeTalents.setEmployee(null);
        return this;
    }

    public Set<EmployeeWorks> getEmployeeworksEmployees() {
        return this.employeeworksEmployees;
    }

    public void setEmployeeworksEmployees(Set<EmployeeWorks> employeeWorks) {
        if (this.employeeworksEmployees != null) {
            this.employeeworksEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employeeWorks != null) {
            employeeWorks.forEach(i -> i.setEmployee(this));
        }
        this.employeeworksEmployees = employeeWorks;
    }

    public Employees employeeworksEmployees(Set<EmployeeWorks> employeeWorks) {
        this.setEmployeeworksEmployees(employeeWorks);
        return this;
    }

    public Employees addEmployeeworksEmployee(EmployeeWorks employeeWorks) {
        this.employeeworksEmployees.add(employeeWorks);
        employeeWorks.setEmployee(this);
        return this;
    }

    public Employees removeEmployeeworksEmployee(EmployeeWorks employeeWorks) {
        this.employeeworksEmployees.remove(employeeWorks);
        employeeWorks.setEmployee(null);
        return this;
    }

    public Set<Employees> getEmployeesManagers() {
        return this.employeesManagers;
    }

    public void setEmployeesManagers(Set<Employees> employees) {
        if (this.employeesManagers != null) {
            this.employeesManagers.forEach(i -> i.setManager(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setManager(this));
        }
        this.employeesManagers = employees;
    }

    public Employees employeesManagers(Set<Employees> employees) {
        this.setEmployeesManagers(employees);
        return this;
    }

    public Employees addEmployeesManager(Employees employees) {
        this.employeesManagers.add(employees);
        employees.setManager(this);
        return this;
    }

    public Employees removeEmployeesManager(Employees employees) {
        this.employeesManagers.remove(employees);
        employees.setManager(null);
        return this;
    }

    public Set<EmploymentHistory> getEmploymenthistoryEmployees() {
        return this.employmenthistoryEmployees;
    }

    public void setEmploymenthistoryEmployees(Set<EmploymentHistory> employmentHistories) {
        if (this.employmenthistoryEmployees != null) {
            this.employmenthistoryEmployees.forEach(i -> i.setEmployee(null));
        }
        if (employmentHistories != null) {
            employmentHistories.forEach(i -> i.setEmployee(this));
        }
        this.employmenthistoryEmployees = employmentHistories;
    }

    public Employees employmenthistoryEmployees(Set<EmploymentHistory> employmentHistories) {
        this.setEmploymenthistoryEmployees(employmentHistories);
        return this;
    }

    public Employees addEmploymenthistoryEmployee(EmploymentHistory employmentHistory) {
        this.employmenthistoryEmployees.add(employmentHistory);
        employmentHistory.setEmployee(this);
        return this;
    }

    public Employees removeEmploymenthistoryEmployee(EmploymentHistory employmentHistory) {
        this.employmenthistoryEmployees.remove(employmentHistory);
        employmentHistory.setEmployee(null);
        return this;
    }

    public Set<FeedbackQuestions> getFeedbackquestionsEmployees() {
        return this.feedbackquestionsEmployees;
    }

    public void setFeedbackquestionsEmployees(Set<FeedbackQuestions> feedbackQuestions) {
        if (this.feedbackquestionsEmployees != null) {
            this.feedbackquestionsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (feedbackQuestions != null) {
            feedbackQuestions.forEach(i -> i.setEmployee(this));
        }
        this.feedbackquestionsEmployees = feedbackQuestions;
    }

    public Employees feedbackquestionsEmployees(Set<FeedbackQuestions> feedbackQuestions) {
        this.setFeedbackquestionsEmployees(feedbackQuestions);
        return this;
    }

    public Employees addFeedbackquestionsEmployee(FeedbackQuestions feedbackQuestions) {
        this.feedbackquestionsEmployees.add(feedbackQuestions);
        feedbackQuestions.setEmployee(this);
        return this;
    }

    public Employees removeFeedbackquestionsEmployee(FeedbackQuestions feedbackQuestions) {
        this.feedbackquestionsEmployees.remove(feedbackQuestions);
        feedbackQuestions.setEmployee(null);
        return this;
    }

    public Set<FeedbackRequests> getFeedbackrequestsEmployees() {
        return this.feedbackrequestsEmployees;
    }

    public void setFeedbackrequestsEmployees(Set<FeedbackRequests> feedbackRequests) {
        if (this.feedbackrequestsEmployees != null) {
            this.feedbackrequestsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (feedbackRequests != null) {
            feedbackRequests.forEach(i -> i.setEmployee(this));
        }
        this.feedbackrequestsEmployees = feedbackRequests;
    }

    public Employees feedbackrequestsEmployees(Set<FeedbackRequests> feedbackRequests) {
        this.setFeedbackrequestsEmployees(feedbackRequests);
        return this;
    }

    public Employees addFeedbackrequestsEmployee(FeedbackRequests feedbackRequests) {
        this.feedbackrequestsEmployees.add(feedbackRequests);
        feedbackRequests.setEmployee(this);
        return this;
    }

    public Employees removeFeedbackrequestsEmployee(FeedbackRequests feedbackRequests) {
        this.feedbackrequestsEmployees.remove(feedbackRequests);
        feedbackRequests.setEmployee(null);
        return this;
    }

    public Set<FeedbackRequests> getFeedbackrequestsLinemanagers() {
        return this.feedbackrequestsLinemanagers;
    }

    public void setFeedbackrequestsLinemanagers(Set<FeedbackRequests> feedbackRequests) {
        if (this.feedbackrequestsLinemanagers != null) {
            this.feedbackrequestsLinemanagers.forEach(i -> i.setLinemanager(null));
        }
        if (feedbackRequests != null) {
            feedbackRequests.forEach(i -> i.setLinemanager(this));
        }
        this.feedbackrequestsLinemanagers = feedbackRequests;
    }

    public Employees feedbackrequestsLinemanagers(Set<FeedbackRequests> feedbackRequests) {
        this.setFeedbackrequestsLinemanagers(feedbackRequests);
        return this;
    }

    public Employees addFeedbackrequestsLinemanager(FeedbackRequests feedbackRequests) {
        this.feedbackrequestsLinemanagers.add(feedbackRequests);
        feedbackRequests.setLinemanager(this);
        return this;
    }

    public Employees removeFeedbackrequestsLinemanager(FeedbackRequests feedbackRequests) {
        this.feedbackrequestsLinemanagers.remove(feedbackRequests);
        feedbackRequests.setLinemanager(null);
        return this;
    }

    public Set<FeedbackRespondents> getFeedbackrespondentsEmployees() {
        return this.feedbackrespondentsEmployees;
    }

    public void setFeedbackrespondentsEmployees(Set<FeedbackRespondents> feedbackRespondents) {
        if (this.feedbackrespondentsEmployees != null) {
            this.feedbackrespondentsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (feedbackRespondents != null) {
            feedbackRespondents.forEach(i -> i.setEmployee(this));
        }
        this.feedbackrespondentsEmployees = feedbackRespondents;
    }

    public Employees feedbackrespondentsEmployees(Set<FeedbackRespondents> feedbackRespondents) {
        this.setFeedbackrespondentsEmployees(feedbackRespondents);
        return this;
    }

    public Employees addFeedbackrespondentsEmployee(FeedbackRespondents feedbackRespondents) {
        this.feedbackrespondentsEmployees.add(feedbackRespondents);
        feedbackRespondents.setEmployee(this);
        return this;
    }

    public Employees removeFeedbackrespondentsEmployee(FeedbackRespondents feedbackRespondents) {
        this.feedbackrespondentsEmployees.remove(feedbackRespondents);
        feedbackRespondents.setEmployee(null);
        return this;
    }

    public Set<Interviews> getInterviewsEmployees() {
        return this.interviewsEmployees;
    }

    public void setInterviewsEmployees(Set<Interviews> interviews) {
        if (this.interviewsEmployees != null) {
            this.interviewsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (interviews != null) {
            interviews.forEach(i -> i.setEmployee(this));
        }
        this.interviewsEmployees = interviews;
    }

    public Employees interviewsEmployees(Set<Interviews> interviews) {
        this.setInterviewsEmployees(interviews);
        return this;
    }

    public Employees addInterviewsEmployee(Interviews interviews) {
        this.interviewsEmployees.add(interviews);
        interviews.setEmployee(this);
        return this;
    }

    public Employees removeInterviewsEmployee(Interviews interviews) {
        this.interviewsEmployees.remove(interviews);
        interviews.setEmployee(null);
        return this;
    }

    public Set<LeaveDetailAdjustmentLogs> getLeavedetailadjustmentlogsAdjustedbies() {
        return this.leavedetailadjustmentlogsAdjustedbies;
    }

    public void setLeavedetailadjustmentlogsAdjustedbies(Set<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogs) {
        if (this.leavedetailadjustmentlogsAdjustedbies != null) {
            this.leavedetailadjustmentlogsAdjustedbies.forEach(i -> i.setAdjustedBy(null));
        }
        if (leaveDetailAdjustmentLogs != null) {
            leaveDetailAdjustmentLogs.forEach(i -> i.setAdjustedBy(this));
        }
        this.leavedetailadjustmentlogsAdjustedbies = leaveDetailAdjustmentLogs;
    }

    public Employees leavedetailadjustmentlogsAdjustedbies(Set<LeaveDetailAdjustmentLogs> leaveDetailAdjustmentLogs) {
        this.setLeavedetailadjustmentlogsAdjustedbies(leaveDetailAdjustmentLogs);
        return this;
    }

    public Employees addLeavedetailadjustmentlogsAdjustedby(LeaveDetailAdjustmentLogs leaveDetailAdjustmentLogs) {
        this.leavedetailadjustmentlogsAdjustedbies.add(leaveDetailAdjustmentLogs);
        leaveDetailAdjustmentLogs.setAdjustedBy(this);
        return this;
    }

    public Employees removeLeavedetailadjustmentlogsAdjustedby(LeaveDetailAdjustmentLogs leaveDetailAdjustmentLogs) {
        this.leavedetailadjustmentlogsAdjustedbies.remove(leaveDetailAdjustmentLogs);
        leaveDetailAdjustmentLogs.setAdjustedBy(null);
        return this;
    }

    public Set<LeaveRequestApprovers> getLeaverequestapproversUsers() {
        return this.leaverequestapproversUsers;
    }

    public void setLeaverequestapproversUsers(Set<LeaveRequestApprovers> leaveRequestApprovers) {
        if (this.leaverequestapproversUsers != null) {
            this.leaverequestapproversUsers.forEach(i -> i.setUser(null));
        }
        if (leaveRequestApprovers != null) {
            leaveRequestApprovers.forEach(i -> i.setUser(this));
        }
        this.leaverequestapproversUsers = leaveRequestApprovers;
    }

    public Employees leaverequestapproversUsers(Set<LeaveRequestApprovers> leaveRequestApprovers) {
        this.setLeaverequestapproversUsers(leaveRequestApprovers);
        return this;
    }

    public Employees addLeaverequestapproversUser(LeaveRequestApprovers leaveRequestApprovers) {
        this.leaverequestapproversUsers.add(leaveRequestApprovers);
        leaveRequestApprovers.setUser(this);
        return this;
    }

    public Employees removeLeaverequestapproversUser(LeaveRequestApprovers leaveRequestApprovers) {
        this.leaverequestapproversUsers.remove(leaveRequestApprovers);
        leaveRequestApprovers.setUser(null);
        return this;
    }

    public Set<LeaveRequestsOlds> getLeaverequestsoldsManagers() {
        return this.leaverequestsoldsManagers;
    }

    public void setLeaverequestsoldsManagers(Set<LeaveRequestsOlds> leaveRequestsOlds) {
        if (this.leaverequestsoldsManagers != null) {
            this.leaverequestsoldsManagers.forEach(i -> i.setManager(null));
        }
        if (leaveRequestsOlds != null) {
            leaveRequestsOlds.forEach(i -> i.setManager(this));
        }
        this.leaverequestsoldsManagers = leaveRequestsOlds;
    }

    public Employees leaverequestsoldsManagers(Set<LeaveRequestsOlds> leaveRequestsOlds) {
        this.setLeaverequestsoldsManagers(leaveRequestsOlds);
        return this;
    }

    public Employees addLeaverequestsoldsManager(LeaveRequestsOlds leaveRequestsOlds) {
        this.leaverequestsoldsManagers.add(leaveRequestsOlds);
        leaveRequestsOlds.setManager(this);
        return this;
    }

    public Employees removeLeaverequestsoldsManager(LeaveRequestsOlds leaveRequestsOlds) {
        this.leaverequestsoldsManagers.remove(leaveRequestsOlds);
        leaveRequestsOlds.setManager(null);
        return this;
    }

    public Set<LeaveRequestsOlds> getLeaverequestsoldsEmployees() {
        return this.leaverequestsoldsEmployees;
    }

    public void setLeaverequestsoldsEmployees(Set<LeaveRequestsOlds> leaveRequestsOlds) {
        if (this.leaverequestsoldsEmployees != null) {
            this.leaverequestsoldsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (leaveRequestsOlds != null) {
            leaveRequestsOlds.forEach(i -> i.setEmployee(this));
        }
        this.leaverequestsoldsEmployees = leaveRequestsOlds;
    }

    public Employees leaverequestsoldsEmployees(Set<LeaveRequestsOlds> leaveRequestsOlds) {
        this.setLeaverequestsoldsEmployees(leaveRequestsOlds);
        return this;
    }

    public Employees addLeaverequestsoldsEmployee(LeaveRequestsOlds leaveRequestsOlds) {
        this.leaverequestsoldsEmployees.add(leaveRequestsOlds);
        leaveRequestsOlds.setEmployee(this);
        return this;
    }

    public Employees removeLeaverequestsoldsEmployee(LeaveRequestsOlds leaveRequestsOlds) {
        this.leaverequestsoldsEmployees.remove(leaveRequestsOlds);
        leaveRequestsOlds.setEmployee(null);
        return this;
    }

    public Set<Leaves> getLeavesUsers() {
        return this.leavesUsers;
    }

    public void setLeavesUsers(Set<Leaves> leaves) {
        if (this.leavesUsers != null) {
            this.leavesUsers.forEach(i -> i.setUser(null));
        }
        if (leaves != null) {
            leaves.forEach(i -> i.setUser(this));
        }
        this.leavesUsers = leaves;
    }

    public Employees leavesUsers(Set<Leaves> leaves) {
        this.setLeavesUsers(leaves);
        return this;
    }

    public Employees addLeavesUser(Leaves leaves) {
        this.leavesUsers.add(leaves);
        leaves.setUser(this);
        return this;
    }

    public Employees removeLeavesUser(Leaves leaves) {
        this.leavesUsers.remove(leaves);
        leaves.setUser(null);
        return this;
    }

    public Set<NotificationSentEmailLogs> getNotificationsentemaillogsRecipients() {
        return this.notificationsentemaillogsRecipients;
    }

    public void setNotificationsentemaillogsRecipients(Set<NotificationSentEmailLogs> notificationSentEmailLogs) {
        if (this.notificationsentemaillogsRecipients != null) {
            this.notificationsentemaillogsRecipients.forEach(i -> i.setRecipient(null));
        }
        if (notificationSentEmailLogs != null) {
            notificationSentEmailLogs.forEach(i -> i.setRecipient(this));
        }
        this.notificationsentemaillogsRecipients = notificationSentEmailLogs;
    }

    public Employees notificationsentemaillogsRecipients(Set<NotificationSentEmailLogs> notificationSentEmailLogs) {
        this.setNotificationsentemaillogsRecipients(notificationSentEmailLogs);
        return this;
    }

    public Employees addNotificationsentemaillogsRecipient(NotificationSentEmailLogs notificationSentEmailLogs) {
        this.notificationsentemaillogsRecipients.add(notificationSentEmailLogs);
        notificationSentEmailLogs.setRecipient(this);
        return this;
    }

    public Employees removeNotificationsentemaillogsRecipient(NotificationSentEmailLogs notificationSentEmailLogs) {
        this.notificationsentemaillogsRecipients.remove(notificationSentEmailLogs);
        notificationSentEmailLogs.setRecipient(null);
        return this;
    }

    public Set<PcRatings> getPcratingsEmployees() {
        return this.pcratingsEmployees;
    }

    public void setPcratingsEmployees(Set<PcRatings> pcRatings) {
        if (this.pcratingsEmployees != null) {
            this.pcratingsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (pcRatings != null) {
            pcRatings.forEach(i -> i.setEmployee(this));
        }
        this.pcratingsEmployees = pcRatings;
    }

    public Employees pcratingsEmployees(Set<PcRatings> pcRatings) {
        this.setPcratingsEmployees(pcRatings);
        return this;
    }

    public Employees addPcratingsEmployee(PcRatings pcRatings) {
        this.pcratingsEmployees.add(pcRatings);
        pcRatings.setEmployee(this);
        return this;
    }

    public Employees removePcratingsEmployee(PcRatings pcRatings) {
        this.pcratingsEmployees.remove(pcRatings);
        pcRatings.setEmployee(null);
        return this;
    }

    public Set<PcRatingsTrainings> getPcratingstrainingsSuccessorfors() {
        return this.pcratingstrainingsSuccessorfors;
    }

    public void setPcratingstrainingsSuccessorfors(Set<PcRatingsTrainings> pcRatingsTrainings) {
        if (this.pcratingstrainingsSuccessorfors != null) {
            this.pcratingstrainingsSuccessorfors.forEach(i -> i.setSuccessorFor(null));
        }
        if (pcRatingsTrainings != null) {
            pcRatingsTrainings.forEach(i -> i.setSuccessorFor(this));
        }
        this.pcratingstrainingsSuccessorfors = pcRatingsTrainings;
    }

    public Employees pcratingstrainingsSuccessorfors(Set<PcRatingsTrainings> pcRatingsTrainings) {
        this.setPcratingstrainingsSuccessorfors(pcRatingsTrainings);
        return this;
    }

    public Employees addPcratingstrainingsSuccessorfor(PcRatingsTrainings pcRatingsTrainings) {
        this.pcratingstrainingsSuccessorfors.add(pcRatingsTrainings);
        pcRatingsTrainings.setSuccessorFor(this);
        return this;
    }

    public Employees removePcratingstrainingsSuccessorfor(PcRatingsTrainings pcRatingsTrainings) {
        this.pcratingstrainingsSuccessorfors.remove(pcRatingsTrainings);
        pcRatingsTrainings.setSuccessorFor(null);
        return this;
    }

    public Set<PerformanceCycleEmployeeRating> getPerformancecycleemployeeratingEmployees() {
        return this.performancecycleemployeeratingEmployees;
    }

    public void setPerformancecycleemployeeratingEmployees(Set<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatings) {
        if (this.performancecycleemployeeratingEmployees != null) {
            this.performancecycleemployeeratingEmployees.forEach(i -> i.setEmployee(null));
        }
        if (performanceCycleEmployeeRatings != null) {
            performanceCycleEmployeeRatings.forEach(i -> i.setEmployee(this));
        }
        this.performancecycleemployeeratingEmployees = performanceCycleEmployeeRatings;
    }

    public Employees performancecycleemployeeratingEmployees(Set<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatings) {
        this.setPerformancecycleemployeeratingEmployees(performanceCycleEmployeeRatings);
        return this;
    }

    public Employees addPerformancecycleemployeeratingEmployee(PerformanceCycleEmployeeRating performanceCycleEmployeeRating) {
        this.performancecycleemployeeratingEmployees.add(performanceCycleEmployeeRating);
        performanceCycleEmployeeRating.setEmployee(this);
        return this;
    }

    public Employees removePerformancecycleemployeeratingEmployee(PerformanceCycleEmployeeRating performanceCycleEmployeeRating) {
        this.performancecycleemployeeratingEmployees.remove(performanceCycleEmployeeRating);
        performanceCycleEmployeeRating.setEmployee(null);
        return this;
    }

    public Set<PerformanceCycleEmployeeRating> getPerformancecycleemployeeratingManagers() {
        return this.performancecycleemployeeratingManagers;
    }

    public void setPerformancecycleemployeeratingManagers(Set<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatings) {
        if (this.performancecycleemployeeratingManagers != null) {
            this.performancecycleemployeeratingManagers.forEach(i -> i.setManager(null));
        }
        if (performanceCycleEmployeeRatings != null) {
            performanceCycleEmployeeRatings.forEach(i -> i.setManager(this));
        }
        this.performancecycleemployeeratingManagers = performanceCycleEmployeeRatings;
    }

    public Employees performancecycleemployeeratingManagers(Set<PerformanceCycleEmployeeRating> performanceCycleEmployeeRatings) {
        this.setPerformancecycleemployeeratingManagers(performanceCycleEmployeeRatings);
        return this;
    }

    public Employees addPerformancecycleemployeeratingManager(PerformanceCycleEmployeeRating performanceCycleEmployeeRating) {
        this.performancecycleemployeeratingManagers.add(performanceCycleEmployeeRating);
        performanceCycleEmployeeRating.setManager(this);
        return this;
    }

    public Employees removePerformancecycleemployeeratingManager(PerformanceCycleEmployeeRating performanceCycleEmployeeRating) {
        this.performancecycleemployeeratingManagers.remove(performanceCycleEmployeeRating);
        performanceCycleEmployeeRating.setManager(null);
        return this;
    }

    public Set<ProjectCycles> getProjectcyclesAllowedafterduedatebies() {
        return this.projectcyclesAllowedafterduedatebies;
    }

    public void setProjectcyclesAllowedafterduedatebies(Set<ProjectCycles> projectCycles) {
        if (this.projectcyclesAllowedafterduedatebies != null) {
            this.projectcyclesAllowedafterduedatebies.forEach(i -> i.setAllowedafterduedateby(null));
        }
        if (projectCycles != null) {
            projectCycles.forEach(i -> i.setAllowedafterduedateby(this));
        }
        this.projectcyclesAllowedafterduedatebies = projectCycles;
    }

    public Employees projectcyclesAllowedafterduedatebies(Set<ProjectCycles> projectCycles) {
        this.setProjectcyclesAllowedafterduedatebies(projectCycles);
        return this;
    }

    public Employees addProjectcyclesAllowedafterduedateby(ProjectCycles projectCycles) {
        this.projectcyclesAllowedafterduedatebies.add(projectCycles);
        projectCycles.setAllowedafterduedateby(this);
        return this;
    }

    public Employees removeProjectcyclesAllowedafterduedateby(ProjectCycles projectCycles) {
        this.projectcyclesAllowedafterduedatebies.remove(projectCycles);
        projectCycles.setAllowedafterduedateby(null);
        return this;
    }

    public Set<ProjectCycles> getProjectcyclesArchitects() {
        return this.projectcyclesArchitects;
    }

    public void setProjectcyclesArchitects(Set<ProjectCycles> projectCycles) {
        if (this.projectcyclesArchitects != null) {
            this.projectcyclesArchitects.forEach(i -> i.setArchitect(null));
        }
        if (projectCycles != null) {
            projectCycles.forEach(i -> i.setArchitect(this));
        }
        this.projectcyclesArchitects = projectCycles;
    }

    public Employees projectcyclesArchitects(Set<ProjectCycles> projectCycles) {
        this.setProjectcyclesArchitects(projectCycles);
        return this;
    }

    public Employees addProjectcyclesArchitect(ProjectCycles projectCycles) {
        this.projectcyclesArchitects.add(projectCycles);
        projectCycles.setArchitect(this);
        return this;
    }

    public Employees removeProjectcyclesArchitect(ProjectCycles projectCycles) {
        this.projectcyclesArchitects.remove(projectCycles);
        projectCycles.setArchitect(null);
        return this;
    }

    public Set<ProjectCycles> getProjectcyclesProjectmanagers() {
        return this.projectcyclesProjectmanagers;
    }

    public void setProjectcyclesProjectmanagers(Set<ProjectCycles> projectCycles) {
        if (this.projectcyclesProjectmanagers != null) {
            this.projectcyclesProjectmanagers.forEach(i -> i.setProjectmanager(null));
        }
        if (projectCycles != null) {
            projectCycles.forEach(i -> i.setProjectmanager(this));
        }
        this.projectcyclesProjectmanagers = projectCycles;
    }

    public Employees projectcyclesProjectmanagers(Set<ProjectCycles> projectCycles) {
        this.setProjectcyclesProjectmanagers(projectCycles);
        return this;
    }

    public Employees addProjectcyclesProjectmanager(ProjectCycles projectCycles) {
        this.projectcyclesProjectmanagers.add(projectCycles);
        projectCycles.setProjectmanager(this);
        return this;
    }

    public Employees removeProjectcyclesProjectmanager(ProjectCycles projectCycles) {
        this.projectcyclesProjectmanagers.remove(projectCycles);
        projectCycles.setProjectmanager(null);
        return this;
    }

    public Set<ProjectLeadership> getProjectleadershipEmployees() {
        return this.projectleadershipEmployees;
    }

    public void setProjectleadershipEmployees(Set<ProjectLeadership> projectLeaderships) {
        if (this.projectleadershipEmployees != null) {
            this.projectleadershipEmployees.forEach(i -> i.setEmployee(null));
        }
        if (projectLeaderships != null) {
            projectLeaderships.forEach(i -> i.setEmployee(this));
        }
        this.projectleadershipEmployees = projectLeaderships;
    }

    public Employees projectleadershipEmployees(Set<ProjectLeadership> projectLeaderships) {
        this.setProjectleadershipEmployees(projectLeaderships);
        return this;
    }

    public Employees addProjectleadershipEmployee(ProjectLeadership projectLeadership) {
        this.projectleadershipEmployees.add(projectLeadership);
        projectLeadership.setEmployee(this);
        return this;
    }

    public Employees removeProjectleadershipEmployee(ProjectLeadership projectLeadership) {
        this.projectleadershipEmployees.remove(projectLeadership);
        projectLeadership.setEmployee(null);
        return this;
    }

    public Set<Projects> getProjectsProjectmanagers() {
        return this.projectsProjectmanagers;
    }

    public void setProjectsProjectmanagers(Set<Projects> projects) {
        if (this.projectsProjectmanagers != null) {
            this.projectsProjectmanagers.forEach(i -> i.setProjectmanager(null));
        }
        if (projects != null) {
            projects.forEach(i -> i.setProjectmanager(this));
        }
        this.projectsProjectmanagers = projects;
    }

    public Employees projectsProjectmanagers(Set<Projects> projects) {
        this.setProjectsProjectmanagers(projects);
        return this;
    }

    public Employees addProjectsProjectmanager(Projects projects) {
        this.projectsProjectmanagers.add(projects);
        projects.setProjectmanager(this);
        return this;
    }

    public Employees removeProjectsProjectmanager(Projects projects) {
        this.projectsProjectmanagers.remove(projects);
        projects.setProjectmanager(null);
        return this;
    }

    public Set<Ratings> getRatingsRaters() {
        return this.ratingsRaters;
    }

    public void setRatingsRaters(Set<Ratings> ratings) {
        if (this.ratingsRaters != null) {
            this.ratingsRaters.forEach(i -> i.setRater(null));
        }
        if (ratings != null) {
            ratings.forEach(i -> i.setRater(this));
        }
        this.ratingsRaters = ratings;
    }

    public Employees ratingsRaters(Set<Ratings> ratings) {
        this.setRatingsRaters(ratings);
        return this;
    }

    public Employees addRatingsRater(Ratings ratings) {
        this.ratingsRaters.add(ratings);
        ratings.setRater(this);
        return this;
    }

    public Employees removeRatingsRater(Ratings ratings) {
        this.ratingsRaters.remove(ratings);
        ratings.setRater(null);
        return this;
    }

    public Set<UserAttributes> getUserattributesUsers() {
        return this.userattributesUsers;
    }

    public void setUserattributesUsers(Set<UserAttributes> userAttributes) {
        if (this.userattributesUsers != null) {
            this.userattributesUsers.forEach(i -> i.setUser(null));
        }
        if (userAttributes != null) {
            userAttributes.forEach(i -> i.setUser(this));
        }
        this.userattributesUsers = userAttributes;
    }

    public Employees userattributesUsers(Set<UserAttributes> userAttributes) {
        this.setUserattributesUsers(userAttributes);
        return this;
    }

    public Employees addUserattributesUser(UserAttributes userAttributes) {
        this.userattributesUsers.add(userAttributes);
        userAttributes.setUser(this);
        return this;
    }

    public Employees removeUserattributesUser(UserAttributes userAttributes) {
        this.userattributesUsers.remove(userAttributes);
        userAttributes.setUser(null);
        return this;
    }

    public Set<UserGoals> getUsergoalsUsers() {
        return this.usergoalsUsers;
    }

    public void setUsergoalsUsers(Set<UserGoals> userGoals) {
        if (this.usergoalsUsers != null) {
            this.usergoalsUsers.forEach(i -> i.setUser(null));
        }
        if (userGoals != null) {
            userGoals.forEach(i -> i.setUser(this));
        }
        this.usergoalsUsers = userGoals;
    }

    public Employees usergoalsUsers(Set<UserGoals> userGoals) {
        this.setUsergoalsUsers(userGoals);
        return this;
    }

    public Employees addUsergoalsUser(UserGoals userGoals) {
        this.usergoalsUsers.add(userGoals);
        userGoals.setUser(this);
        return this;
    }

    public Employees removeUsergoalsUser(UserGoals userGoals) {
        this.usergoalsUsers.remove(userGoals);
        userGoals.setUser(null);
        return this;
    }

    public Set<UserRatings> getUserratingsUsers() {
        return this.userratingsUsers;
    }

    public void setUserratingsUsers(Set<UserRatings> userRatings) {
        if (this.userratingsUsers != null) {
            this.userratingsUsers.forEach(i -> i.setUser(null));
        }
        if (userRatings != null) {
            userRatings.forEach(i -> i.setUser(this));
        }
        this.userratingsUsers = userRatings;
    }

    public Employees userratingsUsers(Set<UserRatings> userRatings) {
        this.setUserratingsUsers(userRatings);
        return this;
    }

    public Employees addUserratingsUser(UserRatings userRatings) {
        this.userratingsUsers.add(userRatings);
        userRatings.setUser(this);
        return this;
    }

    public Employees removeUserratingsUser(UserRatings userRatings) {
        this.userratingsUsers.remove(userRatings);
        userRatings.setUser(null);
        return this;
    }

    public Set<UserRatings> getUserratingsReviewmanagers() {
        return this.userratingsReviewmanagers;
    }

    public void setUserratingsReviewmanagers(Set<UserRatings> userRatings) {
        if (this.userratingsReviewmanagers != null) {
            this.userratingsReviewmanagers.forEach(i -> i.setReviewManager(null));
        }
        if (userRatings != null) {
            userRatings.forEach(i -> i.setReviewManager(this));
        }
        this.userratingsReviewmanagers = userRatings;
    }

    public Employees userratingsReviewmanagers(Set<UserRatings> userRatings) {
        this.setUserratingsReviewmanagers(userRatings);
        return this;
    }

    public Employees addUserratingsReviewmanager(UserRatings userRatings) {
        this.userratingsReviewmanagers.add(userRatings);
        userRatings.setReviewManager(this);
        return this;
    }

    public Employees removeUserratingsReviewmanager(UserRatings userRatings) {
        this.userratingsReviewmanagers.remove(userRatings);
        userRatings.setReviewManager(null);
        return this;
    }

    public Set<UserRatingsRemarks> getUserratingsremarksRaters() {
        return this.userratingsremarksRaters;
    }

    public void setUserratingsremarksRaters(Set<UserRatingsRemarks> userRatingsRemarks) {
        if (this.userratingsremarksRaters != null) {
            this.userratingsremarksRaters.forEach(i -> i.setRater(null));
        }
        if (userRatingsRemarks != null) {
            userRatingsRemarks.forEach(i -> i.setRater(this));
        }
        this.userratingsremarksRaters = userRatingsRemarks;
    }

    public Employees userratingsremarksRaters(Set<UserRatingsRemarks> userRatingsRemarks) {
        this.setUserratingsremarksRaters(userRatingsRemarks);
        return this;
    }

    public Employees addUserratingsremarksRater(UserRatingsRemarks userRatingsRemarks) {
        this.userratingsremarksRaters.add(userRatingsRemarks);
        userRatingsRemarks.setRater(this);
        return this;
    }

    public Employees removeUserratingsremarksRater(UserRatingsRemarks userRatingsRemarks) {
        this.userratingsremarksRaters.remove(userRatingsRemarks);
        userRatingsRemarks.setRater(null);
        return this;
    }

    public Set<UserRelations> getUserrelationsUsers() {
        return this.userrelationsUsers;
    }

    public void setUserrelationsUsers(Set<UserRelations> userRelations) {
        if (this.userrelationsUsers != null) {
            this.userrelationsUsers.forEach(i -> i.setUser(null));
        }
        if (userRelations != null) {
            userRelations.forEach(i -> i.setUser(this));
        }
        this.userrelationsUsers = userRelations;
    }

    public Employees userrelationsUsers(Set<UserRelations> userRelations) {
        this.setUserrelationsUsers(userRelations);
        return this;
    }

    public Employees addUserrelationsUser(UserRelations userRelations) {
        this.userrelationsUsers.add(userRelations);
        userRelations.setUser(this);
        return this;
    }

    public Employees removeUserrelationsUser(UserRelations userRelations) {
        this.userrelationsUsers.remove(userRelations);
        userRelations.setUser(null);
        return this;
    }

    public Set<UserRelations> getUserrelationsRelatedusers() {
        return this.userrelationsRelatedusers;
    }

    public void setUserrelationsRelatedusers(Set<UserRelations> userRelations) {
        if (this.userrelationsRelatedusers != null) {
            this.userrelationsRelatedusers.forEach(i -> i.setRelatedUser(null));
        }
        if (userRelations != null) {
            userRelations.forEach(i -> i.setRelatedUser(this));
        }
        this.userrelationsRelatedusers = userRelations;
    }

    public Employees userrelationsRelatedusers(Set<UserRelations> userRelations) {
        this.setUserrelationsRelatedusers(userRelations);
        return this;
    }

    public Employees addUserrelationsRelateduser(UserRelations userRelations) {
        this.userrelationsRelatedusers.add(userRelations);
        userRelations.setRelatedUser(this);
        return this;
    }

    public Employees removeUserrelationsRelateduser(UserRelations userRelations) {
        this.userrelationsRelatedusers.remove(userRelations);
        userRelations.setRelatedUser(null);
        return this;
    }

    public Set<WorkLogs> getWorklogsEmployees() {
        return this.worklogsEmployees;
    }

    public void setWorklogsEmployees(Set<WorkLogs> workLogs) {
        if (this.worklogsEmployees != null) {
            this.worklogsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (workLogs != null) {
            workLogs.forEach(i -> i.setEmployee(this));
        }
        this.worklogsEmployees = workLogs;
    }

    public Employees worklogsEmployees(Set<WorkLogs> workLogs) {
        this.setWorklogsEmployees(workLogs);
        return this;
    }

    public Employees addWorklogsEmployee(WorkLogs workLogs) {
        this.worklogsEmployees.add(workLogs);
        workLogs.setEmployee(this);
        return this;
    }

    public Employees removeWorklogsEmployee(WorkLogs workLogs) {
        this.worklogsEmployees.remove(workLogs);
        workLogs.setEmployee(null);
        return this;
    }

    public Set<ZEmployeeProjects> getZemployeeprojectsEmployees() {
        return this.zemployeeprojectsEmployees;
    }

    public void setZemployeeprojectsEmployees(Set<ZEmployeeProjects> zEmployeeProjects) {
        if (this.zemployeeprojectsEmployees != null) {
            this.zemployeeprojectsEmployees.forEach(i -> i.setEmployee(null));
        }
        if (zEmployeeProjects != null) {
            zEmployeeProjects.forEach(i -> i.setEmployee(this));
        }
        this.zemployeeprojectsEmployees = zEmployeeProjects;
    }

    public Employees zemployeeprojectsEmployees(Set<ZEmployeeProjects> zEmployeeProjects) {
        this.setZemployeeprojectsEmployees(zEmployeeProjects);
        return this;
    }

    public Employees addZemployeeprojectsEmployee(ZEmployeeProjects zEmployeeProjects) {
        this.zemployeeprojectsEmployees.add(zEmployeeProjects);
        zEmployeeProjects.setEmployee(this);
        return this;
    }

    public Employees removeZemployeeprojectsEmployee(ZEmployeeProjects zEmployeeProjects) {
        this.zemployeeprojectsEmployees.remove(zEmployeeProjects);
        zEmployeeProjects.setEmployee(null);
        return this;
    }

    public Set<ZEmployeeProjects> getZemployeeprojectsAssignors() {
        return this.zemployeeprojectsAssignors;
    }

    public void setZemployeeprojectsAssignors(Set<ZEmployeeProjects> zEmployeeProjects) {
        if (this.zemployeeprojectsAssignors != null) {
            this.zemployeeprojectsAssignors.forEach(i -> i.setAssignor(null));
        }
        if (zEmployeeProjects != null) {
            zEmployeeProjects.forEach(i -> i.setAssignor(this));
        }
        this.zemployeeprojectsAssignors = zEmployeeProjects;
    }

    public Employees zemployeeprojectsAssignors(Set<ZEmployeeProjects> zEmployeeProjects) {
        this.setZemployeeprojectsAssignors(zEmployeeProjects);
        return this;
    }

    public Employees addZemployeeprojectsAssignor(ZEmployeeProjects zEmployeeProjects) {
        this.zemployeeprojectsAssignors.add(zEmployeeProjects);
        zEmployeeProjects.setAssignor(this);
        return this;
    }

    public Employees removeZemployeeprojectsAssignor(ZEmployeeProjects zEmployeeProjects) {
        this.zemployeeprojectsAssignors.remove(zEmployeeProjects);
        zEmployeeProjects.setAssignor(null);
        return this;
    }

    public Set<ZEmployeeProjects> getZemployeeprojectsProjectmanagers() {
        return this.zemployeeprojectsProjectmanagers;
    }

    public void setZemployeeprojectsProjectmanagers(Set<ZEmployeeProjects> zEmployeeProjects) {
        if (this.zemployeeprojectsProjectmanagers != null) {
            this.zemployeeprojectsProjectmanagers.forEach(i -> i.setProjectmanager(null));
        }
        if (zEmployeeProjects != null) {
            zEmployeeProjects.forEach(i -> i.setProjectmanager(this));
        }
        this.zemployeeprojectsProjectmanagers = zEmployeeProjects;
    }

    public Employees zemployeeprojectsProjectmanagers(Set<ZEmployeeProjects> zEmployeeProjects) {
        this.setZemployeeprojectsProjectmanagers(zEmployeeProjects);
        return this;
    }

    public Employees addZemployeeprojectsProjectmanager(ZEmployeeProjects zEmployeeProjects) {
        this.zemployeeprojectsProjectmanagers.add(zEmployeeProjects);
        zEmployeeProjects.setProjectmanager(this);
        return this;
    }

    public Employees removeZemployeeprojectsProjectmanager(ZEmployeeProjects zEmployeeProjects) {
        this.zemployeeprojectsProjectmanagers.remove(zEmployeeProjects);
        zEmployeeProjects.setProjectmanager(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employees)) {
            return false;
        }
        return id != null && id.equals(((Employees) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employees{" +
            "id=" + getId() +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", phonenumber='" + getPhonenumber() + "'" +
            ", dateofbirth='" + getDateofbirth() + "'" +
            ", email='" + getEmail() + "'" +
            ", skype='" + getSkype() + "'" +
            ", employeeDesignation='" + getEmployeeDesignation() + "'" +
            ", joiningdate='" + getJoiningdate() + "'" +
            ", area='" + getArea() + "'" +
            ", leavingdate='" + getLeavingdate() + "'" +
            ", notes='" + getNotes() + "'" +
            ", isactive='" + getIsactive() + "'" +
            ", googleid='" + getGoogleid() + "'" +
            ", oracleid='" + getOracleid() + "'" +
            ", deptt='" + getDeptt() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", genderid='" + getGenderid() + "'" +
            ", onprobation='" + getOnprobation() + "'" +
            ", employeeCompetency='" + getEmployeeCompetency() + "'" +
            ", resourcetype='" + getResourcetype() + "'" +
            ", grade='" + getGrade() + "'" +
            ", subgrade='" + getSubgrade() + "'" +
            ", imageurl='" + getImageurl() + "'" +
            ", resignationdate='" + getResignationdate() + "'" +
            ", graduationdate='" + getGraduationdate() + "'" +
            ", careerstartdate='" + getCareerstartdate() + "'" +
            ", externalexpyears=" + getExternalexpyears() +
            ", externalexpmonths=" + getExternalexpmonths() +
            ", placeofbirth='" + getPlaceofbirth() + "'" +
            ", hireddate='" + getHireddate() + "'" +
            ", lasttrackingupdate='" + getLasttrackingupdate() + "'" +
            ", middlename='" + getMiddlename() + "'" +
            ", grosssalary='" + getGrosssalary() + "'" +
            ", grosssalaryContentType='" + getGrosssalaryContentType() + "'" +
            ", fuelallowance='" + getFuelallowance() + "'" +
            ", fuelallowanceContentType='" + getFuelallowanceContentType() + "'" +
            ", mobilenumber='" + getMobilenumber() + "'" +
            ", resignationtype='" + getResignationtype() + "'" +
            ", primaryreasonforleaving='" + getPrimaryreasonforleaving() + "'" +
            ", secondaryreasonforleaving='" + getSecondaryreasonforleaving() + "'" +
            ", noticeperiodduration=" + getNoticeperiodduration() +
            ", noticeperiodserved=" + getNoticeperiodserved() +
            ", probationperiodduration=" + getProbationperiodduration() +
            "}";
    }
}
