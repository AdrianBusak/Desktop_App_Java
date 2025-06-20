/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.view.model;

import hr.algebra.model.Book;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author daniel.bele
 */
public class BookTableModel extends AbstractTableModel{
    
    private static final String[] COLUMN_NAMES = {"Id", "Title", "Link", "Description", "Picture path", "Published date"};
    
    private List<Book> books;

    public BookTableModel(List<Book> books) {
        this.books = books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return books.size();
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
                return books.get(rowIndex).getId();
            case 1:
                return books.get(rowIndex).getTitle();
            case 2:
                return books.get(rowIndex).getLink();
            case 3:
                return books.get(rowIndex).getDescription();
            case 4:
                return books.get(rowIndex).getPicturePath();
            case 5:
                return books.get(rowIndex).getPublishedDate().format(Book.DATE_FORMATTER);
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