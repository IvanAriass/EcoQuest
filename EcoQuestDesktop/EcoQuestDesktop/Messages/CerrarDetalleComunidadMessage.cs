using CommunityToolkit.Mvvm.Messaging.Messages;

namespace PrototipadoEscritorio.Messages
{
    public class CerrarDetalleComunidadMessage : ValueChangedMessage<bool>
    {
        public CerrarDetalleComunidadMessage(bool value) : base(value) { }
    }
}
