import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_sunmi_bacode_scanner/flutter_sunmi_bacode_scanner.dart';

void main() {
  const MethodChannel channel = MethodChannel('flutter_sunmi_bacode_scanner');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    // expect(await FlutterSunmiBacodeScanner.platformVersion, '42');
  });
}
