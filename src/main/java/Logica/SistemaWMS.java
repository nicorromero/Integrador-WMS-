/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Logica;

import Persistencia.ControladoraPersistencia;
import java.util.List;

public class SistemaWMS {
    
    ControladoraPersistencia controladora = new ControladoraPersistencia();    
    
    public Producto buscarProductoPorDescripcion(String txtProducto) {
        // 1. Buscamos el ID asociado a ese nombre
        Integer idEncontrado = controladora.obtenerIdProducto(txtProducto.trim());
        
        // 2. Si lo encontramos, usamos el método de buscar por ID que tanto te gusta
        if (idEncontrado != null) {
            return controladora.buscarProductoPorId(idEncontrado);
        }
        
        return null; // Si no hubo ID, el producto no existe
    }
 
    public Ubicacion buscarUbicacionPorCodigo(int codigo){       
        if (codigo <= 0) {
            return null; 
        }     
        return controladora.buscarUbicacionPorId(codigo);
    }
   
    public TipoOrden buscarTipoOrden (String descripcionOrden){
        //validacion
        if (descripcionOrden == null || descripcionOrden.trim().isEmpty()) {
            return null;
        }
        
        for (TipoOrden orden : TipoOrden.values()) {
          
            if ( orden.getDescripcion().equalsIgnoreCase(descripcionOrden.trim()) || orden.name().equalsIgnoreCase(descripcionOrden)) {
                return orden;
            }    
        }
        return null;
    }
    
    public TipoZona buscarTipoZona (String descripcionZona){
        if (descripcionZona == null || descripcionZona.trim().isEmpty()) {
            return null;
        }
        for (TipoZona zona : TipoZona.values()) 
        {
            if(zona.getDescripcion().equalsIgnoreCase(descripcionZona.trim())|| zona.name().equalsIgnoreCase(descripcionZona)) {
                return zona;
            }
        }   
        return null;   
    }
    
    
    public Producto crearNuevoProducto( String descripcion, String unidadMedida, double pesoPorUnidad) {
       // 2. Llamar al constructor de la Orden
       
        try {  //como un if que siempre da true 
            Producto nuevoProducto = new Producto(
           
                descripcion, 
                unidadMedida, 
                pesoPorUnidad
            );
            
            controladora.crearProducto(nuevoProducto);
            return nuevoProducto;    
        } 
        catch (IllegalArgumentException e) { 
            //muestra del error, podria dejar mensaje
            throw e; 
        }            
    }

    
    public Ubicacion crearNuevaUbicacion( String nave, String zona, String estanteria, String nivel) {
        // generador automatico de id para orden  
        TipoZona tipoEncontradoZona = buscarTipoZona(zona);
        
        if(tipoEncontradoZona == null) 
        {
            throw new IllegalArgumentException("el tipo zona: " + zona+ "' no existe en el sistema.");       
        }
        // 2. Llamar al constructor de la Orden
        try {  //como un if que siempre da true 
                Ubicacion nuevoUbicacion = new Ubicacion(        
                nave,  
                tipoEncontradoZona, 
                estanteria, 
                nivel
                );
                
                controladora.crearUbicacion(nuevoUbicacion);
                return nuevoUbicacion;
        } 
        catch (IllegalArgumentException e) { //como el else, se activa en false
            throw e; //muestra del error, podria dejar mensaje
        }
    }
    
    
    public Orden crearNuevaOrden(String descripcionProducto, int cantidad, String usuario, int uOrigen, String tipoOrden, int uDestino){
        
        Ubicacion ubicacionDestino = null; // Inicializada a null por defecto
                                           //marca rojo el null por parecer rebundante pero NO lo es
        //convierte el string en producto
        Producto productoEncontrado = buscarProductoPorDescripcion(descripcionProducto);
        
        //validacion
        if (productoEncontrado == null) {
        throw new IllegalArgumentException("El producto con descripción '" + descripcionProducto + "' no existe en el sistema.");
        }
        
        //convierte el string en ubicacion
        Ubicacion ubicacionEncontrada = buscarUbicacionPorCodigo(uOrigen);
        
        //validacion
        if (ubicacionEncontrada == null) {
        throw new IllegalArgumentException("La Ubicacion con descripción '" + uOrigen + "' no existe en el sistema.");
        }
        
        //convertir el string de TipoOrden en objeto 
        //validacion
        TipoOrden tipoEncontrado = buscarTipoOrden(tipoOrden);
        if(tipoEncontrado == null) {
            throw new IllegalArgumentException("el tipo orden: " + tipoOrden + "' no existe en el sistema.");       
        }
           
        //validacion y manejo de null para la segunda ubicacion 
        if (tipoEncontrado == TipoOrden.INTERNO ) 
        {
            //convierte el string en ubicacion
            ubicacionDestino = buscarUbicacionPorCodigo(uDestino);
            
            if ( ubicacionDestino == null || uDestino == 0 ) //uDestino no puede estar vacío
            {
                throw new IllegalArgumentException("Una orden de tipo INTERNO requiere un código de Ubicación de Destino.");
            }  
            if (ubicacionEncontrada.equals(ubicacionDestino)) //comparar que las ubicaciones no sean iguales 
            {
            throw new IllegalArgumentException("El origen y el destino no pueden ser la misma ubicación para una transferencia interna.");
            }
        }          
            try {  
                Orden nuevaOrden = new Orden(
                    usuario, 
                    productoEncontrado, 
                    cantidad,
                    ubicacionEncontrada, 
                    tipoEncontrado, 
                    ubicacionDestino 
                );  
                controladora.crearOrden(nuevaOrden);
                
                if (ubicacionDestino != null) {
                    controladora.editarUbicacion(ubicacionDestino);
                }
                
                return nuevaOrden;    
            } 
            catch (IllegalArgumentException e) {   
                throw e; 
            }            
    }
    
    public String consultarUbicacionStock(int ubicacion){
        
        Ubicacion ubicacionStock = buscarUbicacionPorCodigo(ubicacion);
        
        if (ubicacionStock == null) {
        return "Error: La ubicación con código " + ubicacion + " no existe en el sistema.";
        }
               
        return ubicacionStock.obtenerDetalleStock();
    }
    
    public String consultarProductoStock(String producto){
        Producto productoStock = buscarProductoPorDescripcion(producto);
        
        if (productoStock == null) {
            return "El producto '" + producto + "' no existe en el sistema.";
        }
        
        int stockTotal= 0;
        
        List<Ubicacion> ubicaciones = controladora.traerTodasLasUbicaciones();
        
        for(Ubicacion u : ubicaciones){
            
            stockTotal += u.StockporProducto(productoStock);
        }         
        return "Stock total de: " + productoStock.getDescripcion() +" = "+ stockTotal;
        
    }
    
    
}
