package com.ecoquest.app.ui.util

import java.time.LocalDate
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

fun formatearFechaChat(fecha: LocalDate, hoy: LocalDate = LocalDate.now()): String {
    return when (fecha) {
        hoy -> "Hoy"
        hoy.minusDays(1) -> "Ayer"
        else -> {
            val diff = hoy.toEpochDay() - fecha.toEpochDay()
            when {
                diff in 2..6 -> fecha.format(DateTimeFormatter.ofPattern("EEEE", Locale("es")))
                    .replaceFirstChar { it.uppercase() }
                else -> fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            }
        }
    }
}

private fun parseFecha(fechaHora: String): LocalDateTime? {
    for (parser in PARSERS) {
        try {
            return LocalDateTime.parse(fechaHora, parser)
        } catch (_: DateTimeParseException) {}
    }
    return null
}
