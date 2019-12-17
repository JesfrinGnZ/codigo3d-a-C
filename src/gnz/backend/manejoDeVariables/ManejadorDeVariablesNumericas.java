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
import gnz.backend.nodoExpresion.NodoExpresion;
import gnz.backend.nodoExpresion.NodoHojaExpresion;
import gnz.backend.nodoExpresion.TipoDeHoja;
import gnz.backend.tablas.TuplaDeSimbolo;
import gnz.gui.frames.EditorDeTextoFrame;

/**
 *
 * @author jesfrin
 */
public class ManejadorDeVariablesNumericas {

    private EditorDeTextoFrame editor;

    public ManejadorDeVariablesNumericas(EditorDeTextoFrame editor) {
        this.editor = editor;
    }

    public ManejadorDeVariablesNumericas() {

    }

    /**
     * Debe devolver tipo y valor
     *
     * @param nodo
     * @return
     */
    public NodoHojaExpresion evaluarExpresionMatematica(Nodo nodo) {
        NodoHojaExpresion nodoHoja = null;
        if (nodo instanceof NodoExpresion) {
            nodoHoja = recorrerExpresion(nodo);
        } else if (nodo instanceof NodoHojaExpresion) {
            //Los tipos numericos
            TipoDeVariable tipo = verificarTipoDeVariableParaExpresionMatematica((NodoHojaExpresion) nodo);
            nodoHoja = new NodoHojaExpresion(tipo, ((NodoHojaExpresion) nodo).getValor());
        } else {// ERROR NO se puede convertir booleano a numerico

        }
        return nodoHoja;
    }

    /**
     * Evalua si la hoja es del tipo correcto(algun numerico), y devuelve el
     * tipo de variable
     *
     * @param nodoHoja
     * @return
     */
    private TipoDeVariable verificarTipoDeVariableParaExpresionMatematica(NodoHojaExpresion nodoHoja) {
        TipoDeHoja tipo = nodoHoja.getTipo();
        switch (tipo) {
            case IDENTIFICADOR:
                TuplaDeSimbolo tupla = editor.getManTablas().buscarVariable(nodoHoja.getValor());
                if (tupla != null) {
                    TipoDeVariable tipoDeVariable = tupla.getTipo();
                    if (tipoDeVariable == TipoDeVariable.BYTE || tipoDeVariable == TipoDeVariable.CHAR || tipoDeVariable == TipoDeVariable.DOUBLE || tipoDeVariable == TipoDeVariable.FLOAT
                            || tipoDeVariable == TipoDeVariable.INT || tipoDeVariable == TipoDeVariable.LONG) {
                        return tipoDeVariable;
                    }
                } else {//ERROR la variable no ha sido declarada
                    return null;
                }
                break;
            case NUMERO_DECIMAL:
                return TipoDeVariable.DOUBLE;
            case NUMERO_DECIMALF:
                return TipoDeVariable.FLOAT;
            case NUMERO_ENTERO:
                return verificarTipoDeVariableEntero(nodoHoja.getValor());
            default:
                break;
        }
        //ERROR elemento no es numerico
        return null;
    }

    /**
     * Verifica el tipo de variable para entero
     *
     * @param valor
     * @return
     */
    private TipoDeVariable verificarTipoDeVariableEntero(String valor) {
        Long numero = Long.valueOf(valor);
        if (numero >= TipoDeVariable.CHAR.getLimiteInferior() && numero <= TipoDeVariable.CHAR.getLimiteSuperior()) {
            return TipoDeVariable.CHAR;
        } else if (numero >= TipoDeVariable.BYTE.getLimiteInferior() && numero <= TipoDeVariable.BYTE.getLimiteSuperior()) {
            return TipoDeVariable.BYTE;
        } else if (numero >= TipoDeVariable.INT.getLimiteInferior() && numero <= TipoDeVariable.INT.getLimiteSuperior()) {
            return TipoDeVariable.INT;
        } else {
            return TipoDeVariable.LONG;
        }
    }

    /**
     * Recorre el arbol
     *
     * @param nodo
     * @return
     */
    private NodoHojaExpresion recorrerExpresion(Nodo nodo) {
        if (nodo instanceof NodoHojaExpresion) {
            TipoDeVariable tipo = verificarTipoDeVariableParaExpresionMatematica((NodoHojaExpresion) nodo);
            if (tipo != null) {
                ((NodoHojaExpresion) nodo).setTipoDEVariable(tipo);
                return (NodoHojaExpresion) nodo;
            }
            return null;
        } else {//NodoExpresion
            NodoExpresion nodoExpresion = (NodoExpresion) nodo;
            NodoHojaExpresion nodo1 = recorrerExpresion(nodoExpresion.getHoja1());
            NodoHojaExpresion nodo2 = recorrerExpresion(nodoExpresion.getHoja2());
            if (nodo1 != null && nodo2 != null) {
                //Se evaluan los tipos
                TipoDeVariable tipoMayor = buscarElMayorTipoDeVariable(nodo1.getTipoDEVariable(), nodo2.getTipoDEVariable());
                //Se crea un cuarteto
                String numTemporal = "t" + String.valueOf(this.editor.getManTablas().obtenerNuevoTemporal());
                Cuarteto nuevoCuarteto = new Cuarteto(nodoExpresion.getOperacion().getSigno(), nodo1.getValor(), nodo2.getValor(), numTemporal, TipoDeCuarteto.SOLO_EXPRESION);
                this.editor.getManTablas().anadirCuarteto(nuevoCuarteto);
                return new NodoHojaExpresion(tipoMayor, numTemporal);
            }
            return null;
        }
    }

    /**
     * Compara los tipos de variable para ver el mayor
     *
     * @param var1
     * @param var2
     * @return
     */
    private TipoDeVariable buscarElMayorTipoDeVariable(TipoDeVariable var1, TipoDeVariable var2) {
        if (var1.getJerarquia() >= var2.getJerarquia()) {
            return var1;
        } else {
            return var2;
        }
    }

}
