public class Producto
    {
        public int ProductoId { get; set; }
        public string Nombre { get; set; }
        public string Descripcion { get; set; }
        public int Puntos { get; set; }

        public Producto() { }

        public Producto(int productoId, string nombre, string descripcion, int puntos)
        {
            ProductoId = productoId;
            Nombre = nombre;
            Descripcion = descripcion;
            Puntos = puntos;
        }
    }