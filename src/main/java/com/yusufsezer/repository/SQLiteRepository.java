package com.yusufsezer.repository;

import com.yusufsezer.contract.IRepository;
import com.yusufsezer.model.Contact;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteRepository<T, I extends Number> implements IRepository<T, I> {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public SQLiteRepository(String source) {
        try {
            connection = DriverManager.getConnection(source);
            statement = connection.createStatement();
            statement.execute("SELECT * FROM contacts");
        } catch (SQLException ex) {
            createTable();
        }
    }

    @Override
    public T get(I index) {
        T item = null;
        String sqlQuery = String.format("SELECT * FROM contacts WHERE id = %d", index.intValue());
        try {
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                item = (T) new Contact(resultSet.getInt(Contact.ID),
                        resultSet.getString(Contact.FIRST_NAME),
                        resultSet.getString(Contact.LAST_NAME),
                        resultSet.getString(Contact.EMAIL),
                        resultSet.getString(Contact.PHONE_NUMBER),
                        resultSet.getString(Contact.ADDRESS),
                        resultSet.getString(Contact.WEB_ADDRESS),
                        resultSet.getString(Contact.NOTES));
            }
        } catch (SQLException ex) {
            logException(ex);
            return item;
        }
        return item;
    }

    @Override
    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        String sqlQuery = "SELECT * FROM contacts";
        try {
            resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                Contact c = new Contact(resultSet.getInt(Contact.ID),
                        resultSet.getString(Contact.FIRST_NAME),
                        resultSet.getString(Contact.LAST_NAME),
                        resultSet.getString(Contact.EMAIL),
                        resultSet.getString(Contact.PHONE_NUMBER),
                        resultSet.getString(Contact.ADDRESS),
                        resultSet.getString(Contact.WEB_ADDRESS),
                        resultSet.getString(Contact.NOTES));
                list.add((T) c);
            }
        } catch (SQLException ex) {
            logException(ex);
            return list;
        }
        return list;
    }

    @Override
    public boolean add(T obj) {
        boolean result = false;
        Contact contact = (Contact) obj;
        String query = String.format("INSERT INTO contacts VALUES(NULL, '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhoneNumber(),
                contact.getAddress(),
                contact.getWebAddress(),
                contact.getNotes());
        try {
            result = statement.execute(query);
        } catch (SQLException ex) {
            logException(ex);
            return result;
        }
        return result;
    }

    @Override
    public T update(I index, T obj) {
        T updatedItem = get(index);
        Contact contact = (Contact) obj;
        String query = String.format("UPDATE contacts SET "
                + Contact.FIRST_NAME + " = '%s', "
                + Contact.LAST_NAME + " = '%s', "
                + Contact.EMAIL + " = '%s', "
                + Contact.PHONE_NUMBER + " = '%s', "
                + Contact.ADDRESS + " = '%s', "
                + Contact.WEB_ADDRESS + " = '%s', "
                + Contact.NOTES + " = '%s' "
                + "WHERE id = %d",
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhoneNumber(),
                contact.getAddress(),
                contact.getWebAddress(),
                contact.getNotes(),
                index.intValue());

        try {
            updatedItem = statement.execute(query) ? updatedItem : obj;
        } catch (SQLException ex) {
            logException(ex);
            return updatedItem;
        }
        return updatedItem;
    }

    @Override
    public T remove(I index) {
        String query = String.format("DELETE FROM contacts WHERE id = %d", index.intValue());
        T deletedItem = get(index);
        try {
            statement.execute(query);
        } catch (SQLException ex) {
            logException(ex);
        }
        return deletedItem;
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
        try {
            statement.execute(sql);
        } catch (SQLException ex) {
            logException(ex);
            System.exit(-1);
        }
    }

    private void logException(SQLException ex) {
        Logger.getLogger(SQLiteRepository.class.getName())
                .log(Level.SEVERE, null, ex);
    }

}
