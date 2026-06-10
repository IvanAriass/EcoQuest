public class UsuarioReto
    {
        public int UsuarioRetoId { get; set; }
        public int UsuarioId { get; set; }
        public int RetoId { get; set; }
        public bool Completado { get; set; }
        public DateTime FechaCompletado { get; set; }

        public UsuarioReto() { }

        public UsuarioReto(int usuarioRetoId, int usuarioId, int retoId, bool completado, DateTime fechaCompletado)
        {
            UsuarioRetoId = usuarioRetoId;
            UsuarioId = usuarioId;
            RetoId = retoId;
            Completado = completado;
            FechaCompletado = fechaCompletado;
        }
    }