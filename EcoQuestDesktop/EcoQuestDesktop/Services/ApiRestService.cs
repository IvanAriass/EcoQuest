using Newtonsoft.Json;
using EcoQuestDesktop.Models;
using RestSharp;
using System;
using System.Collections.Generic;
using System.IO;
using System.Threading.Tasks;

namespace EcoQuestDesktop.Services
{
    public class ApiRestService
    {
        // ==================== PRODUCTOS ====================

        public static List<Producto> GetProductos()
        {
            try
            {
                RestClient client = new RestClient(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new RestRequest("productos", Method.Get);
                RestResponse response = client.Execute(request);
                return JsonConvert.DeserializeObject<List<Producto>>(response.Content) ?? new();
            }
            catch { return new(); }
        }

        public static List<Producto> BuscarProductosPorNombre(string nombre)
        {
            try
            {
                RestClient client = new RestClient(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new RestRequest("productos/buscar", Method.Get);
                request.AddQueryParameter("nombre", nombre);
                RestResponse response = client.Execute(request);
                return JsonConvert.DeserializeObject<List<Producto>>(response.Content) ?? new();
            }
            catch { return new(); }
        }

        public static async Task<Producto?> CrearProductoConImagen(string nombre, int precio, string? rutaImagen)
        {
            RestClient client = new RestClient(Properties.Settings.Default.ApiRestEndPoint);
            RestRequest request = new RestRequest("productos/con-imagen", Method.Post);

            var productoJson = JsonConvert.SerializeObject(new { nombre, precio });
            request.AddParameter("producto", productoJson, ParameterType.GetOrPost);

            if (!string.IsNullOrEmpty(rutaImagen) && File.Exists(rutaImagen))
            {
                request.AddFile("imagen", rutaImagen);
            }

            request.AlwaysMultipartFormData = true;

            RestResponse response = await client.ExecuteAsync(request);

            if (response.IsSuccessful)
                return JsonConvert.DeserializeObject<Producto>(response.Content);

            return null;
        }

        public static async Task<Producto?> EditarProductoConImagen(int id, string nombre, int precio, string? rutaImagen)
        {
            RestClient client = new RestClient(Properties.Settings.Default.ApiRestEndPoint);
            RestRequest request = new RestRequest($"productos/{id}/con-imagen", Method.Put);

            var productoJson = JsonConvert.SerializeObject(new { nombre, precio });
            request.AddParameter("producto", productoJson, ParameterType.GetOrPost);

            if (!string.IsNullOrEmpty(rutaImagen) && File.Exists(rutaImagen))
            {
                request.AddFile("imagen", rutaImagen);
            }

            request.AlwaysMultipartFormData = true;

            RestResponse response = await client.ExecuteAsync(request);

            if (response.IsSuccessful)
                return JsonConvert.DeserializeObject<Producto>(response.Content);

            return null;
        }

        public static bool EliminarProducto(int id)
        {
            try
            {
                RestClient client = new RestClient(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new RestRequest($"productos/{id}", Method.Delete);
                RestResponse response = client.Execute(request);
                return response.IsSuccessful;
            }
            catch
            {
                return false;
            }
        }

        // ==================== USUARIOS ====================

        public static List<Usuario> GetUsuarios()
        {
            try
            {
                RestClient client = new RestClient(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new RestRequest("usuarios", Method.Get);
                RestResponse response = client.Execute(request);
                return JsonConvert.DeserializeObject<List<Usuario>>(response.Content) ?? new();
            }
            catch { return new(); }
        }

        public static List<Usuario> GetUsuariosTodos()
        {
            try
            {
                RestClient client = new RestClient(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new RestRequest("usuarios/todos", Method.Get);
                RestResponse response = client.Execute(request);
                return JsonConvert.DeserializeObject<List<Usuario>>(response.Content) ?? new();
            }
            catch { return new(); }
        }

        public static List<Usuario> BuscarUsuariosPorNombre(string nombre)
        {
            try
            {
                RestClient client = new RestClient(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new RestRequest("usuarios/buscar", Method.Get);
                request.AddQueryParameter("nombre", nombre);
                RestResponse response = client.Execute(request);
                return JsonConvert.DeserializeObject<List<Usuario>>(response.Content) ?? new();
            }
            catch { return new(); }
        }

        public static async Task<Usuario?> CrearUsuarioConImagen(string nombreUsuario, string nombre, string apellido, string email, DateTime? fechaNacimiento, string descripcion, string contraseña, string? rutaImagen)
        {
            RestClient client = new RestClient(Properties.Settings.Default.ApiRestEndPoint);
            RestRequest request = new RestRequest("usuarios/con-imagen", Method.Post);

            var usuarioJson = JsonConvert.SerializeObject(new
            {
                nombreUsuario,
                nombre,
                apellido,
                email,
                fechaNacimiento,
                descripcion,
                contraseña
            });
            request.AddParameter("usuario", usuarioJson, ParameterType.GetOrPost);

            if (!string.IsNullOrEmpty(rutaImagen) && File.Exists(rutaImagen))
            {
                request.AddFile("imagen", rutaImagen);
            }

            request.AlwaysMultipartFormData = true;

            RestResponse response = await client.ExecuteAsync(request);

            if (response.IsSuccessful)
                return JsonConvert.DeserializeObject<Usuario>(response.Content);

            return null;
        }

        public static async Task<Usuario?> EditarUsuarioConImagen(int id, string nombreUsuario, string nombre, string apellido, string email, DateTime? fechaNacimiento, string descripcion, string? contraseña, string? rutaImagen)
        {
            RestClient client = new RestClient(Properties.Settings.Default.ApiRestEndPoint);
            RestRequest request = new RestRequest($"usuarios/{id}/con-imagen", Method.Put);

            var usuarioJson = JsonConvert.SerializeObject(new
            {
                nombreUsuario,
                nombre,
                apellido,
                email,
                fechaNacimiento,
                descripcion,
                contraseña
            });
            request.AddParameter("usuario", usuarioJson, ParameterType.GetOrPost);

            if (!string.IsNullOrEmpty(rutaImagen) && File.Exists(rutaImagen))
            {
                request.AddFile("imagen", rutaImagen);
            }

            request.AlwaysMultipartFormData = true;

            RestResponse response = await client.ExecuteAsync(request);

            if (response.IsSuccessful)
                return JsonConvert.DeserializeObject<Usuario>(response.Content);

            return null;
        }

        public static bool EliminarUsuario(int id)
        {
            try
            {
                RestClient client = new RestClient(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new RestRequest($"usuarios/{id}", Method.Delete);
                RestResponse response = client.Execute(request);
                return response.IsSuccessful;
            }
            catch
            {
                return false;
            }
        }

        public static List<Usuario> GetUsuariosBloqueados()
        {
            try
            {
                RestClient client = new RestClient(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new RestRequest("usuarios/bloqueados", Method.Get);
                RestResponse response = client.Execute(request);
                return JsonConvert.DeserializeObject<List<Usuario>>(response.Content) ?? new();
            }
            catch { return new(); }
        }

        public static async Task<Usuario?> BloquearUsuario(int id, string causa)
        {
            try
            {
                RestClient client = new RestClient(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new RestRequest($"usuarios/{id}/bloquear", Method.Put);
                var body = JsonConvert.SerializeObject(new { causa });
                request.AddParameter("application/json", body, ParameterType.RequestBody);
                RestResponse response = await client.ExecuteAsync(request);
                if (response.IsSuccessful)
                    return JsonConvert.DeserializeObject<Usuario>(response.Content);
                return null;
            }
            catch
            {
                return null;
            }
        }

        public static async Task<Usuario?> DesbloquearUsuario(int id)
        {
            try
            {
                RestClient client = new RestClient(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new RestRequest($"usuarios/{id}/desbloquear", Method.Put);
                RestResponse response = await client.ExecuteAsync(request);
                if (response.IsSuccessful)
                    return JsonConvert.DeserializeObject<Usuario>(response.Content);
                return null;
            }
            catch
            {
                return null;
            }
        }

        // ==================== COMUNIDADES ====================

        public static List<Comunidad> GetComunidades()
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new("comunidades", Method.Get);
                RestResponse response = client.Execute(request);
                return JsonConvert.DeserializeObject<List<Comunidad>>(response.Content) ?? new();
            }
            catch { return new(); }
        }

        public static async Task<bool> CrearComunidad(string nombre, string descripcion, string roles, string? rutaImagen)
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new("comunidades/con-imagen", Method.Post);

                var comunidadJson = JsonConvert.SerializeObject(new { nombre, descripcion, roles });
                request.AddParameter("comunidad", comunidadJson, ParameterType.GetOrPost);

                if (!string.IsNullOrEmpty(rutaImagen) && File.Exists(rutaImagen))
                {
                    request.AddFile("imagen", rutaImagen);
                }

                request.AlwaysMultipartFormData = true;

                RestResponse response = await client.ExecuteAsync(request);
                return response.IsSuccessful;
            }
            catch
            {
                return false;
            }
        }

        public static List<Comunidad> BuscarComunidadesPorNombre(string nombre)
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new("comunidades", Method.Get);
                RestResponse response = client.Execute(request);
                var todas = JsonConvert.DeserializeObject<List<Comunidad>>(response.Content);
                return todas?.FindAll(c => c.Nombre.Contains(nombre, StringComparison.OrdinalIgnoreCase)) ?? new();
            }
            catch { return new(); }
        }

        public static List<Comunidad> GetComunidadesPorEstado(string estado)
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new("comunidades/estado", Method.Get);
                request.AddQueryParameter("estado", estado);
                RestResponse response = client.Execute(request);
                return JsonConvert.DeserializeObject<List<Comunidad>>(response.Content) ?? new();
            }
            catch { return new(); }
        }

        public static async Task<bool> EnviarComunidadARevision(int id, string motivo)
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new($"comunidades/{id}/en-revision", Method.Patch);
                var body = JsonConvert.SerializeObject(new { motivo });
                request.AddParameter("application/json", body, ParameterType.RequestBody);
                RestResponse response = await client.ExecuteAsync(request);
                return response.IsSuccessful;
            }
            catch
            {
                return false;
            }
        }

