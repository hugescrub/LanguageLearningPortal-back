package net.auth.spring.login.payload.response;

import net.auth.spring.login.models.Course;
import java.util.List;

public class EnrollmentResponse {

    private String username;
    private String email;
    private int enrollmentsCount;
    private List<Course> enrollments;

    public EnrollmentResponse(String username, String email, int enrollmentsCount, List<Course> enrollments) {
        this.username = username;
        this.email = email;
        this.enrollmentsCount = enrollmentsCount;
        this.enrollments = enrollments;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEnrollmentsCount() {
        return enrollmentsCount;
    }

    public void setEnrollmentsCount(int enrollmentsCount) {
        this.enrollmentsCount = enrollmentsCount;
    }

    public List<Course> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Course> enrollments) {
        this.enrollments = enrollments;
    }
}
