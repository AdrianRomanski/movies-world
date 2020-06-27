package adrianromanski.movies.mapper.person;

import adrianromanski.movies.domain.person.Actor;
import adrianromanski.movies.model.person.ActorDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActorMapperTest {

    public static final String FIRST_NAME = "Adrian";
    public static final String LAST_NAME = "Romanski";
    public static final String MALE = "Male";
    public static final long ID = 1L;
    ActorMapper actorMapper = new ActorMapperImpl();

    @Test
    void actorToActorDTO() {
        Actor actor = Actor.builder().firstName(FIRST_NAME).lastName(LAST_NAME).gender(MALE).id(ID).build();

        ActorDTO actorDTO = actorMapper.actorToActorDTO(actor);

        assertEquals(actorDTO.getFirstName(), FIRST_NAME);
        assertEquals(actorDTO.getLastName(), LAST_NAME);
        assertEquals(actorDTO.getGender(), MALE);
        assertEquals(actorDTO.getId(), ID);
    }

    @Test
    void actorDTOToActor() {
        ActorDTO actorDTO = ActorDTO.builder().firstName(FIRST_NAME).lastName(LAST_NAME).gender(MALE).id(ID).build();

        Actor actor = actorMapper.actorDTOToActor(actorDTO);

        assertEquals(actor.getFirstName(), FIRST_NAME);
        assertEquals(actor.getLastName(), LAST_NAME);
        assertEquals(actor.getGender(), MALE);
        assertEquals(actor.getId(), ID);
    }
}