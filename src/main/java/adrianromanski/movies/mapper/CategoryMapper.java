package adrianromanski.movies.mapper;

import adrianromanski.movies.domain.Category;
import adrianromanski.movies.model.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel="spring")
public interface CategoryMapper {

    @Mapping(source = "movies", target = "moviesDTO")
    CategoryDTO categoryToCategoryDTO(Category category);

    @Mapping(source = "moviesDTO", target = "movies")
    Category categoryDTOToCategory(CategoryDTO categoryDTO);
}
