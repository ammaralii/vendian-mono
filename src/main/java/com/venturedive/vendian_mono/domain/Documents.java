package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Documents.
 */
@Entity
@Table(name = "documents")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Documents implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "name", nullable = false)
    private byte[] name;

    @NotNull
    @Column(name = "name_content_type", nullable = false)
    private String nameContentType;

    @Lob
    @Column(name = "url", nullable = false)
    private byte[] url;

    @NotNull
    @Column(name = "url_content_type", nullable = false)
    private String urlContentType;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @OneToMany(mappedBy = "document")
    @JsonIgnoreProperties(value = { "document", "designation", "employeejobinfoJobdescriptions" }, allowSetters = true)
    private Set<DesignationJobDescriptions> designationjobdescriptionsDocuments = new HashSet<>();

    @OneToMany(mappedBy = "document")
    @JsonIgnoreProperties(value = { "document", "commenter", "employee" }, allowSetters = true)
    private Set<EmployeeComments> employeecommentsDocuments = new HashSet<>();

    @OneToMany(mappedBy = "document")
    @JsonIgnoreProperties(value = { "document", "employee" }, allowSetters = true)
    private Set<EmployeeDocuments> employeedocumentsDocuments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Documents id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getName() {
        return this.name;
    }

    public Documents name(byte[] name) {
        this.setName(name);
        return this;
    }

    public void setName(byte[] name) {
        this.name = name;
    }

    public String getNameContentType() {
        return this.nameContentType;
    }

    public Documents nameContentType(String nameContentType) {
        this.nameContentType = nameContentType;
        return this;
    }

    public void setNameContentType(String nameContentType) {
        this.nameContentType = nameContentType;
    }

    public byte[] getUrl() {
        return this.url;
    }

    public Documents url(byte[] url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(byte[] url) {
        this.url = url;
    }

    public String getUrlContentType() {
        return this.urlContentType;
    }

    public Documents urlContentType(String urlContentType) {
        this.urlContentType = urlContentType;
        return this;
    }

    public void setUrlContentType(String urlContentType) {
        this.urlContentType = urlContentType;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Documents createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Documents updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<DesignationJobDescriptions> getDesignationjobdescriptionsDocuments() {
        return this.designationjobdescriptionsDocuments;
    }

    public void setDesignationjobdescriptionsDocuments(Set<DesignationJobDescriptions> designationJobDescriptions) {
        if (this.designationjobdescriptionsDocuments != null) {
            this.designationjobdescriptionsDocuments.forEach(i -> i.setDocument(null));
        }
        if (designationJobDescriptions != null) {
            designationJobDescriptions.forEach(i -> i.setDocument(this));
        }
        this.designationjobdescriptionsDocuments = designationJobDescriptions;
    }

    public Documents designationjobdescriptionsDocuments(Set<DesignationJobDescriptions> designationJobDescriptions) {
        this.setDesignationjobdescriptionsDocuments(designationJobDescriptions);
        return this;
    }

    public Documents addDesignationjobdescriptionsDocument(DesignationJobDescriptions designationJobDescriptions) {
        this.designationjobdescriptionsDocuments.add(designationJobDescriptions);
        designationJobDescriptions.setDocument(this);
        return this;
    }

    public Documents removeDesignationjobdescriptionsDocument(DesignationJobDescriptions designationJobDescriptions) {
        this.designationjobdescriptionsDocuments.remove(designationJobDescriptions);
        designationJobDescriptions.setDocument(null);
        return this;
    }

    public Set<EmployeeComments> getEmployeecommentsDocuments() {
        return this.employeecommentsDocuments;
    }

    public void setEmployeecommentsDocuments(Set<EmployeeComments> employeeComments) {
        if (this.employeecommentsDocuments != null) {
            this.employeecommentsDocuments.forEach(i -> i.setDocument(null));
        }
        if (employeeComments != null) {
            employeeComments.forEach(i -> i.setDocument(this));
        }
        this.employeecommentsDocuments = employeeComments;
    }

    public Documents employeecommentsDocuments(Set<EmployeeComments> employeeComments) {
        this.setEmployeecommentsDocuments(employeeComments);
        return this;
    }

    public Documents addEmployeecommentsDocument(EmployeeComments employeeComments) {
        this.employeecommentsDocuments.add(employeeComments);
        employeeComments.setDocument(this);
        return this;
    }

    public Documents removeEmployeecommentsDocument(EmployeeComments employeeComments) {
        this.employeecommentsDocuments.remove(employeeComments);
        employeeComments.setDocument(null);
        return this;
    }

    public Set<EmployeeDocuments> getEmployeedocumentsDocuments() {
        return this.employeedocumentsDocuments;
    }

    public void setEmployeedocumentsDocuments(Set<EmployeeDocuments> employeeDocuments) {
        if (this.employeedocumentsDocuments != null) {
            this.employeedocumentsDocuments.forEach(i -> i.setDocument(null));
        }
        if (employeeDocuments != null) {
            employeeDocuments.forEach(i -> i.setDocument(this));
        }
        this.employeedocumentsDocuments = employeeDocuments;
    }

    public Documents employeedocumentsDocuments(Set<EmployeeDocuments> employeeDocuments) {
        this.setEmployeedocumentsDocuments(employeeDocuments);
        return this;
    }

    public Documents addEmployeedocumentsDocument(EmployeeDocuments employeeDocuments) {
        this.employeedocumentsDocuments.add(employeeDocuments);
        employeeDocuments.setDocument(this);
        return this;
    }

    public Documents removeEmployeedocumentsDocument(EmployeeDocuments employeeDocuments) {
        this.employeedocumentsDocuments.remove(employeeDocuments);
        employeeDocuments.setDocument(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Documents)) {
            return false;
        }
        return id != null && id.equals(((Documents) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Documents{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nameContentType='" + getNameContentType() + "'" +
            ", url='" + getUrl() + "'" +
            ", urlContentType='" + getUrlContentType() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
