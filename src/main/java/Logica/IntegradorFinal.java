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
        
        /*
        //intanciar ubicaciones una vez
        Ubicacion ubi1 = new Ubicacion("Nave1", TipoZona.ALMACENAMIENTO, "Estantería 1", "Nivel 1");
        Ubicacion ubi2 = new Ubicacion("Nave1", TipoZona.ALMACENAMIENTO, "Estantería 1", "Nivel 2"); 
        
        //instanciar objetos una vez
        Producto producto1 = new Producto("tornillos", "caja  mediana " , " kg " , 0.25);
        Producto producto2 = new Producto("Tuercas", "Caja de tuercas", "kg", 0.30);
        
        //el parametro ubicaciondestino debe ser null, solo se usa para orden interno 
        
        //error porque no existe de donde sacar los 50 tornillos
        Orden orden1 = new Orden (1, "nicor", producto1, 50, ubi1, TipoOrden.INGRESO, null );  
        
        ubi1.mostrarStockUbicacion();
        */
        
        SistemaWMS controlador;
        controlador = new SistemaWMS();
        
        
        PantallaInicio Pantalla = new PantallaInicio(controlador); 
        Pantalla.setVisible(true);   
       
        
        
    }
}
