/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.BookUserRepository;
import hr.algebra.model.Book;
import hr.algebra.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;



public class BookUserRepositorySql implements BookUserRepository {

     private static final String ADD_BOOK_USER = "{ CALL addBookUser(?, ?) }";
    private static final String REMOVE_BOOK_USER = "{ CALL removeBookUser(?, ?) }";
    private static final String GET_USERS_FOR_BOOK = "{ CALL getUsersForBook(?) }";
    private static final String GET_BOOKS_FOR_USER = "{ CALL getBooksForUser(?) }";

    @Override
    public void addBookUser(int bookId, int userId) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             CallableStatement stmt = con.prepareCall(ADD_BOOK_USER)) {

            stmt.setInt(1, bookId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    @Override
    public void removeBookUser(int bookId, int userId) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             CallableStatement stmt = con.prepareCall(REMOVE_BOOK_USER)) {

            stmt.setInt(1, bookId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<User> getUsersForBook(int bookId) throws Exception {
        List<User> users = new ArrayList<>();

        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             CallableStatement stmt = con.prepareCall(GET_USERS_FOR_BOOK)) {

            stmt.setInt(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(
                            rs.getInt("Id"),
                            rs.getString("Username"),
                            rs.getString("Password"),
                            rs.getString("Role")
                    ));
                }
            }
        }

        return users;
    }

    @Override
    public List<Book> getBooksForUser(int userId) throws Exception {
        List<Book> books = new ArrayList<>();

        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             CallableStatement stmt = con.prepareCall(GET_BOOKS_FOR_USER)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(new Book(
                            rs.getInt("Id"),
                            rs.getString("Title"),
                            rs.getString("Description"),
                            rs.getString("Link"),
                            LocalDateTime.parse(rs.getString("PublishedDate"), Book.DATE_FORMATTER),
                            rs.getString("PicturePath")
                    ));
                }
            }
        }

        return books;
    }
    
}
