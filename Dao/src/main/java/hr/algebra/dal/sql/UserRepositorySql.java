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
    
    private static final String USERNAME =  "Username";
    private static final String PASSWORD =  "Password";
    private static final String ROLE =  "Role";
    private static final String USER_ID =  "UserId";

    @Override
    public int createUser(User user) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection(); CallableStatement stmt = con.prepareCall(CREATE_USER)) {

            stmt.setString(USERNAME, user.getUsername());
            stmt.setString(PASSWORD, user.getPassword());
            stmt.setString(ROLE, user.getRole());
            stmt.registerOutParameter(USER_ID, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(USER_ID);
        }
    }

    @Override
    public Optional<User> checkUser(String username, String password) throws Exception {
        try (Connection con = DataSourceSingleton.getInstance().getConnection(); CallableStatement stmt = con.prepareCall(CHECK_USER)) {

            stmt.setString(USERNAME, username);
            stmt.setString(PASSWORD, password);

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
