import java.awt.*;
import javax.swing.*;

public class Vista extends JFrame{
    private JLabel texto;
    private JTextField ipInicioField, ipFinField;

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
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton iniciarButton = new JButton("Iniciar escaneo");
        JButton limpiarButton = new JButton("Limpiar pantalla");
        JButton configuracionButton = new JButton("Configuración");
        buttonPanel.add(iniciarButton);
        buttonPanel.add(limpiarButton);
        buttonPanel.add(configuracionButton);

        // Añadir panel de botones al GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(buttonPanel, gbc);

        // Añadir panel principal a la ventana
        add(panel);

        // Hacer la ventana visible
        setVisible(true);
    }
}
