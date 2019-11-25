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
public class NodoDeclaracion implements Nodo {

    private TipoDeVariable tipo;
    private LinkedList<Nodo> ids;
    private int linea;
    private int columna;

    public NodoDeclaracion(TipoDeVariable tipo, LinkedList<Nodo> ids) {
        this.tipo = tipo;
        this.ids = ids;
    }

    public TipoDeVariable getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeVariable tipo) {
        this.tipo = tipo;
    }

    public LinkedList<Nodo> getIds() {
        return ids;
    }

    public void setIds(LinkedList<Nodo> ids) {
        this.ids = ids;
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
