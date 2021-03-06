package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.domain.base_entity.Category;
import adrianromanski.movies.model.base_entity.CategoryDTO;
import adrianromanski.movies.services.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminCategoryControllerTest {

    public static final String DESCRIPTION = "A horror film is a film that seeks to elicit fear for entertainment purposes Initially " +
            "inspired by literature from authors such as Edgar Allan Poe, Bram Stoker, and Mary Shelley";
    public static final String NAME = "Horror";
    public static final String VIEW = "admin/category/";

    @Mock
    CategoryService categoryService;

    @InjectMocks
    AdminCategoryController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Happy Path, method = createCategory")
    void createCategory() throws Exception {
        mockMvc.perform(get("/admin/category/createCategory"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("categoryDTO"))
                .andExpect(view().name(VIEW + "createCategoryForm"));
    }


    @Test
    @DisplayName("Happy Path, method = updateCategory")
    void updateCategory() throws Exception {
        //given
        CategoryDTO categoryDTO = new CategoryDTO();

        //when
        when(categoryService.getCategoryDTOById(anyLong())).thenReturn(categoryDTO);

        //then
        mockMvc.perform(get("/admin/category/updateCategory/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categoryDTO"))
                .andExpect(view().name(VIEW + "updateCategoryForm"));
    }


    @Test
    @DisplayName("Happy Path, method = listCategoryPageByPage")
    void listCategoryPageByPage() throws Exception {
        // Given
        List<Category> categoryList = Arrays.asList(new Category(), new Category(), new Category());
        PageRequest pageable = PageRequest.of(0, 5);
        Page<Category> categoryPage = new PageImpl<>(categoryList, pageable, categoryList.size());

        List<CategoryDTO> categoryListDTO = Arrays.asList(new CategoryDTO(), new CategoryDTO(), new CategoryDTO());
        PageRequest pageableDTO = PageRequest.of(0, 5);
        Page<CategoryDTO> categoryPageDTO = new PageImpl<>(categoryListDTO, pageableDTO, categoryListDTO.size());

        when(categoryService.getAllCategoriesPaged(pageable)).thenReturn(categoryPage);
        when(categoryService.getPageCategoryDTO(categoryPage, pageable)).thenReturn(categoryPageDTO);

        mockMvc.perform(get("/admin/category/showCategories/page/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categoryList"))
                .andExpect(view().name("admin/category/showCategories"));
    }


    @Test
    @DisplayName("Happy Path, method = checkCategoryCreation")
    void checkCategoryCreationHappyPath() throws Exception {
        //given
        CategoryDTO categoryDTO = new CategoryDTO();

        //when
        when(categoryService.createCategory(any())).thenReturn(categoryDTO);

        //then
        mockMvc.perform(post("/admin/category/createCategory/check")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", NAME)
                .param("description", DESCRIPTION)
        )
                .andExpect(view().name(VIEW + "showCategories"))
                .andExpect(model().attributeExists("categoryDTO"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("UnHappy Path, method = checkCategoryCreation")
    void checkCategoryCreationUnHappyPath() throws Exception {
        //given
        CategoryDTO categoryDTO = new CategoryDTO();

        //when
        when(categoryService.createCategory(any())).thenReturn(categoryDTO);

        //then
        mockMvc.perform(post("/admin/category/createCategory/check")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "HorrorHorrorHorrorHorrorHorrorvHorrorHorrorHorrorHorrorHorrorHorror")
                .param("description", "word")
        )
                .andExpect(view().name(VIEW + "createCategoryForm"));
    }


    @Test
    @DisplayName("Happy Path, method = checkCategoryCreation")
    void checkCategoryUpdateHappyPath() throws Exception {
        //given
        CategoryDTO categoryDTO = CategoryDTO.builder().name("Horror")
                .description(DESCRIPTION)
                .id(1L)
                .build();

        //when
        when(categoryService.getCategoryDTOById(anyLong())).thenReturn(categoryDTO);

        //then
        mockMvc.perform(post("/admin/category/updateCategory/check")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Horror")
                .param("description", DESCRIPTION)
        )
                .andExpect(view().name(VIEW + "showCategories"))
                .andExpect(model().attributeExists("categoryDTO"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("UnHappy Path, method = checkCategoryUpdate")
    void checkCategoryUpdateUnHappyPath() throws Exception {
        //given
        CategoryDTO categoryDTO = CategoryDTO.builder().name("Horror")
                .description(DESCRIPTION)
                .id(1L)
                .build();

        //when
        when(categoryService.getCategoryDTOById(anyLong())).thenReturn(categoryDTO);

        //then
        mockMvc.perform(post("/admin/category/updateCategory/check")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "HorrorHorrorHorrorHorrorHorrorvHorrorHorrorHorrorHorrorHorrorHorror")
                .param("description", "word")
        )
                .andExpect(view().name(VIEW + "updateCategoryForm"));
    }


    @Test
    @DisplayName("Happy Path, method = deleteCategory")
    void deleteCategory() throws Exception {
        //given
        CategoryDTO categoryDTO = new CategoryDTO();

        //when
        when(categoryService.getCategoryDTOById(anyLong())).thenReturn(categoryDTO);

        mockMvc.perform(get("/admin/category/deleteCategory/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("showCategories"))
                .andExpect(view().name(VIEW + "showCategories"));
    }
}