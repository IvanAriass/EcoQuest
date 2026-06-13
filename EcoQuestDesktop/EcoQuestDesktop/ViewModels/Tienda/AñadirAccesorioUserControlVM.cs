using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using Microsoft.Win32;
using EcoQuestDesktop.Messages;
using EcoQuestDesktop.Models;
using EcoQuestDesktop.Services;
using System.Collections.ObjectModel;
using System.IO;
using System.Windows;
using System.Windows.Media.Imaging;

namespace EcoQuestDesktop.ViewModels.Tienda
{
    public partial class AñadirAccesorioUserControlVM : ObservableObject
    {
        [ObservableProperty]
        private string _nombre = string.Empty;

        [ObservableProperty]
        private int _precio;

        [ObservableProperty]
        private Categoria? _categoriaSeleccionada;

        [ObservableProperty]
        private string? _rutaImagenLocal;

        [ObservableProperty]
        private BitmapImage? _previsualizacion;

        [ObservableProperty]
        private bool _isAddingCategoria;

        [ObservableProperty]
        private string _nuevaCategoriaNombre = string.Empty;

        public ObservableCollection<Categoria> Categorias { get; } = new();

        public AñadirAccesorioUserControlVM()
        {
            CargarCategorias();
        }

        private void CargarCategorias()
        {
            var categorias = ApiRestService.GetCategorias();
            Categorias.Clear();
            foreach (var cat in categorias)
                Categorias.Add(cat);
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
                MessageBox.Show("Error al crear la categoría", "Error",
                    MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        [RelayCommand]
        private void CancelarNuevaCategoria()
        {
            NuevaCategoriaNombre = string.Empty;
            IsAddingCategoria = false;
        }

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
                var producto = await ApiRestService.CrearProductoConImagen(
                    Nombre, Precio, RutaImagenLocal, CategoriaSeleccionada?.Id);

                if (producto != null)
                {
                    WeakReferenceMessenger.Default.Send(new AccesorioAñadidoMessage());
                    MessageBox.Show("Accesorio añadido correctamente", "Éxito", MessageBoxButton.OK, MessageBoxImage.Information);
                    Nombre = string.Empty;
                    Precio = 0;
                    CategoriaSeleccionada = null;
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