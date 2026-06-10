using PrototipadoEscritorio.Views;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace PrototipadoEscritorio.Services
{
    internal class NavegacionService
    {
        private static UserControl usuariosVista, accesoriosVista, eventosVista, comunidadesVista;
        private static UserControl listadoAccesorios, añadirAccesorio, eliminarAccesorios;

        public UserControl ObtenerVentanaUsuarios()
        {
            if (usuariosVista is null)
            {
                usuariosVista = new UsuariosUserControl();
            }
            return usuariosVista;
        }

        public UserControl ObtenerVentanaAccesorios()
        {
            if (accesoriosVista is null)
            {
                accesoriosVista = new AccesoriosUserControl();
            }
            return accesoriosVista;
        }

        public UserControl ObtenerVentanaEventos()
        {
            if (eventosVista is null)
            {
                eventosVista = new EventosUserControl();
            }
            return eventosVista;
        }

        public UserControl ObtenerVentanaComunidades()
        {
            if (comunidadesVista is null)
            {
                comunidadesVista = new ComunidadesUserControl();
            }
            return comunidadesVista;
        }

        public UserControl ObtenerVentanaListadoAccesorios()
        {
            if (listadoAccesorios is null)
            {
                listadoAccesorios = new ListadoAccesoriosUserControl();
            }
            return listadoAccesorios;
        }

        public UserControl ObtenerVentanaAñadirAccesorio()
        {
            if (añadirAccesorio is null)
            {
                añadirAccesorio = new AñadirAccesorioUserControl();
            }
            return añadirAccesorio;
        }

        public UserControl ObtenerVentanaEliminarAccesorios()
        {
            if (eliminarAccesorios is null)
            {
                eliminarAccesorios = new ListadoEliminarAccesoriosUserControl();
            }
            return eliminarAccesorios;
        }
    }
}