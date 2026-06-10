using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using PrototipadoEscritorio.Messages;
using PrototipadoEscritorio.Models;
using PrototipadoEscritorio.Services;
using System.Collections.ObjectModel;
using System.Linq;

namespace PrototipadoEscritorio.ViewModels.Usuarios
{
    public partial class ListadoBloqueadosVM : ObservableObject, IRecipient<UsuarioAñadidoMessage>
    {
        [ObservableProperty]
        private string _textoBusqueda = string.Empty;

        [ObservableProperty]
        private bool _modalVisible = false;

        [ObservableProperty]
        private bool _detalleModalVisible = false;

        [ObservableProperty]
        private DesbloquearUsuarioUserControlVM _desbloquearUsuarioVM = new();

        [ObservableProperty]
        private DetalleUsuarioUserControlVM _detalleUsuarioVM = new();

        public ObservableCollection<Usuario> ListaBloqueados { get; } = new();

        private System.Collections.Generic.List<Usuario> _todosBloqueados = new();

        public ListadoBloqueadosVM()
        {
            WeakReferenceMessenger.Default.Register<DesbloquearUsuarioMessage>(this, async (r, m) =>
            {
                if (m.Value != null)
                {
                    await ApiRestService.DesbloquearUsuario(m.Value.Id);
                    WeakReferenceMessenger.Default.Send(new UsuarioAñadidoMessage());
                    CargarBloqueados();
                }
                ModalVisible = false;
            });

            WeakReferenceMessenger.Default.Register<CerrarDetalleUsuarioMessage>(this, (r, m) =>
            {
                DetalleModalVisible = false;
            });

            WeakReferenceMessenger.Default.Register(this);
            CargarBloqueados();
        }

        public void CargarBloqueados()
        {
            var usuarios = ApiRestService.GetUsuariosBloqueados();
            _todosBloqueados = usuarios ?? new System.Collections.Generic.List<Usuario>();
            FiltrarBloqueados();
        }

        partial void OnTextoBusquedaChanged(string value)
        {
            FiltrarBloqueados();
        }

        private void FiltrarBloqueados()
        {
            ListaBloqueados.Clear();
            var filtrados = string.IsNullOrWhiteSpace(TextoBusqueda)
                ? _todosBloqueados
                : _todosBloqueados.Where(u =>
                    u.NombreUsuario.Contains(TextoBusqueda) ||
                    u.Nombre.Contains(TextoBusqueda) ||
                    u.Apellido.Contains(TextoBusqueda) ||
                    u.CausaBloqueo.Contains(TextoBusqueda)).ToList();

            foreach (var u in filtrados)
            {
                ListaBloqueados.Add(u);
            }
        }

        public void Receive(UsuarioAñadidoMessage message)
        {
            CargarBloqueados();
        }

        [RelayCommand]
        private void DesbloquearUsuario(Usuario usuario)
        {
            if (usuario == null) return;
            DesbloquearUsuarioVM.Usuario = usuario;
            ModalVisible = true;
        }

        [RelayCommand]
        private void CerrarModal() => ModalVisible = false;

        [RelayCommand]
        private void VerDetalle(Usuario usuario)
        {
            if (usuario == null) return;
            DetalleUsuarioVM.Usuario = usuario;
            DetalleModalVisible = true;
        }

        [RelayCommand]
        private void CerrarDetalle() => DetalleModalVisible = false;
    }
}
