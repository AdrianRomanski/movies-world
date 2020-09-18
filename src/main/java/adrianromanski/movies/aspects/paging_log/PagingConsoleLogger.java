package adrianromanski.movies.aspects.paging_log;

import adrianromanski.movies.domain.base_entity.Movie;
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
public class PagingConsoleLogger {


    @Pointcut("@annotation(adrianromanski.movies.aspects.paging_log.LogPaging)")
    public void logPaging() {}


//    @AfterThrowing(value = "logSearching()", throwing = "exception")
//    public void onException(Exception exception) {
//        log.warning("Exception: " + exception.getClass().getSimpleName() +
//                        " " + exception.getMessage());
//    }

    @AfterReturning(value = "logPaging()", returning = "paged")
    public void log(Page<Movie> paged) {
        log.info(paged.getNumber() + 1 + "# Page of Movies successfully returned");
    }

}
