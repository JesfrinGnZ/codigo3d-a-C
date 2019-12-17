/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.nodoDeclaracion;

/**
 *
 * @author jesfrin
 */
public class IncrementoDecremento {
    
    private String tipo;
    private String numero;

    public IncrementoDecremento(String tipo, String numero) {
        this.tipo = tipo;
        this.numero = numero;
    }

    
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    
    
}
