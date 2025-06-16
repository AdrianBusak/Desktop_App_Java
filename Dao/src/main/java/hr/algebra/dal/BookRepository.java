/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Book;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author AdrianBusak
 */
public interface BookRepository {

    int createBook(Book book) throws Exception;

    void createBooks(List<Book> books) throws Exception;

    void updateBook(int id, Book data) throws Exception;

    void deleteBook(int id) throws Exception;

    Optional<Book> selectBook(int id) throws Exception;

    List<Book> selectBooks() throws Exception;
}
