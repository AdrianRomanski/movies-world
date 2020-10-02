package adrianromanski.movies.aspects.delete_log;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Log
@Aspect
@Component
@AllArgsConstructor
public class DeleteConsoleLogger {

    @Pointcut("@annotation(adrianromanski.movies.aspects.delete_log.LogDelete)")
    public void logDelete() {}


    @After("logDelete()")
    public void log() {
        System.out.println("Successfully Deleted");
    }

    @AfterThrowing(value = "logDelete()", throwing = "exception")
    public void onException(Exception exception) {
        log.info("Delete exception: " + exception.getClass().getSimpleName());
    }

}
