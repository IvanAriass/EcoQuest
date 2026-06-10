using CommunityToolkit.Mvvm.ComponentModel;
using Newtonsoft.Json;
using PrototipadoEscritorio.Properties;
using System;
using System.Collections.Generic;
using System.Windows.Media;
using System.Windows.Media.Imaging;

namespace PrototipadoEscritorio.Models
{
    public partial class Comunidad : ObservableObject
    {
        [ObservableProperty]
        [JsonProperty("id")]
        private int _comunidadId;

        [ObservableProperty]
        [JsonProperty("nombre")]
        private string _nombre = string.Empty;

        [ObservableProperty]
        [JsonProperty("descripcion")]
        private string _descripcion = string.Empty;

        [ObservableProperty]
        [JsonProperty("imagen")]
        private string _imagen = string.Empty;

        [ObservableProperty]
        [JsonProperty("estado")]
        private string _estado = string.Empty;

        [ObservableProperty]
        [JsonProperty("motivoCancelacion")]
        private string _motivoCancelacion = string.Empty;

        [ObservableProperty]
        [JsonProperty("roles")]
        private string _roles = string.Empty;

        [ObservableProperty]
        [JsonProperty("usuarios")]
        private List<UsuarioResumen> _usuarios = new();

        [JsonIgnore]
        public ImageSource UrlImagen
        {
            get
            {
                if (string.IsNullOrEmpty(Imagen) || Imagen.StartsWith("/Assets/"))
                {
                    var fallback = string.IsNullOrEmpty(Imagen) ? "/Assets/mundo.png" : Imagen;
                    return new BitmapImage(new Uri(fallback, UriKind.RelativeOrAbsolute));
                }

                var url = $"{Settings.Default.ApiRestEndPoint.TrimEnd('/')}/comunidades/imagen/{Imagen}";
                var bitmap = new BitmapImage();
                bitmap.BeginInit();
                bitmap.UriSource = new Uri(url, UriKind.Absolute);
                bitmap.CacheOption = BitmapCacheOption.OnLoad;
                bitmap.EndInit();
                return bitmap;
            }
        }

        partial void OnImagenChanged(string value)
        {
            OnPropertyChanged(nameof(UrlImagen));
        }

        public Comunidad() { }

        public Comunidad(int comunidadId, string nombre, string imagen)
        {
            ComunidadId = comunidadId;
            Nombre = nombre;
            Imagen = imagen;
        }

        public Comunidad(int comunidadId, string nombre, string descripcion, string imagen)
        {
            ComunidadId = comunidadId;
            Nombre = nombre;
            Descripcion = descripcion;
            Imagen = imagen;
        }
    }
}
