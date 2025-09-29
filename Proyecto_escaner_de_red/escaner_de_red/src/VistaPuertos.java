import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;

public class VistaPuertos extends JDialog {
    private JButton botonCerrar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public VistaPuertos(JFrame parent, List<String> puertos) {
        super(parent, "Puertos Abiertos", true);
        setTitle("Puertos Abiertos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());


        modeloTabla = new DefaultTableModel(new Object[][]{}, new String[]{"Protocolo", "Dirección Local", "Dirección Remota", "Estado"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        botonCerrar = new JButton("Cerrar");
        botonCerrar.addActionListener(e -> dispose());
        JPanel panelBoton = new JPanel();
        panelBoton.add(botonCerrar);
        add(panelBoton, BorderLayout.SOUTH);

        String estado;
        for (String linea : puertos){
            List<String> partes = List.of(linea.strip().split("\\s+"));
            if(partes.get(0).equals("TCP")){
                if(partes.get(3).equals("LISTENING")){
                    estado = "En espera";
                } else if(partes.get(3).equals("ESTABLISHED")){
                    estado = "En curso";
                } else{
                    estado = "Desconectado";
                }
            } else{
                estado = "N/A";
            }
            modeloTabla.addRow(new Object[]{partes.get(0), partes.get(1), partes.get(2), estado});
        }

        setVisible(true);
    }
}
