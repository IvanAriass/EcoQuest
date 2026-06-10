using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using PrototipadoEscritorio.Messages;
using PrototipadoEscritorio.Models;

namespace PrototipadoEscritorio.ViewModels.Usuarios
{
    public partial class BloquearUsuarioUserControlVM : ObservableObject
    {
        [ObservableProperty]
        private Usuario _usuario = new();

        [ObservableProperty]
        private string _causa = string.Empty;

        [RelayCommand]
        private void Cancelar()
        {
            WeakReferenceMessenger.Default.Send(new BloquearUsuarioMessage(null));
        }

        [RelayCommand]
        private void Confirmar()
        {
            WeakReferenceMessenger.Default.Send(new BloquearUsuarioMessage(Usuario));
        }
    }
}
