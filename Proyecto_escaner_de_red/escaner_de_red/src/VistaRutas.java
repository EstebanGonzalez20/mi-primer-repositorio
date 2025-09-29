import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;

public class VistaRutas extends JDialog {
    private JButton botonCerrar;
    private JTable tabla;
    private DefaultTableModel modeloTablaInterfaces, modeloTablaRutas4;

    public VistaRutas(JFrame parent, List<List<String>> rutas) {
        super(parent, "Rutas de red", true);
        setTitle("Rutas de red");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        //Primer bloque
        JLabel label = new JLabel("Lista de interfaces:");
        label.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(label, gbc);

        modeloTablaInterfaces = new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Dirección MAC","Interfaz" }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTablaInterfaces);
        JScrollPane scrollPane = new JScrollPane(tabla);
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scrollPane, gbc);
        gbc.weightx = 0;
        gbc.weighty = 0;

        for(String interfaces : rutas.get(0).subList(1, rutas.get(0).size())) {
            String id = interfaces.substring(1, 3);
            String mac = interfaces.substring(6, 23);
            String interfaz = interfaces.substring(30).trim();

            if(mac.matches("^\\.+$")) {
                mac = "N/A";
            }

            modeloTablaInterfaces.addRow(new Object[] { id, mac, interfaz } );
        }


        //Segundo bloque
        label = new JLabel("IPv4 Tabla de enrutamiento:");
        label.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(label, gbc);

        modeloTablaRutas4 = new DefaultTableModel(new Object[][] {}, new String[] { "Destino", "Máscara de red","Puerta de enlace", "Interfaz", "Métrica"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTablaRutas4);
        scrollPane = new JScrollPane(tabla);
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scrollPane, gbc);
        gbc.weightx = 0;
        gbc.weighty = 0;

        String metrica;
        String interfaz;
        for(String ruta : rutas.get(2).subList(2, rutas.get(2).size())) {
            String[] partes = ruta.trim().split("\\s+");
            if(partes.length >= 5) {
                if(partes[2].equals("On-link") || partes[2].equals("En")) {
                    partes[2] = "N/A";
                    interfaz = partes[4];
                    metrica = partes[5];
                } else {
                    interfaz = partes[3];
                    metrica = partes[4];
                }
                modeloTablaRutas4.addRow(new Object[] { partes[0], partes[1], partes[2], interfaz, metrica} );
            }
        }

        botonCerrar = new JButton("Cerrar");
        botonCerrar.addActionListener(e -> dispose());
        JPanel panelBoton = new JPanel();
        panelBoton.add(botonCerrar);
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        add(panelBoton, gbc);
        setVisible(true);
    }

}
