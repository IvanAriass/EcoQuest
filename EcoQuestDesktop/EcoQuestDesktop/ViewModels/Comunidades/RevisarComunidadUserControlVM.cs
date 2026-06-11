using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using EcoQuestDesktop.Messages;
using EcoQuestDesktop.Models;
using EcoQuestDesktop.Services;
using System.Threading.Tasks;

namespace EcoQuestDesktop.ViewModels.Comunidades
{
    public partial class RevisarComunidadUserControlVM : ObservableObject
    {
        [ObservableProperty]
        private Comunidad _comunidad;

        [ObservableProperty]
        private string _razon = string.Empty;

        public RevisarComunidadUserControlVM()
        {
            Comunidad = new Comunidad();
        }

        [RelayCommand]
        private void Cancelar()
        {
            WeakReferenceMessenger.Default.Send(new RevisarComunidadMessage(null));
        }

        [RelayCommand]
        private async Task Confirmar()
        {
            if (Comunidad?.ComunidadId > 0)
            {
                await ApiRestService.RevisarComunidad(Comunidad.ComunidadId, Razon?.Trim() ?? "");
            }
            WeakReferenceMessenger.Default.Send(new RevisarComunidadMessage(Comunidad));
        }
    }
}
