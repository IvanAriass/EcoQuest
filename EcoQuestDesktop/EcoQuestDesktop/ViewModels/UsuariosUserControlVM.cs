using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using EcoQuestDesktop.Services;
using System.Windows.Controls;

namespace EcoQuestDesktop.ViewModels
{
    public partial class UsuariosUserControlVM : ObservableObject
    {
        private NavegacionService sn;

        [ObservableProperty]
        private bool isDockVisible = false;

        [ObservableProperty]
        private UserControl? _contenidoVentana;

        public UsuariosUserControlVM()
        {
            sn = new NavegacionService();
            ContenidoVentana = sn.ObtenerVentanaListadoUsuarios();
        }

        [RelayCommand]
        private void ToggleDock()
        {
            IsDockVisible = !IsDockVisible;
        }

        [RelayCommand]
        private void AbrirListadoUsuarios()
        {
            ContenidoVentana = sn.ObtenerVentanaListadoUsuarios();
        }

        [RelayCommand]
        private void AbrirAñadirUsuario()
        {
            ContenidoVentana = sn.ObtenerVentanaAñadirUsuario();
        }

        [RelayCommand]
        private void AbrirEliminarUsuario()
        {
            ContenidoVentana = sn.ObtenerVentanaEliminarUsuarios();
        }

        [RelayCommand]
        private void AbrirListadoBloqueados()
        {
            ContenidoVentana = sn.ObtenerVentanaListadoBloqueados();
        }
    }
}
