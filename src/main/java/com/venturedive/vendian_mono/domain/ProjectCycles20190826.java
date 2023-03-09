package com.venturedive.vendian_mono.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A ProjectCycles20190826.
 */
@Entity
@Table(name = "project_cycles_20190826")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectCycles20190826 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "isactive")
    private Boolean isactive;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @Column(name = "performancecycleid")
    private Integer performancecycleid;

    @Column(name = "projectid")
    private Integer projectid;

    @Column(name = "allowedafterduedateby")
    private Integer allowedafterduedateby;

    @Column(name = "allowedafterduedateat")
    private Instant allowedafterduedateat;

    @Column(name = "duedate")
    private Instant duedate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProjectCycles20190826 id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsactive() {
        return this.isactive;
    }

    public ProjectCycles20190826 isactive(Boolean isactive) {
        this.setIsactive(isactive);
        return this;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public ProjectCycles20190826 createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public ProjectCycles20190826 updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Integer getPerformancecycleid() {
        return this.performancecycleid;
    }

    public ProjectCycles20190826 performancecycleid(Integer performancecycleid) {
        this.setPerformancecycleid(performancecycleid);
        return this;
    }

    public void setPerformancecycleid(Integer performancecycleid) {
        this.performancecycleid = performancecycleid;
    }

    public Integer getProjectid() {
        return this.projectid;
    }

    public ProjectCycles20190826 projectid(Integer projectid) {
        this.setProjectid(projectid);
        return this;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public Integer getAllowedafterduedateby() {
        return this.allowedafterduedateby;
    }

    public ProjectCycles20190826 allowedafterduedateby(Integer allowedafterduedateby) {
        this.setAllowedafterduedateby(allowedafterduedateby);
        return this;
    }

    public void setAllowedafterduedateby(Integer allowedafterduedateby) {
        this.allowedafterduedateby = allowedafterduedateby;
    }

    public Instant getAllowedafterduedateat() {
        return this.allowedafterduedateat;
    }

    public ProjectCycles20190826 allowedafterduedateat(Instant allowedafterduedateat) {
        this.setAllowedafterduedateat(allowedafterduedateat);
        return this;
    }

    public void setAllowedafterduedateat(Instant allowedafterduedateat) {
        this.allowedafterduedateat = allowedafterduedateat;
    }

    public Instant getDuedate() {
        return this.duedate;
    }

    public ProjectCycles20190826 duedate(Instant duedate) {
        this.setDuedate(duedate);
        return this;
    }

    public void setDuedate(Instant duedate) {
        this.duedate = duedate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectCycles20190826)) {
            return false;
        }
        return id != null && id.equals(((ProjectCycles20190826) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectCycles20190826{" +
            "id=" + getId() +
            ", isactive='" + getIsactive() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", performancecycleid=" + getPerformancecycleid() +
            ", projectid=" + getProjectid() +
            ", allowedafterduedateby=" + getAllowedafterduedateby() +
            ", allowedafterduedateat='" + getAllowedafterduedateat() + "'" +
            ", duedate='" + getDuedate() + "'" +
            "}";
    }
}
