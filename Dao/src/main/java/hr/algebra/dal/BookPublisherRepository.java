/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Book;
import hr.algebra.model.Publisher;
import java.util.List;

/**
 *
 * @author AdrianBusak
 */
public interface BookPublisherRepository {

    void addBookPublisher(int bookId, int publisherId) throws Exception;

    void removeBookPublisher(int bookId, int publisherId) throws Exception;

    List<Publisher> getPublishersForBook(int bookId) throws Exception;

    List<Book> getBooksForPublisher(int publisherId) throws Exception;
}
