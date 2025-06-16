/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Author;
import hr.algebra.model.Book;
import hr.algebra.model.BookAuthor;
import java.util.List;

/**
 *
 * @author AdrianBusak
 */
public interface BookAuthorRepository {

    void createBookAuthor(BookAuthor bookAuthor) throws Exception;

    void removeBookAuthor(BookAuthor bookAuthor) throws Exception;

    List<Author> selectAuthorsForBook(int bookId) throws Exception;

    List<Book> selectBooksForAuthor(int authorId) throws Exception;
}
