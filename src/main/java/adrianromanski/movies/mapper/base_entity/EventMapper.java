package adrianromanski.movies.mapper.base_entity;

import adrianromanski.movies.domain.base_entity.News;
import adrianromanski.movies.model.base_entity.NewsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface EventMapper {

    NewsDTO eventToEventDTO(News news);

    News eventDTOToEvent(NewsDTO newsDTO);
}
