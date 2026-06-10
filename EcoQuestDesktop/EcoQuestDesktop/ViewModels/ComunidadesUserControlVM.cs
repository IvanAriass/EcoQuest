using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using PrototipadoEscritorio.Services;
using System.Windows.Controls;

namespace PrototipadoEscritorio.ViewModels
{
    public partial class ComunidadesUserControlVM : ObservableObject
    {
        private NavegacionService sn;

        [ObservableProperty]
        private bool isDockVisible = false;

        [ObservableProperty]
        private UserControl? _contenidoVentana;

        public ComunidadesUserControlVM()
        {
            sn = new NavegacionService();
            ContenidoVentana = sn.ObtenerVentanaListadoComunidades();
        }

        [RelayCommand]
        private void ToggleDock()
        {
            IsDockVisible = !IsDockVisible;
        }

        [RelayCommand]
        private void AbrirListadoComunidades()
        {
            ContenidoVentana = sn.ObtenerVentanaListadoComunidades();
        }

        [RelayCommand]
        private void AbrirAñadirComunidad()
        {
            ContenidoVentana = sn.ObtenerVentanaAñadirComunidad();
        }

        [RelayCommand]
        private void AbrirRevisarComunidad()
        {
            ContenidoVentana = sn.ObtenerVentanaRevisarComunidades();
        }

        [RelayCommand]
        private void AbrirEliminarComunidad()
        {
            ContenidoVentana = sn.ObtenerVentanaEliminarComunidades();
        }
    }
}
