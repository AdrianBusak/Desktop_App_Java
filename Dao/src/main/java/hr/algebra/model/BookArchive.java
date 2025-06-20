/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AdrianBusak
 */
@XmlRootElement(name = "booksArchive")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookArchive {

    @XmlElement(name = "books")
    private List<Book> books;
    
    public BookArchive() {
    }

    public BookArchive(List<Book> books) {
        this.books = books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    
    public List<Book> getBooks(){
        return books;
    }
    
}
