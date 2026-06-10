# Propuesta de Proyecto: EcoQuest

**EcoQuest:** Pequeñas acciones, gran impacto  

<div style="text-align: center;">
  <img src="./Assets/ImagenDiario1.png" alt="EcoQuest" width="60%"/>
</div>


### Datos del Equipo

- **Nombre del Proyecto:** EcoQuest
- **Equipo:** Prompt
- **Miembros del Equipo:**
  - Iván Arias Pastor (Hacedor)
  - Abdurrahman Belaziz Amier (Hacedor/Divergente)
  - Michel Garcia Galipienso (Divergente)
  - Saúl Valcarcel Picapiedra (Analista)
- **Curso:** 2º DAM - IES Doctor Balmis
- **Fecha de inicio:** Octubre 2025

### Alineación con los Objetivos de Desarrollo Sostenible (ODS)

Este proyecto se alinea directa y explícitamente con los siguientes Objetivos de Desarrollo Sostenible de las Naciones Unidas, tal como se requiere en la programación didáctica:

- **ODS 4: Educacion Calidad:**
  La aplicación ofrece contenidos educativos accesibles, interactivos y gamificados sobre sostenibilidad, reciclaje, biodiversidad, ahorro de agua y energía.

    - Promueve el aprendizaje inclusivo y equitativo para todas las edades.

    - Utiliza simuladores y juegos para facilitar la comprensión de temas ambientales.

    - Fomenta el pensamiento crítico y la conciencia ecológica desde la educación informal.
   
-  **ODS 6: Agua Limpia y Saneamiento:**
    Incluye módulos específicos sobre el uso responsable del agua y prácticas de ahorro en el hogar y la comunidad.

      - Enseña a identificar hábitos que desperdician agua.

      - Simuladores muestran el impacto positivo de pequeñas acciones (cerrar el grifo, duchas cortas, reutilización).

      - Promueve el saneamiento básico a través de educación ambiental.
      - 
- **ODS 12: Producción y Consumo Responsables:**
  La app impulsa hábitos sostenibles en el día a día mediante retos, seguimiento y recompensas.

    - Educa sobre la reducción de residuos, reutilización y reciclaje.

    - Motiva a los usuarios a adoptar un estilo de vida más consciente y responsable.

    - Ofrece estadísticas personales para visualizar el impacto del consumo. 
  
- **ODS 13: Ciudades y Comunidades Sostenibles:** 
  El proyecto sensibiliza sobre el cambio climático y empodera a los usuarios para actuar.

    - Calcula y muestra la huella ecológica individual.

    - Propone acciones concretas para reducir emisiones (transporte sostenible, dieta baja en carbono).

    - Promueve la participación ciudadana en la protección del medio ambiente.

### Descripción del Problema
En nuestra sociedad, la conciencia ambiental aún no está suficientemente integrada en la vida cotidiana. Muchas personas desconocen el impacto de sus acciones sobre el medio ambiente, y aunque existen iniciativas ecológicas, la participación ciudadana suele ser baja y dispersa. Además, no hay suficientes herramientas que motiven, eduquen y conecten a la comunidad en torno a hábitos sostenibles.

### Descripción de la Solución Propuesta
EcoQuest es una solución multiplataforma diseñada para fomentar la educación ambiental a través de retos ecológicos gamificados y un sistema de ranking comunitario. Su objetivo es transformar el aprendizaje ecológico en una experiencia divertida, colaborativa y con impacto real.

 1. Aplicación Móvil para Clientes (Android con Kotlin y Jetpack Compose): 
   
     - Plataforma principal para los participantes.
     - Permite a los usuarios registrarse, completar retos ecológicos (como reciclar, plantar  árboles, reducir consumo de agua), subir evidencias (fotos, ubicaciones) y ganar puntos.
    - Incluye un sistema de logros, niveles y recompensas virtuales.
    - El ranking comunitario motiva la participación y permite ver el impacto colectivo.  
  <br>

