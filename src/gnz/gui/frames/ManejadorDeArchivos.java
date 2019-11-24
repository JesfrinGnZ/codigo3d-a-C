package gnz.gui.frames;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author jesfrin
 */
public class ManejadorDeArchivos {

    public static String leerArchivo(File ruta) throws Exception {
        String texto = "";
        String copiaTexto="";
        FileReader archivos = new FileReader(ruta);
        BufferedReader lee = new BufferedReader(archivos);
        while ((texto = lee.readLine()) != null) {
            texto += "\n";
            copiaTexto+=texto;
        }
        lee.close();
        return copiaTexto;
    }

    public static void escribirArchivo(File direccionDeArchivo, String texto) throws Exception {
        FileWriter save = new FileWriter(direccionDeArchivo);//guardamos el archivo
        save.write(texto);
        save.close();
    }

}
