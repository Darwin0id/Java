/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author darwin
 */
public class ActorTableModel extends AbstractTableModel {
    private static final String[] COLUMN_NAMES = {"Id", "FirstName", "LastName", "Type"};
    private List<Actor> persons;

    public ActorTableModel(List<Actor> persons) {
        this.persons = persons;
    }

    public void setPersons(List<Actor> persons) {
        this.persons = persons;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return persons.size();
    }

    @Override
    public int getColumnCount() {
        return Actor.class.getDeclaredFields().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return persons.get(rowIndex).id;
            case 1:
                return persons.get(rowIndex).firstName;
            case 2:
                return persons.get(rowIndex).lastName;
            case 3:
                return persons.get(rowIndex).type;
            default:
                throw new RuntimeException("No such column");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }
}
