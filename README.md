# 🚚 CityDrop - Sistema de Gestión de Envíos Urbanos

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-17.0.6-blue?style=for-the-badge&logo=java)
![Maven](https://img.shields.io/badge/Maven-3.8+-red?style=for-the-badge&logo=apache-maven)
![Lombok](https://img.shields.io/badge/Lombok-1.18.34-green?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

**Sistema completo de gestión de envíos urbanos "CityDrop con 10 patrones de diseño implementados**

[Características](#-características-principales) • [Instalación](#-instalación-y-ejecución) • [Uso](#-uso-del-sistema) • [Patrones](#-patrones-de-diseño-implementados) • [Arquitectura](#-arquitectura-del-sistema)

</div>

---

## 📋 Descripción General

**CityDrop** es un sistema integral de gestión de envíos urbanos desarrollado en **JavaFX** que implementa una arquitectura robusta basada en **10 patrones de diseño GoF** (Gang of Four). El sistema permite a tres tipos de usuarios (Clientes, Repartidores y Administradores) gestionar todo el ciclo de vida de los envíos de forma eficiente y escalable.

### 🎯 Objetivos del Proyecto

- ✅ Demostrar la aplicación práctica de **10 patrones de diseño** en un sistema real
- ✅ Implementar una **arquitectura limpia y escalable** con separación de responsabilidades
- ✅ Crear una **interfaz gráfica intuitiva** con JavaFX
- ✅ Gestionar el **ciclo completo de envíos** desde solicitud hasta entrega
- ✅ Proporcionar herramientas administrativas completas para control operacional

### 🌟 Ventajas del Sistema

- **Modular**: Cada patrón encapsula responsabilidades específicas
- **Extensible**: Fácil agregar nuevas funcionalidades sin modificar código existente
- **Mantenible**: Código limpio con bajo acoplamiento
- **Testeable**: Inyección de dependencias y separación de lógica de negocio
- **Escalable**: Arquitectura preparada para crecer

---

## 🎯 Patrones de Diseño Implementados

### 🏗️ Patrones Creacionales (3)

#### 1. **Singleton** 
- **Clase**: `SistemaGestion`
- **Propósito**: Garantizar una única instancia del sistema de gestión central
- **Uso**: Control centralizado de usuarios, envíos, repartidores y datos del sistema

#### 2. **Factory Method**
- **Clases**: `EntidadFactory`, `EnvioFactory`
- **Propósito**: Creación de objetos complejos sin exponer la lógica de construcción
- **Uso**: Crear envíos de diferentes tipos y entidades del sistema

#### 3. **Builder**
- **Clases**: `Direccion`, `Envio`, `Pago`, `Tarifa`, `Incidencia`, `Repartidor`
- **Propósito**: Construcción flexible de objetos complejos con múltiples parámetros
- **Uso**: Facilitar la creación de objetos con muchos atributos opcionales

### 🔌 Patrones Estructurales (3)

#### 4. **Decorator**
- **Clases**: `ServicioEnvio`, `ServicioEnvioBase`, `ServicioEnvioDecorator`, `ServicioSeguro`
- **Propósito**: Agregar funcionalidades adicionales a envíos de forma dinámica
- **Uso**: Añadir seguro, prioridad o características especiales a envíos

#### 5. **Adapter**
- **Clases**: `EmailAdapter`, `SMSAdapter`, `MapasAdapter`
- **Propósito**: Integrar servicios externos con interfaces incompatibles
- **Uso**: Notificaciones por email/SMS y cálculo de distancias

#### 6. **Bridge**
- **Clases**: `Reporte`, `FormatoReporte`, `FormatoCSV`, `FormatoPDF`, `ReporteEnvios`, `ReporteUsuarios`
- **Propósito**: Separar abstracción (tipo de reporte) de implementación (formato)
- **Uso**: Generar diferentes tipos de reportes en múltiples formatos

### 🎬 Patrones Comportamentales (4)

#### 7. **Strategy**
- **Clases**: `EstrategiaPago`, `PagoTarjeta`, `PagoNequi`, `PagoPayPal`, `PagoEfectivo`
- **Propósito**: Definir familia de algoritmos intercambiables para procesamiento de pagos
- **Uso**: Procesar pagos con diferentes métodos de pago

#### 8. **Observer**
- **Clases**: `Observer`, `Subject`, `NotificadorUsuario`, `NotificadorRepartidor`, `AdminDashboardObserver`
- **Propósito**: Notificar automáticamente a observadores cuando cambia el estado
- **Uso**: Actualizar UI y enviar notificaciones cuando un envío cambia de estado

#### 9. **Command**
- **Clases**: `Command`, `GestorComandos`, `AsignarRepartidorCommand`, `CancelarEnvioCommand`, `ActualizarEstadoCommand`
- **Propósito**: Encapsular operaciones como objetos, permitiendo deshacer/rehacer
- **Uso**: Historial de operaciones reversibles

#### 10. **State**
- **Clases**: `EstadoEnvio`, `EstadoSolicitado`, `EstadoAsignado`, `EstadoEnRuta`, `EstadoEntregado`, `EstadoIncidencia`
- **Propósito**: Cambiar comportamiento del envío según su estado actual
- **Uso**: Gestionar transiciones válidas entre estados de envío

---

## 🚀 Características Principales

### 👤 Panel de Usuario (Cliente)

#### 🔐 Autenticación y Perfil
- ✅ Registro de nuevos usuarios con validación
- ✅ Login seguro con credenciales
- ✅ Gestión de perfil personal
- ✅ Actualización de información de contacto

#### 📦 Gestión de Envíos
- ✅ **Crear envíos** con 3 tipos disponibles:
  - 📦 **Estándar**: Entrega en 24-48 horas
  - ⚡ **Express**: Entrega el mismo día
  - 🔒 **Frágil**: Manejo especial para paquetes delicados
- ✅ **Historial completo** de todos los envíos
- ✅ **Filtros avanzados** por estado:
  - Solicitado
  - Asignado
  - En Ruta
  - Entregado
  - Con Incidencia
- ✅ **Rastreo en tiempo real** del estado del envío
- ✅ **Detalles completos** de cada envío (origen, destino, costo, repartidor)

#### 📍 Gestión de Direcciones
- ✅ Agregar direcciones frecuentes
- ✅ Editar direcciones existentes
- ✅ Eliminar direcciones no usadas
- ✅ Seleccionar desde mapa interactivo

#### 💳 Gestión de Pagos
- ✅ **4 métodos de pago disponibles**:
  - 💳 Tarjeta de crédito/débito
  - 📱 Nequi
  - 🅿️ PayPal
  - 💵 Efectivo contra entrega
- ✅ Guardar métodos de pago favoritos
- ✅ Historial de pagos realizados
- ✅ Comprobantes de pago

#### 📊 Estadísticas Personales
- ✅ Total de envíos realizados
- ✅ Envíos activos
- ✅ Envíos completados
- ✅ Gasto total en envíos

---

### 🚚 Panel de Repartidor

#### 🔑 Acceso y Disponibilidad
- ✅ Login con documento y contraseña
- ✅ **Control de estado**:
  - 🟢 **ACTIVO**: Disponible para asignaciones
  - 🔴 **INACTIVO**: No disponible
  - 🔵 **EN_RUTA**: Realizando una entrega
- ✅ Actualización de zona de cobertura

#### 📋 Gestión de Entregas
- ✅ Ver envíos asignados en tiempo real
- ✅ Detalles completos de cada entrega
- ✅ Información del destinatario (nombre, teléfono, dirección)
- ✅ Actualizar estado de entregas:
  - Iniciar entrega
  - Marcar como entregado
  - Reportar incidencias
- ✅ Historial de entregas realizadas

#### 📊 Estadísticas del Repartidor
- ✅ Total de envíos completados
- ✅ Envíos en curso
- ✅ Zona de cobertura asignada
- ✅ Estado actual

---

### 👨‍💼 Panel de Administrador

#### 📊 Dashboard de Métricas
- ✅ **Métricas en tiempo real**:
  - 👥 Total de usuarios registrados
  - 📦 Total de envíos en el sistema
  - 🔄 Envíos activos
  - 💰 Ingreso total generado
- ✅ Gráficos de estado de envíos
- ✅ Botón de actualización manual de datos

#### 👥 Gestión de Usuarios
- ✅ **Tabla completa** de usuarios con:
  - ID, nombre, email, teléfono
  - Número de envíos realizados
- ✅ **Filtro de búsqueda** por nombre o email
- ✅ **Ver envíos** de usuario específico
- ✅ **Eliminar usuarios** (con validación)
- ✅ **Exportar datos** de usuarios

#### 📦 Gestión de Envíos
- ✅ **Tabla completa** de envíos con:
  - ID, usuario, origen, destino, estado, costo
- ✅ **Filtros por estado** del envío
- ✅ **Ver detalles completos** de cada envío
- ✅ **Actualizar manualmente** el estado
- ✅ **Eliminar envíos** (con confirmación)
- ✅ **Asignar/Reasignar repartidores**

#### 🚚 Gestión de Repartidores
- ✅ **CRUD completo** de repartidores:
  - ➕ Agregar nuevo repartidor
  - ✏️ Editar información existente
  - 🗑️ Eliminar repartidor (validando envíos)
- ✅ **Formulario con campos**:
  - Nombre completo
  - Número de documento
  - Teléfono de contacto
  - Zona de cobertura
  - Estado (ACTIVO/INACTIVO/EN_RUTA)
- ✅ **Tabla con información**:
  - ID, nombre, documento, teléfono
  - Zona de cobertura
  - Estado actual (con colores)
  - Número de envíos asignados
- ✅ **Botón "Ver Envíos"**: Ventana emergente scrolleable con todos los envíos del repartidor
- ✅ **Asignación de envíos** a repartidores disponibles
- ✅ **Control de disponibilidad** por estado

#### 📈 Reportes y Exportación
- ✅ **Generación de reportes** en 2 formatos:
  - 📄 **CSV**: Para análisis en Excel/Google Sheets
  - 📑 **PDF**: Para impresión (Apache PDFBox)
- ✅ **Tipos de reportes**:
  - Reporte de envíos (completo o filtrado)
  - Reporte de usuarios
  - Reporte de repartidores
- ✅ **Estadísticas detalladas** del sistema
- ✅ **Exportación masiva** de todos los datos

#### 🔔 Notificaciones Masivas
- ✅ Enviar notificaciones a todos los usuarios
- ✅ Enviar notificaciones a todos los repartidores
- ✅ Notificaciones por cambio de estado de envíos

---

## 🔧 Tecnologías y Dependencias

### Principales

| Tecnología | Versión | Propósito |
|-----------|---------|-----------|
| **Java** | 17 | Lenguaje de programación principal |
| **JavaFX** | 17.0.6 | Framework para interfaz gráfica |
| **Maven** | 3.8+ | Gestión de dependencias y build |
| **Lombok** | 1.18.34 | Reducción de código boilerplate |
| **Apache PDFBox** | 2.0.29 | Generación de reportes PDF |
| **JUnit 5** | 5.10.2 | Testing unitario |

---

## 📦 Instalación y Ejecución

### ⚙️ Requisitos Previos

- ☕ **JDK 17** o superior ([Descargar](https://www.oracle.com/java/technologies/downloads/#java17))
- 🔨 **Maven 3.8** o superior ([Descargar](https://maven.apache.org/download.cgi))
- 💻 **Windows/Linux/Mac** compatible

### 🔍 Verificar Instalación

```bash
# Verificar Java
java -version
# Debe mostrar: java version "17.x.x"

# Verificar Maven
mvn -version
# Debe mostrar: Apache Maven 3.8.x o superior
```

### 📥 Clonar o Descargar el Proyecto

**Opción 1: Clonar con Git**
```bash
git clone https://github.com/AiiwA/Proyecto_Final_P2.git
cd Proyecto_Final_P2
```

**Opción 2: Descargar ZIP**
1. Descargar el archivo ZIP del repositorio
2. Extraer en una carpeta
3. Abrir terminal en la carpeta extraída

### ▶️ Ejecutar la Aplicación

#### En Windows:

```bash
# 1. Limpiar y compilar
.\mvnw.cmd clean compile

# 2. Ejecutar aplicación
.\mvnw.cmd javafx:run
```

#### En Linux/Mac:

```bash
# 1. Dar permisos de ejecución
chmod +x mvnw

# 2. Limpiar y compilar
./mvnw clean compile

# 3. Ejecutar aplicación
./mvnw javafx:run
```

#### Con Maven Instalado (Cualquier OS):

```bash
# 1. Limpiar y compilar
mvn clean compile

# 2. Ejecutar aplicación
mvn javafx:run
```

### 🐛 Solución de Problemas Comunes

#### Error: "JAVA_HOME no está definido"
```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-17

# Linux/Mac
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
```

---

## 🔐 Credenciales de Prueba

**Usuario Cliente:**
- Correo: `juan@email.com`
- Contraseña: `123456`

**Administrador:**
- Correo: `admin@sistema.com`
- Contraseña: `admin123`

### 📊 Datos Iniciales

Al iniciar la aplicación, el sistema carga automáticamente:
- ✅ **2 Usuarios** (Juan Pérez, María González)
- ✅ **1 Administrador** 
- ✅ **3 Repartidores** (Carlos Ramírez, Laura Martínez, Diego Silva)
- ✅ **3 Envíos de ejemplo** en diferentes estados
- ✅ **Direcciones de prueba** en Armenia y Calarcá

---

## 🏗️ Arquitectura del Sistema

### 📐 Arquitectura en Capas

```
┌─────────────────────────────────────────────────────┐
│          CAPA DE PRESENTACIÓN (UI)                  │
│  ┌───────────────────────────────────────────────┐  │
│  │  JavaFX FXML Views                            │  │
│  │  - login-view.fxml                            │  │
│  │  - admin-dashboard.fxml                       │  │
│  │  - user-dashboard.fxml                        │  │
│  └───────────────────────────────────────────────┘  │
│  ┌───────────────────────────────────────────────┐  │
│  │  View Controllers                             │  │
│  │  - LoginViewController                        │  │
│  │  - AdminDashboardViewController               │  │
│  │  - UserDashboardViewController                │  │
│  │  - NavigationController                       │  │
│  │  - SessionManager                             │  │
│  └───────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────┘
                         ↓↑
┌─────────────────────────────────────────────────────┐
│       CAPA DE LÓGICA DE NEGOCIO (Business)          │
│  ┌───────────────────────────────────────────────┐  │
│  │  Controllers (Business Logic)                 │  │
│  │  - UsuarioController                          │  │
│  │  - EnvioController                            │  │
│  │  - RepartidorController                       │  │
│  │  - AdminController                            │  │
│  │  - PagoController                             │  │
│  └───────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────┘
                         ↓↑
┌─────────────────────────────────────────────────────┐
│          CAPA DE PATRONES (Patterns)                │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐           │
│  │ Strategy │  │ Observer │  │  State   │           │
│  │  (Pago)  │  │(Notific.)│  │ (Envío)  │           │
│  └──────────┘  └──────────┘  └──────────┘           │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐           │
│  │Decorator │  │ Adapter  │  │  Bridge  │           │
│  │(Servicio)│  │(Externos)│  │(Reportes)│           │
│  └──────────┘  └──────────┘  └──────────┘           │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐           │
│  │ Factory  │  │ Command  │  │ Builder  │           │
│  │(Entidad) │  │(Historial)│ │(Objetos) │           │
│  └──────────┘  └──────────┘  └──────────┘           │
└─────────────────────────────────────────────────────┘
                         ↓↑
┌─────────────────────────────────────────────────────┐
│         CAPA DE DOMINIO (Domain/Model)              │
│  ┌───────────────────────────────────────────────┐  │
│  │  Entidades de Dominio                         │  │
│  │  - Usuario                                    │  │
│  │  - Envio                                      │  │
│  │  - Repartidor                                 │  │
│  │  - Administrador                              │  │
│  │  - Pago, Direccion, Tarifa, etc.              │  │
│  └───────────────────────────────────────────────┘  │
│  ┌───────────────────────────────────────────────┐  │
│  │  Sistema Central (Singleton)                  │  │
│  │  - SistemaGestion                             │  │
│  │    • Gestión de usuarios                      │  │
│  │    • Gestión de envíos                        │  │
│  │    • Gestión de repartidores                  │  │
│  │    • Datos de prueba iniciales                │  │
│  └───────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────┘
```

### 🔄 Flujo de Datos

1. **Usuario interactúa** con la UI (FXML + ViewController)
2. **ViewController** delega a **BusinessController**
3. **BusinessController** aplica **patrones de diseño**
4. **Patrones** manipulan **entidades de dominio**
5. **SistemaGestion** (Singleton) almacena datos
6. **Observer** notifica cambios a la UI

### 🎨 Principios SOLID Aplicados

- **S** - Single Responsibility: Cada clase tiene una responsabilidad única
- **O** - Open/Closed: Extensible mediante patrones (Strategy, Decorator)
- **L** - Liskov Substitution: Interfaces e implementaciones intercambiables
- **I** - Interface Segregation: Interfaces específicas y cohesivas
- **D** - Dependency Inversion: Depender de abstracciones, no concreciones

---

## 📚 Documentación Adicional

### 📝 Convenciones de Código

- **Nombres de clases**: PascalCase (`UsuarioController`)
- **Nombres de métodos**: camelCase (`crearEnvio`)
- **Constantes**: UPPER_SNAKE_CASE (`MAX_PESO_KG`)
- **Paquetes**: lowercase (`co.edu.uniquindio.poo`)
- **Anotaciones Lombok**: @Getter, @Setter, @Builder, @NonNull

### 🧪 Testing

El proyecto incluye **23 pruebas unitarias** con JUnit 5 en 3 clases principales:

- **AdminControllerTest** (8 tests): Registro de administradores, gestión de repartidores, métricas del sistema
- **UsuarioControllerTest** (7 tests): Registro de usuarios, búsqueda, gestión de direcciones
- **EnvioControllerTest** (8 tests): Creación de envíos (estándar/express), cálculo de costos, cambios de estado

```bash
# Ejecutar todos los tests
mvn test
```

---

## 📄 Licencia

Este proyecto está bajo la **Licencia MIT** - ver el archivo [LICENSE](LICENSE) para detalles.

```
MIT License

Copyright (c) 2025 Brandon Gil & Santiago Padilla

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

---

## 👨‍💻 Autores y Créditos

### Desarrolladores

| Nombre | GitHub | Rol |
|--------|--------|-----|
| **Brandon Gil** | [AiiwA](https://github.com/AiiwA) | Ing. en proceso |
| **Santiago Padilla** | [@padilla05x](https://github.com/padilla05x) | Ing. en proceso |

### 🙏 Agradecimientos

- **Universidad del Quindío** - Por la formación académica
- **Profesor de Programación 2** - Por la guía en patrones de diseño
- **Comunidad JavaFX** - Por la documentación y ejemplos
- **OpenJFX Project** - Por el framework JavaFX
- **Proyecto Lombok** - Por simplificar el código Java

---

## 📞 Contacto y Soporte

### 💬 ¿Necesitas Ayuda?

- 📧 **Email**: santiago.padillar@uqvirtual.edu.co
- 🐛 **Issues**: [GitHub Issues](https://github.com/AiiwA/Proyecto_Final_P2/issues)
- 📖 **Wiki**: [Documentación Completa](https://github.com/AiiwA/Proyecto_Final_P2/wiki)

### 🔗 Enlaces Útiles

- [JavaFX Documentation](https://openjfx.io/)
- [Lombok Documentation](https://projectlombok.org/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [Design Patterns (Gang of Four)](https://refactoring.guru/design-patterns)

---

<div align="center">

## ⭐ Si te gustó el proyecto, dale una estrella!

**© 2025 CityDrop - Sistema de Gestión de Envíos Urbanos**

Desarrollado con berraquera por **Brandon Gil** y **Santiago Padilla**

[⬆ Volver arriba](#-citydrop---sistema-de-gestión-de-envíos-urbanos)

</div>
