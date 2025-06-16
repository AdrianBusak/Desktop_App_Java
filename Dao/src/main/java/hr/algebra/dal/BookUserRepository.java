/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Book;
import hr.algebra.model.User;
import java.util.List;

/**
 *
 * @author AdrianBusak
 */
public interface BookUserRepository {

    void addBookUser(int bookId, int userId) throws Exception;

    void removeBookUser(int bookId, int userId) throws Exception;

    List<User> getUsersForBook(int bookId) throws Exception;

    List<Book> getBooksForUser(int userId) throws Exception;
}
