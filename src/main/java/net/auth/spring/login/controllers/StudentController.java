package net.auth.spring.login.controllers;

import net.auth.spring.login.models.Course;
import net.auth.spring.login.models.Enrollment;
import net.auth.spring.login.models.User;
import net.auth.spring.login.payload.response.EnrollmentResponse;
import net.auth.spring.login.payload.response.UserInfoResponse;
import net.auth.spring.login.repository.EnrollmentRepository;
import net.auth.spring.login.repository.UserRepository;
import net.auth.spring.login.security.services.CourseService;
import net.auth.spring.login.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final CourseService courseService;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public StudentController(CourseService courseService, UserRepository userRepository, EnrollmentRepository enrollmentRepository) {
        this.courseService = courseService;
        this.userRepository = userRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    // retrieve user info
    @GetMapping("/details")
    @ResponseBody
    public ResponseEntity<?> userInfo(Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok()
                .body(new UserInfoResponse(userDetails.getUsername(),
                        userDetails.getEmail()));
    }

    // retrieve user info and its enrollments
    @GetMapping("/profile")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getUserCourses(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        try {
            String currentUsername = authentication.getName();
            User user = userRepository.findByUsername(currentUsername);

            List<Enrollment> enrollmentList = enrollmentRepository.findAllByUser(user);
            int enrollmentsCount = enrollmentList.size();

            List<Long> courseIds = enrollmentList.stream()
                    .map(item -> item.getCourse().getId())
                    .collect(Collectors.toList());

            List<Course> enrollments = new ArrayList<>();

            for (Long courseId: courseIds){
                List<Course> getEnrollments = courseService.getAllById(courseId);
                enrollments.addAll(getEnrollments);
            }
            return ResponseEntity
                    .ok()
                    .body(new EnrollmentResponse(userDetails.getUsername(),
                            userDetails.getEmail(),
                            enrollmentsCount,
                            enrollments));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body("Something went wrong");
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Enrollment> getAllStudents(){
        return enrollmentRepository.findAll();
    }
}
