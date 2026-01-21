package com.hotelfazendaramon.satisfacao

import android.app.AlertDialog
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private val SENHA_ADMIN = "3522"
    private val URL_SISTEMA = "https://hotelfazendaramon.com.br/pesquisa-de-satisfacao/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url.toString()
                return !url.contains(URL_SISTEMA) // Bloqueia se não for a sua URL
            }
        }
        webView.loadUrl(URL_SISTEMA)
    }

    override fun onBackPressed() {
        val input = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("Senha Requerida")
            .setView(input)
            .setPositiveButton("Sair") { _, _ ->
                if (input.text.toString() == SENHA_ADMIN) finishAffinity()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
