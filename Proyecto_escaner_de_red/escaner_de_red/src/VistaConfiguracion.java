import javax.swing.*;
import java.awt.*;

public class VistaConfiguracion extends JDialog {
    private JTextField campoTiempoRespuesta;
    private JComboBox<String> comboFormato;
    private JButton botonGuardar;

    public VistaConfiguracion(JFrame parent) {
        super(parent, "Configuración", true);
        setTitle("Configuración");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel label1 = new JLabel("Tiempo de respuesta:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(label1, gbc);

        campoTiempoRespuesta = new JTextField(15);
        gbc.gridx = 1;
        add(campoTiempoRespuesta, gbc);

        JLabel label2 = new JLabel("Formato de guardado:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(label2, gbc);

        // ComboBox con opciones "CVS" y "TXT"
        String[] opciones = { "CVS", "TXT" };
        comboFormato = new JComboBox<>(opciones);
        gbc.gridx = 1;
        add(comboFormato, gbc);

        botonGuardar = new JButton("Guardar configuración");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(botonGuardar, gbc);

        setVisible(true);
    }

    // Getters
    public JTextField getCampoTiempoRespuesta() {
        return campoTiempoRespuesta;
    }

    public JComboBox<String> getComboFormato() {
        return comboFormato;
    }

    public JButton getBotonGuardar() {
        return botonGuardar;
    }
}
