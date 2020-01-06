/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.cuarteto;

import gnz.backend.nodoDeclaracion.TipoDeVariable;

/**
 *
 * @author jesfrin
 */
public class Cuarteto {

    private String operando;
    private String operador1;
    private String operador2;
    private String resultado;
    public TipoDeCuarteto tipoDeCuarteto;
    public TipoDeVariable tipoDeVariable;

    public Cuarteto(String operando, String operador1, String operador2, String resultado, TipoDeCuarteto tipoDeCuarteto, TipoDeVariable tipoDeVariable) {
        this.operando = operando;
        this.operador1 = operador1;
        this.operador2 = operador2;
        this.resultado = resultado;
        this.tipoDeCuarteto = tipoDeCuarteto;
        this.tipoDeVariable = tipoDeVariable;
    }

    public Cuarteto(String operando, String operador1, String operador2, String resultado, TipoDeCuarteto tipoDeCuarteto) {
        this.operando = operando;
        this.operador1 = operador1;
        this.operador2 = operador2;
        this.resultado = resultado;
        this.tipoDeCuarteto = tipoDeCuarteto;
        this.tipoDeVariable = TipoDeVariable.VOID;
    }

    public String getOperando() {
        return operando;
    }

    public void setOperando(String operando) {
        this.operando = operando;
    }

    public String getOperador1() {
        return operador1;
    }

    public void setOperador1(String operador1) {
        this.operador1 = operador1;
    }

    public String getOperador2() {
        return operador2;
    }

    public void setOperador2(String operador2) {
        this.operador2 = operador2;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public TipoDeCuarteto getTipoDeCuarteto() {
        return tipoDeCuarteto;
    }

    public void setTipoDeCuarteto(TipoDeCuarteto tipoDeCuarteto) {
        this.tipoDeCuarteto = tipoDeCuarteto;
    }

    public TipoDeVariable getTipoDeVariable() {
        return tipoDeVariable;
    }

    public void setTipoDeVariable(TipoDeVariable tipoDeVariable) {
        this.tipoDeVariable = tipoDeVariable;
    }

}
