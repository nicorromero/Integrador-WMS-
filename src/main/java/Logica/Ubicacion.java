/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyJoinColumn;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nicor
 */
@Entity
public class Ubicacion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigoUnico;
    private String nave;
    @Enumerated(EnumType.STRING)
    private TipoZona zona;
    private String estanteria;
    private String nivel;
    private double pesoActualKg;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "stock_ubicacion", joinColumns = @JoinColumn(name = "ubicacion_id"))
    @MapKeyJoinColumn(name = "producto_id")
    @Column(name = "cantidad")
    private Map <Producto, Integer> stockPorProducto;// Usamos un Mapa para relacionar cada Producto con su cantidad (stock) en esta ubicación.
    private static final double CAPACIDAD_MAXIMA_KG = 1250.0; // Constante para la capacidad. 'final' significa que no puede cambiar.
   
   
    //contructor:
    public Ubicacion( String nave, TipoZona zona, String estanteria, String nivel) {
        this.nave = nave;
        this.zona = zona;
        this.estanteria = estanteria;
        this.nivel = nivel;
        this.pesoActualKg = 0.0;
        this.stockPorProducto = new HashMap<>();
    }
    
    public Ubicacion() {}

    
    //Getters:
    
    public int getCodigoUnico() {
        return codigoUnico;
    }

    public String getNave() {
        return nave;
    }

    public TipoZona getZona() {
        return zona;
    }

    public String getEstanteria() {
        return estanteria;
    }

    public String getNivel() {
        return nivel;
    }

    public double getPesoActualKg() {
        return pesoActualKg;
    }

    public Map<Producto, Integer> getStockPorProducto() {
        return stockPorProducto;
    }

    public static double getCAPACIDAD_MAXIMA_KG() {
        return CAPACIDAD_MAXIMA_KG;
    }
    
    public String getIdentificador() {
        return nave + "/" + zona.getDescripcion() + "/" + estanteria + "/" + nivel;
    }
    
    
    //metodos:
    
    public void agregarStock(Producto producto, int cantidad) {
        
        double pesoAAgregar = producto.getPesoPorUnidad() * cantidad;

        if (pesoActualKg + pesoAAgregar > CAPACIDAD_MAXIMA_KG) {
            throw new IllegalArgumentException("Error: Capacidad máxima de la ubicación excedida.");
        }      
        
        stockPorProducto.merge(producto, cantidad, Integer::sum);
        
        this.pesoActualKg += pesoAAgregar;  
    }
    
    public void quitarStock(Producto producto, int cantidad) {
        //                      1                  "or"                    2                       1-verifica que exista el producto en la ubicacion
        if (!stockPorProducto.containsKey(producto) || stockPorProducto.get(producto) < cantidad) //2-verifica que alla la suficente cantidad de unidades del producto
        {
            throw new IllegalArgumentException("Error: Stock insuficiente del producto " + producto.getDescripcion() + " en la ubicación.");
        }
        
        stockPorProducto.put(producto, stockPorProducto.get(producto) - cantidad); //actualiza el value, la cantidad,  del hashmap
       
        double pesoAQuitar = producto.getPesoPorUnidad() * cantidad;
        this.pesoActualKg -= pesoAQuitar;  
    }     
    
    //stock total de un ubicacion solo de un producto 
     public int StockporProducto(Producto producto) {
        
        int stock = stockPorProducto.getOrDefault(producto, 0);
        
        return stock;
    }
    
     
    public String obtenerDetalleStock() {
        
        if (stockPorProducto.isEmpty()) {
            return "La ubicación está vacía.";
        }
    
        // StringBuilder es una clase como String Sirve para mandar textos largos y poder usar el for
        StringBuilder reporte = new StringBuilder();
    
        reporte.append("Stock en Ubicación (").append(this.getIdentificador()).append("):\n");
    
            for (Map.Entry<Producto, Integer> entry : stockPorProducto.entrySet()) {
                reporte.append(" - ")
                    .append(entry.getKey().getDescripcion())  //nombre producto
                    .append(": ") 
                    .append(entry.getValue()) //cantidad
                    .append(" unidades\n"); // \n hace un salto de línea
            }
    
        reporte.append("Peso actual: ").append(this.pesoActualKg).append(" Kg");
    
        return reporte.toString(); //reporte es la instancia de StringBuilder  
    }
    
    
}
