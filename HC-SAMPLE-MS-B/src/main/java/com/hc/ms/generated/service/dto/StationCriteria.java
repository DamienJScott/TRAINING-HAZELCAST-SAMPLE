package com.hc.ms.generated.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.hc.ms.generated.domain.Station} entity. This class is used
 * in {@link com.hc.ms.generated.web.rest.StationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /stations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter desc;

    private StringFilter postion;

    private BooleanFilter enabled;

    private LongFilter powerbankId;

    public StationCriteria() {
    }

    public StationCriteria(StationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.desc = other.desc == null ? null : other.desc.copy();
        this.postion = other.postion == null ? null : other.postion.copy();
        this.enabled = other.enabled == null ? null : other.enabled.copy();
        this.powerbankId = other.powerbankId == null ? null : other.powerbankId.copy();
    }

    @Override
    public StationCriteria copy() {
        return new StationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDesc() {
        return desc;
    }

    public void setDesc(StringFilter desc) {
        this.desc = desc;
    }

    public StringFilter getPostion() {
        return postion;
    }

    public void setPostion(StringFilter postion) {
        this.postion = postion;
    }

    public BooleanFilter getEnabled() {
        return enabled;
    }

    public void setEnabled(BooleanFilter enabled) {
        this.enabled = enabled;
    }

    public LongFilter getPowerbankId() {
        return powerbankId;
    }

    public void setPowerbankId(LongFilter powerbankId) {
        this.powerbankId = powerbankId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StationCriteria that = (StationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(desc, that.desc) &&
            Objects.equals(postion, that.postion) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(powerbankId, that.powerbankId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        desc,
        postion,
        enabled,
        powerbankId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (desc != null ? "desc=" + desc + ", " : "") +
                (postion != null ? "postion=" + postion + ", " : "") +
                (enabled != null ? "enabled=" + enabled + ", " : "") +
                (powerbankId != null ? "powerbankId=" + powerbankId + ", " : "") +
            "}";
    }

}
