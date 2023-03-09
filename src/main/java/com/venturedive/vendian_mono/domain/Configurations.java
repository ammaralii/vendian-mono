package com.venturedive.vendian_mono.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Configurations.
 */
@Entity
@Table(name = "configurations")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Configurations implements Serializable {

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
    @Size(max = 255)
    @Column(name = "jhi_group", length = 255, nullable = false)
    private String group;

    @Column(name = "int_value")
    private Integer intValue;

    @Size(max = 255)
    @Column(name = "string_value", length = 255)
    private String stringValue;

    @Column(name = "double_value")
    private Double doubleValue;

    @Column(name = "date_value")
    private Instant dateValue;

    @Column(name = "json_value")
    private String jsonValue;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Configurations id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Configurations name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return this.group;
    }

    public Configurations group(String group) {
        this.setGroup(group);
        return this;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getIntValue() {
        return this.intValue;
    }

    public Configurations intValue(Integer intValue) {
        this.setIntValue(intValue);
        return this;
    }

    public void setIntValue(Integer intValue) {
        this.intValue = intValue;
    }

    public String getStringValue() {
        return this.stringValue;
    }

    public Configurations stringValue(String stringValue) {
        this.setStringValue(stringValue);
        return this;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Double getDoubleValue() {
        return this.doubleValue;
    }

    public Configurations doubleValue(Double doubleValue) {
        this.setDoubleValue(doubleValue);
        return this;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Instant getDateValue() {
        return this.dateValue;
    }

    public Configurations dateValue(Instant dateValue) {
        this.setDateValue(dateValue);
        return this;
    }

    public void setDateValue(Instant dateValue) {
        this.dateValue = dateValue;
    }

    public String getJsonValue() {
        return this.jsonValue;
    }

    public Configurations jsonValue(String jsonValue) {
        this.setJsonValue(jsonValue);
        return this;
    }

    public void setJsonValue(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Configurations createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Configurations updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public Configurations deletedAt(Instant deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public Configurations version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Configurations)) {
            return false;
        }
        return id != null && id.equals(((Configurations) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Configurations{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", group='" + getGroup() + "'" +
            ", intValue=" + getIntValue() +
            ", stringValue='" + getStringValue() + "'" +
            ", doubleValue=" + getDoubleValue() +
            ", dateValue='" + getDateValue() + "'" +
            ", jsonValue='" + getJsonValue() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
