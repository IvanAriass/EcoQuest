using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using EcoQuestDesktop.Messages;
using EcoQuestDesktop.Models;
using System.Collections.ObjectModel;
using System.Linq;

namespace EcoQuestDesktop.ViewModels.Comunidades
{
    public partial class DetalleComunidadUserControlVM : ObservableObject
    {
        [ObservableProperty]
        private Comunidad _comunidad = new();

        [ObservableProperty]
        private string _colorTipo = "verde";

        public int NumeroMiembros => Comunidad.Usuarios?.Count ?? 0;

        public ObservableCollection<string> RolesUnicos { get; } = new();

        partial void OnComunidadChanged(Comunidad value)
        {
            OnPropertyChanged(nameof(Comunidad));
            OnPropertyChanged(nameof(NumeroMiembros));

            RolesUnicos.Clear();

            if (value?.Usuarios != null)
            {
                foreach (var rol in value.Usuarios.Select(u => u.Rol).Distinct())
                    RolesUnicos.Add(rol);
            }

            if (!string.IsNullOrEmpty(value?.Roles))
            {
                foreach (var rol in value.Roles.Split(new[] { ',' }, StringSplitOptions.RemoveEmptyEntries | StringSplitOptions.TrimEntries))
                {
                    if (!RolesUnicos.Contains(rol))
                        RolesUnicos.Add(rol);
                }
            }
        }

        [RelayCommand]
        private void Cerrar()
        {
            WeakReferenceMessenger.Default.Send(new CerrarDetalleComunidadMessage(true));
        }
    }
}
