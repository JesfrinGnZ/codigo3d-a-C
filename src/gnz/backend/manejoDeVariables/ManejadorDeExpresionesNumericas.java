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
import gnz.backend.nodoExpresion.TipoDeHoja;
import gnz.backend.tablas.Categoria;
import gnz.backend.tablas.TuplaDeSimbolo;
import gnz.gui.frames.EditorDeTextoFrame;
import java.util.LinkedList;

/**
 *
 * @author jesfrin
 */
public class ManejadorDeExpresionesNumericas {

    private EditorDeTextoFrame editor;
    private String ambito;
    private String ambitoActualDeVariable = "";

    public ManejadorDeExpresionesNumericas(EditorDeTextoFrame editor, String ambito) {
        this.editor = editor;
        this.ambito = ambito;
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
            if (tipo != null) {
                nodoHoja = new NodoHojaExpresion(tipo, ((NodoHojaExpresion) nodo).getValor() + ambitoActualDeVariable);
            }
        } else {// ERROR NO se puede convertir booleano a numerico
            String mensaje = "Error SEMANTICO, no se puede convertir booleano a numerico.\n" + CreadorDeVariables.averiguarTipoDeNodo(nodo);
            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
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
                TuplaDeSimbolo tupla = editor.getManTablas().buscarVariable(nodoHoja.getValor(), ambito);//Puede que devuelva un arreglo
                if (tupla != null) {
                    if (tupla.getCategoria() == Categoria.Variable && nodoHoja.getExpresiones() == null) {
                        TipoDeVariable tipoDeVariable = tupla.getTipo();
                        if (tipoDeVariable == TipoDeVariable.BYTE || tipoDeVariable == TipoDeVariable.CHAR || tipoDeVariable == TipoDeVariable.DOUBLE || tipoDeVariable == TipoDeVariable.FLOAT
                                || tipoDeVariable == TipoDeVariable.INT || tipoDeVariable == TipoDeVariable.LONG) {
                            ambitoActualDeVariable = ambito;
                            return tipoDeVariable;
                        }
                    } else if (tupla.getCategoria() == Categoria.Arreglo && nodoHoja.getExpresiones() != null) {
                        TipoDeVariable tipoDeVariable = tupla.getTipo();
                        if (tipoDeVariable == TipoDeVariable.BYTE || tipoDeVariable == TipoDeVariable.CHAR || tipoDeVariable == TipoDeVariable.DOUBLE || tipoDeVariable == TipoDeVariable.FLOAT
                                || tipoDeVariable == TipoDeVariable.INT || tipoDeVariable == TipoDeVariable.LONG) {
                            if (nodoHoja.getExpresiones().size() == tupla.getNumeroDimensiones()) {//Misma dimension
                                LinkedList<NodoHojaExpresion> nodosHoja = ManejadorDeExpresionesParaArreglos.evaluarNodosHoja(nodoHoja.getExpresiones(), editor, ambito);
                                if (nodosHoja != null) {
                                    String valor = ManejadorDeExpresionesParaArreglos.evaluarArreglo(tupla.getDimensionesArreglo(), nodosHoja, editor);
                                    String nuevaTemporal = "t" + editor.getManTablas().obtenerNuevoTemporal();
                                    Cuarteto cuartetoAsignacion = new Cuarteto(null, nuevaTemporal, null, nodoHoja.getValor() + ambito + "[" + valor + "]", TipoDeCuarteto.ASIGNACION);
                                    editor.getManTablas().anadirCuarteto(cuartetoAsignacion);
                                    nodoHoja.setValor(nuevaTemporal);
                                    ambitoActualDeVariable = "";
                                    return tipoDeVariable;
                                }
                            } else {//Error de dimensiones
                                String mensaje = "Error SEMANTICO, dimensiones incorrectas para arreglo " + nodoHoja.getValor() + ".\nLinea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                                ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                                return null;
                            }
                        }

                    } else {//Error de conversion de tipos
                        String mensaje = "Error SEMANTICO, el elemento " + nodoHoja.getValor() + " NO se puede convertir a numerico.\nLinea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                        ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                        return null;
                    }

                } else {
                    tupla = editor.getManTablas().buscarVariable(nodoHoja.getValor(), "global");
                    if (tupla != null) {
                        if (tupla.getCategoria() == Categoria.Variable && nodoHoja.getExpresiones() == null) {
                            TipoDeVariable tipoDeVariable = tupla.getTipo();
                            if (tipoDeVariable == TipoDeVariable.BYTE || tipoDeVariable == TipoDeVariable.CHAR || tipoDeVariable == TipoDeVariable.DOUBLE || tipoDeVariable == TipoDeVariable.FLOAT
                                    || tipoDeVariable == TipoDeVariable.INT || tipoDeVariable == TipoDeVariable.LONG) {
                                ambitoActualDeVariable = "global";
                                return tipoDeVariable;
                            }
                        } else if (tupla.getCategoria() == Categoria.Arreglo && nodoHoja.getExpresiones() != null) {
                            TipoDeVariable tipoDeVariable = tupla.getTipo();
                            if (tipoDeVariable == TipoDeVariable.BYTE || tipoDeVariable == TipoDeVariable.CHAR || tipoDeVariable == TipoDeVariable.DOUBLE || tipoDeVariable == TipoDeVariable.FLOAT
                                    || tipoDeVariable == TipoDeVariable.INT || tipoDeVariable == TipoDeVariable.LONG) {
                                if (nodoHoja.getExpresiones().size() == tupla.getNumeroDimensiones()) {//Misma dimension
                                    LinkedList<NodoHojaExpresion> nodosHoja = ManejadorDeExpresionesParaArreglos.evaluarNodosHoja(nodoHoja.getExpresiones(), editor, ambito);
                                    if (nodosHoja != null) {
                                        String valor = ManejadorDeExpresionesParaArreglos.evaluarArreglo(tupla.getDimensionesArreglo(), nodosHoja, editor);
                                        String nuevaTemporal = "t" + editor.getManTablas().obtenerNuevoTemporal();
                                        Cuarteto cuartetoAsignacion = new Cuarteto(null, nuevaTemporal, null, nodoHoja.getValor() + "global" + "[" + valor + "]", TipoDeCuarteto.ASIGNACION);
                                        editor.getManTablas().anadirCuarteto(cuartetoAsignacion);
                                        nodoHoja.setValor(nuevaTemporal);
                                        ambitoActualDeVariable = "";
                                        return tipoDeVariable;
                                    }
                                } else {//Error de dimensiones
                                    String mensaje = "Error SEMANTICO, dimensiones incorrectas para arreglo " + nodoHoja.getValor() + ".\nLinea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                                    ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                                    return null;
                                }
                            }

                        } else {//Error de conversion de tipos
                            String mensaje = "Error SEMANTICO, el elemento " + nodoHoja.getValor() + " No se puede convertir a numerico.\nLinea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                            return null;
                        }

                    } else {//ERROR la variable no ha sido declarada
                        String mensaje = "Error SEMANTICO, la variable " + nodoHoja.getValor() + " No ha sido declarada.\nLinea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                        ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                        return null;
                    }

                }
                break;
            case NUMERO_DECIMAL:
                ambitoActualDeVariable = "";
                return TipoDeVariable.DOUBLE;
            case NUMERO_DECIMALF:
                ambitoActualDeVariable = "";
                return TipoDeVariable.FLOAT;
            case NUMERO_ENTERO:
                ambitoActualDeVariable = "";
                return verificarTipoDeVariableEntero(nodoHoja.getValor());
            default:
                break;
        }
        //ERROR elemento no es numerico
        String mensaje = "Error SEMANTICO, no se puede convertir " + nodoHoja.getTipo() + "(" + nodoHoja.getValor() + ")" + " a numerico.\nLinea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
        ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
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
                ((NodoHojaExpresion) nodo).setAmbito(ambitoActualDeVariable);
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
                Cuarteto nuevoCuarteto = new Cuarteto(nodoExpresion.getOperacion().getSigno(), nodo1.getValor() + nodo1.getAmbito(), nodo2.getValor() + nodo2.getAmbito(), numTemporal, TipoDeCuarteto.SOLO_EXPRESION);
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
