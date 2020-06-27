package adrianromanski.movies.mapper.base_entity;

import adrianromanski.movies.domain.base_entity.Category;
import adrianromanski.movies.model.base_entity.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel="spring")
public interface CategoryMapper {

    @Mapping(source = "movies", target = "moviesDTO")
    CategoryDTO categoryToCategoryDTO(Category category);

    @Mapping(source = "moviesDTO", target = "movies")
    Category categoryDTOToCategory(CategoryDTO categoryDTO);
}
