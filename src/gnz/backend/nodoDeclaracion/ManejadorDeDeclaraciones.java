/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.nodoDeclaracion;

import gnz.backend.cuarteto.Cuarteto;
import gnz.backend.cuarteto.TipoDeCuarteto;
import gnz.backend.errores.ManejadorDeErrores;
import gnz.backend.nodo.Nodo;
import gnz.backend.nodoComparacion.NodoComparacion;
import gnz.backend.nodoComparacion.NodoLogico;
import gnz.backend.nodoExpresion.NodoExpresion;
import gnz.backend.nodoExpresion.NodoHojaExpresion;
import gnz.backend.nodoExpresion.OperacionAritmetica;
import gnz.backend.nodoExpresion.TipoDeHoja;
import gnz.backend.tablas.Categoria;
import gnz.backend.tablas.TuplaDeSimbolo;
import gnz.gui.frames.EditorDeTextoFrame;
import java.util.LinkedList;

/**
 *
 * @author jesfrin
 */
public class ManejadorDeDeclaraciones {

    private LinkedList<Nodo> declaraciones;
    private EditorDeTextoFrame editor;

    public ManejadorDeDeclaraciones(LinkedList<Nodo> declaraciones, EditorDeTextoFrame editor) {
        this.declaraciones = declaraciones;
        this.editor = editor;
        crearCuartetos();
    }

    private void crearCuartetos() {
        for (Nodo nododDeclaracion : declaraciones) {
            NodoDeclaracion n = (NodoDeclaracion) nododDeclaracion;
            for (Nodo nodoId : n.getIds()) {
                NodoId n1 = (NodoId) (nodoId);
                System.out.print("ID::::::" + n1.getId() + " Tipo:" + n.getTipo().name());
                busquedaDeTipoDeNodo(n, n1);
            }

        }

    }

    private void busquedaDeTipoDeNodo(NodoDeclaracion nodoDeclaracion, NodoId nodoId) {
        if (nodoId.getAsignacion() == null) {
            TuplaDeSimbolo variableAGuardar = new TuplaDeSimbolo(0, nodoId.getId(), nodoDeclaracion.getTipo(), Categoria.Variable, null);
            this.editor.getManTablas().guardarNuevaVariable(variableAGuardar, nodoId.getLinea(), nodoId.getColumna());
        } else if (nodoId.getAsignacion() instanceof NodoHojaExpresion) {
            NodoHojaExpresion n3 = (NodoHojaExpresion) nodoId.getAsignacion();
            System.out.println(" NodoHojaExpresion");
            evaluacionDeNodoHojaExpresion(nodoDeclaracion, n3, nodoId);

        } else if (nodoId.getAsignacion() instanceof NodoExpresion) {
            System.out.println(" NodoExpresion");
            if (nodoDeclaracion.getTipo() != TipoDeVariable.BOOLEAN) {
                NodoExpresion n3 = (NodoExpresion) nodoId.getAsignacion();
                evaluacionDeNodoExpresion(nodoDeclaracion, n3);
            } else {
                //ERROR,tipos no compatibles
                String mensaje = "Tipos incompatibles, expresion no puede ser booleano";
                ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
            }
        } else if (nodoId.getAsignacion() instanceof NodoComparacion) {//Solo para booleano
            System.out.println(" NodoComparacion");
            if (nodoDeclaracion.getTipo() == TipoDeVariable.BOOLEAN) {

            } else {
                //ERROR
                String mensaje = "Tipos incompatibles, booleano no puede ser expresion";
                ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
            }
        } else if (nodoId.getAsignacion() instanceof NodoLogico) {
            System.out.println(" NodoLogico");
        }

    }

    private void guardarVariableYCuarteto(String operador1, TipoDeVariable tipo, NodoId nodoId) {
        Cuarteto cuarteto = new Cuarteto(null, operador1, null, nodoId.getId(), TipoDeCuarteto.SOLO_HOJA);
        TuplaDeSimbolo variableAGuardar = new TuplaDeSimbolo(0, nodoId.getId(), tipo, Categoria.Variable, null);
        this.editor.getManTablas().guardarNuevaVariable(variableAGuardar, nodoId.getLinea(), nodoId.getColumna());
        this.editor.getManTablas().anadirCuarteto(cuarteto);
    }

