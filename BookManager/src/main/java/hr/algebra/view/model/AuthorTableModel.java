package hr.algebra.view.model;

import hr.algebra.model.Author;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author adrian.busak
 */
public class AuthorTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {
        "Id", "First name", "Last name", "Date of birth", "Picture path"
    };

    private List<Author> authors;

    public AuthorTableModel(List<Author> authors) {
        this.authors = authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return authors.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Author author = authors.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return author.getId();
            case 1:
                return author.getFirstName();
            case 2:
                return author.getLastName();
            case 3:
                return author.getDateBirth();
            case 4:
                return author.getPicturePath();
            default:
                throw new RuntimeException("No such column");
        }
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Integer.class;
        }
        return super.getColumnClass(columnIndex);
    }
}
