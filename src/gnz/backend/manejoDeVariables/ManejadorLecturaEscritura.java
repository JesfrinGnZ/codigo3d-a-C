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
import gnz.backend.nodoExpresion.NodoHojaExpresion;
import gnz.backend.tablas.TuplaDeSimbolo;
import gnz.gui.frames.EditorDeTextoFrame;
import java.util.LinkedList;

/**
 *
 * @author jesfrin
 */
public class ManejadorLecturaEscritura {

    private final static String FORMATO_NUMERO_ENTERO = "%d";
    private final static String FORMATO_NUMERO_LONG = "%ld";
    private final static String FORMATO_NUMERO_FLOAT = "%f";
    private final static String FORMATO_NUMERO_DOUBLE = "%lf";
    private final static String FORMATO_CHAR = "%c";
    private final static String FORMATO_CADENA = "%s";

    private EditorDeTextoFrame editor;

    public ManejadorLecturaEscritura(EditorDeTextoFrame editor) {
        this.editor = editor;
    }

    private String evaluarVariable(TuplaDeSimbolo tupla) {
        String tipoDeValor = "";
        switch (tupla.getTipo()) {
            case BOOLEAN: {
                tipoDeValor += FORMATO_NUMERO_ENTERO;
                break;
            }
            case STRING: {
                tipoDeValor += FORMATO_CADENA;
                break;
            }
            case BYTE: {
                tipoDeValor += FORMATO_NUMERO_ENTERO;
                break;
            }
            case CHAR: {
                tipoDeValor += FORMATO_CHAR;
                break;
            }
            case INT: {
                tipoDeValor += FORMATO_NUMERO_ENTERO;
                break;
            }
            case LONG: {
                tipoDeValor += FORMATO_NUMERO_LONG;
                break;
            }
            case FLOAT: {
                tipoDeValor += FORMATO_NUMERO_FLOAT;
                break;
            }
            case DOUBLE: {
                tipoDeValor += FORMATO_NUMERO_DOUBLE;
                break;
            }
            default:
                break;
        }
        return tipoDeValor;
    }

    public void evaluarPrint(LinkedList<Nodo> nodosDePrint, String ambito, TipoDeCuarteto tipoDeCuarteto) {
        char c = 92;
        String saltoDeLinea = c + "n";
        TipoDeCuarteto tipoDeCuartetoParC = TipoDeCuarteto.PrintlnC;
        Cuarteto cuarteto = null;
        String instruccionInicioParaC = "\tprintf(\"";
        String instruccionFInParaC = "";
        if (tipoDeCuarteto == TipoDeCuarteto.Print) {
            saltoDeLinea = "";
            tipoDeCuartetoParC = TipoDeCuarteto.PrintC;
        }
        for (Nodo nodo : nodosDePrint) {
            if (nodo instanceof NodoHojaExpresion) {
                NodoHojaExpresion nodoHoja = (NodoHojaExpresion) nodo;
                switch (nodoHoja.getTipo()) {
                    case IDENTIFICADOR: {
                        TuplaDeSimbolo variable = editor.getManTablas().buscarVariable(nodoHoja.getValor(), ambito);
                        String amb = ambito;
                        if (variable == null && !ambito.equalsIgnoreCase("global")) {
                            variable = editor.getManTablas().buscarVariable(nodoHoja.getValor(), "global");
                            cuarteto = new Cuarteto("", "", "", nodoHoja.getValor() + "global", tipoDeCuarteto, variable.getTipo());
                            amb = "global";
                        } else {
                            cuarteto = new Cuarteto("", "", "", nodoHoja.getValor() + amb, tipoDeCuarteto, variable.getTipo());
                        }
                        instruccionInicioParaC += evaluarVariable(variable) + saltoDeLinea;
                        instruccionFInParaC += nodoHoja.getValor() + amb + ",";
                        break;
                    }
                    case TRUE: {
                        cuarteto = new Cuarteto("", "", "", "TRUE", tipoDeCuarteto, TipoDeVariable.STRING);
                        instruccionInicioParaC += "TRUE " + saltoDeLinea;
                        break;
                    }
                    case FALSE: {
                        cuarteto = new Cuarteto("", "", "", "false", tipoDeCuarteto, TipoDeVariable.STRING);
                        instruccionInicioParaC += "FALSE " + saltoDeLinea;
                        break;
                    }
                    case NUMERO_ENTERO: {
                        cuarteto = new Cuarteto("", "", "", nodoHoja.getValor(), tipoDeCuarteto, TipoDeVariable.LONG);
                        instruccionInicioParaC += nodoHoja.getValor() + " " + saltoDeLinea;
                        break;
                    }
                    case NUMERO_DECIMAL: {
                        cuarteto = new Cuarteto("", "", "", nodoHoja.getValor(), tipoDeCuarteto, TipoDeVariable.DOUBLE);
                        instruccionInicioParaC += nodoHoja.getValor() + " " + saltoDeLinea;
                        break;
                    }
                    case NUMERO_DECIMALF: {
                        cuarteto = new Cuarteto("", "", "", nodoHoja.getValor(), tipoDeCuarteto, TipoDeVariable.FLOAT);
                        instruccionInicioParaC += nodoHoja.getValor() + " " + saltoDeLinea;
                        break;
                    }
                    case DECLARACION_STRING: {
                        String cadena = nodoHoja.getValor().substring(1, nodoHoja.getValor().length() - 1);
                        cuarteto = new Cuarteto("", "", "", cadena, tipoDeCuarteto, TipoDeVariable.STRING);
                        instruccionInicioParaC += cadena + " " + saltoDeLinea;
                        break;
                    }
                    default://Error no se admite el elemento en print
                        cuarteto = null;
                        String mensaje = "Error SEMANTICO, no se admite elemento en Print\n Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                        ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                        break;
                }
            } else {//Error no se admite el elemento en  PRint
                String mensaje = "Error SEMANTICO, no se admite elemento en Print\n" + CreadorDeVariables.averiguarTipoDeNodo(nodo);
                ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
            }
            if (cuarteto != null) {
                editor.getManTablas().anadirCuarteto(cuarteto);
            }
        }
        instruccionInicioParaC += "\"";
        if (instruccionFInParaC.endsWith(",")) {
            instruccionInicioParaC += ",";
            instruccionInicioParaC += instruccionFInParaC.substring(0, instruccionFInParaC.length() - 1);
        }
        instruccionInicioParaC += ");\n";
        cuarteto = new Cuarteto("", "", "", instruccionInicioParaC, tipoDeCuartetoParC);
        editor.getManTablas().anadirCuarteto(cuarteto);
    }

