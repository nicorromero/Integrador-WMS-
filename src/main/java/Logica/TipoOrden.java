/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Logica;

/**
 *
 * @author nicor
 */
public enum TipoOrden {
    INGRESO("ingreso"),
    EGRESO("egreso"),
    INTERNO("interno");
    
    private final String descripcion;
    
    TipoOrden (String descripcion)
    {   
        this.descripcion = descripcion;
    };

    public String getDescripcion() {
        return descripcion;
    }
    
   
}
