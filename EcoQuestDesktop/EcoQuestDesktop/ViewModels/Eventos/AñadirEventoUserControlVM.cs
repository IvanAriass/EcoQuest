using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using Microsoft.Win32;
using EcoQuestDesktop.Messages;
using EcoQuestDesktop.Services;
using System;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Media.Imaging;

namespace EcoQuestDesktop.ViewModels.Eventos
{
    public partial class AñadirEventoUserControlVM : ObservableObject
    {
        [ObservableProperty]
        private string _nombre = string.Empty;

        [ObservableProperty]
        private string _ubicacion = string.Empty;

        [ObservableProperty]
        private string _descripcion = string.Empty;

        [ObservableProperty]
        private DateTime? _fecha;

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
                if (string.IsNullOrWhiteSpace(Nombre) || string.IsNullOrWhiteSpace(Ubicacion))
                {
                    MessageBox.Show("Rellena los campos obligatorios", "Aviso",
                        MessageBoxButton.OK, MessageBoxImage.Warning);
                    return;
                }

                var fechaHora = Fecha?.ToString("o") ?? DateTime.Now.ToString("o");

                var creado = await ApiRestService.CrearEvento(
                    Nombre,
                    Descripcion,
                    Ubicacion,
                    fechaHora,
                    RutaImagenLocal
                );

                if (creado != null)
                {
                    WeakReferenceMessenger.Default.Send(new EventoAñadidoMessage());
                    MessageBox.Show("Evento añadido correctamente", "Éxito",
                        MessageBoxButton.OK, MessageBoxImage.Information);
                    LimpiarFormulario();
                }
                else
                {
                    MessageBox.Show("Error al guardar el evento", "Error",
                        MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Excepción: {ex.Message}", "Error",
                    MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void LimpiarFormulario()
        {
            Nombre = string.Empty;
            Ubicacion = string.Empty;
            Descripcion = string.Empty;
            Fecha = null;
            RutaImagenLocal = null;
            Previsualizacion = null;
        }
    }
}
