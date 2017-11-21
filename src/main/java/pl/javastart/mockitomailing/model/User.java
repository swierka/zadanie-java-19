package pl.javastart.mockitomailing.model;

public class User {

    private String name;
    private String emailAdress;

    public User(String name, String emailAdress) {
        this.name = name;
        this.emailAdress = emailAdress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    @Override
    public String toString() {
        return name;
    }
}
