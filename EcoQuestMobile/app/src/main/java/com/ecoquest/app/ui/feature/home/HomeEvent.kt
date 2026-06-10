package com.ecoquest.app.ui.feature.home

sealed interface HomeEvent {
    data object GoHome : HomeEvent
    data object GoComunidad : HomeEvent
    data object GoTienda : HomeEvent
    data object OpenPerfil : HomeEvent
    data object OpenSettings : HomeEvent
}
