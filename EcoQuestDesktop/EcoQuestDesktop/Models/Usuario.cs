using CommunityToolkit.Mvvm.ComponentModel;
using Newtonsoft.Json;
using System;
using System.Runtime.Serialization;
using System.Windows.Media;

namespace EcoQuestDesktop.Models
{
    [DataContract]
    public partial class Usuario : ObservableObject
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
        private int _id;

        [ObservableProperty]
        [JsonProperty("nombreUsuario")]
        [DataMember]
        private string _nombreUsuario = string.Empty;

        [ObservableProperty]
        [JsonProperty("nombre")]
        [DataMember]
        private string _nombre = string.Empty;

        [ObservableProperty]
        [JsonProperty("apellido")]
        [DataMember]
        private string _apellido = string.Empty;

        [ObservableProperty]
        [JsonProperty("descripcion")]
        [DataMember]
        private string _descripcion = string.Empty;

        [ObservableProperty]
        [JsonProperty("edad")]
        [DataMember]
        private int _edad;

        [ObservableProperty]
        [JsonProperty("email")]
        [DataMember]
        private string _email = string.Empty;

        [ObservableProperty]
        [JsonProperty("imagen")]
        [DataMember]
        private string _imagen = string.Empty;

        [ObservableProperty]
        [JsonProperty("bloqueado")]
        [DataMember]
        private bool _bloqueado;

        [ObservableProperty]
        [JsonProperty("causaBloqueo")]
        [DataMember]
        private string _causaBloqueo = string.Empty;

        [ObservableProperty]
        [JsonProperty("fechaBloqueo")]
        [DataMember]
        private DateTime? _fechaBloqueo;

        public string NombreCompleto => string.IsNullOrEmpty(Apellido) ? Nombre : $"{Apellido}, {Nombre}";

        public string Avatar => Imagen;

        public string Comunidades => string.Empty;

        public string FechaBloqueoFormateada =>
            FechaBloqueo.HasValue ? FechaBloqueo.Value.ToString("dd/MM/yyyy HH:mm") : string.Empty;

        [JsonIgnore]
        public SolidColorBrush ColorBorde { get; } = new((Color)ColorConverter.ConvertFromString(_coloresHex[_random.Next(_coloresHex.Length)]));

        public Usuario() { }
    }
}
