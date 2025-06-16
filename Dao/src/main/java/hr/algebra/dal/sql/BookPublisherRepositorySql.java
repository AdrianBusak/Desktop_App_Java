/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.BookPublisherRepository;
import hr.algebra.model.Book;
import hr.algebra.model.Publisher;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class BookPublisherRepositorySql implements BookPublisherRepository {

    private static final String BOOK_ID = "BookId";
    private static final String PUBLISHER_ID = "PublisherId";
    
    private static final String ID_PUBLISHER = "Id";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String DATE_BIRTH = "DateBirth";
    private static final String PICTURE_PATH = "PicturePath";

    private static final String ADD_BOOK_PUBLISHER = "{ CALL addBookPublisher(?, ?) }";
    private static final String REMOVE_BOOK_PUBLISHER = "{ CALL removeBookPublisher(?, ?) }";
    private static final String GET_PUBLISHERS_FOR_BOOK = "{ CALL getPublishersForBook(?) }";
    private static final String GET_BOOKS_FOR_PUBLISHER = "{ CALL getBooksForPublisher(?) }";

    @Override
    public void addBookPublisher(int bookId, int publisherId) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection(); CallableStatement stmt = con.prepareCall(ADD_BOOK_PUBLISHER)) {

            stmt.setInt(BOOK_ID, bookId);
            stmt.setInt(PUBLISHER_ID, publisherId);

            stmt.executeUpdate();
        }
    }

    @Override
    public void removeBookPublisher(int bookId, int publisherId) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection(); CallableStatement stmt = con.prepareCall(REMOVE_BOOK_PUBLISHER)) {

            stmt.setInt(BOOK_ID, bookId);
            stmt.setInt(PUBLISHER_ID, publisherId);

            stmt.executeUpdate();
        }
    }

    @Override
    public List<Publisher> getPublishersForBook(int bookId) throws Exception {
        List<Publisher> publishers = new ArrayList<>();

        try (Connection con = DataSourceSingleton.getInstance().getConnection(); CallableStatement stmt = con.prepareCall(GET_PUBLISHERS_FOR_BOOK)) {

            stmt.setInt(BOOK_ID, bookId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    publishers.add(new Publisher(
                            rs.getInt(ID_PUBLISHER),
                            rs.getString(FIRST_NAME),
                            rs.getString(LAST_NAME),
                            LocalDate.parse(rs.getString(DATE_BIRTH), Publisher.DATE_FORMATTER),
                            rs.getString(PICTURE_PATH)
                    ));
                }
            }
        }

        return publishers;
    }

    @Override
    public List<Book> getBooksForPublisher(int publisherId) throws Exception {
        List<Book> books = new ArrayList<>();

        try (Connection con = DataSourceSingleton.getInstance().getConnection(); CallableStatement stmt = con.prepareCall(GET_BOOKS_FOR_PUBLISHER)) {

            stmt.setInt(PUBLISHER_ID, publisherId);

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
