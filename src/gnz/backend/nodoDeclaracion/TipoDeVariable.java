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
public enum TipoDeVariable {

    STRING(10),BOOLEAN(10),CHAR(-1*Math.pow(2, 16),Math.pow(2, 16),1),BYTE(-1*Math.pow(2, 8),Math.pow(2, 8),2),INT(-1*Math.pow(2, 31),Math.pow(2, 31),3,"int"),LONG(4),FLOAT(5),DOUBLE(6),VOID("");
    
    private Double limiteInferior;
    private Double limiteSuperior;
    private Integer jerarquia;
    private String nombre;

    private TipoDeVariable() {
    }

    private TipoDeVariable(Integer jerarquia) {
        this.jerarquia = jerarquia;
    }

    
    private TipoDeVariable(Double limiteInferior, Double limiteSuperior, Integer jerarquia) {
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
        this.jerarquia = jerarquia;
    }
    
        private TipoDeVariable(Double limiteInferior, Double limiteSuperior, Integer jerarquia,String nombre) {
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
        this.jerarquia = jerarquia;
        this.nombre=nombre;
    }

  
    private TipoDeVariable(String nombre){
        this.nombre=nombre;
    }

    public double getLimiteInferior() {
        return limiteInferior;
    }

    public void setLimiteInferior(double limiteInferior) {
        this.limiteInferior = limiteInferior;
    }

    public double getLimiteSuperior() {
        return limiteSuperior;
    }

    public void setLimiteSuperior(double limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }

    public int getJerarquia() {
        return jerarquia;
    }

    public void setJerarquia(int jerarquia) {
        this.jerarquia = jerarquia;
    }

    public String getNombre() {
        return nombre;
    }
    
    
    
}
