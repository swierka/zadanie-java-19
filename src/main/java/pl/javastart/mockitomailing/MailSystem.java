package pl.javastart.mockitomailing;

public interface MailSystem {

    void sendEmail(String emailAddress, String title, String text);

}
