package com.pmdm.proyectobase.ui.features.home

sealed interface HomeEvent {
    object GoHome : HomeEvent
    object GoComunidad : HomeEvent
    object GoTienda : HomeEvent
    object OpenPerfil : HomeEvent
    object OpenSettings : HomeEvent
}
