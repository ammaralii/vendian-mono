package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Attributes.
 */
@Entity
@Table(name = "attributes")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Attributes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "name", length = 255)
    private String name;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "version")
    private Integer version;

    @Column(name = "eff_date")
    private Instant effDate;

    @OneToMany(mappedBy = "attribute")
    @JsonIgnoreProperties(value = { "leaveApprovalCriteria", "attribute" }, allowSetters = true)
    private Set<LeaveApprovers> leaveapproversAttributes = new HashSet<>();

    @OneToMany(mappedBy = "attribute")
    @JsonIgnoreProperties(value = { "leaveEscalationCriteria", "attribute" }, allowSetters = true)
    private Set<LeaveEscalationApprovers> leaveescalationapproversAttributes = new HashSet<>();

    @OneToMany(mappedBy = "attributesFor")
    @JsonIgnoreProperties(
        value = { "raterattribute", "attributesFor", "attributesBy", "ratingattributesRaterattributemappings" },
        allowSetters = true
    )
    private Set<RaterAttributeMappings> raterattributemappingsAttributesfors = new HashSet<>();

    @OneToMany(mappedBy = "attributesBy")
    @JsonIgnoreProperties(
        value = { "raterattribute", "attributesFor", "attributesBy", "ratingattributesRaterattributemappings" },
        allowSetters = true
    )
    private Set<RaterAttributeMappings> raterattributemappingsAttributesbies = new HashSet<>();

    @OneToMany(mappedBy = "attribute")
    @JsonIgnoreProperties(value = { "attribute", "ratingAttribute", "pcraterattributesRatingattributemappings" }, allowSetters = true)
    private Set<RatingAttributeMappings> ratingattributemappingsAttributes = new HashSet<>();

    @OneToMany(mappedBy = "attribute")
    @JsonIgnoreProperties(value = { "attribute", "leaveApprovalCriteria" }, allowSetters = true)
    private Set<UserAttributeApprovalRules> userattributeapprovalrulesAttributes = new HashSet<>();

    @OneToMany(mappedBy = "attribute")
    @JsonIgnoreProperties(value = { "attribute", "approverStatus", "leaveescalationcriteria" }, allowSetters = true)
    private Set<UserAttributeEscalationRules> userattributeescalationrulesAttributes = new HashSet<>();

    @OneToMany(mappedBy = "attribute")
    @JsonIgnoreProperties(value = { "attribute", "user" }, allowSetters = true)
    private Set<UserAttributes> userattributesAttributes = new HashSet<>();

    @OneToMany(mappedBy = "attribute")
    @JsonIgnoreProperties(value = { "attribute", "leaveApprovalCriteria" }, allowSetters = true)
    private Set<UserRelationApprovalRules> userrelationapprovalrulesAttributes = new HashSet<>();

    @OneToMany(mappedBy = "attribute")
    @JsonIgnoreProperties(value = { "user", "attribute", "relatedUser" }, allowSetters = true)
    private Set<UserRelations> userrelationsAttributes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Attributes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Attributes name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Attributes createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Attributes updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public Attributes endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public Attributes version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Instant getEffDate() {
        return this.effDate;
    }

    public Attributes effDate(Instant effDate) {
        this.setEffDate(effDate);
        return this;
    }

    public void setEffDate(Instant effDate) {
        this.effDate = effDate;
    }

    public Set<LeaveApprovers> getLeaveapproversAttributes() {
        return this.leaveapproversAttributes;
    }

    public void setLeaveapproversAttributes(Set<LeaveApprovers> leaveApprovers) {
        if (this.leaveapproversAttributes != null) {
            this.leaveapproversAttributes.forEach(i -> i.setAttribute(null));
        }
        if (leaveApprovers != null) {
            leaveApprovers.forEach(i -> i.setAttribute(this));
        }
        this.leaveapproversAttributes = leaveApprovers;
    }

    public Attributes leaveapproversAttributes(Set<LeaveApprovers> leaveApprovers) {
        this.setLeaveapproversAttributes(leaveApprovers);
        return this;
    }

    public Attributes addLeaveapproversAttribute(LeaveApprovers leaveApprovers) {
        this.leaveapproversAttributes.add(leaveApprovers);
        leaveApprovers.setAttribute(this);
        return this;
    }

    public Attributes removeLeaveapproversAttribute(LeaveApprovers leaveApprovers) {
        this.leaveapproversAttributes.remove(leaveApprovers);
        leaveApprovers.setAttribute(null);
        return this;
    }

    public Set<LeaveEscalationApprovers> getLeaveescalationapproversAttributes() {
        return this.leaveescalationapproversAttributes;
    }

    public void setLeaveescalationapproversAttributes(Set<LeaveEscalationApprovers> leaveEscalationApprovers) {
        if (this.leaveescalationapproversAttributes != null) {
            this.leaveescalationapproversAttributes.forEach(i -> i.setAttribute(null));
        }
        if (leaveEscalationApprovers != null) {
            leaveEscalationApprovers.forEach(i -> i.setAttribute(this));
        }
        this.leaveescalationapproversAttributes = leaveEscalationApprovers;
    }

    public Attributes leaveescalationapproversAttributes(Set<LeaveEscalationApprovers> leaveEscalationApprovers) {
        this.setLeaveescalationapproversAttributes(leaveEscalationApprovers);
        return this;
    }

    public Attributes addLeaveescalationapproversAttribute(LeaveEscalationApprovers leaveEscalationApprovers) {
        this.leaveescalationapproversAttributes.add(leaveEscalationApprovers);
        leaveEscalationApprovers.setAttribute(this);
        return this;
    }

    public Attributes removeLeaveescalationapproversAttribute(LeaveEscalationApprovers leaveEscalationApprovers) {
        this.leaveescalationapproversAttributes.remove(leaveEscalationApprovers);
        leaveEscalationApprovers.setAttribute(null);
        return this;
    }

    public Set<RaterAttributeMappings> getRaterattributemappingsAttributesfors() {
        return this.raterattributemappingsAttributesfors;
    }

    public void setRaterattributemappingsAttributesfors(Set<RaterAttributeMappings> raterAttributeMappings) {
        if (this.raterattributemappingsAttributesfors != null) {
            this.raterattributemappingsAttributesfors.forEach(i -> i.setAttributesFor(null));
        }
        if (raterAttributeMappings != null) {
            raterAttributeMappings.forEach(i -> i.setAttributesFor(this));
        }
        this.raterattributemappingsAttributesfors = raterAttributeMappings;
    }

    public Attributes raterattributemappingsAttributesfors(Set<RaterAttributeMappings> raterAttributeMappings) {
        this.setRaterattributemappingsAttributesfors(raterAttributeMappings);
        return this;
    }

    public Attributes addRaterattributemappingsAttributesfor(RaterAttributeMappings raterAttributeMappings) {
        this.raterattributemappingsAttributesfors.add(raterAttributeMappings);
        raterAttributeMappings.setAttributesFor(this);
        return this;
    }

    public Attributes removeRaterattributemappingsAttributesfor(RaterAttributeMappings raterAttributeMappings) {
        this.raterattributemappingsAttributesfors.remove(raterAttributeMappings);
        raterAttributeMappings.setAttributesFor(null);
        return this;
    }

    public Set<RaterAttributeMappings> getRaterattributemappingsAttributesbies() {
        return this.raterattributemappingsAttributesbies;
    }

    public void setRaterattributemappingsAttributesbies(Set<RaterAttributeMappings> raterAttributeMappings) {
        if (this.raterattributemappingsAttributesbies != null) {
            this.raterattributemappingsAttributesbies.forEach(i -> i.setAttributesBy(null));
        }
        if (raterAttributeMappings != null) {
            raterAttributeMappings.forEach(i -> i.setAttributesBy(this));
        }
        this.raterattributemappingsAttributesbies = raterAttributeMappings;
    }

    public Attributes raterattributemappingsAttributesbies(Set<RaterAttributeMappings> raterAttributeMappings) {
        this.setRaterattributemappingsAttributesbies(raterAttributeMappings);
        return this;
    }

    public Attributes addRaterattributemappingsAttributesby(RaterAttributeMappings raterAttributeMappings) {
        this.raterattributemappingsAttributesbies.add(raterAttributeMappings);
        raterAttributeMappings.setAttributesBy(this);
        return this;
    }

    public Attributes removeRaterattributemappingsAttributesby(RaterAttributeMappings raterAttributeMappings) {
        this.raterattributemappingsAttributesbies.remove(raterAttributeMappings);
        raterAttributeMappings.setAttributesBy(null);
        return this;
    }

    public Set<RatingAttributeMappings> getRatingattributemappingsAttributes() {
        return this.ratingattributemappingsAttributes;
    }

    public void setRatingattributemappingsAttributes(Set<RatingAttributeMappings> ratingAttributeMappings) {
        if (this.ratingattributemappingsAttributes != null) {
            this.ratingattributemappingsAttributes.forEach(i -> i.setAttribute(null));
        }
        if (ratingAttributeMappings != null) {
            ratingAttributeMappings.forEach(i -> i.setAttribute(this));
        }
        this.ratingattributemappingsAttributes = ratingAttributeMappings;
    }

    public Attributes ratingattributemappingsAttributes(Set<RatingAttributeMappings> ratingAttributeMappings) {
        this.setRatingattributemappingsAttributes(ratingAttributeMappings);
        return this;
    }

    public Attributes addRatingattributemappingsAttribute(RatingAttributeMappings ratingAttributeMappings) {
        this.ratingattributemappingsAttributes.add(ratingAttributeMappings);
        ratingAttributeMappings.setAttribute(this);
        return this;
    }

    public Attributes removeRatingattributemappingsAttribute(RatingAttributeMappings ratingAttributeMappings) {
        this.ratingattributemappingsAttributes.remove(ratingAttributeMappings);
        ratingAttributeMappings.setAttribute(null);
        return this;
    }

    public Set<UserAttributeApprovalRules> getUserattributeapprovalrulesAttributes() {
        return this.userattributeapprovalrulesAttributes;
    }

    public void setUserattributeapprovalrulesAttributes(Set<UserAttributeApprovalRules> userAttributeApprovalRules) {
        if (this.userattributeapprovalrulesAttributes != null) {
            this.userattributeapprovalrulesAttributes.forEach(i -> i.setAttribute(null));
        }
        if (userAttributeApprovalRules != null) {
            userAttributeApprovalRules.forEach(i -> i.setAttribute(this));
        }
        this.userattributeapprovalrulesAttributes = userAttributeApprovalRules;
    }

    public Attributes userattributeapprovalrulesAttributes(Set<UserAttributeApprovalRules> userAttributeApprovalRules) {
        this.setUserattributeapprovalrulesAttributes(userAttributeApprovalRules);
        return this;
    }

    public Attributes addUserattributeapprovalrulesAttribute(UserAttributeApprovalRules userAttributeApprovalRules) {
        this.userattributeapprovalrulesAttributes.add(userAttributeApprovalRules);
        userAttributeApprovalRules.setAttribute(this);
        return this;
    }

    public Attributes removeUserattributeapprovalrulesAttribute(UserAttributeApprovalRules userAttributeApprovalRules) {
        this.userattributeapprovalrulesAttributes.remove(userAttributeApprovalRules);
        userAttributeApprovalRules.setAttribute(null);
        return this;
    }

    public Set<UserAttributeEscalationRules> getUserattributeescalationrulesAttributes() {
        return this.userattributeescalationrulesAttributes;
    }

    public void setUserattributeescalationrulesAttributes(Set<UserAttributeEscalationRules> userAttributeEscalationRules) {
        if (this.userattributeescalationrulesAttributes != null) {
            this.userattributeescalationrulesAttributes.forEach(i -> i.setAttribute(null));
        }
        if (userAttributeEscalationRules != null) {
            userAttributeEscalationRules.forEach(i -> i.setAttribute(this));
        }
        this.userattributeescalationrulesAttributes = userAttributeEscalationRules;
    }

    public Attributes userattributeescalationrulesAttributes(Set<UserAttributeEscalationRules> userAttributeEscalationRules) {
        this.setUserattributeescalationrulesAttributes(userAttributeEscalationRules);
        return this;
    }

    public Attributes addUserattributeescalationrulesAttribute(UserAttributeEscalationRules userAttributeEscalationRules) {
        this.userattributeescalationrulesAttributes.add(userAttributeEscalationRules);
        userAttributeEscalationRules.setAttribute(this);
        return this;
    }

    public Attributes removeUserattributeescalationrulesAttribute(UserAttributeEscalationRules userAttributeEscalationRules) {
        this.userattributeescalationrulesAttributes.remove(userAttributeEscalationRules);
        userAttributeEscalationRules.setAttribute(null);
        return this;
    }

    public Set<UserAttributes> getUserattributesAttributes() {
        return this.userattributesAttributes;
    }

    public void setUserattributesAttributes(Set<UserAttributes> userAttributes) {
        if (this.userattributesAttributes != null) {
            this.userattributesAttributes.forEach(i -> i.setAttribute(null));
        }
        if (userAttributes != null) {
            userAttributes.forEach(i -> i.setAttribute(this));
        }
        this.userattributesAttributes = userAttributes;
    }

    public Attributes userattributesAttributes(Set<UserAttributes> userAttributes) {
        this.setUserattributesAttributes(userAttributes);
        return this;
    }

    public Attributes addUserattributesAttribute(UserAttributes userAttributes) {
        this.userattributesAttributes.add(userAttributes);
        userAttributes.setAttribute(this);
        return this;
    }

    public Attributes removeUserattributesAttribute(UserAttributes userAttributes) {
        this.userattributesAttributes.remove(userAttributes);
        userAttributes.setAttribute(null);
        return this;
    }

    public Set<UserRelationApprovalRules> getUserrelationapprovalrulesAttributes() {
        return this.userrelationapprovalrulesAttributes;
    }

    public void setUserrelationapprovalrulesAttributes(Set<UserRelationApprovalRules> userRelationApprovalRules) {
        if (this.userrelationapprovalrulesAttributes != null) {
            this.userrelationapprovalrulesAttributes.forEach(i -> i.setAttribute(null));
        }
        if (userRelationApprovalRules != null) {
            userRelationApprovalRules.forEach(i -> i.setAttribute(this));
        }
        this.userrelationapprovalrulesAttributes = userRelationApprovalRules;
    }

    public Attributes userrelationapprovalrulesAttributes(Set<UserRelationApprovalRules> userRelationApprovalRules) {
        this.setUserrelationapprovalrulesAttributes(userRelationApprovalRules);
        return this;
    }

    public Attributes addUserrelationapprovalrulesAttribute(UserRelationApprovalRules userRelationApprovalRules) {
        this.userrelationapprovalrulesAttributes.add(userRelationApprovalRules);
        userRelationApprovalRules.setAttribute(this);
        return this;
    }

    public Attributes removeUserrelationapprovalrulesAttribute(UserRelationApprovalRules userRelationApprovalRules) {
        this.userrelationapprovalrulesAttributes.remove(userRelationApprovalRules);
        userRelationApprovalRules.setAttribute(null);
        return this;
    }

    public Set<UserRelations> getUserrelationsAttributes() {
        return this.userrelationsAttributes;
    }

    public void setUserrelationsAttributes(Set<UserRelations> userRelations) {
        if (this.userrelationsAttributes != null) {
            this.userrelationsAttributes.forEach(i -> i.setAttribute(null));
        }
        if (userRelations != null) {
            userRelations.forEach(i -> i.setAttribute(this));
        }
        this.userrelationsAttributes = userRelations;
    }

    public Attributes userrelationsAttributes(Set<UserRelations> userRelations) {
        this.setUserrelationsAttributes(userRelations);
        return this;
    }

    public Attributes addUserrelationsAttribute(UserRelations userRelations) {
        this.userrelationsAttributes.add(userRelations);
        userRelations.setAttribute(this);
        return this;
    }

    public Attributes removeUserrelationsAttribute(UserRelations userRelations) {
        this.userrelationsAttributes.remove(userRelations);
        userRelations.setAttribute(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attributes)) {
            return false;
        }
        return id != null && id.equals(((Attributes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attributes{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", version=" + getVersion() +
            ", effDate='" + getEffDate() + "'" +
            "}";
    }
}
