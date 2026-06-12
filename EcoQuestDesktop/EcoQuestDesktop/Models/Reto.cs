using CommunityToolkit.Mvvm.ComponentModel;
using Newtonsoft.Json;
using System.Runtime.Serialization;

namespace EcoQuestDesktop.Models
{
    [DataContract]
    public partial class Reto : ObservableObject
    {
        [ObservableProperty]
        [JsonProperty("id")]
        [DataMember]
        private int _id;

        [ObservableProperty]
        [JsonProperty("nombre")]
        [DataMember]
        private string _nombre = string.Empty;

        [ObservableProperty]
        [JsonProperty("descripcion")]
        [DataMember]
        private string _descripcion = string.Empty;

        [ObservableProperty]
        [JsonProperty("puntos")]
        [DataMember]
        private int _puntos;

        [ObservableProperty]
        [JsonProperty("tipo")]
        [DataMember]
        private string _tipo = string.Empty;

        [ObservableProperty]
        [JsonProperty("requisitoCantidad")]
        [DataMember]
        private int _requisitoCantidad = 1;

        [ObservableProperty]
        [JsonProperty("frecuencia")]
        [DataMember]
        private string _frecuencia = "ILIMITADA";

        public string FrecuenciaLabel => Frecuencia switch
        {
            "DIARIA" => "Diario",
            "UNICA" => "Una vez",
            "ILIMITADA" => "Ilimitado",
            _ => Frecuencia
        };

        public Reto() { }
    }
}
