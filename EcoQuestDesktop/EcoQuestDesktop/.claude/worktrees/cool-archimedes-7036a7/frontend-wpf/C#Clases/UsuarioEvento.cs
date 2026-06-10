public class UsuarioEvento
    {
        public int UsuarioEventoId { get; set; }
        public int UsuarioId { get; set; }
        public int EventoId { get; set; }
        public DateTime FechaRegistro { get; set; }
        public bool Asistencia { get; set; }

        public UsuarioEvento() { }

        public UsuarioEvento(int usuarioEventoId, int usuarioId, int eventoId, DateTime fechaRegistro, bool asistencia)
        {
            UsuarioEventoId = usuarioEventoId;
            UsuarioId = usuarioId;
            EventoId = eventoId;
            FechaRegistro = fechaRegistro;
            Asistencia = asistencia;
        }
    }