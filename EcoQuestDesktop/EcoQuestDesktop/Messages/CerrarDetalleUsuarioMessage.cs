using CommunityToolkit.Mvvm.Messaging.Messages;

namespace PrototipadoEscritorio.Messages
{
    public class CerrarDetalleUsuarioMessage : ValueChangedMessage<bool>
    {
        public CerrarDetalleUsuarioMessage(bool value) : base(value) { }
    }
}
