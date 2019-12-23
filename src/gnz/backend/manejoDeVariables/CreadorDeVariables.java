/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.manejoDeVariables;

import gnz.backend.cuarteto.Cuarteto;
import gnz.backend.cuarteto.TipoDeCuarteto;
import gnz.backend.errores.ManejadorDeErrores;
import gnz.backend.funcion.Parametro;
import gnz.backend.nodo.Nodo;
import gnz.backend.nodoComparacion.NodoComparacion;
import gnz.backend.nodoComparacion.NodoLogico;
import gnz.backend.nodoDeclaracion.NodoId;
import gnz.backend.nodoDeclaracion.TipoDeVariable;
import gnz.backend.nodoExpresion.NodoExpresion;
import gnz.backend.nodoExpresion.NodoHojaExpresion;
import gnz.backend.nodoExpresion.OperacionAritmetica;
import gnz.backend.tablas.Categoria;
import gnz.backend.tablas.TuplaDeSimbolo;
import gnz.gui.frames.EditorDeTextoFrame;
import java.util.LinkedList;

/**
 *
 * @author jesfrin
 */
public class CreadorDeVariables {

    /**
     * ***************************************************************Funciones************************************************************************************
     */
    public static void guardarFuncion(String nombreDeFuncion, TipoDeVariable tipo, LinkedList<Parametro> parametros, int linea, int columna, EditorDeTextoFrame editor, String ambito) {
        if (parametros == null) {
            parametros = new LinkedList<>();
        }
        TuplaDeSimbolo tupla = new TuplaDeSimbolo(0, nombreDeFuncion, tipo, parametros.size(), parametros);
        boolean seGuardo = editor.getManTablas().guardarSubPrograma(tupla, linea, columna);
        if (seGuardo) {
            //Ahora se guardan los parametros como variables
            for (Parametro parametro : parametros) {
                tupla = new TuplaDeSimbolo(0, parametro.getNombreDeParametro(), tipo, ambito);
                editor.getManTablas().guardarVariable(tupla, linea, columna);
            }
        }

    }

