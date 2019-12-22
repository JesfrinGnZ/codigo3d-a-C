/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gnz.backend.funcion;

import gnz.backend.nodoDeclaracion.TipoDeVariable;

/**
 *
 * @author jesfrin
 */
public class Parametro {
    
    private TipoDeVariable tipo;
    private String nombreDeParametro;
    
    public Parametro(TipoDeVariable tipo,String nombreDeParametro){
        this.tipo=tipo;
        this.nombreDeParametro=nombreDeParametro;
    }
    
    
    //Al ingresar uno se debe guardar como variable

    public TipoDeVariable getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeVariable tipo) {
        this.tipo = tipo;
    }

    public String getNombreDeParametro() {
        return nombreDeParametro;
    }

    public void setNombreDeParametro(String nombreDeParametro) {
        this.nombreDeParametro = nombreDeParametro;
    }

 
}
