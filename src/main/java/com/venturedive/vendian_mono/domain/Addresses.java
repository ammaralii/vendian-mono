package com.venturedive.vendian_mono.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Addresses.
 */
@Entity
@Table(name = "addresses")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Addresses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @Size(max = 255)
    @Column(name = "city", length = 255)
    private String city;

    @Size(max = 255)
    @Column(name = "province", length = 255)
    private String province;

    @Size(max = 255)
    @Column(name = "country", length = 255)
    private String country;

    @Size(max = 255)
    @Column(name = "postalcode", length = 255)
    private String postalcode;

    @Column(name = "isdeleted")
    private Boolean isdeleted;

    @Column(name = "createdat")
    private Instant createdat;

    @Column(name = "updatedat")
    private Instant updatedat;

    @OneToMany(mappedBy = "address")
    @JsonIgnoreProperties(value = { "address", "employee" }, allowSetters = true)
    private Set<EmployeeAddresses> employeeaddressesAddresses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Addresses id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public Addresses address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public Addresses city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return this.province;
    }

    public Addresses province(String province) {
        this.setProvince(province);
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return this.country;
    }

    public Addresses country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalcode() {
        return this.postalcode;
    }

    public Addresses postalcode(String postalcode) {
        this.setPostalcode(postalcode);
        return this;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public Boolean getIsdeleted() {
        return this.isdeleted;
    }

    public Addresses isdeleted(Boolean isdeleted) {
        this.setIsdeleted(isdeleted);
        return this;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Addresses createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Addresses updatedat(Instant updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(Instant updatedat) {
        this.updatedat = updatedat;
    }

    public Set<EmployeeAddresses> getEmployeeaddressesAddresses() {
        return this.employeeaddressesAddresses;
    }

    public void setEmployeeaddressesAddresses(Set<EmployeeAddresses> employeeAddresses) {
        if (this.employeeaddressesAddresses != null) {
            this.employeeaddressesAddresses.forEach(i -> i.setAddress(null));
        }
        if (employeeAddresses != null) {
            employeeAddresses.forEach(i -> i.setAddress(this));
        }
        this.employeeaddressesAddresses = employeeAddresses;
    }

    public Addresses employeeaddressesAddresses(Set<EmployeeAddresses> employeeAddresses) {
        this.setEmployeeaddressesAddresses(employeeAddresses);
        return this;
    }

    public Addresses addEmployeeaddressesAddress(EmployeeAddresses employeeAddresses) {
        this.employeeaddressesAddresses.add(employeeAddresses);
        employeeAddresses.setAddress(this);
        return this;
    }

    public Addresses removeEmployeeaddressesAddress(EmployeeAddresses employeeAddresses) {
        this.employeeaddressesAddresses.remove(employeeAddresses);
        employeeAddresses.setAddress(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Addresses)) {
            return false;
        }
        return id != null && id.equals(((Addresses) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Addresses{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", province='" + getProvince() + "'" +
            ", country='" + getCountry() + "'" +
            ", postalcode='" + getPostalcode() + "'" +
            ", isdeleted='" + getIsdeleted() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
