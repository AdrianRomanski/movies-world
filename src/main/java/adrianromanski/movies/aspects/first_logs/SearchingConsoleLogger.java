package adrianromanski.movies.aspects.first_logs;

import adrianromanski.movies.domain.base_entity.Movie;
import adrianromanski.movies.model.base_entity.MovieDTO;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Log
@Aspect
@Component
@AllArgsConstructor
public class SearchingConsoleLogger<T> {


    @Pointcut("@annotation(adrianromanski.movies.aspects.first_logs.LogSearching)")
    public void logSearching() { }


//    @AfterThrowing(value = "logSearching()", throwing = "exception")
//    public void onException(Exception exception) {
//        log.warning("Exception: " + exception.getClass().getSimpleName() +
//                        " " + exception.getMessage());
//    }

    @AfterReturning(value = "logSearching()", returning = "movieDTO")
    public void log(MovieDTO movieDTO) {
        log.info("Movie: " + movieDTO.getName() + " successfully returned");
    }

    @AfterReturning(value = "logSearching()", returning = "paged")
    public void log(Page<Movie> paged) {
        log.info(paged.getNumber() + 1 + "# Page of Movies successfully returned");
    }


}
