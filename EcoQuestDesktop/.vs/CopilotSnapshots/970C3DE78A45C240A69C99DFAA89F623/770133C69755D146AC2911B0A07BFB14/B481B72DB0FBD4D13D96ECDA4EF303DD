using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Messaging;
using PrototipadoEscritorio.Messages;
using PrototipadoEscritorio.Models;
using PrototipadoEscritorio.Services;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PrototipadoEscritorio.ViewModels.Tienda
{
    public partial class ListadoEliminarAccesoriosVM : ObservableObject, IRecipient<AccesorioAñadidoMessage>
    {
        [ObservableProperty]
        private ObservableCollection<Producto> _listaProductos = new();

        [ObservableProperty]
        private string _textoBusqueda = string.Empty;

        partial void OnTextoBusquedaChanged(string value)
        {
            if (string.IsNullOrWhiteSpace(value))
                CargarProductos();
            else
                BuscarProductos(value);
        }

        public ListadoEliminarAccesoriosVM()
        {
            WeakReferenceMessenger.Default.Register(this);
            CargarProductos();
        }

        public void Receive(AccesorioAñadidoMessage message)
        {
            CargarProductos();
        }

        private void CargarProductos()
        {
            var productos = ApiRestService.GetProductos();
            ListaProductos = new ObservableCollection<Producto>(productos);
        }

        private void BuscarProductos(string nombre)
        {
            var productos = ApiRestService.BuscarProductosPorNombre(nombre);
            ListaProductos = new ObservableCollection<Producto>(productos);
        }
    }
}
