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
```java
SistemaGestion sistema = SistemaGestion.obtenerInstancia();
```

#### 2. **Factory Method**
- **Clases**: `EntidadFactory`, `EnvioFactory`
- **Propósito**: Creación de objetos complejos sin exponer la lógica de construcción
- **Uso**: Crear envíos de diferentes tipos y entidades del sistema
```java
Envio envio = EnvioFactory.crearEnvioExpress(usuario, origen, destino, peso);
Direccion direccion = EntidadFactory.crearDireccion("Casa", "Calle 50", "Armenia", lat, lon);
```

#### 3. **Builder**
- **Clases**: `Direccion`, `Envio`, `Pago`, `Tarifa`, `Incidencia`, `Repartidor`
- **Propósito**: Construcción flexible de objetos complejos con múltiples parámetros
- **Uso**: Facilitar la creación de objetos con muchos atributos opcionales
```java
Repartidor rep = Repartidor.builder()
    .idRepartidor("REP-001")
    .nombre("Carlos Ramírez")
    .documento("1234567890")
    .telefono("3101234567")
    .zonaCobertura("Armenia Centro")
    .estado(EstadoRepartidor.ACTIVO)
    .build();
```

### 🔌 Patrones Estructurales (3)

#### 4. **Decorator**
- **Clases**: `ServicioEnvio`, `ServicioEnvioBase`, `ServicioEnvioDecorator`, `ServicioSeguro`
- **Propósito**: Agregar funcionalidades adicionales a envíos de forma dinámica
- **Uso**: Añadir seguro, prioridad o características especiales a envíos
```java
ServicioEnvio envio = new ServicioEnvioBase(envioBase);
envio = new ServicioSeguro(envio, valorSeguro);
double costoTotal = envio.calcularCosto();
```

#### 5. **Adapter**
- **Clases**: `EmailAdapter`, `SMSAdapter`, `MapasAdapter`
- **Propósito**: Integrar servicios externos con interfaces incompatibles
- **Uso**: Notificaciones por email/SMS y cálculo de distancias
```java
ServicioNotificacion emailAdapter = new EmailAdapter(servicioEmailExterno);
emailAdapter.enviarNotificacion("usuario@email.com", "Envío en camino");

ServicioDistancia mapasAdapter = new MapasAdapter(servicioMapasExterno);
double distancia = mapasAdapter.calcularDistancia(origen, destino);
```

#### 6. **Bridge**
- **Clases**: `Reporte`, `FormatoReporte`, `FormatoCSV`, `FormatoPDF`, `ReporteEnvios`, `ReporteUsuarios`
- **Propósito**: Separar abstracción (tipo de reporte) de implementación (formato)
- **Uso**: Generar diferentes tipos de reportes en múltiples formatos
```java
FormatoReporte formato = new FormatoPDF();
Reporte reporte = new ReporteEnvios(formato, listaEnvios);
reporte.generar("reporte_envios_mensual");
```

### 🎬 Patrones Comportamentales (4)

#### 7. **Strategy**
- **Clases**: `EstrategiaPago`, `PagoTarjeta`, `PagoNequi`, `PagoPayPal`, `PagoEfectivo`
- **Propósito**: Definir familia de algoritmos intercambiables para procesamiento de pagos
- **Uso**: Procesar pagos con diferentes métodos de pago
```java
// Cambiar estrategia de pago dinámicamente
EstrategiaPago estrategia = new PagoTarjeta("4532-****-****-1234", "Juan Pérez");
Pago pago = estrategia.procesarPago(envio);
```

#### 8. **Observer**
- **Clases**: `Observer`, `Subject`, `NotificadorUsuario`, `NotificadorRepartidor`, `AdminDashboardObserver`
- **Propósito**: Notificar automáticamente a observadores cuando cambia el estado
- **Uso**: Actualizar UI y enviar notificaciones cuando un envío cambia de estado
```java
envio.agregarObservador(new NotificadorUsuario(usuario));
envio.agregarObservador(new NotificadorRepartidor(repartidor));
envio.cambiarEstado(EstadoEnvio.EN_RUTA); // Notifica a todos los observadores
```

#### 9. **Command**
- **Clases**: `Command`, `GestorComandos`, `AsignarRepartidorCommand`, `CancelarEnvioCommand`, `ActualizarEstadoCommand`
- **Propósito**: Encapsular operaciones como objetos, permitiendo deshacer/rehacer
- **Uso**: Historial de operaciones reversibles
```java
Command comando = new AsignarRepartidorCommand(envio, repartidor);
gestorComandos.ejecutarComando(comando);
// Deshacer si es necesario
gestorComandos.deshacerUltimoComando();
```

