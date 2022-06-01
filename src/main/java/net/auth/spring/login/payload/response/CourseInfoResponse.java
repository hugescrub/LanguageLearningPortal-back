package net.auth.spring.login.payload.response;

public class CourseInfoResponse {

    private Long id;
    private String title;
    private String description;
    private String language;
    private String cover;

    public CourseInfoResponse(Long id, String title, String description, String language, String cover) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.language = language;
        this.cover = cover;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
