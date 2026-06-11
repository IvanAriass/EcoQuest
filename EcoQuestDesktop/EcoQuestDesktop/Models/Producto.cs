using CommunityToolkit.Mvvm.ComponentModel;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using static System.Runtime.InteropServices.JavaScript.JSType;

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

        public Producto() {
            Nombre = string.Empty;
            Imagen = string.Empty;
            Precio = 0;
        }

        public Producto(string nombre, string imagen, int precio)
        {
            Nombre = nombre;
            Imagen = imagen;
            Precio = precio;
        }
    }
}

