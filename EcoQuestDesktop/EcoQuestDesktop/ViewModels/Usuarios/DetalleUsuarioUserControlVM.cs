using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using EcoQuestDesktop.Messages;
using EcoQuestDesktop.Models;
using EcoQuestDesktop.Properties;
using System;
using System.Windows.Media;
using System.Windows.Media.Imaging;

namespace EcoQuestDesktop.ViewModels.Usuarios
{
    public partial class DetalleUsuarioUserControlVM : ObservableObject
    {
        [ObservableProperty]
        private Usuario _usuario = new();

        public string NombreMostrar => $"{Usuario.Nombre} {Usuario.Apellido}".Trim();

        public string EdadMostrar => $"{Usuario.Edad} años";

        [RelayCommand]
        private void Cerrar()
        {
            WeakReferenceMessenger.Default.Send(new CerrarDetalleUsuarioMessage(true));
        }

        public ImageSource UrlAvatar
        {
            get
            {
                if (string.IsNullOrEmpty(Usuario.Imagen))
                    return new BitmapImage(new Uri("/Assets/mundo.png", UriKind.RelativeOrAbsolute));

                if (Usuario.Imagen.StartsWith("/Assets/") || Usuario.Imagen.StartsWith("http"))
                    return new BitmapImage(new Uri(Usuario.Imagen, UriKind.RelativeOrAbsolute));

                var url = $"{Settings.Default.ApiRestEndPoint.TrimEnd('/')}/usuarios/imagen/{Usuario.Imagen}";
                var bitmap = new BitmapImage();
                bitmap.BeginInit();
                bitmap.UriSource = new Uri(url, UriKind.Absolute);
                bitmap.CacheOption = BitmapCacheOption.OnLoad;
                bitmap.EndInit();
                return bitmap;
            }
        }

        partial void OnUsuarioChanged(Usuario value)
        {
            OnPropertyChanged(nameof(NombreMostrar));
            OnPropertyChanged(nameof(EdadMostrar));
            OnPropertyChanged(nameof(UrlAvatar));
        }
    }
}
