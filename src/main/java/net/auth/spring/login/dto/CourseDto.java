package net.auth.spring.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.auth.spring.login.models.Teacher;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseDto {
    private Long id;
    private String title;
    private String description;
    private String language;
    private String cover;
    private Teacher teacher;
}
