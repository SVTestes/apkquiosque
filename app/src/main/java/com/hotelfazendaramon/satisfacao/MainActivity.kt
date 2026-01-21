package com.hotelfazendaramon.satisfacao

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private val SENHA_ADMIN = "3522" 
    private val URL_1 = "https://hotelfazendaramon.com.br/pesquisa-de-satisfacao/"
    private val URL_2 = "https://hotelfazendaramon.com.br/formulario-de-satisfacao/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ativa o modo tela cheia imediatamente
        esconderBarrasSistema()

        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url.toString()
                // Permite navegar apenas se for uma das duas URLs autorizadas
                return !(url.contains(URL_1) || url.contains(URL_2))
            }
        }

        mostrarSelecaoDeUrl()
    }

    // Esconde a barra de notificações e o menu inferior (Immersive Mode)
    private fun esconderBarrasSistema() {
        window.setDecorFitsSystemWindows(false)
        val controller = window.insetsController
        if (controller != null) {
            controller.hide(WindowInsets.Type.systemBars()) // Esconde barras
            controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    // Garante que as barras continuem escondidas se o usuário interagir com a tela
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) esconderBarrasSistema()
    }

    private fun mostrarSelecaoDeUrl() {
        val opcoes = arrayOf("Pesquisa Check-in", "Pesquisa Check-out")
        AlertDialog.Builder(this)
            .setTitle("Selecione o Sistema")
            .setCancelable(false)
            .setItems(opcoes) { _, which ->
                when (which) {
                    0 -> webView.loadUrl(URL_1)
                    1 -> webView.loadUrl(URL_2)
                }
            }
            .show()
    }

    override fun onBackPressed() {
        val input = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("Senha Requerida")
            .setView(input)
            .setPositiveButton("Sair") { _, _ ->
                if (input.text.toString() == SENHA_ADMIN) finishAffinity()
                else esconderBarrasSistema()
            }
            .setNegativeButton("Voltar") { _, _ -> esconderBarrasSistema() }
            .show()
    }
}
