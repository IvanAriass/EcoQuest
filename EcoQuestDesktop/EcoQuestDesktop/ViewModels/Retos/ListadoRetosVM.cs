using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using EcoQuestDesktop.Models;
using EcoQuestDesktop.Services;
using System.Collections.ObjectModel;

namespace EcoQuestDesktop.ViewModels.Retos
{
    public partial class ListadoRetosVM : ObservableObject
    {
        [ObservableProperty]
        private ObservableCollection<Reto> _listaRetos = new();

        [ObservableProperty]
        private ObservableCollection<TransaccionPuntos> _historialPuntos = new();

        [ObservableProperty]
        private int _saldoPuntos;

        [ObservableProperty]
        private int _usuarioId = 1;

        public ListadoRetosVM()
        {
            CargarRetos();
            CargarHistorial();
        }

        [RelayCommand]
        private void Refrescar()
        {
            CargarRetos();
            CargarHistorial();
        }

        private void CargarRetos()
        {
            var retos = ApiRestService.GetRetos();
            ListaRetos = new ObservableCollection<Reto>(retos);
        }

        private void CargarHistorial()
        {
            var historial = ApiRestService.GetHistorialPuntos(UsuarioId);
            HistorialPuntos = new ObservableCollection<TransaccionPuntos>(historial);
            SaldoPuntos = ApiRestService.GetSaldoPuntos(UsuarioId);
        }
    }
}
