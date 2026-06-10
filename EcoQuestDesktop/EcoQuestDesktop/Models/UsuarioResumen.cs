using Newtonsoft.Json;

namespace PrototipadoEscritorio.Models
{
    public class UsuarioResumen
    {
        [JsonProperty("nombreUsuario")]
        public string NombreUsuario { get; set; } = string.Empty;

        [JsonProperty("rol")]
        public string Rol { get; set; } = string.Empty;
    }
}
