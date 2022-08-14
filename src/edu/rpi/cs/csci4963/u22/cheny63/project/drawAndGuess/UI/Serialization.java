package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.formdev.flatlaf.FlatDarkLaf;

public class Serialization extends JFrame {

    public void setGui() {
        FlatDarkLaf.setup(); // When using FlatLaf, it causes a NullPointer when serializing.

        MyAbstractModel model = new MyAbstractModel();
        JTable table = new JTable(model);

        getContentPane().add(table);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws Exception {
        // Create GUI with frame and model
        UI ui = new UI();
        for (int i = 0; i < args.length; i++) {
            SERIALIZATION_THREAD t = SERIALIZATION_THREAD.valueOf(args[i]);
            // Serialize the table model
            System.out.printf("Serializing on thread %s%n", args[i]);
            serialize(ui.model, t);
            sleep(1000);
        }

    }

}

class MyAbstractModel extends AbstractTableModel {

    private List<String> list = new ArrayList<>();

    public MyAbstractModel() {
        list = Collections.synchronizedList(new ArrayList<>());
        list.add("A");
        list.add("B");
        list.add("C");
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowIndex + "-" + columnIndex;
    }

    @Override
    public String getColumnName(int column) {
        return "Column " + column;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // TODO
    }
    
    
}

