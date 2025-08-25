package src; 

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TablaOrdenadora {
    /**
     * Ordena la JTable de mayor a menor según la columna indicada
     */
    public static void ordenarDescendente(DefaultTableModel model, int columna) {
        ArrayList<Object[]> filas = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            Object[] fila = new Object[model.getColumnCount()];
            for (int j = 0; j < model.getColumnCount(); j++) {
                fila[j] = model.getValueAt(i, j);
            }
            filas.add(fila);
        }
        Collections.sort(filas, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                Comparable c1 = (Comparable) o1[columna];
                Comparable c2 = (Comparable) o2[columna];
                return c2.compareTo(c1); // Descendente
            }
        });
        model.setRowCount(0);
        for (Object[] fila : filas) {
            model.addRow(fila);
        }
    }

    /**
     * Ordena la JTable de menor a mayor según la columna indicada
     */
    public static void ordenarAscendente(DefaultTableModel model, int columna) {
        ArrayList<Object[]> filas = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            Object[] fila = new Object[model.getColumnCount()];
            for (int j = 0; j < model.getColumnCount(); j++) {
                fila[j] = model.getValueAt(i, j);
            }
            filas.add(fila);
        }
        Collections.sort(filas, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                Comparable c1 = (Comparable) o1[columna];
                Comparable c2 = (Comparable) o2[columna];
                return c1.compareTo(c2); // Ascendente
            }
        });
        model.setRowCount(0);
        for (Object[] fila : filas) {
            model.addRow(fila);
        }
    }
}
