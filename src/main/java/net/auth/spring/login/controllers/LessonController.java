package net.auth.spring.login.controllers;

import net.auth.spring.login.dto.LessonDto;
import net.auth.spring.login.models.Course;
import net.auth.spring.login.models.Lesson;
import net.auth.spring.login.payload.response.LessonInfoResponse;
import net.auth.spring.login.repository.CourseRepository;
import net.auth.spring.login.repository.LessonRepository;
import net.auth.spring.login.security.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public LessonController(LessonService lessonService,
                            LessonRepository lessonRepository,
                            CourseRepository courseRepository) {
        this.lessonService = lessonService;
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    @PostMapping("/create/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createLesson(@PathVariable Long id, @RequestBody LessonDto lesson){
        try {
            lessonService.create(lesson, id);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body("Something went wrong: "+ e.getMessage());
        }
        return ResponseEntity
                .ok()
                .body(new LessonInfoResponse(lesson.getTitle(),
                                             lesson.getContent()
                ));
    }

    @GetMapping("/all")
    public List<Lesson> getAllLessons(){
        return lessonRepository.findAll();
    }

    @GetMapping("/all/{id}")
    public List<Lesson> getAllLessonsByCourse(@PathVariable Long id){
        Course course = courseRepository.findById(id).get();
        return lessonRepository.findAllByCourse(course);
    }
}
