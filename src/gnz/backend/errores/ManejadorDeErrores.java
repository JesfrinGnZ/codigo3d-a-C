/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.errores;

import javax.swing.JTextArea;

/**
 *
 * @author jesfrin
 */
public class ManejadorDeErrores {

    public static boolean hayError=false;
    
    public static void escribirErrorSemantico(String mensaje,JTextArea textArea){
        textArea.append(mensaje+"\n\n");
        hayError=true;
    }
    
}
