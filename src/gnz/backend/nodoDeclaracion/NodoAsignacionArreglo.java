/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.nodoDeclaracion;

import gnz.backend.nodo.Nodo;
import java.util.LinkedList;

/**
 *
 * @author jesfrin
 */
public class NodoAsignacionArreglo implements Nodo{
    
    private String id;
    private LinkedList<Nodo> expresionesDeDimension;
    private Nodo expresionAsignacion;
    private int linea;
    private int columna;

    public NodoAsignacionArreglo(String id, LinkedList<Nodo> expresionesDeDimension, Nodo expresionAsignacion, int linea, int columna) {
        this.id = id;
        this.expresionesDeDimension = expresionesDeDimension;
        this.expresionAsignacion = expresionAsignacion;
        this.linea = linea;
        this.columna = columna;
    }

    
    
    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    
   

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedList<Nodo> getExpresionesDeDimension() {
        return expresionesDeDimension;
    }

    public void setExpresionesDeDimension(LinkedList<Nodo> expresionesDeDimension) {
        this.expresionesDeDimension = expresionesDeDimension;
    }

    public Nodo getExpresionAsignacion() {
        return expresionAsignacion;
    }

    public void setExpresionAsignacion(Nodo expresionAsignacion) {
        this.expresionAsignacion = expresionAsignacion;
    }
    
    
    
}
