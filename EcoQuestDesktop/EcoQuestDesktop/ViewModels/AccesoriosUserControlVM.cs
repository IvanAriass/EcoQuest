using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using EcoQuestDesktop.Services;
using System.Windows.Controls;

namespace EcoQuestDesktop.ViewModels
{
    public partial class AccesoriosUserControlVM : ObservableObject
    {

        private NavegacionService sn;

        [ObservableProperty]
        private bool isDockVisible = false;

        [ObservableProperty]
        private UserControl? _contenidoVentana;

        public AccesoriosUserControlVM()
        {
            sn = new NavegacionService();
            ContenidoVentana = sn.ObtenerVentanaListadoAccesorios();
        }

        [RelayCommand]
        private void ToggleDock()
        {
            IsDockVisible = !IsDockVisible;
        }

        // Navegación entre vistas
        [RelayCommand]
        private void AbrirListadoAccesorios()
        {
            ContenidoVentana = sn.ObtenerVentanaListadoAccesorios();
        }

        [RelayCommand]
        private void AbrirAñadirAccesorio()
        {
            ContenidoVentana = sn.ObtenerVentanaAñadirAccesorio();
        }

        [RelayCommand]
        private void AbrirEliminarAccesorio()
        {
            ContenidoVentana = sn.ObtenerVentanaEliminarAccesorios();
        }
    }
}