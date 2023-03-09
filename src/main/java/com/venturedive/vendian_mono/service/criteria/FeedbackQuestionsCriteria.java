package com.venturedive.vendian_mono.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.venturedive.vendian_mono.domain.FeedbackQuestions} entity. This class is used
 * in {@link com.venturedive.vendian_mono.web.rest.FeedbackQuestionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /feedback-questions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FeedbackQuestionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter question;

    private BooleanFilter isdefault;

    private StringFilter area;

    private StringFilter competency;

    private IntegerFilter category;

    private BooleanFilter isskill;

    private IntegerFilter skilltype;

    private InstantFilter createdat;

    private InstantFilter updatedat;

    private LongFilter employeeId;

    private LongFilter feedbackresponsesQuestionId;

    private Boolean distinct;

    public FeedbackQuestionsCriteria() {}

    public FeedbackQuestionsCriteria(FeedbackQuestionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.question = other.question == null ? null : other.question.copy();
        this.isdefault = other.isdefault == null ? null : other.isdefault.copy();
        this.area = other.area == null ? null : other.area.copy();
        this.competency = other.competency == null ? null : other.competency.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.isskill = other.isskill == null ? null : other.isskill.copy();
        this.skilltype = other.skilltype == null ? null : other.skilltype.copy();
        this.createdat = other.createdat == null ? null : other.createdat.copy();
        this.updatedat = other.updatedat == null ? null : other.updatedat.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.feedbackresponsesQuestionId = other.feedbackresponsesQuestionId == null ? null : other.feedbackresponsesQuestionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FeedbackQuestionsCriteria copy() {
        return new FeedbackQuestionsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getQuestion() {
        return question;
    }

    public StringFilter question() {
        if (question == null) {
            question = new StringFilter();
        }
        return question;
    }

    public void setQuestion(StringFilter question) {
        this.question = question;
    }

    public BooleanFilter getIsdefault() {
        return isdefault;
    }

    public BooleanFilter isdefault() {
        if (isdefault == null) {
            isdefault = new BooleanFilter();
        }
        return isdefault;
    }

    public void setIsdefault(BooleanFilter isdefault) {
        this.isdefault = isdefault;
    }

    public StringFilter getArea() {
        return area;
    }

    public StringFilter area() {
        if (area == null) {
            area = new StringFilter();
        }
        return area;
    }

    public void setArea(StringFilter area) {
        this.area = area;
    }

    public StringFilter getCompetency() {
        return competency;
    }

    public StringFilter competency() {
        if (competency == null) {
            competency = new StringFilter();
        }
        return competency;
    }

    public void setCompetency(StringFilter competency) {
        this.competency = competency;
    }

    public IntegerFilter getCategory() {
        return category;
    }

    public IntegerFilter category() {
        if (category == null) {
            category = new IntegerFilter();
        }
        return category;
    }

    public void setCategory(IntegerFilter category) {
        this.category = category;
    }

    public BooleanFilter getIsskill() {
        return isskill;
    }

    public BooleanFilter isskill() {
        if (isskill == null) {
            isskill = new BooleanFilter();
        }
        return isskill;
    }

    public void setIsskill(BooleanFilter isskill) {
        this.isskill = isskill;
    }

    public IntegerFilter getSkilltype() {
        return skilltype;
    }

    public IntegerFilter skilltype() {
        if (skilltype == null) {
            skilltype = new IntegerFilter();
        }
        return skilltype;
    }

    public void setSkilltype(IntegerFilter skilltype) {
        this.skilltype = skilltype;
    }

    public InstantFilter getCreatedat() {
        return createdat;
    }

    public InstantFilter createdat() {
        if (createdat == null) {
            createdat = new InstantFilter();
        }
        return createdat;
    }

    public void setCreatedat(InstantFilter createdat) {
        this.createdat = createdat;
    }

    public InstantFilter getUpdatedat() {
        return updatedat;
    }

    public InstantFilter updatedat() {
        if (updatedat == null) {
            updatedat = new InstantFilter();
        }
        return updatedat;
    }

    public void setUpdatedat(InstantFilter updatedat) {
        this.updatedat = updatedat;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getFeedbackresponsesQuestionId() {
        return feedbackresponsesQuestionId;
    }

    public LongFilter feedbackresponsesQuestionId() {
        if (feedbackresponsesQuestionId == null) {
            feedbackresponsesQuestionId = new LongFilter();
        }
        return feedbackresponsesQuestionId;
    }

    public void setFeedbackresponsesQuestionId(LongFilter feedbackresponsesQuestionId) {
        this.feedbackresponsesQuestionId = feedbackresponsesQuestionId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FeedbackQuestionsCriteria that = (FeedbackQuestionsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(question, that.question) &&
            Objects.equals(isdefault, that.isdefault) &&
            Objects.equals(area, that.area) &&
            Objects.equals(competency, that.competency) &&
            Objects.equals(category, that.category) &&
            Objects.equals(isskill, that.isskill) &&
            Objects.equals(skilltype, that.skilltype) &&
            Objects.equals(createdat, that.createdat) &&
            Objects.equals(updatedat, that.updatedat) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(feedbackresponsesQuestionId, that.feedbackresponsesQuestionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            question,
            isdefault,
            area,
            competency,
            category,
            isskill,
            skilltype,
            createdat,
            updatedat,
            employeeId,
            feedbackresponsesQuestionId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedbackQuestionsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (question != null ? "question=" + question + ", " : "") +
            (isdefault != null ? "isdefault=" + isdefault + ", " : "") +
            (area != null ? "area=" + area + ", " : "") +
            (competency != null ? "competency=" + competency + ", " : "") +
            (category != null ? "category=" + category + ", " : "") +
            (isskill != null ? "isskill=" + isskill + ", " : "") +
            (skilltype != null ? "skilltype=" + skilltype + ", " : "") +
            (createdat != null ? "createdat=" + createdat + ", " : "") +
            (updatedat != null ? "updatedat=" + updatedat + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (feedbackresponsesQuestionId != null ? "feedbackresponsesQuestionId=" + feedbackresponsesQuestionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
