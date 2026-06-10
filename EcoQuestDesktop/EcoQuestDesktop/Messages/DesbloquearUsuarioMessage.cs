using CommunityToolkit.Mvvm.Messaging.Messages;
using PrototipadoEscritorio.Models;

namespace PrototipadoEscritorio.Messages
{
    public class DesbloquearUsuarioMessage : ValueChangedMessage<Usuario>
    {
        public DesbloquearUsuarioMessage(Usuario usuario) : base(usuario) { }
    }
}
