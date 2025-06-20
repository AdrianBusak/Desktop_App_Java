/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AdrianBusak
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserFavorites {

    private String username;
    private List<Book> favorites;

    public UserFavorites() {
    }

    public UserFavorites(String username, List<Book> favorites) {
        this.username = username;
        this.favorites = favorites;
    }
}
