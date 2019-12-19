/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.manejoDeVariables;

import gnz.backend.cuarteto.Cuarteto;
import gnz.backend.cuarteto.TipoDeCuarteto;
import gnz.backend.errores.ManejadorDeErrores;
import gnz.backend.nodo.Nodo;
import gnz.backend.nodoDeclaracion.TipoDeVariable;
import gnz.backend.nodoExpresion.NodoExpresion;
import gnz.backend.nodoExpresion.NodoHojaExpresion;
import gnz.backend.nodoExpresion.OperacionAritmetica;
import gnz.backend.nodoExpresion.TipoDeHoja;
import gnz.backend.tablas.TuplaDeSimbolo;
import gnz.gui.frames.EditorDeTextoFrame;

/**
 *
 * @author jesfrin
 */
public class ManejadorDeExpresionesString {

    private EditorDeTextoFrame editor;
    private String ambito;
    private int contadorDeString;

    public ManejadorDeExpresionesString(EditorDeTextoFrame editor, String ambito) {
        this.editor = editor;
        this.contadorDeString = 0;
        this.ambito = ambito;
    }

    /**
     * Evalua la expresion cadena
     *
     * @param nodo
     * @return
     */
    public NodoHojaExpresion evaluarExpresionCadena(Nodo nodo) {
        NodoHojaExpresion nodoHoja = null;
        if (nodo instanceof NodoExpresion) {
            NodoHojaExpresion nodoHoja2 = recorrerExpresion((NodoExpresion) nodo);
            if (nodoHoja2 != null) {
                if (contadorDeString > 0) {
                    nodoHoja = nodoHoja2;
                } else {//Error falto String 
                    String mensaje = "Error SEMANTICO, falta un elemento String no se puede convertir a Cadena.\n" + CreadorDeVariables.averiguarTipoDeNodo(nodo);
                    ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                }
            }
        } else if (nodo instanceof NodoHojaExpresion) {
            verificarSiVariableEsDeTipoString((NodoHojaExpresion) nodo);
            if (contadorDeString > 0) {
                nodoHoja = new NodoHojaExpresion(TipoDeVariable.STRING, ((NodoHojaExpresion) nodo).getValor());
            } else {//Error falto String
                String mensaje = "Error SEMANTICO, falta un elemento String no se puede convertir a Cadena.\n" + CreadorDeVariables.averiguarTipoDeNodo(nodo);
                ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
            }
        } else {//No se puede convertir booleano a cadena
            String mensaje = "Error SEMANTICO, no se puede convertir booleano a cadena.\n" + CreadorDeVariables.averiguarTipoDeNodo(nodo);
            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
        }
        return nodoHoja;
    }

    /**
     * Verifica que el nodo hoja sea String
     *
     * @param nodoHoja
     */
    private void verificarSiVariableEsDeTipoString(NodoHojaExpresion nodoHoja) {
        TipoDeHoja tipo = nodoHoja.getTipo();
        switch (tipo) {
            case IDENTIFICADOR:
                TuplaDeSimbolo tupla = editor.getManTablas().buscarVariable(nodoHoja.getValor(), ambito);
                if (tupla != null) {
                    if (tupla.getTipo() == TipoDeVariable.STRING) {
                        this.contadorDeString++;
                    }
                } else {
                    tupla = editor.getManTablas().buscarVariable(nodoHoja.getValor(), "global");
                    if (tupla != null) {
                        if (tupla.getTipo() == TipoDeVariable.STRING) {
                            this.contadorDeString++;
                        }
                    } else {//Error la variable no ha sido declarada
                        String mensaje = "Error SEMANTICO, la variable " + nodoHoja.getValor() + " No ha sido declarada.\nLinea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                        ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                    }
                }
                break;
            case DECLARACION_STRING:
                int fin = nodoHoja.getValor().length() - 1;
                nodoHoja.setValor(nodoHoja.getValor().substring(1, fin));
                this.contadorDeString++;
            default:
                break;
        }
    }

    public NodoHojaExpresion recorrerExpresion(Nodo nodo) {
        if (nodo instanceof NodoHojaExpresion) {
            verificarSiVariableEsDeTipoString((NodoHojaExpresion) nodo);
            return (NodoHojaExpresion) nodo;
        } else {//Nodo expresion
            NodoExpresion nodoExpresion = (NodoExpresion) nodo;
            NodoHojaExpresion nodo1 = recorrerExpresion(nodoExpresion.getHoja1());
            NodoHojaExpresion nodo2 = recorrerExpresion(nodoExpresion.getHoja2());
            if (nodo1 != null && nodo2 != null) {
                if (nodoExpresion.getOperacion() == OperacionAritmetica.MAS) {
                    String numTemporal = "t" + String.valueOf(this.editor.getManTablas().obtenerNuevoTemporal());
                    Cuarteto nuevoCuarteto = new Cuarteto(nodoExpresion.getOperacion().getSigno(), nodo1.getValor(), nodo2.getValor(), numTemporal, TipoDeCuarteto.SOLO_EXPRESION);
                    this.editor.getManTablas().anadirCuarteto(nuevoCuarteto);
                    return new NodoHojaExpresion(TipoDeVariable.STRING, numTemporal);
                } else {//Error la operacion no es valida para cadena
                    String mensaje = "Error SEMANTICO, operacion " + nodoExpresion.getOperacion() + "(" + nodoExpresion.getOperacion().getSigno() + ")" + " No es valida para String.\n en Linea:" + nodoExpresion.getLinea() + " Columna:" + nodoExpresion.getColumna();
                    ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                }
            }

        }
        return null;
    }

    public int getContadorDeString() {
        return contadorDeString;
    }

}