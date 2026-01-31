/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Logica.Orden;
import Logica.Producto;
import Logica.Ubicacion;
import java.util.List;
/**
 *
 * @author nicor
 */
public class ControladoraPersistencia {
    ProductoJpaController  productoJpa = new ProductoJpaController();
    
    UbicacionJpaController  ubicacionJpa = new UbicacionJpaController();
    
    OrdenJpaController  ordenJpa = new OrdenJpaController();
        
    //METODOS DE PRODUCTO
    public void crearProducto (Producto producto){
        productoJpa.create(producto);
    }
    
    public Integer obtenerIdProducto(String descripcion) {
        return productoJpa.findIdByDescripcion(descripcion);
    }
    
    public Producto buscarProductoPorId(int id) {   
        return productoJpa.findProducto(id); 
    }

    
    //METODOS DE UBICACION
    public void crearUbicacion (Ubicacion ubicacion){
        ubicacionJpa.create(ubicacion);
    }
    
    public Ubicacion buscarUbicacionPorId(int id) {
        return ubicacionJpa.findUbicacion(id); 
    }
    
    public List<Ubicacion> traerTodasLasUbicaciones() {
    return ubicacionJpa.findUbicacionEntities();
    }
    

    //METODOS DE ORDEN
    public void crearOrden (Orden orden){
        ordenJpa.create(orden);
    }
    
    
    
}
