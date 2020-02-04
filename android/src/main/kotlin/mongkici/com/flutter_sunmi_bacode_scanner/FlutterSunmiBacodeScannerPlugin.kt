package mongkici.com.flutter_sunmi_bacode_scanner

import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import android.content.Intent
import android.content.IntentFilter
import android.app.Service
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import com.sunmi.scanner.IScanInterface
import io.flutter.plugin.common.EventChannel
import timber.log.Timber

class BarcodeScannerStreamHandler(): EventChannel.StreamHandler {
  var _eventSink: EventChannel.EventSink? = null

  override fun onListen(p0: Any?, p1: EventChannel.EventSink?) {
    this._eventSink = p1
  }

  override fun onCancel(p0: Any?) {
    this._eventSink = null
  }

}

/** FlutterSunmiBacodeScannerPlugin */
public class FlutterSunmiBacodeScannerPlugin: FlutterPlugin, MethodCallHandler {
//  private var scanInterface: IScanInterface?  = null
//  private val streamHandler = BarcodeScannerStreamHandler()
//  private val BARCODE_SCANNER_EVENT_CHANNEL = "flutter_sunmi_bacode_scanner/scanner_result"
  private val METHOD_CHANNEL = "flutter_sunmi_bacode_scanner/scanner"

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    val channel = MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), METHOD_CHANNEL)
    channel.setMethodCallHandler(FlutterSunmiBacodeScannerPlugin());
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  companion object {
    private val BARCODE_SCANNER_EVENT_CHANNEL = "flutter_sunmi_bacode_scanner/scanner_result"
    private val METHOD_CHANNEL = "flutter_sunmi_bacode_scanner/scanner"
    private var scanInterface: IScanInterface?  = null
    private val streamHandler = BarcodeScannerStreamHandler()

    private val scanner = object : ServiceConnection {
      override fun onServiceConnected(name: ComponentName, service: IBinder) {
        scanInterface = IScanInterface.Stub.asInterface(service)
        Timber.w("Scanner Service Connected!")
      }

      override fun onServiceDisconnected(name: ComponentName) {
        Timber.w( "Scanner Service Disconnected!")
        scanInterface = null
      }
    }

    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), METHOD_CHANNEL)
      channel.setMethodCallHandler(FlutterSunmiBacodeScannerPlugin())

      val intent = Intent()
      intent.setPackage("com.sunmi.scanner")
      intent.action = "com.sunmi.scanner.IScanInterface"
      registrar.activity().applicationContext.bindService(intent, scanner, Service.BIND_AUTO_CREATE)

      EventChannel(registrar.messenger(), BARCODE_SCANNER_EVENT_CHANNEL).setStreamHandler(streamHandler)
      registrar.activity().registerReceiver(BarcodeScannerReceiver(streamHandler), IntentFilter("com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED"))
    }
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    when (call.method) {
      "scanBarcode" -> {
        scanInterface?.scan();
        result.success(null)
      }

      "hasBarcodeScanner" -> {
        val model = if(scanInterface != null) scanInterface?.scannerModel else -1
        result.success(model != -1 && model != 100)
      }

    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
  }
}
