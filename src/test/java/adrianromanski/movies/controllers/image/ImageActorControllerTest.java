package adrianromanski.movies.controllers.image;

import adrianromanski.movies.model.person.ActorDTO;
import adrianromanski.movies.services.actor.ActorServiceImpl;
import adrianromanski.movies.services.image.ImageServiceActor;
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

class ImageActorControllerTest {

    @Mock
    ImageServiceActor imageService;

    @Mock
    ActorServiceImpl actorService;

    @InjectMocks
    ImageActorController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void renderImageFromDB() throws Exception {
        //given
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setId(2L);

        String s = "fake image text";
        Byte[] bytesBoxed = new Byte[s.getBytes().length];

        int i = 0;

        for (byte primByte : s.getBytes()){
            bytesBoxed[i++] = primByte;
        }

        actorDTO.setImage(bytesBoxed);

        when(actorService.getActorByID(anyLong())).thenReturn(actorDTO);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/actor/2/actorImage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();

        assertEquals(s.getBytes().length, responseBytes.length);
    }



    @Test
    public void handleImagePost() throws Exception {
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setId(2L);

        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "File".getBytes());

        when(actorService.getActorByID(anyLong())).thenReturn(actorDTO);

        mockMvc.perform(multipart("/actor/1/image").file(multipartFile))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin/actor/showActorForm"));
    }


    @Test
    @DisplayName("Happy Path, method = getImageForm")
    void getImageForm() throws Exception {
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setId(2L);;

        //when
        when(actorService.getActorByID(anyLong())).thenReturn(actorDTO);

        //then
        mockMvc.perform(get("/actor/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("actorDTO"));

        verify(actorService, times(1)).getActorByID(anyLong());
    }


}