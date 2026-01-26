package com.hotelfazendaramon.satisfacao

import android.app.AlertDialog
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private val SENHA_ADMIN = "3522" // SUA SENHA AQUI
    private val URL_CHECKIN = "https://hotelfazendaramon.com.br/pesquisa-de-satisfacao/"
    private val URL_CHECKOUT = "https://hotelfazendaramon.com.br/formulario-de-satisfacao/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = WebViewClient()

        mostrarSelecaoDeUrl()
        aplicarModoImersivo()
    }

    private fun mostrarSelecaoDeUrl() {
        val opcoes = arrayOf("Pesquisa Check-in", "Pesquisa Check-out")
        AlertDialog.Builder(this)
            .setTitle("Hotel Fazenda Ramon - Selecione")
            .setCancelable(false)
            .setItems(opcoes) { _, which ->
                when (which) {
                    0 -> webView.loadUrl(URL_CHECKIN)
                    1 -> webUrl.loadUrl(URL_CHECKOUT)
                }
            }
            .show()
    }

    private fun aplicarModoImersivo() {
        window.setDecorFitsSystemWindows(false)
        window.insetsController?.let {
            it.hide(WindowInsets.Type.systemBars())
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) aplicarModoImersivo()
    }

    override fun onBackPressed() {
        exibirPromptSenha()
    }

    private fun exibirPromptSenha() {
        val input = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("Área Restrita")
            .setMessage("Digite a senha para sair:")
            .setView(input)
            .setCancelable(false)
            .setPositiveButton("Sair") { _, _ ->
                if (input.text.toString() == SENHA_ADMIN) finishAffinity()
                else aplicarModoImersivo()
            }
            .setNegativeButton("Voltar") { _, _ -> aplicarModoImersivo() }
            .show()
    }
}
