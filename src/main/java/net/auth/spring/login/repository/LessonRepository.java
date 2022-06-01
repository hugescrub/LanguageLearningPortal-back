package net.auth.spring.login.repository;

import net.auth.spring.login.models.Course;
import net.auth.spring.login.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByCourse (Course course);
}
