import java.io.*;
import java.net.*;
import org.json.*;

public class ApiMedicamentos extends ServicioApi {

    private static final String URL_FDA = "https://api.fda.gov/drug/event.json";          //obtiene la API de la fda
    private static final String URL_TRADUCTOR = "https://api.mymemory.translated.net/get";//Obtiene la API del traductor

@Override   //sobrescribe el metodo abstracto de la clase ServicioApi
public String consultar(String nombre) {
        try {
            String urlStr = URL_FDA +   //Construye la URL para consultar la API de la fda
                    "?search=patient.drug.medicinalproduct:" +
                    URLEncoder.encode(nombre, "UTF-8") +
                    "&limit=5";     //limita la busqueda a 5 resultados

            URL url = new URL(urlStr);      //crea el objeto URL
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();      //abre la conexion con la URL mediante HttpURLConnection
            conexion.setRequestMethod("GET")    ;//establece el metodo de la peticion como GET

            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));       //lee la respuesta que le da la API
            StringBuilder respuesta = new StringBuilder();      //almacena la respuesta en un StringBuilder
            String line;

            while ((line = in.readLine()) != null) {    //lee linea por linea la respuesta
                respuesta.append(line);     //y la agrega al StringBuilder
            }
            in.close();

            return procesarJson(respuesta.toString());  //procesa el JSON y devuelve los efectos secundarios traducidos

        } catch (Exception e) {
            return "Error al obtener datos: " + e.getMessage();
        }   //Cierre del try, manejando posibles errores
        }

            private String traducir(String texto) {     //Metodo que va a traducir el texto del ingles al español usando la API del traductor
        try {
            String urlStr = URL_TRADUCTOR +     //Construye la URL para consultar la aAPI del traductor
                    "?q=" + URLEncoder.encode(texto, "UTF-8") +
                    "&langpair=en|es";      //traduce de ingles a español con langpair, que indica el par de idiomas

            URL url = new URL(urlStr);      //Hace lo mismo que con la API de la fda
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));    //Igual que con la API de la fda, lee la respuesta del api del traductor
            StringBuilder respuesta = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                respuesta.append(line);
            }
            in.close();

            JSONObject json = new JSONObject(respuesta.toString());     //procesa el JSON recibido
            return json.getJSONObject("responseData").getString("translatedText");      //obtiene el texto traducido

        } catch (Exception e) {
            return "(sin traducción)";
        }   //Cierre del try, manejando posibles errores
    }

    private String procesarJson(String jsonStr) {   //procesa el JSON recibido de la API de la FDA
        try {
            JSONObject json = new JSONObject(jsonStr);

            if (!json.has("results")) {     //verifica si el JSON tiene la clave "results", si no devuelve un mensaje diciendo que no se encontraron datos
                return "No se encontraron datos.";
            }

            JSONArray resultados = json.getJSONArray("results");    //obtiene el array de resultados
            StringBuilder efectos = new StringBuilder();    //almacena los efectos secundarios

            for (int i = 0; i < resultados.length(); i++) {     //recorre cada resultado
                JSONObject evento = resultados.getJSONObject(i);
                JSONObject paciente = evento.getJSONObject("patient");      //obtiene la informacion del paciente, Para acceder a los efectos secundarios, debido a que toma a los medicamentos como parte de la informacion del paciente

                if (paciente.has("reaction")) {     //verifica si el paciente tiene efectos secundarios, 
                    JSONArray reacciones = paciente.getJSONArray("reaction");

                    for (int j = 0; j < reacciones.length(); j++) {     //recorre cada efecto secundario
                        JSONObject reaccion = reacciones.getJSONObject(j);      //obtiene el efecto secundario
                        String termino = reaccion.getString("reactionmeddrapt");    //obtiene el nombre del efecto secundario

                        efectos.append("- ")
                                .append(termino)
                                .append(" → ")
                                .append(traducir(termino))
                                .append("\n");      //traduce el efecto secundario y lo agrega al StringBuilder
                    }
                }
            }

            return efectos.toString();      //devuelve los efectos secundarios traducidos

        } catch (Exception e) {
            return "Error procesando JSON: " + e.getMessage();
        }   //Cierre del try, manejando posibles errores
    }
}
   
