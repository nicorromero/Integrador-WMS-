/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

// 1. Importaciones obligatorias para JPA
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
/**
 *
 * @author nicor
 */
@Entity
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoUnico;
    private String usuarioResponsable;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @ManyToOne // Muchas ordenes pueden ser de UN producto
    private Producto producto;
    private int cantidad;
    @ManyToOne // Muchas ordenes pueden salir de UNA ubicación
    private Ubicacion ubicacionOrigen;
    @Enumerated(EnumType.STRING) // Guardamos el texto "INGRESO", "EGRESO"..
    private TipoOrden tipo;
    @ManyToOne //Muchas ordenes pueden salir de UNA ubicación
    private Ubicacion ubicacionDestino= null;

    //contructor:
    public Orden() {}  
    
    public Orden(String usuarioResponsable, Producto producto, int cantidad, Ubicacion ubicacionOrigen, TipoOrden tipo, Ubicacion ubicacionDestino){
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
    
    // --- Getters y Setters necesarios para JPA ---
    
    
    
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
