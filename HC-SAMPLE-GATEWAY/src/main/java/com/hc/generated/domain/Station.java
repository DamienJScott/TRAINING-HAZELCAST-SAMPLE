package com.hc.generated.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Station.
 */
@Entity
@Table(name = "station")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "hc_desc")
    private String desc;

    @NotNull
    @Column(name = "postion", nullable = false)
    private String postion;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @OneToMany(mappedBy = "station")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Powerbank> powerbanks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Station name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public Station desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPostion() {
        return postion;
    }

    public Station postion(String postion) {
        this.postion = postion;
        return this;
    }

    public void setPostion(String postion) {
        this.postion = postion;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Station enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Powerbank> getPowerbanks() {
        return powerbanks;
    }

    public Station powerbanks(Set<Powerbank> powerbanks) {
        this.powerbanks = powerbanks;
        return this;
    }

    public Station addPowerbank(Powerbank powerbank) {
        this.powerbanks.add(powerbank);
        powerbank.setStation(this);
        return this;
    }

    public Station removePowerbank(Powerbank powerbank) {
        this.powerbanks.remove(powerbank);
        powerbank.setStation(null);
        return this;
    }

    public void setPowerbanks(Set<Powerbank> powerbanks) {
        this.powerbanks = powerbanks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Station)) {
            return false;
        }
        return id != null && id.equals(((Station) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Station{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", desc='" + getDesc() + "'" +
            ", postion='" + getPostion() + "'" +
            ", enabled='" + isEnabled() + "'" +
            "}";
    }
}
