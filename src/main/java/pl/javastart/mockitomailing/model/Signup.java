package pl.javastart.mockitomailing.model;

import java.time.LocalDate;

public class Signup {

    private User user;
    private Course course;
    private LocalDate accessFrom;
    private LocalDate accessTo;

    public Signup(User user, Course course, LocalDate accessFrom, LocalDate accessTo) {
        this.user = user;
        this.course = course;
        this.accessFrom = accessFrom;
        this.accessTo = accessTo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getAccessFrom() {
        return accessFrom;
    }

    public void setAccessFrom(LocalDate accessFrom) {
        this.accessFrom = accessFrom;
    }

    public LocalDate getAccessTo() {
        return accessTo;
    }

    public void setAccessTo(LocalDate accessTo) {
        this.accessTo = accessTo;
    }
}
