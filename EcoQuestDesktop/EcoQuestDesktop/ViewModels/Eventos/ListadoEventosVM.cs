using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using PrototipadoEscritorio.Messages;
using PrototipadoEscritorio.Models;
using PrototipadoEscritorio.Services;
using System.Collections.ObjectModel;
using System.Linq;

namespace PrototipadoEscritorio.ViewModels.Eventos
{
    public partial class ListadoEventosVM : ObservableObject
    {
        [ObservableProperty]
        private string _textoBusqueda = string.Empty;

        [ObservableProperty]
        private bool _modalVisible = false;

        [ObservableProperty]
        private DetalleEventoUserControlVM _detalleEventoVM = new();

        public ObservableCollection<Evento> ListaEventos { get; } = new();

        public ListadoEventosVM()
        {
            CargarEventos();

            WeakReferenceMessenger.Default.Register<EventoAñadidoMessage>(this, (r, m) =>
            {
                CargarEventos();
            });

            WeakReferenceMessenger.Default.Register<CerrarDetalleEventoMessage>(this, (r, m) =>
            {
                DetalleEventoVM.Evento = new();
                ModalVisible = false;
            });
        }

        public void CargarEventos()
        {
            var eventos = ApiRestService.BuscarEventosPorEstado("ACTIVO");
            ListaEventos.Clear();
            foreach (var e in eventos)
                ListaEventos.Add(e);
        }

        partial void OnTextoBusquedaChanged(string value)
        {
            if (string.IsNullOrWhiteSpace(value))
            {
                CargarEventos();
                return;
            }
            var resultados = ApiRestService.BuscarEventosPorNombre(value);
            ListaEventos.Clear();
            foreach (var e in resultados.Where(e => e.Estado == "ACTIVO"))
                ListaEventos.Add(e);
        }

        [RelayCommand]
        private void VerDetalleEvento(Evento evento)
        {
            if (evento == null) return;
            DetalleEventoVM.Evento = evento;
            ModalVisible = true;
        }

        [RelayCommand]
        private void CerrarModal()
        {
            DetalleEventoVM.Evento = new();
            ModalVisible = false;
        }
    }
}
