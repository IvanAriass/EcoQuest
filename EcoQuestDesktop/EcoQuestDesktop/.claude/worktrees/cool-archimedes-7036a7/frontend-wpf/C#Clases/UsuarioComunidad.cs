public class UsuarioComunidad
    {
        public int UsuarioComunidadId { get; set; }
        public int UsuarioId { get; set; }
        public int ComunidadId { get; set; }
        public Rol Rol { get; set; }

        public UsuarioComunidad() { }

        public UsuarioComunidad(int usuarioComunidadId, int usuarioId, int comunidadId, Rol rol)
        {
            UsuarioComunidadId = usuarioComunidadId;
            UsuarioId = usuarioId;
            ComunidadId = comunidadId;
            Rol = rol;
        }
    }