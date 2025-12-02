/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Logica;

/**
 *
 * @author nicor
 */
public enum TipoZona {
    RECEPCION("zona de recepción"),
    ALMACENAMIENTO("zona de almacenamiento"),
    SALIDA("zona de salida");

    // Atributo para guardar una descripción más amigable
    private final String descripcion;

    // Constructor del enum
    TipoZona(String descripcion) {
        this.descripcion = descripcion;
    }

    // Método para obtener la descripción
    public String getDescripcion() {
        return descripcion;
    }
}
