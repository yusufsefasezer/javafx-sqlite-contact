package com.yusufsezer.controller;

import com.yusufsezer.contract.IRepository;
import com.yusufsezer.model.Contact;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ContactController implements Initializable {

    private final IRepository<Contact, Integer> contactRepository;

    @FXML
    private ListView<Contact> listViewContactList;

    @FXML
    private TextField textFieldFirstName, textFieldLastName, textFieldEmail, textFieldPhoneNumber, textFieldWebAddress;

    @FXML
    private TextArea textAreaAddress, textAreaNotes;

    public ContactController(IRepository<Contact, Integer> repository) {
        this.contactRepository = repository;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listViewContactList.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }
                    textFieldFirstName.setText(newValue.getFirstName());
                    textFieldLastName.setText(newValue.getLastName());
                    textFieldEmail.setText(newValue.getEmail());
                    textFieldPhoneNumber.setText(newValue.getPhoneNumber());
                    textAreaAddress.setText(newValue.getAddress());
                    textFieldWebAddress.setText(newValue.getWebAddress());
                    textAreaNotes.setText(newValue.getNotes());
                });
        fillListItems();
    }

    @FXML
    private void addContact(ActionEvent event) {
        Contact newContact = Contact.of(
                textFieldFirstName.getText(),
                textFieldLastName.getText(),
                textFieldEmail.getText(),
                textFieldPhoneNumber.getText(),
                textAreaAddress.getText(),
                textFieldWebAddress.getText(),
                textAreaNotes.getText());
        contactRepository.add(newContact);
        fillListItems();
        clearInput();
    }

    @FXML
    private void updateContact(ActionEvent event) {
        Contact selectedContact = listViewContactList.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            return;
        }
        selectedContact.setFirstName(textFieldFirstName.getText());
        selectedContact.setLastName(textFieldLastName.getText());
        selectedContact.setEmail(textFieldEmail.getText());
        selectedContact.setPhoneNumber(textFieldPhoneNumber.getText());
        selectedContact.setAddress(textAreaAddress.getText());
        selectedContact.setWebAddress(textFieldWebAddress.getText());
        selectedContact.setNotes(textAreaNotes.getText());
        int contactId = selectedContact.getId();
        contactRepository.update(contactId, selectedContact);
        fillListItems();
        clearInput();
    }

    @FXML
    private void removeContact(ActionEvent event) {
        Contact selectedContact = listViewContactList.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            return;
        }
        int contactId = selectedContact.getId();
        contactRepository.remove(contactId);
        fillListItems();
        clearInput();
    }

    private void clearInput() {
        textFieldFirstName.clear();
        textFieldLastName.clear();
        textFieldEmail.clear();
        textFieldPhoneNumber.clear();
        textAreaAddress.clear();
        textFieldWebAddress.clear();
        textAreaNotes.clear();
    }

    private void fillListItems() {
        listViewContactList.getItems().clear();
        List<Contact> contacts = contactRepository.getAll();
        listViewContactList.getItems().addAll(contacts);
    }

}
