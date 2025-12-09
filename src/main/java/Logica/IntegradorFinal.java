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

        
        
        // Crea la ventana, pasándole la instancia del SistemaWMS con los datos cargados.
        PantallaInicio pantalla = new PantallaInicio(sistema);
        pantalla.setVisible(true);
        pantalla.setLocationRelativeTo(null); // Centra la ventana
    }
}
