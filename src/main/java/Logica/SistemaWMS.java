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
public class SistemaWMS {
    
    public SistemaWMS() 
    {
        this.inicializarDatosDePrueba(); 
    }
    
    // Método privado para mantener el constructor limpio
    private void inicializarDatosDePrueba() {
        // Ejemplo: Añadir una ubicación inicial
        Ubicacion u1 = new Ubicacion( "naveA", TipoZona.ALMACENAMIENTO, "E01", "N01");
        ubicaciones.add(u1);
        
        Ubicacion u2 = new Ubicacion( "naveA", TipoZona.ALMACENAMIENTO, "E01", "N02");
        ubicaciones.add(u2);
        
        
        // Ejemplo: Añadir un producto inicial
        Producto p1 = new Producto("zapatilas", "Litro", 1.0);
        productos.add(p1);
    }
    
    
    //de aca salen registros de Stock
    private final List<Producto> productos = new ArrayList<>();
    private final List<Ubicacion> ubicaciones = new ArrayList<>();
    private final List<Orden> Ordenes = new ArrayList<>();
    //contador para IDs
 
    

    
    public List<Producto> getProductos() {
        return productos;
    }

    public List<Ubicacion> getUbicaciones() {
        return ubicaciones;
    }

    public List<Orden> getOrdenes() {
        return Ordenes;
    }


    


    //Para leer los txt del frame y tranformar a objeto
    public Producto buscarProductoPorDescripcion(String txtProducto){
        for (Producto p : productos){
            if(p.getDescripcion().equalsIgnoreCase(txtProducto.trim()))
            {
                return p;
            }
        }
                return null;
    }
    
    //Para leer los txt del frame y tranformar a objeto
    public Ubicacion buscarUbicacionPorCodigo(int codigo){
        
        for (Ubicacion u : ubicaciones){
            if(u.getCodigoUnico() == codigo)
            {
                return u;
            }
        }
            return null;
    }
    
    public TipoOrden buscarTipoOrden (String descripcionOrden){
        for (TipoOrden orden : TipoOrden.values()) 
        {
            if(orden.getDescripcion().equalsIgnoreCase(descripcionOrden))
            {
                return orden;
            }
        }
            return null;
    }
        
    public TipoZona buscarTipoZona (String descripcionZona){
        for (TipoZona zona : TipoZona.values()) 
        {
            if(zona.getDescripcion().equalsIgnoreCase(descripcionZona)|| zona.name().equalsIgnoreCase(descripcionZona))
            {
                return zona;
            }
        }   
            return null;   
    }
  
    
    /**
     * Crea y registra una nueva Orden (Movimiento) en el sistema.
     * @param descripcionProducto El string del objeto.
     * @param cantidad La cantidad a mover.
     * @param usuario El String del usuario logueado.
     * @param uOrigen La Ubicacion de donde sale o entra el producto.
     * @param tipoOrden
     * @param uDestino La Ubicacion de destino (solo se usa en INTERNO).
     * @return La Orden creada o lanza una excepción.
     */
    
    //recibe solo tipo de datos simples para trabajarlos en el package logica
    public Orden crearNuevaOrden(String descripcionProducto, int cantidad, String usuario, int uOrigen, String tipoOrden, int uDestino) {
        
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
            try {  //como un if que siempre da true 
                Orden nuevaOrden = new Orden(
                    usuario, 
                    productoEncontrado, 
                    cantidad,
                    ubicacionEncontrada, 
                    tipoEncontrado, 
                    ubicacionDestino 
                );  
                Ordenes.add(nuevaOrden);
                return nuevaOrden;    
            } 
            catch (IllegalArgumentException e) {   
                throw e; 
            }            
    }
    
    public Producto crearNuevoProducto( String descripcion, String unidadMedida, double pesoPorUnidad) {
        
 
      

        // 2. Llamar al constructor de la Orden
        try {  //como un if que siempre da true 
            Producto nuevoProducto = new Producto(
           
                descripcion, 
                unidadMedida, 
                pesoPorUnidad
            );
            
            productos.add(nuevoProducto);
            
            return nuevoProducto;    
        } 
        catch (IllegalArgumentException e) { 
            
            throw e; //muestra del error, podria dejar mensaje
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
            
                ubicaciones.add(nuevoUbicacion);
            
                return nuevoUbicacion;
        } 
        catch (IllegalArgumentException e) { //como el else, se activa en false
            throw e; //muestra del error, podria dejar mensaje
        }
    }
}
