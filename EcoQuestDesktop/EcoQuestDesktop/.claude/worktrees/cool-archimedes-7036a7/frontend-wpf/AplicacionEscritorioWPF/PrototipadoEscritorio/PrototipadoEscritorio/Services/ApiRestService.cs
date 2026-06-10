using Newtonsoft.Json;
using PrototipadoEscritorio.Models;
using RestSharp;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace PrototipadoEscritorio.Services
{
    public class ApiRestService
    {
        public static List<Producto> GetProductos()
        {
            RestClient client = new RestClient(Properties.Settings.Default.ApiRestEndPoint);
            RestRequest request = new RestRequest("productos", Method.Get);
            RestResponse response = client.Execute(request);
            return JsonConvert.DeserializeObject<List<Producto>>(response.Content);
        }

        public static List<Producto> BuscarProductosPorNombre(string nombre)
        {
            RestClient client = new RestClient(Properties.Settings.Default.ApiRestEndPoint);
            RestRequest request = new RestRequest("productos/buscar", Method.Get);
            request.AddQueryParameter("nombre", nombre);
            RestResponse response = client.Execute(request);
            return JsonConvert.DeserializeObject<List<Producto>>(response.Content);
        }

        public static async Task<Producto?> CrearProductoConImagen(string nombre, int precio, string? rutaImagen)
        {
            RestClient client = new RestClient(Properties.Settings.Default.ApiRestEndPoint);
            RestRequest request = new RestRequest("productos/con-imagen", Method.Post);

            var productoJson = JsonConvert.SerializeObject(new { nombre, precio });
            request.AddParameter("producto", productoJson, ParameterType.GetOrPost);

            // Imagen
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
            RestClient client = new RestClient(Properties.Settings.Default.ApiRestEndPoint);
            RestRequest request = new RestRequest($"productos/{id}", Method.Delete);
            RestResponse response = client.Execute(request);
            return response.IsSuccessful;
        }
    }
}
