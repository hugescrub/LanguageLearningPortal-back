package net.auth.spring.login.security.services;

import net.auth.spring.login.dto.LessonDto;
import net.auth.spring.login.models.*;
import net.auth.spring.login.repository.CourseRepository;
import net.auth.spring.login.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository,
                         CourseRepository courseRepository){
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    public void create(LessonDto lessonDto, Long id){

        String title = lessonDto.getTitle();
        String content = lessonDto.getContent();
        Course course = courseRepository.findById(id).get();

        Lesson lesson = new Lesson(title, content, course);
        lessonRepository.save(lesson);
    }
}
