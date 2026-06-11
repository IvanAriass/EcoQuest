using EcoQuestDesktop.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.Messaging.Messages;

namespace EcoQuestDesktop.Messages
{
    public class RevisarEventoMessage : ValueChangedMessage<Evento>
    {
        public string Motivo { get; }
        public RevisarEventoMessage(Evento evento, string motivo = "") : base(evento)
        {
            Motivo = motivo;
        }
    }
}
