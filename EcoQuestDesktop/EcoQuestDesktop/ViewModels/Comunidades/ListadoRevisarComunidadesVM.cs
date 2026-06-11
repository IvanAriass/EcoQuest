using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using EcoQuestDesktop.Messages;
using EcoQuestDesktop.Models;
using EcoQuestDesktop.Services;
using System.Collections.ObjectModel;
using System.Linq;

namespace EcoQuestDesktop.ViewModels.Comunidades
{
    public partial class ListadoRevisarComunidadesVM : ObservableObject
    {
        [ObservableProperty]
        private string _textoBusqueda = string.Empty;

        [ObservableProperty]
        private bool _modalVisible = false;

        [ObservableProperty]
        private bool _modalDetalleVisible = false;

        [ObservableProperty]
        private bool _modalAceptarVisible = false;

        [ObservableProperty]
        private RevisarComunidadUserControlVM _revisarComunidadVM = new();

        [ObservableProperty]
        private DetalleComunidadUserControlVM _detalleComunidadVM = new();

        [ObservableProperty]
        private ConfirmacionEliminacionModalVM _confirmacionAceptarVM = new();

        public ObservableCollection<Comunidad> ListaComunidades { get; } = new();

        public ListadoRevisarComunidadesVM()
        {
            CargarComunidades();

            WeakReferenceMessenger.Default.Register<ComunidadAñadidaMessage>(this, (r, m) =>
            {
                CargarComunidades();
            });

            WeakReferenceMessenger.Default.Register<RevisarComunidadMessage>(this, (r, m) =>
            {
                ModalVisible = false;
                CargarComunidades();
            });

            WeakReferenceMessenger.Default.Register<CerrarDetalleComunidadMessage>(this, (r, m) =>
            {
                DetalleComunidadVM.Comunidad = new();
                ModalDetalleVisible = false;
            });

            ConfirmacionAceptarVM.OnConfirmar += async (item) =>
            {
                var comunidad = item as Comunidad;
                if (comunidad != null)
                {
                    await ApiRestService.AprobarComunidad(comunidad.ComunidadId);
                    WeakReferenceMessenger.Default.Send(new ComunidadAñadidaMessage());
                    CargarComunidades();
                }
                ModalAceptarVisible = false;
            };

            ConfirmacionAceptarVM.OnCancelar += () =>
            {
                ModalAceptarVisible = false;
            };
        }

        public void CargarComunidades()
        {
            var comunidades = ApiRestService.GetComunidadesPorEstado("PENDIENTE");
            ListaComunidades.Clear();
            foreach (var c in comunidades)
                ListaComunidades.Add(c);
        }

        partial void OnTextoBusquedaChanged(string value)
        {
            if (string.IsNullOrWhiteSpace(value))
            {
                CargarComunidades();
                return;
            }
            var todas = ApiRestService.GetComunidadesPorEstado("PENDIENTE");
            var resultados = todas.Where(c => c.Nombre.Contains(value, System.StringComparison.OrdinalIgnoreCase)).ToList();
            ListaComunidades.Clear();
            foreach (var c in resultados)
                ListaComunidades.Add(c);
        }

        [RelayCommand]
        private void RevisarComunidad(Comunidad comunidad)
        {
            if (comunidad == null) return;
            RevisarComunidadVM.Comunidad = comunidad;
            RevisarComunidadVM.Razon = string.Empty;
            ModalVisible = true;
        }

        [RelayCommand]
        private void AceptarComunidad(Comunidad comunidad)
        {
            if (comunidad == null) return;
            string titulo = $"Aceptar: {comunidad.Nombre}";
            string mensaje = $"¿Estás seguro de que quieres aceptar \"{comunidad.Nombre}\"?";
            ConfirmacionAceptarVM.CargarConfirmacion(titulo, mensaje, comunidad,
                "#27AE60", "#27AE60", "#1E8449", "#145A32", "Aceptar");
            ModalAceptarVisible = true;
        }

        [RelayCommand]
        private void VerDetalleComunidad(Comunidad comunidad)
        {
            if (comunidad == null) return;
            DetalleComunidadVM.Comunidad = comunidad;
            DetalleComunidadVM.ColorTipo = "amarillo";
            ModalDetalleVisible = true;
        }

        [RelayCommand]
        private void CerrarModal()
        {
            if (ModalVisible) ModalVisible = false;
            if (ModalDetalleVisible)
            {
                DetalleComunidadVM.Comunidad = new();
                ModalDetalleVisible = false;
            }
        }

        [RelayCommand]
        private void CerrarModalAceptar() => ModalAceptarVisible = false;
    }
}
