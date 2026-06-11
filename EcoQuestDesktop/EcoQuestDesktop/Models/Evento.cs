using CommunityToolkit.Mvvm.ComponentModel;
using Newtonsoft.Json;
using System;
using System.Runtime.Serialization;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using EcoQuestDesktop.Properties;

namespace EcoQuestDesktop.Models
{
    [DataContract]
    public partial class Evento : ObservableObject
    {
        private static readonly Random _random = new();
        private static readonly string[] _coloresHex =
        {
            "#FF6B6B", "#4ECDC4", "#45B7D1", "#96CEB4", "#F7DC6F",
            "#DDA0DD", "#98D8C8", "#F39C12", "#BB8FCE", "#85C1E9",
            "#E67E22", "#2ECC71", "#E74C3C", "#3498DB", "#1ABC9C"
        };

        [ObservableProperty]
        [JsonProperty("id")]
        [DataMember]
        private int _eventoId;

        [ObservableProperty]
        [JsonProperty("nombre")]
        [DataMember]
        private string _nombre;

        [ObservableProperty]
        [JsonProperty("ubicacion")]
        [DataMember]
        private string _ubicacion;

        [ObservableProperty]
        [JsonProperty("descripcion")]
        [DataMember]
        private string _descripcion;

        [ObservableProperty]
        [JsonProperty("imagen")]
        [DataMember]
        private string _imagen;

        [ObservableProperty]
        [JsonProperty("fechaHora")]
        [DataMember]
        private string _fechaHora;

        [ObservableProperty]
        [JsonProperty("estado")]
        [DataMember]
        private string _estado;

        [ObservableProperty]
        [JsonProperty("nombreComunidad")]
        [DataMember]
        private string _nombreComunidad = string.Empty;

        [ObservableProperty]
        [JsonProperty("imagenComunidad")]
        [DataMember]
        private string _imagenComunidad = string.Empty;

        [JsonIgnore]
        public string NombreComunidadMostrar => string.IsNullOrEmpty(NombreComunidad) ? "Administración EcoQuest" : NombreComunidad;

        [JsonIgnore]
        public ImageSource UrlImagen => CrearImagen(Imagen, "/Assets/mundo.png", "eventos");

        [JsonIgnore]
        public ImageSource UrlImagenComunidad => CrearImagen(ImagenComunidad, "/Assets/mundo.png", "comunidades");

        private static ImageSource CrearImagen(string? valor, string fallback, string ruta)
        {
            if (string.IsNullOrEmpty(valor) || valor.StartsWith("/Assets/"))
            {
                return new BitmapImage(new Uri(valor ?? fallback, UriKind.RelativeOrAbsolute));
            }

            if (valor.StartsWith("http://") || valor.StartsWith("https://"))
            {
                var bitmap = new BitmapImage();
                bitmap.BeginInit();
                bitmap.UriSource = new Uri(valor, UriKind.Absolute);
                bitmap.CacheOption = BitmapCacheOption.OnLoad;
                bitmap.EndInit();
                return bitmap;
            }

            var url = $"{Settings.Default.ApiRestEndPoint.TrimEnd('/')}/{ruta}/imagen/{valor}";
            var bitmap2 = new BitmapImage();
            bitmap2.BeginInit();
            bitmap2.UriSource = new Uri(url, UriKind.Absolute);
            bitmap2.CacheOption = BitmapCacheOption.OnLoad;
            bitmap2.EndInit();
            return bitmap2;
        }

        [JsonIgnore]
        public SolidColorBrush ColorBorde { get; } = new((Color)ColorConverter.ConvertFromString(_coloresHex[_random.Next(_coloresHex.Length)]));

        [JsonIgnore]
        public DateTime Fecha => DateTime.TryParse(FechaHora, out var dt) ? dt : DateTime.MinValue;

        partial void OnImagenChanged(string value)
        {
            OnPropertyChanged(nameof(UrlImagen));
        }

        partial void OnImagenComunidadChanged(string value)
        {
            OnPropertyChanged(nameof(UrlImagenComunidad));
        }

        partial void OnNombreComunidadChanged(string value)
        {
            OnPropertyChanged(nameof(NombreComunidadMostrar));
        }

        partial void OnFechaHoraChanged(string value)
        {
            OnPropertyChanged(nameof(Fecha));
        }

        public Evento()
        {
            _ = ColorBorde;
        }

        public Evento(int eventoId, string nombre, string ubicacion, string descripcion, string imagen, string fechaHora) : this()
        {
            EventoId = eventoId;
            Nombre = nombre;
            Ubicacion = ubicacion;
            Descripcion = descripcion;
            Imagen = imagen;
            FechaHora = fechaHora;
        }

        public Evento(int eventoId, string nombre, string ubicacion, string descripcion, string imagen, string fechaHora, string nombreComunidad, string imagenComunidad) : this(eventoId, nombre, ubicacion, descripcion, imagen, fechaHora)
        {
            NombreComunidad = nombreComunidad;
            ImagenComunidad = imagenComunidad;
        }
    }
}
