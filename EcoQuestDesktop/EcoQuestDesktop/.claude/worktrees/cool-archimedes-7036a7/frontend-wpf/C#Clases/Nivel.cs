public class Nivel
    {
        public int NivelId { get; set; }
        public string Nombre { get; set; }
        public string Descripcion { get; set; }
        public int Puntos { get; set; }
        public string Icono { get; set; }

        public Nivel() { }

        public Nivel(int nivelId, string nombre, string descripcion, int puntos, string icono)
        {
            NivelId = nivelId;
            Nombre = nombre;
            Descripcion = descripcion;
            Puntos = puntos;
            Icono = icono;
        }
        public void NivelSubir()
        {
            
        }
    }