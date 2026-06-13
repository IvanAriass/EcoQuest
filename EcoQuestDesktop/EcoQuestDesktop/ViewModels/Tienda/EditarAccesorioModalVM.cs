using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using Microsoft.Win32;
using EcoQuestDesktop.Models;
using EcoQuestDesktop.Services;
using System.Collections.ObjectModel;
using System.IO;
using System.Windows.Media.Imaging;

namespace EcoQuestDesktop.ViewModels.Tienda
{
    public partial class EditarAccesorioModalVM : ObservableObject
    {
        public event Action? OnGuardar;

        private Producto? _productoOriginal;

        [ObservableProperty]
        private string _nombreOriginal = string.Empty;

        [ObservableProperty]
        private string _nombre = string.Empty;

        [ObservableProperty]
        private int _precio;

        [ObservableProperty]
        private Categoria? _categoriaSeleccionada;

        [ObservableProperty]
        private string _nombreArchivo = string.Empty;

        [ObservableProperty]
        private BitmapImage? _imagen;

        [ObservableProperty]
        private string _rutaImagenCompleta = string.Empty;

        [ObservableProperty]
        private bool _isAddingCategoria;

        [ObservableProperty]
        private string _nuevaCategoriaNombre = string.Empty;

        public ObservableCollection<Categoria> Categorias { get; } = new();

        public EditarAccesorioModalVM()
        {
            CargarCategorias();
        }

        [RelayCommand]
        private void MostrarNuevaCategoria()
        {
            NuevaCategoriaNombre = string.Empty;
            IsAddingCategoria = true;
        }

        [RelayCommand]
        private async Task AgregarCategoria()
        {
            var nombre = NuevaCategoriaNombre?.Trim();
            if (string.IsNullOrWhiteSpace(nombre)) return;

            var nueva = await ApiRestService.CrearCategoria(nombre);
            if (nueva != null)
            {
                Categorias.Add(nueva);
                CategoriaSeleccionada = nueva;
                NuevaCategoriaNombre = string.Empty;
                IsAddingCategoria = false;
            }
            else
            {
                System.Windows.MessageBox.Show("Error al crear la categoría", "Error",
                    System.Windows.MessageBoxButton.OK, System.Windows.MessageBoxImage.Error);
            }
        }

        [RelayCommand]
        private void CancelarNuevaCategoria()
        {
            NuevaCategoriaNombre = string.Empty;
            IsAddingCategoria = false;
        }

        private void CargarCategorias()
        {
            var categorias = ApiRestService.GetCategorias();
            Categorias.Clear();
            foreach (var cat in categorias)
                Categorias.Add(cat);
        }

        public void CargarProducto(Producto producto)
        {
            _productoOriginal = producto;
            NombreOriginal = producto.Nombre;
            Nombre = producto.Nombre;
            Precio = producto.Precio;
            CategoriaSeleccionada = producto.Categoria != null
                ? Categorias.FirstOrDefault(c => c.Id == producto.Categoria.Id)
                : null;
            RutaImagenCompleta = producto.Imagen ?? string.Empty;

            if (!string.IsNullOrEmpty(producto.Imagen))
            {
                NombreArchivo = Path.GetFileName(producto.Imagen);
                try
                {
                    var bmp = new BitmapImage();
                    bmp.BeginInit();
                    bmp.UriSource = new Uri(producto.Imagen, UriKind.RelativeOrAbsolute);
                    bmp.CacheOption = BitmapCacheOption.OnLoad;
                    bmp.EndInit();
                    Imagen = bmp;
                }
                catch
                {
                    Imagen = null;
                }
            }
            else
            {
                NombreArchivo = string.Empty;
                Imagen = null;
            }
        }

        [RelayCommand]
        private void SeleccionarImagen()
        {
            var dialog = new OpenFileDialog
            {
                Title = "Seleccionar imagen del accesorio",
                Filter = "Imágenes|*.png;*.jpg;*.jpeg;*.bmp;*.gif;*.webp",
                Multiselect = false
            };

            if (dialog.ShowDialog() != true) return;

            RutaImagenCompleta = dialog.FileName;
            NombreArchivo = Path.GetFileName(dialog.FileName);

            try
            {
                var bmp = new BitmapImage();
                bmp.BeginInit();
                bmp.UriSource = new Uri(dialog.FileName, UriKind.Absolute);
                bmp.CacheOption = BitmapCacheOption.OnLoad;
                bmp.EndInit();
                Imagen = bmp;
            }
            catch
            {
                Imagen = null;
            }
        }

        [RelayCommand]
        private async Task Guardar()
        {
            if (_productoOriginal == null) return;

            string? rutaLocal = File.Exists(RutaImagenCompleta) ? RutaImagenCompleta : null;

            var productoActualizado = await ApiRestService.EditarProductoConImagen(
                _productoOriginal.Id,
                Nombre,
                Precio,
                rutaLocal,
                CategoriaSeleccionada?.Id
            );

            if (productoActualizado != null)
            {
                _productoOriginal.Nombre = productoActualizado.Nombre;
                _productoOriginal.Precio = productoActualizado.Precio;
                _productoOriginal.Categoria = productoActualizado.Categoria;

                OnGuardar?.Invoke();
            }
            else
            {
                System.Windows.MessageBox.Show("Error al guardar el producto.", "Error",
                    System.Windows.MessageBoxButton.OK, System.Windows.MessageBoxImage.Error);
            }
        }
    }
}