#### 10. **State**
- **Clases**: `EstadoEnvio`, `EstadoSolicitado`, `EstadoAsignado`, `EstadoEnRuta`, `EstadoEntregado`, `EstadoIncidencia`
- **Propósito**: Cambiar comportamiento del envío según su estado actual
- **Uso**: Gestionar transiciones válidas entre estados de envío
```java
envio.asignarRepartidor();  // EstadoSolicitado -> EstadoAsignado
envio.iniciarEntrega();      // EstadoAsignado -> EstadoEnRuta
envio.marcarEntregado();     // EstadoEnRuta -> EstadoEntregado
```

---

## 📁 Estructura Detallada del Proyecto

```
Proyecto_Final_P2/
│
├── src/main/java/co/edu/uniquindio/poo/
│   │
│   ├── 📦 adapter/                    # Patrón Adapter - Integración servicios externos
│   │   ├── ServicioNotificacion.java          # Interfaz para notificaciones
│   │   ├── EmailAdapter.java                  # Adaptador para email
│   │   ├── SMSAdapter.java                    # Adaptador para SMS
│   │   ├── ServicioEmailExterno.java          # Servicio externo de email
│   │   ├── ServicioSMSExterno.java            # Servicio externo de SMS
│   │   ├── ServicioDistancia.java             # Interfaz para distancias
│   │   ├── MapasAdapter.java                  # Adaptador para mapas
│   │   └── ServicioMapasExterno.java          # Servicio externo de mapas
│   │
│   ├── 📦 bridge/                     # Patrón Bridge - Reportes y formatos
│   │   ├── Reporte.java                       # Abstracción de reporte
│   │   ├── FormatoReporte.java                # Implementación de formato
│   │   ├── FormatoCSV.java                    # Formato CSV
│   │   ├── FormatoPDF.java                    # Formato PDF (Apache PDFBox)
│   │   ├── ReporteEnvios.java                 # Reporte de envíos
│   │   └── ReporteUsuarios.java               # Reporte de usuarios
│   │
│   ├── 📦 command/                    # Patrón Command - Operaciones reversibles
│   │   ├── Command.java                       # Interfaz Command
│   │   ├── GestorComandos.java                # Gestor de comandos (Invoker)
│   │   ├── AsignarRepartidorCommand.java      # Comando asignar repartidor
│   │   ├── CancelarEnvioCommand.java          # Comando cancelar envío
│   │   └── ActualizarEstadoCommand.java       # Comando actualizar estado
│   │
│   ├── 📦 controller/                 # Lógica de Negocio (Business Logic)
│   │   ├── UsuarioController.java             # CRUD y lógica de usuarios
│   │   ├── EnvioController.java               # Gestión de envíos
│   │   ├── RepartidorController.java          # Gestión de repartidores
│   │   ├── AdminController.java               # Operaciones administrativas
│   │   └── PagoController.java                # Procesamiento de pagos
│   │
│   ├── 📦 decorator/                  # Patrón Decorator - Servicios adicionales
│   │   ├── ServicioEnvio.java                 # Interfaz Component
│   │   ├── ServicioEnvioBase.java             # Concrete Component
│   │   ├── ServicioEnvioDecorator.java        # Decorator abstracto
│   │   └── ServicioSeguro.java                # Concrete Decorator (seguro)
│   │
│   ├── 📦 factory/                    # Patrón Factory - Creación de objetos
│   │   ├── EntidadFactory.java                # Factory de entidades generales
│   │   └── EnvioFactory.java                  # Factory de envíos específicos
│   │
│   ├── 📦 model/                      # Modelos de Dominio (Domain Models)
│   │   ├── Administrador.java                 # Entidad Administrador (Builder)
│   │   ├── Direccion.java                     # Entidad Dirección (Builder)
│   │   ├── Envio.java                         # Entidad Envío (Builder + Observer)
│   │   ├── Incidencia.java                    # Entidad Incidencia (Builder)
│   │   ├── MetodoPago.java                    # Entidad Método de Pago (Builder)
│   │   ├── Pago.java                          # Entidad Pago (Builder)
│   │   ├── Repartidor.java                    # Entidad Repartidor (Builder)
│   │   ├── SistemaGestion.java                # Singleton - Sistema central
│   │   ├── Tarifa.java                        # Entidad Tarifa (Builder)
│   │   └── Usuario.java                       # Entidad Usuario (Builder)
│   │
│   ├── 📦 observer/                   # Patrón Observer - Notificaciones
│   │   ├── Observer.java                      # Interfaz Observer
│   │   ├── Subject.java                       # Interfaz Subject
│   │   ├── NotificadorUsuario.java            # Observer para usuarios
│   │   ├── NotificadorRepartidor.java         # Observer para repartidores
│   │   └── AdminDashboardObserver.java        # Observer para dashboard admin
│   │
│   ├── 📦 state/                      # Patrón State - Estados de envío
│   │   ├── EstadoEnvio.java                   # Interfaz State
│   │   ├── EstadoSolicitado.java              # Estado Solicitado
│   │   ├── EstadoAsignado.java                # Estado Asignado
│   │   ├── EstadoEnRuta.java                  # Estado En Ruta
│   │   ├── EstadoEntregado.java               # Estado Entregado
│   │   └── EstadoIncidencia.java              # Estado Incidencia
│   │
│   ├── 📦 strategy/                   # Patrón Strategy - Métodos de pago
│   │   ├── EstrategiaPago.java                # Interfaz Strategy
│   │   ├── PagoTarjeta.java                   # Pago con tarjeta
│   │   ├── PagoNequi.java                     # Pago con Nequi
│   │   ├── PagoPayPal.java                    # Pago con PayPal
│   │   └── PagoEfectivo.java                  # Pago en efectivo
│   │
│   ├── 📦 utils/                      # Utilidades del Sistema
│   │   └── DataInitializer.java               # Inicialización datos de prueba
│   │
│   ├── 📦 viewController/             # Controladores de Vista (UI Controllers)
│   │   ├── NavigationController.java          # Navegación entre vistas
│   │   ├── SessionManager.java                # Gestión de sesión de usuario
│   │   ├── LoginViewController.java           # Controlador login
│   │   ├── RegisterViewController.java        # Controlador registro
│   │   ├── UserDashboardViewController.java   # Dashboard de usuario
│   │   ├── AdminDashboardViewController.java  # Dashboard de administrador
│   │   ├── NuevoEnvioViewController.java      # Crear nuevo envío
│   │   └── MapaSelectorViewController.java    # Selector de ubicaciones
│   │
│   ├── module-info.java               # Configuración módulo Java 9+
│   └── HelloApplication.java          # Clase principal de la aplicación
│
├── src/main/resources/co/edu/uniquindio/demop2pf/
│   ├── login-view.fxml                # Vista de login
│   ├── register-view.fxml             # Vista de registro
│   ├── user-dashboard.fxml            # Dashboard usuario
│   ├── admin-dashboard.fxml           # Dashboard administrador
│   ├── nuevo-envio.fxml               # Formulario nuevo envío
│   └── mapa-selector.fxml             # Selector de mapa
│
├── pom.xml                            # Configuración Maven
├── LICENSE                            # Licencia del proyecto
└── README.md                          # Este archivo
```

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

