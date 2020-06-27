package adrianromanski.movies.services.director;

import adrianromanski.movies.model.person.DirectorDTO;

public interface DirectorService {

    //GET
    DirectorDTO getDirectorByID(Long id);

    DirectorDTO getDirectorByName(String firstName, String lastName);

    //POST
    DirectorDTO createNewDirector(DirectorDTO directorDTO);

    //PUT
    DirectorDTO updateDirector(Long id, DirectorDTO directorDTO);

    //DELETE
    void deleteDirectorByID(Long id);
}
