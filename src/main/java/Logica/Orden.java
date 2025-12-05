/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;


import java.util.Date;
/**
 *
 * @author nicor
 */

public class Orden {
    private static int contadorIds = 1;
    private int codigoUnico;
    private String usuarioResponsable;
    private Date fecha;
    private Producto producto;
    private int cantidad;
    private Ubicacion ubicacionOrigen;
    private TipoOrden tipo;
    private Ubicacion ubicacionDestino= null;

    //contructor:
    public Orden(String usuarioResponsable, Producto producto, int cantidad, Ubicacion ubicacionOrigen, TipoOrden tipo, Ubicacion ubicacionDestino){
    this.codigoUnico = contadorIds++;
    this.usuarioResponsable = usuarioResponsable;
    this.fecha = new Date();
    this.producto = producto;
    this.cantidad = cantidad;
    this.ubicacionOrigen = ubicacionOrigen;
    this.tipo = tipo;
    this.ubicacionDestino = ubicacionDestino;
        
       
        //validaciones
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (ubicacionOrigen == null) {
            throw new IllegalArgumentException("La ubicación de origen no puede ser nula");
        }
        
            //manejo del null para tipo Orden
        if (tipo == TipoOrden.INTERNO) 
        {       //DEBE tener una ubicación destino.
            if (ubicacionDestino == null) 
            {
                throw new IllegalArgumentException("Una orden de tipo INTERNO requiere una Ubicación de Destino.");
            } 
        } else //tipo orden NO es interno
            {   //NO debe tener una ubicación destino
                if (ubicacionDestino != null) 
                {
                    throw new IllegalArgumentException("Las órdenes de INGRESO y EGRESO NO deben especificar una Ubicación de Destino.");
                }
            }   
        
        
        //registra la orden segun el tipo
        switch (tipo) {
            case INGRESO -> OrdenIngreso();
            case EGRESO -> OrdenEgreso();
            case INTERNO -> OrdenInterno(ubicacionDestino);
        }
        
    } //aca termina el constructor
    
    

    public int getCodigoUnico() {
        return codigoUnico;
    }

    //getters:
    public Ubicacion getUbicacionDestino() {
        return ubicacionDestino;
    }

    public String getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public Date getFecha() {
        return fecha;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Ubicacion getUbicacionOrigen() {
        return ubicacionOrigen;
    }

    public TipoOrden getTipo() {
        return tipo;
    }

  
    
    
    //metodos para registrar cada tipo de orden: 
    public void OrdenIngreso()
    {
        this.ubicacionOrigen.agregarStock(producto, cantidad); 
    }
    
    public void OrdenEgreso()
    {
        this.ubicacionOrigen.quitarStock(producto, cantidad);        
    }
        
    public void OrdenInterno(Ubicacion ubicacionDestino)
    {   
        this.ubicacionOrigen.quitarStock(this.producto, this.cantidad);
        ubicacionDestino.agregarStock(this.producto, this.cantidad);
    }
    
}
