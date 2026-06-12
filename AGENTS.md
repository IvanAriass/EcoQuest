# EcoQuest — Agent instructions

## Monorepo layout

Three independent sub-projects under one root:

| Sub-project | Language / Stack | Entrypoint |
|---|---|---|
| `EcoQuestAPI/` | Java 21 + Spring Boot 3.4, Maven, WAR packaging | `src/main/java/com/proyecto/spring/` |
| `EcoQuestDesktop/` | C# .NET 9 + WPF (MVVM Toolkit) | `EcoQuestDesktop.sln` |
| `EcoQuestMobile/` | Kotlin 2.0 + Jetpack Compose + Hilt | `app/src/main/java/com/ecoquest/app/` |

## Commands

### API
- Run (dev, H2): `cd EcoQuestAPI && mvn spring-boot:run -Dspring-boot.run.profiles=dev`
- Run (prod, PostgreSQL): `cd EcoQuestAPI && mvn spring-boot:run -Dspring-boot.run.profiles=prod`
- Package: `mvn package -DskipTests` → produces `target/aplicacion-0.0.1-SNAPSHOT.war`
- Test: `mvn test` (single smoke test — `contextLoads`)
- Docker: `docker build -t ecoquest-api . && docker run -p 9000:9000 -e SPRING_PROFILES_ACTIVE=prod ecoquest-api`

### Desktop
- Run: `cd EcoQuestDesktop && dotnet run`
- Open with Visual Studio: `EcoQuestDesktop.sln`

### Mobile
- Open `EcoQuestMobile/` in Android Studio, run on emulator
- No CLI build command (Gradle wrapper available: `gradlew`)

## Architecture notes

- API always starts on port **9000** (`server.port=9000`). Dev profile uses H2 file DB (`jdbc:h2:./ecoquest`), prod uses PostgreSQL.
- Seed data (6 users, 8 communities, 7 events, 6 products) inserted on startup by CommandLineRunner when DB is empty. H2 console at `/h2-console`. Dev DB credentials: `admin` / `1234`.
- JWT auth is implemented but **not enforced** — `SecurityConfig` uses `anyRequest().permitAll()`. Desktop client does not send tokens; mobile sends Bearer token via `AuthInterceptor`.
- Mobile app points to `http://10.0.2.2:9000/api/` (Android emulator loopback). Image URLs with `localhost:9000` are rewritten to `10.0.2.2:9000` on the mobile side.
- Desktop API connection configured in `Properties/Settings.settings` (`ApiRestEndPoint`). No auth required.
- Desktop uses RestSharp + Newtonsoft.Json for HTTP. API upload methods (`*-con-imagen`) accept `multipart/form-data`.
- Images: stored in `src/main/resources/static/imagenes/{entity}/`, served via `GET /api/{entity}/imagen/{nombre}`.

## Conventions

- **API**: Lombok (`@Data`, `@Builder`, etc.), Spring Data JPA repositories, `@RestController` for endpoints.
- **Desktop**: MVVM Toolkit — `[ObservableProperty]` for reactive properties, `[RelayCommand]` for commands, `WeakReferenceMessenger` for inter-VM communication, overlay+modal pattern (never `Window.ShowDialog()`). Styles in `Styles/EstilosGeneral.xaml`. See `COPILOT_INSTRUCTIONS.md` for exhaustive WPF rules.
- **Mobile**: offline-first with Room (local) → Retrofit (remote), `Flow`-based ViewModels, Hilt DI, Jetpack Navigation Compose. Room uses `fallbackToDestructiveMigration()` on version changes.
- All projects are in Spanish (entity/class names, endpoint paths, comments).

## Test quirks

Only one test exists — `AplicacionApplicationTests.contextLoads()`. No mobile or desktop test suites.
