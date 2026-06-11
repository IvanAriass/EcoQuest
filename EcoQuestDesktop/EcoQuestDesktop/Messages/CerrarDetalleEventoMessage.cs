using CommunityToolkit.Mvvm.Messaging.Messages;

namespace EcoQuestDesktop.Messages
{
    public class CerrarDetalleEventoMessage : ValueChangedMessage<bool>
    {
        public CerrarDetalleEventoMessage(bool value) : base(value) { }
    }
}
