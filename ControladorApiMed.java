public class ControladorApiMed {

    private ServicioApi api;

    public ControladorApiMed(ServicioApi api) { //constructor que recibe una implementacion de ServicioApi
        this.api = api;
    }

    public Medicamento buscar(String nombre) {//metodo que busca un medicamento por su nombre
        String efectos = api.consultar(nombre);//consulta la API para obtener los efectos secundarios
        return new Medicamento(nombre, efectos);//crea y devuelve un objeto Medicamento con el nombre y los efectos obtenidos
    }
}
