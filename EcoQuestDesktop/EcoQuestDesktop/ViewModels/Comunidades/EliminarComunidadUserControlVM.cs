using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using PrototipadoEscritorio.Messages;
using PrototipadoEscritorio.Models;

namespace PrototipadoEscritorio.ViewModels.Comunidades
{
    public partial class EliminarComunidadUserControlVM : ObservableObject
    {
        [ObservableProperty]
        private Comunidad _comunidad = new();

        [RelayCommand]
        private void Cancelar()
        {
            WeakReferenceMessenger.Default.Send(new EliminarComunidadMessage(null));
        }

        [RelayCommand]
        private void Confirmar()
        {
            WeakReferenceMessenger.Default.Send(new EliminarComunidadMessage(Comunidad));
        }
    }
}
