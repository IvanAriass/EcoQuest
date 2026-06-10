using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using PrototipadoEscritorio.Messages;
using PrototipadoEscritorio.Models;

namespace PrototipadoEscritorio.ViewModels
{
    public partial class EliminarEventoUserControlVM : ObservableObject
    {
        [ObservableProperty]
        private Evento _evento = new();

        [ObservableProperty]
        private string _razon = string.Empty;

        [RelayCommand]
        private void Cancelar()
        {
            WeakReferenceMessenger.Default.Send(new EliminarEventoMessage(null));
        }

        [RelayCommand]
        private void Confirmar()
        {
            WeakReferenceMessenger.Default.Send(new EliminarEventoMessage(Evento));
        }
    }
}
