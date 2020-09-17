package adrianromanski.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class MoviesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesApplication.class, args);
    }

}
