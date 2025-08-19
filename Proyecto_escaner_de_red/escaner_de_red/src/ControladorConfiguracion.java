import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControladorConfiguracion implements ActionListener {
    private VistaConfiguracion vistaConfig;
    private int tiempoRespuesta;
    private String formato;

    public ControladorConfiguracion(VistaConfiguracion vistaConfig) {
        this.vistaConfig = vistaConfig;
        this.vistaConfig.getBotonGuardar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaConfig.getBotonGuardar()) {
            System.out.println("Hola");
            try{
                tiempoRespuesta = Integer.parseInt(vistaConfig.getCampoTiempoRespuesta().getText());
                formato = (String) vistaConfig.getComboFormato().getSelectedItem();
                // Aquí puedes guardar los valores o usarlos como necesites
                JOptionPane.showMessageDialog(vistaConfig,
                    "Tiempo de respuesta: " + tiempoRespuesta + "\nFormato: " + formato,
                    "Configuración guardada", JOptionPane.INFORMATION_MESSAGE);
                vistaConfig.dispose();
            } catch (NumberFormatException error) {
                JOptionPane.showMessageDialog(vistaConfig,"Porfavor ingrese un tiempo de respuesta válido",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public String getFormato(){
        return formato;
    }
    public int getTiempoRespuesta(){
        return tiempoRespuesta;
    }
}
