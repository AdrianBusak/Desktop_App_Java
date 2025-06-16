/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.view.model;

import hr.algebra.model.Genre;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author daniel.bele
 */
public class GenreTableModel extends AbstractTableModel{
    
    private static final String[] COLUMN_NAMES = {"Id", "Name"};
    
    private List<Genre> genres;

    public GenreTableModel(List<Genre> genres) {
        this.genres = genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return genres.size();
    }

    @Override
    public int getColumnCount() {
        //return Article.class.getDeclaredFields().length - 1;
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return genres.get(rowIndex).getId();
            case 1:
                return genres.get(rowIndex).getName();
            default:
                throw new RuntimeException("No such column");
        }
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }


    // important for the id ordering
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex); 
    }
 
    
}