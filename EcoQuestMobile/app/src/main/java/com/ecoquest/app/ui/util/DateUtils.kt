package com.ecoquest.app.ui.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

private val FORMATO_LARGO = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy · HH:mm", Locale("es"))
private val FORMATO_CORTO = DateTimeFormatter.ofPattern("d MMM · HH:mm", Locale("es"))

private val PARSERS = listOf(
    DateTimeFormatter.ISO_LOCAL_DATE_TIME,
    DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm", Locale.ENGLISH),
    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),
    DateTimeFormatter.ofPattern("dd/MM HH:mm")
)

fun formatearFechaHora(fechaHora: String): String {
    val dt = parseFecha(fechaHora) ?: return fechaHora
    return dt.format(FORMATO_LARGO)
}

fun formatearFechaHoraCorta(fechaHora: String): String {
    val dt = parseFecha(fechaHora) ?: return fechaHora
    return dt.format(FORMATO_CORTO)
}

private fun parseFecha(fechaHora: String): LocalDateTime? {
    for (parser in PARSERS) {
        try {
            return LocalDateTime.parse(fechaHora, parser)
        } catch (_: DateTimeParseException) {}
    }
    return null
}
