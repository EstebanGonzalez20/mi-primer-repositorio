package src; 

import java.awt.*;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;



public class Vista extends JFrame{
    private JLabel texto, labelConectadas;
    private JTextField ipInicioField, ipFinField;
    private JButton iniciarButton, limpiarButton, configuracionButton, guardarButton, ordenarButton, filtrarButton;
    private JPanel buttonPanel;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public Vista() {
        // Configuración básica de la ventana
        setTitle("Escaner de red");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes

        //texto antes del campo de texto para "Ingrese ip de inicio"
        texto = new JLabel("Ingrese la ip de inicio del rango");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(texto, gbc);

        // Campo de texto para "Ingrese ip de inicio"
        ipInicioField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(ipInicioField, gbc);

        //texto antes del campo de texto para "Ingrese ip de fin"
        texto = new JLabel("Ingrese la ip de fin del rango");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(texto, gbc);

        // Campo de texto para "Ingrese ip de fin"
        ipFinField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(ipFinField, gbc);

        // Panel para los botones
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        iniciarButton = new JButton("Iniciar escaneo");
        limpiarButton = new JButton("Limpiar pantalla");
        configuracionButton = new JButton("Configuración");
        guardarButton = new JButton("Guardar resultado");
        buttonPanel.add(iniciarButton);
        buttonPanel.add(limpiarButton);
        buttonPanel.add(configuracionButton);
        buttonPanel.add(guardarButton);

        // Añadir panel de botones al GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(buttonPanel, gbc);

        //Informacion sobre la tabla
        labelConectadas = new JLabel("Dispositivos conectados: 0");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(labelConectadas, gbc);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ordenarButton = new JButton("Ordenar");
        filtrarButton = new JButton("Filtrar");
        buttonPanel.add(ordenarButton);
        buttonPanel.add(filtrarButton);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(buttonPanel, gbc);

        // Tabla para mostrar resultados (solo lectura)
        modeloTabla = new DefaultTableModel(new Object[][]{}, new String[]{"IP", "Nombre", "Estado", "Tiempo (ms)"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scrollPane, gbc);

        // Resetear pesos para siguientes componentes
        gbc.weightx = 0;
        gbc.weighty = 0;

        // Añadir panel principal a la ventana
        add(panel);

        // Hacer la ventana visible
        setVisible(true);
        }

    public JTextField getIPinicioField(){
        return ipInicioField;
    }

    public JTextField getIPfinField(){
        return ipFinField;
    }

    public JButton getIniciarButton(){
        return iniciarButton;
    }

    public JButton getLimpiarButton(){
        return limpiarButton;
    }

    public JButton getConfiguracionButton(){
        return configuracionButton;
    }

    public JButton getGuardarButton(){
        return guardarButton;
    }

    public JTable getTabla(){
        return tabla;
    }

    public DefaultTableModel getModeloTabla(){
        return modeloTabla;
    }

    public JLabel getConectadas(){
        return labelConectadas;
    }

    public JButton getOrdenarButton(){
        return ordenarButton;
    }

    public JButton getFiltrarButton(){
        return filtrarButton;
    }
}