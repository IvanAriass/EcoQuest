using CommunityToolkit.Mvvm.ComponentModel;
using Newtonsoft.Json;
using System.Runtime.Serialization;

namespace EcoQuestDesktop.Models
{
    [DataContract]
    public partial class Categoria : ObservableObject
    {
        [ObservableProperty]
        [JsonProperty("id")]
        [DataMember]
        private int _id;

        [ObservableProperty]
        [JsonProperty("nombre")]
        [DataMember]
        private string _nombre = string.Empty;

        public Categoria() { }

        public Categoria(int id, string nombre)
        {
            Id = id;
            Nombre = nombre;
        }

        public override string ToString() => Nombre;
    }
}
