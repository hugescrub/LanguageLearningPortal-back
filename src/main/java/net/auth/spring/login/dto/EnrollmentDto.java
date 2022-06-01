package net.auth.spring.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.auth.spring.login.models.Course;
import net.auth.spring.login.models.User;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EnrollmentDto {
    private User user;
    private Course course;
    private String status;
}
