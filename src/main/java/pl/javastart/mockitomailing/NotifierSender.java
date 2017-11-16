package pl.javastart.mockitomailing;

public class NotifierSender {

    private MailSystem mailSystem;
    private Database database;

    public NotifierSender(MailSystem mailSystem, Database database) {
        this.mailSystem = mailSystem;
        this.database = database;
    }

    public void prepareAndSendMails() {
        // TODO dodaj implementajcę

        mailSystem.sendEmail("", "", "");
    }


}
