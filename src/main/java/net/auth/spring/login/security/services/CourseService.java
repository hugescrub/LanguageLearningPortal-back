package net.auth.spring.login.security.services;

import net.auth.spring.login.dto.CourseDto;
import net.auth.spring.login.models.Course;
import net.auth.spring.login.models.Teacher;
import net.auth.spring.login.repository.CourseRepository;
import net.auth.spring.login.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository,
                         TeacherRepository teacherRepository){
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    public void create(CourseDto courseDto, Long id) throws Exception{

        if (null != courseRepository.findByTitle(courseDto.getTitle())){
            throw new Exception("The course with such name: "+ courseDto.getTitle() + "; already exists");
        }
        String title = courseDto.getTitle();
        String description = courseDto.getDescription();
        String language = courseDto.getLanguage();
        String courseCover = courseDto.getCover();
        Teacher teacher = teacherRepository.findById(id).get();
        Course course = new Course(title, description, language, courseCover, teacher);

        courseRepository.save(course);
    }

    public void update (Course course, Long id){
        Course currentCourse = courseRepository.findById(id).get();
        currentCourse.setTitle(course.getTitle());
        currentCourse.setDescription(course.getDescription());
        currentCourse.setLanguage(course.getLanguage());
        currentCourse.setCover(course.getCover());

        courseRepository.save(currentCourse);
    }

    public void delete (Course course){
        courseRepository.delete(course);
    }

    public List<Course> getAll(){
        return courseRepository.findAll();
    }

    public List<Course> getAllById(Long id){
        return courseRepository.findCourseById(id);
    }
}
