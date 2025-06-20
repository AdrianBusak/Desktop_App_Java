/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AdrianBusak
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
    private int id;
    private String username;
    private String password;
    private String role;

    public User(String firstName, String password,  String role) {
        this.username = firstName;
        this.password = password;
        this.role = role;
    }

    public User(int id, String firstName, String password, String role) {
        this.id = id;
        this.username = firstName;
        this.password = password;
        this.role = role;
    }

    public User() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String Role) {
        this.role = role;
    }
}
