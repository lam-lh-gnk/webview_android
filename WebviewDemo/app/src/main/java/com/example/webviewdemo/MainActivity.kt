package com.example.webviewdemo

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.browser.customtabs.CustomTabsIntent

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private  lateinit var settings: WebSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = WebView(this)
        setContentView(webView)

        // Set up for loading Flutter web
        settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        webView.webViewClient = CustomWebViewClient()

        // Load a website
        webView.loadUrl("https://web.tmsmart.botsignal.com/#/:soh_id/home/appointment")
    }

    inner class CustomWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (url.startsWith("tel:")) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse(url)
                startActivity(intent)
                return true
            } else if (url.startsWith("https://ssl.fdoc.jp")) {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(this@MainActivity, Uri.parse(url))
                return true
            }

            return false
        }
    }
}
