public class Reto
    {
        public int RetoId { get; set; }
        public string Nombre { get; set; }
        public string Descripcion { get; set; }
        public int Puntos { get; set; }

        public Reto() { }

        public Reto(int retoId, string nombre, string descripcion, int puntos)
        {
            RetoId = retoId;
            Nombre = nombre;
            Descripcion = descripcion;
            Puntos = puntos;
        }
    }