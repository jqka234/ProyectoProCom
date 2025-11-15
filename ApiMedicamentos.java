import java.io.*;
import java.net.*;
import org.json.*;

public class ApiMedicamentos extends ServicioApi {

    private static final String URL_FDA = "https://api.fda.gov/drug/event.json";
    private static final String URL_TRADUCTOR = "https://api.mymemory.translated.net/get";

@Override
public String consultar(String nombre) {
        try {
            String urlStr = URL_FDA +
                    "?search=patient.drug.medicinalproduct:" +
                    URLEncoder.encode(nombre, "UTF-8") +
                    "&limit=5";

            URL url = new URL(urlStr);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            StringBuilder respuesta = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                respuesta.append(line);
            }
            in.close();

            return procesarJson(respuesta.toString());

        } catch (Exception e) {
            return "Error al obtener datos: " + e.getMessage();
        }
    }
}
