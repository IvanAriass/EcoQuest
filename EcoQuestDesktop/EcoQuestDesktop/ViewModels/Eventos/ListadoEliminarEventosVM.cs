using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using PrototipadoEscritorio.Messages;
using PrototipadoEscritorio.Models;
using PrototipadoEscritorio.Services;
using System.Collections.ObjectModel;

namespace PrototipadoEscritorio.ViewModels.Eventos
{
    public partial class ListadoEliminarEventosVM : ObservableObject
    {
        [ObservableProperty]
        private string _textoBusqueda = string.Empty;

        [ObservableProperty]
        private bool _modalVisible = false;

        [ObservableProperty]
        private bool _detalleModalVisible = false;

        [ObservableProperty]
        private EliminarEventoUserControlVM _eliminarEventoVM = new();

        [ObservableProperty]
        private DetalleEventoUserControlVM _detalleEventoVM = new();

        public ObservableCollection<Evento> ListaEventos { get; } = new();

        public ListadoEliminarEventosVM()
        {
            CargarEventos();

            WeakReferenceMessenger.Default.Register<EventoAñadidoMessage>(this, (r, m) =>
            {
                CargarEventos();
            });

            WeakReferenceMessenger.Default.Register<EliminarEventoMessage>(this, (r, m) =>
            {
                if (m.Value != null)
                {
                    ApiRestService.EliminarEvento(m.Value.EventoId);
                    WeakReferenceMessenger.Default.Send(new EventoAñadidoMessage());
                }
                ModalVisible = false;
            });

            WeakReferenceMessenger.Default.Register<CerrarDetalleEventoMessage>(this, (r, m) =>
            {
                DetalleEventoVM.Evento = new();
                DetalleModalVisible = false;
            });
        }

        public void CargarEventos()
        {
            var eventos = ApiRestService.GetEventos();
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
            foreach (var e in resultados)
                ListaEventos.Add(e);
        }

        [RelayCommand]
        private void EliminarEvento(Evento evento)
        {
            if (evento == null) return;
            EliminarEventoVM.Evento = evento;
            EliminarEventoVM.Razon = string.Empty;
            ModalVisible = true;
        }

        [RelayCommand]
        private void CerrarModal() => ModalVisible = false;

        [RelayCommand]
        private void VerDetalleEvento(Evento evento)
        {
            if (evento == null) return;
            DetalleEventoVM.Evento = evento;
            DetalleModalVisible = true;
        }

        [RelayCommand]
        private void CerrarDetalleModal()
        {
            DetalleEventoVM.Evento = new();
            DetalleModalVisible = false;
        }
    }
}
