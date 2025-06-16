package hr.algebra.dal.sql;

import hr.algebra.dal.BookAuthorRepository;
import hr.algebra.model.Author;
import hr.algebra.model.Book;
import hr.algebra.model.BookAuthor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import java.time.LocalDateTime;


public class BookAuthorRepositorySql implements BookAuthorRepository {

    private static final String BOOK_ID = "BookId";
    private static final String AUTHOR_ID = "AuthorId";

    private static final String ID_AUTHOR = "Id";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String DATE_BIRTH = "DateBirth";
    private static final String PICTURE_PATH = "PicturePath";
    
    private static final String ADD_BOOK_AUTHOR = "{ CALL addBookAuthor (?, ?) }";
    private static final String REMOVE_BOOK_AUTHOR = "{ CALL removeBookAuthor (?, ?) }";
    private static final String GET_AUTHORS_FOR_BOOK = "{ CALL getAuthorsForBook (?) }";
    private static final String GET_BOOKS_FOR_AUTHOR = "{ CALL getBooksForAuthor (?) }";

    @Override
    public void createBookAuthor(BookAuthor bookAuthor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(ADD_BOOK_AUTHOR)) {

            stmt.setInt(1, bookAuthor.getBookId());
            stmt.setInt(2, bookAuthor.getAuthorId());
            stmt.executeUpdate();
        }
    }

//    @Override
//    public void deleteAuthorsForBook(int bookId) throws Exception {
//        // Optional: ako želiš obrisati sve, moraš imati proceduru koja prima samo bookId
//        throw new UnsupportedOperationException("Use removeBookAuthor(BookAuthor) to delete specific author-book pair");
//    }

    @Override
    public void removeBookAuthor(BookAuthor bookAuthor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(REMOVE_BOOK_AUTHOR)) {

            stmt.setInt(1, bookAuthor.getBookId());
            stmt.setInt(2, bookAuthor.getAuthorId());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Author> selectAuthorsForBook(int bookId) throws Exception {
        List<Author> authors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(GET_AUTHORS_FOR_BOOK)) {

            stmt.setInt(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    authors.add(new Author(
                            rs.getInt(ID_AUTHOR),
                            rs.getString(FIRST_NAME),
                            rs.getString(LAST_NAME),
                            LocalDate.parse(rs.getString(DATE_BIRTH), Author.DATE_FORMATTER),
                            rs.getString(PICTURE_PATH)
                    ));
                }
            }
        }
        return authors;
    }

    @Override
    public List<Book> selectBooksForAuthor(int authorId) throws Exception {
        List<Book> books = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(GET_BOOKS_FOR_AUTHOR)) {

            stmt.setInt(AUTHOR_ID, authorId);
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
