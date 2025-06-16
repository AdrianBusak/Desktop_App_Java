package hr.algebra.dal.sql;

import hr.algebra.dal.AuthorRepository;
import hr.algebra.model.Author;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public class AuthorRepositorySql implements AuthorRepository {

    private static final String ID_AUTHOR = "Id";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String DATE_BIRTH = "DateBirth";
    private static final String PICTURE_PATH = "PicturePath";

    private static final String CREATE_AUTHOR = "{ CALL createAuthor(?, ?, ?, ?, ?) }";
    private static final String CREATE_AUTHORS = "{ CALL createAuthor(?, ?, ?, ?, ?) }";
    private static final String UPDATE_AUTHOR = "{ CALL updateAuthor(?, ?, ?, ?, ?) }";
    private static final String DELETE_AUTHOR = "{ CALL deleteAuthor(?) }";
    private static final String SELECT_AUTHOR = "{ CALL selectAuthor(?) }";
    private static final String SELECT_AUTHORS = "{ CALL selectAuthors }";

    @Override
    public int createAuthor(Author author) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_AUTHOR)) {

            stmt.setString(FIRST_NAME, author.getFirstName());
            stmt.setString(LAST_NAME, author.getLastName());
            stmt.setString(DATE_BIRTH, author.getDateBirth().format(Author.DATE_FORMATTER));
            stmt.setString(PICTURE_PATH, author.getPicturePath());
            stmt.registerOutParameter(ID_AUTHOR, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID_AUTHOR);
        }
    }

    @Override
    public void createAuthors(List<Author> authors) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_AUTHORS)) {

            for (Author author : authors) {
                stmt.setString(FIRST_NAME, author.getFirstName());
                stmt.setString(LAST_NAME, author.getLastName());
                stmt.setString(DATE_BIRTH, author.getDateBirth().format(Author.DATE_FORMATTER));
                stmt.setString(PICTURE_PATH, author.getPicturePath());
                stmt.registerOutParameter(ID_AUTHOR, Types.INTEGER);

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void updateAuthor(int id, Author data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_AUTHOR)) {

            stmt.setInt(ID_AUTHOR, id);
            stmt.setString(FIRST_NAME, data.getFirstName());
            stmt.setString(LAST_NAME, data.getLastName());
            stmt.setString(DATE_BIRTH, data.getDateBirth().format(Author.DATE_FORMATTER));
            stmt.setString(PICTURE_PATH, data.getPicturePath());

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteAuthor(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_AUTHOR)) {

            stmt.setInt(ID_AUTHOR, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Author> selectAuthor(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_AUTHOR)) {

            stmt.setInt(ID_AUTHOR, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Author(
                            rs.getInt(ID_AUTHOR),
                            rs.getString(FIRST_NAME),
                            rs.getString(LAST_NAME),
                            LocalDate.parse(rs.getString(DATE_BIRTH), Author.DATE_FORMATTER),
                            rs.getString(PICTURE_PATH)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Author> selectAuthors() throws Exception {
        List<Author> authors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_AUTHORS); ResultSet rs = stmt.executeQuery()) {

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
        return authors;
    }
}
