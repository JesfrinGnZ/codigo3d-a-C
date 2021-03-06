/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.manejoDeVariables;

import gnz.backend.cuarteto.Cuarteto;
import gnz.backend.cuarteto.TipoDeCuarteto;
import gnz.backend.nodo.Nodo;
import gnz.backend.nodoDeclaracion.TipoDeVariable;
import gnz.backend.nodoExpresion.NodoHojaExpresion;
import gnz.backend.nodoExpresion.OperacionAritmetica;
import gnz.gui.frames.EditorDeTextoFrame;
import java.util.LinkedList;

/**
 *
 * @author jesfrin
 */
public class ManejadorDeExpresionesParaArreglos {

    public static LinkedList<NodoHojaExpresion> evaluarNodosHoja(LinkedList<Nodo> expresionesDeNodoHoja,EditorDeTextoFrame editor, String ambito) {
        LinkedList<NodoHojaExpresion> nodosHoja = new LinkedList<>();
        ManejadorDeExpresionesNumericas manNumerico = new ManejadorDeExpresionesNumericas(editor, ambito);
        for (Nodo expresion : expresionesDeNodoHoja) {
            NodoHojaExpresion nodHojaExpresion = manNumerico.evaluarExpresionMatematica(expresion);
            if (nodHojaExpresion != null) {
                nodosHoja.add(nodHojaExpresion);
            }else{
                nodosHoja=null;
                break;
            }
        }
        return nodosHoja;
    }

    public static String evaluarArreglo(LinkedList<NodoHojaExpresion> dimensionesArreglo, LinkedList<NodoHojaExpresion> hojasEvaluadas, EditorDeTextoFrame editor) {
        //Multiplicaciones
        int dondeSeEMpezara = 1;
        String ultimaTemporal = "";
        LinkedList<String> temporalesDeSuma = new LinkedList<>();
        for (NodoHojaExpresion hojasEvaluada : hojasEvaluadas) {
            for (int i = dondeSeEMpezara; i < dimensionesArreglo.size(); i++) {
                if (i == dondeSeEMpezara) {
                    String numTemporal = "t" + String.valueOf(editor.getManTablas().obtenerNuevoTemporal());
                    Cuarteto nuevoCuarteto = new Cuarteto(OperacionAritmetica.POR.getSigno(), hojasEvaluada.getValor() + hojasEvaluada.getAmbito(), dimensionesArreglo.get(i).getValor(), numTemporal, TipoDeCuarteto.SOLO_EXPRESION,TipoDeVariable.INT);
                    ultimaTemporal = numTemporal;
                    editor.getManTablas().anadirCuarteto(nuevoCuarteto);
                } else {
                    String numTemporal = "t" + String.valueOf(editor.getManTablas().obtenerNuevoTemporal());
                    Cuarteto nuevoCuarteto = new Cuarteto(OperacionAritmetica.POR.getSigno(), ultimaTemporal + hojasEvaluada.getAmbito(), dimensionesArreglo.get(i).getValor(), numTemporal, TipoDeCuarteto.SOLO_EXPRESION,TipoDeVariable.INT);
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

        //Sumas
        if (temporalesDeSuma.size() >= 2) {
            String numTemporal = "t" + String.valueOf(editor.getManTablas().obtenerNuevoTemporal());
            Cuarteto nuevoCuarteto = new Cuarteto(OperacionAritmetica.MAS.getSigno(), temporalesDeSuma.getFirst(), temporalesDeSuma.get(1), numTemporal, TipoDeCuarteto.SOLO_EXPRESION,TipoDeVariable.INT);
            editor.getManTablas().anadirCuarteto(nuevoCuarteto);
            ultimaTemporal = numTemporal;
            //Para los que siguen de 2
            for (int i = 2; i < temporalesDeSuma.size(); i++) {
                numTemporal = "t" + String.valueOf(editor.getManTablas().obtenerNuevoTemporal());
                nuevoCuarteto = new Cuarteto(OperacionAritmetica.MAS.getSigno(), ultimaTemporal, temporalesDeSuma.get(i), numTemporal, TipoDeCuarteto.SOLO_EXPRESION,TipoDeVariable.INT);
                editor.getManTablas().anadirCuarteto(nuevoCuarteto);
                ultimaTemporal = numTemporal;
            }
            return ultimaTemporal;
        } else {
            return temporalesDeSuma.getLast();
        }

    }
}
