using PrototipadoEscritorio.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.Messaging.Messages;

namespace PrototipadoEscritorio.Messages
{
    public class EliminarMarcoMessage : ValueChangedMessage<Producto>
    {
        public EliminarMarcoMessage(Producto producto) : base(producto) {}
    }
}
