package hr.algebra.dal.sql;

import hr.algebra.dal.BookRepository;
import hr.algebra.model.Book;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

public class BookRepositorySql implements BookRepository {

    private static final String ID_BOOK = "Id";
    private static final String TITLE = "Title";
    private static final String DESCRIPTION = "Description";
    private static final String LINK = "Link";
    private static final String PUBLISHED_DATE = "PublishedDate";
    private static final String PICTURE_PATH = "PicturePath";
    private static final String PUBLISHER_ID = "PublisherId";
    private static final String GENRE_ID = "GenreId";

    private static final String CREATE_BOOK = "{ CALL createBook (?, ?, ?, ?, ?, ?) }";
    private static final String UPDATE_BOOK = "{ CALL updateBook (?, ?, ?, ?, ?, ?) }";
    private static final String DELETE_BOOK = "{ CALL deleteBook (?) }";
    private static final String SELECT_BOOK = "{ CALL selectBook (?) }";
    private static final String SELECT_BOOKS = "{ CALL selectBooks }";

    @Override
    public int createBook(Book book) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_BOOK)) {

            stmt.setString(TITLE, book.getTitle());
            stmt.setString(DESCRIPTION, book.getDescription());
            stmt.setString(LINK, book.getLink());
            stmt.setString(PUBLISHED_DATE, book.getPublishedDate().format(Book.DATE_FORMATTER));
            stmt.setString(PICTURE_PATH, book.getPicturePath());
            stmt.registerOutParameter(ID_BOOK, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID_BOOK);
        }
    }

    @Override
    public void updateBook(int id, Book book) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_BOOK)) {

            stmt.setInt(ID_BOOK, id);
            stmt.setString(TITLE, book.getTitle());
            stmt.setString(DESCRIPTION, book.getDescription());
            stmt.setString(LINK, book.getLink());
            stmt.setString(PUBLISHED_DATE, book.getPublishedDate().format(Book.DATE_FORMATTER));
            stmt.setString(PICTURE_PATH, book.getPicturePath());

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteBook(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_BOOK)) {

            stmt.setInt(ID_BOOK, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Book> selectBook(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_BOOK)) {

            stmt.setInt(ID_BOOK, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Book(
                            rs.getInt(ID_BOOK),
                            rs.getString(TITLE),
                            rs.getString(DESCRIPTION),
                            rs.getString(LINK),
                            LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Book.DATE_FORMATTER),
                            rs.getString(PICTURE_PATH)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Book> selectBooks() throws Exception {
        List<Book> books = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_BOOKS); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(new Book(
                        rs.getInt(ID_BOOK),
                        rs.getString(TITLE),
                        rs.getString(DESCRIPTION),
                        rs.getString(LINK),
                        LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Book.DATE_FORMATTER),
                        rs.getString(PICTURE_PATH)
                ));
            }
        }
        return books;
    }

    @Override
    public void createBooks(List<Book> books) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_BOOK)) {

            for (Book book : books) {
                stmt.setString(TITLE, book.getTitle());
                stmt.setString(LINK, book.getLink());
                stmt.setString(DESCRIPTION, book.getDescription());
                stmt.setString(PICTURE_PATH, book.getPicturePath());
                stmt.setString(PUBLISHED_DATE, book.getPublishedDate().format(Book.DATE_FORMATTER));
                stmt.registerOutParameter(ID_BOOK, Types.INTEGER);

                stmt.executeUpdate();
            }
        }
    }
}
