public class Usuario
    {
        public int UsuarioId { get; set; }
        public string Contraseña { get; set; }
        public bool Admin { get; set; }
        public string NombreUsuario { get; set; }
        public string Nombre { get; set; }
        public string Apellidos { get; set; }
        public string Correo { get; set; }
        public string Imagen { get; set; }
        public string Descripcion { get; set; }
        public DateTime Edad { get; set; }
        public List<Comunidad> Comunidades { get; set; }
        public int Puntos { get; set; }
        public int PuntosExperiencia { get; set; }
        public Nivel Nivel { get; set; }
        public List<Producto> Productos { get; set; }

        public Usuario()
        {
            Comunidades = new List<Comunidad>();
            Productos = new List<Producto>();
        }

        public Usuario(int usuarioId, string contraseña, bool admin, string nombreUsuario, string nombre,
                       string apellidos, string correo, string imagen, string descripcion, DateTime edad,
                       int puntos, int puntosExperiencia, Nivel nivel)
        {
            UsuarioId = usuarioId;
            Contraseña = contraseña;
            Admin = admin;
            NombreUsuario = nombreUsuario;
            Nombre = nombre;
            Apellidos = apellidos;
            Correo = correo;
            Imagen = imagen;
            Descripcion = descripcion;
            Edad = edad;
            Puntos = puntos;
            PuntosExperiencia = puntosExperiencia;
            Nivel = nivel;
            Comunidades = new List<Comunidad>();
            Productos = new List<Producto>();
        }
    }