package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.domain.base_entity.Category;
import adrianromanski.movies.model.base_entity.CategoryDTO;
import adrianromanski.movies.services.category.CategoryService;
import adrianromanski.movies.services.image.ImageServiceCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageCategoryControllerTest {

    @Mock
    ImageServiceCategory imageService;

    @Mock
    CategoryService categoryService;

    @InjectMocks
    ImageCategoryController controller;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getImageForm() throws Exception {
        //given
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);

        when(categoryService.getCategoryDTOByName(anyString())).thenReturn(categoryDTO);

        //when
        mockMvc.perform(get("/category/comedy/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("category"));

        verify(categoryService, times(1)).getCategoryDTOByName(anyString());
    }


    @Test
    public void handleImagePost() throws Exception {
        //given
        Category category = new Category();
        category.setId(1L);
        category.setName("comedy");

        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "Spring Framework Guru".getBytes());

        when(categoryService.getCategoryById(anyLong())).thenReturn(category);

        mockMvc.perform(multipart("/category/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/category/comedy"));
    }


    @Test
    public void renderImageFromDB() throws Exception {
        //given
        Category category = new Category();
        category.setId(1L);

        String s = "fake image text";
        Byte[] bytesBoxed = new Byte[s.getBytes().length];

        int i = 0;

        for (byte primByte : s.getBytes()){
            bytesBoxed[i++] = primByte;
        }

        category.setImage(bytesBoxed);


        when(categoryService.getCategoryById(anyLong())).thenReturn(category);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/category/1/categoryImage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();

        assertEquals(s.getBytes().length, responseBytes.length);
    }
}