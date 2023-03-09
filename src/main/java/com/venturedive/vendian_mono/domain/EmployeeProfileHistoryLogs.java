package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A EmployeeProfileHistoryLogs.
 */
@Entity
@Table(name = "employee_profile_history_logs")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeProfileHistoryLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "tablename", length = 255, nullable = false)
    private String tablename;

    @NotNull
    @Column(name = "rowid", nullable = false)
    private Integer rowid;

    @NotNull
    @Size(max = 255)
    @Column(name = "eventtype", length = 255, nullable = false)
    private String eventtype;

    @Lob
    @Column(name = "fields")
    private byte[] fields;

    @Column(name = "fields_content_type")
    private String fieldsContentType;

    @NotNull
    @Column(name = "updatedbyid", nullable = false)
    private Integer updatedbyid;

    @NotNull
    @Size(max = 255)
    @Column(name = "activityid", length = 255, nullable = false)
    private String activityid;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @NotNull
    @Size(max = 255)
    @Column(name = "category", length = 255, nullable = false)
    private String category;

    @ManyToOne(optional = false)
    @NotNull
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmployeeProfileHistoryLogs id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTablename() {
        return this.tablename;
    }

    public EmployeeProfileHistoryLogs tablename(String tablename) {
        this.setTablename(tablename);
        return this;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public Integer getRowid() {
        return this.rowid;
    }

    public EmployeeProfileHistoryLogs rowid(Integer rowid) {
        this.setRowid(rowid);
        return this;
    }

    public void setRowid(Integer rowid) {
        this.rowid = rowid;
    }

    public String getEventtype() {
        return this.eventtype;
    }

    public EmployeeProfileHistoryLogs eventtype(String eventtype) {
        this.setEventtype(eventtype);
        return this;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    public byte[] getFields() {
        return this.fields;
    }

    public EmployeeProfileHistoryLogs fields(byte[] fields) {
        this.setFields(fields);
        return this;
    }

    public void setFields(byte[] fields) {
        this.fields = fields;
    }

    public String getFieldsContentType() {
        return this.fieldsContentType;
    }

    public EmployeeProfileHistoryLogs fieldsContentType(String fieldsContentType) {
        this.fieldsContentType = fieldsContentType;
        return this;
    }

    public void setFieldsContentType(String fieldsContentType) {
        this.fieldsContentType = fieldsContentType;
    }

    public Integer getUpdatedbyid() {
        return this.updatedbyid;
    }

    public EmployeeProfileHistoryLogs updatedbyid(Integer updatedbyid) {
        this.setUpdatedbyid(updatedbyid);
        return this;
    }

    public void setUpdatedbyid(Integer updatedbyid) {
        this.updatedbyid = updatedbyid;
    }

    public String getActivityid() {
        return this.activityid;
    }

    public EmployeeProfileHistoryLogs activityid(String activityid) {
        this.setActivityid(activityid);
        return this;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public EmployeeProfileHistoryLogs createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public EmployeeProfileHistoryLogs updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public String getCategory() {
        return this.category;
    }

    public EmployeeProfileHistoryLogs category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public EmployeeProfileHistoryLogs employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeProfileHistoryLogs)) {
            return false;
        }
        return id != null && id.equals(((EmployeeProfileHistoryLogs) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeProfileHistoryLogs{" +
            "id=" + getId() +
            ", tablename='" + getTablename() + "'" +
            ", rowid=" + getRowid() +
            ", eventtype='" + getEventtype() + "'" +
            ", fields='" + getFields() + "'" +
            ", fieldsContentType='" + getFieldsContentType() + "'" +
            ", updatedbyid=" + getUpdatedbyid() +
            ", activityid='" + getActivityid() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
