using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using CommunityToolkit.Mvvm.Messaging;
using PrototipadoEscritorio.Messages;
using PrototipadoEscritorio.Models;
using PrototipadoEscritorio.Services;
using System.Collections.ObjectModel;
using System.Linq;
using System.Windows;

namespace PrototipadoEscritorio.ViewModels.Usuarios
{
    public partial class ListadoEliminarUsuariosVM : ObservableObject, IRecipient<UsuarioAñadidoMessage>
    {
        [ObservableProperty]
        private string _textoBusqueda = string.Empty;

        [ObservableProperty]
        private bool _modalVisible = false;

        [ObservableProperty]
        private bool _detalleModalVisible = false;

        [ObservableProperty]
        private EliminarUsuarioUserControlVM _eliminarUsuarioVM = new();

        [ObservableProperty]
        private DetalleUsuarioUserControlVM _detalleUsuarioVM = new();

        public ObservableCollection<Usuario> ListaUsuarios { get; } = new();

        private System.Collections.Generic.List<Usuario> _todosUsuarios = new();

        public ListadoEliminarUsuariosVM()
        {
            WeakReferenceMessenger.Default.Register(this);

            WeakReferenceMessenger.Default.Register<EliminarUsuarioMessage>(this, (r, m) =>
            {
                if (m.Value != null)
                {
                    ApiRestService.EliminarUsuario(m.Value.Id);
                    CargarUsuarios();
                    WeakReferenceMessenger.Default.Send(new UsuarioAñadidoMessage());
                }
                ModalVisible = false;
            });

            WeakReferenceMessenger.Default.Register<CerrarDetalleUsuarioMessage>(this, (r, m) =>
            {
                DetalleModalVisible = false;
            });

            CargarUsuarios();
        }

        public void CargarUsuarios()
        {
            var usuarios = ApiRestService.GetUsuariosTodos();
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
                    u.Apellido.Contains(TextoBusqueda)).ToList();

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
        private void EliminarUsuario(Usuario usuario)
        {
            if (usuario == null) return;
            EliminarUsuarioVM.Usuario = usuario;
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
