/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Publisher;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author AdrianBusak
 */
public interface PublisherRepository {

    int createPublisher(Publisher publisher) throws Exception;

    void createPublishers(List<Publisher> publishers) throws Exception;

    void updatePublisher(int id, Publisher data) throws Exception;

    void deletePublisher(int id) throws Exception;

    Optional<Publisher> selectPublisher(int id) throws Exception;

    List<Publisher> selectPublishers() throws Exception;
}
