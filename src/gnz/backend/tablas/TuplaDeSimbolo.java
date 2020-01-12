/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.tablas;

import gnz.backend.funcion.Parametro;
import gnz.backend.nodoDeclaracion.TipoDeVariable;
import gnz.backend.nodoExpresion.NodoHojaExpresion;
import java.util.LinkedList;

/**
 *
 * @author jesfrin
 */
public class TuplaDeSimbolo {

    private int numero;
    private String nombre;
    private TipoDeVariable tipo;//
    private Categoria categoria;
    private String ambito;//Global="", o el nombre de la funcion
    //Arreglo
    private int numeroDimensiones;
    private LinkedList<NodoHojaExpresion> dimensionesArreglo;
    //Funciones
    private TipoDeVariable tipoDeRetorno;
    private Integer numeroDeParametros;
    private LinkedList<Parametro> parametrosDeFuncion;
    //Para assembler
    private int posicionEnMemoria;
    
    
    
    //Para funciones
    public TuplaDeSimbolo(String nombre, TipoDeVariable tipoDeRetorno) {
        this.categoria = Categoria.Subprograma;

    }

    /**
     * Para variables normales
     *
     * @param numero
     * @param nombre
     * @param tipo
     * @param ambito
     */
    public TuplaDeSimbolo(int numero, String nombre, TipoDeVariable tipo, String ambito) {
        this.numero = numero;
        this.nombre = nombre;
        this.tipo = tipo;
        this.categoria = Categoria.Variable;
        this.ambito = ambito;
        this.numeroDimensiones = 0;
    }

    /**
     * Para arreglos
     *
     * @param numero
     * @param nombre
     * @param tipo
     * @param numeroDimensiones
     * @param dimensionesArreglo
     * @param ambito
     */
    public TuplaDeSimbolo(int numero, String nombre, TipoDeVariable tipo, int numeroDimensiones, LinkedList<NodoHojaExpresion> dimensionesArreglo, String ambito) {
        this.numero = numero;
        this.nombre = nombre;
        this.tipo = tipo;
        this.categoria = Categoria.Arreglo;
        this.numeroDimensiones = numeroDimensiones;
        this.dimensionesArreglo = dimensionesArreglo;
        this.ambito = ambito;
    }

    /**
     * Para funciones
     *
     * @param numero
     * @param nombre
     * @param tipoDeRetorno
     * @param numeroDeParametros
     * @param parametrosDeFuncion
     */
    public TuplaDeSimbolo(int numero, String nombre, TipoDeVariable tipoDeRetorno, Integer numeroDeParametros, LinkedList<Parametro> parametrosDeFuncion) {
        this.ambito = "";
        this.categoria = Categoria.Subprograma;
        this.numero = numero;
        this.nombre = nombre;
        this.tipoDeRetorno = tipoDeRetorno;
        this.numeroDeParametros = numeroDeParametros;
        this.parametrosDeFuncion = parametrosDeFuncion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoDeVariable getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeVariable tipo) {
        this.tipo = tipo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Integer getNumeroDeParametros() {
        return numeroDeParametros;
    }

    public void setNumeroDeParametros(Integer numeroDeParametros) {
        this.numeroDeParametros = numeroDeParametros;
    }

    public int getNumeroDimensiones() {
        return numeroDimensiones;
    }

    public void setNumeroDimensiones(int numeroDimensiones) {
        this.numeroDimensiones = numeroDimensiones;
    }

    public LinkedList<NodoHojaExpresion> getDimensionesArreglo() {
        return dimensionesArreglo;
    }

    public void setDimensionesArreglo(LinkedList<NodoHojaExpresion> dimensionesArreglo) {
        this.dimensionesArreglo = dimensionesArreglo;
    }

    public LinkedList<Parametro> getParametrosDeFuncion() {
        return parametrosDeFuncion;
    }

    public void setParametrosDeFuncion(LinkedList<Parametro> parametrosDeFuncion) {
        this.parametrosDeFuncion = parametrosDeFuncion;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public TipoDeVariable getTipoDeRetorno() {
        return tipoDeRetorno;
    }

    public void setTipoDeRetorno(TipoDeVariable tipoDeRetorno) {
        this.tipoDeRetorno = tipoDeRetorno;
    }

    public int getPosicionEnMemoria() {
        return posicionEnMemoria;
    }

    public void setPosicionEnMemoria(int posicionEnMemoria) {
        this.posicionEnMemoria = posicionEnMemoria;
    }

}
