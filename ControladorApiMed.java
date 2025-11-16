public class ControladorApiMed {

    private ServicioApi api;

    public ControladorMedicamentos(ServicioApi api) {
        this.api = api;
    }

    public Medicamento buscar(String nombre) {
        String efectos = api.consultar(nombre);
        return new Medicamento(nombre, efectos);
    }
}