import javax.swing.*;
import java.awt.*;

public class VentanaProgreso extends JFrame {
    private JProgressBar barra;

    public VentanaProgreso(int min, int max) {
        setTitle("Progreso");
        setSize(300, 80);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        barra = new JProgressBar(min, max);
        barra.setStringPainted(true);

        add(barra, BorderLayout.CENTER);
    }

    public JProgressBar getBarra() {
        return barra;
    }
}

