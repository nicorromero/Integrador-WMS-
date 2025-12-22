# Integrador WMS (Warehouse Management System) 📦

Este proyecto es un sistema de gestión de almacenes desarrollado en **Java**. Permite controlar el flujo de inventario mediante la creación de productos, ubicaciones estratégicas en naves y la gestión de órdenes de ingreso, egreso y movimientos internos.

## 🚀 Funcionalidades Principales

* **Gestión de Inventario:** Creación y seguimiento de productos con atributos de peso y unidad de medida.
* **Control de Ubicaciones:** Organización por Naves, Zonas (Recepción, Almacenamiento, Salida), Estanterías y Niveles.
* **Sistema de Órdenes:** * **Ingreso:** Aumenta el stock en una ubicación específica.
    * **Egreso:** Reduce el stock validando existencias previas.
    * **Interno (Transferencia):** Mueve productos entre ubicaciones del almacén.
* **Validación de Capacidad:** El sistema controla automáticamente que no se exceda el peso máximo de 1250 kg por ubicación.
* **Interfaz Gráfica (IGU):** Panel amigable desarrollado en Swing para la operación del sistema en tiempo real.

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java 23.
* **GUI Framework:** Swing (Java Foundation Classes).
* **Gestor de Dependencias:** Maven.
* **Persistencia (Preparado):** JPA / Hibernate con MySQL.

## 📋 Estructura del Proyecto

El código se organiza bajo el paquete `Logica` para el motor del sistema y `IGU` para la visualización:

* `SistemaWMS.java`: El controlador central que gestiona las listas de productos, ubicaciones y órdenes.
* `Ubicacion.java`: Gestiona la capacidad de carga y el mapa de stock (`HashMap`) de cada punto del almacén.
* `Orden.java`: Lógica de validación para los movimientos de mercancía.

## 🚀 Instalación y Ejecución

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/tu-usuario/integrador-wms.git](https://github.com/tu-usuario/integrador-wms.git)
    ```
2.  **Abrir en tu IDE preferido:** (Recomendado NetBeans o IntelliJ IDEA).
3.  **Ejecutar la clase principal:**
    Localiza `Logica.IntegradorFinal` y ejecuta el método `main` para iniciar la interfaz.

## 📸 Vista Previa
El sistema cuenta con un **Menú Principal** que permite navegar entre las funciones de Stock, Generación de Órdenes y configuración de nuevas entidades en el almacén.

---
Desarrollado por Nico Romero como proyecto integrador de gestión logística.
