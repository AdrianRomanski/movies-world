package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.model.base_entity.CategoryDTO;
import adrianromanski.movies.services.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
        mockMvc.perform(get("/admin/createCategory"))
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
        mockMvc.perform(get("/admin/updateCategory/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categoryDTO"))
                .andExpect(view().name(VIEW + "updateCategoryForm"));
    }


    @Test
    @DisplayName("Happy Path, method = showCategories")
    void showCategories() throws Exception {
        mockMvc.perform(get("/admin/showCategories"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("showCategories"))
                .andExpect(view().name(VIEW + "showCategories"));
    }


    @Test
    @DisplayName("Happy Path, method = checkCategoryCreation")
    void checkCategoryCreationHappyPath() throws Exception {
        //given
        CategoryDTO categoryDTO = new CategoryDTO();

        //when
        when(categoryService.createCategory(any())).thenReturn(categoryDTO);

        //then
        mockMvc.perform(post("/admin/createCategory/check")
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
        mockMvc.perform(post("/admin/createCategory/check")
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
        mockMvc.perform(post("/admin/updateCategory/check")
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
        mockMvc.perform(post("/admin/updateCategory/check")
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

        mockMvc.perform(get("/admin/deleteCategory/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("showCategories"))
                .andExpect(view().name(VIEW + "showCategories"));
    }
}