### Dependencias Maven

```xml
<dependencies>
    <!-- JavaFX Core -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>17.0.6</version>
    </dependency>
    
    <!-- JavaFX FXML -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>17.0.6</version>
    </dependency>
    
    <!-- JavaFX Web (para mapas) -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-web</artifactId>
        <version>17.0.6</version>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.34</version>
        <scope>provided</scope>
    </dependency>
    
    <!-- Apache PDFBox -->
    <dependency>
        <groupId>org.apache.pdfbox</groupId>
        <artifactId>pdfbox</artifactId>
        <version>2.0.29</version>
    </dependency>
    
    <!-- JUnit 5 -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>5.10.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

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

#### Error: "Módulo JavaFX no encontrado"
- Asegúrate de tener Java 17+ instalado
- Limpia el proyecto: `mvn clean`
- Elimina carpeta `target/` y recompila

#### Error de Lombok en IDE
- **IntelliJ IDEA**: Instalar plugin "Lombok"
- **Eclipse**: Ejecutar `lombok.jar` como instalador
- **VS Code**: Instalar extensión "Lombok Annotations Support"

---

## 👤 Uso del Sistema

### 🔐 Credenciales de Prueba

#### Usuario Cliente
```
Correo: juan@email.com
Contraseña: 123456
```

#### Administrador
```
Correo: admin@sistema.com
Contraseña: admin123
```

### 📊 Datos Iniciales Pre-cargados

Al iniciar la aplicación por primera vez, el sistema carga automáticamente:

- ✅ **2 Usuarios** (Juan Pérez, María González)
- ✅ **1 Administrador** (Administrador)
- ✅ **3 Repartidores**:
  - Carlos Ramírez (ACTIVO - Armenia Centro)
  - Laura Martínez (ACTIVO - Calarcá)
  - Diego Silva (INACTIVO - Armenia Norte)
- ✅ **3 Envíos de ejemplo** en diferentes estados
- ✅ **Direcciones de prueba** en Armenia y Calarcá

### 🎯 Flujo de Trabajo Típico

#### Como Usuario:
1. **Registrarse** o **Iniciar sesión** con credenciales
2. **Crear un nuevo envío**:
   - Seleccionar origen y destino
   - Ingresar datos del paquete (peso, dimensiones)
   - Elegir tipo de envío
   - Seleccionar método de pago
3. **Rastrear el envío** desde el dashboard
4. **Recibir notificaciones** de cambios de estado

#### Como Administrador:
1. **Iniciar sesión** con credenciales de admin
2. **Ver métricas** generales del sistema
3. **Gestionar repartidores**:
   - Agregar nuevos repartidores
   - Actualizar estado (ACTIVO/INACTIVO/EN_RUTA)
   - Ver envíos asignados a cada repartidor
4. **Asignar envíos** a repartidores disponibles
5. **Generar reportes** CSV/PDF
6. **Gestionar usuarios** y **envíos**

#### Como Repartidor (simulado):
1. El administrador crea el repartidor
2. El administrador asigna envíos al repartidor
3. Se puede ver la lista de envíos asignados
4. El administrador actualiza el estado del repartidor

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
│  ┌──────────┐  ┌──────────┐  ┌──────────┐          │
│  │ Strategy │  │ Observer │  │  State   │          │
│  │  (Pago)  │  │(Notific.)│  │ (Envío)  │          │
│  └──────────┘  └──────────┘  └──────────┘          │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐          │
│  │Decorator │  │ Adapter  │  │  Bridge  │          │
│  │(Servicio)│  │(Externos)│  │(Reportes)│          │
│  └──────────┘  └──────────┘  └──────────┘          │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐          │
│  │ Factory  │  │ Command  │  │ Builder  │          │
│  │(Entidad) │  │(Historial)│ │(Objetos) │          │
│  └──────────┘  └──────────┘  └──────────┘          │
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
│  │  - Pago, Direccion, Tarifa, etc.             │  │
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

### 🎓 Guía de Patrones Implementados

#### Cuándo usar cada patrón:

| Patrón | Cuándo Usar |
|--------|-------------|
| **Singleton** | Necesitas una única instancia global (SistemaGestion) |
| **Factory** | Creación compleja de objetos con lógica (Envios, Entidades) |
| **Builder** | Objetos con muchos parámetros opcionales (Direccion, Pago) |
| **Decorator** | Agregar funcionalidades dinámicamente (Seguro a envío) |
| **Adapter** | Integrar servicios con interfaces incompatibles (Email, SMS) |
| **Bridge** | Separar abstracción de implementación (Reportes/Formatos) |
| **Strategy** | Algoritmos intercambiables (Métodos de pago) |
| **Observer** | Notificar cambios a múltiples interesados (Estado envío) |
| **Command** | Encapsular operaciones reversibles (Historial) |
| **State** | Comportamiento cambia según estado (Estados de envío) |

### 📝 Convenciones de Código

- **Nombres de clases**: PascalCase (`UsuarioController`)
- **Nombres de métodos**: camelCase (`crearEnvio`)
- **Constantes**: UPPER_SNAKE_CASE (`MAX_PESO_KG`)
- **Paquetes**: lowercase (`co.edu.uniquindio.poo`)
- **Anotaciones Lombok**: @Getter, @Setter, @Builder, @NonNull

### 🧪 Testing

El proyecto incluye estructura para pruebas unitarias con JUnit 5:

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar con reporte de cobertura
mvn test jacoco:report
```