    private void evaluacionDeNodoHojaExpresion(NodoDeclaracion nodoDeclaracion, NodoHojaExpresion nodoHoja, NodoId nodoId) {
        if (null != nodoDeclaracion.getTipo()) {
            switch (nodoDeclaracion.getTipo()) {
                case BOOLEAN: {
                    //Solo puede ser TRUE FALSE O ID
                    TipoDeHoja tipo = nodoHoja.getTipo();
                    if (tipo == TipoDeHoja.TRUE) {
                        //Se crea el cuarteto
                        guardarVariableYCuarteto("1", TipoDeVariable.BOOLEAN, nodoId);
                    } else if (tipo == TipoDeHoja.FALSE) {
                        //Se crea cuarteto
                        guardarVariableYCuarteto("0", TipoDeVariable.BOOLEAN, nodoId);
                    } else if (tipo == TipoDeHoja.IDENTIFICADOR) {
                        //Se crea cuarteto
                        TuplaDeSimbolo variableABuscar = this.editor.getManTablas().buscarVariable(nodoHoja.getValor());//Simbolo es el id al que se le esta igualando
                        if (variableABuscar != null) {
                            if (variableABuscar.getTipo() == TipoDeVariable.BOOLEAN) {
                                guardarVariableYCuarteto(variableABuscar.getNombre(), TipoDeVariable.BOOLEAN, nodoId);
                            } else {
                                //Error de conversion de tipos
                                String mensaje = "Error SEMANTICO no se puede convertir" + variableABuscar.getTipo() + "a Booleano" + ".Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                                ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                            }
                        } else {
                            //Error la variable no existe
                            String mensaje = "Error SEMANTICO la variable:" + nodoHoja.getValor() + " no ha sido declarada." + "Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                        }
                    } else {
                        //Error, de conversion de tipo
                        String mensaje = "Error SEMANTICO no se puede convertir " + tipo + " a Booleano.Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                        ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                    }
                    break;
                }
                case STRING: {
                    TipoDeHoja tipo = nodoHoja.getTipo();
                    if (tipo == TipoDeHoja.DECLARACION_STRING) {
                        guardarVariableYCuarteto(nodoHoja.getValor(), TipoDeVariable.STRING, nodoId);
                    } else if (tipo == TipoDeHoja.IDENTIFICADOR) {
                        TuplaDeSimbolo variableABuscar = this.editor.getManTablas().buscarVariable(nodoHoja.getValor());//Simbolo es el id al que se le esta igualando
                        if (variableABuscar != null) {
                            if (variableABuscar.getTipo() == TipoDeVariable.STRING) {
                                guardarVariableYCuarteto(variableABuscar.getNombre(), TipoDeVariable.STRING, nodoId);
                            } else {
                                //Error de conversion de tipos
                                String mensaje = "Error SEMANTICO no se puede convertir" + variableABuscar.getTipo() + "a String" + ".Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                                ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                            }
                        } else {
                            //Error la variable no existe
                            String mensaje = "Error SEMANTICO la variable:" + nodoHoja.getValor() + " no ha sido declarada." + "Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                        }
                    } else {
                        //ERROR,de conversion de tipo
                        String mensaje = "Error SEMANTICO no se puede convertir " + tipo + " a String.Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                        ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                    }
                    break;
                }
                case CHAR: {
                    TipoDeHoja tipo = nodoHoja.getTipo();
                    if (tipo == TipoDeHoja.DECLARACION_CARACTER) {
                        //Se crea el cuarteto
                        guardarVariableYCuarteto(nodoHoja.getValor(), TipoDeVariable.CHAR, nodoId);
                    } else if (tipo == TipoDeHoja.NUMERO_ENTERO) {
                        Long numero = Long.valueOf(nodoHoja.getValor());
                        if (numero >= TipoDeVariable.CHAR.getLimiteInferior() && numero <= TipoDeVariable.CHAR.getLimiteSuperior()) {
                            guardarVariableYCuarteto(nodoHoja.getValor(), TipoDeVariable.CHAR, nodoId);
                        } else {
                            //Error de limites
                            String mensaje = "Se han pasado los limites para char" + "Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());

                        }
                    } else if (tipo == TipoDeHoja.IDENTIFICADOR) {
                        TuplaDeSimbolo variableABuscar = this.editor.getManTablas().buscarVariable(nodoHoja.getValor());//Simbolo es el id al que se le esta igualando
                        if (variableABuscar != null) {
                            if (variableABuscar.getTipo() == TipoDeVariable.CHAR) {
                                guardarVariableYCuarteto(variableABuscar.getNombre(), TipoDeVariable.CHAR, nodoId);
                            } else {
                                //Error de conversion de tipos
                                String mensaje = "Error SEMANTICO no se puede convertir" + variableABuscar.getTipo() + "a Char" + ".Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                                ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                            }
                        } else {
                            //Error la variable no existe
                            String mensaje = "Error SEMANTICO la variable:" + nodoHoja.getValor() + " no ha sido declarada." + "Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                        }
                    } else {
                        //ERROR,de conversion de tipo
                        String mensaje = "Error SEMANTICO no se puede convertir " + tipo + " a CHAR.Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                        ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                    }
                    break;
                }
                case BYTE: {
                    TipoDeHoja tipo = nodoHoja.getTipo();
                    if (tipo == TipoDeHoja.NUMERO_ENTERO) {
                        Long numero = Long.valueOf(nodoHoja.getValor());
                        if (numero >= TipoDeVariable.BYTE.getLimiteInferior() && numero <= TipoDeVariable.BYTE.getLimiteSuperior()) {
                            guardarVariableYCuarteto(nodoHoja.getValor(), TipoDeVariable.BYTE, nodoId);
                        } else {
                            //Error de limites
                            String mensaje = "Se han pasado los limites para byte" + "Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                        }
                    } else if (tipo == TipoDeHoja.IDENTIFICADOR) {
                        TuplaDeSimbolo variableABuscar = this.editor.getManTablas().buscarVariable(nodoHoja.getValor());//Simbolo es el id al que se le esta igualando
                        if (variableABuscar != null) {
                            if (TipoDeVariable.BYTE.getJerarquia() >= variableABuscar.getTipo().getJerarquia()) {
                                guardarVariableYCuarteto(variableABuscar.getNombre(), TipoDeVariable.BYTE, nodoId);
                            } else {
                                //Error de conversion de tipos
                                String mensaje = "Error SEMANTICO no se puede convertir" + variableABuscar.getTipo() + "a Byte" + ".Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                                ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                            }
                        } else {
                            //Error la variable no existe
                            String mensaje = "Error SEMANTICO la variable:" + nodoHoja.getValor() + " no ha sido declarada." + "Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                        }
                    } else {
                        //ERROR,de conversion de tipo
                        String mensaje = "Error SEMANTICO no se puede convertir " + tipo + " a Byte.Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                        ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                    }
                    break;
                }
                case INT: {
                    TipoDeHoja tipo = nodoHoja.getTipo();
                    if (tipo == TipoDeHoja.NUMERO_ENTERO) {
                        //Se crea el cuarteto
                        Long numero = Long.valueOf(nodoHoja.getValor());
                        if (numero >= TipoDeVariable.INT.getLimiteInferior() && numero <= TipoDeVariable.INT.getLimiteSuperior()) {
                            guardarVariableYCuarteto(nodoHoja.getValor(), TipoDeVariable.INT, nodoId);
                        } else {
                            //Error de limites
                            String mensaje = "Se han pasado los limites para byte" + "Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                        }
                    } else if (tipo == TipoDeHoja.IDENTIFICADOR) {
                        TuplaDeSimbolo variableABuscar = this.editor.getManTablas().buscarVariable(nodoHoja.getValor());//Simbolo es el id al que se le esta igualando
                        if (variableABuscar != null) {
                            if (TipoDeVariable.INT.getJerarquia() >= variableABuscar.getTipo().getJerarquia()) {
                                guardarVariableYCuarteto(variableABuscar.getNombre(), TipoDeVariable.INT, nodoId);
                            } else {
                                //Error de conversion de tipos
                                String mensaje = "Error SEMANTICO no se puede convertir" + variableABuscar.getTipo() + "a Int" + ".Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                                ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                            }
                        } else {
                            //Error la variable no existe
                            String mensaje = "Error SEMANTICO la variable:" + nodoHoja.getValor() + " no ha sido declarada." + "Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                        }
                    } else {
                        //ERROR
                        String mensaje = "Error SEMANTICO no se puede convertir " + tipo + " a Int.Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                        ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                    }
                    break;
                }
                case LONG: {
                    TipoDeHoja tipo = nodoHoja.getTipo();
                    if (tipo == TipoDeHoja.NUMERO_ENTERO) {
                        //Se crea el cuarteto
                        guardarVariableYCuarteto(nodoHoja.getValor(), TipoDeVariable.LONG, nodoId);
                    } else if (tipo == TipoDeHoja.IDENTIFICADOR) {
                        TuplaDeSimbolo variableABuscar = this.editor.getManTablas().buscarVariable(nodoHoja.getValor());//Simbolo es el id al que se le esta igualando
                        if (variableABuscar != null) {
                            if (TipoDeVariable.LONG.getJerarquia() >= variableABuscar.getTipo().getJerarquia()) {
                                guardarVariableYCuarteto(variableABuscar.getNombre(), TipoDeVariable.LONG, nodoId);
                            } else {
                                //Error de conversion de tipos
                                String mensaje = "Error SEMANTICO no se puede convertir" + variableABuscar.getTipo() + "a Long" + ".Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                                ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                            }
                        } else {
                            //Error la variable no existe
                            String mensaje = "Error SEMANTICO la variable:" + nodoHoja.getValor() + " no ha sido declarada." + "Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                        }

                    } else {
                        //ERROR,de conversion de tipos
                        String mensaje = "Error SEMANTICO no se puede convertir " + tipo + " a Long.Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                        ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                    }
                    break;
                }
                case FLOAT: {
                    TipoDeHoja tipo = nodoHoja.getTipo();
                    if (tipo == TipoDeHoja.NUMERO_DECIMALF || tipo == TipoDeHoja.NUMERO_ENTERO || tipo == TipoDeHoja.DECLARACION_CARACTER) {
                        guardarVariableYCuarteto(nodoHoja.getValor(), TipoDeVariable.FLOAT, nodoId);
                    } else if (tipo == TipoDeHoja.IDENTIFICADOR) {
                        TuplaDeSimbolo variableABuscar = this.editor.getManTablas().buscarVariable(nodoHoja.getValor());//Simbolo es el id al que se le esta igualando
                        if (variableABuscar != null) {
                            if (TipoDeVariable.FLOAT.getJerarquia() >= variableABuscar.getTipo().getJerarquia()) {
                                guardarVariableYCuarteto(variableABuscar.getNombre(), TipoDeVariable.FLOAT, nodoId);
                            } else {
                                //Error de conversion de tipos
                                String mensaje = "Error SEMANTICO no se puede convertir" + variableABuscar.getTipo() + "a Float" + ".Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                                ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                            }
                        } else {
                            //Error la variable no existe
                            String mensaje = "Error SEMANTICO la variable:" + nodoHoja.getValor() + " no ha sido declarada." + "Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                        }
                    } else {
                        //ERROR,de conversion de tipos
                        String mensaje = "Error SEMANTICO no se puede convertir " + tipo + " a Float.Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                        ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                    }
                    break;
                }
                case DOUBLE: {
                    TipoDeHoja tipo = nodoHoja.getTipo();
                    if (tipo == TipoDeHoja.NUMERO_DECIMAL || tipo == TipoDeHoja.NUMERO_DECIMALF || tipo == TipoDeHoja.NUMERO_ENTERO || tipo == TipoDeHoja.DECLARACION_CARACTER) {
                        //Se crea el cuarteto
                        guardarVariableYCuarteto(nodoHoja.getValor(), TipoDeVariable.DOUBLE, nodoId);
                    } else if (tipo == TipoDeHoja.IDENTIFICADOR) {
                    } else {
                        TuplaDeSimbolo variableABuscar = this.editor.getManTablas().buscarVariable(nodoHoja.getValor());//Simbolo es el id al que se le esta igualando
                        if (variableABuscar != null) {
                            if (TipoDeVariable.DOUBLE.getJerarquia() >= variableABuscar.getTipo().getJerarquia()) {
                                guardarVariableYCuarteto(variableABuscar.getNombre(), TipoDeVariable.DOUBLE, nodoId);
                            } else {
                                //Error de conversion de tipos
                                String mensaje = "Error SEMANTICO no se puede convertir" + variableABuscar.getTipo() + "a Float" + ".Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                                ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                            }
                        } else {
                            //Error la variable no existe
                            String mensaje = "Error SEMANTICO la variable:" + nodoHoja.getValor() + " no ha sido declarada." + "Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                        }
                        //ERROR,de conversion de tipos
                        String mensaje = "Error SEMANTICO no se puede convertir " + tipo + " a Double.Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                        ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                    }
                    break;
                }
                default:
                    break;
            }
        }

    }

    private void evaluacionDeNodoExpresion(NodoDeclaracion nodoDeclaracion, NodoExpresion nodoExpresion) {
        //Se crea una lista la cual almacenara cuartetos
        //Recorrer recursivamente hasta llegar al nodo hoja
        //En el nodo hoja se evalua si la hoja corresponde al tipoDeVariableDeclarada
        //Al pasar al nodoExpresion, se evalua si la operacion es valida para el tipoDeVariableDeclarada
        //Si al final no existiecen errores, la lista del inicio pasa a agregarse a la lista general de cuartetos

        //Recorrido de arbol
        recorrerArbol(nodoDeclaracion, nodoExpresion);

    }

    private String recorrerArbol(NodoDeclaracion nodoDeclaracion, Nodo nodo) {
        if (nodo instanceof NodoHojaExpresion) {
            //Se comprueba que el nodo hoja sea menor o igual en jerarquia tipoDeVariableDeclarada
            NodoHojaExpresion n1 = (NodoHojaExpresion) nodo;
            if (verificarSiElementoDeHojaEsEquivalenteATipoDeAsignacion(nodoDeclaracion, n1)) {
                return n1.getValor();
            } else {
                //Error de conversion de datos
                String mensaje = "Error SEMANTICO " + n1.getTipo() + " no se puede convertir en " + nodoDeclaracion.getTipo() + ".Linea:" + n1.getLinea() + " Columna:" + n1.getColumna();
                ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
            }
            return "";
        } else {
            NodoExpresion n1 = (NodoExpresion) nodo;
            String h1 = recorrerArbol(nodoDeclaracion, n1.getHoja1());
            String h2 = recorrerArbol(nodoDeclaracion, n1.getHoja2());
            verificarOperacionParaVariableDeclarada(nodoDeclaracion, n1);
            //Al pasar al nodoExpresion, se evalua si la operacion es valida para el tipoDeVariableDeclarada,+-*/            
            if (!h1.isEmpty() && !h2.isEmpty()) {
                if (nodoDeclaracion.getTipo() == TipoDeVariable.STRING && n1.getOperacion() != OperacionAritmetica.MAS) {
                    //Error la operacion para string solo puede ser suma
                    String mensaje = "Error SEMANTICO para String solo es permitida la operacion SUMA(+).Linea:" + n1.getLinea() + " Columna:" + n1.getColumna();
                    ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                } else {
                    //Se crea un cuarteto
                    String numTemporal = String.valueOf(this.editor.getManTablas().obtenerNuevoTemporal());
                    Cuarteto nuevoCuarteto = new Cuarteto(n1.getOperacion().getSigno(), h1, h2, numTemporal, TipoDeCuarteto.SOLO_EXPRESION);
                    this.editor.getManTablas().anadirCuarteto(nuevoCuarteto);
                    return numTemporal;//La nueva temporal
                }
            }

            return "";
        }
    }

    public boolean verificarSiElementoDeHojaEsEquivalenteATipoDeAsignacion(NodoDeclaracion nodoDeclaracion, NodoHojaExpresion nodoHoja) {
        if (nodoDeclaracion.getTipo() == TipoDeVariable.STRING) {
            return true;
        } else {//Es de tipo numerico
            TipoDeHoja tipo = nodoHoja.getTipo();
            if (tipo == TipoDeHoja.DECLARACION_CARACTER) {
                if (nodoDeclaracion.getTipo().getJerarquia() >= TipoDeVariable.CHAR.getJerarquia()) {
                    return true;
                }
            } else if (tipo == TipoDeHoja.NUMERO_ENTERO) {//Comprende Long,byte,int,char
                Long numero = Long.valueOf(nodoHoja.getValor());
                TipoDeVariable tipoFinal;
                if (numero >= TipoDeVariable.CHAR.getLimiteInferior() && numero <= TipoDeVariable.CHAR.getLimiteSuperior()) {
                    tipoFinal = TipoDeVariable.CHAR;
                } else if (numero >= TipoDeVariable.BYTE.getLimiteInferior() && numero <= TipoDeVariable.BYTE.getLimiteSuperior()) {
                    tipoFinal = TipoDeVariable.BYTE;
                } else if (numero >= TipoDeVariable.INT.getLimiteInferior() && numero <= TipoDeVariable.INT.getLimiteSuperior()) {
                    tipoFinal = TipoDeVariable.INT;
                } else {
                    tipoFinal = TipoDeVariable.LONG;
                }

                if (nodoDeclaracion.getTipo().getJerarquia() >= tipoFinal.getJerarquia()) {
                    return true;
                }

            } else if (tipo == TipoDeHoja.NUMERO_DECIMALF) {
                if (nodoDeclaracion.getTipo().getJerarquia() >= TipoDeVariable.FLOAT.getJerarquia()) {
                    return true;
                }
            } else if (tipo == TipoDeHoja.NUMERO_DECIMAL) {
                if (nodoDeclaracion.getTipo().getJerarquia() >= TipoDeVariable.DOUBLE.getJerarquia()) {
                    return true;
                }
            } else if (tipo == TipoDeHoja.IDENTIFICADOR) {
                //Buscar el Id
                TuplaDeSimbolo variable = this.editor.getManTablas().buscarVariable(nodoHoja.getValor());
                if (variable != null) {
                    if (nodoDeclaracion.getTipo().getJerarquia() >= variable.getTipo().getJerarquia()) {
                        return true;
                    } else {
                        //Error tipos incompatibles
                        String mensaje = "Error SEMANTICO no se puede convertir" + variable.getTipo() + "a " + nodoDeclaracion.getTipo() + ".Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                        ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                    }
                } else {
                    //Error la variable no se ha declarado
                    String mensaje = "Error SEMANTICO la variable:" + nodoHoja.getValor() + " no ha sido declarada." + "Linea:" + nodoHoja.getLinea() + " Columna:" + nodoHoja.getColumna();
                    ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
                }
            } else {
                //Se supone que viene TRUE FALSE DECLARACION STRING y estas no se toman en cuenta
            }
        }
        return false;
    }

    public boolean verificarOperacionParaVariableDeclarada(NodoDeclaracion nodoDeclaracion, NodoExpresion nodoExpresion) {
        TipoDeVariable tipo = nodoDeclaracion.getTipo();
        if (tipo == TipoDeVariable.STRING) {
            if (nodoExpresion.getOperacion() == OperacionAritmetica.MAS) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

}
