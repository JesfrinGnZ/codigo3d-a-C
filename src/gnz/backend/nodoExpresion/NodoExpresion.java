/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.nodoExpresion;

import gnz.backend.nodo.Nodo;

/**
 *
 * @author jesfrin
 */
public class NodoExpresion implements Nodo{
    
    private OperacionAritmetica operacion;
    private Nodo hoja1;
    private Nodo hoja2;
    private int linea;
    private int columna;

    public NodoExpresion(OperacionAritmetica operacion, Nodo hoja1, Nodo hoja2,int linea,int columna) {
        this.operacion = operacion;
        this.hoja1 = hoja1;
        this.hoja2 = hoja2;
        this.linea=linea;
        this.columna=columna;
    }

    public OperacionAritmetica getOperacion() {
        return operacion;
    }

    public void setOperacion(OperacionAritmetica operacion) {
        this.operacion = operacion;
    }

    public Nodo getHoja1() {
        return hoja1;
    }

    public void setHoja1(Nodo hoja1) {
        this.hoja1 = hoja1;
    }

    public Nodo getHoja2() {
        return hoja2;
    }

    public void setHoja2(Nodo hoja2) {
        this.hoja2 = hoja2;
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
