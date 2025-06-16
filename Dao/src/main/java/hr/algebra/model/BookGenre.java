/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

/**
 *
 * @author AdrianBusak
 */
public class BookGenre {

    private int bookId;
    private int genreId;

    public BookGenre(int bookId, int genreId) {
        this.bookId = bookId;
        this.genreId = genreId;
    }

    public BookGenre() {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }
    
    

}
