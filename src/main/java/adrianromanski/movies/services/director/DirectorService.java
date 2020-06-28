package adrianromanski.movies.services.director;

import adrianromanski.movies.model.award.DirectorAwardDTO;
import adrianromanski.movies.model.person.DirectorDTO;

public interface DirectorService {

    //GET
    DirectorDTO getDirectorByID(Long id);

    DirectorDTO getDirectorByName(String firstName, String lastName);

    //POST
    DirectorDTO createNewDirector(DirectorDTO directorDTO);

    DirectorAwardDTO addAward(Long id, DirectorAwardDTO awardDTO);

    //PUT
    DirectorDTO updateDirector(Long id, DirectorDTO directorDTO);

    DirectorAwardDTO updateAward(Long directorID, Long awardID, DirectorAwardDTO awardDTO);

    //DELETE
    void deleteDirectorByID(Long id);

    void deleteAwardByID(Long directorID, Long awardID);
}
