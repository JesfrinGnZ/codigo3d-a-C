/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.tablas;

import gnz.backend.funcion.Parametro;
import gnz.backend.nodoDeclaracion.TipoDeVariable;
import java.util.LinkedList;

/**
 *
 * @author jesfrin
 */
public class TuplaDeSimbolo {

    private int numero;
    private String nombre;
    private TipoDeVariable tipo;//Para una funcion sera su tipo de retorno
    private Categoria categoria;
    private String ambito;//Global="", o el nombre de la funcion
    //Arreglo
    private int numeroDimensiones;
    private LinkedList<String> dimensionesArreglo;
    //Funciones
    private Integer numeroDeParametros;
    private LinkedList<Parametro> parametrosDeFuncion;

    //Para funciones
    public TuplaDeSimbolo(String nombre,TipoDeVariable tipoDeRetorno){
        this.categoria=Categoria.Subprograma;
        
    }
    
    
    public TuplaDeSimbolo(int numero, String nombre, TipoDeVariable tipo, Categoria categoria, Integer numeroDeParametros,String ambito) {
        this.numero = numero;
        this.nombre = nombre;
        this.tipo = tipo;
        this.categoria = categoria;
        this.numeroDeParametros = numeroDeParametros;
        this.ambito=ambito;
    }

    public TuplaDeSimbolo(int numero, String nombre, TipoDeVariable tipo, Categoria categoria, Integer numeroDeParametros, int numeroDimensiones, LinkedList<String> dimensionesArreglo) {
        this.numero = numero;
        this.nombre = nombre;
        this.tipo = tipo;
        this.categoria = categoria;
        this.numeroDeParametros = numeroDeParametros;
        this.numeroDimensiones = numeroDimensiones;
        this.dimensionesArreglo = dimensionesArreglo;
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

    public LinkedList<String> getDimensionesArreglo() {
        return dimensionesArreglo;
    }

    public void setDimensionesArreglo(LinkedList<String> dimensionesArreglo) {
        this.dimensionesArreglo = dimensionesArreglo;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

}
