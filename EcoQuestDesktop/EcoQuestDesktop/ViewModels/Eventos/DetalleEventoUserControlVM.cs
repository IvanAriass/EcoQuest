using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using EcoQuestDesktop.Messages;
using EcoQuestDesktop.Models;
using EcoQuestDesktop.Services;
using System.Collections.ObjectModel;
using System.Linq;

namespace EcoQuestDesktop.ViewModels.Eventos
{
    public partial class DetalleEventoUserControlVM : ObservableObject
    {
        [ObservableProperty]
        private Evento _evento = new();

        [ObservableProperty]
        private string _nuevoComentario = string.Empty;

        public ObservableCollection<Comentario> Comentarios { get; } = new();

        public string FechaFormateada => Evento.Fecha.ToString("dd/MM/yyyy");

        public string NombreComunidadMostrar => string.IsNullOrEmpty(Evento.NombreComunidad)
            ? "Administración EcoQuest"
            : Evento.NombreComunidad;

        partial void OnEventoChanged(Evento value)
        {
            OnPropertyChanged(nameof(FechaFormateada));
            OnPropertyChanged(nameof(NombreComunidadMostrar));
            CargarComentarios();
        }

        private void CargarComentarios()
        {
            Comentarios.Clear();
            var lista = ApiRestService.GetComentarios(Evento.EventoId);
            foreach (var c in lista)
                Comentarios.Add(c);
        }

        [RelayCommand]
        private async Task EnviarComentario()
        {
            if (string.IsNullOrWhiteSpace(NuevoComentario)) return;
            var comentario = await ApiRestService.CrearComentario(Evento.EventoId, 1, NuevoComentario.Trim());
            if (comentario != null)
            {
                Comentarios.Add(comentario);
                NuevoComentario = string.Empty;
            }
        }

        [RelayCommand]
        private void Cerrar()
        {
            WeakReferenceMessenger.Default.Send(new CerrarDetalleEventoMessage(true));
        }
    }
}
