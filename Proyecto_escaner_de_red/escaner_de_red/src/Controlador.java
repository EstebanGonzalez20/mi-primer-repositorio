import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.table.TableModel;

public class Controlador implements ActionListener{

    private Vista vista;
    private int tiempo = 0;
    private String formato = "CSV";

    public Controlador(){
        vista = new Vista();
        vista.getIPinicioField().addActionListener(this);
        vista.getIPfinField().addActionListener(this);
        vista.getIniciarButton().addActionListener(this);
        vista.getLimpiarButton().addActionListener(this);
        vista.getConfiguracionButton().addActionListener(this);
        vista.getGuardarButton().addActionListener(this);
        vista.setVisible(true);

    }
    @Override

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.getIniciarButton()){
            String inicio = vista.getIPinicioField().getText();
            String fin = vista.getIPfinField().getText();

            if(fin.isEmpty() || inicio.isEmpty()){
                JOptionPane.showMessageDialog(null, "Error, debe haber una IP de principio y de fin", "Error", JOptionPane.ERROR_MESSAGE);
            } 

            else if(!IP.esIPValida(fin) || !IP.esIPValida(inicio)){
                JOptionPane.showMessageDialog(null, "Error, ingrese las IPs correctamente", "Error", JOptionPane.ERROR_MESSAGE);
            } 

            else {
                // Validaciones ya hechas aquí...

                SwingWorker<Void, Object[]> worker = new SwingWorker<Void, Object[]>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        IP ipFin = new IP(fin);
                        IP ipActual = new IP(inicio);
                        int totalIPs = IP.diferenciaEntreIPs(inicio, fin);
                        int progreso = 0;

                        while (!ipActual.getIpCompleta().equals(ipFin.getIpCompleta())) {
                            String nombre;
                            String coneccion;
                            long inicioProceso = System.currentTimeMillis();
                            
                            ArrayList<String> ping = CMD.ejecutarComando("ping " + ipActual.getIpCompleta());
                            ArrayList<String> nslookup = CMD.ejecutarComando("nslookup " + ipActual.getIpCompleta());

                            if (nslookup.size() > 4) {
                                nombre = nslookup.get(3).substring(8);
                            } else {
                                nombre = "No resuelto";
                            }

                            if (Integer.parseInt(ping.get(8).substring(54)) == 4) {
                                coneccion = "Desconectado";
                            } else {
                                coneccion = "Conectado";
                            }

                            long finalProceso = System.currentTimeMillis();
                            long tiempo = finalProceso - inicioProceso;

                            publish(new Object[]{ipActual.getIpCompleta(), nombre, coneccion, tiempo});  // Publica resultados

                            progreso++;
                            setProgress((progreso * 100) / totalIPs);  // Actualiza progreso

                            ipActual.setNumero4(ipActual.getNumero4() + 1);
                        }

                        return null;
                    }

                    @Override
                    protected void process(java.util.List<Object[]> chunks) {
                        for (Object[] row : chunks) {
                            vista.getModeloTabla().addRow(row); // Agrega fila a la tabla en el EDT
                        }
                    }

                    @Override
                    protected void done() {
                        JOptionPane.showMessageDialog(null, "Proceso completado", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                };

                // Crear y mostrar barra de progreso
                VentanaProgreso vistaBarra = new VentanaProgreso(0, 100);
                vistaBarra.setVisible(true);

                // Vincular progreso de SwingWorker con la barra
                worker.addPropertyChangeListener(evt -> {
                    if ("progress".equals(evt.getPropertyName())) {
                        int progress = (Integer) evt.getNewValue();
                        vistaBarra.getBarra().setValue(progress);
                    }
                });
                worker.execute();  // Ejecutar en segundo plano
            }
        }

        else if(e.getSource() == vista.getLimpiarButton()){
            vista.getModeloTabla().setRowCount(0); // Limpia la tabla
        }

        else if(e.getSource() == vista.getConfiguracionButton()){
            VistaConfiguracion ventanaConfig = new VistaConfiguracion(vista);
            ControladorConfiguracion c = new ControladorConfiguracion(ventanaConfig);
            tiempo = c.getTiempoRespuesta();
            formato = c.getFormato();
            System.out.println(tiempo);
            System.out.println(formato);
        } 

        else if(e.getSource() == vista.getGuardarButton()){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar resultados como");

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();

                // Asegura que el archivo tenga extensión .csv
                if (!path.toLowerCase().endsWith(".csv")) {
                    path += ".csv";
                }

                guardarResultadosComoCSV(path);
            }
        }
        
    }

    public void guardarResultadosComoCSV(String nombreArchivo) {
        try (FileWriter csvWriter = new FileWriter(nombreArchivo)) {
            TableModel modelo = vista.getModeloTabla();

            // Escribir encabezados
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                csvWriter.append(modelo.getColumnName(i));
                if (i < modelo.getColumnCount() - 1) {
                    csvWriter.append(",");
                }
            }
            csvWriter.append("\n");

            // Escribir filas
            for (int row = 0; row < modelo.getRowCount(); row++) {
                for (int col = 0; col < modelo.getColumnCount(); col++) {
                    Object valor = modelo.getValueAt(row, col);
                    csvWriter.append(valor != null ? valor.toString() : "");
                    if (col < modelo.getColumnCount() - 1) {
                        csvWriter.append(",");
                    }
                }
                csvWriter.append("\n");
            }

            csvWriter.flush();
            JOptionPane.showMessageDialog(null, "Resultados guardados en: " + nombreArchivo);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el archivo: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

