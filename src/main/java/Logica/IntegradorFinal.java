/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Logica;

import IGU.PantallaInicio;


/**
 *
 * @author nicor
 */
public class IntegradorFinal {

    public static void main(String[] args) {
        
        // 1. Inicializar el Sistema WMS (El controlador central)
        SistemaWMS sistema = new SistemaWMS();
        System.out.println("Sistema WMS Inicializado.");
        System.out.println("----------------------------------------");

        /*
        // ==========================================================
        // 2. CREACIÓN DE 5 PRODUCTOS
        // ==========================================================
        System.out.println("Creando Productos de Prueba...");
        
        // Los IDs (códigos únicos) se asignarán automáticamente por SistemaWMS
        Producto producto1 = sistema.crearNuevoProducto("Tornillo M8", "Caja", 0.5); // 0.5 kg
        Producto producto2 = sistema.crearNuevoProducto("Tubo PVC 40mm", "Unidad", 1.2); // 1.2 kg
        Producto producto3 = sistema.crearNuevoProducto("Caja de Cartón", "Unidad", 0.3); // 0.3 kg
        Producto producto4 = sistema.crearNuevoProducto("Pallet de Madera", "Unidad", 20.0); // 20 kg
        Producto producto5 = sistema.crearNuevoProducto("Motor Eléctrico", "Unidad", 50.0); // 50 kg
        
        System.out.println("Productos creados: " + sistema.getProductos().size());
        System.out.println("----------------------------------------");


        // ==========================================================
        // 3. CREACIÓN DE 5 UBICACIONES (ZONAS)
        // ==========================================================
        System.out.println("Creando Ubicaciones de Prueba...");
        
        // Zona de Recepción (para órdenes de Ingreso)
        Ubicacion ubiRecepcion = sistema.crearNuevaUbicacion("Nave A", "RECEPCION", "R01", "Nivel 1"); 
        
        // Zonas de Almacenamiento (para stock)
        Ubicacion ubiAlmacenA = sistema.crearNuevaUbicacion("Nave A", "ALMACENAMIENTO", "A01", "Nivel 3");
        Ubicacion ubiAlmacenB = sistema.crearNuevaUbicacion("Nave B", "ALMACENAMIENTO", "B05", "Nivel 2");
        Ubicacion ubiAlmacenC = sistema.crearNuevaUbicacion("Nave C", "ALMACENAMIENTO", "C10", "Nivel 1");
        
        // Zona de Salida (para órdenes de Egreso)
        Ubicacion ubiSalida = sistema.crearNuevaUbicacion("Nave C", "SALIDA", "S01", "Nivel 1");
        
        System.out.println("Ubicaciones creadas: " + sistema.getUbicaciones().size());
        System.out.println("----------------------------------------");


        // ==========================================================
        // 4. PREPARACIÓN DE STOCK INICIAL (Para poder probar el Egreso)
        // ==========================================================
        System.out.println("Cargando Stock Inicial a Almacenes...");
        
        // Agregamos stock directamente a las ubicaciones de almacenamiento
        ubiAlmacenA.agregarStock(producto1, 500); // 500 Tornillos en Almacen A
        ubiAlmacenA.agregarStock(producto2, 100); // 100 Tubos en Almacen A
        
        ubiAlmacenB.agregarStock(producto4, 5);   // 5 Pallets en Almacen B
        ubiAlmacenB.agregarStock(producto5, 2);   // 2 Motores en Almacen B
        
        System.out.println("Stock cargado. Peso actual Almacen A: " + ubiAlmacenA.getPesoActualKg() + " kg");
        System.out.println("Peso actual Almacen B: " + ubiAlmacenB.getPesoActualKg() + " kg");
        System.out.println("----------------------------------------");

        
        // ==========================================================
        // 5. CREACIÓN DE 5 ÓRDENES DE PRUEBA
        // ==========================================================
        System.out.println("Creando Órdenes de Prueba...");
        
        // ORDEN 1: INGRESO (De Recepción a Almacenamiento A)
        // El sistema WMS debe mover el stock de Origen (Recepción) a Destino (Almacenamiento)
       
        // ORDEN 2: EGRESO (De Almacenamiento A a Salida)
        // El sistema WMS debe mover el stock de Origen (Almacenamiento A) a Destino (Salida)
        Orden orden2 = sistema.crearNuevaOrden("Tornillo M8", 50 , "USER_ANA",  3, "egreso", 0);

        System.out.println("Órdenes creadas: " + sistema.getOrdenes().size());
        System.out.println("----------------------------------------");
        */

        // ==========================================================
        // 6. INICIAR LA INTERFAZ GRÁFICA (IGU)
        // ==========================================================
        
        // Crea la ventana, pasándole la instancia del SistemaWMS con los datos cargados.
        PantallaInicio pantalla = new PantallaInicio(sistema);
        pantalla.setVisible(true);
        pantalla.setLocationRelativeTo(null); // Centra la ventana
    }
}
