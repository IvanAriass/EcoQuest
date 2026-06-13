package com.ecoquest.app.managers

import com.ecoquest.app.ui.components.general.ToastData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalToastManager @Inject constructor() {

    private val _toastEvent = MutableSharedFlow<ToastData>(extraBufferCapacity = 4)
    val toastEvent: SharedFlow<ToastData> = _toastEvent.asSharedFlow()

    fun show(message: String, points: Int = 0) {
        _toastEvent.tryEmit(ToastData(message = message, points = points))
    }
}
