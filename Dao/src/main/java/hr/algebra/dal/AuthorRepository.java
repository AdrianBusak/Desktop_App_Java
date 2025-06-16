/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Author;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author AdrianBusak
 */
public interface AuthorRepository {

    int createAuthor(Author author) throws Exception;
    
    void createAuthors(List<Author> authors) throws Exception;

    void updateAuthor(int id, Author data) throws Exception;

    void deleteAuthor(int id) throws Exception;

    Optional<Author> selectAuthor(int id) throws Exception;

    List<Author> selectAuthors() throws Exception;

}
