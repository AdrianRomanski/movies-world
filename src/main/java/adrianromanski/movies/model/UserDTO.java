package adrianromanski.movies.model;


import lombok.*;



@Data
@NoArgsConstructor
public class UserDTO  extends  PersonDTO {

    private Long id;

    @Builder
    public UserDTO(String firstName, String lastName, String gender,Long id) {
        super(firstName, lastName, gender);
        this.id = id;
    }
}
