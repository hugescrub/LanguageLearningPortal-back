package net.auth.spring.login.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "teachers",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })

public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "username", referencedColumnName = "username"),
            @JoinColumn(name = "email", referencedColumnName = "email")
    })
    private User user;

    @NotBlank
    @Column(name = "first_name")
    @Size(min = 2, max = 20)
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    @Size(min = 2, max = 20)
    private String lastName;

    @Column(name = "description")
    private String description;

    @Size(max = 2000000)
    private String image;

    private boolean approved;


    public Teacher(){

    }

    public Teacher(Long id, User user, String firstName,
                   String lastName, String description, String image, boolean approved) {
        this.id = id;
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.image = image;
        this.approved = approved;
    }

    public Teacher(User user, String firstName,
                   String lastName, String description, String image, boolean approved) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.image = image;
        this.approved = approved;
    }

    public Teacher(User user, String firstName,
                   String lastName, String description, String image) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.image = image;
    }

    public Teacher(String firstName,
                   String lastName, String description, String image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
