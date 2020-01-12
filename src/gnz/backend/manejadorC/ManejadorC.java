/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.manejadorC;

import gnz.backend.cuarteto.Cuarteto;
import gnz.backend.cuarteto.TipoDeCuarteto;
import gnz.backend.funcion.Parametro;
import gnz.backend.nodoDeclaracion.TipoDeVariable;
import gnz.backend.tablas.TuplaDeSimbolo;
import gnz.gui.frames.EditorDeTextoFrame;
import java.util.LinkedList;

/**
 *
 * @author jesfrin
 */
public class ManejadorC {

    private final static String INICIO_C = "#include <stdio.h>\n#include <string.h>\n"
            + "\n"
            + "char global[10];\n;"
            + "int main()\n"
            + "{\n";

    private final static String FIN_C = "}";

    private final LinkedList<Cuarteto> listaDeCuartetos;
    private final EditorDeTextoFrame editor;
    private LinkedList<String> funcionesYaUtilizadas;
    private int numeroParFuncion;

    public ManejadorC(LinkedList<Cuarteto> listaDeCuartetos, EditorDeTextoFrame editor) {
        this.listaDeCuartetos = listaDeCuartetos;
        this.editor = editor;
        this.funcionesYaUtilizadas = new LinkedList<>();
        this.numeroParFuncion = 1;
    }

    public String escribirProgramaC() {
        String programaC;
        programaC = escribirExprescionesYAsignacionesGlobales();
        programaC += escribirContenidoDeMain();
        return "\n\n\n\n\n\n\n\n\n" + INICIO_C + programaC + FIN_C;
    }

    private String escribirExprescionesYAsignacionesGlobales() {
        String expresiones = "";
        LinkedList<Cuarteto> expresionesGlobales = new LinkedList<>();
        for (Cuarteto cuarteto : listaDeCuartetos) {
            if (cuarteto.getTipoDeCuarteto() == TipoDeCuarteto.GOTO && cuarteto.getResultado().equalsIgnoreCase("main")) {
                break;
            } else {
                expresionesGlobales.add(cuarteto);
            }
        }
        expresiones += evaluarContenido(expresionesGlobales, false, false, null, "");
        return expresiones;

    }

    private String escribirContenidoDeMain() {
        String expresiones = "";
        LinkedList<Cuarteto> contenidoDeMain = new LinkedList<>();
        boolean seEstaEnMain = false;
        for (Cuarteto cuarteto : listaDeCuartetos) {
            if (seEstaEnMain) {
                contenidoDeMain.add(cuarteto);
            } else {
                if (cuarteto.getTipoDeCuarteto() == TipoDeCuarteto.INICIO_FUNCION && cuarteto.getResultado().equals("MAIN")) {
                    seEstaEnMain = true;
                }
            }

        }
        expresiones += evaluarContenido(contenidoDeMain, false, false, null, "");
        return expresiones;
    }

    private TipoDeVariable cambiarTipoDeVariable(TipoDeVariable tipoDeVariable) {
        if (tipoDeVariable == TipoDeVariable.STRING) {
            return TipoDeVariable.CHAR;
        } else if (tipoDeVariable == TipoDeVariable.BOOLEAN || tipoDeVariable == TipoDeVariable.BYTE) {
            return TipoDeVariable.INT;
        }
        return tipoDeVariable;
    }

