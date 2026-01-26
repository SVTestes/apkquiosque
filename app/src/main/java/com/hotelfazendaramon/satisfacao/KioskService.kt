package com.hotelfazendaramon.satisfacao

import android.accessibilityservice.AccessibilityService
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class KioskService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}
    override fun onInterrupt() {}

    // Intercepta e anula os botões Home e Apps Recentes
    override fun onKeyEvent(event: KeyEvent): Boolean {
        val keyCode = event.keyCode
        if (keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_APP_SWITCH) {
            return true // Bloqueia a ação do botão
        }
        return super.onKeyEvent(event)
    }
}
