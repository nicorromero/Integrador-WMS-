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
        Ubicacion u1 = new Ubicacion(contadorUbicaciones++, "naveA", TipoZona.ALMACENAMIENTO, "E01", "N01");
        ubicaciones.add(u1);
        
        Ubicacion u2 = new Ubicacion(contadorUbicaciones++, "naveA", TipoZona.ALMACENAMIENTO, "E01", "N02");
        ubicaciones.add(u2);
        
        
        // Ejemplo: Añadir un producto inicial
        Producto p1 = new Producto(contadorProductos++, "zapatilas", "Litro", 1.0);
        productos.add(p1);
    }
    
    
    //de aca salen registros de Stock
    private final List<Producto> productos = new ArrayList<>();
    private final List<Ubicacion> ubicaciones = new ArrayList<>();
    private final List<Orden> Ordenes = new ArrayList<>();
    //contador para IDs
    private int contadorOrdenes = 1;
    private int contadorProductos = 1;
    private int contadorUbicaciones =1;

    
    public List<Producto> getProductos() {
        return productos;
    }

    public List<Ubicacion> getUbicaciones() {
        return ubicaciones;
    }

    public List<Orden> getOrdenes() {
        return Ordenes;
    }

    public int getContadorOrdenes() {
        return contadorOrdenes;
    }

    public int getContadorProductos() {
        return contadorProductos;
    }

    public int getContadorUbicaciones() {
        return contadorUbicaciones;
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
            if(zona.getDescripcion().equalsIgnoreCase(descripcionZona))
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
        TipoOrden tipoEncontrado = buscarTipoOrden(tipoOrden);
        if(tipoEncontrado == null) {
            throw new IllegalArgumentException("el tipo orden: " + tipoOrden + "' no existe en el sistema.");       
        }
        
        int nuevoNumOrden = contadorOrdenes++; 
        
        //validacion y manejo de null para la segunda ubicacion 
        if (tipoEncontrado == TipoOrden.INTERNO) 
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
                    
            try {  //como un if que siempre da true 
                Orden nuevaOrden = new Orden(
                    nuevoNumOrden, 
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
            catch (IllegalArgumentException e) 
            {   //como el else, se activa en false
                contadorOrdenes--; //descuenta el id en caso de false 
                throw e; //muestra del error, podria dejar mensaje
            }        
        } 
        
        if(tipoEncontrado == TipoOrden.EGRESO || tipoEncontrado == TipoOrden.INTERNO){
               
            
                try {  
                    //como un if que siempre da true 
                    Orden nuevaOrden = new Orden(
                    nuevoNumOrden, 
                    usuario, 
                    productoEncontrado, 
                    cantidad, 
                    ubicacionEncontrada, 
                    tipoEncontrado, 
                    ubicacionDestino // Si es INGRESO/EGRESO, uDestino será null, lo cual está bien si el constructor lo maneja.
                    );
            
                    Ordenes.add(nuevaOrden);
                    return nuevaOrden;
                } 
                catch (IllegalArgumentException e){
                 
                    //como el else, se activa en false
                    contadorOrdenes--; //descuenta el id en caso de false 
                    throw e; //muestra del error, podria dejar mensaje
                }
        }
        
        return null;
        
    }
    
    public Producto crearNuevoProducto( String descripcion, String unidadMedida, double pesoPorUnidad) {
        
        // generador automatico de id para orden
        int nuevoIdProducto = contadorProductos++; 

        // 2. Llamar al constructor de la Orden
        try {  //como un if que siempre da true 
            Producto nuevoProducto = new Producto(
                nuevoIdProducto,  
                descripcion, 
                unidadMedida, 
                pesoPorUnidad
            );
            
            productos.add(nuevoProducto);
            
            return nuevoProducto;    
        } 
        catch (IllegalArgumentException e) { 
            //como el else, se activa en false
            contadorProductos--; //descuenta el id en caso de false 
            throw e; //muestra del error, podria dejar mensaje
        }
             
        
    }
        
    public Ubicacion crearNuevaUbicacion( String nave, String zona, String estanteria, String nivel) {
        
        // generador automatico de id para orden
        int nuevoIdUbicacion = contadorUbicaciones++; 
        
        
        TipoZona tipoEncontradoZona = buscarTipoZona(zona);
        
        if(tipoEncontradoZona == null) 
        {
            throw new IllegalArgumentException("el tipo zona: " + zona+ "' no existe en el sistema.");       
        }
        

        // 2. Llamar al constructor de la Orden
        try {  //como un if que siempre da true 
                Ubicacion nuevoUbicacion = new Ubicacion(
                nuevoIdUbicacion,    
                nave,  
                tipoEncontradoZona, 
                estanteria, 
                nivel
                );
            
                ubicaciones.add(nuevoUbicacion);
            
                return nuevoUbicacion;
        } 
        catch (IllegalArgumentException e) { //como el else, se activa en false
            contadorUbicaciones--; //descuenta el id en caso de false 
            throw e; //muestra del error, podria dejar mensaje
        }
    }
}
