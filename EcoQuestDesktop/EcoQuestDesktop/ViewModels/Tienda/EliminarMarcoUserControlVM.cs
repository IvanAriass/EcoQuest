using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using PrototipadoEscritorio.Messages;
using PrototipadoEscritorio.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PrototipadoEscritorio.ViewModels
{
    public partial class EliminarMarcoUserControlVM : ObservableObject
    {
        // Producto que se muestra en la vista
        [ObservableProperty]
        private Producto producto;

        public EliminarMarcoUserControlVM( )
        {
            Producto = producto;
        }

        // Comando cancelar
        [RelayCommand]
        private void Cancelar()
        {
            // Puedes enviar un mensaje indicando que no se elimina
            WeakReferenceMessenger.Default.Send(new EliminarMarcoMessage(null));
        }

        // Comando confirmar
        [RelayCommand]
        private void Confirmar()
        {
            WeakReferenceMessenger.Default.Send(new EliminarMarcoMessage(Producto));
        }
    }
}
