import java.awt.event.*;
import java.lang.runtime.*;
import javax.swing.JOptionPane;

public class Controlador implements ActionListener{
    private Vista vista;
    
    public Controlador(){
        vista = new Vista();
        vista.getIPinicioField().addActionListener(this);
        vista.getIPfinField().addActionListener(this);
        vista.getIniciarButton().addActionListener(this);
        vista.getLimpiarButton().addActionListener(this);
        vista.getConfiguracionButton().addActionListener(this);
        vista.setVisible(true);
    }

    @Override
	public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.getIniciarButton()){
            String inicio = vista.getIPinicioField().getText();
            String fin = vista.getIPfinField().getText();

            if(fin.isEmpty() || inicio.isEmpty()){
                JOptionPane.showMessageDialog(null, "Error, debe haber una IP de principio y de fin", "Error", JOptionPane.ERROR_MESSAGE);
            } else if(!esIPValida(fin) || !esIPValida(inicio)){
                JOptionPane.showMessageDialog(null, "Error, ingrese las IPs correctamente", "Error", JOptionPane.ERROR_MESSAGE);
            } else{
                // Ejemplo: agregar la IP de inicio y un estado "Pendiente" a la tabla
                vista.getModeloTabla().addRow(new Object[]{inicio, "Pendiente"});
            }
        }
    }

    public boolean esIPValida(String ip) {
        return ip.matches("^((25[0-5]|2[0-4]\\d|[0-1]?\\d{1,2})\\.){3}(25[0-5]|2[0-4]\\d|[0-1]?\\d{1,2})$");
    }
}
