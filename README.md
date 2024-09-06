# Anime Event Management Software
## Author: Hernan Mendez & Robinson Mosquera

## Link document
https://docs.google.com/document/d/1URPCrXIZNqrfRw5qL7ErD015ODR2ljs6/edit?usp=sharing&ouid=114928836479325487781&rtpof=true&sd=true

## Descripción

Este software está diseñado para gestionar múltiples eventos de anime, permitiendo la administración de eventos, taquillas, actividades (como concursos de cosplay y trivia), y comercios (tiendas y restaurantes). Desarrollado utilizando **Java JDK 17.0.7** y **MySQL 8.0.3**, este sistema integra una arquitectura de modelo vista controlador (MVC) para ofrecer una solución robusta y escalable.

## Características

### 1. Creación y Gestión de Eventos
- **Gestión de eventos múltiples**: Cada evento incluye información detallada como nombre, ubicación, aforo máximo, fechas, organizadores, clasificación por edad y estado.
- **Gestión de personal**: Registro de empleados asignados a roles específicos (seguridad, limpieza, encargado de tienda/restaurante, etc.) y gestión de su disponibilidad y tareas.
- **Inventario de utilería**: Registro y gestión del inventario de elementos como luces, sillas, decoraciones, etc.

### 2. Taquillas
- **Creación de taquillas**: Cada evento tiene una taquilla única, con información sobre ubicación, contacto y personal a cargo.
- **Gestión de boletos**: Creación de diferentes tipos de boletos con su respectiva clasificación, precios y estado (pagado/reservado).
- **Gestión de visitantes**: Registro detallado de los visitantes con sus datos personales y participación en actividades.
- **Informes de boletería**: Generación de reportes sobre boletos vendidos, montos recaudados y estadísticas de participación.

### 3. Actividades
- **Concursos de Cosplay y Trivia**: Gestión de actividades con categorías, participantes, premios, y un sistema de puntuación y calificación por jurados.
- **Concursos de Trivia**: Sistema de preguntas aleatorias organizadas en rondas eliminatorias.
- **Inventario de premios**: Administración de premios asignados a actividades, su disponibilidad y entrega.

### 4. Comercios
- **Tiendas**: Gestión de inventarios, productos, descuentos y promociones.
- **Restaurantes**: Gestión de menús, inventarios de ingredientes y tiempos de preparación.

### 5. Interfaces
- **Arquitectura MVC**: Implementación de una interfaz gráfica que permita la interacción con todos los módulos del sistema, asegurando un flujo homogéneo entre las diferentes funcionalidades del software.

## Tecnologías Utilizadas

- **Lenguaje de Programación**: Java (JDK 17.0.7)
- **Base de Datos**: MySQL (8.0.3)
- **Patrón de Arquitectura**: Modelo Vista Controlador (MVC)
- **Conexión a Base de Datos**: JDBC

## Requerimientos No Funcionales

- **Buenas Prácticas de Desarrollo**: Aplicación de principios SOLID y patrones de diseño.
- **Programación Orientada a Objetos**: Uso correcto de clases, relaciones y abstracciones.
- **Optimización de Operaciones**: Uso de expresiones lambda y el API Stream en Java.
- **Manejo de Errores**: Control adecuado de excepciones y generación de logs.
