package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A FeedbackResponses.
 */
@Entity
@Table(name = "feedback_responses")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FeedbackResponses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "answer")
    private byte[] answer;

    @Column(name = "answer_content_type")
    private String answerContentType;

    @Lob
    @Column(name = "rating")
    private byte[] rating;

    @Column(name = "rating_content_type")
    private String ratingContentType;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "employee", "feedbackresponsesQuestions" }, allowSetters = true)
    private FeedbackQuestions question;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FeedbackResponses id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getAnswer() {
        return this.answer;
    }

    public FeedbackResponses answer(byte[] answer) {
        this.setAnswer(answer);
        return this;
    }

    public void setAnswer(byte[] answer) {
        this.answer = answer;
    }

    public String getAnswerContentType() {
        return this.answerContentType;
    }

    public FeedbackResponses answerContentType(String answerContentType) {
        this.answerContentType = answerContentType;
        return this;
    }

    public void setAnswerContentType(String answerContentType) {
        this.answerContentType = answerContentType;
    }

    public byte[] getRating() {
        return this.rating;
    }

    public FeedbackResponses rating(byte[] rating) {
        this.setRating(rating);
        return this;
    }

    public void setRating(byte[] rating) {
        this.rating = rating;
    }

    public String getRatingContentType() {
        return this.ratingContentType;
    }

    public FeedbackResponses ratingContentType(String ratingContentType) {
        this.ratingContentType = ratingContentType;
        return this;
    }

    public void setRatingContentType(String ratingContentType) {
        this.ratingContentType = ratingContentType;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public FeedbackResponses createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public FeedbackResponses updatedat(Instant updatedat) {
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

    public FeedbackResponses respondent(FeedbackRespondents feedbackRespondents) {
        this.setRespondent(feedbackRespondents);
        return this;
    }

    public FeedbackQuestions getQuestion() {
        return this.question;
    }

    public void setQuestion(FeedbackQuestions feedbackQuestions) {
        this.question = feedbackQuestions;
    }

    public FeedbackResponses question(FeedbackQuestions feedbackQuestions) {
        this.setQuestion(feedbackQuestions);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeedbackResponses)) {
            return false;
        }
        return id != null && id.equals(((FeedbackResponses) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedbackResponses{" +
            "id=" + getId() +
            ", answer='" + getAnswer() + "'" +
            ", answerContentType='" + getAnswerContentType() + "'" +
            ", rating='" + getRating() + "'" +
            ", ratingContentType='" + getRatingContentType() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
