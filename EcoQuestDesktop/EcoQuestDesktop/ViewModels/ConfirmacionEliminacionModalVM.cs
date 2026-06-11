using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using System;
using System.Windows.Media;

namespace EcoQuestDesktop.ViewModels
{
    public partial class ConfirmacionEliminacionModalVM : ObservableObject
    {
        public event Action<object>? OnConfirmar;
        public event Action? OnCancelar;

        [ObservableProperty]
        private string _tituloModal = string.Empty;

        [ObservableProperty]
        private string _mensajeConfirmacion = string.Empty;

        [ObservableProperty]
        private SolidColorBrush _colorHeader = BrushFromHex("#AF4C4E");

        [ObservableProperty]
        private SolidColorBrush _colorConfirmar = BrushFromHex("#AF4C4E");

        [ObservableProperty]
        private SolidColorBrush _colorConfirmarHover = BrushFromHex("#8B3A3C");

        [ObservableProperty]
        private SolidColorBrush _colorConfirmarPress = BrushFromHex("#6B2A2C");

        [ObservableProperty]
        private string _textoConfirmar = "Eliminar";

        private object? _itemAEliminar;

        private static SolidColorBrush BrushFromHex(string hex)
        {
            return new SolidColorBrush((Color)ColorConverter.ConvertFromString(hex));
        }

        public void CargarConfirmacion(string titulo, string mensaje, object item)
        {
            TituloModal = titulo;
            MensajeConfirmacion = mensaje;
            _itemAEliminar = item;
            ColorHeader = BrushFromHex("#AF4C4E");
            ColorConfirmar = BrushFromHex("#AF4C4E");
            ColorConfirmarHover = BrushFromHex("#8B3A3C");
            ColorConfirmarPress = BrushFromHex("#6B2A2C");
            TextoConfirmar = "Eliminar";
        }

        public void CargarConfirmacion(string titulo, string mensaje, object item,
            string colorHeader, string colorConfirmar, string colorConfirmarHover, string colorConfirmarPress, string textoConfirmar)
        {
            TituloModal = titulo;
            MensajeConfirmacion = mensaje;
            _itemAEliminar = item;
            ColorHeader = BrushFromHex(colorHeader);
            ColorConfirmar = BrushFromHex(colorConfirmar);
            ColorConfirmarHover = BrushFromHex(colorConfirmarHover);
            ColorConfirmarPress = BrushFromHex(colorConfirmarPress);
            TextoConfirmar = textoConfirmar;
        }

        [RelayCommand]
        public void Confirmar()
        {
            OnConfirmar?.Invoke(_itemAEliminar);
            Limpiar();
        }

        [RelayCommand]
        public void Cancelar()
        {
            OnCancelar?.Invoke();
            Limpiar();
        }

        private void Limpiar()
        {
            TituloModal = string.Empty;
            MensajeConfirmacion = string.Empty;
            _itemAEliminar = null;
        }
    }
}
