package src; 
import java.util.ArrayList;

public class CMD {
	// Método de ejemplo para ejecutar un comando de CMD
    public static ArrayList <String> ejecutarComando(String comando) {
        ArrayList <String> salida = new ArrayList<>();

        try {
            Process proceso = Runtime.getRuntime().exec(comando);
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(proceso.getInputStream()));

            String linea;
            while ((linea = reader.readLine()) != null) {
                salida.add(linea); // Acumula la salida
            }
            reader.close();

            // También podrías esperar a que el proceso termine (opcional)
            proceso.waitFor();

        } catch (Exception e) {
            salida.add("Error al ejecutar el comando:" + e.getMessage());
        }

        return salida;
    }
}
