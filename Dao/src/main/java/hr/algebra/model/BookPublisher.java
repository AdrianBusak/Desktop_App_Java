/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

/**
 *
 * @author AdrianBusak
 */
public class BookPublisher {
    
        private int bookId;
    private int publisherId;

    public BookPublisher(int bookId, int publisherId) {
        this.bookId = bookId;
        this.publisherId = publisherId;
    }

    public BookPublisher() {
    }
    

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    
}
