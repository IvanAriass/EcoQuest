using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using PrototipadoEscritorio.Messages;
using PrototipadoEscritorio.Models;
using PrototipadoEscritorio.Services;
using System;
using System.Collections.ObjectModel;
using System.Linq;

namespace PrototipadoEscritorio.ViewModels.Eventos
{
    public partial class ListadoRevisarEventosVM : ObservableObject
    {
        [ObservableProperty]
        private string _textoBusqueda = string.Empty;

        [ObservableProperty]
        private bool _modalVisible = false;

        [ObservableProperty]
        private bool _detalleModalVisible = false;

        [ObservableProperty]
        private bool _modalAceptarVisible = false;

        [ObservableProperty]
        private RevisarEventoUserControlVM _revisarEventoVM = new();

        [ObservableProperty]
        private DetalleEventoUserControlVM _detalleEventoVM = new();

        [ObservableProperty]
        private ConfirmacionEliminacionModalVM _confirmacionAceptarVM = new();

        public ObservableCollection<Evento> ListaEventos { get; } = new();

        public ListadoRevisarEventosVM()
        {
            CargarEventos();

            WeakReferenceMessenger.Default.Register<EventoAñadidoMessage>(this, (r, m) =>
            {
                CargarEventos();
            });

            WeakReferenceMessenger.Default.Register<RevisarEventoMessage>(this, async (r, m) =>
            {
                if (m.Value != null)
                {
                    await ApiRestService.EnviarARevision(m.Value.EventoId, m.Motivo);
                    WeakReferenceMessenger.Default.Send(new EventoAñadidoMessage());
                }
                ModalVisible = false;
            });

            WeakReferenceMessenger.Default.Register<CerrarDetalleEventoMessage>(this, (r, m) =>
            {
                DetalleEventoVM.Evento = new();
                DetalleModalVisible = false;
            });

            ConfirmacionAceptarVM.OnConfirmar += (item) =>
            {
                var evento = item as Evento;
                if (evento != null)
                {
                    _ = ApiRestService.AceptarEvento(evento.EventoId);
                    CargarEventos();
                    WeakReferenceMessenger.Default.Send(new EventoAñadidoMessage());
                }
                ModalAceptarVisible = false;
            };

            ConfirmacionAceptarVM.OnCancelar += () =>
            {
                ModalAceptarVisible = false;
            };
        }

        public void CargarEventos()
        {
            var eventos = ApiRestService.BuscarEventosPorEstado("PENDIENTE");
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
            foreach (var e in resultados.Where(e => e.Estado == "PENDIENTE"))
                ListaEventos.Add(e);
        }

        [RelayCommand]
        private void RevisarEvento(Evento evento)
        {
            if (evento == null) return;
            RevisarEventoVM.Evento = evento;
            RevisarEventoVM.Razon = string.Empty;
            ModalVisible = true;
        }

        [RelayCommand]
        private void CerrarModal() => ModalVisible = false;

        [RelayCommand]
        private void AceptarEvento(Evento evento)
        {
            if (evento == null) return;
            string titulo = $"Aceptar: {evento.Nombre}";
            string mensaje = $"¿Estás seguro de que quieres aceptar \"{evento.Nombre}\"?";
            ConfirmacionAceptarVM.CargarConfirmacion(titulo, mensaje, evento,
                "#27AE60", "#27AE60", "#1E8449", "#145A32", "Aceptar");
            ModalAceptarVisible = true;
        }

        [RelayCommand]
        private void CerrarModalAceptar() => ModalAceptarVisible = false;

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
