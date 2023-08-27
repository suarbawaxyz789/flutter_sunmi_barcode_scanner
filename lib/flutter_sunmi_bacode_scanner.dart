import 'dart:async';

import 'package:flutter/services.dart';

class FlutterSunmiBacodeScanner {
  late MethodChannel _platform;
  late EventChannel _eventChannel;

  FlutterSunmiBacodeScanner() {
    _platform = const MethodChannel('flutter_sunmi_bacode_scanner/scanner');
    _eventChannel = EventChannel('flutter_sunmi_bacode_scanner/scanner_result');
  }

//  static Future<String> get platformVersion async {
//    final String version = await _channel.invokeMethod('getPlatformVersion');
//    return version;
//  }

  Future<bool?> hasBarcodeScanner() async{
    return await _platform.invokeMethod('hasBarcodeScanner');
  }

  Stream<Object?> scanBarcode() async* {
    yield await _platform.invokeMethod('scanBarcode');

    yield* _eventChannel.receiveBroadcastStream() as Stream<Object>;
  }
}
