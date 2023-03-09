package com.venturedive.vendian_mono.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Configs.
 */
@Entity
@Table(name = "configs")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Configs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "name", length = 255)
    private String name;

    @Size(max = 255)
    @Column(name = "jhi_group", length = 255)
    private String group;

    @Column(name = "intvalue")
    private Integer intvalue;

    @Size(max = 255)
    @Column(name = "stringvalue", length = 255)
    private String stringvalue;

    @Column(name = "decimalvalue", precision = 21, scale = 2)
    private BigDecimal decimalvalue;

    @Column(name = "jsonvalue")
    private String jsonvalue;

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

    public Configs id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Configs name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return this.group;
    }

    public Configs group(String group) {
        this.setGroup(group);
        return this;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getIntvalue() {
        return this.intvalue;
    }

    public Configs intvalue(Integer intvalue) {
        this.setIntvalue(intvalue);
        return this;
    }

    public void setIntvalue(Integer intvalue) {
        this.intvalue = intvalue;
    }

    public String getStringvalue() {
        return this.stringvalue;
    }

    public Configs stringvalue(String stringvalue) {
        this.setStringvalue(stringvalue);
        return this;
    }

    public void setStringvalue(String stringvalue) {
        this.stringvalue = stringvalue;
    }

    public BigDecimal getDecimalvalue() {
        return this.decimalvalue;
    }

    public Configs decimalvalue(BigDecimal decimalvalue) {
        this.setDecimalvalue(decimalvalue);
        return this;
    }

    public void setDecimalvalue(BigDecimal decimalvalue) {
        this.decimalvalue = decimalvalue;
    }

    public String getJsonvalue() {
        return this.jsonvalue;
    }

    public Configs jsonvalue(String jsonvalue) {
        this.setJsonvalue(jsonvalue);
        return this;
    }

    public void setJsonvalue(String jsonvalue) {
        this.jsonvalue = jsonvalue;
    }

    public Instant getCreatedat() {
        return this.createdat;
    }

    public Configs createdat(Instant createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(Instant createdat) {
        this.createdat = createdat;
    }

    public Instant getUpdatedat() {
        return this.updatedat;
    }

    public Configs updatedat(Instant updatedat) {
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
        if (!(o instanceof Configs)) {
            return false;
        }
        return id != null && id.equals(((Configs) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Configs{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", group='" + getGroup() + "'" +
            ", intvalue=" + getIntvalue() +
            ", stringvalue='" + getStringvalue() + "'" +
            ", decimalvalue=" + getDecimalvalue() +
            ", jsonvalue='" + getJsonvalue() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            "}";
    }
}
