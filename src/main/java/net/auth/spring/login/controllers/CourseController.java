package net.auth.spring.login.controllers;

import net.auth.spring.login.dto.CourseDto;
import net.auth.spring.login.payload.request.CourseUpdateRequest;
import net.auth.spring.login.models.Course;
import net.auth.spring.login.models.Teacher;
import net.auth.spring.login.payload.response.CourseInfoResponse;
import net.auth.spring.login.payload.response.MessageResponse;
import net.auth.spring.login.repository.*;
import net.auth.spring.login.security.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public CourseController(CourseService courseService,
                            TeacherRepository teacherRepository,
                            CourseRepository courseRepository) {

        this.courseService = courseService;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public List<Course> getAllCourses() {

        return courseService.getAll();
    }

    @PostMapping("/create/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> createCourse(@PathVariable Long id, @RequestBody CourseDto course) {
        try {
            Teacher teacher = teacherRepository.findById(id).get();
            course.setTeacher(teacher);
            courseService.create(course, id);
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("New course was successfully created"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Something went wrong"));
        }
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody CourseUpdateRequest courseUpdateRequest) {
        try {
            if (!(courseRepository.existsById(id))) {
                return ResponseEntity
                        .badRequest()
                        .body("Error: no course found with id:  " + id);
            }

            Course course = courseRepository.findById(id).get();
            course.setTitle(courseUpdateRequest.getTitle());
            course.setDescription(courseUpdateRequest.getDescription());
            course.setLanguage(courseUpdateRequest.getLanguage());
            course.setCover(courseUpdateRequest.getCover());

            courseService.update(course, id);
            return ResponseEntity
                    .ok()
                    .body(new CourseInfoResponse(course.getId(), course.getTitle(),
                            course.getDescription(), course.getLanguage(), course.getCover()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body("Something went wrong: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        try {
            Course course = courseRepository.findById(id).get();
            courseService.delete(course);
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Course was successfully deleted"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Something went wrong"));
        }
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')")
    public String userAccess() {
        return "User content";
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String teacherAccess() {
        return "Teacher content";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminAccess() {
        return "Admin content";
    }
}
