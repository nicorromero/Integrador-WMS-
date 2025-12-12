/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nicor
 */
public class Ubicacion {
    private static int contadorIds = 1;
    private int codigoUnico;
    private String nave;
    private TipoZona zona;
    private String estanteria;
    private String nivel;
    private double pesoActualKg;
    private Map <Producto, Integer> stockPorProducto;// Usamos un Mapa para relacionar cada Producto con su cantidad (stock) en esta ubicación.
    private static final double CAPACIDAD_MAXIMA_KG = 1250.0; // Constante para la capacidad. 'final' significa que no puede cambiar.
   
   
    //contructor:
    public Ubicacion( String nave, TipoZona zona, String estanteria, String nivel) {
        this.codigoUnico = contadorIds++;
        this.nave = nave;
        this.zona = zona;
        this.estanteria = estanteria;
        this.nivel = nivel;
        this.pesoActualKg = 0.0;
        this.stockPorProducto = new HashMap<>();
    }

    
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
        else 
        {// Se usa getOrDefault para sumar la nueva cantidad al stock existente. Si el producto no existía, empieza en 0.
           //lo quiero cambiar, si no existe el producto tambien hay que agregar la ubicacion a ubicaciones en ese producto 
                
                //int stockActual= stockPorProducto.getOrDefault(producto, 0) + cantidad;
                //stockPorProducto.put(producto, stockActual);
                
                // Opción más limpia y recomendada si el stock es un Map<Producto, Integer>
                stockPorProducto.merge(producto, cantidad, Integer::sum);
                
                this.pesoActualKg += pesoAAgregar;
            
      
        } 
    }
    
    public void quitarStock(Producto producto, int cantidad) {
        //                      1                   "or"                    2                       1-verifica que exista el producto en la ubicacion
        if (!stockPorProducto.containsKey(producto) || stockPorProducto.get(producto) < cantidad) //2-verifica que alla la suficente cantidad de unidades del producto
        {
            throw new IllegalArgumentException("Error: Stock insuficiente del producto " + producto.getDescripcion() + " en la ubicación.");
        }
        else
        {
            double pesoAQuitar = producto.getPesoPorUnidad() * cantidad;
            stockPorProducto.put(producto, stockPorProducto.get(producto) - cantidad); //actualiza el value, la cantidad,  del hashmap
          
            this.pesoActualKg -= pesoAQuitar;
            System.out.println("INFO: Stock retirado de " + this.getIdentificador() + ". Peso actual: " + this.pesoActualKg + " kg.");
        
        }
    }     
    
    //metodo para contar el stock de un producto en una ubicacio
    //este cpz no se usa
     public int StockporProducto(Producto producto) {
        
        int stock = stockPorProducto.getOrDefault(producto, 0);
        
        return stock;
    }
     
    //metodo para ver los productos y cantidades de una ubicacion
     //por consola sirve parta testear
     public void mostrarStockUbicacion() {
        if (!stockPorProducto.isEmpty()) {
            for (Map.Entry<Producto, Integer> entry : stockPorProducto.entrySet()) {
                System.out.println("   - Producto: " + entry.getKey().getDescripcion() + " | Cantidad: " + entry.getValue());
            }
        } else {
            System.out.println("Ubicación vacía ");
            
        }
    }
     
     // En Logica/Ubicacion.java

    // Cambiamos void por String para que devuelva el texto
    public String obtenerDetalleStock() {
    // Si el mapa está vacío, devolvemos un mensaje simple
    if (stockPorProducto.isEmpty()) {
        return "La ubicación está vacía.";
    }
    
    // StringBuilder es eficiente para unir muchas líneas de texto
    StringBuilder reporte = new StringBuilder();
    
    reporte.append("Stock en Ubicación (").append(this.getIdentificador()).append("):\n");
    
    for (Map.Entry<Producto, Integer> entry : stockPorProducto.entrySet()) {
        reporte.append(" - ")
               .append(entry.getKey().getDescripcion())
               .append(": ")
               .append(entry.getValue())
               .append(" unidades\n"); // \n hace un salto de línea
    }
    
    reporte.append("Peso actual: ").append(this.pesoActualKg).append(" Kg");
    
    return reporte.toString(); // Devolvemos todo el texto junto
    }
}
