using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using PrototipadoEscritorio.Services;
using System.Windows.Controls;

namespace PrototipadoEscritorio.ViewModels
{
    public partial class EventosUserControlVM : ObservableObject
    {
        private NavegacionService sn;

        [ObservableProperty]
        private bool isDockVisible = false;

        [ObservableProperty]
        private UserControl? _contenidoVentana;

        public EventosUserControlVM()
        {
            sn = new NavegacionService();
            ContenidoVentana = sn.ObtenerVentanaListadoEventos();
        }

        [RelayCommand]
        private void ToggleDock()
        {
            IsDockVisible = !IsDockVisible;
        }

        [RelayCommand]
        private void AbrirListadoEventos()
        {
            ContenidoVentana = sn.ObtenerVentanaListadoEventos();
        }

        [RelayCommand]
        private void AbrirAñadirEvento()
        {
            ContenidoVentana = sn.ObtenerVentanaAñadirEvento();
        }

        [RelayCommand]
        private void AbrirRevisarEvento()
        {
            ContenidoVentana = sn.ObtenerVentanaRevisarEventos();
        }

        [RelayCommand]
        private void AbrirEliminarEvento()
        {
            ContenidoVentana = sn.ObtenerVentanaEliminarEventos();
        }
    }
}
