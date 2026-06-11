# EcoQuest

Plataforma social para la organización y participación en actividades ecológicas al aire libre. EcoQuest conecta a usuarios con comunidades y eventos enfocados en la sostenibilidad y el cuidado del medio ambiente.

## Arquitectura

El proyecto se compone de tres aplicaciones independientes:

| Aplicación | Tecnología | Propósito |
|---|---|---|
| **EcoQuestAPI** | Java 21 + Spring Boot 3.4 | Backend REST API con autenticación JWT |
| **EcoQuestDesktop** | C# .NET 9 + WPF | Cliente de administración para escritorio (Windows) |
| **EcoQuestMobile** | Kotlin 2.0 + Jetpack Compose + Hilt | Aplicación móvil para usuarios finales (Android) |

```
┌────────────────┐      ┌──────────────────┐      ┌──────────────┐
│  EcoQuestMobile│ ──→  │                  │ ──→  │              │
│  (Android)     │ JWT  │   EcoQuestAPI    │      │   MySQL /    │
│                │      │  (Spring Boot)   │      │   H2 (dev)   │
│  EcoQuestDesktop│ ──→ │                  │      │              │
│  (WPF .NET)    │      │  Puerto: 9000    │      │              │
└────────────────┘      └──────────────────┘      └──────────────┘
```

## Estructura del proyecto

```
EcoQuest/
├── EcoQuestAPI/              # Backend REST (Java / Spring Boot)
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       └── main/java/com/proyecto/spring/
│           ├── controladores/     # REST controllers (usuarios, eventos, comunidades, productos, auth)
│           ├── configuracion/     # CORS y configuración general
│           ├── dto/               # Data Transfer Objects
│           ├── manejadores/       # Manejadores de errores
│           ├── modelos/           # Entidades JPA (usuario, evento, comunidad, producto)
│           ├── repository/        # Repositorios Spring Data JPA
│           ├── seguridad/         # JWT (filtro, util, SecurityConfig)
│           └── servicios/         # Lógica de negocio
├── EcoQuestDesktop/          # Cliente escritorio (C# / WPF)
│   ├── EcoQuestDesktop.sln
│   └── EcoQuestDesktop/
│       ├── Models/            # Modelos del dominio
│       ├── Services/          # ApiRestService (RestSharp) + NavegacionService
│       ├── ViewModels/        # ViewModels (MVVM con CommunityToolkit)
│       └── Views/             # Vistas WPF (XAML)
└── EcoQuestMobile/           # App Android (Kotlin / Jetpack Compose)
    ├── build.gradle.kts
    ├── gradle/
    │   └── libs.versions.toml
    └── app/
        └── src/main/java/com/ecoquest/app/
            ├── data/            # Capa de datos (local + remoto)
            │   ├── local/       # Room (DAO, entities, mappers)
            │   ├── remote/      # Retrofit (ApiService, DTOs, AuthInterceptor)
            │   └── repository/  # Implementaciones offline-first de repositorios
            ├── di/              # Módulos de inyección (Hilt)
            ├── domain/          # Capa de dominio
            │   ├── model/       # Modelos de dominio
            │   ├── repository/  # Interfaces de repositorio
            │   └── usecase/     # Casos de uso (auth, comunidades, eventos)
            ├── managers/        # TokenManager (SharedPreferences)
            └── ui/              # Capa de presentación (MVVM)
                ├── components/  # Componentes reutilizables
                ├── feature/     # Pantallas (auth, home, comunidades, etc.)
                ├── navigation/  # Navegación (Routes, NavHosts)
                └── theme/       # Tema (verde monocromático, claro/oscuro)
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
- Android Studio Koala o superior
- Android SDK 35 (minSdk 28, targetSdk 35)
- Gradle 8.7 / AGP 8.6.0
- Kotlin 2.0.20 + Jetpack Compose BOM 2024.09.02

## Inicio rápido

### 1. API (backend)

```bash
cd EcoQuestAPI
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

La API arranca en `http://localhost:9000`. Consola H2 disponible en `http://localhost:9000/h2-console`.

Al arrancar, si la base de datos está vacía se ejecuta el **CommandLineRunner** que inserta datos de semilla:
- **6 usuarios** (contraseñas hasheadas con BCrypt)
- **8 comunidades** con distintos estados
- **7 eventos** asociados a comunidades
- **6 productos** en la tienda

### 2. Desktop (administración)

```bash
cd EcoQuestDesktop
dotnet run
```

