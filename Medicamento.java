public class Medicamento {

    private String nombre;      //encapsula el nombre
    private String efectos;     //encapsula los efectos secundarios

    public Medicamento(String nombre, String efectos) {     //constructor
        this.nombre = nombre;
        this.efectos = efectos;
    }

    public String getNombre() {     //getter nombre
        return nombre;
    }

    public String getEfectos() {    //getter efectos
        return efectos;
    }
}
