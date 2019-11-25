/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.nodoDeclaracion;

import gnz.backend.nodo.Nodo;

/**
 *
 * @author jesfrin
 */
public class NodoId implements Nodo{

    private String id;
    private Nodo asignacion;
    private int linea;
    private int columna;

    public NodoId(String id, Nodo asignacion,int linea,int columna) {
        this.id = id;
        this.asignacion = asignacion;
        this.linea=linea;
        this.columna=columna;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Nodo getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(Nodo asignacion) {
        this.asignacion = asignacion;
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
    
    
    
}
