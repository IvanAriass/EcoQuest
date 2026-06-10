using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using Microsoft.Win32;
using PrototipadoEscritorio.Models;
using PrototipadoEscritorio.Services;
using System.IO;
using System.Windows.Media.Imaging;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace PrototipadoEscritorio.ViewModels.Tienda
{
    public partial class EditarAccesorioModalVM : ObservableObject
    {
        // Evento que avisa al padre (ListadoAccesoriosVM) que se guardó
        public event Action? OnGuardar;

        private Producto? _productoOriginal;


        [ObservableProperty]
        private string _nombreOriginal = string.Empty;  // Solo para el header del modal

        [ObservableProperty]
        private string _nombre = string.Empty;

        [ObservableProperty]
        private int _precio;

        [ObservableProperty]
        private string _nombreArchivo = string.Empty;

        [ObservableProperty]
        private BitmapImage? _imagen;

        [ObservableProperty]
        private string _rutaImagenCompleta = string.Empty;

        public void CargarProducto(Producto producto)
        {
            _productoOriginal = producto;
            NombreOriginal = producto.Nombre;
            Nombre = producto.Nombre;
            Precio = producto.Precio;
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
                rutaLocal
            );

            if (productoActualizado != null)
            {
                _productoOriginal.Nombre = productoActualizado.Nombre;
                _productoOriginal.Precio = productoActualizado.Precio;

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
