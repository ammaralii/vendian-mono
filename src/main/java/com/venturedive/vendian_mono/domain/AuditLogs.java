package com.venturedive.vendian_mono.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A AuditLogs.
 */
@Entity
@Table(name = "audit_logs")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AuditLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "event", length = 255, nullable = false)
    private String event;

    @NotNull
    @Column(name = "event_time", nullable = false)
    private Instant eventTime;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "old_change")
    private String oldChange;

    @Column(name = "new_change")
    private String newChange;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AuditLogs id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvent() {
        return this.event;
    }

    public AuditLogs event(String event) {
        this.setEvent(event);
        return this;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Instant getEventTime() {
        return this.eventTime;
    }

    public AuditLogs eventTime(Instant eventTime) {
        this.setEventTime(eventTime);
        return this;
    }

    public void setEventTime(Instant eventTime) {
        this.eventTime = eventTime;
    }

    public String getDescription() {
        return this.description;
    }

    public AuditLogs description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOldChange() {
        return this.oldChange;
    }

    public AuditLogs oldChange(String oldChange) {
        this.setOldChange(oldChange);
        return this;
    }

    public void setOldChange(String oldChange) {
        this.oldChange = oldChange;
    }

    public String getNewChange() {
        return this.newChange;
    }

    public AuditLogs newChange(String newChange) {
        this.setNewChange(newChange);
        return this;
    }

    public void setNewChange(String newChange) {
        this.newChange = newChange;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public AuditLogs createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public AuditLogs updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getVersion() {
        return this.version;
    }

    public AuditLogs version(Integer version) {
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
        if (!(o instanceof AuditLogs)) {
            return false;
        }
        return id != null && id.equals(((AuditLogs) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditLogs{" +
            "id=" + getId() +
            ", event='" + getEvent() + "'" +
            ", eventTime='" + getEventTime() + "'" +
            ", description='" + getDescription() + "'" +
            ", oldChange='" + getOldChange() + "'" +
            ", newChange='" + getNewChange() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
