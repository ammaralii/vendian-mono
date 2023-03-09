package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LeaveTypesOlds.
 */
@Entity
@Table(name = "leave_types_olds")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveTypesOlds implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "isactive")
    private Boolean isactive;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @OneToMany(mappedBy = "leavetype")
    @JsonIgnoreProperties(value = { "leavetype", "manager", "employee" }, allowSetters = true)
    private Set<LeaveRequestsOlds> leaverequestsoldsLeavetypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeaveTypesOlds id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public LeaveTypesOlds name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsactive() {
        return this.isactive;
    }

    public LeaveTypesOlds isactive(Boolean isactive) {
        this.setIsactive(isactive);
        return this;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public LeaveTypesOlds createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public LeaveTypesOlds updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<LeaveRequestsOlds> getLeaverequestsoldsLeavetypes() {
        return this.leaverequestsoldsLeavetypes;
    }

    public void setLeaverequestsoldsLeavetypes(Set<LeaveRequestsOlds> leaveRequestsOlds) {
        if (this.leaverequestsoldsLeavetypes != null) {
            this.leaverequestsoldsLeavetypes.forEach(i -> i.setLeavetype(null));
        }
        if (leaveRequestsOlds != null) {
            leaveRequestsOlds.forEach(i -> i.setLeavetype(this));
        }
        this.leaverequestsoldsLeavetypes = leaveRequestsOlds;
    }

    public LeaveTypesOlds leaverequestsoldsLeavetypes(Set<LeaveRequestsOlds> leaveRequestsOlds) {
        this.setLeaverequestsoldsLeavetypes(leaveRequestsOlds);
        return this;
    }

    public LeaveTypesOlds addLeaverequestsoldsLeavetype(LeaveRequestsOlds leaveRequestsOlds) {
        this.leaverequestsoldsLeavetypes.add(leaveRequestsOlds);
        leaveRequestsOlds.setLeavetype(this);
        return this;
    }

    public LeaveTypesOlds removeLeaverequestsoldsLeavetype(LeaveRequestsOlds leaveRequestsOlds) {
        this.leaverequestsoldsLeavetypes.remove(leaveRequestsOlds);
        leaveRequestsOlds.setLeavetype(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveTypesOlds)) {
            return false;
        }
        return id != null && id.equals(((LeaveTypesOlds) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveTypesOlds{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isactive='" + getIsactive() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
