using CommunityToolkit.Mvvm.ComponentModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace PrototipadoEscritorio.Models
{
    public partial class Evento : ObservableObject
    {
        public int EventoId { get; set; } // No reactivo

        [ObservableProperty]
        private string nombre;

        [ObservableProperty]
        private string ubicacion;

        [ObservableProperty]
        private string descripcion;

        [ObservableProperty]
        private string imagen;

        [ObservableProperty]
        private DateTime fecha;

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
}

