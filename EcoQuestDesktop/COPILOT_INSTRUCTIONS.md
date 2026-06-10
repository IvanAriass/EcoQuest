# 🧠 Instrucciones de Copilot para el Proyecto PrototipadoEscritorio

## 📋 Información del Proyecto
- **Tecnología**: WPF (Windows Presentation Foundation)
- **Framework**: .NET 9
- **Arquitectura**: MVVM (Model-View-ViewModel) con MVVM Toolkit
- **Base de Datos**: API REST (Backend Java/Spring Boot)
- **Control de Versiones**: Git (rama `ivan`)

---

## ✅ DEBES HACER

### 1. **Patrones Arquitectónicos**
- ✅ Usar MVVM Toolkit (CommunityToolkit.Mvvm) para ViewModels
- ✅ Usar `[ObservableProperty]` para propiedades reactivas
- ✅ Usar `[RelayCommand]` para comandos
- ✅ Usar `WeakReferenceMessenger` para comunicación entre ViewModels
- ✅ Implementar `IRecipient<>` para recibir mensajes

### 2. **Estructura de Carpetas**
```
PrototipadoEscritorio/
├── Views/
│   ├── MainWindow.xaml
│   └── UserControl/
│       ├── Tienda/
│       ├── Comunidades/
│       ├── Eventos/
│       └── Usuarios/
├── ViewModels/
│   ├── MainWindowVM.cs
│   ├── Tienda/
│   ├── Comunidades/
│   ├── Eventos/
│   └── Usuarios/
├── Models/
│   └── Producto.cs, Comunidad.cs, etc.
├── Services/
│   ├── ApiRestService.cs
│   └── NavegacionService.cs
├── Styles/
│   └── EstilosGeneral.xaml
└── Messages/
    └── AccesorioAñadidoMessage.cs
```

### 3. **Patrones de Modales**
- ✅ **Pattern A (Recomendado)**: Overlay + Modal con Grid
  - El overlay es un botón transparente que cierra el modal
  - El modal se centra con HorizontalAlignment y VerticalAlignment
  - ✅ **Ejemplo**: ListadoAccesoriosUserControl.xaml

- ✅ **Componentes requeridos**:
  - ViewModel padre con propiedad `ModalVisible` (bool)
  - ViewModel modal con evento `OnAccion` (ej: OnGuardar, OnConfirmar)
  - Método en ViewModel padre para cargar datos en el modal (ej: `CargarProducto()`, `CargarConfirmacion()`)

### 4. **Integración de Modales**
- ✅ Paso 1: Crear propiedad `[ObservableProperty] private bool _modalVisible = false;`
- ✅ Paso 2: Crear propiedad `[ObservableProperty] private ModalVM _modalVM = new();`
- ✅ Paso 3: Suscribirse a eventos del modal en constructor
- ✅ Paso 4: Crear comando `AbrirXXX(Objeto item)` para mostrar el modal
- ✅ Paso 5: Crear comando `CerrarModal()` para cerrar
- ✅ Paso 6: En XAML, agregar Grid con overlay + modal

### 5. **Estilos y Colores**
- ✅ Usar colores definidos en `EstilosGeneral.xaml`
- ✅ Estilos consistentes:
  - Verde: `#2E7D32` (edición)
  - Rojo: `#AF4C4E` (eliminación)
  - Negro: `#000000` (cancelación)
  - Gris: `#F5F5F5` (fondo de inputs)
- ✅ Botones con hover y pressed states en ControlTemplate

### 6. **DataBinding**
- ✅ Usar `RelativeSource="{RelativeSource AncestorType=UserControl}"` para acceder al padre
- ✅ Usar `UpdateSourceTrigger=PropertyChanged` en búsquedas
- ✅ Usar conversores como `BooleanToVisibilityConverter`
- ✅ Castear en la DataTemplate (ej: `{Binding}` es el item)

### 7. **Servicios API**
- ✅ Usar `RestSharp` para llamadas HTTP
- ✅ Usar `Newtonsoft.Json` para serialización
- ✅ Métodos síncronos: `GetProductos()`, `BuscarProductosPorNombre()`
- ✅ Métodos asíncronos: `async Task<T> CrearProductoConImagen()`, `EditarProductoConImagen()`
- ✅ Métodos DELETE: `EliminarProducto(int id)`

### 8. **Nomenclatura**
- ✅ ViewModels: `NombreDelControVieModel` o `NombreDelControl + "VM"` (ej: `ListadoAccesoriosVM.cs`)
- ✅ UserControls: `NombreDelControl + "UserControl.xaml"` (ej: `ListadoAccesoriosUserControl.xaml`)
- ✅ Comandos: `NombreAccionCommand` (ej: `EliminarAccesorioCommand`)
- ✅ Eventos: `OnAccion` (ej: `OnGuardar`, `OnConfirmar`)
- ✅ Propiedades privadas: `_nombreEnCamelCase`
- ✅ Propiedades públicas: `NombreEnPascalCase`

### 9. **Mensajes (Messenger)**
- ✅ Crear en carpeta `Messages/`
- ✅ Nombre: `NombreAccionMessage.cs`
- ✅ Heredar de clase base si aplica
- ✅ Registrar con `WeakReferenceMessenger.Default.Register(this)`

### 10. **Navidad de Vistas**
- ✅ Usar `NavegacionService` para cambiar entre vistas
- ✅ Métodos: `ObtenerVentanaXXX()` retorna `UserControl`
- ✅ Actualizar propiedad `ContenidoVentana` en ViewModel padre

