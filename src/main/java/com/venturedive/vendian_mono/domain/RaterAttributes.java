package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A RaterAttributes.
 */
@Entity
@Table(name = "rater_attributes")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RaterAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "name", length = 255)
    private String name;

    @Size(max = 255)
    @Column(name = "title", length = 255)
    private String title;

    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

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

    @OneToMany(mappedBy = "raterattribute")
    @JsonIgnoreProperties(
        value = { "raterattribute", "attributesFor", "attributesBy", "ratingattributesRaterattributemappings" },
        allowSetters = true
    )
    private Set<RaterAttributeMappings> raterattributemappingsRaterattributes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RaterAttributes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public RaterAttributes name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public RaterAttributes title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public RaterAttributes description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getEffdate() {
        return this.effdate;
    }

    public RaterAttributes effdate(Instant effdate) {
        this.setEffdate(effdate);
        return this;
    }

    public void setEffdate(Instant effdate) {
        this.effdate = effdate;
    }

    public Instant getEnddate() {
        return this.enddate;
    }

    public RaterAttributes enddate(Instant enddate) {
        this.setEnddate(enddate);
        return this;
    }

    public void setEnddate(Instant enddate) {
        this.enddate = enddate;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public RaterAttributes createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public RaterAttributes updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<RaterAttributeMappings> getRaterattributemappingsRaterattributes() {
        return this.raterattributemappingsRaterattributes;
    }

    public void setRaterattributemappingsRaterattributes(Set<RaterAttributeMappings> raterAttributeMappings) {
        if (this.raterattributemappingsRaterattributes != null) {
            this.raterattributemappingsRaterattributes.forEach(i -> i.setRaterattribute(null));
        }
        if (raterAttributeMappings != null) {
            raterAttributeMappings.forEach(i -> i.setRaterattribute(this));
        }
        this.raterattributemappingsRaterattributes = raterAttributeMappings;
    }

    public RaterAttributes raterattributemappingsRaterattributes(Set<RaterAttributeMappings> raterAttributeMappings) {
        this.setRaterattributemappingsRaterattributes(raterAttributeMappings);
        return this;
    }

    public RaterAttributes addRaterattributemappingsRaterattribute(RaterAttributeMappings raterAttributeMappings) {
        this.raterattributemappingsRaterattributes.add(raterAttributeMappings);
        raterAttributeMappings.setRaterattribute(this);
        return this;
    }

    public RaterAttributes removeRaterattributemappingsRaterattribute(RaterAttributeMappings raterAttributeMappings) {
        this.raterattributemappingsRaterattributes.remove(raterAttributeMappings);
        raterAttributeMappings.setRaterattribute(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RaterAttributes)) {
            return false;
        }
        return id != null && id.equals(((RaterAttributes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RaterAttributes{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", effdate='" + getEffdate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