1. Aplicación de Escritorio para Administradores (WPF con C# y MVVM):
   
    - Herramienta de gestión para educadores, ONGs y coordinadores de sostenibilidad.
    - Permite crear y validar nuevos retos, revisar evidencias enviadas por los usuarios, gestionar perfiles y moderar contenido.
    - Ofrece estadísticas sobre participación, impacto ambiental estimado y evolución de la comunidad.
  <br>

1. Servicio Backend (API REST con Java, Spring Boot y Spring Security): 
   
      - Núcleo del sistema que gestiona la lógica de negocio.
      - Controla la autenticación de usuarios mediante JWT, la validación de retos, el cálculo de puntuaciones y la persistencia de datos en una base de datos PostgreSQL.
      - Asegura la comunicación entre las aplicaciones cliente y administrador de forma segura y escalable.
  
   
### Actores y Roles
- **Usuario**: Cualquier persona que utiliza la aplicación para interactuar con comunidades o retos.
- **Comunidades**: 	Grupos creados por personas u organizaciones con intereses comunes.
- **Administrador de comunidad**: Persona responsable de gestionar y moderar una comunidad específica. Puede aceptar miembros, publicar contenido, y configurar reglas internas.
- **Administrador**: Superusuario con acceso a la aplicación de escritorio. Supervisa, modera y gestiona el funcionamiento general de la plataforma. Tiene autoridad sobre todas las comunidades.

### Arquitectura general

<div style="text-align: center;">
  <img src="./Assets/arquitectura.png" alt="EcoQuest" width="90%"/>
</div>
  
## Casos de Uso Principales

### 📲 Para Usuarios

| Casos de Uso | Descripción | Prioridad |
|:-- |:---- | :--: |
| Registro de Usuario | Registro y login de los usuarios | Alta |
| Creación Comunidades | Comunidad para quedadas, noticias, mensajes... | Alta |
| Perfil de Usuarios y Comunidades| Sus perfiles respectivos | Alta |
| Lista de retos ecológicos | Retos predefinidos(reciclar, usar transporte público, apagar luces, etc.) | Alta |
| Marcar retos | Funciones del progreso de los retos: Completado, Aceptado, none... y mostrarlo | Media |
| Puntuación y Progreso | Cada reto suma puntos | Media |
| Estadísticas | Mostrar total de retos completados o puntos en un gráfico simple | Media |
| Notificación diaria | Recordatorio de retos/publicaciones comunidad | Media |
| Juegos ecologicos para niños | Juegos simples para fomentar las ODS | Baja |
| Pantalla Informativa para las ODS | Explicar brevemente que son las ODS y como contribuyen | Baja |

### 👨‍💻 Para Administradores de Comunidad

| Casos de Uso | Descripción | Prioridad |
|:-- |:---- | :--: |
| Gestión de miembros | Aceptar, rechazar o eliminar miembros de la comunidad | Alta |
| Publicación de contenido | Crear publicaciones, noticias, eventos o retos dentro de la comunidad | Alta |
| Moderación de contenido | Eliminar publicaciones inapropiadas o fuera de contexto | Alta |
| Configuración de comunidad | Editar nombre, descripción, imagen, tipo de comunidad, etc. | Media |
| Estadísticas de comunidad | Visualizar actividad, retos completados y participación | Media |
| Envío de notificaciones | Enviar mensajes o recordatorios a los miembros | Media |

### 🛠️ Para Administradores de Plataforma

| Casos de Uso | Descripción | Prioridad |
|:-- |:---- | :--: |
| Gestión global de usuarios | Ver, suspender o eliminar cuentas de usuarios | Alta |
| Gestión global de comunidades | Supervisar, editar o eliminar comunidades | Alta |
| Moderación de contenido | Revisar y eliminar contenido reportado o inapropiado | Alta |
| Panel de control | Acceso a métricas generales de uso, actividad y crecimiento | Alta |
| Configuración de plataforma | Ajustes generales como idioma, permisos, categorías de retos | Media |
| Gestión de retos globales | Crear, editar o eliminar retos ecológicos disponibles para todos | Media |

## ⚠️ Riesgos y Mitigación

| Riesgo | Mitigación |
|:-- |:---- |
| Mapas y Geolocalización | Uso de alguna API de Google Maps y componente especificos |
| Notificaciones | Uso de Firebase Cloud Messaging (FCM) |
| Complejidad JWT | Uso de sesiones y encriptación de contraseñas |
| Falta de Experiencia | Uso de IA para generación de código y resolución de dudas | 
| Falta de Compromiso | Reuniones semanales de seguimiento y revisión del proyecto | 
| Conflictos en equipo | Roles claros y comunicación abierta |

### Planificación aproximada Sprints por quincena

| Sprint | Semanas | Fecha fin | Entregable clave |
|--------|---------|-----------|------------------|
| 1      | 7-8     | 31 oct    | Modelo de dominio Java + diagramas |
| 2      | 9-10    | 14 nov    | Clases transpiladas a C#/Kotlin + mocks con IA |
| 3      | 11-12   | 28 nov    | Prototipos UI/UX Stitch/Figma (Material 3 & XAML) |
| 4      | 13-14   | 12 dic    | App WPF navegable con estados y mocks |
| 5      | 15-16   | 26 dic    | App Android navegable con estados y mocks |
| 6      | 17-18   | 09 ene    | Servicios de negocio en WPF (MVVM) |
| 7      | 19-20   | 23 ene    | Servicios de negocio en Android (MVVM) |
| 8      | 21-22   | 06 feb    | API REST CRUD + BBDD H2 (2+ entidades) |
| 9      | 23-24   | 27 feb    | Seguridad: registro + JWTs + roles |
| 10     | 25-26   | 20 mar    | Autorización por roles + endpoints protegidos |
| 11     | 27-28   | 03 abr    | Integración full-stack (estudiantes mocks => API) |
| 12     | 29-30   | 08 may    | Testing |
| 13     | 31-32   | 22 may    | Memoria final, video 2 min, presentación oral |


### Organización repositorio

```
https://github.com/Prompt-pi2damiesbalmis/
├── docs/
|   |── Assets           ← imagenes, iconos, logos
│   ├── PROYECTO.md      ← visión, ODS, casos de uso
│   ├── DISENO.md        ← modelo, decisiones arquitectónicas
│   └── DIARIO.md        ← seguimiento semanal individual
├── backend/
├── frontend-wpf/
└── frontend-android/
```