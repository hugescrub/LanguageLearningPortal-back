package net.auth.spring.login.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.auth.spring.login.models.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDto {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String description;
    private String image;
    private boolean approved = false;

    @JsonIgnore
    private User user;
}