O abrir `EcoQuestDesktop.sln` con Visual Studio. La conexión a la API se configura en `Properties/Settings.settings` (`ApiRestEndPoint`).

### 3. Mobile (usuarios)

Abrir la carpeta `EcoQuestMobile/` con Android Studio y ejecutar en un emulador. La app apunta a `http://10.0.2.2:9000/api` (localhost del emulador Android).

## Autenticación JWT

La API implementa autenticación mediante tokens JWT.

### Endpoints de autenticación

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/auth/login` | Inicio de sesión (email + contraseña) → devuelve token JWT |
| POST | `/api/auth/register` | Registro de nuevo usuario |

### Flujo de autenticación (Mobile)

1. El usuario introduce email y contraseña
2. `POST /api/auth/login` → devuelve `{ token, usuarioId, email, nombreUsuario, imagen }`
3. El token se almacena en `TokenManager` (SharedPreferences)
4. `AuthInterceptor` añade el token como `Authorization: Bearer <token>` en cada petición
5. La API valida el token mediante `JwtAuthenticationFilter`

### Escritorio

El cliente de escritorio funciona sin autenticación JWT al ser una herramienta de administración local. La API acepta peticiones sin token gracias a `anyRequest().permitAll()` en SecurityConfig.

## API REST

### Endpoints principales

#### Usuarios
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/usuarios` | Lista usuarios no bloqueados |
| GET | `/api/usuarios/todos` | Lista todos los usuarios |
| GET | `/api/usuarios/{id}` | Obtener usuario por ID |
| GET | `/api/usuarios/buscar?nombre=` | Buscar por nombre de usuario |
| GET | `/api/usuarios/bloqueados` | Lista usuarios bloqueados |
| POST | `/api/usuarios/con-imagen` | Crear usuario con imagen (multipart) |
| PUT | `/api/usuarios/{id}` | Actualizar usuario |
| PUT | `/api/usuarios/{id}/con-imagen` | Actualizar usuario con imagen (multipart) |
| PUT | `/api/usuarios/{id}/bloquear` | Bloquear usuario |
| PUT | `/api/usuarios/{id}/desbloquear` | Desbloquear usuario |
| DELETE | `/api/usuarios/{id}` | Eliminar usuario |
| GET | `/api/usuarios/imagen/{nombre}` | Servir imagen de usuario |

#### Eventos
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/eventos` | Lista todos los eventos |
| GET | `/api/eventos/{id}` | Obtener evento por ID |
| GET | `/api/eventos/buscar?nombre=` | Buscar por nombre |
| GET | `/api/eventos/estado?estado=` | Buscar por estado |
| POST | `/api/eventos/comunidad/{id}` | Crear evento en comunidad |
| POST | `/api/eventos/con-imagen` | Crear evento con imagen (multipart) |
| PUT | `/api/eventos/{id}` | Actualizar evento |
| PUT | `/api/eventos/{id}/con-imagen` | Actualizar evento con imagen (multipart) |
| PATCH | `/api/eventos/{id}/en-revision` | Enviar a revisión |
| PATCH | `/api/eventos/{id}/aprobar` | Aprobar evento |
| PATCH | `/api/eventos/{id}/cancelar` | Cancelar evento |
| PATCH | `/api/eventos/{id}/finalizar` | Finalizar evento |
| DELETE | `/api/eventos/{id}` | Eliminar evento |
| GET | `/api/eventos/imagen/{nombre}` | Servir imagen de evento |

#### Comunidades
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/comunidades` | Lista todas las comunidades |
| GET | `/api/comunidades/{id}` | Obtener comunidad por ID |
| GET | `/api/comunidades/estado?estado=` | Buscar por estado |
| POST | `/api/comunidades` | Crear comunidad |
| POST | `/api/comunidades/con-imagen` | Crear comunidad con imagen (multipart) |
| PUT | `/api/comunidades/{id}` | Actualizar comunidad |
| PUT | `/api/comunidades/{id}/con-imagen` | Actualizar comunidad con imagen (multipart) |
| PATCH | `/api/comunidades/{id}/en-revision` | Enviar a revisión |
| PATCH | `/api/comunidades/{id}/revisar` | Revisar comunidad |
| PATCH | `/api/comunidades/{id}/aprobar` | Aprobar comunidad |
| PATCH | `/api/comunidades/{id}/cancelar` | Cancelar comunidad |
| DELETE | `/api/comunidades/{id}` | Eliminar comunidad |
| GET | `/api/comunidades/imagen/{nombre}` | Servir imagen de comunidad |

