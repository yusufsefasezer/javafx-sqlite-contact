package com.yusufsezer.controller;

import com.yusufsezer.contracts.IRepository;
import com.yusufsezer.model.Contact;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class JFXCController implements Initializable {

	private final IRepository<Contact, Integer> repository;

	@FXML
	private ListView<Contact> listViewContactList;

	@FXML
	private TextField textFieldFirstName,
					textFieldLastName,
					textFieldEmail,
					textFieldPhoneNumber,
					textFieldWebAddress;

	@FXML
	private TextArea textAreaAddress,
					textAreaNotes;

	public JFXCController(IRepository<Contact, Integer> repository) {
		this.repository = repository;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		listViewContactList
						.getSelectionModel()
						.selectedItemProperty()
						.addListener(new ChangeListener<Contact>() {
							@Override
							public void changed(
											ObservableValue<? extends Contact> observable,
											Contact oldValue,
											Contact newValue) {
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
							}
						});
		fillListItems();
	}

	@FXML
	private void addContact(ActionEvent event) {
		Contact c = new Contact(0,
						textFieldFirstName.getText(),
						textFieldLastName.getText(),
						textFieldEmail.getText(),
						textFieldPhoneNumber.getText(),
						textAreaAddress.getText(),
						textFieldWebAddress.getText(),
						textAreaNotes.getText());
		repository.add(c);
		fillListItems();
		clearInput();
	}

	@FXML
	private void updateContact(ActionEvent event) {
		Contact selectedContact = listViewContactList.getSelectionModel()
						.getSelectedItem();
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
		repository.update(selectedContact.getId(), selectedContact);
		fillListItems();
		clearInput();
	}

	@FXML
	private void removeContact(ActionEvent event) {
		Contact selectedContact = listViewContactList.getSelectionModel()
						.getSelectedItem();
		if (selectedContact == null) {
			return;
		}
		repository.remove(selectedContact.getId());
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
		listViewContactList.getItems()
						.addAll(repository.getAll());
	}

}
