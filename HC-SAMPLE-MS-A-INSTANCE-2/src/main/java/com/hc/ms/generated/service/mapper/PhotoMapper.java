package com.hc.ms.generated.service.mapper;


import com.hc.ms.generated.domain.*;
import com.hc.ms.generated.service.dto.PhotoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Photo} and its DTO {@link PhotoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AlbumMapper.class})
public interface PhotoMapper extends EntityMapper<PhotoDTO, Photo> {

    @Mapping(source = "album.id", target = "albumId")
    PhotoDTO toDto(Photo photo);

    @Mapping(source = "albumId", target = "album")
    Photo toEntity(PhotoDTO photoDTO);

    default Photo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Photo photo = new Photo();
        photo.setId(id);
        return photo;
    }
}
