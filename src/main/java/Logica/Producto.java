/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nicor
 */
public class Producto {
    private static  int contadorIds =1;
    private int codigoUnico;
    private String descripcion;
    private String unidadMedida; // Por ejemplo: "unidades", "kg", "litros"
    private double pesoPorUnidad; // Peso en kg de una sola unidad del producto 
    
    //hcer la lista con int que sean el codigo de las ubicaciones + un metodo que busque el codigo y sume en el momento

    
    //contructor: 
    public Producto( String descripcion, String unidadMedida, double pesoPorUnidad) {
        this.codigoUnico = contadorIds++;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.pesoPorUnidad = pesoPorUnidad;   
    }
  
    //getters: 
    public int getCodigoUnico() {
        return codigoUnico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public double getPesoPorUnidad() {
        return pesoPorUnidad;
    }   
}
