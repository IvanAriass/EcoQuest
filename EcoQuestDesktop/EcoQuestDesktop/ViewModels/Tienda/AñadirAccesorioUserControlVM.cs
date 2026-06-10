using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using Microsoft.Win32;
using PrototipadoEscritorio.Messages;
using PrototipadoEscritorio.Services;
using RestSharp;
using System.IO;
using System.Windows;
using System.Windows.Media.Imaging;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace PrototipadoEscritorio.ViewModels.Tienda
{
    public partial class AñadirAccesorioUserControlVM : ObservableObject
    {
        [ObservableProperty]
        private string _nombre = string.Empty;

        [ObservableProperty]
        private int _precio;

        [ObservableProperty]
        private string? _rutaImagenLocal;      

        [ObservableProperty]
        private BitmapImage? _previsualizacion;

        [RelayCommand]
        private void SeleccionarImagen()
        {
            var dialog = new OpenFileDialog
            {
                Filter = "Imágenes|*.jpg;*.jpeg;*.png;*.gif;*.webp;*.bmp",
                Title = "Seleccionar imagen"
            };

            if (dialog.ShowDialog() == true)
            {
                RutaImagenLocal = dialog.FileName;

                var bmp = new BitmapImage();
                bmp.BeginInit();
                bmp.UriSource = new Uri(RutaImagenLocal);
                bmp.CacheOption = BitmapCacheOption.OnLoad;
                bmp.EndInit();
                Previsualizacion = bmp;
            }
        }

        [RelayCommand]
        private async Task Guardar()
        {
            try
            {
                var producto = await ApiRestService.CrearProductoConImagen(Nombre, Precio, RutaImagenLocal);

                if (producto != null)
                {
                    WeakReferenceMessenger.Default.Send(new AccesorioAñadidoMessage());
                    MessageBox.Show("Accesorio añadido correctamente", "Éxito", MessageBoxButton.OK, MessageBoxImage.Information);
                    Nombre = string.Empty;
                    Precio = 0;
                    RutaImagenLocal = null;
                    Previsualizacion = null;
                }
                else
                {
                    MessageBox.Show("Error al guardar", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Excepción: {ex.Message}", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
    }
}