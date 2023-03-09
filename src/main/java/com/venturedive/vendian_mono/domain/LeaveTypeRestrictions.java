package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeaveTypeRestrictions.
 */
@Entity
@Table(name = "leave_type_restrictions")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveTypeRestrictions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "restriction_json", nullable = false)
    private String restrictionJson;

    @NotNull
    @Column(name = "eff_date", nullable = false)
    private Instant effDate;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "end_date")
    private Instant endDate;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "leavedetailsLeavetypes",
            "leavetypeapprovalrulesLeavetypes",
            "leavetypeconfigurationsLeavetypes",
            "leavetypeescalationrulesLeavetypes",
            "leavetyperestrictionsLeavetypes",
        },
        allowSetters = true
    )
    private LeaveTypes leaveType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeaveTypeRestrictions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestrictionJson() {
        return this.restrictionJson;
    }

    public LeaveTypeRestrictions restrictionJson(String restrictionJson) {
        this.setRestrictionJson(restrictionJson);
        return this;
    }

    public void setRestrictionJson(String restrictionJson) {
        this.restrictionJson = restrictionJson;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public LeaveTypeRestrictions effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public LeaveTypeRestrictions createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public LeaveTypeRestrictions updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public LeaveTypeRestrictions endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public LeaveTypeRestrictions version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public LeaveTypes getLeaveType() {
        return this.leaveType;
    }

    public void setLeaveType(LeaveTypes leaveTypes) {
        this.leaveType = leaveTypes;
    }

    public LeaveTypeRestrictions leaveType(LeaveTypes leaveTypes) {
        this.setLeaveType(leaveTypes);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveTypeRestrictions)) {
            return false;
        }
        return id != null && id.equals(((LeaveTypeRestrictions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveTypeRestrictions{" +
            "id=" + getId() +
            ", restrictionJson='" + getRestrictionJson() + "'" +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
