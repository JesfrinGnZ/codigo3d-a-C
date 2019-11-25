/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.nodoExpresion;

/**
 *
 * @author jesfrin
 */
public enum OperacionAritmetica {

    MAS("+"),MENOS("-"),POR("*"),DIV("/"),PORCENTAJE("%");
    
    
    private String signo;

    private OperacionAritmetica(String signo) {
        this.signo=signo;
    }

    public static OperacionAritmetica getMAS() {
        return MAS;
    }

    public static OperacionAritmetica getMENOS() {
        return MENOS;
    }

    public static OperacionAritmetica getPOR() {
        return POR;
    }

    public static OperacionAritmetica getDIV() {
        return DIV;
    }

    public static OperacionAritmetica getPORCENTAJE() {
        return PORCENTAJE;
    }

    public String getSigno() {
        return signo;
    }
    
    
    
}
