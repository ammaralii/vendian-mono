package com.venturedive.vendian_mono.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ZLeaveRequests.
 */
@Entity
@Table(name = "z_leave_requests")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ZLeaveRequests implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "action", length = 255)
    private String action;

    @Size(max = 255)
    @Column(name = "userid", length = 255)
    private String userid;

    @Size(max = 255)
    @Column(name = "managerid", length = 255)
    private String managerid;

    @Size(max = 65535)
    @Column(name = "requestparams", length = 65535)
    private String requestparams;

    @Size(max = 65535)
    @Column(name = "responseparams", length = 65535)
    private String responseparams;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ZLeaveRequests id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return this.action;
    }

    public ZLeaveRequests action(String action) {
        this.setAction(action);
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUserid() {
        return this.userid;
    }

    public ZLeaveRequests userid(String userid) {
        this.setUserid(userid);
        return this;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getManagerid() {
        return this.managerid;
    }

    public ZLeaveRequests managerid(String managerid) {
        this.setManagerid(managerid);
        return this;
    }

    public void setManagerid(String managerid) {
        this.managerid = managerid;
    }

    public String getRequestparams() {
        return this.requestparams;
    }

    public ZLeaveRequests requestparams(String requestparams) {
        this.setRequestparams(requestparams);
        return this;
    }

    public void setRequestparams(String requestparams) {
        this.requestparams = requestparams;
    }

    public String getResponseparams() {
        return this.responseparams;
    }

    public ZLeaveRequests responseparams(String responseparams) {
        this.setResponseparams(responseparams);
        return this;
    }

    public void setResponseparams(String responseparams) {
        this.responseparams = responseparams;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public ZLeaveRequests createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public ZLeaveRequests updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ZLeaveRequests)) {
            return false;
        }
        return id != null && id.equals(((ZLeaveRequests) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ZLeaveRequests{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", userid='" + getUserid() + "'" +
            ", managerid='" + getManagerid() + "'" +
            ", requestparams='" + getRequestparams() + "'" +
            ", responseparams='" + getResponseparams() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
