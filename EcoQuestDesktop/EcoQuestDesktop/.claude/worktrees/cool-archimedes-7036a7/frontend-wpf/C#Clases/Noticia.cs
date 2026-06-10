public class Noticia
    {
        public int NoticiaId { get; set; }
        public string Autor { get; set; }
        public string Descripcion { get; set; }
        public string Imagen { get; set; }
        public string Nombre { get; set; }

        public Noticia() { }

        public Noticia(int noticiaId, string autor, string descripcion, string imagen, string nombre)
        {
            NoticiaId = noticiaId;
            Autor = autor;
            Descripcion = descripcion;
            Imagen = imagen;
            Nombre = nombre;
        }
    }