package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Permissions.
 */
@Entity
@Table(name = "permissions")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Permissions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "name", length = 255)
    private String name;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Size(max = 255)
    @Column(name = "group_name", length = 255)
    private String groupName;

    @Column(name = "isactive")
    private Boolean isactive;

    @NotNull
    @Column(name = "createdat", nullable = false)
    private Instant createdat;

    @NotNull
    @Column(name = "updatedat", nullable = false)
    private Instant updatedat;

    @OneToMany(mappedBy = "permission")
    @JsonIgnoreProperties(value = { "role", "permission" }, allowSetters = true)
    private Set<RolePermissions> rolepermissionsPermissions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Permissions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Permissions name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Permissions description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public Permissions groupName(String groupName) {
        this.setGroupName(groupName);
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Boolean getIsactive() {
        return this.isactive;
    }

    public Permissions isactive(Boolean isactive) {
        this.setIsactive(isactive);
        return this;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Permissions createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Permissions updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<RolePermissions> getRolepermissionsPermissions() {
        return this.rolepermissionsPermissions;
    }

    public void setRolepermissionsPermissions(Set<RolePermissions> rolePermissions) {
        if (this.rolepermissionsPermissions != null) {
            this.rolepermissionsPermissions.forEach(i -> i.setPermission(null));
        }
        if (rolePermissions != null) {
            rolePermissions.forEach(i -> i.setPermission(this));
        }
        this.rolepermissionsPermissions = rolePermissions;
    }

    public Permissions rolepermissionsPermissions(Set<RolePermissions> rolePermissions) {
        this.setRolepermissionsPermissions(rolePermissions);
        return this;
    }

    public Permissions addRolepermissionsPermission(RolePermissions rolePermissions) {
        this.rolepermissionsPermissions.add(rolePermissions);
        rolePermissions.setPermission(this);
        return this;
    }

    public Permissions removeRolepermissionsPermission(RolePermissions rolePermissions) {
        this.rolepermissionsPermissions.remove(rolePermissions);
        rolePermissions.setPermission(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Permissions)) {
            return false;
        }
        return id != null && id.equals(((Permissions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Permissions{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", groupName='" + getGroupName() + "'" +
            ", isactive='" + getIsactive() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
