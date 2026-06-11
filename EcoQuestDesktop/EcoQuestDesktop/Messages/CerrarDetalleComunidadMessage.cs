using CommunityToolkit.Mvvm.Messaging.Messages;

namespace EcoQuestDesktop.Messages
{
    public class CerrarDetalleComunidadMessage : ValueChangedMessage<bool>
    {
        public CerrarDetalleComunidadMessage(bool value) : base(value) { }
    }
}
