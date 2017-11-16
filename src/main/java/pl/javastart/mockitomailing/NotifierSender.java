package pl.javastart.mockitomailing;

import pl.javastart.mockitomailing.util.DateProvider;

import java.time.LocalDate;

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
        LocalDate today = dateProvider.getCurrentDate();
        // TODO dodaj implementację

        mailSystem.sendEmail("", "", "");
    }


}
