package com.hc.generated.service.dto;

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
 * Criteria class for the {@link com.hc.generated.domain.Photo} entity. This class is used
 * in {@link com.hc.generated.web.rest.PhotoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /photos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PhotoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter desc;

    private StringFilter src;

    private BooleanFilter isPrivate;

    private LongFilter albumId;

    public PhotoCriteria() {
    }

    public PhotoCriteria(PhotoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.desc = other.desc == null ? null : other.desc.copy();
        this.src = other.src == null ? null : other.src.copy();
        this.isPrivate = other.isPrivate == null ? null : other.isPrivate.copy();
        this.albumId = other.albumId == null ? null : other.albumId.copy();
    }

    @Override
    public PhotoCriteria copy() {
        return new PhotoCriteria(this);
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

    public StringFilter getSrc() {
        return src;
    }

    public void setSrc(StringFilter src) {
        this.src = src;
    }

    public BooleanFilter getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(BooleanFilter isPrivate) {
        this.isPrivate = isPrivate;
    }

    public LongFilter getAlbumId() {
        return albumId;
    }

    public void setAlbumId(LongFilter albumId) {
        this.albumId = albumId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PhotoCriteria that = (PhotoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(desc, that.desc) &&
            Objects.equals(src, that.src) &&
            Objects.equals(isPrivate, that.isPrivate) &&
            Objects.equals(albumId, that.albumId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        desc,
        src,
        isPrivate,
        albumId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhotoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (desc != null ? "desc=" + desc + ", " : "") +
                (src != null ? "src=" + src + ", " : "") +
                (isPrivate != null ? "isPrivate=" + isPrivate + ", " : "") +
                (albumId != null ? "albumId=" + albumId + ", " : "") +
            "}";
    }

}
