/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.tablas;

import gnz.backend.cuarteto.Cuarteto;
import gnz.backend.errores.ManejadorDeErrores;
import gnz.backend.nodoDeclaracion.TipoDeVariable;
import gnz.gui.frames.EditorDeTextoFrame;
import java.util.LinkedList;

/**
 *
 * @author jesfrin
 */
public class ManejadorDeTablas {

    private LinkedList<Cuarteto> tablaDeCuarteto;
    private LinkedList<TuplaDeSimbolo> tablaDeSimbolos;
    private int numeroDeTemporal;
    private int numeroDeLabel;
    private EditorDeTextoFrame editor;

    public ManejadorDeTablas(EditorDeTextoFrame editor) {
        this.tablaDeCuarteto = new LinkedList<>();
        this.tablaDeSimbolos = new LinkedList<>();
        numeroDeLabel = 0;
        numeroDeTemporal = 0;
        this.editor = editor;
    }

    public LinkedList<Cuarteto> getTablaDeCuarteto() {
        return tablaDeCuarteto;
    }

    public void setTablaDeCuarteto(LinkedList<Cuarteto> tablaDeCuarteto) {
        this.tablaDeCuarteto = tablaDeCuarteto;
    }

    public LinkedList<TuplaDeSimbolo> getTablaDeSimbolos() {
        return tablaDeSimbolos;
    }

    public void setTablaDeSimbolos(LinkedList<TuplaDeSimbolo> tablaDeSimbolos) {
        this.tablaDeSimbolos = tablaDeSimbolos;
    }

    //****************************************************Acciones para tabla de simbolos**********************************************
    /**
     * Busca una variable en su ambito
     * @param nombre
     * @param ambito
     * @return 
     */
    public TuplaDeSimbolo buscarVariable(String nombre, String ambito) {
        TuplaDeSimbolo tupla = null;
        for (TuplaDeSimbolo s : tablaDeSimbolos) {
            if (s.getNombre().equals(nombre) && s.getAmbito().equalsIgnoreCase(ambito)) {
                tupla = s;
            }
        }
        return tupla;
    }
    /**
     * Recibe una tupla a guardar con su respectivo ambito,
     * revisa si ya ha sido declarada en ese ambito
     * @param simbolo
     * @param linea
     * @param columna 
     */
    public void guardarVariable(TuplaDeSimbolo simbolo, int linea, int columna) {
        TuplaDeSimbolo tupla = buscarVariable(simbolo.getNombre(), simbolo.getAmbito());//simbolo.getAmbito
        if (tupla == null) {//La variable no ha sido declarada en ese ambito
            tablaDeSimbolos.add(simbolo);
        } else {//Error semantico
            String mensaje = "Error SEMANTICO La variable:" + simbolo.getNombre() + " ya ha sido declarada.Linea:" + linea + " Columna:" + columna;
            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
        }
    }

    
    
    
    
    public TuplaDeSimbolo buscarVariable(String nombre) {
        for (TuplaDeSimbolo simbolo : tablaDeSimbolos) {
            if (simbolo.getNombre().equals(nombre)) {
                return simbolo;
            }
        }
        return null;
    }

    public void guardarNuevaVariable(TuplaDeSimbolo simbolo, int linea, int columna) {
        TuplaDeSimbolo simboloEncontrado = buscarVariable(simbolo.getNombre());
        if (simboloEncontrado == null) {
            tablaDeSimbolos.add(simbolo);
        } else {
            String mensaje = "Error SEMANTICO La variable:" + simbolo.getNombre() + " ya ha sido declarada.Linea:" + linea + " Columna:" + columna;
            ManejadorDeErrores.escribirErrorSemantico(mensaje, editor.getErroresTextArea());
        }
    }

    //****************************************************Acciones para cuarteto******************************************************
    public void anadirCuarteto(Cuarteto e) {
        this.tablaDeCuarteto.add(e);
    }

    public int obtenerNuevoTemporal() {
        this.numeroDeTemporal++;
        return numeroDeTemporal;
    }

    public int obtenerNuevoNumeroDeLabel() {
        this.numeroDeLabel++;
        return numeroDeLabel;
    }

    public void escribirCuartetos() {
        for (Cuarteto cuarteto : tablaDeCuarteto) {
            switch (cuarteto.getTipoDeCuarteto()) {
                case ASIGNACION: {
                    this.editor.getCodigo3dTextArea().append(cuarteto.getResultado() + "=" + cuarteto.getOperador1() + "\n");
                    break;
                }
                case SOLO_EXPRESION: {
                    this.editor.getCodigo3dTextArea().append(cuarteto.getResultado() + "=" + cuarteto.getOperador1() + cuarteto.getOperando() + cuarteto.getOperador2() + "\n");
                    break;
                }
                case IF: {
                    this.editor.getCodigo3dTextArea().append("If(" + cuarteto.getOperador1() + cuarteto.getOperando() + cuarteto.getOperador2() + ") goto " + cuarteto.getResultado() + "\n");
                    break;
                }
                case GOTO: {//Tiene el padre de si y el que no
                    this.editor.getCodigo3dTextArea().append("goto " + cuarteto.getResultado() + "\n");
                    //this.editor.getCodigo3dTextArea().append(cuarteto.getOperador1()+"\n");
                    break;
                }
                case SOLO_LABEL: {
                    this.editor.getCodigo3dTextArea().append(cuarteto.getResultado() + "\n");
                    break;
                }
                case GOTOSALIDA: {
                    this.editor.getCodigo3dTextArea().append(cuarteto.getOperando() + " " + cuarteto.getResultado() + "\n");
                    break;
                }

            }
        }
    }
}
