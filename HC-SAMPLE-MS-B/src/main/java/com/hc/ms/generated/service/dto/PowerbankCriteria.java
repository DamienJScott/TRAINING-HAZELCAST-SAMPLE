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
 * Criteria class for the {@link com.hc.ms.generated.domain.Powerbank} entity. This class is used
 * in {@link com.hc.ms.generated.web.rest.PowerbankResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /powerbanks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PowerbankCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter desc;

    private StringFilter type;

    private StringFilter position;

    private BooleanFilter isUsing;

    private BooleanFilter enabled;

    private LongFilter stationId;

    public PowerbankCriteria() {
    }

    public PowerbankCriteria(PowerbankCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.desc = other.desc == null ? null : other.desc.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.position = other.position == null ? null : other.position.copy();
        this.isUsing = other.isUsing == null ? null : other.isUsing.copy();
        this.enabled = other.enabled == null ? null : other.enabled.copy();
        this.stationId = other.stationId == null ? null : other.stationId.copy();
    }

    @Override
    public PowerbankCriteria copy() {
        return new PowerbankCriteria(this);
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

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getPosition() {
        return position;
    }

    public void setPosition(StringFilter position) {
        this.position = position;
    }

    public BooleanFilter getIsUsing() {
        return isUsing;
    }

    public void setIsUsing(BooleanFilter isUsing) {
        this.isUsing = isUsing;
    }

    public BooleanFilter getEnabled() {
        return enabled;
    }

    public void setEnabled(BooleanFilter enabled) {
        this.enabled = enabled;
    }

    public LongFilter getStationId() {
        return stationId;
    }

    public void setStationId(LongFilter stationId) {
        this.stationId = stationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PowerbankCriteria that = (PowerbankCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(desc, that.desc) &&
            Objects.equals(type, that.type) &&
            Objects.equals(position, that.position) &&
            Objects.equals(isUsing, that.isUsing) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(stationId, that.stationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        desc,
        type,
        position,
        isUsing,
        enabled,
        stationId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PowerbankCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (desc != null ? "desc=" + desc + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (position != null ? "position=" + position + ", " : "") +
                (isUsing != null ? "isUsing=" + isUsing + ", " : "") +
                (enabled != null ? "enabled=" + enabled + ", " : "") +
                (stationId != null ? "stationId=" + stationId + ", " : "") +
            "}";
    }

}
