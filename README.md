# Scalable WMS Core - Sistema de Gestión de Almacenes

[![Java Version](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.8.1-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

Este proyecto es un núcleo de **Warehouse Management System (WMS)** diseñado para optimizar el flujo de inventario, la gestión de ubicaciones jerárquicas y el procesamiento de órdenes de entrada y salida.

## 🚀 Características Principales
* **Jerarquía de Almacenamiento:** Gestión de ubicaciones por zonas (Picking, Recepción, etc.) con validación de capacidad de carga.
* **Motor de Órdenes:** Procesamiento inteligente de órdenes de `ENTRADA` y `SALIDA`.
* **Persistencia Robusta:** Implementación de JPA para la gestión de datos relacionales.
* **Interfaz Administrativa:** GUI desarrollada en Swing para control operativo en tiempo real.

## 🏗️ Arquitectura del Sistema
El sistema sigue una arquitectura orientada a objetos con separación de responsabilidades:
* **Logica:** Contiene el "cerebro" (`SistemaWMS.java`) y las entidades de negocio.
* **IGU:** Interfaz gráfica de usuario para la interacción humana.
* **Persistencia:** Capa de datos configurada mediante `persistence.xml`.



## 🛠️ Desafíos Resueltos
* **Validación de Peso:** Se implementó una lógica en la clase `Ubicacion` para evitar el sobrealmacenamiento, asegurando que `pesoActual + pesoProducto <= capacidadMaxima`.
* **Trazabilidad:** Sistema de estados para órdenes que permite seguir el flujo logístico desde la creación hasta el cierre.

## 🗺️ Roadmap de Evolución
- [ ] **Migración a Spring Boot:** Transformar el core en una API REST escalable.
- [ ] **Dockerización:** Contenedores para despliegue rápido.
- [ ] **Dashboard de Analíticas:** Gráficos en tiempo real de ocupación del almacén.
