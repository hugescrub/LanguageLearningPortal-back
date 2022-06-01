package net.auth.spring.login.repository;

import net.auth.spring.login.models.Course;
import net.auth.spring.login.models.Enrollment;
import net.auth.spring.login.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findAllByUser(User user);

    Enrollment findByCourseAndUser(Course course, User user);
}
