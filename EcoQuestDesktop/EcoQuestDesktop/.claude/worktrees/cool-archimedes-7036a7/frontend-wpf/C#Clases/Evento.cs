public class Evento
    {
        public int EventoId { get; set; }
        public string Nombre { get; set; }
        public string Ubicacion { get; set; }
        public string Descripcion { get; set; }
        public string Imagen { get; set; }
        public DateTime Fecha { get; set; }

        public Evento() { }

        public Evento(int eventoId, string nombre, string ubicacion, string descripcion, string imagen, DateTime fecha)
        {
            EventoId = eventoId;
            Nombre = nombre;
            Ubicacion = ubicacion;
            Descripcion = descripcion;
            Imagen = imagen;
            Fecha = fecha;
        }
    }