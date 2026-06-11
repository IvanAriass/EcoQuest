using CommunityToolkit.Mvvm.Messaging.Messages;

namespace EcoQuestDesktop.Messages
{
    public class CerrarDetalleUsuarioMessage : ValueChangedMessage<bool>
    {
        public CerrarDetalleUsuarioMessage(bool value) : base(value) { }
    }
}
