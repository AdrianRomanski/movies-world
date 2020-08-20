package adrianromanski.movies.services.image;

import adrianromanski.movies.domain.base_entity.Category;
import adrianromanski.movies.mapper.base_entity.CategoryMapper;
import adrianromanski.movies.mapper.base_entity.CategoryMapperImpl;
import adrianromanski.movies.model.base_entity.CategoryDTO;
import adrianromanski.movies.repositories.base_entity.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ImageServiceCategoryTest {

    @Mock
    CategoryRepository categoryRepository;

    ImageServiceCategory imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        CategoryMapper categoryMapper = new CategoryMapperImpl();

        imageService = new ImageServiceCategory(categoryRepository, categoryMapper);
    }

    @Test
    @DisplayName("Happy Path, method = saveImageFile")
    void saveImageFile() throws IOException {
        //given
        Long id = 1L;
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "Spring Framework Guru".getBytes());

        Category category = new Category();
        category.setId(id);
        Optional<Category> categoryOptional = Optional.of(category);

        CategoryDTO categoryDTO = new CategoryDTO();

        when(categoryRepository.findByName(anyString())).thenReturn(categoryOptional);

        ArgumentCaptor<Category> argumentCaptor = ArgumentCaptor.forClass(Category.class);

        //when
        imageService.saveImageFile(categoryDTO, multipartFile);

        //then
        verify(categoryRepository, times(1)).save(argumentCaptor.capture());
        Category savedCategory = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedCategory.getImage().length);
    }
}