---

## 🤝 Contribución

### 💡 Cómo Contribuir

1. **Fork** el repositorio
2. **Crea una rama** para tu feature (`git checkout -b feature/AmazingFeature`)
3. **Commit** tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. **Push** a la rama (`git push origin feature/AmazingFeature`)
5. **Abre un Pull Request**

### 📋 Guía de Estilo

- Seguir principios SOLID
- Documentar métodos públicos con JavaDoc
- Escribir tests para nueva funcionalidad
- Mantener compatibilidad con Java 17
- Usar Lombok para reducir boilerplate

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

## 📊 Estadísticas del Proyecto

```
Lenguajes:
  Java:        85%
  FXML:        10%
  XML (Maven):  3%
  Markdown:     2%

Líneas de código:
  Java:        ~8,500 líneas
  FXML:        ~1,200 líneas
  Total:       ~10,000 líneas

Archivos:
  Clases Java:      65 archivos
  Vistas FXML:       6 archivos
  Configuración:     2 archivos
  
Patrones:         10 implementados
Paquetes:         11 organizados
```

---

<div align="center">

## ⭐ Si te gustó el proyecto, dale una estrella!

**© 2025 CityDrop - Sistema de Gestión de Envíos Urbanos**

Desarrollado con berraquera por **Brandon Gil** y **Santiago Padilla**

[⬆ Volver arriba](#-citydrop---sistema-de-gestión-de-envíos-urbanos)

</div>
