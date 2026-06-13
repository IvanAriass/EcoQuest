using CommunityToolkit.Mvvm.ComponentModel;
using Newtonsoft.Json;
using System.Runtime.Serialization;

namespace EcoQuestDesktop.Models
{
    [DataContract]
    public partial class Producto : ObservableObject
    {
        [ObservableProperty]
        [JsonProperty("id")]
        [DataMember]
        private int _id;

        [ObservableProperty]
        [JsonProperty("nombre")]
        [DataMember]
        private string nombre;

        [ObservableProperty]
        [JsonProperty("imagen")]
        [DataMember]
        private string imagen;

        [ObservableProperty]
        [JsonProperty("precio")]
        [DataMember]
        private int precio;

        [ObservableProperty]
        [JsonProperty("categoria")]
        [DataMember]
        private Categoria? categoria;

        public Producto() {
            Nombre = string.Empty;
            Imagen = string.Empty;
            Precio = 0;
        }

        public Producto(string nombre, string imagen, int precio, Categoria? categoria = null)
        {
            Nombre = nombre;
            Imagen = imagen;
            Precio = precio;
            Categoria = categoria;
        }
    }
}

