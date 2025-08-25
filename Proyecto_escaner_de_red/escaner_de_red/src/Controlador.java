package src; 
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.table.TableModel;

public class Controlador implements ActionListener{

    private Vista vista;
    private int tiempo = 0;
    private String formato = "CSV";
    private int conectadas;
    private boolean descendente = false;

    public Controlador(){
        vista = new Vista();
        agregarValidadorIP(vista.getIPinicioField());
        agregarValidadorIP(vista.getIPfinField());
        vista.getIniciarButton().addActionListener(this);
        vista.getLimpiarButton().addActionListener(this);
        vista.getConfiguracionButton().addActionListener(this);
        vista.getGuardarButton().addActionListener(this);
        vista.getOrdenarButton().addActionListener(this);
        vista.getFiltrarButton().addActionListener(this);
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

            else if(IP.esMayorQue(inicio, fin)){
                JOptionPane.showMessageDialog(null, "Error, ingrese la IP más grande al como fin del rango", "Error", JOptionPane.ERROR_MESSAGE);
            } 

            else {
                
                SwingWorker<Void, Object[]> worker = new SwingWorker<Void, Object[]>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        conectadas = 0;
                        IP ipFin = new IP(fin);
                        IP ipActual = new IP(inicio);
                        int totalIPs = IP.diferenciaEntreIPs(inicio, fin) + 1;
                        int progreso = 0;

                        while (true) {
                            System.out.println(ipActual.getIpCompleta());
                            String nombre;
                            String coneccion;
                            long inicioProceso = System.currentTimeMillis();
                            
                            ArrayList<String> ping;
                            if (tiempo != 0){
                                ping = CMD.ejecutarComando("ping " + ipActual.getIpCompleta() + " -w " + tiempo);
                            } else {
                                ping = CMD.ejecutarComando("ping " + ipActual.getIpCompleta());
                            }
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
                                conectadas++;
                                vista.getConectadas().setText("Dispositivos conectados: " + conectadas);
                            }

                            long finalProceso = System.currentTimeMillis();
                            long tiempo = finalProceso - inicioProceso;

                            publish(new Object[]{ipActual.getIpCompleta(), nombre, coneccion, tiempo});  // Publica resultados

                            progreso++;
                            setProgress((progreso * 100) / totalIPs);  // Actualiza progreso

                            if (ipActual.getIpCompleta().equals(ipFin.getIpCompleta())) {
                                break; // Sale del bucle si se alcanzó la IP final
                            }
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
        } 

        else if(e.getSource() == vista.getGuardarButton()){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar resultados como");
            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();

                // Asegura que el archivo tenga extensión .csv
                if (!path.toLowerCase().endsWith("." + formato.toLowerCase())) {
                    path += "." + formato.toLowerCase();
                }

                Archivo.guardarResultados(path, vista.getModeloTabla());
            }
        }

        else if(e.getSource() == vista.getOrdenarButton()){
            if(descendente){
                TablaOrdenadora.ordenarAscendente(vista.getModeloTabla(), 0); // Ordena por IP (columna 0)
                descendente = false;
            } else {
                TablaOrdenadora.ordenarDescendente(vista.getModeloTabla(), 0); // Ordena por IP (columna 0)
                descendente = true;
            }
        }

        else if(e.getSource() == vista.getFiltrarButton()){
            String estado = (String) JOptionPane.showInputDialog(null, "Seleccione el estado a filtrar:",
                "Filtrar por estado", JOptionPane.QUESTION_MESSAGE, null,
                new String[]{"Conectado", "Desconectado"}, "Conectado");

            if (estado != null) {
                TableModel model = vista.getModeloTabla();
                javax.swing.table.TableRowSorter<TableModel> sorter = new javax.swing.table.TableRowSorter<>(model);
                vista.getTabla().setRowSorter(sorter);
                sorter.setRowFilter(javax.swing.RowFilter.regexFilter(estado, 2)); // Filtra por la columna "Estado" (índice 2)
            }
        }
    }

    private void agregarValidadorIP(javax.swing.JTextField campo) {
    campo.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            validarIP(campo.getText(), campo);
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
            validarIP(campo.getText(), campo);
        }
        @Override
        public void changedUpdate(DocumentEvent e) {
            validarIP(campo.getText(), campo);
        }
        });
    }

    private void validarIP(String texto, javax.swing.JTextField campo) {
        if (IP.esIPValida(texto)) {
            campo.setBackground(java.awt.Color.WHITE); // IP válida → fondo blanco
        } else {
            campo.setBackground(java.awt.Color.PINK);  // IP inválida → fondo rosado
        }
    }
}