        public static async Task<bool> RevisarComunidad(int id, string motivo)
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new($"comunidades/{id}/revisar", Method.Patch);
                var body = JsonConvert.SerializeObject(new { motivo });
                request.AddParameter("application/json", body, ParameterType.RequestBody);
                RestResponse response = await client.ExecuteAsync(request);
                return response.IsSuccessful;
            }
            catch
            {
                return false;
            }
        }

        public static async Task<bool> AprobarComunidad(int id)
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new($"comunidades/{id}/aprobar", Method.Patch);
                RestResponse response = await client.ExecuteAsync(request);
                return response.IsSuccessful;
            }
            catch
            {
                return false;
            }
        }

        public static async Task<bool> CancelarComunidad(int id)
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new($"comunidades/{id}/cancelar", Method.Patch);
                RestResponse response = await client.ExecuteAsync(request);
                return response.IsSuccessful;
            }
            catch
            {
                return false;
            }
        }

        public static bool EliminarComunidad(int id)
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new($"comunidades/{id}", Method.Delete);
                RestResponse response = client.Execute(request);
                return response.IsSuccessful;
            }
            catch
            {
                return false;
            }
        }

        // ==================== EVENTOS ====================

        public static List<Evento> GetEventos()
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new("eventos", Method.Get);
                RestResponse response = client.Execute(request);
                return JsonConvert.DeserializeObject<List<Evento>>(response.Content) ?? new();
            }
            catch { return new(); }
        }

        public static List<Evento> BuscarEventosPorNombre(string nombre)
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new("eventos/buscar", Method.Get);
                request.AddQueryParameter("nombre", nombre);
                RestResponse response = client.Execute(request);
                return JsonConvert.DeserializeObject<List<Evento>>(response.Content) ?? new();
            }
            catch { return new(); }
        }

        public static List<Evento> BuscarEventosPorEstado(string estado)
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new("eventos/estado", Method.Get);
                request.AddQueryParameter("estado", estado);
                RestResponse response = client.Execute(request);
                return JsonConvert.DeserializeObject<List<Evento>>(response.Content) ?? new();
            }
            catch { return new(); }
        }

        public static async Task<Evento?> CrearEvento(string nombre, string descripcion, string ubicacion, string fechaHora, string? rutaImagen)
        {
            RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
            RestRequest request = new("eventos/con-imagen", Method.Post);

            var eventoJson = JsonConvert.SerializeObject(new
            {
                nombre,
                descripcion,
                ubicacion,
                fechaHora,
                estado = "ACTIVO"
            });
            request.AddParameter("evento", eventoJson, ParameterType.GetOrPost);

            if (!string.IsNullOrEmpty(rutaImagen) && File.Exists(rutaImagen))
            {
                request.AddFile("imagen", rutaImagen);
            }

            request.AlwaysMultipartFormData = true;

            RestResponse response = await client.ExecuteAsync(request);

            if (response.IsSuccessful)
                return JsonConvert.DeserializeObject<Evento>(response.Content);

            return null;
        }

        public static bool EliminarEvento(int id)
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new($"eventos/{id}", Method.Delete);
                RestResponse response = client.Execute(request);
                return response.IsSuccessful;
            }
            catch
            {
                return false;
            }
        }

        public static async Task<bool> CancelarEvento(int id)
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new($"eventos/{id}/cancelar", Method.Patch);
                RestResponse response = await client.ExecuteAsync(request);
                return response.IsSuccessful;
            }
            catch
            {
                return false;
            }
        }

        public static async Task<bool> CancelarEventoConMotivo(int id, string motivo)
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new($"eventos/{id}/cancelar-con-motivo", Method.Patch);
                var body = JsonConvert.SerializeObject(new { motivo });
                request.AddParameter("application/json", body, ParameterType.RequestBody);
                RestResponse response = await client.ExecuteAsync(request);
                return response.IsSuccessful;
            }
            catch
            {
                return false;
            }
        }

        public static async Task<bool> EnviarARevision(int id, string motivo)
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new($"eventos/{id}/en-revision", Method.Patch);
                var body = JsonConvert.SerializeObject(new { motivo });
                request.AddParameter("application/json", body, ParameterType.RequestBody);
                RestResponse response = await client.ExecuteAsync(request);
                return response.IsSuccessful;
            }
            catch
            {
                return false;
            }
        }

        public static async Task<bool> AceptarEvento(int id)
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new($"eventos/{id}/aprobar", Method.Patch);
                RestResponse response = await client.ExecuteAsync(request);
                return response.IsSuccessful;
            }
            catch
            {
                return false;
            }
        }

        public static async Task<bool> FinalizarEvento(int id)
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new($"eventos/{id}/finalizar", Method.Patch);
                RestResponse response = await client.ExecuteAsync(request);
                return response.IsSuccessful;
            }
            catch
            {
                return false;
            }
        }
    

        // ==================== RETOS / PUNTOS ====================

        public static List<Reto> GetRetos()
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new("retos", Method.Get);
                RestResponse response = client.Execute(request);
                return JsonConvert.DeserializeObject<List<Reto>>(response.Content) ?? new();
            }
            catch { return new(); }
        }

        public static List<TransaccionPuntos> GetHistorialPuntos(int usuarioId)
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new($"usuarios/{usuarioId}/puntos/historial", Method.Get);
                RestResponse response = client.Execute(request);
                return JsonConvert.DeserializeObject<List<TransaccionPuntos>>(response.Content) ?? new();
            }
            catch { return new(); }
        }

        public static int GetSaldoPuntos(int usuarioId)
        {
            try
            {
                RestClient client = new(Properties.Settings.Default.ApiRestEndPoint);
                RestRequest request = new($"usuarios/{usuarioId}/puntos/saldo", Method.Get);
                RestResponse response = client.Execute(request);
                if (response.IsSuccessful && int.TryParse(response.Content, out int saldo))
                    return saldo;
                return 0;
            }
            catch { return 0; }
        }
    }
}