    /**
     * *************************************************************CREACION**********************************************************************************
     */
    public static void declararVariables(DeclaracionDeVariable declaracion, EditorDeTextoFrame editor) {
        if (declaracion.getTipo() == TipoDeVariable.BOOLEAN) {
            ManejadorDeExpresionesBooleanas manejadorBooleano = new ManejadorDeExpresionesBooleanas(editor, declaracion.getAmbito());
            for (NodoId nodoId : declaracion.getIds()) {
                if (nodoId.getAsignacion() == null) {
                    TuplaDeSimbolo tupla = new TuplaDeSimbolo(0, nodoId.getId(), declaracion.getTipo(), declaracion.getAmbito());
                    editor.getManTablas().guardarVariable(tupla, nodoId.getLinea(), nodoId.getColumna());
                } else {
                    Cuarteto ultimoCuarteto = manejadorBooleano.evaluarExpresionBooleana(nodoId.getAsignacion());
                    if (ultimoCuarteto != null) {
                        //Guardando la variable
                        TuplaDeSimbolo tupla = new TuplaDeSimbolo(0, nodoId.getId(), declaracion.getTipo(), declaracion.getAmbito());
                        editor.getManTablas().guardarVariable(tupla, nodoId.getLinea(), nodoId.getColumna());
                        crearCuartetosParaBooleano(ultimoCuarteto, editor, nodoId, declaracion.getAmbito());
                    }
                }
            }
        } else if (declaracion.getTipo() == TipoDeVariable.STRING) {
            ManejadorDeExpresionesString manejadorDeCadenas = new ManejadorDeExpresionesString(editor, declaracion.getAmbito());
            for (NodoId nodoId : declaracion.getIds()) {
                if (nodoId.getAsignacion() == null) {
                    TuplaDeSimbolo tupla = new TuplaDeSimbolo(0, nodoId.getId(), declaracion.getTipo(), declaracion.getAmbito());
                    editor.getManTablas().guardarVariable(tupla, nodoId.getLinea(), nodoId.getColumna());
                } else {
                    NodoHojaExpresion nodoHoja = manejadorDeCadenas.evaluarExpresionCadena(nodoId.getAsignacion());
                    if (nodoHoja != null) {
                        TuplaDeSimbolo tupla = new TuplaDeSimbolo(0, nodoId.getId(), declaracion.getTipo(), declaracion.getAmbito());
                        editor.getManTablas().guardarVariable(tupla, nodoId.getLinea(), nodoId.getColumna());
                        Cuarteto cuartetoAsignacion = new Cuarteto(null, nodoHoja.getValor(), null, nodoId.getId() + declaracion.getAmbito(), TipoDeCuarteto.ASIGNACION);
                        editor.getManTablas().anadirCuarteto(cuartetoAsignacion);
                    }
                }
            }
        } else {//Es numerico
            ManejadorDeExpresionesNumericas manejadorNumerico = new ManejadorDeExpresionesNumericas(editor, declaracion.getAmbito());
            for (NodoId nodoId : declaracion.getIds()) {
                if (nodoId.getAsignacion() == null) {//Solo se debe guardar la variable
                    TuplaDeSimbolo tupla = new TuplaDeSimbolo(0, nodoId.getId(), declaracion.getTipo(), declaracion.getAmbito());
                    editor.getManTablas().guardarVariable(tupla, nodoId.getLinea(), nodoId.getColumna());
                } else {
                    NodoHojaExpresion nodoHoja = manejadorNumerico.evaluarExpresionMatematica(nodoId.getAsignacion());//Tipo y ultimo valor temporal
                    if (nodoHoja != null) {
                        //Se verifica que sean de tipos compatibles
                        if (declaracion.getTipo().getJerarquia() >= nodoHoja.getTipoDEVariable().getJerarquia()) {
                            TuplaDeSimbolo tupla = new TuplaDeSimbolo(0, nodoId.getId(), declaracion.getTipo(), declaracion.getAmbito());
                            editor.getManTablas().guardarVariable(tupla, nodoId.getLinea(), nodoId.getColumna());
                            Cuarteto cuartetoAsignacion = new Cuarteto(null, nodoHoja.getValor(), null, nodoId.getId() + declaracion.getAmbito(), TipoDeCuarteto.ASIGNACION);
                            editor.getManTablas().anadirCuarteto(cuartetoAsignacion);
                        } else {//ERROR de conversion de tipos
                            String mensaje = "Error SEMANTICO, tipos no compatibles " + nodoHoja.getTipoDEVariable() + "\n No se puede convertirn en" + declaracion.getTipo() + " en Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                        }

                    }
                }

            }
        }

    }

    private static void crearCuartetosParaBooleano(Cuarteto ultimoCuarteto, EditorDeTextoFrame editor, NodoId nodoId, String ambito) {
        String labelSalida = "L" + editor.getManTablas().obtenerNuevoNumeroDeLabel();
        //Cuartetos
        Cuarteto labelSi = new Cuarteto(null, null, null, ultimoCuarteto.getOperador1(), TipoDeCuarteto.SOLO_LABEL);
        Cuarteto labelNo = new Cuarteto(null, null, null, ultimoCuarteto.getResultado(), TipoDeCuarteto.SOLO_LABEL);
        Cuarteto cuartetoAsignacionSi = new Cuarteto(null, "1", null, nodoId.getId() + ambito, TipoDeCuarteto.ASIGNACION);
        Cuarteto cuartetoAsignacionNo = new Cuarteto(null, "0", null, nodoId.getId() + ambito, TipoDeCuarteto.ASIGNACION);
        Cuarteto cuartetoGoto = new Cuarteto("goto", null, null, labelSalida, TipoDeCuarteto.GOTO);
        Cuarteto labelFinal = new Cuarteto(null, null, null, labelSalida, TipoDeCuarteto.SOLO_LABEL);
        //Se anaden los cuartetos
        editor.getManTablas().anadirCuarteto(labelSi);
        editor.getManTablas().anadirCuarteto(cuartetoAsignacionSi);
        editor.getManTablas().anadirCuarteto(cuartetoGoto);//GOTO_SALIDA
        editor.getManTablas().anadirCuarteto(labelNo);
        editor.getManTablas().anadirCuarteto(cuartetoAsignacionNo);
        editor.getManTablas().anadirCuarteto(labelFinal);//LABEL
    }

    private static void crearCuartetosParaBooleano(Cuarteto ultimoCuarteto, EditorDeTextoFrame editor, NodoId nodoId, String ambito, String ultimoTemp) {
        String labelSalida = "L" + editor.getManTablas().obtenerNuevoNumeroDeLabel();
        //Cuartetos
        Cuarteto labelSi = new Cuarteto(null, null, null, ultimoCuarteto.getOperador1(), TipoDeCuarteto.SOLO_LABEL);
        Cuarteto labelNo = new Cuarteto(null, null, null, ultimoCuarteto.getResultado(), TipoDeCuarteto.SOLO_LABEL);
        Cuarteto cuartetoAsignacionSi = new Cuarteto(null, "1", null, nodoId.getId() + ambito + "[" + ultimoTemp + "]", TipoDeCuarteto.ASIGNACION);
        Cuarteto cuartetoAsignacionNo = new Cuarteto(null, "0", null, nodoId.getId() + ambito + "[" + ultimoTemp + "]", TipoDeCuarteto.ASIGNACION);
        Cuarteto cuartetoGoto = new Cuarteto("goto", null, null, labelSalida, TipoDeCuarteto.GOTO);
        Cuarteto labelFinal = new Cuarteto(null, null, null, labelSalida, TipoDeCuarteto.SOLO_LABEL);
        //Se anaden los cuartetos
        editor.getManTablas().anadirCuarteto(labelSi);
        editor.getManTablas().anadirCuarteto(cuartetoAsignacionSi);
        editor.getManTablas().anadirCuarteto(cuartetoGoto);//GOTO_SALIDA
        editor.getManTablas().anadirCuarteto(labelNo);
        editor.getManTablas().anadirCuarteto(cuartetoAsignacionNo);
        editor.getManTablas().anadirCuarteto(labelFinal);//LABEL
    }

    /**
     * *************************************************************ASIGNACION**********************************************************************************
     */
    public static void asignarVariable(NodoId nodoId, EditorDeTextoFrame editor, String ambito) {
        TuplaDeSimbolo tupla = editor.getManTablas().buscarVariable(nodoId.getId(), ambito);
        if (tupla != null) {//En su ambito
            crearLaAsignacion(tupla, editor, nodoId, ambito);
        } else {//En el mabito global
            tupla = editor.getManTablas().buscarVariable(nodoId.getId(), "global");
            if (tupla != null) {
                crearLaAsignacion(tupla, editor, nodoId, "global");
            } else {//Error semantico la variable no ha sido declarada
                String mensaje = "Error SEMANTICO, la variable " + nodoId.getId() + " No ha sido declarada.\nLinea:" + nodoId.getLinea() + " Columna:" + nodoId.getColumna();
                ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
            }
        }
    }

    private static void crearLaAsignacion(TuplaDeSimbolo tupla, EditorDeTextoFrame editor, NodoId nodoId, String ambito) {
        TipoDeVariable tipo = tupla.getTipo();
        if (tipo == TipoDeVariable.BOOLEAN) {
            ManejadorDeExpresionesBooleanas manBooleano = new ManejadorDeExpresionesBooleanas(editor, ambito);
            Cuarteto ultimoCuarteto = manBooleano.evaluarExpresionBooleana(nodoId.getAsignacion());
            if (ultimoCuarteto != null) {
                crearCuartetosParaBooleano(ultimoCuarteto, editor, nodoId, ambito);
            }
        } else if (tipo == TipoDeVariable.STRING) {
            ManejadorDeExpresionesString manString = new ManejadorDeExpresionesString(editor, ambito);
            NodoHojaExpresion nodoHoja = manString.evaluarExpresionCadena(nodoId.getAsignacion());
            if (nodoHoja != null) {
                Cuarteto cuartetoAsignacion = new Cuarteto(null, nodoHoja.getValor(), null, nodoId.getId() + ambito, TipoDeCuarteto.ASIGNACION);
                editor.getManTablas().anadirCuarteto(cuartetoAsignacion);
            }
        } else {//Es numerico
            ManejadorDeExpresionesNumericas manNumerico = new ManejadorDeExpresionesNumericas(editor, ambito);
            NodoHojaExpresion nodoHoja = manNumerico.evaluarExpresionMatematica(nodoId.getAsignacion());
            if (nodoHoja != null) {
                if (tupla.getTipo().getJerarquia() >= nodoHoja.getTipoDEVariable().getJerarquia()) {
                    Cuarteto cuartetoAsignacion = new Cuarteto(null, nodoHoja.getValor(), null, nodoId.getId() + ambito, TipoDeCuarteto.ASIGNACION);
                    editor.getManTablas().anadirCuarteto(cuartetoAsignacion);
                } else {//ERROR de conversion de tipos
                    String mensaje = "Error SEMANTICO, tipos no compatibles " + nodoHoja.getTipoDEVariable() + "\n No se puede convertirn en" + tupla.getTipo() + " en Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                    ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                }
            }
        }
    }

    //****************************************************************Arreglos***************************************************************
    public static void declararArreglo(TipoDeVariable tipoDeVariable, LinkedList<NodoId> ids, LinkedList<Nodo> expresiones, EditorDeTextoFrame editor, String ambito) {
        NodoHojaExpresion hojaActual;
        TuplaDeSimbolo tupla;
        ManejadorDeExpresionesNumericas manejadorNumerico = new ManejadorDeExpresionesNumericas(editor, ambito);
        LinkedList<NodoHojaExpresion> hojasEvaluadas = new LinkedList<>();
        boolean existeErrorEnHoja = false;
        //Evaluando las expresiones
        for (Nodo expresion : expresiones) {
            hojaActual = manejadorNumerico.evaluarExpresionMatematica(expresion);
            if (hojaActual == null) {
                existeErrorEnHoja = true;
                break;
            } else {
                if (hojaActual.getTipoDEVariable().getJerarquia() <= TipoDeVariable.LONG.getJerarquia()) {
                    hojasEvaluadas.add(hojaActual);
                } else {//Error de conversion de tipos
                    existeErrorEnHoja = true;
                    String mensaje = "Error SEMANTICO, expresion EN ARREGLO no es NUMERICA.\n" + averiguarTipoDeNodo(expresion);
                    ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                    break;
                }
            }
        }
        //Ahora se procede a guardar la tupla
        if (!existeErrorEnHoja) {
            for (NodoId id : ids) {
                tupla = new TuplaDeSimbolo(0, id.getId(), tipoDeVariable, hojasEvaluadas.size(), hojasEvaluadas, ambito);
                editor.getManTablas().guardarVariable(tupla, id.getLinea(), id.getColumna());
            }
        }
    }

    public static void asignarValorArreglo(EditorDeTextoFrame editor, String ambito, LinkedList<Nodo> expresiones, NodoId nodoId) {
        NodoHojaExpresion hojaActual;
        boolean existeErrorEnHoja = false;
        TuplaDeSimbolo tupla;
        ManejadorDeExpresionesNumericas manejadorNumerico = new ManejadorDeExpresionesNumericas(editor, ambito);
        LinkedList<NodoHojaExpresion> hojasEvaluadas = new LinkedList<>();
        //Primero verificar que la variable exista,que tipo sea arreglo, y las dimenciones sean las correctas
        tupla = editor.getManTablas().buscarVariable(nodoId.getId(), ambito);
        if (tupla != null) {
            if (tupla.getCategoria() == Categoria.Arreglo) {
                if (tupla.getNumeroDimensiones() == expresiones.size()) {
                    //Evaluando expresiones
                    for (Nodo expresion : expresiones) {
                        hojaActual = manejadorNumerico.evaluarExpresionMatematica(expresion);
                        if (hojaActual == null) {
                            existeErrorEnHoja = true;
                            break;
                        } else {
                            if (hojaActual.getTipoDEVariable().getJerarquia() <= TipoDeVariable.LONG.getJerarquia()) {
                                hojasEvaluadas.add(hojaActual);
                            } else {//Error de conversion de tipos
                                existeErrorEnHoja = true;
                                String mensaje = "Error SEMANTICO, expresion EN ARREGLO no es NUMERICA.\n" + averiguarTipoDeNodo(expresion);
                                ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                                break;
                            }
                        }
                    }

                    if (!existeErrorEnHoja) {
                        if (tupla.getTipo() == TipoDeVariable.BOOLEAN) {
                            ManejadorDeExpresionesBooleanas manejadorBooleano = new ManejadorDeExpresionesBooleanas(editor, ambito);
                            String ultimoTemp = ManejadorDeExpresionesParaArreglos.evaluarArreglo(tupla.getDimensionesArreglo(), hojasEvaluadas, editor);
                            Cuarteto ultimoCuarteto = manejadorBooleano.evaluarExpresionBooleana(nodoId.getAsignacion());
                            if (ultimoCuarteto != null) {
                                crearCuartetosParaBooleano(ultimoCuarteto, editor, nodoId, ambito, ultimoTemp);
                            }

                        } else if (tupla.getTipo() == TipoDeVariable.STRING) {
                            ManejadorDeExpresionesString manejadorCadena = new ManejadorDeExpresionesString(editor, ambito);
                            String ultimoTemp = ManejadorDeExpresionesParaArreglos.evaluarArreglo(tupla.getDimensionesArreglo(), hojasEvaluadas, editor);
                            NodoHojaExpresion nodoHoja = manejadorCadena.evaluarExpresionCadena(nodoId.getAsignacion());
                            if (nodoHoja != null) {
                                Cuarteto cuartetoAsignacion = new Cuarteto(null, nodoHoja.getValor(), null, nodoId.getId() + ambito + "[" + ultimoTemp + "]", TipoDeCuarteto.ASIGNACION);
                                editor.getManTablas().anadirCuarteto(cuartetoAsignacion);
                            }
                        } else {//Es numerico
                            String ultimoTemp = ManejadorDeExpresionesParaArreglos.evaluarArreglo(tupla.getDimensionesArreglo(), hojasEvaluadas, editor);
                            NodoHojaExpresion nodoHoja = manejadorNumerico.evaluarExpresionMatematica(nodoId.getAsignacion());
                            if (nodoHoja != null) {
                                if (tupla.getTipo().getJerarquia() >= nodoHoja.getTipoDEVariable().getJerarquia()) {
                                    Cuarteto cuartetoAsignacion = new Cuarteto(null, nodoHoja.getValor(), null, nodoId.getId() + ambito + "[" + ultimoTemp + "]", TipoDeCuarteto.ASIGNACION);
                                    editor.getManTablas().anadirCuarteto(cuartetoAsignacion);
                                } else {//Error conversion ce tipos
                                    String mensaje = "Error SEMANTICO, tipos no compatibles " + nodoHoja.getTipoDEVariable() + "\n No se puede convertirn en" + tupla.getTipo() + " en Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                                    ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                                }
                            }
                        }
                    }

                } else {//Error de dimension

                }
            } else {//Error de conversion de tipos

            }
        } else {//Error la variable no ha sido declarada

        }

    }

    public static String averiguarTipoDeNodo(Nodo nodoPadre) {
        if (nodoPadre instanceof NodoHojaExpresion) {
            NodoHojaExpresion nodo = (NodoHojaExpresion) nodoPadre;
            return "Linea:" + nodo.getLinea() + " Columna:" + nodo.getColumna();
        } else if (nodoPadre instanceof NodoExpresion) {
            NodoExpresion nodo = (NodoExpresion) nodoPadre;
            return "Linea:" + nodo.getLinea() + " Columna:" + nodo.getColumna();
        } else if (nodoPadre instanceof NodoComparacion) {
            NodoComparacion nodo = (NodoComparacion) nodoPadre;
            return "Linea:" + nodo.getLinea() + " Columna:" + nodo.getColumna();
        } else if (nodoPadre instanceof NodoLogico) {
            NodoLogico nodo = (NodoLogico) nodoPadre;
            return "Linea:" + nodo.getLinea() + " Columna:" + nodo.getColumna();
        } else {
            return "";
        }
    }

}
