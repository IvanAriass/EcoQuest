using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using PrototipadoEscritorio.Messages;
using PrototipadoEscritorio.Models;

namespace PrototipadoEscritorio.ViewModels.Usuarios
{
    public partial class DesbloquearUsuarioUserControlVM : ObservableObject
    {
        [ObservableProperty]
        private Usuario _usuario = new();

        [RelayCommand]
        private void Cancelar()
        {
            WeakReferenceMessenger.Default.Send(new DesbloquearUsuarioMessage(null));
        }

        [RelayCommand]
        private void Confirmar()
        {
            WeakReferenceMessenger.Default.Send(new DesbloquearUsuarioMessage(Usuario));
        }
    }
}
