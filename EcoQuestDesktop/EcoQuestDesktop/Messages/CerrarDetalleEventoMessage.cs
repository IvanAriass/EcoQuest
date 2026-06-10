using CommunityToolkit.Mvvm.Messaging.Messages;

namespace PrototipadoEscritorio.Messages
{
    public class CerrarDetalleEventoMessage : ValueChangedMessage<bool>
    {
        public CerrarDetalleEventoMessage(bool value) : base(value) { }
    }
}
