package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A EmployeeProjectRatings.
 */
@Entity
@Table(name = "employee_project_ratings")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeProjectRatings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @Lob
    @Column(name = "pmquality")
    private byte[] pmquality;

    @Column(name = "pmquality_content_type")
    private String pmqualityContentType;

    @Lob
    @Column(name = "pmownership")
    private byte[] pmownership;

    @Column(name = "pmownership_content_type")
    private String pmownershipContentType;

    @Lob
    @Column(name = "pmskill")
    private byte[] pmskill;

    @Column(name = "pmskill_content_type")
    private String pmskillContentType;

    @Lob
    @Column(name = "pmethics")
    private byte[] pmethics;

    @Column(name = "pmethics_content_type")
    private String pmethicsContentType;

    @Lob
    @Column(name = "pmefficiency")
    private byte[] pmefficiency;

    @Column(name = "pmefficiency_content_type")
    private String pmefficiencyContentType;

    @Lob
    @Column(name = "pmfreeze")
    private byte[] pmfreeze;

    @Column(name = "pmfreeze_content_type")
    private String pmfreezeContentType;

    @Lob
    @Column(name = "archfreeze")
    private byte[] archfreeze;

    @Column(name = "archfreeze_content_type")
    private String archfreezeContentType;

    @Lob
    @Column(name = "pmcomment")
    private byte[] pmcomment;

    @Column(name = "pmcomment_content_type")
    private String pmcommentContentType;

    @Lob
    @Column(name = "archquality")
    private byte[] archquality;

    @Column(name = "archquality_content_type")
    private String archqualityContentType;

    @Lob
    @Column(name = "archownership")
    private byte[] archownership;

    @Column(name = "archownership_content_type")
    private String archownershipContentType;

    @Lob
    @Column(name = "archskill")
    private byte[] archskill;

    @Column(name = "archskill_content_type")
    private String archskillContentType;

    @Lob
    @Column(name = "archethics")
    private byte[] archethics;

    @Column(name = "archethics_content_type")
    private String archethicsContentType;

    @Lob
    @Column(name = "archefficiency")
    private byte[] archefficiency;

    @Column(name = "archefficiency_content_type")
    private String archefficiencyContentType;

    @Lob
    @Column(name = "archcomment")
    private byte[] archcomment;

    @Column(name = "archcomment_content_type")
    private String archcommentContentType;

    @Lob
    @Column(name = "archcodequality")
    private byte[] archcodequality;

    @Column(name = "archcodequality_content_type")
    private String archcodequalityContentType;

    @Lob
    @Column(name = "archdocumentation")
    private byte[] archdocumentation;

    @Column(name = "archdocumentation_content_type")
    private String archdocumentationContentType;

    @Lob
    @Column(name = "archcollaboration")
    private byte[] archcollaboration;

    @Column(name = "archcollaboration_content_type")
    private String archcollaborationContentType;

    @Lob
    @Column(name = "pmdocumentation")
    private byte[] pmdocumentation;

    @Column(name = "pmdocumentation_content_type")
    private String pmdocumentationContentType;

    @Lob
    @Column(name = "pmcollaboration")
    private byte[] pmcollaboration;

    @Column(name = "pmcollaboration_content_type")
    private String pmcollaborationContentType;

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
    private Employees projectarchitect;

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
    private Employees projectmanager;

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
    private Employees employee;

    @ManyToOne
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
    private ProjectCycles projectcycle;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmployeeProjectRatings id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public EmployeeProjectRatings createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public EmployeeProjectRatings updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public byte[] getPmquality() {
        return this.pmquality;
    }

    public EmployeeProjectRatings pmquality(byte[] pmquality) {
        this.setPmquality(pmquality);
        return this;
    }

    public void setPmquality(byte[] pmquality) {
        this.pmquality = pmquality;
    }

    public String getPmqualityContentType() {
        return this.pmqualityContentType;
    }

    public EmployeeProjectRatings pmqualityContentType(String pmqualityContentType) {
        this.pmqualityContentType = pmqualityContentType;
        return this;
    }

    public void setPmqualityContentType(String pmqualityContentType) {
        this.pmqualityContentType = pmqualityContentType;
    }

    public byte[] getPmownership() {
        return this.pmownership;
    }

    public EmployeeProjectRatings pmownership(byte[] pmownership) {
        this.setPmownership(pmownership);
        return this;
    }

    public void setPmownership(byte[] pmownership) {
        this.pmownership = pmownership;
    }

    public String getPmownershipContentType() {
        return this.pmownershipContentType;
    }

    public EmployeeProjectRatings pmownershipContentType(String pmownershipContentType) {
        this.pmownershipContentType = pmownershipContentType;
        return this;
    }

    public void setPmownershipContentType(String pmownershipContentType) {
        this.pmownershipContentType = pmownershipContentType;
    }

    public byte[] getPmskill() {
        return this.pmskill;
    }

    public EmployeeProjectRatings pmskill(byte[] pmskill) {
        this.setPmskill(pmskill);
        return this;
    }

    public void setPmskill(byte[] pmskill) {
        this.pmskill = pmskill;
    }

    public String getPmskillContentType() {
        return this.pmskillContentType;
    }

    public EmployeeProjectRatings pmskillContentType(String pmskillContentType) {
        this.pmskillContentType = pmskillContentType;
        return this;
    }

    public void setPmskillContentType(String pmskillContentType) {
        this.pmskillContentType = pmskillContentType;
    }

    public byte[] getPmethics() {
        return this.pmethics;
    }

    public EmployeeProjectRatings pmethics(byte[] pmethics) {
        this.setPmethics(pmethics);
        return this;
    }

    public void setPmethics(byte[] pmethics) {
        this.pmethics = pmethics;
    }

    public String getPmethicsContentType() {
        return this.pmethicsContentType;
    }

    public EmployeeProjectRatings pmethicsContentType(String pmethicsContentType) {
        this.pmethicsContentType = pmethicsContentType;
        return this;
    }

    public void setPmethicsContentType(String pmethicsContentType) {
        this.pmethicsContentType = pmethicsContentType;
    }

    public byte[] getPmefficiency() {
        return this.pmefficiency;
    }

    public EmployeeProjectRatings pmefficiency(byte[] pmefficiency) {
        this.setPmefficiency(pmefficiency);
        return this;
    }

    public void setPmefficiency(byte[] pmefficiency) {
        this.pmefficiency = pmefficiency;
    }

    public String getPmefficiencyContentType() {
        return this.pmefficiencyContentType;
    }

    public EmployeeProjectRatings pmefficiencyContentType(String pmefficiencyContentType) {
        this.pmefficiencyContentType = pmefficiencyContentType;
        return this;
    }

    public void setPmefficiencyContentType(String pmefficiencyContentType) {
        this.pmefficiencyContentType = pmefficiencyContentType;
    }

    public byte[] getPmfreeze() {
        return this.pmfreeze;
    }

    public EmployeeProjectRatings pmfreeze(byte[] pmfreeze) {
        this.setPmfreeze(pmfreeze);
        return this;
    }

    public void setPmfreeze(byte[] pmfreeze) {
        this.pmfreeze = pmfreeze;
    }

    public String getPmfreezeContentType() {
        return this.pmfreezeContentType;
    }

    public EmployeeProjectRatings pmfreezeContentType(String pmfreezeContentType) {
        this.pmfreezeContentType = pmfreezeContentType;
        return this;
    }

    public void setPmfreezeContentType(String pmfreezeContentType) {
        this.pmfreezeContentType = pmfreezeContentType;
    }

    public byte[] getArchfreeze() {
        return this.archfreeze;
    }

    public EmployeeProjectRatings archfreeze(byte[] archfreeze) {
        this.setArchfreeze(archfreeze);
        return this;
    }

    public void setArchfreeze(byte[] archfreeze) {
        this.archfreeze = archfreeze;
    }

    public String getArchfreezeContentType() {
        return this.archfreezeContentType;
    }

    public EmployeeProjectRatings archfreezeContentType(String archfreezeContentType) {
        this.archfreezeContentType = archfreezeContentType;
        return this;
    }

    public void setArchfreezeContentType(String archfreezeContentType) {
        this.archfreezeContentType = archfreezeContentType;
    }

    public byte[] getPmcomment() {
        return this.pmcomment;
    }

    public EmployeeProjectRatings pmcomment(byte[] pmcomment) {
        this.setPmcomment(pmcomment);
        return this;
    }

    public void setPmcomment(byte[] pmcomment) {
        this.pmcomment = pmcomment;
    }

    public String getPmcommentContentType() {
        return this.pmcommentContentType;
    }

    public EmployeeProjectRatings pmcommentContentType(String pmcommentContentType) {
        this.pmcommentContentType = pmcommentContentType;
        return this;
    }

    public void setPmcommentContentType(String pmcommentContentType) {
        this.pmcommentContentType = pmcommentContentType;
    }

    public byte[] getArchquality() {
        return this.archquality;
    }

    public EmployeeProjectRatings archquality(byte[] archquality) {
        this.setArchquality(archquality);
        return this;
    }

    public void setArchquality(byte[] archquality) {
        this.archquality = archquality;
    }

    public String getArchqualityContentType() {
        return this.archqualityContentType;
    }

    public EmployeeProjectRatings archqualityContentType(String archqualityContentType) {
        this.archqualityContentType = archqualityContentType;
        return this;
    }

    public void setArchqualityContentType(String archqualityContentType) {
        this.archqualityContentType = archqualityContentType;
    }

    public byte[] getArchownership() {
        return this.archownership;
    }

    public EmployeeProjectRatings archownership(byte[] archownership) {
        this.setArchownership(archownership);
        return this;
    }

    public void setArchownership(byte[] archownership) {
        this.archownership = archownership;
    }

    public String getArchownershipContentType() {
        return this.archownershipContentType;
    }

    public EmployeeProjectRatings archownershipContentType(String archownershipContentType) {
        this.archownershipContentType = archownershipContentType;
        return this;
    }

    public void setArchownershipContentType(String archownershipContentType) {
        this.archownershipContentType = archownershipContentType;
    }

    public byte[] getArchskill() {
        return this.archskill;
    }

    public EmployeeProjectRatings archskill(byte[] archskill) {
        this.setArchskill(archskill);
        return this;
    }

    public void setArchskill(byte[] archskill) {
        this.archskill = archskill;
    }

    public String getArchskillContentType() {
        return this.archskillContentType;
    }

    public EmployeeProjectRatings archskillContentType(String archskillContentType) {
        this.archskillContentType = archskillContentType;
        return this;
    }

    public void setArchskillContentType(String archskillContentType) {
        this.archskillContentType = archskillContentType;
    }

    public byte[] getArchethics() {
        return this.archethics;
    }

    public EmployeeProjectRatings archethics(byte[] archethics) {
        this.setArchethics(archethics);
        return this;
    }

    public void setArchethics(byte[] archethics) {
        this.archethics = archethics;
    }

    public String getArchethicsContentType() {
        return this.archethicsContentType;
    }

    public EmployeeProjectRatings archethicsContentType(String archethicsContentType) {
        this.archethicsContentType = archethicsContentType;
        return this;
    }

    public void setArchethicsContentType(String archethicsContentType) {
        this.archethicsContentType = archethicsContentType;
    }

    public byte[] getArchefficiency() {
        return this.archefficiency;
    }

    public EmployeeProjectRatings archefficiency(byte[] archefficiency) {
        this.setArchefficiency(archefficiency);
        return this;
    }

    public void setArchefficiency(byte[] archefficiency) {
        this.archefficiency = archefficiency;
    }

    public String getArchefficiencyContentType() {
        return this.archefficiencyContentType;
    }

    public EmployeeProjectRatings archefficiencyContentType(String archefficiencyContentType) {
        this.archefficiencyContentType = archefficiencyContentType;
        return this;
    }

    public void setArchefficiencyContentType(String archefficiencyContentType) {
        this.archefficiencyContentType = archefficiencyContentType;
    }

    public byte[] getArchcomment() {
        return this.archcomment;
    }

    public EmployeeProjectRatings archcomment(byte[] archcomment) {
        this.setArchcomment(archcomment);
        return this;
    }

    public void setArchcomment(byte[] archcomment) {
        this.archcomment = archcomment;
    }

    public String getArchcommentContentType() {
        return this.archcommentContentType;
    }

    public EmployeeProjectRatings archcommentContentType(String archcommentContentType) {
        this.archcommentContentType = archcommentContentType;
        return this;
    }

    public void setArchcommentContentType(String archcommentContentType) {
        this.archcommentContentType = archcommentContentType;
    }

    public byte[] getArchcodequality() {
        return this.archcodequality;
    }

    public EmployeeProjectRatings archcodequality(byte[] archcodequality) {
        this.setArchcodequality(archcodequality);
        return this;
    }

    public void setArchcodequality(byte[] archcodequality) {
        this.archcodequality = archcodequality;
    }

    public String getArchcodequalityContentType() {
        return this.archcodequalityContentType;
    }

    public EmployeeProjectRatings archcodequalityContentType(String archcodequalityContentType) {
        this.archcodequalityContentType = archcodequalityContentType;
        return this;
    }

    public void setArchcodequalityContentType(String archcodequalityContentType) {
        this.archcodequalityContentType = archcodequalityContentType;
    }

    public byte[] getArchdocumentation() {
        return this.archdocumentation;
    }

    public EmployeeProjectRatings archdocumentation(byte[] archdocumentation) {
        this.setArchdocumentation(archdocumentation);
        return this;
    }

    public void setArchdocumentation(byte[] archdocumentation) {
        this.archdocumentation = archdocumentation;
    }

    public String getArchdocumentationContentType() {
        return this.archdocumentationContentType;
    }

    public EmployeeProjectRatings archdocumentationContentType(String archdocumentationContentType) {
        this.archdocumentationContentType = archdocumentationContentType;
        return this;
    }

    public void setArchdocumentationContentType(String archdocumentationContentType) {
        this.archdocumentationContentType = archdocumentationContentType;
    }

    public byte[] getArchcollaboration() {
        return this.archcollaboration;
    }

    public EmployeeProjectRatings archcollaboration(byte[] archcollaboration) {
        this.setArchcollaboration(archcollaboration);
        return this;
    }

    public void setArchcollaboration(byte[] archcollaboration) {
        this.archcollaboration = archcollaboration;
    }

    public String getArchcollaborationContentType() {
        return this.archcollaborationContentType;
    }

    public EmployeeProjectRatings archcollaborationContentType(String archcollaborationContentType) {
        this.archcollaborationContentType = archcollaborationContentType;
        return this;
    }

    public void setArchcollaborationContentType(String archcollaborationContentType) {
        this.archcollaborationContentType = archcollaborationContentType;
    }

    public byte[] getPmdocumentation() {
        return this.pmdocumentation;
    }

    public EmployeeProjectRatings pmdocumentation(byte[] pmdocumentation) {
        this.setPmdocumentation(pmdocumentation);
        return this;
    }

    public void setPmdocumentation(byte[] pmdocumentation) {
        this.pmdocumentation = pmdocumentation;
    }

    public String getPmdocumentationContentType() {
        return this.pmdocumentationContentType;
    }

    public EmployeeProjectRatings pmdocumentationContentType(String pmdocumentationContentType) {
        this.pmdocumentationContentType = pmdocumentationContentType;
        return this;
    }

    public void setPmdocumentationContentType(String pmdocumentationContentType) {
        this.pmdocumentationContentType = pmdocumentationContentType;
    }

    public byte[] getPmcollaboration() {
        return this.pmcollaboration;
    }

    public EmployeeProjectRatings pmcollaboration(byte[] pmcollaboration) {
        this.setPmcollaboration(pmcollaboration);
        return this;
    }

    public void setPmcollaboration(byte[] pmcollaboration) {
        this.pmcollaboration = pmcollaboration;
    }

    public String getPmcollaborationContentType() {
        return this.pmcollaborationContentType;
    }

    public EmployeeProjectRatings pmcollaborationContentType(String pmcollaborationContentType) {
        this.pmcollaborationContentType = pmcollaborationContentType;
        return this;
    }

    public void setPmcollaborationContentType(String pmcollaborationContentType) {
        this.pmcollaborationContentType = pmcollaborationContentType;
    }

    public Employees getProjectarchitect() {
        return this.projectarchitect;
    }

    public void setProjectarchitect(Employees employees) {
        this.projectarchitect = employees;
    }

    public EmployeeProjectRatings projectarchitect(Employees employees) {
        this.setProjectarchitect(employees);
        return this;
    }

    public Employees getProjectmanager() {
        return this.projectmanager;
    }

    public void setProjectmanager(Employees employees) {
        this.projectmanager = employees;
    }

    public EmployeeProjectRatings projectmanager(Employees employees) {
        this.setProjectmanager(employees);
        return this;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public EmployeeProjectRatings employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    public ProjectCycles getProjectcycle() {
        return this.projectcycle;
    }

    public void setProjectcycle(ProjectCycles projectCycles) {
        this.projectcycle = projectCycles;
    }

    public EmployeeProjectRatings projectcycle(ProjectCycles projectCycles) {
        this.setProjectcycle(projectCycles);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeProjectRatings)) {
            return false;
        }
        return id != null && id.equals(((EmployeeProjectRatings) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeProjectRatings{" +
            "id=" + getId() +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", pmquality='" + getPmquality() + "'" +
            ", pmqualityContentType='" + getPmqualityContentType() + "'" +
            ", pmownership='" + getPmownership() + "'" +
            ", pmownershipContentType='" + getPmownershipContentType() + "'" +
            ", pmskill='" + getPmskill() + "'" +
            ", pmskillContentType='" + getPmskillContentType() + "'" +
            ", pmethics='" + getPmethics() + "'" +
            ", pmethicsContentType='" + getPmethicsContentType() + "'" +
            ", pmefficiency='" + getPmefficiency() + "'" +
            ", pmefficiencyContentType='" + getPmefficiencyContentType() + "'" +
            ", pmfreeze='" + getPmfreeze() + "'" +
            ", pmfreezeContentType='" + getPmfreezeContentType() + "'" +
            ", archfreeze='" + getArchfreeze() + "'" +
            ", archfreezeContentType='" + getArchfreezeContentType() + "'" +
            ", pmcomment='" + getPmcomment() + "'" +
            ", pmcommentContentType='" + getPmcommentContentType() + "'" +
            ", archquality='" + getArchquality() + "'" +
            ", archqualityContentType='" + getArchqualityContentType() + "'" +
            ", archownership='" + getArchownership() + "'" +
            ", archownershipContentType='" + getArchownershipContentType() + "'" +
            ", archskill='" + getArchskill() + "'" +
            ", archskillContentType='" + getArchskillContentType() + "'" +
            ", archethics='" + getArchethics() + "'" +
            ", archethicsContentType='" + getArchethicsContentType() + "'" +
            ", archefficiency='" + getArchefficiency() + "'" +
            ", archefficiencyContentType='" + getArchefficiencyContentType() + "'" +
            ", archcomment='" + getArchcomment() + "'" +
            ", archcommentContentType='" + getArchcommentContentType() + "'" +
            ", archcodequality='" + getArchcodequality() + "'" +
            ", archcodequalityContentType='" + getArchcodequalityContentType() + "'" +
            ", archdocumentation='" + getArchdocumentation() + "'" +
            ", archdocumentationContentType='" + getArchdocumentationContentType() + "'" +
            ", archcollaboration='" + getArchcollaboration() + "'" +
            ", archcollaborationContentType='" + getArchcollaborationContentType() + "'" +
            ", pmdocumentation='" + getPmdocumentation() + "'" +
            ", pmdocumentationContentType='" + getPmdocumentationContentType() + "'" +
            ", pmcollaboration='" + getPmcollaboration() + "'" +
            ", pmcollaborationContentType='" + getPmcollaborationContentType() + "'" +
            "}";
    }
}
