using System;
using System.Globalization;
using System.Windows.Data;
using System.Windows.Media;

namespace PrototipadoEscritorio.Converters
{
    public class ColorTipoToBrushConverter : IValueConverter
    {
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            if (value is string colorTipo)
            {
                return colorTipo switch
                {
                    "amarillo" => new SolidColorBrush(Color.FromRgb(0xF0, 0xA0, 0x30)),
                    "rojo" => new SolidColorBrush(Color.FromRgb(0xE7, 0x4C, 0x3C)),
                    _ => new SolidColorBrush(Color.FromRgb(0x3B, 0xB2, 0x73)),
                };
            }
            return new SolidColorBrush(Color.FromRgb(0x3B, 0xB2, 0x73));
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }
}
