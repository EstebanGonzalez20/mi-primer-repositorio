package src; 
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

public class Archivo {
    static public void guardarResultados(String nombreArchivo, TableModel modelo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            // Escribir encabezados
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                writer.append(modelo.getColumnName(i));
                if (i < modelo.getColumnCount() - 1) {
                    writer.append(",");
                }
            }
            writer.append("\n");

            // Escribir filas
            for (int row = 0; row < modelo.getRowCount(); row++) {
                for (int col = 0; col < modelo.getColumnCount(); col++) {
                    Object valor = modelo.getValueAt(row, col);
                    writer.append(valor != null ? valor.toString() : "");
                    if (col < modelo.getColumnCount() - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }

            writer.flush();
            JOptionPane.showMessageDialog(null, "Resultados guardados en: " + nombreArchivo);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el archivo: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