    private String evaluarContenido(LinkedList<Cuarteto> cuartetos, boolean esDeFuncion, boolean seGuardoJustoAhora, String nombreResult, String nombreDeFuncion) {
        LinkedList<String> parametrosDeFuncion = new LinkedList<>();
        String expresiones = "";
        for (Cuarteto cuarteto : cuartetos) {
            switch (cuarteto.getTipoDeCuarteto()) {
                case DECLARACION: {
                    if (esDeFuncion) {
                        if (seGuardoJustoAhora) {
                            expresiones += "\t" + cambiarTipoDeVariable(cuarteto.getTipoDeVariable()).toString().toLowerCase() + " " + cuarteto.getResultado() + ";\n";
                        }
                    } else {
                        expresiones += "\t" + cambiarTipoDeVariable(cuarteto.getTipoDeVariable()).toString().toLowerCase() + " " + cuarteto.getResultado() + ";\n";
                    }
                    break;
                }
                case ASIGNACION_DECLARACION: {
                    if (esDeFuncion && seGuardoJustoAhora || !esDeFuncion) {
                        if (cuarteto.getTipoDeVariable() == TipoDeVariable.STRING) {
                            expresiones += evaluarAsignacionString(cuarteto.getResultado(), cuarteto.getOperador1(), true);
                        } else {
                            expresiones += "\t" + cuarteto.getTipoDeVariable().toString().toLowerCase() + " " + cuarteto.getResultado() + "=" + cuarteto.getOperador1() + ";\n";
                        }
                    } else {
                        if (cuarteto.getTipoDeVariable() == TipoDeVariable.STRING) {
                            expresiones += evaluarAsignacionString(cuarteto.getResultado(), cuarteto.getOperador1(), false);
                        } else {
                            expresiones += "\t" + cuarteto.getResultado() + "=" + cuarteto.getOperador1() + ";\n";
                        }
                    }

                    break;
                }
                case SOLO_ASIGNACION: {
                    if (cuarteto.getTipoDeVariable() == TipoDeVariable.STRING) {
                        expresiones += evaluarAsignacionString(cuarteto.getResultado(), cuarteto.getOperador1(), false);
                    } else {
                        expresiones += "\t" + cuarteto.getResultado() + "=" + cuarteto.getOperador1() + ";\n";
                    }
                    break;
                }
                case SOLO_EXPRESION: {
                    if (esDeFuncion && seGuardoJustoAhora || !esDeFuncion) {
                        if (cuarteto.getTipoDeVariable() == TipoDeVariable.STRING) {
                            expresiones += "\tchar " + cuarteto.getResultado() + "[100];\n";
                            expresiones += evaluarExpresionString(cuarteto.getResultado(), cuarteto.getOperador1());
                            expresiones += evaluarExpresionString(cuarteto.getResultado(), cuarteto.getOperador2());
                        } else {
                            String tipo = cuarteto.getTipoDeVariable().toString().toLowerCase();
                            if (cuarteto.getTipoDeVariable() == TipoDeVariable.VOID) {
                                tipo = "";
                            }
                            expresiones += "\t" + tipo + " " + cuarteto.getResultado() + "=" + cuarteto.getOperador1() + cuarteto.getOperando() + cuarteto.getOperador2() + ";\n";
                        }
                    } else {
                        if (cuarteto.getTipoDeVariable() == TipoDeVariable.STRING) {
                            expresiones += "\t" + cuarteto.getResultado() + "[100];\n";
                            expresiones += evaluarExpresionString(cuarteto.getResultado(), cuarteto.getOperador1());
                            expresiones += evaluarExpresionString(cuarteto.getResultado(), cuarteto.getOperador2());
                        } else {
                            expresiones += "\t" + cuarteto.getResultado() + "=" + cuarteto.getOperador1() + cuarteto.getOperando() + cuarteto.getOperador2() + ";\n";
                        }
                    }

                    break;
                }
                case IF: {
                    if (esDeFuncion) {
                        expresiones += "\tif(" + cuarteto.getOperador1() + cuarteto.getOperando() + cuarteto.getOperador2() + ")goto " + cuarteto.getResultado() + nombreDeFuncion + numeroParFuncion + ";\n";

                    } else {
                        expresiones += "\tif(" + cuarteto.getOperador1() + cuarteto.getOperando() + cuarteto.getOperador2() + ")goto " + cuarteto.getResultado() + ";\n";
                    }
                    break;
                }
                case GOTO: {
                    if (esDeFuncion) {
                        expresiones += "\tgoto " + cuarteto.getResultado() + nombreDeFuncion + numeroParFuncion + ";\n";

                    } else {
                        expresiones += "\tgoto " + cuarteto.getResultado() + ";\n";
                    }
                    break;
                }
                case SOLO_LABEL: {
                    if (esDeFuncion) {
                        expresiones += "\t" + cuarteto.getResultado() + nombreDeFuncion + numeroParFuncion + ":;\n";

                    } else {
                        expresiones += "\t" + cuarteto.getResultado() + ":;\n";
                    }
                    break;
                }
                case PrintC: {
                    expresiones += cuarteto.getResultado();
                    break;
                }
                case PrintlnC: {
                    expresiones += cuarteto.getResultado();
                    break;
                }
                case SCANF: {
                    expresiones += cuarteto.getOperando() + cuarteto.getResultado();
                    break;
                }
                case INICIO_FUNCION: {
                    break;
                }
                case RETURN: {
                    expresiones += "\t" + nombreResult + "= " + cuarteto.getResultado() + ";\n";
                    break;
                }
                case PARAMETRO: {
                    parametrosDeFuncion.add(cuarteto.getResultado());
                    break;
                }
                case LLAMADA_DE_FUNCION: {
                    TuplaDeSimbolo subPrograma = editor.getManTablas().buscarSubPrograma(cuarteto.getOperador1());
                    LinkedList<Cuarteto> cuartetosDeSubprograma = new LinkedList<>();
                    boolean funcionYaSeGuardo = guardarFuncionYaUsada(subPrograma.getNombre());
                    int i = 0;
                    //Trabajo con los parametros
                    if (!subPrograma.getParametrosDeFuncion().isEmpty()) {
                        for (Parametro parametro : subPrograma.getParametrosDeFuncion()) {
                            String tipo = "";
                            if (funcionYaSeGuardo) {
                                tipo = parametro.getTipo().toString().toLowerCase();
                            }
                            expresiones += "\t" + tipo + " " + parametro.getNombreDeParametro() + cuarteto.getOperador1() + "=" + parametrosDeFuncion.get(i) + ";\n";
                            i++;
                        }
                    }

                    parametrosDeFuncion = new LinkedList<>();//Llenando lista de cuartetos 
                    boolean seEstaEnLaFuncion = false;
                    for (Cuarteto cuart : listaDeCuartetos) {
                        if (seEstaEnLaFuncion) {
                            cuartetosDeSubprograma.add(cuart);
                        }
                        if (cuart.getTipoDeCuarteto() == TipoDeCuarteto.INICIO_FUNCION && cuart.getResultado().equals(cuarteto.getOperador1())) {
                            seEstaEnLaFuncion = true;
                        } else if (cuart.getTipoDeCuarteto() == TipoDeCuarteto.INICIO_FUNCION && !cuartetosDeSubprograma.isEmpty()) {
                            break;
                        }
                    }
                    //Trabajo con el return
                    Parametro paramReturn = new Parametro(subPrograma.getTipoDeRetorno(), cuarteto.getResultado());
                    if (paramReturn.getTipo() == TipoDeVariable.BOOLEAN) {
                        expresiones += "\t" + "int" + " " + paramReturn.getNombreDeParametro() + ";\n";
                    } else if (paramReturn.getTipo() == TipoDeVariable.STRING) {
                        expresiones += "\t" + "char" + " " + paramReturn.getNombreDeParametro() + ";\n";
                    } else if (paramReturn.getTipo() == TipoDeVariable.VOID) {
                    } else {
                        expresiones += "\t" + paramReturn.getTipo().toString().toLowerCase() + " " + paramReturn.getNombreDeParametro() + ";\n";
                    }
                    //Trabajo con el contenido de la funcion
                    expresiones += evaluarContenido(cuartetosDeSubprograma, true, funcionYaSeGuardo, cuarteto.getResultado(), subPrograma.getNombre());//Resultado es a lo que se iguala el return
                    numeroParFuncion++;
                    break;
                }
            }
        }
        return expresiones;
    }

