package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Events.
 */
@Entity
@Table(name = "events")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Events implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "type", length = 255)
    private String type;

    @OneToMany(mappedBy = "event")
    @JsonIgnoreProperties(value = { "event", "employee", "project", "employeeproject", "assignor", "projectmanager" }, allowSetters = true)
    private Set<ZEmployeeProjects> zemployeeprojectsEvents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Events id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public Events type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<ZEmployeeProjects> getZemployeeprojectsEvents() {
        return this.zemployeeprojectsEvents;
    }

    public void setZemployeeprojectsEvents(Set<ZEmployeeProjects> zEmployeeProjects) {
        if (this.zemployeeprojectsEvents != null) {
            this.zemployeeprojectsEvents.forEach(i -> i.setEvent(null));
        }
        if (zEmployeeProjects != null) {
            zEmployeeProjects.forEach(i -> i.setEvent(this));
        }
        this.zemployeeprojectsEvents = zEmployeeProjects;
    }

    public Events zemployeeprojectsEvents(Set<ZEmployeeProjects> zEmployeeProjects) {
        this.setZemployeeprojectsEvents(zEmployeeProjects);
        return this;
    }

    public Events addZemployeeprojectsEvent(ZEmployeeProjects zEmployeeProjects) {
        this.zemployeeprojectsEvents.add(zEmployeeProjects);
        zEmployeeProjects.setEvent(this);
        return this;
    }

    public Events removeZemployeeprojectsEvent(ZEmployeeProjects zEmployeeProjects) {
        this.zemployeeprojectsEvents.remove(zEmployeeProjects);
        zEmployeeProjects.setEvent(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Events)) {
            return false;
        }
        return id != null && id.equals(((Events) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Events{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
