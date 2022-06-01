package net.auth.spring.login.controllers;

import net.auth.spring.login.payload.response.MessageResponse;
import net.auth.spring.login.security.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/enrollment")
@PreAuthorize("hasRole('ROLE_USER')")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/save/{id}")
    public ResponseEntity<?> saveEnrollment(@PathVariable Long id, Authentication authentication) {
        try {
            String username = authentication.getName();
            enrollmentService.createEnrollment(id, username);

            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("User enrolled successfully"));
        } catch (Exception e) {
            String message = e.getMessage();
            e.printStackTrace();

            if (message.equalsIgnoreCase("no value present")){
                return ResponseEntity
                        .badRequest()
                        .body("Course with id {" + id + "} not found");
            }
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Something went wrong"));
        }
    }
}
