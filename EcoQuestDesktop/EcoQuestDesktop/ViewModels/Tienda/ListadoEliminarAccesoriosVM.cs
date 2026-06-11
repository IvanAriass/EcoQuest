using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using EcoQuestDesktop.Messages;
using EcoQuestDesktop.Models;
using EcoQuestDesktop.Services;
using System;
using System.Collections.ObjectModel;

namespace EcoQuestDesktop.ViewModels.Tienda
{
    public partial class ListadoEliminarAccesoriosVM : ObservableObject, IRecipient<AccesorioAñadidoMessage>
    {
        [ObservableProperty]
        private ObservableCollection<Producto> _listaProductos = new();

        [ObservableProperty]
        private string _textoBusqueda = string.Empty;

        [ObservableProperty]
        private bool _modalVisible = false;

        [ObservableProperty]
        private ConfirmacionEliminacionModalVM _confirmacionVM = new();

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

            // Cuando se confirma la eliminación
            ConfirmacionVM.OnConfirmar += (item) =>
            {
                var producto = item as Producto;
                if (producto != null)
                {
                    ApiRestService.EliminarProducto(producto.Id);
                    CargarProductos();
                    WeakReferenceMessenger.Default.Send(new AccesorioAñadidoMessage());
                }
                ModalVisible = false;
            };

            // Cuando se cancela la eliminación
            ConfirmacionVM.OnCancelar += () =>
            {
                ModalVisible = false;
            };
        }

        public void Receive(AccesorioAñadidoMessage message)
        {
            CargarProductos();
        }

        [RelayCommand]
        public void EliminarAccesorio(Producto producto)
        {
            if (producto == null) return;

            string titulo = $"Eliminar: {producto.Nombre}";
            string mensaje = $"¿Estás seguro de que quieres eliminar \"{producto.Nombre}\"?";

            ConfirmacionVM.CargarConfirmacion(titulo, mensaje, producto);
            ModalVisible = true;
        }

        [RelayCommand]
        private void CerrarModal() => ModalVisible = false;

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
