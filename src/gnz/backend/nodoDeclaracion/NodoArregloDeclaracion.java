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
public class NodoArregloDeclaracion implements Nodo{
    
    private TipoDeVariable tipo;
    private LinkedList<NodoId> listaDeIds;
    private LinkedList<Nodo> expresiones;
    private int linea;
    private int columna;

    public NodoArregloDeclaracion(TipoDeVariable tipo, LinkedList<NodoId> listaDeIds, LinkedList<Nodo> expresiones, int linea, int columna) {
        this.tipo = tipo;
        this.listaDeIds = listaDeIds;
        this.expresiones = expresiones;
        this.linea = linea;
        this.columna = columna;
    }

   
    public LinkedList<NodoId> getListaDeIds() {
        return listaDeIds;
    }

    public void setListaDeIds(LinkedList<NodoId> listaDeIds) {
        this.listaDeIds = listaDeIds;
    }

    public LinkedList<Nodo> getExpresiones() {
        return expresiones;
    }

    public void setExpresiones(LinkedList<Nodo> expresiones) {
        this.expresiones = expresiones;
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

    public TipoDeVariable getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeVariable tipo) {
        this.tipo = tipo;
    }
    
    
    
}
