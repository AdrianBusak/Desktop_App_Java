/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.UserRepository;
import hr.algebra.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Optional;

public class UserRepositorySql implements UserRepository {

    private static final String CREATE_USER = "{ CALL createUser(?, ?, ?, ?) }";
    private static final String CHECK_USER = "{ CALL checkUser(?, ?) }";

    @Override
    public int createUser(User user) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection(); CallableStatement stmt = con.prepareCall(CREATE_USER)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());  // Ako hashaš, zamijeni ovo hashiranom verzijom
            stmt.setString(3, user.getRole());
            stmt.registerOutParameter(4, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(4);
        }
    }

    @Override
    public Optional<User> checkUser(String username, String password) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection(); CallableStatement stmt = con.prepareCall(CHECK_USER)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(
                            rs.getInt("Id"),
                            rs.getString("Username"),
                            rs.getString("Password"),
                            rs.getString("Role")
                    ));
                }
            }
        }
        return Optional.empty();
    }

}
