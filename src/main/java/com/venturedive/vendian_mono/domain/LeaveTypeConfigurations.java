package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeaveTypeConfigurations.
 */
@Entity
@Table(name = "leave_type_configurations")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveTypeConfigurations implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "no_of_leaves", precision = 21, scale = 2, nullable = false)
    private BigDecimal noOfLeaves;

    @NotNull
    @Size(max = 5)
    @Column(name = "tenure_cycle", length = 5, nullable = false)
    private String tenureCycle;

    @Column(name = "jhi_to")
    private Integer to;

    @Column(name = "jhi_from")
    private Integer from;

    @Size(max = 14)
    @Column(name = "inclusivity", length = 14)
    private String inclusivity;

    @Column(name = "max_quota")
    private Integer maxQuota;

    @Column(name = "is_accured")
    private Boolean isAccured;

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

    public LeaveTypeConfigurations id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getNoOfLeaves() {
        return this.noOfLeaves;
    }

    public LeaveTypeConfigurations noOfLeaves(BigDecimal noOfLeaves) {
        this.setNoOfLeaves(noOfLeaves);
        return this;
    }

    public void setNoOfLeaves(BigDecimal noOfLeaves) {
        this.noOfLeaves = noOfLeaves;
    }

    public String getTenureCycle() {
        return this.tenureCycle;
    }

    public LeaveTypeConfigurations tenureCycle(String tenureCycle) {
        this.setTenureCycle(tenureCycle);
        return this;
    }

    public void setTenureCycle(String tenureCycle) {
        this.tenureCycle = tenureCycle;
    }

    public Integer getTo() {
        return this.to;
    }

    public LeaveTypeConfigurations to(Integer to) {
        this.setTo(to);
        return this;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public Integer getFrom() {
        return this.from;
    }

    public LeaveTypeConfigurations from(Integer from) {
        this.setFrom(from);
        return this;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public String getInclusivity() {
        return this.inclusivity;
    }

    public LeaveTypeConfigurations inclusivity(String inclusivity) {
        this.setInclusivity(inclusivity);
        return this;
    }

    public void setInclusivity(String inclusivity) {
        this.inclusivity = inclusivity;
    }

    public Integer getMaxQuota() {
        return this.maxQuota;
    }

    public LeaveTypeConfigurations maxQuota(Integer maxQuota) {
        this.setMaxQuota(maxQuota);
        return this;
    }

    public void setMaxQuota(Integer maxQuota) {
        this.maxQuota = maxQuota;
    }

    public Boolean getIsAccured() {
        return this.isAccured;
    }

    public LeaveTypeConfigurations isAccured(Boolean isAccured) {
        this.setIsAccured(isAccured);
        return this;
    }

    public void setIsAccured(Boolean isAccured) {
        this.isAccured = isAccured;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public LeaveTypeConfigurations effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public LeaveTypeConfigurations createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public LeaveTypeConfigurations updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public LeaveTypeConfigurations endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public LeaveTypeConfigurations version(Integer version) {
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

    public LeaveTypeConfigurations leaveType(LeaveTypes leaveTypes) {
        this.setLeaveType(leaveTypes);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveTypeConfigurations)) {
            return false;
        }
        return id != null && id.equals(((LeaveTypeConfigurations) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveTypeConfigurations{" +
            "id=" + getId() +
            ", noOfLeaves=" + getNoOfLeaves() +
            ", tenureCycle='" + getTenureCycle() + "'" +
            ", to=" + getTo() +
            ", from=" + getFrom() +
            ", inclusivity='" + getInclusivity() + "'" +
            ", maxQuota=" + getMaxQuota() +
            ", isAccured='" + getIsAccured() + "'" +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
