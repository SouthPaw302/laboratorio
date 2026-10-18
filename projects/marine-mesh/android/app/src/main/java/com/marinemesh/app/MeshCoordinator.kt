package com.marinemesh.app

import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import org.json.JSONArray
import org.json.JSONObject
import java.net.InetSocketAddress
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.sin
import kotlin.math.cos

/** Read-only simulated LAN telemetry, not for navigation. */
class MeshCoordinator(port: Int) : WebSocketServer(InetSocketAddress("0.0.0.0",port)) {
  private val ticker = Executors.newSingleThreadScheduledExecutor()
  override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {}
  override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {}
  override fun onMessage(conn: WebSocket, message: String) {} // No remote commands
  override fun onError(conn: WebSocket?, ex: Exception) { android.util.Log.w("MarineMesh", "WebSocket", ex) }
  override fun onStart() {
    ticker.scheduleAtFixedRate({
      val t = System.currentTimeMillis()/1000.0
      val targets = JSONArray()
      for (i in 0..2) targets.put(JSONObject().put("id","T${i+1}").put("bearing",(40+i*112+t*.22)%360).put("rangeNm",.65+i*.24))
      val payload = JSONObject().put("kind","telemetry").put("demo",true).put("timestamp",System.currentTimeMillis())
        .put("heading",242+3*sin(t/16)).put("sog",5.8+.2*sin(t/6))
        .put("depthFt",24.6+.5*cos(t/9)).put("windKn",12+1.5*cos(t/11))
        .put("anchorDistanceFt",45+8*sin(t/15)).put("targets",targets).toString()
      connections.filter{it.isOpen}.forEach { try { it.send(payload) } catch (_:Exception) {} }
    },0,1,TimeUnit.SECONDS)
  }
  fun shutdownSafely() { ticker.shutdownNow(); try { stop(1000) } catch (_:Exception) {} }
}
