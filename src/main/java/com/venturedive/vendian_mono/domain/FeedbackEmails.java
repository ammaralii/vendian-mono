package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A FeedbackEmails.
 */
@Entity
@Table(name = "feedback_emails")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FeedbackEmails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 65535)
    @Column(name = "jhi_to", length = 65535)
    private String to;

    @Size(max = 65535)
    @Column(name = "body", length = 65535)
    private String body;

    @Column(name = "status")
    private Integer status;

    @Column(name = "sentat")
    private Instant sentat;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "employee", "request", "feedbackemailsRespondents", "feedbackresponsesRespondents" },
        allowSetters = true
    )
    private FeedbackRespondents respondent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FeedbackEmails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTo() {
        return this.to;
    }

    public FeedbackEmails to(String to) {
        this.setTo(to);
        return this;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return this.body;
    }

    public FeedbackEmails body(String body) {
        this.setBody(body);
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getStatus() {
        return this.status;
    }

    public FeedbackEmails status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Instant getSentat() {
        return this.sentat;
    }

    public FeedbackEmails sentat(Instant sentat) {
        this.setSentat(sentat);
        return this;
    }

    public void setSentat(Instant sentat) {
        this.sentat = sentat;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public FeedbackEmails createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public FeedbackEmails updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public FeedbackRespondents getRespondent() {
        return this.respondent;
    }

    public void setRespondent(FeedbackRespondents feedbackRespondents) {
        this.respondent = feedbackRespondents;
    }

    public FeedbackEmails respondent(FeedbackRespondents feedbackRespondents) {
        this.setRespondent(feedbackRespondents);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeedbackEmails)) {
            return false;
        }
        return id != null && id.equals(((FeedbackEmails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedbackEmails{" +
            "id=" + getId() +
            ", to='" + getTo() + "'" +
            ", body='" + getBody() + "'" +
            ", status=" + getStatus() +
            ", sentat='" + getSentat() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
