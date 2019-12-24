/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.nodoExpresion;

import gnz.backend.nodo.Nodo;
import gnz.backend.nodoDeclaracion.TipoDeVariable;
import java.util.LinkedList;

/**
 *
 * @author jesfrin
 */
public class NodoHojaExpresion implements Nodo {

    private TipoDeHoja tipo;
    private String valor;
    private int linea;
    private int columna;
    private TipoDeVariable tipoDEVariable;
    private String ambito;
    private LinkedList<Nodo> expresiones;

    /**
     * Para arreglos
     *
     * @param tipo
     * @param valor
     * @param linea
     * @param columna
     * @param expresiones
     */
    public NodoHojaExpresion(TipoDeHoja tipo, String valor, int linea, int columna, LinkedList<Nodo> expresiones) {
        this.tipo = tipo;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
        this.expresiones = expresiones;
        this.ambito = "";
    }

    /**
     * Para elementos minimos
     *
     * @param tipo
     * @param valor
     * @param linea
     * @param columna
     */
    public NodoHojaExpresion(TipoDeHoja tipo, String valor, int linea, int columna) {
        this.tipo = tipo;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
        this.ambito = "";
    }

    /**
     * Para guardar solo nombre y tipo
     *
     * @param tipoDeVariable
     * @param valor
     */
    public NodoHojaExpresion(TipoDeVariable tipoDeVariable, String valor) {
        this.tipoDEVariable = tipoDeVariable;
        this.valor = valor;
        this.ambito = "";
    }

    public NodoHojaExpresion(TipoDeHoja tipoDeHoja,TipoDeVariable tipoDeVariable, String valor,int linea,int columna) {
        this.tipoDEVariable = tipoDeVariable;
        this.valor = valor;
        this.tipo=tipoDeHoja;
        this.ambito = "";
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

    public TipoDeVariable getTipoDEVariable() {
        return tipoDEVariable;
    }

    public void setTipoDEVariable(TipoDeVariable tipoDEVariable) {
        this.tipoDEVariable = tipoDEVariable;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public LinkedList<Nodo> getExpresiones() {
        return expresiones;
    }

    public void setExpresiones(LinkedList<Nodo> expresiones) {
        this.expresiones = expresiones;
    }

}
