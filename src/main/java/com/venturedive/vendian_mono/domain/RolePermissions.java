package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A RolePermissions.
 */
@Entity
@Table(name = "role_permissions")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RolePermissions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employeerolesRoles", "employeesRoles", "rolepermissionsRoles" }, allowSetters = true)
    private Roles role;

    @ManyToOne
    @JsonIgnoreProperties(value = { "rolepermissionsPermissions" }, allowSetters = true)
    private Permissions permission;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RolePermissions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public RolePermissions createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public RolePermissions updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Roles getRole() {
        return this.role;
    }

    public void setRole(Roles roles) {
        this.role = roles;
    }

    public RolePermissions role(Roles roles) {
        this.setRole(roles);
        return this;
    }

    public Permissions getPermission() {
        return this.permission;
    }

    public void setPermission(Permissions permissions) {
        this.permission = permissions;
    }

    public RolePermissions permission(Permissions permissions) {
        this.setPermission(permissions);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RolePermissions)) {
            return false;
        }
        return id != null && id.equals(((RolePermissions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RolePermissions{" +
            "id=" + getId() +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
