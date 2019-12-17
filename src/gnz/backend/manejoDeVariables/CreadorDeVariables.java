/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.manejoDeVariables;

import gnz.backend.cuarteto.Cuarteto;
import gnz.backend.cuarteto.TipoDeCuarteto;
import gnz.backend.nodoDeclaracion.NodoId;
import gnz.backend.nodoDeclaracion.TipoDeVariable;
import gnz.backend.nodoExpresion.NodoHojaExpresion;
import gnz.backend.tablas.Categoria;
import gnz.backend.tablas.TuplaDeSimbolo;
import gnz.gui.frames.EditorDeTextoFrame;
import java.util.LinkedList;

/**
 *
 * @author jesfrin
 */
public class CreadorDeVariables {

    public static void declararVariables(DeclaracionDeVariable declaracion, EditorDeTextoFrame editor) {
        if (declaracion.getTipo() == TipoDeVariable.BOOLEAN) {

        } else if (declaracion.getTipo() == TipoDeVariable.STRING) {

        } else {//Es numerico
            ManejadorDeVariablesNumericas manejador = new ManejadorDeVariablesNumericas(editor);
            for (NodoId nodoId : declaracion.getIds()) {
                NodoHojaExpresion nodoHoja = manejador.evaluarExpresionMatematica(nodoId.getAsignacion());
                if (nodoHoja != null) {
                    //Se verifica que sean de tipos compatibles
                    if (declaracion.getTipo().getJerarquia() >= nodoHoja.getTipoDEVariable().getJerarquia()) {
                        TuplaDeSimbolo tupla = new TuplaDeSimbolo(0, nodoId.getId(), nodoHoja.getTipoDEVariable(), Categoria.Variable, 0, "global");
                        editor.getManTablas().guardarNuevaVariable(tupla, nodoId.getLinea(), nodoId.getColumna());
                        Cuarteto cuartetoAsignacion = new Cuarteto(null, nodoHoja.getValor(), null, nodoId.getId(), TipoDeCuarteto.ASIGNACION);
                        editor.getManTablas().anadirCuarteto(cuartetoAsignacion);
                    } else {//ERROR de conversion de tipos

                    }
                    //Se guarda la variable(Alli se verifica si ya existia)

                }
            }
        }
    }

}
