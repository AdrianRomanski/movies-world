package adrianromanski.movies.controllers.admin;

import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.services.image.ImageService;
import adrianromanski.movies.services.movie.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ImageMovieControllerTest {

    @Mock
    ImageService<MovieDTO> imageService;

    @Mock
    MovieService movieService;

    @InjectMocks
    ImageMovieController controller;

    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
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