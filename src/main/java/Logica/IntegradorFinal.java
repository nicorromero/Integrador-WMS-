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
        
        // 1. Inicializar el Sistema WMS (El controlador de la logica)
        SistemaWMS sistema = new SistemaWMS();
        System.out.println("Sistema WMS Inicializado.");
        System.out.println("----------------------------------------");

        
        //inicializo la interfaz para el modo cliente
        PantallaInicio pantalla = new PantallaInicio(sistema);
        pantalla.pack(); // Asegura que la ventana tenga el tamaño correcto antes de moverse
        pantalla.setLocationRelativeTo(null); // PRIMERO la centras (mientras es invisible)
        pantalla.setVisible(true);            // LUEGO la muestras (ya centrada)
    }
}
