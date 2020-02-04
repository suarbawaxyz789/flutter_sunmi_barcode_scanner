import Flutter
import UIKit

public class SwiftFlutterSunmiBacodeScannerPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "flutter_sunmi_bacode_scanner", binaryMessenger: registrar.messenger())
    let instance = SwiftFlutterSunmiBacodeScannerPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    result("iOS " + UIDevice.current.systemVersion)
  }
}
