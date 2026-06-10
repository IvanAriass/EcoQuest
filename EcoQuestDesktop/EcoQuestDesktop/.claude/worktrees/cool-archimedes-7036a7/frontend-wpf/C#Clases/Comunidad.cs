public class Comunidad
    {
        public int ComunidadId { get; set; }
        public List<Usuario> Usuarios { get; set; }
        public string Nombre { get; set; }
        public List<Evento> Eventos { get; set; }
        public string Imagen { get; set; }
        public string Descripcion { get; set; }
        public Rol Rol { get; set; }

        public Comunidad()
        {
            Usuarios = new List<Usuario>();
            Eventos = new List<Evento>();
        }

        public Comunidad(int comunidadId, string nombre, string imagen, string descripcion, Rol rol)
        {
            ComunidadId = comunidadId;
            Nombre = nombre;
            Imagen = imagen;
            Descripcion = descripcion;
            Rol = rol;
            Usuarios = new List<Usuario>();
            Eventos = new List<Evento>();
        }
    }