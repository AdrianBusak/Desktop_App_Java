/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.DataRepository;
import java.sql.CallableStatement;
import java.sql.Connection;
import javax.sql.DataSource;

/**
 *
 * @author AdrianBusak
 */
public class DataRepositorySql implements DataRepository {

    private static final String CLEAR_ALL_DATA = "{ CALL clearAllData }";

    @Override
    public void clearAllData() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CLEAR_ALL_DATA)) {

            stmt.executeUpdate();
        }
    }
}
