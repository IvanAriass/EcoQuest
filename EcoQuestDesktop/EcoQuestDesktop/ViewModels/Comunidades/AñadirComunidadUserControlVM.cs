using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using Microsoft.Win32;
using EcoQuestDesktop.Messages;
using EcoQuestDesktop.Services;
using System;
using System.Collections.ObjectModel;
using System.Linq;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Media.Imaging;

namespace EcoQuestDesktop.ViewModels.Comunidades
{
    public partial class AñadirComunidadUserControlVM : ObservableObject
    {
        [ObservableProperty]
        private string _nombre = string.Empty;

        [ObservableProperty]
        private string _descripcion = string.Empty;

        [ObservableProperty]
        private string? _rutaImagenLocal;

        [ObservableProperty]
        private BitmapImage? _previsualizacion;

        [ObservableProperty]
        private string _nuevoRol = string.Empty;

        public ObservableCollection<string> RolesDisponibles { get; } = new();

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
        private void AgregarRol()
        {
            var rol = NuevoRol?.Trim().ToUpperInvariant();
            if (string.IsNullOrWhiteSpace(rol)) return;

            if (!RolesDisponibles.Contains(rol, StringComparer.OrdinalIgnoreCase))
                RolesDisponibles.Add(rol);

            NuevoRol = string.Empty;
        }

        [RelayCommand]
        private void QuitarRol(string rol)
        {
            RolesDisponibles.Remove(rol);
        }

        [RelayCommand]
        private async Task Guardar()
        {
            try
            {
                if (string.IsNullOrWhiteSpace(Nombre))
                {
                    MessageBox.Show("Rellena los campos obligatorios", "Aviso",
                        MessageBoxButton.OK, MessageBoxImage.Warning);
                    return;
                }

                var rolesStr = string.Join(",", RolesDisponibles);
                var resultado = await ApiRestService.CrearComunidad(Nombre.Trim(), Descripcion?.Trim() ?? "", rolesStr, RutaImagenLocal);
                if (resultado)
                {
                    WeakReferenceMessenger.Default.Send(new ComunidadAñadidaMessage());
                    MessageBox.Show("Comunidad añadida correctamente", "Éxito",
                        MessageBoxButton.OK, MessageBoxImage.Information);
                    LimpiarFormulario();
                }
                else
                {
                    MessageBox.Show("Error al guardar la comunidad", "Error",
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
            Descripcion = string.Empty;
            RutaImagenLocal = null;
            Previsualizacion = null;
            RolesDisponibles.Clear();
            NuevoRol = string.Empty;
        }
    }
}
