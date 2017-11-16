package pl.javastart.mockitomailing;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import pl.javastart.mockitomailing.model.Course;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class NotifierSenderTest {

    @Mock
    Database database;

    @Before
    public void init() {
        Course javaCourse = new Course("Java Podstawy");
        Course springCourse = new Course("Kurs Spring");

        List<Course> courses = new ArrayList<>();

        when(database.getAllCourses()).thenReturn(courses);
    }

    @Test
    public void shouldDoSth() {

    }

}