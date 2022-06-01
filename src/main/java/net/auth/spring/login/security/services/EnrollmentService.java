package net.auth.spring.login.security.services;

import net.auth.spring.login.models.Course;
import net.auth.spring.login.models.Enrollment;
import net.auth.spring.login.models.User;
import net.auth.spring.login.repository.CourseRepository;
import net.auth.spring.login.repository.EnrollmentRepository;
import net.auth.spring.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository, UserRepository userRepository){
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public void createEnrollment(Long id, String username) throws Exception{
        Course course = courseRepository.findById(id).get();
        User user = userRepository.findByUsername(username);
        if(null != enrollmentRepository.findByCourseAndUser(course, user)){
            throw new Exception("You are already enrolled on this course");
        }
        Enrollment enrollment = new Enrollment(user, course);
        enrollmentRepository.save(enrollment);
    }
}
