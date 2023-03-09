package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A RaterAttributeMappings.
 */
@Entity
@Table(name = "rater_attribute_mappings")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RaterAttributeMappings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "effdate")
    private Instant effdate;

    @Column(name = "enddate")
    private Instant enddate;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "raterattributemappingsRaterattributes" }, allowSetters = true)
    private RaterAttributes raterattribute;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "leaveapproversAttributes",
            "leaveescalationapproversAttributes",
            "raterattributemappingsAttributesfors",
            "raterattributemappingsAttributesbies",
            "ratingattributemappingsAttributes",
            "userattributeapprovalrulesAttributes",
            "userattributeescalationrulesAttributes",
            "userattributesAttributes",
            "userrelationapprovalrulesAttributes",
            "userrelationsAttributes",
        },
        allowSetters = true
    )
    private Attributes attributesFor;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "leaveapproversAttributes",
            "leaveescalationapproversAttributes",
            "raterattributemappingsAttributesfors",
            "raterattributemappingsAttributesbies",
            "ratingattributemappingsAttributes",
            "userattributeapprovalrulesAttributes",
            "userattributeescalationrulesAttributes",
            "userattributesAttributes",
            "userrelationapprovalrulesAttributes",
            "userrelationsAttributes",
        },
        allowSetters = true
    )
    private Attributes attributesBy;

    @OneToMany(mappedBy = "raterattributemapping")
    @JsonIgnoreProperties(value = { "rating", "raterattributemapping" }, allowSetters = true)
    private Set<RatingAttributes> ratingattributesRaterattributemappings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RaterAttributeMappings id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getEffdate() {
        return this.effdate;
    }

    public RaterAttributeMappings effdate(Instant effdate) {
        this.setEffdate(effdate);
        return this;
    }

    public void setEffdate(Instant effdate) {
        this.effdate = effdate;
    }

    public Instant getEnddate() {
        return this.enddate;
    }

    public RaterAttributeMappings enddate(Instant enddate) {
        this.setEnddate(enddate);
        return this;
    }

    public void setEnddate(Instant enddate) {
        this.enddate = enddate;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public RaterAttributeMappings createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public RaterAttributeMappings updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public RaterAttributes getRaterattribute() {
        return this.raterattribute;
    }

    public void setRaterattribute(RaterAttributes raterAttributes) {
        this.raterattribute = raterAttributes;
    }

    public RaterAttributeMappings raterattribute(RaterAttributes raterAttributes) {
        this.setRaterattribute(raterAttributes);
        return this;
    }

    public Attributes getAttributesFor() {
        return this.attributesFor;
    }

    public void setAttributesFor(Attributes attributes) {
        this.attributesFor = attributes;
    }

    public RaterAttributeMappings attributesFor(Attributes attributes) {
        this.setAttributesFor(attributes);
        return this;
    }

    public Attributes getAttributesBy() {
        return this.attributesBy;
    }

    public void setAttributesBy(Attributes attributes) {
        this.attributesBy = attributes;
    }

    public RaterAttributeMappings attributesBy(Attributes attributes) {
        this.setAttributesBy(attributes);
        return this;
    }

    public Set<RatingAttributes> getRatingattributesRaterattributemappings() {
        return this.ratingattributesRaterattributemappings;
    }

    public void setRatingattributesRaterattributemappings(Set<RatingAttributes> ratingAttributes) {
        if (this.ratingattributesRaterattributemappings != null) {
            this.ratingattributesRaterattributemappings.forEach(i -> i.setRaterattributemapping(null));
        }
        if (ratingAttributes != null) {
            ratingAttributes.forEach(i -> i.setRaterattributemapping(this));
        }
        this.ratingattributesRaterattributemappings = ratingAttributes;
    }

    public RaterAttributeMappings ratingattributesRaterattributemappings(Set<RatingAttributes> ratingAttributes) {
        this.setRatingattributesRaterattributemappings(ratingAttributes);
        return this;
    }

    public RaterAttributeMappings addRatingattributesRaterattributemapping(RatingAttributes ratingAttributes) {
        this.ratingattributesRaterattributemappings.add(ratingAttributes);
        ratingAttributes.setRaterattributemapping(this);
        return this;
    }

    public RaterAttributeMappings removeRatingattributesRaterattributemapping(RatingAttributes ratingAttributes) {
        this.ratingattributesRaterattributemappings.remove(ratingAttributes);
        ratingAttributes.setRaterattributemapping(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RaterAttributeMappings)) {
            return false;
        }
        return id != null && id.equals(((RaterAttributeMappings) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RaterAttributeMappings{" +
            "id=" + getId() +
            ", effdate='" + getEffdate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
