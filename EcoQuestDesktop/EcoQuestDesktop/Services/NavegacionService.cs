using EcoQuestDesktop.Views;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace EcoQuestDesktop.Services
{
    internal class NavegacionService
    {
        private static UserControl usuariosVista, accesoriosVista, eventosVista, comunidadesVista;
        private static UserControl listadoAccesorios, añadirAccesorio, eliminarAccesorios;
        private static UserControl listadoEventos, añadirEvento, revisarEventos, eliminarEventos;
        private static UserControl listadoComunidades, añadirComunidad, revisarComunidades, eliminarComunidades;
        private static UserControl listadoUsuarios, añadirUsuario, eliminarUsuarios, listadoBloqueados;

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

        public UserControl ObtenerVentanaListadoEventos()
        {
            if (listadoEventos is null)
            {
                listadoEventos = new ListadoEventosUserControl();
            }
            return listadoEventos;
        }

        public UserControl ObtenerVentanaAñadirEvento()
        {
            if (añadirEvento is null)
            {
                añadirEvento = new AñadirEventoUserControl();
            }
            return añadirEvento;
        }

        public UserControl ObtenerVentanaRevisarEventos()
        {
            if (revisarEventos is null)
            {
                revisarEventos = new ListadoRevisarEventosUserControl();
            }
            return revisarEventos;
        }

        public UserControl ObtenerVentanaEliminarEventos()
        {
            if (eliminarEventos is null)
            {
                eliminarEventos = new ListadoEliminarEventosUserControl();
            }
            return eliminarEventos;
        }

        public UserControl ObtenerVentanaListadoComunidades()
        {
            if (listadoComunidades is null)
            {
                listadoComunidades = new ListadoComunidadesUserControl();
            }
            return listadoComunidades;
        }

        public UserControl ObtenerVentanaAñadirComunidad()
        {
            if (añadirComunidad is null)
            {
                añadirComunidad = new AñadirComunidadUserControl();
            }
            return añadirComunidad;
        }

        public UserControl ObtenerVentanaRevisarComunidades()
        {
            if (revisarComunidades is null)
            {
                revisarComunidades = new ListadoRevisarComunidadesUserControl();
            }
            return revisarComunidades;
        }

        public UserControl ObtenerVentanaEliminarComunidades()
        {
            if (eliminarComunidades is null)
            {
                eliminarComunidades = new ListadoEliminarComunidadesUserControl();
            }
            return eliminarComunidades;
        }

        public UserControl ObtenerVentanaListadoUsuarios()
        {
            if (listadoUsuarios is null)
            {
                listadoUsuarios = new ListadoUsuariosUserControl();
            }
            return listadoUsuarios;
        }

        public UserControl ObtenerVentanaAñadirUsuario()
        {
            if (añadirUsuario is null)
            {
                añadirUsuario = new AñadirUsuarioUserControl();
            }
            return añadirUsuario;
        }

        public UserControl ObtenerVentanaEliminarUsuarios()
        {
            if (eliminarUsuarios is null)
            {
                eliminarUsuarios = new ListadoEliminarUsuariosUserControl();
            }
            return eliminarUsuarios;
        }

        public UserControl ObtenerVentanaListadoBloqueados()
        {
            if (listadoBloqueados is null)
            {
                listadoBloqueados = new ListadoBloqueadosUserControl();
            }
            return listadoBloqueados;
        }
    }
}
