package com.venturedive.vendian_mono.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A EmployeeProjectRatings20190826.
 */
@Entity
@Table(name = "employee_project_ratings_20190826")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeProjectRatings20190826 implements Serializable {

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

    @Column(name = "projectarchitectid")
    private Integer projectarchitectid;

    @Column(name = "projectmanagerid")
    private Integer projectmanagerid;

    @Column(name = "employeeid")
    private Integer employeeid;

    @Column(name = "projectcycleid")
    private Integer projectcycleid;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmployeeProjectRatings20190826 id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public EmployeeProjectRatings20190826 createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public EmployeeProjectRatings20190826 updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public byte[] getPmquality() {
        return this.pmquality;
    }

    public EmployeeProjectRatings20190826 pmquality(byte[] pmquality) {
        this.setPmquality(pmquality);
        return this;
    }

    public void setPmquality(byte[] pmquality) {
        this.pmquality = pmquality;
    }

    public String getPmqualityContentType() {
        return this.pmqualityContentType;
    }

    public EmployeeProjectRatings20190826 pmqualityContentType(String pmqualityContentType) {
        this.pmqualityContentType = pmqualityContentType;
        return this;
    }

    public void setPmqualityContentType(String pmqualityContentType) {
        this.pmqualityContentType = pmqualityContentType;
    }

    public byte[] getPmownership() {
        return this.pmownership;
    }

    public EmployeeProjectRatings20190826 pmownership(byte[] pmownership) {
        this.setPmownership(pmownership);
        return this;
    }

    public void setPmownership(byte[] pmownership) {
        this.pmownership = pmownership;
    }

    public String getPmownershipContentType() {
        return this.pmownershipContentType;
    }

    public EmployeeProjectRatings20190826 pmownershipContentType(String pmownershipContentType) {
        this.pmownershipContentType = pmownershipContentType;
        return this;
    }

    public void setPmownershipContentType(String pmownershipContentType) {
        this.pmownershipContentType = pmownershipContentType;
    }

    public byte[] getPmskill() {
        return this.pmskill;
    }

    public EmployeeProjectRatings20190826 pmskill(byte[] pmskill) {
        this.setPmskill(pmskill);
        return this;
    }

    public void setPmskill(byte[] pmskill) {
        this.pmskill = pmskill;
    }

    public String getPmskillContentType() {
        return this.pmskillContentType;
    }

    public EmployeeProjectRatings20190826 pmskillContentType(String pmskillContentType) {
        this.pmskillContentType = pmskillContentType;
        return this;
    }

    public void setPmskillContentType(String pmskillContentType) {
        this.pmskillContentType = pmskillContentType;
    }

    public byte[] getPmethics() {
        return this.pmethics;
    }

    public EmployeeProjectRatings20190826 pmethics(byte[] pmethics) {
        this.setPmethics(pmethics);
        return this;
    }

    public void setPmethics(byte[] pmethics) {
        this.pmethics = pmethics;
    }

    public String getPmethicsContentType() {
        return this.pmethicsContentType;
    }

    public EmployeeProjectRatings20190826 pmethicsContentType(String pmethicsContentType) {
        this.pmethicsContentType = pmethicsContentType;
        return this;
    }

    public void setPmethicsContentType(String pmethicsContentType) {
        this.pmethicsContentType = pmethicsContentType;
    }

    public byte[] getPmefficiency() {
        return this.pmefficiency;
    }

    public EmployeeProjectRatings20190826 pmefficiency(byte[] pmefficiency) {
        this.setPmefficiency(pmefficiency);
        return this;
    }

    public void setPmefficiency(byte[] pmefficiency) {
        this.pmefficiency = pmefficiency;
    }

    public String getPmefficiencyContentType() {
        return this.pmefficiencyContentType;
    }

    public EmployeeProjectRatings20190826 pmefficiencyContentType(String pmefficiencyContentType) {
        this.pmefficiencyContentType = pmefficiencyContentType;
        return this;
    }

    public void setPmefficiencyContentType(String pmefficiencyContentType) {
        this.pmefficiencyContentType = pmefficiencyContentType;
    }

    public byte[] getPmfreeze() {
        return this.pmfreeze;
    }

    public EmployeeProjectRatings20190826 pmfreeze(byte[] pmfreeze) {
        this.setPmfreeze(pmfreeze);
        return this;
    }

    public void setPmfreeze(byte[] pmfreeze) {
        this.pmfreeze = pmfreeze;
    }

    public String getPmfreezeContentType() {
        return this.pmfreezeContentType;
    }

    public EmployeeProjectRatings20190826 pmfreezeContentType(String pmfreezeContentType) {
        this.pmfreezeContentType = pmfreezeContentType;
        return this;
    }

    public void setPmfreezeContentType(String pmfreezeContentType) {
        this.pmfreezeContentType = pmfreezeContentType;
    }

    public byte[] getArchfreeze() {
        return this.archfreeze;
    }

    public EmployeeProjectRatings20190826 archfreeze(byte[] archfreeze) {
        this.setArchfreeze(archfreeze);
        return this;
    }

    public void setArchfreeze(byte[] archfreeze) {
        this.archfreeze = archfreeze;
    }

    public String getArchfreezeContentType() {
        return this.archfreezeContentType;
    }

    public EmployeeProjectRatings20190826 archfreezeContentType(String archfreezeContentType) {
        this.archfreezeContentType = archfreezeContentType;
        return this;
    }

    public void setArchfreezeContentType(String archfreezeContentType) {
        this.archfreezeContentType = archfreezeContentType;
    }

    public byte[] getPmcomment() {
        return this.pmcomment;
    }

    public EmployeeProjectRatings20190826 pmcomment(byte[] pmcomment) {
        this.setPmcomment(pmcomment);
        return this;
    }

    public void setPmcomment(byte[] pmcomment) {
        this.pmcomment = pmcomment;
    }

    public String getPmcommentContentType() {
        return this.pmcommentContentType;
    }

    public EmployeeProjectRatings20190826 pmcommentContentType(String pmcommentContentType) {
        this.pmcommentContentType = pmcommentContentType;
        return this;
    }

    public void setPmcommentContentType(String pmcommentContentType) {
        this.pmcommentContentType = pmcommentContentType;
    }

    public byte[] getArchquality() {
        return this.archquality;
    }

    public EmployeeProjectRatings20190826 archquality(byte[] archquality) {
        this.setArchquality(archquality);
        return this;
    }

    public void setArchquality(byte[] archquality) {
        this.archquality = archquality;
    }

    public String getArchqualityContentType() {
        return this.archqualityContentType;
    }

    public EmployeeProjectRatings20190826 archqualityContentType(String archqualityContentType) {
        this.archqualityContentType = archqualityContentType;
        return this;
    }

    public void setArchqualityContentType(String archqualityContentType) {
        this.archqualityContentType = archqualityContentType;
    }

    public byte[] getArchownership() {
        return this.archownership;
    }

    public EmployeeProjectRatings20190826 archownership(byte[] archownership) {
        this.setArchownership(archownership);
        return this;
    }

    public void setArchownership(byte[] archownership) {
        this.archownership = archownership;
    }

    public String getArchownershipContentType() {
        return this.archownershipContentType;
    }

    public EmployeeProjectRatings20190826 archownershipContentType(String archownershipContentType) {
        this.archownershipContentType = archownershipContentType;
        return this;
    }

    public void setArchownershipContentType(String archownershipContentType) {
        this.archownershipContentType = archownershipContentType;
    }

    public byte[] getArchskill() {
        return this.archskill;
    }

    public EmployeeProjectRatings20190826 archskill(byte[] archskill) {
        this.setArchskill(archskill);
        return this;
    }

    public void setArchskill(byte[] archskill) {
        this.archskill = archskill;
    }

    public String getArchskillContentType() {
        return this.archskillContentType;
    }

    public EmployeeProjectRatings20190826 archskillContentType(String archskillContentType) {
        this.archskillContentType = archskillContentType;
        return this;
    }

    public void setArchskillContentType(String archskillContentType) {
        this.archskillContentType = archskillContentType;
    }

    public byte[] getArchethics() {
        return this.archethics;
    }

    public EmployeeProjectRatings20190826 archethics(byte[] archethics) {
        this.setArchethics(archethics);
        return this;
    }

    public void setArchethics(byte[] archethics) {
        this.archethics = archethics;
    }

    public String getArchethicsContentType() {
        return this.archethicsContentType;
    }

    public EmployeeProjectRatings20190826 archethicsContentType(String archethicsContentType) {
        this.archethicsContentType = archethicsContentType;
        return this;
    }

    public void setArchethicsContentType(String archethicsContentType) {
        this.archethicsContentType = archethicsContentType;
    }

    public byte[] getArchefficiency() {
        return this.archefficiency;
    }

    public EmployeeProjectRatings20190826 archefficiency(byte[] archefficiency) {
        this.setArchefficiency(archefficiency);
        return this;
    }

    public void setArchefficiency(byte[] archefficiency) {
        this.archefficiency = archefficiency;
    }

    public String getArchefficiencyContentType() {
        return this.archefficiencyContentType;
    }

    public EmployeeProjectRatings20190826 archefficiencyContentType(String archefficiencyContentType) {
        this.archefficiencyContentType = archefficiencyContentType;
        return this;
    }

    public void setArchefficiencyContentType(String archefficiencyContentType) {
        this.archefficiencyContentType = archefficiencyContentType;
    }

    public byte[] getArchcomment() {
        return this.archcomment;
    }

    public EmployeeProjectRatings20190826 archcomment(byte[] archcomment) {
        this.setArchcomment(archcomment);
        return this;
    }

    public void setArchcomment(byte[] archcomment) {
        this.archcomment = archcomment;
    }

    public String getArchcommentContentType() {
        return this.archcommentContentType;
    }

    public EmployeeProjectRatings20190826 archcommentContentType(String archcommentContentType) {
        this.archcommentContentType = archcommentContentType;
        return this;
    }

    public void setArchcommentContentType(String archcommentContentType) {
        this.archcommentContentType = archcommentContentType;
    }

    public Integer getProjectarchitectid() {
        return this.projectarchitectid;
    }

    public EmployeeProjectRatings20190826 projectarchitectid(Integer projectarchitectid) {
        this.setProjectarchitectid(projectarchitectid);
        return this;
    }

    public void setProjectarchitectid(Integer projectarchitectid) {
        this.projectarchitectid = projectarchitectid;
    }

    public Integer getProjectmanagerid() {
        return this.projectmanagerid;
    }

    public EmployeeProjectRatings20190826 projectmanagerid(Integer projectmanagerid) {
        this.setProjectmanagerid(projectmanagerid);
        return this;
    }

    public void setProjectmanagerid(Integer projectmanagerid) {
        this.projectmanagerid = projectmanagerid;
    }

    public Integer getEmployeeid() {
        return this.employeeid;
    }

    public EmployeeProjectRatings20190826 employeeid(Integer employeeid) {
        this.setEmployeeid(employeeid);
        return this;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
    }

    public Integer getProjectcycleid() {
        return this.projectcycleid;
    }

    public EmployeeProjectRatings20190826 projectcycleid(Integer projectcycleid) {
        this.setProjectcycleid(projectcycleid);
        return this;
    }

    public void setProjectcycleid(Integer projectcycleid) {
        this.projectcycleid = projectcycleid;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeProjectRatings20190826)) {
            return false;
        }
        return id != null && id.equals(((EmployeeProjectRatings20190826) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeProjectRatings20190826{" +
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
            ", projectarchitectid=" + getProjectarchitectid() +
            ", projectmanagerid=" + getProjectmanagerid() +
            ", employeeid=" + getEmployeeid() +
            ", projectcycleid=" + getProjectcycleid() +
            "}";
    }
}
