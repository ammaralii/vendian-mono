package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A NotificationEvents.
 */
@Entity
@Table(name = "notification_events")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificationEvents implements Serializable {

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

    @OneToMany(mappedBy = "notificationEvent")
    @JsonIgnoreProperties(value = { "notificationEvent" }, allowSetters = true)
    private Set<NotificationMergeFields> notificationmergefieldsNotificationevents = new HashSet<>();

    @OneToMany(mappedBy = "notificationEvent")
    @JsonIgnoreProperties(value = { "notificationEvent", "notificationsentemaillogsNotificationtemplates" }, allowSetters = true)
    private Set<NotificationTemplates> notificationtemplatesNotificationevents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NotificationEvents id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public NotificationEvents name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public NotificationEvents effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public NotificationEvents createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public NotificationEvents updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public NotificationEvents endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public NotificationEvents version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<NotificationMergeFields> getNotificationmergefieldsNotificationevents() {
        return this.notificationmergefieldsNotificationevents;
    }

    public void setNotificationmergefieldsNotificationevents(Set<NotificationMergeFields> notificationMergeFields) {
        if (this.notificationmergefieldsNotificationevents != null) {
            this.notificationmergefieldsNotificationevents.forEach(i -> i.setNotificationEvent(null));
        }
        if (notificationMergeFields != null) {
            notificationMergeFields.forEach(i -> i.setNotificationEvent(this));
        }
        this.notificationmergefieldsNotificationevents = notificationMergeFields;
    }

    public NotificationEvents notificationmergefieldsNotificationevents(Set<NotificationMergeFields> notificationMergeFields) {
        this.setNotificationmergefieldsNotificationevents(notificationMergeFields);
        return this;
    }

    public NotificationEvents addNotificationmergefieldsNotificationevent(NotificationMergeFields notificationMergeFields) {
        this.notificationmergefieldsNotificationevents.add(notificationMergeFields);
        notificationMergeFields.setNotificationEvent(this);
        return this;
    }

    public NotificationEvents removeNotificationmergefieldsNotificationevent(NotificationMergeFields notificationMergeFields) {
        this.notificationmergefieldsNotificationevents.remove(notificationMergeFields);
        notificationMergeFields.setNotificationEvent(null);
        return this;
    }

    public Set<NotificationTemplates> getNotificationtemplatesNotificationevents() {
        return this.notificationtemplatesNotificationevents;
    }

    public void setNotificationtemplatesNotificationevents(Set<NotificationTemplates> notificationTemplates) {
        if (this.notificationtemplatesNotificationevents != null) {
            this.notificationtemplatesNotificationevents.forEach(i -> i.setNotificationEvent(null));
        }
        if (notificationTemplates != null) {
            notificationTemplates.forEach(i -> i.setNotificationEvent(this));
        }
        this.notificationtemplatesNotificationevents = notificationTemplates;
    }

    public NotificationEvents notificationtemplatesNotificationevents(Set<NotificationTemplates> notificationTemplates) {
        this.setNotificationtemplatesNotificationevents(notificationTemplates);
        return this;
    }

    public NotificationEvents addNotificationtemplatesNotificationevent(NotificationTemplates notificationTemplates) {
        this.notificationtemplatesNotificationevents.add(notificationTemplates);
        notificationTemplates.setNotificationEvent(this);
        return this;
    }

    public NotificationEvents removeNotificationtemplatesNotificationevent(NotificationTemplates notificationTemplates) {
        this.notificationtemplatesNotificationevents.remove(notificationTemplates);
        notificationTemplates.setNotificationEvent(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationEvents)) {
            return false;
        }
        return id != null && id.equals(((NotificationEvents) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationEvents{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
