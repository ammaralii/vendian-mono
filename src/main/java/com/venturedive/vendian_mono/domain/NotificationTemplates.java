package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A NotificationTemplates.
 */
@Entity
@Table(name = "notification_templates")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificationTemplates implements Serializable {

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
    @Size(max = 5)
    @Column(name = "type", length = 5, nullable = false)
    private String type;

    @Size(max = 255)
    @Column(name = "subject", length = 255)
    private String subject;

    @Lob
    @Column(name = "template", nullable = false)
    private byte[] template;

    @NotNull
    @Column(name = "template_content_type", nullable = false)
    private String templateContentType;

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
        value = { "notificationmergefieldsNotificationevents", "notificationtemplatesNotificationevents" },
        allowSetters = true
    )
    private NotificationEvents notificationEvent;

    @OneToMany(mappedBy = "notificationTemplate")
    @JsonIgnoreProperties(value = { "notificationTemplate", "recipient" }, allowSetters = true)
    private Set<NotificationSentEmailLogs> notificationsentemaillogsNotificationtemplates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NotificationTemplates id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public NotificationTemplates name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public NotificationTemplates type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return this.subject;
    }

    public NotificationTemplates subject(String subject) {
        this.setSubject(subject);
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public byte[] getTemplate() {
        return this.template;
    }

    public NotificationTemplates template(byte[] template) {
        this.setTemplate(template);
        return this;
    }

    public void setTemplate(byte[] template) {
        this.template = template;
    }

    public String getTemplateContentType() {
        return this.templateContentType;
    }

    public NotificationTemplates templateContentType(String templateContentType) {
        this.templateContentType = templateContentType;
        return this;
    }

    public void setTemplateContentType(String templateContentType) {
        this.templateContentType = templateContentType;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public NotificationTemplates effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public NotificationTemplates createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public NotificationTemplates updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public NotificationTemplates endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public NotificationTemplates version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public NotificationEvents getNotificationEvent() {
        return this.notificationEvent;
    }

    public void setNotificationEvent(NotificationEvents notificationEvents) {
        this.notificationEvent = notificationEvents;
    }

    public NotificationTemplates notificationEvent(NotificationEvents notificationEvents) {
        this.setNotificationEvent(notificationEvents);
        return this;
    }

    public Set<NotificationSentEmailLogs> getNotificationsentemaillogsNotificationtemplates() {
        return this.notificationsentemaillogsNotificationtemplates;
    }

    public void setNotificationsentemaillogsNotificationtemplates(Set<NotificationSentEmailLogs> notificationSentEmailLogs) {
        if (this.notificationsentemaillogsNotificationtemplates != null) {
            this.notificationsentemaillogsNotificationtemplates.forEach(i -> i.setNotificationTemplate(null));
        }
        if (notificationSentEmailLogs != null) {
            notificationSentEmailLogs.forEach(i -> i.setNotificationTemplate(this));
        }
        this.notificationsentemaillogsNotificationtemplates = notificationSentEmailLogs;
    }

    public NotificationTemplates notificationsentemaillogsNotificationtemplates(Set<NotificationSentEmailLogs> notificationSentEmailLogs) {
        this.setNotificationsentemaillogsNotificationtemplates(notificationSentEmailLogs);
        return this;
    }

    public NotificationTemplates addNotificationsentemaillogsNotificationtemplate(NotificationSentEmailLogs notificationSentEmailLogs) {
        this.notificationsentemaillogsNotificationtemplates.add(notificationSentEmailLogs);
        notificationSentEmailLogs.setNotificationTemplate(this);
        return this;
    }

    public NotificationTemplates removeNotificationsentemaillogsNotificationtemplate(NotificationSentEmailLogs notificationSentEmailLogs) {
        this.notificationsentemaillogsNotificationtemplates.remove(notificationSentEmailLogs);
        notificationSentEmailLogs.setNotificationTemplate(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationTemplates)) {
            return false;
        }
        return id != null && id.equals(((NotificationTemplates) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationTemplates{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", subject='" + getSubject() + "'" +
            ", template='" + getTemplate() + "'" +
            ", templateContentType='" + getTemplateContentType() + "'" +
            ", effDate='" + getEffDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