    /**
     * evalua scan
     *
     * @param id
     * @param ambito
     * @param tipoDeCuarteto
     * @param linea
     * @param columna
     */
    public void evaluarScan(String id, String ambito, TipoDeCuarteto tipoDeCuarteto, int linea, int columna) {
        TuplaDeSimbolo tupla = editor.getManTablas().buscarVariable(id, ambito);
        Cuarteto cuarteto;
        char c = 92;
        String formatoCadena = "%[^"+c + "n]";
        if (tupla == null) {
            tupla = editor.getManTablas().buscarVariable(id, "global");
            ambito = "global";
        }
        if (tipoDeCuarteto == TipoDeCuarteto.SCANS) {//ValorString
            if (tupla.getTipo() == TipoDeVariable.STRING) {
                cuarteto = new Cuarteto("\tscanf(\"" + formatoCadena + "\",", "", "", id + ambito + ");\n", tipoDeCuarteto.SCANF);
                editor.getManTablas().anadirCuarteto(cuarteto);
                cuarteto = new Cuarteto("SCANS", "", "", id + ambito, tipoDeCuarteto.SCANS);
                editor.getManTablas().anadirCuarteto(cuarteto);
            } else {//Error de ingrerso de datos
                String mensaje = "Error SEMANTICO, solo se admite variables String en SCANS.\nLinea:" + linea + " Columna:" + columna;
                ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
            }
        } else {
            if (tupla.getTipo() != TipoDeVariable.STRING && tupla.getTipo() != TipoDeVariable.BOOLEAN) {
                String formato = evaluarVariable(tupla);
                cuarteto = new Cuarteto("\tscanf(\"" + formato + "\",", "", "", "&" + id + ambito + ");\n", tipoDeCuarteto.SCANF);
                editor.getManTablas().anadirCuarteto(cuarteto);
                cuarteto = new Cuarteto("\tSCANN", "", "", id + ambito, tipoDeCuarteto.SCANN);
                editor.getManTablas().anadirCuarteto(cuarteto);
            } else {//Error de ingreso de datos
                String mensaje = "Error SEMANTICO, solo se admiten variables numericas en SCANN.\nLinea:" + linea + " Columna:" + columna;
                ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
            }
        }
    }

}
