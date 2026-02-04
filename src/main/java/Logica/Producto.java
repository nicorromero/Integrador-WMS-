/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

/**
 *
 * @author nicor
 */
@Entity
public class Producto implements Serializable {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoUnico;
    private String descripcion;
    private String unidadMedida; // Por ejemplo: "unidades", "kg", "litros"
    private double pesoPorUnidad; // Peso en kg de una sola unidad del producto 
    
    //hcer la lista con int que sean el codigo de las ubicaciones + un metodo que busque el codigo y sume en el momento

    
    //contructor: 
    public Producto( String descripcion, String unidadMedida, double pesoPorUnidad) {
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.pesoPorUnidad = pesoPorUnidad;   
    }
    
    public Producto (){}
  
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
    
    //setters

    public void setCodigoUnico(int codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public void setPesoPorUnidad(double pesoPorUnidad) {
        this.pesoPorUnidad = pesoPorUnidad;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.codigoUnico;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Producto other = (Producto) obj;
        return this.codigoUnico == other.codigoUnico;
    }    
}
