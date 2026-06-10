using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using PrototipadoEscritorio.Messages;
using PrototipadoEscritorio.Models;
using PrototipadoEscritorio.Services;
using System.Collections.ObjectModel;
using System.Linq;

namespace PrototipadoEscritorio.ViewModels.Comunidades
{
    public partial class ListadoEliminarComunidadesVM : ObservableObject
    {
        [ObservableProperty]
        private string _textoBusqueda = string.Empty;

        [ObservableProperty]
        private bool _modalVisible = false;

        [ObservableProperty]
        private bool _modalDetalleVisible = false;

        [ObservableProperty]
        private EliminarComunidadUserControlVM _eliminarComunidadVM = new();

        [ObservableProperty]
        private DetalleComunidadUserControlVM _detalleComunidadVM = new();

        public ObservableCollection<Comunidad> ListaComunidades { get; } = new();

        public ListadoEliminarComunidadesVM()
        {
            CargarComunidades();

            WeakReferenceMessenger.Default.Register<ComunidadAñadidaMessage>(this, (r, m) =>
            {
                CargarComunidades();
            });

            WeakReferenceMessenger.Default.Register<EliminarComunidadMessage>(this, (r, m) =>
            {
                if (m.Value != null)
                {
                    ApiRestService.EliminarComunidad(m.Value.ComunidadId);
                    WeakReferenceMessenger.Default.Send(new ComunidadAñadidaMessage());
                }
                ModalVisible = false;
                CargarComunidades();
            });

            WeakReferenceMessenger.Default.Register<CerrarDetalleComunidadMessage>(this, (r, m) =>
            {
                DetalleComunidadVM.Comunidad = new();
                ModalDetalleVisible = false;
            });
        }

        public void CargarComunidades()
        {
            var comunidades = ApiRestService.GetComunidades();
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
            var resultados = ApiRestService.BuscarComunidadesPorNombre(value);
            ListaComunidades.Clear();
            foreach (var c in resultados)
                ListaComunidades.Add(c);
        }

        [RelayCommand]
        private void EliminarComunidad(Comunidad comunidad)
        {
            if (comunidad == null) return;
            EliminarComunidadVM.Comunidad = comunidad;
            ModalVisible = true;
        }

        [RelayCommand]
        private void VerDetalleComunidad(Comunidad comunidad)
        {
            if (comunidad == null) return;
            DetalleComunidadVM.Comunidad = comunidad;
            DetalleComunidadVM.ColorTipo = "rojo";
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
    }
}
