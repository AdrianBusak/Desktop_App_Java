/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.User;
import java.util.Optional;

/**
 *
 * @author AdrianBusak
 */
public interface UserRepository {

    int createUser(User user) throws Exception;

    Optional<User> checkUser(String username, String password) throws Exception;
}
