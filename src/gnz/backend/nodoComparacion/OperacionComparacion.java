/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.nodoComparacion;

/**
 *
 * @author jesfrin
 */
public enum OperacionComparacion {

    MENOR("<"),MAYOR(">"),MENOR_IGUAL("<="),MAYOR_IGUAL(">="),DISTINTO_DE("!="),IGUAL_A("==");
    
    private String simbolo;

    private OperacionComparacion(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
    
    
}