#### Productos
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/productos` | Lista todos los productos |
| GET | `/api/productos/{id}` | Obtener producto por ID |
| GET | `/api/productos/buscar?nombre=` | Buscar por nombre |
| POST | `/api/productos` | Crear producto |
| POST | `/api/productos/con-imagen` | Crear producto con imagen (multipart) |
| PUT | `/api/productos/{id}` | Actualizar producto |
| PUT | `/api/productos/{id}/con-imagen` | Actualizar producto con imagen (multipart) |
| DELETE | `/api/productos/{id}` | Eliminar producto |
| GET | `/api/productos/imagen/{nombre}` | Servir imagen de producto |

### URLs de imagen

La API devuelve URLs completas para las imágenes (configurado mediante `app.base.url`). Por ejemplo:

```
http://localhost:9000/api/usuarios/imagen/ic-hombre.png
http://localhost:9000/api/eventos/imagen/carrera.jpg
http://localhost:9000/api/comunidades/imagen/deportes.jpg
http://localhost:9000/api/productos/imagen/cuerda.jpg
```

El cliente móvil traduce `localhost:9000` a `10.0.2.2:9000` para el emulador Android.

### Configuración de imagen

- Las imágenes se almacenan en `src/main/resources/static/imagenes/{entidad}/`
- Se sirven a través de endpoints dedicados `GET /api/{entidad}/imagen/{nombre}`
- En las respuestas JSON, el campo `imagen` contiene la URL completa
- Los métodos `con-imagen` aceptan `multipart/form-data` con un campo JSON y un archivo opcional

## Arquitectura Mobile (offline-first)

La app Android sigue un patrón **offline-first**:

```
UI (Compose) → ViewModel → UseCase → Repository → Room (local)
                                                ↘ API (remota)
```

1. Los repositorios intentan obtener datos de la API
2. Si tienen éxito, guardan la respuesta en Room
3. Los ViewModels observan `Flow` de Room (caché local)
4. En caso de error de red, los datos locales están disponibles inmediatamente

### Capas

- **data/remote**: Retrofit services + DTOs + AuthInterceptor (Bearer token)
- **data/local**: Room database con DAOs y entities
- **data/repository**: Repositorios que combinan API + Room
- **domain/model**: Modelos de dominio limpios (sin dependencias de frameworks)
- **domain/usecase**: Casos de uso (login, register, listar, etc.)
- **ui/feature**: Pantallas con Jetpack Compose + ViewModels

## Datos de semilla

Al iniciar la API con la base de datos vacía, se crean automáticamente:

| Usuarios | Email | Contraseña |
|---|---|---|
| Juan García | juan@email.com | 1234 |
| María López | maria@email.com | 1234 |
| Carlos Martínez | carlos@email.com | 1234 |
| Ana Rodríguez | ana@email.com | 1234 |
| Pedro Sánchez | pedro@email.com | 1234 |
| Laura Fernández | laura@email.com | 1234 |

## Despliegue con Docker (API)

```bash
cd EcoQuestAPI
docker build -t ecoquest-api .
docker run -p 9000:9000 -e SPRING_PROFILES_ACTIVE=prod ecoquest-api
```

### Variables de entorno (producción)

| Variable | Descripción | Valor por defecto |
|---|---|---|
| `SPRING_PROFILES_ACTIVE` | Perfil activo | `dev` |
| `SPRING_DATASOURCE_URL` | URL de base de datos | `jdbc:h2:mem:ecoquest` |
| `SPRING_DATASOURCE_USERNAME` | Usuario BD | `sa` |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña BD | (vacío) |
| `BASE_URL` | URL base para imágenes | `http://localhost:9000` |

## Funcionalidades principales

- **Usuarios**: registro, inicio de sesión JWT, perfiles con imagen, bloqueo/desbloqueo
- **Comunidades**: creación con imagen, edición, moderación (pendiente → revisión → activa/cancelada), búsqueda por estado
- **Eventos**: creación con imagen, edición, registro de asistentes, cambio de estado (revisión, activo, cancelado, finalizado)
- **Tienda (accesorios)**: catálogo de productos con imágenes
- **Tema oscuro/claro** seleccionable por el usuario (mobile)
- **Persistencia local** con Room (mobile, offline-first) y fallback a destructivo en cambios de versión

## Licencia

Proyecto educativo sin licencia específica.
