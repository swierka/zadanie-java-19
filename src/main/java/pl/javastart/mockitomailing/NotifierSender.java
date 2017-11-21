package pl.javastart.mockitomailing;

import pl.javastart.mockitomailing.model.Course;
import pl.javastart.mockitomailing.model.Signup;
import pl.javastart.mockitomailing.model.User;
import pl.javastart.mockitomailing.util.DateProvider;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NotifierSender {

    private MailSystem mailSystem;
    private Database database;
    private DateProvider dateProvider;

    public NotifierSender(MailSystem mailSystem, Database database, DateProvider dateProvider) {
        this.mailSystem = mailSystem;
        this.database = database;
        this.dateProvider = dateProvider;
    }

    public void prepareAndSendMails() {

        Map<Signup, Long> timeTillEndOneUserCourseToNotify = new HashMap<>(); /* mapa z kursami, ktore koncza sie za 1d, 7d, 1m, 3m, 6m*/

        for (User user : database.getAllUsers()) {
            Set<Map.Entry<Signup, Long>> entries = checkHowManyDaysLeftForOneUser(user).entrySet();
            for (Map.Entry<Signup, Long> entry : entries) {
                Signup signup = entry.getKey();
                long days = entry.getValue();
                if (days == 1 || days == 7 || days == 30 || days == 90||days==180) {
                    mailSystem.sendEmail(user.getEmailAdress(), "Niedługo kończy się Twój dostęp do naszego kursu!", message(days, user, signup.getCourse()));
                    System.out.println(message(days, user, signup.getCourse()));
                    break;
                }
            }
        }
    }

    public String message(Long days, User user, Course course) {

        String m1 = null;
        String message = null;

        StringBuilder sb = new StringBuilder();


        Long daysOther = 0L;
         if(forHowManyCoursesEnrolled(user)>1) {
             for (int i = 0; i < database.getAllSignups().size(); i++) {
                 Set<Map.Entry<Signup, Long>> entries = checkHowManyDaysLeftForOneUser(user).entrySet();
                 for (Map.Entry<Signup, Long> entry : entries) {
                     Signup signup = entry.getKey();
                     if (database.getAllSignups().get(i).getCourse().equals(signup.getCourse()) && !database.getAllSignups().get(i).getCourse().equals(course)) {
                         daysOther = entry.getValue();
                         sb.append("\n \u2022" + database.getAllSignups().get(i).getCourse().toString() + " - " + dayTransformator(daysOther));
                     }
                 }
             } m1 = "Dostęp do Twoich pozostałych kursów: " + sb;
         } else m1="";

        message = "Cześć\n" + user + ",\nza "+dayTransformator(days) + " kończy Ci się dostęp do kursu " + course +
                ". Wykorzystaj maksymalnie ten czas! \n" + m1 + sb + "\nPozdrawiamy";
        
        return message;
    }


    //metoda zwracajace mape z zapisami(Signup) konkretnego uzytkownika (User)i liczba dni do konca kursu;
    public Map<Signup, Long> checkHowManyDaysLeftForOneUser(User user) {

        LocalDate today = dateProvider.getCurrentDate();
        Map<Signup, Long> timeTillEndOneUser = new HashMap<>();

        for (int i = 0; i < database.getAllSignups().size(); i++) {
            for (int j = 0; j < database.getAllCourses().size(); j++) {

                if (database.getAllSignups().get(i).getUser().equals(user)) {
                    long daysBetween = ChronoUnit.DAYS.between(today, database.getAllSignups().get(i).getAccessTo());
                    timeTillEndOneUser.put(database.getAllSignups().get(i), daysBetween);
                }
            }
        }
        return timeTillEndOneUser;
    }

    public int forHowManyCoursesEnrolled(User user){
        int courseCount=0;

        for(int i =0; i < database.getAllSignups().size(); i++){
            if(database.getAllSignups().get(i).getUser().equals(user)){
                courseCount++;
            }
        }

        return courseCount;
    }


   //zamienia liczbe dni na odpowiedniego Stringa
    public String dayTransformator(Long days) {

        if (days == 1) {
            return days + " dzień";
        } else if (days>1 && days <=29){
            return days + " dni";
        } else if (days == 7) {
            return "tydzień";
        } else if (days == 30) {
            return "miesiąc";
        } else if (days > 30 && days < 60){
            return "1 miesiąc i " + (days%30)+" dni";
        } else if (days==60 || days == 90 || days== 120){
            return days/30 + " miesiące";
        } else if (days==150 || days == 180 || days == 210){return days/30+" miesięcy";}
        else if(days>60 && days < 150) {
            return (days / 30) + "miesiące i " + (days % 30) + " dni";
        }
        else {
            return (days / 30) + "miesiący i " + (days % 30) + " dni";
        }
    }
}
