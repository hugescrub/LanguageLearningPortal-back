package net.auth.spring.login.security.services;

import net.auth.spring.login.dto.TeacherDto;
import net.auth.spring.login.models.Teacher;
import net.auth.spring.login.models.User;
import net.auth.spring.login.repository.TeacherRepository;
import net.auth.spring.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;

    @Autowired
    public TeacherService (TeacherRepository teacherRepository, UserRepository userRepository){
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
    }

    public void createTeacher (TeacherDto teacherDto) {
        User user = userRepository.findByUsername(teacherDto.getUsername());
        String firstName = teacherDto.getFirstName();
        String lastName = teacherDto.getLastName();
        String description = teacherDto.getDescription();
        String image = teacherDto.getImage();
        boolean approved = teacherDto.isApproved();

        Teacher teacher = new Teacher(user, firstName, lastName, description, image, approved);

        teacherRepository.save(teacher);
    }

    public List<Teacher> getAll(){
        return teacherRepository.findAll();
    }

    public void update (Teacher teacher) throws Exception{

        Teacher currentTeacher = teacherRepository.findById(teacher.getId()).get();

        currentTeacher.setFirstName(teacher.getFirstName());
        currentTeacher.setLastName(teacher.getLastName());
        currentTeacher.setDescription(teacher.getDescription());
        currentTeacher.setImage(teacher.getImage());

        teacherRepository.save(currentTeacher);
    }

    public void update (TeacherDto teacher) throws Exception{

        Teacher currentTeacher = teacherRepository.findById(teacher.getId()).get();

        currentTeacher.setFirstName(teacher.getFirstName());
        currentTeacher.setLastName(teacher.getLastName());
        currentTeacher.setDescription(teacher.getDescription());
        currentTeacher.setImage(teacher.getImage());

        teacherRepository.save(currentTeacher);
    }

    public void delete(Teacher teacher){
        teacherRepository.delete(teacher);
    }

}
