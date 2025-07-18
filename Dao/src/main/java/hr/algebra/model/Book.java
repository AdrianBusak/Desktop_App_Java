/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author AdrianBusak
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Book implements Comparable<Book> {

    @XmlElement(name = "id")
    private int id;
    @XmlElement(name = "title")
    private String title;
    @XmlElement(name = "description")
    private String description;
    @XmlElement(name = "link")
    private String link;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = "publishDate")
    private LocalDateTime publishedDate;
    @XmlElement(name = "picturePath")
    private String picturePath;

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Book(int id, String title, String description, String link, LocalDateTime publishedDate, String picturePath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.publishedDate = publishedDate;
        this.picturePath = picturePath;
    }

    public Book(String title, String description, String link, LocalDateTime publishedDate, String picturePath) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.publishedDate = publishedDate;
        this.picturePath = picturePath;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Override
    public String toString() {
        return id + " - " + title;
    }

    @Override
    public int compareTo(Book o) {
        return title.compareTo(o.title);
    }

}
