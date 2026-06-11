using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using EcoQuestDesktop.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;
using System.Windows.Navigation;

namespace EcoQuestDesktop.ViewModels
{
    public partial class MainWindowVM : ObservableObject
    {
        private NavegacionService sn;

        [ObservableProperty]
        public UserControl? _contenidoVentana;

        public MainWindowVM()
        {
            sn = new NavegacionService();
            ContenidoVentana = sn.ObtenerVentanaUsuarios();
        }


        [RelayCommand]
        public void AbrirVentanaUsuarios()
        {
            ContenidoVentana = sn.ObtenerVentanaUsuarios();
        }

        [RelayCommand]
        public void AbrirVentanaAccesorios()
        {
            ContenidoVentana = sn.ObtenerVentanaAccesorios();
        }

        [RelayCommand]
        public void AbrirVentanaEventos()
        {
            ContenidoVentana = sn.ObtenerVentanaEventos();
        }

        [RelayCommand]
        public void AbrirVentanaComunidades()
        {
            ContenidoVentana = sn.ObtenerVentanaComunidades();
        }

    }
}
