package hr.algebra.dal.sql;

import hr.algebra.dal.BookGenreRepository;
import hr.algebra.model.Book;
import hr.algebra.model.Genre;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookGenreRepositorySql implements BookGenreRepository {

    private static final String BOOK_ID = "BookId";
    private static final String GENRE_ID = "GenreId";

    private static final String ADD_BOOK_GENRE = "{ CALL addBookGenre(?, ?) }";
    private static final String REMOVE_BOOK_GENRE = "{ CALL removeBookGenre(?, ?) }";
    private static final String GET_GENRES_FOR_BOOK = "{ CALL getGenresForBook(?) }";
    private static final String GET_BOOKS_FOR_GENRE = "{ CALL getBooksForGenre(?) }";

    @Override
    public void addBookGenre(int bookId, int genreId) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             CallableStatement stmt = con.prepareCall(ADD_BOOK_GENRE)) {

            stmt.setInt(BOOK_ID, bookId);
            stmt.setInt(GENRE_ID, genreId);

            stmt.executeUpdate();
        }
    }

    @Override
    public void removeBookGenre(int bookId, int genreId) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             CallableStatement stmt = con.prepareCall(REMOVE_BOOK_GENRE)) {

            stmt.setInt(BOOK_ID, bookId);
            stmt.setInt(GENRE_ID, genreId);

            stmt.executeUpdate();
        }
    }

    @Override
    public List<Genre> getGenresForBook(int bookId) throws Exception {
        List<Genre> genres = new ArrayList<>();

        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             CallableStatement stmt = con.prepareCall(GET_GENRES_FOR_BOOK)) {

            stmt.setInt(BOOK_ID, bookId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    genres.add(new Genre(
                            rs.getInt("Id"),
                            rs.getString("Name")
                    ));
                }
            }
        }

        return genres;
    }

    @Override
    public List<Book> getBooksForGenre(int genreId) throws Exception {
        List<Book> books = new ArrayList<>();

        try (Connection con = DataSourceSingleton.getInstance().getConnection();
             CallableStatement stmt = con.prepareCall(GET_BOOKS_FOR_GENRE)) {

            stmt.setInt(GENRE_ID, genreId);

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
