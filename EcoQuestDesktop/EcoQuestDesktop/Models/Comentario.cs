using Newtonsoft.Json;
using System.Runtime.Serialization;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using EcoQuestDesktop.Properties;

namespace EcoQuestDesktop.Models
{
    [DataContract]
    public class Comentario
    {
        [JsonProperty("id")]
        [DataMember]
        public int Id { get; set; }

        [JsonProperty("texto")]
        [DataMember]
        public string Texto { get; set; }

        [JsonProperty("fechaHora")]
        [DataMember]
        public string FechaHora { get; set; }

        [JsonProperty("usuarioId")]
        [DataMember]
        public int UsuarioId { get; set; }

        [JsonProperty("usuarioNombre")]
        [DataMember]
        public string UsuarioNombre { get; set; }

        [JsonProperty("usuarioNombreUsuario")]
        [DataMember]
        public string UsuarioNombreUsuario { get; set; }

        [JsonProperty("usuarioImagen")]
        [DataMember]
        public string UsuarioImagen { get; set; }

        [JsonProperty("eventoId")]
        [DataMember]
        public int EventoId { get; set; }

        [JsonProperty("comentarioPadreId")]
        [DataMember]
        public int? ComentarioPadreId { get; set; }

        [JsonIgnore]
        public ImageSource UrlUsuarioImagen
        {
            get
            {
                if (string.IsNullOrEmpty(UsuarioImagen))
                    return null;

                if (UsuarioImagen.StartsWith("http://") || UsuarioImagen.StartsWith("https://"))
                {
                    var bitmap = new BitmapImage();
                    bitmap.BeginInit();
                    bitmap.UriSource = new Uri(UsuarioImagen, UriKind.Absolute);
                    bitmap.CacheOption = BitmapCacheOption.OnLoad;
                    bitmap.EndInit();
                    return bitmap;
                }

                var url = $"{Settings.Default.ApiRestEndPoint.TrimEnd('/')}/usuarios/imagen/{UsuarioImagen}";
                var bitmap2 = new BitmapImage();
                bitmap2.BeginInit();
                bitmap2.UriSource = new Uri(url, UriKind.Absolute);
                bitmap2.CacheOption = BitmapCacheOption.OnLoad;
                bitmap2.EndInit();
                return bitmap2;
            }
        }

        [JsonIgnore]
        public string NombreMostrar => !string.IsNullOrEmpty(UsuarioNombreUsuario) ? UsuarioNombreUsuario : UsuarioNombre;
    }
}
