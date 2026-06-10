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
    public partial class ListadoComunidadesVM : ObservableObject
    {
        [ObservableProperty]
        private string _textoBusqueda = string.Empty;

        [ObservableProperty]
        private bool _modalVisible = false;

        [ObservableProperty]
        private DetalleComunidadUserControlVM _detalleComunidadVM = new();

        public ObservableCollection<Comunidad> ListaComunidades { get; } = new();

        public ListadoComunidadesVM()
        {
            CargarComunidades();

            WeakReferenceMessenger.Default.Register<ComunidadAñadidaMessage>(this, (r, m) =>
            {
                CargarComunidades();
            });

            WeakReferenceMessenger.Default.Register<CerrarDetalleComunidadMessage>(this, (r, m) =>
            {
                DetalleComunidadVM.Comunidad = new();
                ModalVisible = false;
            });
        }

        public void CargarComunidades()
        {
            var comunidades = ApiRestService.GetComunidadesPorEstado("ACTIVO");
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
            var todas = ApiRestService.GetComunidadesPorEstado("ACTIVO");
            var resultados = todas.Where(c => c.Nombre.Contains(value, System.StringComparison.OrdinalIgnoreCase)).ToList();
            ListaComunidades.Clear();
            foreach (var c in resultados)
                ListaComunidades.Add(c);
        }

        [RelayCommand]
        private void VerDetalleComunidad(Comunidad comunidad)
        {
            if (comunidad == null) return;
            DetalleComunidadVM.Comunidad = comunidad;
            ModalVisible = true;
        }

        [RelayCommand]
        private void CerrarModal()
        {
            DetalleComunidadVM.Comunidad = new();
            ModalVisible = false;
        }
    }
}
