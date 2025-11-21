import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanelGrafico extends JPanel {

    private JTextField campoNombre;         //campo de texto para el nombre del medicamento
    private JButton botonBuscar;            //boton para iniciar la busqueda
    private JTextArea areaResultados;       //area de texto para mostrar los resultados
    private JScrollPane scrollPane;         //scroll pane para el area de resultados
    private ControladorApiMed controlador;  //controlador para manejar la logica de la aplicacion

    public MainPanelGrafico() {
        // Inicializar el controlador con la API de medicamentos
        ServicioApi api = new ApiMedicamentos();        //crea una instancia de ApiMedicamentos que implementa ServicioApi
        controlador = new ControladorApiMed(api);

        // Configurar el layout del panel
        setLayout(new BorderLayout());

        // Panel superior para el campo de texto y el botón
        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.add(new JLabel("Nombre del medicamento:"));
        campoNombre = new JTextField(20);
        panelSuperior.add(campoNombre);
        botonBuscar = new JButton("Buscar");
        panelSuperior.add(botonBuscar);

        // Área de texto para mostrar los resultados
        areaResultados = new JTextArea(15, 50);
        areaResultados.setEditable(false);
        scrollPane = new JScrollPane(areaResultados);

        // Agregar componentes al panel principal
        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Agregar listener al botón
        botonBuscar.addActionListener(new ActionListener() {        //cuando se presiona el boton, llama al metodo buscarMedicamento
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarMedicamento();
            }
        });
    }

    private void buscarMedicamento() {
        String nombre = campoNombre.getText().trim();       //obtiene el nombre del medicamento ingresado
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el nombre del medicamento.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Medicamento medicamento = controlador.buscar(nombre);       //usa el controlador para buscar el medicamento
            String resultados = "Medicamento: " + medicamento.getNombre() + "\n\nEfectos secundarios:\n" + medicamento.getEfectos();    //prepara el texto con los resultados, llamando al metodo getNombre y getEfectos
            areaResultados.setText(resultados);     //muestra los resultados en el area de texto
        } catch (Exception ex) {
            areaResultados.setText("Error al buscar el medicamento: " + ex.getMessage());   //maneja posibles errores, dado caso se produzca uno
        }
    }

    // Clase principal para ejecutar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Consulta de Medicamentos");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new MainPanelGrafico());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
