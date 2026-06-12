using CommunityToolkit.Mvvm.ComponentModel;
using Newtonsoft.Json;
using System;
using System.Runtime.Serialization;

namespace EcoQuestDesktop.Models
{
    [DataContract]
    public partial class TransaccionPuntos : ObservableObject
    {
        [ObservableProperty]
        [JsonProperty("id")]
        [DataMember]
        private int _id;

        [ObservableProperty]
        [JsonProperty("puntos")]
        [DataMember]
        private int _puntos;

        [ObservableProperty]
        [JsonProperty("tipo")]
        [DataMember]
        private string _tipo = string.Empty;

        [ObservableProperty]
        [JsonProperty("concepto")]
        [DataMember]
        private string _concepto = string.Empty;

        [ObservableProperty]
        [JsonProperty("fecha")]
        [DataMember]
        private DateTime _fecha;

        public string FechaFormateada => Fecha.ToString("dd/MM/yyyy HH:mm");

        public string Signo => Tipo == "GANADO" ? "+" : "-";

        public TransaccionPuntos() { }
    }
}
