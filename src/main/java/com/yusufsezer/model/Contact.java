package com.yusufsezer.model;

import java.io.Serializable;

public class Contact implements Serializable {

    public final static String ID = "id";
    public final static String FIRST_NAME = "firstName";
    public final static String LAST_NAME = "lastName";
    public final static String EMAIL = "email";
    public final static String PHONE_NUMBER = "phoneNumber";
    public final static String ADDRESS = "address";
    public final static String WEB_ADDRESS = "webAddress";
    public final static String NOTES = "notes";
    public final static Contact EMPTY = Contact.of("", "", "", "", "", "", "");

    protected long id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String address;
    protected String webAddress;
    protected String notes;

    public Contact() {
    }

    public Contact(int id, String firstName, String lastName, String email,
            String phoneNumber, String address, String webAddress, String notes) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.webAddress = webAddress;
        this.notes = notes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public static Contact of(String firstName, String lastName, String email,
            String phoneNumber, String address, String webAddress, String notes) {
        Contact newContact = new Contact();
        newContact.setFirstName(firstName);
        newContact.setLastName(lastName);
        newContact.setEmail(email);
        newContact.setPhoneNumber(phoneNumber);
        newContact.setAddress(address);
        newContact.setWebAddress(webAddress);
        newContact.setNotes(notes);
        return newContact;
    }

}
