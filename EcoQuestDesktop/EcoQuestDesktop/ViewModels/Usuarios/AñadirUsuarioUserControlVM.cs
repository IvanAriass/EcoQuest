using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using Microsoft.Win32;
using EcoQuestDesktop.Messages;
using EcoQuestDesktop.Services;
using System;
using System.IO;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Media.Imaging;

namespace EcoQuestDesktop.ViewModels.Usuarios
{
    public partial class AñadirUsuarioUserControlVM : ObservableObject
    {
        [ObservableProperty]
        private string _nombreUsuario = string.Empty;

        [ObservableProperty]
        private string _nombre = string.Empty;

        [ObservableProperty]
        private string _apellido = string.Empty;

        [ObservableProperty]
        private string _email = string.Empty;

        [ObservableProperty]
        private int _edad;

        [ObservableProperty]
        private string _descripcion = string.Empty;

        [ObservableProperty]
        private string _contraseña = string.Empty;

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
                Title = "Seleccionar imagen de perfil"
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
                var usuario = await ApiRestService.CrearUsuarioConImagen(
                    NombreUsuario, Nombre, Apellido, Email, Edad, Descripcion, Contraseña, RutaImagenLocal);

                if (usuario != null)
                {
                    WeakReferenceMessenger.Default.Send(new UsuarioAñadidoMessage());
                    MessageBox.Show("Usuario añadido correctamente", "Éxito",
                        MessageBoxButton.OK, MessageBoxImage.Information);

                    NombreUsuario = string.Empty;
                    Nombre = string.Empty;
                    Apellido = string.Empty;
                    Email = string.Empty;
                    Edad = 0;
                    Descripcion = string.Empty;
                    Contraseña = string.Empty;
                    RutaImagenLocal = null;
                    Previsualizacion = null;
                }
                else
                {
                    MessageBox.Show("Error al guardar el usuario", "Error",
                        MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Excepción: {ex.Message}", "Error",
                    MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
    }
}
