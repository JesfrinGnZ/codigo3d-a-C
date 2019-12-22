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
import gnz.backend.nodoComparacion.NodoComparacion;
import gnz.backend.nodoComparacion.NodoLogico;
import gnz.backend.nodoComparacion.OperacionLogica;
import gnz.backend.nodoDeclaracion.TipoDeVariable;
import gnz.backend.nodoExpresion.NodoHojaExpresion;
import gnz.backend.nodoExpresion.TipoDeHoja;
import gnz.backend.tablas.Categoria;
import gnz.backend.tablas.TuplaDeSimbolo;
import gnz.gui.frames.EditorDeTextoFrame;

/**
 *
 * @author jesfrin
 */
public class ManejadorDeExpresionesBooleanas {

    private EditorDeTextoFrame editor;
    private String ambito;

    public ManejadorDeExpresionesBooleanas(EditorDeTextoFrame editor, String ambito) {
        this.editor = editor;
        this.ambito = ambito;
    }

    public Cuarteto evaluarExpresionBooleana(Nodo nodo) {
        Cuarteto cuarteto = null;
        if (nodo instanceof NodoLogico) {
            cuarteto = recorrerNodoLogico(nodo);
        } else if (nodo instanceof NodoComparacion) {
            cuarteto = recorrerNodoComparacion((NodoComparacion) nodo);
        } else if (nodo instanceof NodoHojaExpresion) {
            cuarteto = verificarSiVariableEsBooleana((NodoHojaExpresion) nodo);
        } else {//Error no se puede convertir expresion a booleano
            String mensaje = "Error SEMANTICO, no se puede convertir expresion a boleano.\n" + CreadorDeVariables.averiguarTipoDeNodo(nodo);
            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
        }
        return cuarteto;
    }

    public Cuarteto verificarSiVariableEsBooleana(NodoHojaExpresion nodoHoja) {
        TipoDeHoja tipo = nodoHoja.getTipo();
        switch (tipo) {
            case IDENTIFICADOR:
                //Primero en su ambito
                TuplaDeSimbolo tupla = editor.getManTablas().buscarVariable(nodoHoja.getValor(), ambito);
                if (tupla != null) {
                    if (tupla.getCategoria() == Categoria.Variable) {
                        if (tupla.getTipo() == TipoDeVariable.BOOLEAN) {
                            String labelSi = "L" + this.editor.getManTablas().obtenerNuevoNumeroDeLabel();
                            String labelNo = "L" + this.editor.getManTablas().obtenerNuevoNumeroDeLabel();
                            Cuarteto cuartetoIf = new Cuarteto("==", tupla.getNombre() + tupla.getAmbito(), "1", labelSi, TipoDeCuarteto.IF);
                            Cuarteto cuartetoGoto = new Cuarteto("goto", labelSi, null, labelNo, TipoDeCuarteto.GOTO);
                            this.editor.getManTablas().anadirCuarteto(cuartetoIf);
                            this.editor.getManTablas().anadirCuarteto(cuartetoGoto);
                            return cuartetoGoto;
                        }
                    } else {//Error de conversion de tipos
                        String mensaje = "Error SEMANTICO, el elemento " + nodoHoja.getValor() + " No es una Variable.\nLinea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                        ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                        return null;
                    }

                } else {//En el ambito global
                    tupla = editor.getManTablas().buscarVariable(nodoHoja.getValor(), "global");
                    if (tupla != null) {
                        if (tupla.getCategoria() == Categoria.Variable) {
                            if (tupla.getTipo() == TipoDeVariable.BOOLEAN) {
                                String labelSi = "L" + this.editor.getManTablas().obtenerNuevoNumeroDeLabel();
                                String labelNo = "L" + this.editor.getManTablas().obtenerNuevoNumeroDeLabel();
                                Cuarteto cuartetoIf = new Cuarteto("==", tupla.getNombre() + "global", "1", labelSi, TipoDeCuarteto.IF);
                                Cuarteto cuartetoGoto = new Cuarteto("goto", labelSi, null, labelNo, TipoDeCuarteto.GOTO);
                                this.editor.getManTablas().anadirCuarteto(cuartetoIf);
                                this.editor.getManTablas().anadirCuarteto(cuartetoGoto);
                                return cuartetoGoto;
                            }
                        } else {//Error de conversion de tipos
                            String mensaje = "Error SEMANTICO, el elemento " + nodoHoja.getValor() + " No es una Variable.\nLinea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                            return null;
                        }

                    } else {//Error la variable no ha sido declarada
                        String mensaje = "Error SEMANTICO, la variable " + nodoHoja.getValor() + " No ha sido declarada.\nLinea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                        ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                        return null;
                    }
                }
                break;
            case TRUE:
                String labelSi = "L" + this.editor.getManTablas().obtenerNuevoNumeroDeLabel();
                String labelNo = "L" + this.editor.getManTablas().obtenerNuevoNumeroDeLabel();
                Cuarteto cuartetoIf = new Cuarteto("==", "1", "1", labelSi, TipoDeCuarteto.IF);
                Cuarteto cuartetoGotoTrue = new Cuarteto("goto", labelSi, null, labelNo, TipoDeCuarteto.GOTO);
                this.editor.getManTablas().anadirCuarteto(cuartetoIf);
                this.editor.getManTablas().anadirCuarteto(cuartetoGotoTrue);
                return cuartetoGotoTrue;
            case FALSE:
                String labelS = "L" + this.editor.getManTablas().obtenerNuevoNumeroDeLabel();
                String labelN = "L" + this.editor.getManTablas().obtenerNuevoNumeroDeLabel();
                Cuarteto cuartetoIf2 = new Cuarteto("==", "1", "0", labelS, TipoDeCuarteto.IF);
                Cuarteto cuartetoGotoFalse = new Cuarteto("goto", labelS, null, labelN, TipoDeCuarteto.GOTO);
                this.editor.getManTablas().anadirCuarteto(cuartetoIf2);
                this.editor.getManTablas().anadirCuarteto(cuartetoGotoFalse);
                return cuartetoGotoFalse;
            default:
                break;
        }
        //Error no es de tipo booleano
        String mensaje = "Error SEMANTICO, no se puede convertir " + nodoHoja.getTipo() + "(" + nodoHoja.getValor() + ")" + " a boleano.\nLinea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
        ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
        return null;
    }