### 11. **Validaciones**
- ✅ Verificar `null` antes de operar
- ✅ Usar try-catch en operaciones críticas (carga de imágenes, API)
- ✅ Mostrar `MessageBox` para errores al usuario

---

## ❌ NO DEBES HACER

### 1. **Patrones ANTIGUOS**
- ❌ NO usar `INotifyPropertyChanged` directamente (usar `ObservableObject`)
- ❌ NO usar `PropertyChanged?.Invoke()` (usar `[ObservableProperty]`)
- ❌ NO usar `ICommand` directamente (usar `[RelayCommand]`)
- ❌ NO crear métodos privados `OnPropertyNameChanged()` (MVVM Toolkit maneja esto)

### 2. **Code-Behind**
- ❌ NO escribir lógica de negocio en code-behind (.xaml.cs)
- ❌ Solo inicialización de componentes (`InitializeComponent()`)
- ❌ Excepciones: Diálogos de archivo (OpenFileDialog, SaveFileDialog)

### 3. **Binding Incorrecto**
- ❌ NO usar `Binding` sin especificar el Path
- ❌ NO olvidar `Converter` para tipos incompatibles
- ❌ NO usar `ElementName` cuando `RelativeSource` sea más apropiado
- ❌ NO bindear eventos directamente (usar Commanding)

### 4. **Modales Incorrectos**
- ❌ NO mostrar modales sin overlay
- ❌ NO usar `Window.ShowDialog()` (mantener todo en UserControls)
- ❌ NO cerrar modal directamente desde el ViewModel modal (usar eventos)
- ❌ NO mezclar lógica de negocio con lógica de UI

### 5. **Estilos**
- ❌ NO crear estilos inline sin Template (usar ControlTemplate cuando sea necesario)
- ❌ NO hardcodear colores (usar colores de `EstilosGeneral.xaml`)
- ❌ NO aplicar estilos sin `TargetType`

### 6. **Gestión de Recursos**
- ❌ NO olvidar `BitmapCacheOption.OnLoad` al cargar imágenes
- ❌ NO olvidar `bmp.EndInit()` después de configurar BitmapImage
- ❌ NO dejar conexiones abiertas a la API

### 7. **Nombres y Convenciones**
- ❌ NO mezclar camelCase y PascalCase
- ❌ NO usar nombres genéricos (`vm1`, `button1`, `grid2`)
- ❌ NO abreviar nombres sin sentido (`prod` en lugar de `producto`)

### 8. **Errores Comunes**
- ❌ NO olvidar `null` checks antes de castear
- ❌ NO usar `string.Empty` sin comprobar null primero
- ❌ NO mezclar operaciones síncronas y asíncronas sin `await`
- ❌ NO modificar colecciones desde threads distintos del UI

### 9. **Configuración de Proyecto**
- ❌ NO cambiar Target Framework sin ajustar dependencias
- ❌ NO agregar referencias NuGet sin revisar versiones
- ❌ NO modificar App.xaml sin respaldar

### 10. **Git y Versionado**
- ❌ NO hacer commit sin compilar
- ❌ NO hacer push a rama principal sin revisar cambios
- ❌ NO agregar archivos compilados (.bin, .obj, .dll)

---

## 🔄 Flujo Típico de Implementación

### Crear una Nueva Funcionalidad de CRUD

1. **Crear el Modelo** (Models/)
2. **Crear el ViewModel** (ViewModels/Seccion/)
   - Propiedades con `[ObservableProperty]`
   - Comandos con `[RelayCommand]`
   - Métodos con lógica
3. **Crear la Vista** (Views/UserControl/Seccion/)
   - ItemsControl o DataGrid
   - Binding a propiedades
   - Botones con Commands
4. **Crear Mensajes** (Messages/) si se necesita comunicación
5. **Actualizar ApiRestService** (Services/)
6. **Agregar métodos de navegación** (NavegacionService)
7. **Compilar y Probar**

---

## 📐 Estructura de un Modal

```csharp
// ViewModel Padre
[ObservableProperty]
private bool _modalVisible = false;

[ObservableProperty]
private ModalVM _modalVM = new();

// En constructor
ModalVM.OnAccion += () =>
{
    ModalVisible = false;
    CargarDatos();
};

[RelayCommand]
private void AbrirModal(Item item)
{
    ModalVM.CargarItem(item);
    ModalVisible = true;
}

[RelayCommand]
private void CerrarModal() => ModalVisible = false;
```

```xaml
<!-- View Padre -->
<Grid Visibility="{Binding ModalVisible, Converter={StaticResource BooleanToVisibilityConverter}}">
    <!-- Overlay -->
    <Button Command="{Binding CerrarModalCommand}" />
    
    <!-- Modal -->
    <local:ModalUserControl DataContext="{Binding ModalVM}" />
</Grid>
```

---

## 🎯 Resumen de Reglas Clave

| Regla | Hacer | NO Hacer |
|-------|-------|---------|
| **Properties** | `[ObservableProperty]` | `INotifyPropertyChanged` |
| **Comandos** | `[RelayCommand]` | `ICommand` |
| **Mensajes** | `WeakReferenceMessenger` | Eventos globales |
| **Modales** | UserControl + Overlay | Window.ShowDialog() |
| **Estilos** | EstilosGeneral.xaml | Inline hardcoded |
| **Code-Behind** | Solo InitializeComponent() | Lógica de negocio |
| **Datos** | ApiRestService | ADO.NET directo |

---

## 📞 Contacto y Dudas

Si encuentras un patrón que no está documentado aquí:
1. Revisa ejemplos similares en el proyecto
2. Consulta las convenciones de MVVM Toolkit
3. Sigue la estructura existente

**Última actualización**: 2024
