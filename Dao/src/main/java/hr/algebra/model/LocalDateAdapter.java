/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

/**
 *
 * @author AdrianBusak
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDateTime>{

    @Override
    public LocalDateTime unmarshal(String vt) throws Exception {
        return LocalDateTime.parse(vt, Book.DATE_FORMATTER);
    }

    @Override
    public String marshal(LocalDateTime bt) throws Exception {
        return bt.format(Book.DATE_FORMATTER);
    }
    
}
