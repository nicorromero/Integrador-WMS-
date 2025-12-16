/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Logica;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class SistemaWMS {
    
    // ESTA es la conexión real a la base de datos
    private EntityManagerFactory emf;
    private EntityManager em;

    public SistemaWMS() {
        // Al iniciar, conectamos con la base de datos "WMSPU" (definida en persistencia.xml)
        this.emf = Persistence.createEntityManagerFactory("WMSPU");
        this.em = emf.createEntityManager();
    }
    
    // --- MÉTODOS DE BÚSQUEDA (CONSULTAS A BD) ---

    public Producto buscarProductoPorDescripcion(String txtProducto){
        try {
            // Escribimos JPQL (SQL de Java): "Selecciona p de la clase Producto donde..."
            TypedQuery<Producto> query = em.createQuery(
                "SELECT p FROM Producto p WHERE p.descripcion = :desc", Producto.class);
            query.setParameter("desc", txtProducto);
            return query.getSingleResult(); // Retorna el producto encontrado
        } catch (Exception e) {
            return null; // Si no encuentra nada, retorna null
        }
    }
    
    public Ubicacion buscarUbicacionPorCodigo(int codigo){
        // find() es el método más rápido para buscar por ID (Clave primaria)
        return em.find(Ubicacion.class, codigo);
    }
    
    // Los Enum no están en BD como tabla, así que los buscamos igual que antes (en memoria)
    public TipoOrden buscarTipoOrden (String descripcionOrden){
        for (TipoOrden orden : TipoOrden.values()) {
            if(orden.getDescripcion().equalsIgnoreCase(descripcionOrden)) return orden;
        }
        return null;
    }
        
    public TipoZona buscarTipoZona (String descripcionZona){
        for (TipoZona zona : TipoZona.values()) {
            if(zona.getDescripcion().equalsIgnoreCase(descripcionZona) || zona.name().equalsIgnoreCase(descripcionZona)) return zona;
        }   
        return null;   
    }
  
    // --- MÉTODOS DE CREACIÓN (GUARDAR EN BD) ---
  
    public Producto crearNuevoProducto(String descripcion, String unidadMedida, double pesoPorUnidad) {
        // 1. Crear el objeto (todavía no está en BD)
        Producto nuevoProducto = new Producto(descripcion, unidadMedida, pesoPorUnidad);
        
        // 2. Iniciar transacción (como "abrir modo edición")
        em.getTransaction().begin();
        
        // 3. Guardar
        em.persist(nuevoProducto);
        
        // 4. Confirmar cambios (Guardar definitivamente)
        em.getTransaction().commit();
        
        return nuevoProducto;    
    }
        
    public Ubicacion crearNuevaUbicacion(String nave, String zonaTexto, String estanteria, String nivel) {
        TipoZona zona = buscarTipoZona(zonaTexto);
        if(zona == null) throw new IllegalArgumentException("La zona no existe");

        Ubicacion nuevaUbicacion = new Ubicacion(nave, zona, estanteria, nivel);
        
        em.getTransaction().begin();
        em.persist(nuevaUbicacion);
        em.getTransaction().commit();
        
        return nuevaUbicacion;
    }
    
    public Orden crearNuevaOrden(String descProducto, int cantidad, String usuario, int idOrigen, String tipoOrdenTxt, int idDestino) {
        // Buscamos en la BD
        Producto prod = buscarProductoPorDescripcion(descProducto);
        if (prod == null) throw new IllegalArgumentException("Producto no encontrado");
        
        Ubicacion uOrigen = buscarUbicacionPorCodigo(idOrigen);
        if (uOrigen == null) throw new IllegalArgumentException("Ubicación origen no encontrada");
        
        TipoOrden tipo = buscarTipoOrden(tipoOrdenTxt);
        if (tipo == null) throw new IllegalArgumentException("Tipo orden no válido");
        
        Ubicacion uDestino = null;
        if (tipo == TipoOrden.INTERNO) {
            uDestino = buscarUbicacionPorCodigo(idDestino);
            if (uDestino == null) throw new IllegalArgumentException("Ubicación destino requerida");
        }

        // Lógica de Negocio (validaciones dentro del constructor de Orden)
        Orden nuevaOrden = new Orden(usuario, prod, cantidad, uOrigen, tipo, uDestino);
        
        // Guardamos la orden
        em.getTransaction().begin();
        
        // IMPORTANTE: También hay que actualizar el stock dentro de las ubicaciones
        // (JPA guardará los cambios en 'uOrigen' automáticamente al hacer commit porque lo modificamos)
        // Pero primero asegurémonos de llamar a tu lógica de movimiento:
        switch (tipo) {
            case INGRESO -> uOrigen.agregarStock(prod, cantidad);
            case EGRESO -> uOrigen.quitarStock(prod, cantidad);
            case INTERNO -> {
                uOrigen.quitarStock(prod, cantidad);
                uDestino.agregarStock(prod, cantidad);
            }
        }
        
        em.persist(nuevaOrden); // Guardamos la orden
        // No hace falta hacer 'em.persist(uOrigen)' porque ya está gestionada, JPA detecta el cambio de stock solo.
        
        em.getTransaction().commit();
        return nuevaOrden;
    }
    
    // --- REPORTES ---
    
    public String consultarUbicacionStock(int codigoUbicacion){
        Ubicacion u = buscarUbicacionPorCodigo(codigoUbicacion);
        if (u == null) return "Ubicación no encontrada";
        return u.obtenerDetalleStock();
    }
    
    public String consultarProductoStock(String descProducto){
        // Esto podría optimizarse con SQL, pero por ahora reutilizamos tu lógica
        Producto p = buscarProductoPorDescripcion(descProducto);
        if (p == null) return "Producto no existe";

        // Traemos todas las ubicaciones de la BD para sumar
        TypedQuery<Ubicacion> query = em.createQuery("SELECT u FROM Ubicacion u", Ubicacion.class);
        List<Ubicacion> todas = query.getResultList();
        
        int total = 0;
        for(Ubicacion u : todas){
            // Usamos tu método existente que busca en el Mapa
            // Nota: Al ser un Mapa, Java ya lo trajo de la BD automáticamente
            total += u.getStockPorProducto().getOrDefault(p, 0);
        }
        return "Stock total de " + p.getDescripcion() + ": " + total;
    }

    // Método para cerrar conexión al salir (opcional pero recomendado)
    public void cerrarSistema() {
        if (em.isOpen()) em.close();
        if (emf.isOpen()) emf.close();
    }
}
