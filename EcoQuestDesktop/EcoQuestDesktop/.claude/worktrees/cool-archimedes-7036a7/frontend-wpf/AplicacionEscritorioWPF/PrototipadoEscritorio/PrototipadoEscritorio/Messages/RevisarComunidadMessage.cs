using PrototipadoEscritorio.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.Messaging.Messages;

namespace PrototipadoEscritorio.Messages
{
    public class RevisarComunidadMessage : ValueChangedMessage<Comunidad>
    {
        public RevisarComunidadMessage(Comunidad comunidad) : base(comunidad) {}
    }
}
