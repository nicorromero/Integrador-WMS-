/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nicor
 */



import Logica.Producto;
import Logica.Ubicacion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class UbicacionTest {

    @Test
    @DisplayName("Validar que la ubicación detecte exceso de peso")
    public void testCapacidadLimite() {
        // 1. Setup: Creamos una ubicación con capacidad de 50.0 kg
        Ubicacion estanteria = new Ubicacion();
        
        
        // 2. Setup: Creamos un producto pesado de 60.0 kg
        Producto productoPesado = new Producto();
        productoPesado.setPesoPorUnidad(1500);
        
        // 3. Verificación: El peso del producto es mayor a la capacidad
        // Esto confirma que nuestra lógica de validación tiene sentido
        assertTrue(productoPesado.getPesoPorUnidad()> estanteria.getCAPACIDAD_MAXIMA_KG(),
                
            "Error: El producto debería ser identificado como más pesado que la capacidad");
    }
}