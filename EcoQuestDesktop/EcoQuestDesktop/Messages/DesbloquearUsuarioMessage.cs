using CommunityToolkit.Mvvm.Messaging.Messages;
using EcoQuestDesktop.Models;

namespace EcoQuestDesktop.Messages
{
    public class DesbloquearUsuarioMessage : ValueChangedMessage<Usuario>
    {
        public DesbloquearUsuarioMessage(Usuario usuario) : base(usuario) { }
    }
}
