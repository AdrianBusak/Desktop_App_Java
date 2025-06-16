package hr.algebra.view.model;

import hr.algebra.model.Publisher;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author adrian.busak
 */
public class PublisherTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {
        "Id", "First name", "Last name", "Date of birth", "Picture path"
    };

    private List<Publisher> publishers;

    public PublisherTableModel(List<Publisher> publishers) {
        this.publishers = publishers;
    }

    public void setPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return publishers.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Publisher publiser = publishers.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return publiser.getId();
            case 1:
                return publiser.getFirstName();
            case 2:
                return publiser.getLastName();
            case 3:
                return publiser.getDateBirth();
            case 4:
                return publiser.getPicturePath();
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
