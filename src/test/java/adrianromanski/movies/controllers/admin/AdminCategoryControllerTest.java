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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminCategoryControllerTest {

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
        mockMvc.perform(get("/createCategory"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("categoryDTO"))
                .andExpect(view().name("admin/createCategoryForm"));
    }


    @Test
    @DisplayName("Happy Path, method = checkCategoryCreation")
    void checkCategoryCreationHappyPath() throws Exception {
        //given
        CategoryDTO categoryDTO = new CategoryDTO();

        //when
        when(categoryService.createCategory(any())).thenReturn(categoryDTO);

        //then
        mockMvc.perform(post("/checkCategory")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Horror")
                .param("description", "A horror film is a film that seeks to elicit fear for entertainment purposes Initially " +
                        "inspired by literature from authors such as Edgar Allan Poe, Bram Stoker, and Mary Shelley")
        )
                .andExpect(view().name("successRegistration"))
                .andExpect(model().attributeExists("categoryDTO"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("UnHappy Path, method = checkCategoryCreation")
    void name() throws Exception {
        //given
        CategoryDTO categoryDTO = new CategoryDTO();

        //when
        when(categoryService.createCategory(any())).thenReturn(categoryDTO);

        //then
        mockMvc.perform(post("/checkCategory")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "HorrorHorrorHorrorHorrorHorrorvHorrorHorrorHorrorHorrorHorrorHorror")
                .param("description", "word")
        )
                .andExpect(view().name("admin/createCategoryForm"));

    }
}