    //*******************************************************************METODOS AUXILIARES*********************************************************
    private String evaluarAsignacionString(String resultado, String operando, boolean seDebeDeclarar) {
        String expresion = "";
        if (seDebeDeclarar) {
            expresion += "\tchar " + resultado + " [100];\n";
        }
        if (operando.startsWith("t") && operando.length() == 2) {//Si viene de expresion
            expresion += "\tstrncat(" + resultado + "," + operando + "," + "100" + ");\n";
            return expresion;
        } else if (operando.startsWith("\"")) {//Declaracion de caracter
            int tamano = operando.length() - 2;
            expresion += "\tstrncat(" + resultado + "," + operando + "," + tamano + ");\n";
        } else {//Es una variable
            expresion += "\tstrncat(" + resultado + "," + operando + "," + "100" + ");\n";
        }
        return expresion;
    }

    private String evaluarExpresionString(String resultado, String operando) {
        String expresion = "";
        if (operando.startsWith("t") && operando.length() == 2) {
            expresion += "\tstrncat(" + resultado + "," + operando + "," + "100" + ");\n";
            return expresion;
        }
        if (operando.startsWith("\"")) {//Declaracion de caracter
            int tamano = operando.length() - 2;
            expresion += "\tstrncat(" + resultado + "," + operando + "," + tamano + ");\n";
        } else {
            TuplaDeSimbolo variable = editor.getManTablas().buscarVariable(operando);
            if (variable != null) {//Es variable
                if (variable.getTipo() == TipoDeVariable.STRING) {
                    expresion += "\tstrncat(" + resultado + "," + operando + "," + "100" + ");\n";
                } else if (variable.getTipo() == TipoDeVariable.DOUBLE || variable.getTipo() == TipoDeVariable.FLOAT) {
                    expresion += "\tsprintf(global,\"%lf\"," + operando + ");\n";
                    expresion += "\tstrncat(" + resultado + ",global," + "100" + ");\n";
                } else {//Booleano o entero
                    expresion += "\tsprintf(global,\"%d\"," + operando + ");\n";
                    expresion += "\tstrncat(" + resultado + ",global," + "100" + ");\n";
                }
            } else {//Es cualquier otra cosa como TRUE o un numero
                int tamano = operando.length();
                expresion += "\tstrncat(" + resultado + "," + "\"" + operando + "\"" + "," + tamano + ");\n";
            }
        }
        return expresion;
    }

    //**************************************************************Para Funciones**********************************
    /**
     * Devuelve true si la funcion se guardo justo ahora
     *
     * @param nombreDeFuncion
     * @return
     */
    private boolean guardarFuncionYaUsada(String nombreDeFuncion) {
        boolean yaSeGuardoLaFuncion = false;
        for (String funcion : funcionesYaUtilizadas) {
            if (funcion.equals(nombreDeFuncion)) {
                yaSeGuardoLaFuncion = true;
            }
        }
        if (!yaSeGuardoLaFuncion) {
            funcionesYaUtilizadas.add(nombreDeFuncion);
            return true;
        }
        return false;
    }

}
