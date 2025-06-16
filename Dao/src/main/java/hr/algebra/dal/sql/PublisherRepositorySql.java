/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.PublisherRepository;
import hr.algebra.model.Publisher;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import static javax.swing.text.html.HTML.Attribute.NAME;

/**
 *
 * @author AdrianBusak
 */
public class PublisherRepositorySql implements PublisherRepository {

    private static final String ID_PUBLISHER = "Id";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String DATE_BIRTH = "DateBirth";
    private static final String PICTURE_PATH = "PicturePath";

    private static final String CREATE_PUBLISHER = "{ CALL createPublisher(?, ?, ?, ?, ?) }";
    private static final String UPDATE_PUBLISHER = "{ CALL updatePublisher(?, ?, ?, ?, ?) }";
    private static final String DELETE_PUBLISHER = "{ CALL deletePublisher(?) }";
    private static final String SELECT_PUBLISHER = "{ CALL selectPublisher(?) }";
    private static final String SELECT_PUBLISHERS = "{ CALL selectPublishers }";

    @Override
    public int createPublisher(Publisher publisher) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_PUBLISHER)) {

            stmt.setString(FIRST_NAME, publisher.getFirstName());
            stmt.setString(LAST_NAME, publisher.getLastName());
            stmt.setString(DATE_BIRTH, publisher.getDateBirth().format(Publisher.DATE_FORMATTER));
            stmt.setString(PICTURE_PATH, publisher.getPicturePath());
            stmt.registerOutParameter(ID_PUBLISHER, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(ID_PUBLISHER);

        }
    }

    @Override
    public void createPublishers(List<Publisher> publishers) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_PUBLISHER)) {
            for (Publisher publisher : publishers) {
                stmt.setString(FIRST_NAME, publisher.getFirstName());
                stmt.setString(LAST_NAME, publisher.getLastName());
                stmt.setString(DATE_BIRTH, publisher.getDateBirth().format(Publisher.DATE_FORMATTER));
                stmt.setString(PICTURE_PATH, publisher.getPicturePath());
            }

        }
    }

    @Override
    public void updatePublisher(int id, Publisher data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_PUBLISHER)) {
            stmt.setInt(ID_PUBLISHER, id);
            stmt.setString(FIRST_NAME, data.getFirstName());
            stmt.setString(LAST_NAME, data.getLastName());
            stmt.setString(DATE_BIRTH, data.getDateBirth().format(Publisher.DATE_FORMATTER));
            stmt.setString(PICTURE_PATH, data.getPicturePath());

            stmt.executeUpdate();
        }
    }

    @Override
    public void deletePublisher(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (
                Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_PUBLISHER)) {
            stmt.setInt(ID_PUBLISHER, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Publisher> selectPublisher(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (
                Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_PUBLISHER)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Publisher(
                            rs.getInt(ID_PUBLISHER),
                            rs.getString(FIRST_NAME),
                            rs.getString(LAST_NAME),
                            LocalDate.parse(rs.getString(DATE_BIRTH), Publisher.DATE_FORMATTER),
                            rs.getString(PICTURE_PATH)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Publisher> selectPublishers() throws Exception {
        List<Publisher> publishers = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (
                Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_PUBLISHERS); ResultSet rs = stmt.executeQuery()) {
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
        return publishers;
    }

}
