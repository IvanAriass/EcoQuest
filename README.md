# EcoQuest

Plataforma social para la organización y participación en actividades ecológicas al aire libre. EcoQuest conecta a usuarios con comunidades y eventos enfocados en la sostenibilidad y el cuidado del medio ambiente.

## Arquitectura

El proyecto se compone de tres aplicaciones independientes:

| Aplicación | Tecnología | Propósito |
|---|---|---|
| **EcoQuestAPI** | Java 21 + Spring Boot 3.4 | Backend REST API |
| **EcoQuestDesktop** | C# .NET 9 + WPF | Cliente de administración para escritorio (Windows) |
| **EcoQuestMobile** | Kotlin + Jetpack Compose | Aplicación móvil para usuarios finales (Android) |

```
┌────────────────┐      ┌──────────────────┐
│  EcoQuestMobile│ ──→  │                  │
│  (Android)     │      │   EcoQuestAPI    │
│                │      │  (Spring Boot)   │
│  EcoQuestDesktop│ ──→ │                  │
│  (WPF .NET)    │      │  Puerto: 9000    │
└────────────────┘      └──────────────────┘
```

## Estructura del proyecto

```
EcoQuest/
├── EcoQuestAPI/          # Backend REST (Java / Spring Boot)
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── EcoQuestDesktop/      # Cliente escritorio (C# / WPF)
│   ├── EcoQuestDesktop.sln
│   └── EcoQuestDesktop/
└── EcoQuestMobile/       # App Android (Kotlin / Jetpack Compose)
    ├── build.gradle.kts
    └── app/
```

## Requisitos mínimos

### API
- Java 21+
- Maven 3.9+ (o usar el wrapper `mvnw`)
- Docker (opcional, para despliegue)

### Desktop
- .NET 9.0 SDK
- Visual Studio 2022 (recomendado)
- Windows (WPF)

### Mobile
- Android Studio Hedgehog o superior
- Android SDK 35
- Gradle 8.7+

## Inicio rápido

### 1. API (backend)

```bash
cd EcoQuestAPI
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

La API arranca en `http://localhost:9000`. Consola H2 disponible en `http://localhost:9000/h2-console`.

### 2. Desktop (administración)

Abrir `EcoQuestDesktop/EcoQuestDesktop.sln` con Visual Studio y ejecutar. La conexión a la API se configura en `App.config`.

### 3. Mobile (usuarios)

Abrir la carpeta `EcoQuestMobile/` con Android Studio y ejecutar en un emulador. La app apunta a `http://10.0.2.2:9000/api` (localhost del emulador Android).

## Funcionalidades principales

- **Usuarios**: registro, inicio de sesión, perfiles, bloqueo/desbloqueo
- **Comunidades**: creación, moderación (pendiente → revisión → activa/cancelada), búsqueda por estado
- **Eventos**: creación, registro de asistentes, finalización, revisión
- **Tienda (accesorios)**: catálogo de productos con imágenes
- **Tema oscuro/claro** (solo mobile)
- **Persistencia local** con Room (mobile) y SQLite (desktop) para caché offline

## Despliegue con Docker (API)

```bash
cd EcoQuestAPI
docker build -t ecoquest-api .
docker run -p 9000:9000 -e SPRING_PROFILES_ACTIVE=prod ecoquest-api
```

## Licencia

Proyecto educativo sin licencia específica.
