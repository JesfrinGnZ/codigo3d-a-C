/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.manejoDeVariables;

import gnz.backend.cuarteto.Cuarteto;
import gnz.backend.cuarteto.TipoDeCuarteto;
import gnz.backend.nodoExpresion.NodoHojaExpresion;
import gnz.backend.nodoExpresion.OperacionAritmetica;
import gnz.gui.frames.EditorDeTextoFrame;
import java.util.LinkedList;

/**
 *
 * @author jesfrin
 */
public class ManejadorDeExpresionesParaArreglos {

    
    public static String evaluarArreglo(LinkedList<NodoHojaExpresion> dimensionesArreglo, LinkedList<NodoHojaExpresion> hojasEvaluadas, EditorDeTextoFrame editor) {
        int dondeSeEMpezara = 1;
        String ultimaTemporal = "";
        LinkedList<String> temporalesDeSuma = new LinkedList<>();
        for (NodoHojaExpresion hojasEvaluada : hojasEvaluadas) {
            for (int i = dondeSeEMpezara; i < dimensionesArreglo.size(); i++) {
                if (i == dondeSeEMpezara) {
                    String numTemporal = "t" + String.valueOf(editor.getManTablas().obtenerNuevoTemporal());
                    Cuarteto nuevoCuarteto = new Cuarteto(OperacionAritmetica.POR.getSigno(), hojasEvaluada.getValor() + hojasEvaluada.getAmbito(), dimensionesArreglo.get(i).getValor(), numTemporal, TipoDeCuarteto.SOLO_EXPRESION);
                    ultimaTemporal = numTemporal;
                    editor.getManTablas().anadirCuarteto(nuevoCuarteto);
                } else {
                    String numTemporal = "t" + String.valueOf(editor.getManTablas().obtenerNuevoTemporal());
                    Cuarteto nuevoCuarteto = new Cuarteto(OperacionAritmetica.POR.getSigno(), ultimaTemporal + hojasEvaluada.getAmbito(), dimensionesArreglo.get(i).getValor(), numTemporal, TipoDeCuarteto.SOLO_EXPRESION);
                    ultimaTemporal = numTemporal;
                    editor.getManTablas().anadirCuarteto(nuevoCuarteto);
                }
                if (i + 1 == dimensionesArreglo.size()) {
                    temporalesDeSuma.add(ultimaTemporal);
                }
            }
            dondeSeEMpezara++;
        }
        temporalesDeSuma.add(hojasEvaluadas.getLast().getValor() + hojasEvaluadas.getLast().getAmbito());

        if (temporalesDeSuma.size() >= 2) {
            String numTemporal = "t" + String.valueOf(editor.getManTablas().obtenerNuevoTemporal());
            Cuarteto nuevoCuarteto = new Cuarteto(OperacionAritmetica.MAS.getSigno(), temporalesDeSuma.getFirst(), temporalesDeSuma.get(1), numTemporal, TipoDeCuarteto.SOLO_EXPRESION);
            editor.getManTablas().anadirCuarteto(nuevoCuarteto);
            ultimaTemporal = numTemporal;
            for (int i = 2; i < temporalesDeSuma.size(); i++) {
                numTemporal = "t" + String.valueOf(editor.getManTablas().obtenerNuevoTemporal());
                nuevoCuarteto = new Cuarteto(OperacionAritmetica.POR.getSigno(), ultimaTemporal, temporalesDeSuma.get(i), numTemporal, TipoDeCuarteto.SOLO_EXPRESION);
                editor.getManTablas().anadirCuarteto(nuevoCuarteto);
                ultimaTemporal = numTemporal;
            }
            return ultimaTemporal;
        } else {
            return temporalesDeSuma.getLast();
        }

    }
}
