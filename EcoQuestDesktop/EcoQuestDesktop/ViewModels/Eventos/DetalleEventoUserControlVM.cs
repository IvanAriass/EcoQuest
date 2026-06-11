using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using EcoQuestDesktop.Messages;
using EcoQuestDesktop.Models;

namespace EcoQuestDesktop.ViewModels.Eventos
{
    public partial class DetalleEventoUserControlVM : ObservableObject
    {
        [ObservableProperty]
        private Evento _evento = new();

        public string FechaFormateada => Evento.Fecha.ToString("dd/MM/yyyy");

        public string NombreComunidadMostrar => string.IsNullOrEmpty(Evento.NombreComunidad)
            ? "Administración EcoQuest"
            : Evento.NombreComunidad;

        partial void OnEventoChanged(Evento value)
        {
            OnPropertyChanged(nameof(FechaFormateada));
            OnPropertyChanged(nameof(NombreComunidadMostrar));
        }

        [RelayCommand]
        private void Cerrar()
        {
            WeakReferenceMessenger.Default.Send(new CerrarDetalleEventoMessage(true));
        }
    }
}
