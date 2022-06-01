package net.auth.spring.login.repository;

import net.auth.spring.login.models.Course;
import net.auth.spring.login.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findByTitle (String title);

    List<Course> findCourseById(Long id);

    List<Course> findByTeacher(Teacher teacher);
}