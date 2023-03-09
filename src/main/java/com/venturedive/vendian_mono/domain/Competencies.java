package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Competencies.
 */
@Entity
@Table(name = "competencies")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Competencies implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @OneToMany(mappedBy = "competency")
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
    private Set<EmployeeJobInfo> employeejobinfoCompetencies = new HashSet<>();

    @OneToMany(mappedBy = "competency")
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
    private Set<Employees> employeesCompetencies = new HashSet<>();

    @OneToMany(mappedBy = "competency")
    @JsonIgnoreProperties(
        value = {
            "competency",
            "interviewsTracks",
            "questionsTracks",
            "questionsfrequencyperclienttrackTracks",
            "questionsfrequencypertrackTracks",
        },
        allowSetters = true
    )
    private Set<Tracks> tracksCompetencies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Competencies id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Competencies name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Competencies createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Competencies updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<EmployeeJobInfo> getEmployeejobinfoCompetencies() {
        return this.employeejobinfoCompetencies;
    }

    public void setEmployeejobinfoCompetencies(Set<EmployeeJobInfo> employeeJobInfos) {
        if (this.employeejobinfoCompetencies != null) {
            this.employeejobinfoCompetencies.forEach(i -> i.setCompetency(null));
        }
        if (employeeJobInfos != null) {
            employeeJobInfos.forEach(i -> i.setCompetency(this));
        }
        this.employeejobinfoCompetencies = employeeJobInfos;
    }

    public Competencies employeejobinfoCompetencies(Set<EmployeeJobInfo> employeeJobInfos) {
        this.setEmployeejobinfoCompetencies(employeeJobInfos);
        return this;
    }

    public Competencies addEmployeejobinfoCompetency(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoCompetencies.add(employeeJobInfo);
        employeeJobInfo.setCompetency(this);
        return this;
    }

    public Competencies removeEmployeejobinfoCompetency(EmployeeJobInfo employeeJobInfo) {
        this.employeejobinfoCompetencies.remove(employeeJobInfo);
        employeeJobInfo.setCompetency(null);
        return this;
    }

    public Set<Employees> getEmployeesCompetencies() {
        return this.employeesCompetencies;
    }

    public void setEmployeesCompetencies(Set<Employees> employees) {
        if (this.employeesCompetencies != null) {
            this.employeesCompetencies.forEach(i -> i.setCompetency(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setCompetency(this));
        }
        this.employeesCompetencies = employees;
    }

    public Competencies employeesCompetencies(Set<Employees> employees) {
        this.setEmployeesCompetencies(employees);
        return this;
    }

    public Competencies addEmployeesCompetency(Employees employees) {
        this.employeesCompetencies.add(employees);
        employees.setCompetency(this);
        return this;
    }

    public Competencies removeEmployeesCompetency(Employees employees) {
        this.employeesCompetencies.remove(employees);
        employees.setCompetency(null);
        return this;
    }

    public Set<Tracks> getTracksCompetencies() {
        return this.tracksCompetencies;
    }

    public void setTracksCompetencies(Set<Tracks> tracks) {
        if (this.tracksCompetencies != null) {
            this.tracksCompetencies.forEach(i -> i.setCompetency(null));
        }
        if (tracks != null) {
            tracks.forEach(i -> i.setCompetency(this));
        }
        this.tracksCompetencies = tracks;
    }

    public Competencies tracksCompetencies(Set<Tracks> tracks) {
        this.setTracksCompetencies(tracks);
        return this;
    }

    public Competencies addTracksCompetency(Tracks tracks) {
        this.tracksCompetencies.add(tracks);
        tracks.setCompetency(this);
        return this;
    }

    public Competencies removeTracksCompetency(Tracks tracks) {
        this.tracksCompetencies.remove(tracks);
        tracks.setCompetency(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Competencies)) {
            return false;
        }
        return id != null && id.equals(((Competencies) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Competencies{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
