package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.controllers.image.ImageMovieController;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.services.image.ImageServiceMovie;
import adrianromanski.movies.services.movie.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageMovieControllerTest {

    @Mock
    ImageServiceMovie imageService;

    @Mock
    MovieServiceImpl movieService;

    @InjectMocks
    ImageMovieController controller;

    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    @DisplayName("Happy Path, method = getImageForm")
    void getImageForm() throws Exception {
        //given
        MovieDTO movieDTO = new MovieDTO();

        //when
        when(movieService.getMovieByID(anyLong())).thenReturn(movieDTO);
        //then
        mockMvc.perform(get("/movie/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movieDTO"));

        verify(movieService, times(1)).getMovieByID(anyLong());
    }


    @Test
    public void handleImagePost() throws Exception {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(1L);
        movieDTO.setName("Star Wars");

        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "Spring Framework Guru".getBytes());

        when(movieService.getMovieByID(anyLong())).thenReturn(movieDTO);

        mockMvc.perform(multipart("/movie/1/image").file(multipartFile))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/adminHome"));
    }



    @Test
    @DisplayName("Happy Path, method = renderImageFromDB")
    void renderImageFromDB() throws Exception {
        //given
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(1L);

        String s = "fake image text";
        Byte[] bytesBoxed = new Byte[s.getBytes().length];

        int i = 0;

        for (byte primByte : s.getBytes()){
            bytesBoxed[i++] = primByte;
        }
        movieDTO.setImage(bytesBoxed);

        when(movieService.getMovieByID(anyLong())).thenReturn(movieDTO);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/movie/1/movieImage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();

        assertEquals(s.getBytes().length, responseBytes.length);
    }
}