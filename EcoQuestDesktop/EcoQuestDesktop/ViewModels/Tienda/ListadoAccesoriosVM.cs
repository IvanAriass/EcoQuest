using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using PrototipadoEscritorio.Messages;
using PrototipadoEscritorio.Models;
using PrototipadoEscritorio.Services;
using System.Collections.ObjectModel;
using System.Windows;
using System.Windows.Threading;

namespace PrototipadoEscritorio.ViewModels.Tienda
{
    public partial class ListadoAccesoriosVM : ObservableObject, IRecipient<AccesorioAñadidoMessage>
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

        [ObservableProperty]
        private bool _modalVisible = false;

        [ObservableProperty]
        private EditarAccesorioModalVM _modalVM = new();

        public ListadoAccesoriosVM()
        {
            WeakReferenceMessenger.Default.Register(this);
            CargarProductos();

            // Cerrar modal cuando el ModalVM dispare OnGuardar
            ModalVM.OnGuardar += () =>
            {
                ModalVisible = false;
                // Diferir la recarga hasta que WPF termine el ciclo de render del cierre del modal
                Application.Current.Dispatcher.BeginInvoke(
                    DispatcherPriority.Background,
                    new Action(() => WeakReferenceMessenger.Default.Send(new AccesorioAñadidoMessage()))
                );
            };
        }

        public void Receive(AccesorioAñadidoMessage message)
        {
            CargarProductos();
        }

        [RelayCommand]
        private void AbrirEditar(Producto producto)
        {
            ModalVM.CargarProducto(producto);
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
