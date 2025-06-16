/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Book;
import hr.algebra.model.Genre;
import java.util.List;

/**
 *
 * @author AdrianBusak
 */
public interface BookGenreRepository {

    void addBookGenre(int bookId, int genreId) throws Exception;

    void removeBookGenre(int bookId, int genreId) throws Exception;

    List<Genre> getGenresForBook(int bookId) throws Exception;

    List<Book> getBooksForGenre(int genreId) throws Exception;
}
