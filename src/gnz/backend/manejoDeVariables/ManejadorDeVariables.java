/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.manejoDeVariables;

import gnz.backend.nodo.Nodo;
import gnz.backend.nodoComparacion.NodoComparacion;
import gnz.backend.nodoComparacion.NodoLogico;
import gnz.backend.nodoDeclaracion.TipoDeVariable;
import gnz.backend.nodoExpresion.NodoExpresion;
import gnz.backend.nodoExpresion.NodoHojaExpresion;
import gnz.backend.nodoExpresion.TipoDeHoja;

/**
 *
 * @author jesfrin
 */
public class ManejadorDeVariables {

    public TipoDeVariable evaluarExpresionMatematica(Nodo nodo) {
        if (nodo instanceof NodoExpresion) {

        } else if (nodo instanceof NodoHojaExpresion) {
            //Los tipos numericos
            return verificarTipoDeVariableParaExpresionMatematica((NodoHojaExpresion)nodo);
        } else {//NO se puede convertir booleano a numerico

        }

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
                //Buscar en la tabla la variable
                return null;
            case NUMERO_DECIMAL:
                return TipoDeVariable.DOUBLE;
            case NUMERO_DECIMALF:
                return TipoDeVariable.FLOAT;
            case NUMERO_ENTERO:
                return verificarTipoDeVariableEntero(nodoHoja.getValor());
            default://Error elemento no es numerico
                return null;
        }
    }

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

    public void evaluarExpresionCadena(Nodo nodo) {
        if (nodo instanceof NodoExpresion) {

        } else if (nodo instanceof NodoHojaExpresion) {

        } else {//No se puede convertir booleano a cadena

        }
    }

    public void evaluarExpresionBooleana(Nodo nodo) {
        if (nodo instanceof NodoLogico) {

        } else if (nodo instanceof NodoComparacion) {

        } else if (nodo instanceof NodoComparacion) {

        } else {//Error no se puede convertir expresion a booleano

        }
    }

}
