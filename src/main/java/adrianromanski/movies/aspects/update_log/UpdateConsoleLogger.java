package adrianromanski.movies.aspects.update_log;

import adrianromanski.movies.model.base_entity.CategoryDTO;
import adrianromanski.movies.model.base_entity.MovieDTO;
import adrianromanski.movies.model.base_entity.SeriesDTO;
import adrianromanski.movies.model.person.ActorDTO;
import adrianromanski.movies.model.person.DirectorDTO;
import adrianromanski.movies.model.person.UserDTO;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Log
@Aspect
@Component
@AllArgsConstructor
public class UpdateConsoleLogger {

    @Pointcut("@annotation(adrianromanski.movies.aspects.update_log.LogUpdate)")
    public void logUpdate() {}

    @AfterReturning(value = "logUpdate()", returning = "movieDTO")
    public void log(MovieDTO movieDTO) {
        getLogEntry("Movie", movieDTO.getName());
    }

    @AfterReturning(value = "logUpdate()", returning = "actorDTO")
    public void log(ActorDTO actorDTO) {
        getLogEntry("Actor", actorDTO.getFullName());
    }

    @AfterReturning(value = "logUpdate()", returning = "categoryDTO")
    public void log(CategoryDTO categoryDTO) {
        getLogEntry("Category", categoryDTO.getName());
    }

    @AfterReturning(value = "logUpdate()", returning = "directorDTO")
    public void log(DirectorDTO directorDTO) {
        getLogEntry("Director", directorDTO.getFullName());
    }

    @AfterReturning(value = "logUpdate()", returning = "seriesDTO")
    public void log(SeriesDTO seriesDTO) {
        getLogEntry("Series", seriesDTO.getName());
    }

    @AfterReturning(value = "logUpdate()", returning = "userDTO")
    public void log(UserDTO userDTO) {
        getLogEntry("User", userDTO.getFullName());
    }

    private void getLogEntry(String className, String fieldName) {
        log.info(className + " " + fieldName + " successfully updated");
    }
}
