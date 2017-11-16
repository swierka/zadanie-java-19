package pl.javastart.mockitomailing;

import pl.javastart.mockitomailing.model.Course;
import pl.javastart.mockitomailing.model.Signup;
import pl.javastart.mockitomailing.model.User;

import java.util.List;

public interface Database {

    List<User> getAllUsers();
    List<Signup> getAllSignups();
    List<Course> getAllCourses();


}
