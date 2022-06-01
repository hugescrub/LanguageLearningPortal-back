package net.auth.spring.login.controllers;

import net.auth.spring.login.dto.TeacherDto;
import net.auth.spring.login.models.*;
import net.auth.spring.login.payload.response.MessageResponse;
import net.auth.spring.login.payload.response.TeacherInfoResponse;
import net.auth.spring.login.repository.CourseRepository;
import net.auth.spring.login.repository.RoleRepository;
import net.auth.spring.login.repository.TeacherRepository;
import net.auth.spring.login.repository.UserRepository;
import net.auth.spring.login.security.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final TeacherRepository teacherRepository;
    private final RoleRepository roleRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Autowired
    public TeacherController(TeacherService teacherService,
                             TeacherRepository teacherRepository,
                             RoleRepository roleRepository,
                             CourseRepository courseRepository,
                             UserRepository userRepository) {

        this.teacherService = teacherService;
        this.teacherRepository = teacherRepository;
        this.roleRepository = roleRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTeacher(@RequestBody TeacherDto teacher) {

        User teacherUser = userRepository.findByUsername(teacher.getUsername());
        Role teacherRole = roleRepository.findById(2);

        try {
            if (!(userRepository.existsByEmail(teacher.getEmail())) || !(userRepository.existsByUsername(teacher.getUsername()))) {
                return ResponseEntity
                        .badRequest()
                        .body(("Error: no user found with such username or email"));
            }

            if (!(teacherUser.getRoles().contains(teacherRole))){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: this user is not a teacher"));
            }

            teacherService.createTeacher(teacher);
            return ResponseEntity
                    .ok()
                    .body(new TeacherInfoResponse(
                            teacher.getUsername(),
                            teacher.getEmail(),
                            teacher.getFirstName(),
                            teacher.getLastName(),
                            teacher.getDescription(),
                            teacher.getImage(),
                            teacher.isApproved()
                    ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: teacher already exists with such username or email"));
        }
    }

    @GetMapping("/unapproved")
    public List<Teacher> getUnapproved(){
        List<Teacher> teacher = teacherRepository.findAll();
        return teacherRepository.findByApproved(false);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateTeacher(@Valid @RequestBody Teacher teacherBody) {
        try {
            Teacher teacher = teacherRepository.findById(teacherBody.getId()).get();
            teacher.setId(teacherBody.getId());
            teacher.setFirstName(teacherBody.getFirstName());
            teacher.setLastName(teacherBody.getLastName());
            teacher.setDescription(teacherBody.getDescription());
            teacher.setImage(teacherBody.getImage());
            teacher.setApproved(true);

            teacherService.update(teacherBody);

            return ResponseEntity
                    .ok()
                    .body(new TeacherInfoResponse(
                            teacher.getUser().getUsername(),
                            teacher.getUser().getEmail(),
                            teacher.getFirstName(),
                            teacher.getLastName(),
                            teacher.getDescription(),
                            teacher.getImage(),
                            teacher.isApproved()
                    ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Something went wrong"));
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
    public List<Teacher> getAllTeachers() {
        return teacherService.getAll();
    }

    @GetMapping("/all/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')")
    public List<Course> getCoursesByTeacher(@PathVariable Long id){
        Teacher teacher = teacherRepository.findById(id).get();
        return courseRepository.findByTeacher(teacher);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {
        try {
            Teacher teacher = teacherRepository.findById(id).get();
            teacherService.delete(teacher);
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Teacher has been deleted successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage();

            if (message.equalsIgnoreCase("no value present")) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Unable to delete teacher. Invalid id provided"));
            }
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Something went wrong"));
        }
    }
}
