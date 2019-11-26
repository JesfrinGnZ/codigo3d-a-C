/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.nodoComparacion;

import gnz.backend.nodo.Nodo;

/**
 *
 * @author jesfrin
 */
public class NodoLogico implements Nodo{
    
    private OperacionLogica operacion;
    private Nodo nodo1;
    private Nodo nodo2;
    private int linea;
    private int columna;

    public NodoLogico(OperacionLogica operacion, Nodo nodo1, Nodo nodo2,int linea,int columna) {
        this.operacion = operacion;
        this.nodo1 = nodo1;
        this.nodo2 = nodo2;
        this.linea=linea;
        this.columna=columna;
    }

    public OperacionLogica getOperacion() {
        return operacion;
    }

    public void setOperacion(OperacionLogica operacion) {
        this.operacion = operacion;
    }

    public Nodo getNodo1() {
        return nodo1;
    }

    public void setNodo1(Nodo nodo1) {
        this.nodo1 = nodo1;
    }

    public Nodo getNodo2() {
        return nodo2;
    }

    public void setNodo2(Nodo nodo2) {
        this.nodo2 = nodo2;
    }

   
    
}
