package mongkici.com.flutter_sunmi_bacode_scanner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BarcodeScannerReceiver(val streamHandler: BarcodeScannerStreamHandler): BroadcastReceiver() {

  override fun onReceive(context: Context?, intent: Intent?) {
    val data = intent!!.getStringExtra("data")
    streamHandler._eventSink?.success(data)
  }

}