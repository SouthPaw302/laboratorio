package com.marinemesh.app

import android.app.Activity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import java.net.NetworkInterface

class MainActivity : Activity() {
  private var coordinator: MeshCoordinator? = null
  private lateinit var browser: WebView
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    browser = WebView(this)
    browser.settings.javaScriptEnabled = true
    browser.settings.domStorageEnabled = true
    browser.settings.allowFileAccessFromFileURLs = false
    browser.settings.allowUniversalAccessFromFileURLs = false
    browser.webChromeClient = WebChromeClient()
    browser.webViewClient = WebViewClient()
    browser.addJavascriptInterface(Bridge(),"MarineNative")
    setContentView(browser)
    browser.loadUrl("file:///android_asset/www/index.html")
  }
  inner class Bridge {
    @JavascriptInterface fun startCoordinator(): String = synchronized(this@MainActivity) {
      if (coordinator != null) return@synchronized "OK"
      try { coordinator = MeshCoordinator(8765).also { it.start() }; "OK" }
      catch(e:Exception) { "Unable to start: ${e.message}" }
    }
    @JavascriptInterface fun stopCoordinator() = synchronized(this@MainActivity) {
      coordinator?.shutdownSafely(); coordinator=null
    }
    @JavascriptInterface fun getLocalIp(): String = try {
      NetworkInterface.getNetworkInterfaces().toList().flatMap{it.inetAddresses.toList()}
        .firstOrNull{!it.isLoopbackAddress && it is java.net.Inet4Address && it.isSiteLocalAddress}?.hostAddress ?: "unknown"
    }catch(_:Exception){"unknown"}
  }
  override fun onDestroy() { coordinator?.shutdownSafely(); browser.destroy(); super.onDestroy() }
}
