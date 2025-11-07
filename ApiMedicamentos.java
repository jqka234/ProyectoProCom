import javax.swing.*;
import java.io.*;
import java.net.*;
import org.json.*;

public class ApiMedicamentos {
    private static final String URL_FDA = "https://api.fda.gov/drug/event.json";
    private static final String URL_TRADUCTOR = "https://api.mymemory.translated.net/get";


    public static void obtenerDatos(String nombre) {
        try {
            String Urlstr = URL_FDA + "?search=patient.drug.medicinalproduct:"
            +URLEncoder.encode(nombre, "UTF-8")
            +"&limit=10";

        URL url = new URL(urlStr);
        HttpURLConnection = (HttpURLConnection)url.openConnection()
        conexion.setRequestMethod("Get");

        
        }

    }

}

