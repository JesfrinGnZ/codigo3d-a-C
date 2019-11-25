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
public class NodoHojaExpresion implements Nodo{
    
    TipoDeHoja tipo;
    String valor;
    private int linea;
    private int columna;

    public NodoHojaExpresion(TipoDeHoja tipo, String valor,int linea, int columna) {
        this.tipo = tipo;
        this.valor = valor;
        this.linea=linea;
        this.columna=columna;
    }

    public TipoDeHoja getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeHoja tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
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
