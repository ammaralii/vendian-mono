package com.venturedive.vendian_mono.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A AttributePermissions.
 */
@Entity
@Table(name = "attribute_permissions")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttributePermissions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "method", length = 255, nullable = false)
    private String method;

    @NotNull
    @Size(max = 255)
    @Column(name = "route", length = 255, nullable = false)
    private String route;

    @Column(name = "responsepermissions")
    private String responsepermissions;

    @Column(name = "requestpermissions")
    private String requestpermissions;

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

    public AttributePermissions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethod() {
        return this.method;
    }

    public AttributePermissions method(String method) {
        this.setMethod(method);
        return this;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRoute() {
        return this.route;
    }

    public AttributePermissions route(String route) {
        this.setRoute(route);
        return this;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getResponsepermissions() {
        return this.responsepermissions;
    }

    public AttributePermissions responsepermissions(String responsepermissions) {
        this.setResponsepermissions(responsepermissions);
        return this;
    }

    public void setResponsepermissions(String responsepermissions) {
        this.responsepermissions = responsepermissions;
    }

    public String getRequestpermissions() {
        return this.requestpermissions;
    }

    public AttributePermissions requestpermissions(String requestpermissions) {
        this.setRequestpermissions(requestpermissions);
        return this;
    }

    public void setRequestpermissions(String requestpermissions) {
        this.requestpermissions = requestpermissions;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public AttributePermissions createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public AttributePermissions updatedat(Instant updatedat) {
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
        if (!(o instanceof AttributePermissions)) {
            return false;
        }
        return id != null && id.equals(((AttributePermissions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttributePermissions{" +
            "id=" + getId() +
            ", method='" + getMethod() + "'" +
            ", route='" + getRoute() + "'" +
            ", responsepermissions='" + getResponsepermissions() + "'" +
            ", requestpermissions='" + getRequestpermissions() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
