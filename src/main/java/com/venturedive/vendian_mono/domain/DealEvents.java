package com.venturedive.vendian_mono.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DealEvents.
 */
@Entity
@Table(name = "deal_events")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DealEvents implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 7)
    @Column(name = "eventtype", length = 7, nullable = false)
    private String eventtype;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DealEvents id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventtype() {
        return this.eventtype;
    }

    public DealEvents eventtype(String eventtype) {
        this.setEventtype(eventtype);
        return this;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public DealEvents createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DealEvents)) {
            return false;
        }
        return id != null && id.equals(((DealEvents) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealEvents{" +
            "id=" + getId() +
            ", eventtype='" + getEventtype() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            "}";
    }
}
