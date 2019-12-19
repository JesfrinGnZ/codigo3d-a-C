/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.manejoDeVariables;

import gnz.backend.nodoDeclaracion.NodoId;
import gnz.backend.nodoDeclaracion.TipoDeVariable;
import java.util.LinkedList;

/**
 *
 * @author jesfrin
 */
public class DeclaracionDeVariable {

    private TipoDeVariable tipo;
    private LinkedList<NodoId> ids;
    private String ambito;
    private int linea;
    private int columna;

    public DeclaracionDeVariable(TipoDeVariable tipo, LinkedList<NodoId> ids,String ambito, int linea, int columna) {
        this.tipo = tipo;
        this.ids = ids;
        this.linea = linea;
        this.columna = columna;
        this.ambito=ambito;
    }

    public TipoDeVariable getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeVariable tipo) {
        this.tipo = tipo;
    }

    public LinkedList<NodoId> getIds() {
        return ids;
    }

    public void setIds(LinkedList<NodoId> ids) {
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

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }
    
    
}
