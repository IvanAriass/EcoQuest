using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using PrototipadoEscritorio.Messages;
using PrototipadoEscritorio.Models;

namespace PrototipadoEscritorio.ViewModels.Eventos
{
    public partial class RevisarEventoUserControlVM : ObservableObject
    {
        [ObservableProperty]
        private Evento _evento;

        [ObservableProperty]
        private string _razon = string.Empty;

        public RevisarEventoUserControlVM()
        {
            Evento = new Evento();
        }

        [RelayCommand]
        private void Cancelar()
        {
            WeakReferenceMessenger.Default.Send(new RevisarEventoMessage(null));
        }

        [RelayCommand]
        private void Confirmar()
        {
            WeakReferenceMessenger.Default.Send(new RevisarEventoMessage(Evento, Razon));
        }
    }
}
