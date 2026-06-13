using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using EcoQuestDesktop.Services;
using System.Windows.Controls;

namespace EcoQuestDesktop.ViewModels
{
    public partial class RetosUserControlVM : ObservableObject
    {
        private NavegacionService sn;

        [ObservableProperty]
        private bool isDockVisible = false;

        [ObservableProperty]
        private UserControl? _contenidoVentana;

        public RetosUserControlVM()
        {
            sn = new NavegacionService();
            ContenidoVentana = sn.ObtenerVentanaListadoRetos();
        }

        [RelayCommand]
        private void ToggleDock()
        {
            IsDockVisible = !IsDockVisible;
        }

        [RelayCommand]
        private void AbrirListadoRetos()
        {
            ContenidoVentana = sn.ObtenerVentanaListadoRetos();
        }
    }
}
