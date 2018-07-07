package karmachallenge.com.karmapay.ui.register;

public class User {
    private String uid;
    private String firstName;
    private String lastName;
    private String contact;

    public User(String uid, String firstName, String lastName, String contact) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
