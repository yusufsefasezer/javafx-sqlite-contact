package com.yusufsezer.repository;

import com.yusufsezer.contract.IRepository;
import com.yusufsezer.model.Contact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactRepository implements IRepository<Contact, Long> {

    private final Connection connection;

    public ContactRepository(Connection connection) {
        this.connection = connection;
        createTableIfNotExists();
    }

    @Override
    public Contact get(Long index) {
        Contact foundContact = Contact.EMPTY;

        try (PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM contacts WHERE id = ?")) {
            prepareStatement.setLong(1, index);
            ResultSet resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                foundContact = mapResultToContact(resultSet);
            }
        } catch (SQLException exception) {
            logException(exception);
        }
        return foundContact;
    }

    @Override
    public List<Contact> getAll() {
        List<Contact> contacts = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM contacts");
            while (resultSet.next()) {
                contacts.add(mapResultToContact(resultSet));
            }
        } catch (Exception exception) {
            logException(exception);
        }
        return contacts;
    }

    @Override
    public boolean add(Contact contact) {
        boolean result = false;

        String contactInsertQuery = "INSERT INTO contacts VALUES(NULL, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(contactInsertQuery)) {
            preparedStatement.setString(1, contact.getFirstName());
            preparedStatement.setString(2, contact.getLastName());
            preparedStatement.setString(3, contact.getEmail());
            preparedStatement.setString(4, contact.getPhoneNumber());
            preparedStatement.setString(5, contact.getAddress());
            preparedStatement.setString(6, contact.getWebAddress());
            preparedStatement.setString(7, contact.getNotes());
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            logException(exception);
        }
        return result;
    }

    @Override
    public Contact update(Long index, Contact contact) {
        Contact foundContact = Contact.EMPTY;

        String contactUpdateQuery = "UPDATE contacts SET "
                + Contact.FIRST_NAME + " = ?, "
                + Contact.LAST_NAME + " = ?, "
                + Contact.EMAIL + " = ?, "
                + Contact.PHONE_NUMBER + " = ?, "
                + Contact.ADDRESS + " = ?, "
                + Contact.WEB_ADDRESS + " = ?, "
                + Contact.NOTES + " = ? "
                + "WHERE " + Contact.ID + " = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(contactUpdateQuery)) {
            preparedStatement.setString(1, contact.getFirstName());
            preparedStatement.setString(2, contact.getLastName());
            preparedStatement.setString(3, contact.getEmail());
            preparedStatement.setString(4, contact.getPhoneNumber());
            preparedStatement.setString(5, contact.getAddress());
            preparedStatement.setString(6, contact.getWebAddress());
            preparedStatement.setString(7, contact.getNotes());
            preparedStatement.setLong(8, index);
            boolean updated = preparedStatement.executeUpdate() > 0;
            if (updated) {
                foundContact = get(index);
            }
        } catch (SQLException exception) {
            logException(exception);
        }
        return foundContact;
    }

    @Override
    public Contact remove(Long index) {
        Contact contactToRemove = get(index);

        if (Contact.EMPTY != contactToRemove) {
            String contactDeleteQuery = "DELETE FROM contacts WHERE " + Contact.ID + "= ?";
            try (PreparedStatement prepareStatement = connection.prepareStatement(contactDeleteQuery)) {
                prepareStatement.setLong(1, index);
                prepareStatement.executeUpdate();
            } catch (SQLException exception) {
                logException(exception);
            }
        }
        return contactToRemove;
    }

    private void createTableIfNotExists() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("SELECT * FROM contacts");
        } catch (SQLException ex) {
            createTable();
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE contacts ("
                + Contact.ID + " integer primary key,"
                + Contact.FIRST_NAME + " VARCHAR,"
                + Contact.LAST_NAME + " VARCHAR,"
                + Contact.EMAIL + " VARCHAR,"
                + Contact.PHONE_NUMBER + " VARCHAR,"
                + Contact.ADDRESS + " VARCHAR,"
                + Contact.WEB_ADDRESS + " VARCHAR,"
                + Contact.NOTES + "  VARCHAR)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException exception) {
            logException(exception);
            System.exit(0);
        }
    }

    private Contact mapResultToContact(ResultSet resultSet) throws SQLException {
        Contact foundContact = Contact.of(
                resultSet.getString(Contact.FIRST_NAME),
                resultSet.getString(Contact.LAST_NAME),
                resultSet.getString(Contact.EMAIL),
                resultSet.getString(Contact.PHONE_NUMBER),
                resultSet.getString(Contact.ADDRESS),
                resultSet.getString(Contact.WEB_ADDRESS),
                resultSet.getString(Contact.NOTES)
        );
        foundContact.setId(resultSet.getLong(Contact.ID));
        return foundContact;
    }

    private void logException(Exception ex) {
        Logger.getLogger(ContactRepository.class.getName())
                .log(Level.SEVERE, null, ex);
    }

}