    private Cuarteto recorrerNodoComparacion(NodoComparacion nodoComparacion) {
        ManejadorDeExpresionesNumericas manejadorNumerico = new ManejadorDeExpresionesNumericas(editor, ambito);
        NodoHojaExpresion nodoHoja1 = manejadorNumerico.evaluarExpresionMatematica(nodoComparacion.getNodo1());
        NodoHojaExpresion nodoHoja2 = manejadorNumerico.evaluarExpresionMatematica(nodoComparacion.getNodo2());
        if (nodoHoja1 != null && nodoHoja2 != null) {
            String labelSi = "L" + this.editor.getManTablas().obtenerNuevoNumeroDeLabel();
            String labelNo = "L" + this.editor.getManTablas().obtenerNuevoNumeroDeLabel();
            Cuarteto cuartetoIf = new Cuarteto(nodoComparacion.getOperacion().getSimbolo(), nodoHoja1.getValor(), nodoHoja2.getValor(), labelSi, TipoDeCuarteto.IF);
            Cuarteto cuartetoGoto = new Cuarteto("goto", labelSi, null, labelNo, TipoDeCuarteto.GOTO);
            this.editor.getManTablas().anadirCuarteto(cuartetoIf);
            this.editor.getManTablas().anadirCuarteto(cuartetoGoto);
            return cuartetoGoto;
        }
        return null;
    }

    private Cuarteto recorrerNodoLogico(Nodo nodo) {
        if (nodo instanceof NodoComparacion) {
            return recorrerNodoComparacion((NodoComparacion) nodo);
        } else if (nodo instanceof NodoHojaExpresion) {
            return verificarSiVariableEsBooleana((NodoHojaExpresion) nodo);
        } else if (nodo instanceof NodoLogico) {//Es nodo logico
            NodoLogico nodoLogico = (NodoLogico) nodo;
            Cuarteto cuarteto1 = recorrerNodoLogico(nodoLogico.getNodo1());
            Cuarteto cuarteto2 = recorrerNodoLogico(nodoLogico.getNodo2());
            if (cuarteto1 != null && cuarteto2 != null) {
                int posicionClave = this.editor.getManTablas().getTablaDeCuarteto().indexOf(cuarteto1) + 1;
                if (nodoLogico.getOperacion() == OperacionLogica.AND) {
                    Cuarteto label = new Cuarteto(null, null, null, cuarteto1.getOperador1(), TipoDeCuarteto.SOLO_LABEL);
                    cuarteto2.setResultado(cuarteto1.getResultado());
                    editor.getManTablas().getTablaDeCuarteto().add(posicionClave, label);
                } else if (nodoLogico.getOperacion() == OperacionLogica.OR) {
                    Cuarteto label = new Cuarteto(null, null, null, cuarteto1.getResultado(), TipoDeCuarteto.SOLO_LABEL);
                    int penultimaPosicion = this.editor.getManTablas().getTablaDeCuarteto().size() - 2;
                    editor.getManTablas().getTablaDeCuarteto().get(penultimaPosicion).setResultado(cuarteto1.getOperador1());
                    cuarteto2.setOperador1(cuarteto1.getOperador1());
                    editor.getManTablas().getTablaDeCuarteto().add(posicionClave, label);
                } else {//ES NOT

                }
                return cuarteto2;

            }
        } else {//Error expresion no puede ser booleano
            String mensaje = "Error SEMANTICO, no se puede convertir expresion a boleano.\n" + CreadorDeVariables.averiguarTipoDeNodo(nodo);
            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
        }
        return null;
    }

}
