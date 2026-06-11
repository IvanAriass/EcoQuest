using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using EcoQuestDesktop.Messages;
using EcoQuestDesktop.Models;
using EcoQuestDesktop.Services;
using System.Collections.ObjectModel;
using System.Linq;

namespace EcoQuestDesktop.ViewModels.Usuarios
{
    public partial class ListadoUsuariosVM : ObservableObject, IRecipient<UsuarioAñadidoMessage>
    {
        [ObservableProperty]
        private string _textoBusqueda = string.Empty;

        [ObservableProperty]
        private bool _modalVisible = false;

        [ObservableProperty]
        private bool _detalleModalVisible = false;

        [ObservableProperty]
        private BloquearUsuarioUserControlVM _bloquearUsuarioVM = new();

        [ObservableProperty]
        private DetalleUsuarioUserControlVM _detalleUsuarioVM = new();

        public ObservableCollection<Usuario> ListaUsuarios { get; } = new();

        private System.Collections.Generic.List<Usuario> _todosUsuarios = new();

        public ListadoUsuariosVM()
        {
            WeakReferenceMessenger.Default.Register<BloquearUsuarioMessage>(this, async (r, m) =>
            {
                if (m.Value != null)
                {
                    await ApiRestService.BloquearUsuario(m.Value.Id, BloquearUsuarioVM.Causa);
                    WeakReferenceMessenger.Default.Send(new UsuarioAñadidoMessage());
                    CargarUsuarios();
                }
                ModalVisible = false;
            });

            WeakReferenceMessenger.Default.Register<CerrarDetalleUsuarioMessage>(this, (r, m) =>
            {
                DetalleModalVisible = false;
            });

            WeakReferenceMessenger.Default.Register(this);
            CargarUsuarios();
        }

        public void CargarUsuarios()
        {
            var usuarios = ApiRestService.GetUsuarios();
            _todosUsuarios = usuarios ?? new System.Collections.Generic.List<Usuario>();
            FiltrarUsuarios();
        }

        partial void OnTextoBusquedaChanged(string value)
        {
            FiltrarUsuarios();
        }

        private void FiltrarUsuarios()
        {
            ListaUsuarios.Clear();
            var filtrados = string.IsNullOrWhiteSpace(TextoBusqueda)
                ? _todosUsuarios
                : _todosUsuarios.Where(u =>
                    u.NombreUsuario.Contains(TextoBusqueda) ||
                    u.Nombre.Contains(TextoBusqueda) ||
                    u.Apellido.Contains(TextoBusqueda) ||
                    u.Email.Contains(TextoBusqueda)).ToList();

            foreach (var u in filtrados)
            {
                ListaUsuarios.Add(u);
            }
        }

        public void Receive(UsuarioAñadidoMessage message)
        {
            CargarUsuarios();
        }

        [RelayCommand]
        private void BloquearUsuario(Usuario usuario)
        {
            if (usuario == null) return;
            BloquearUsuarioVM.Usuario = usuario;
            BloquearUsuarioVM.Causa = string.Empty;
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
