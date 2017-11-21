package pl.javastart.mockitomailing;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.javastart.mockitomailing.model.Course;
import pl.javastart.mockitomailing.model.Signup;
import pl.javastart.mockitomailing.model.User;
import pl.javastart.mockitomailing.util.DateProvider;

import java.security.Signature;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class NotifierSenderTest {
    private NotifierSender notifierSender;

    @Mock
    private Database database;

    @Mock
    private MailSystem mailSystem;

    @Mock
    private DateProvider dateProvider;

    @Mock
    private User userM;



    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        final DateProvider today = Mockito.mock(DateProvider.class);
        Mockito.when(dateProvider.getCurrentDate()).thenReturn(LocalDate.of(2017,11,21));
    }

    @Test
    public void shouldSend1email() {

        //given
        Course course1 = new Course("Kurs1");
        User user1 = new User("Tomek", "t@t.pl");
        Signup signup1 = new Signup(user1, course1, LocalDate.of(2017, 02, 01), LocalDate.of(2017, 11, 22));

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        List<User> users = new ArrayList<>();
        users.add(user1);
        List<Signup> signups = new ArrayList<>();
        signups.add(signup1);
        when(database.getAllCourses()).thenReturn(courses);
        when(database.getAllSignups()).thenReturn(signups);
        when(database.getAllUsers()).thenReturn(users);

        notifierSender = new NotifierSender(mailSystem, database, dateProvider);

        //when
        notifierSender.prepareAndSendMails();

        //then
        Mockito.verify(mailSystem, Mockito.times(1)).sendEmail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }


    @Test
    public void shouldReturn1() {

        //given
        Course course1 = new Course("Kurs1");
        User user1 = new User("Tomek", "t@t.pl");
        Signup signup1 = new Signup(user1, course1, LocalDate.of(2017, 02, 01), LocalDate.of(2017, 11, 28));

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        List<User> users = new ArrayList<>();
        users.add(user1);
        List<Signup> signups = new ArrayList<>();
        signups.add(signup1);
        when(database.getAllCourses()).thenReturn(courses);
        when(database.getAllSignups()).thenReturn(signups);
        when(database.getAllUsers()).thenReturn(users);

        notifierSender = new NotifierSender(mailSystem, database, dateProvider);

        //when
        Map<Signup,Long> mapa = notifierSender.checkHowManyDaysLeftForOneUser(user1);

        //then
        Assert.assertThat(mapa.get(signup1), CoreMatchers.is(7L));
    }

    @Test
    public void shouldSend2emails() {

        //given
        List<Course> courses = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Signup> signups = new ArrayList<>();

        Course course1 = new Course("Kurs1");
        Course course2 = new Course("Kurs2");
        Course course3 = new Course("Kurs3");
        Course course4 = new Course("Kurs4");
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);

        User user1 = new User("Tomek", "t@t.pl");
        User user2 = new User("Maciej", "m@m.pl");
        User user3 = new User("Elvis", "e@e.pl");
        users.add(user1);
        users.add(user2);
        users.add(user3);

        Signup signup1 = new Signup(user1, course1, LocalDate.of(2017, 02, 01), LocalDate.of(2017, 11, 22));
        Signup signup2 = new Signup(user1, course2, LocalDate.of(2017, 10, 01), LocalDate.of(2017, 12, 30));
        Signup signup3 = new Signup(user1, course3, LocalDate.of(2017, 02, 01), LocalDate.of(2017, 11, 30));
        Signup signup4 = new Signup(user2, course1, LocalDate.of(2017, 02, 01), LocalDate.of(2017, 12, 21));
        signups.add(signup1);
        signups.add(signup2);
        signups.add(signup3);
        signups.add(signup4);

        when(database.getAllCourses()).thenReturn(courses);
        when(database.getAllSignups()).thenReturn(signups);
        when(database.getAllUsers()).thenReturn(users);

        notifierSender = new NotifierSender(mailSystem, database, dateProvider);

        //when
        notifierSender.prepareAndSendMails();

        //then
        Mockito.verify(mailSystem, Mockito.times(2)).sendEmail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void shouldReturn3() {

        //given
        List<Course> courses = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Signup> signups = new ArrayList<>();

        Course course1 = new Course("Kurs1");
        Course course2 = new Course("Kurs2");
        Course course3 = new Course("Kurs3");
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);

        User user1 = new User("Tomek", "t@t.pl");
        users.add(user1);

        Signup signup1 = new Signup(user1, course1, LocalDate.of(2017, 02, 01), LocalDate.of(2017, 11, 22));
        Signup signup2 = new Signup(user1, course2, LocalDate.of(2017, 10, 01), LocalDate.of(2017, 12, 30));
        Signup signup3 = new Signup(user1, course3, LocalDate.of(2017, 02, 01), LocalDate.of(2017, 11, 30));
        signups.add(signup1);
        signups.add(signup2);
        signups.add(signup3);;

        when(database.getAllCourses()).thenReturn(courses);
        when(database.getAllSignups()).thenReturn(signups);
        when(database.getAllUsers()).thenReturn(users);

        notifierSender = new NotifierSender(mailSystem, database, dateProvider);

        //when
        userM = Mockito.mock(User.class);
        int howManyCourses = notifierSender.forHowManyCoursesEnrolled(user1);

        //then
        Assert.assertThat(howManyCourses, CoreMatchers.is(3));
    }
}