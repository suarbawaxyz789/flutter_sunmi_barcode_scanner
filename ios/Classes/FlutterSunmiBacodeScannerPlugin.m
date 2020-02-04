#import "FlutterSunmiBacodeScannerPlugin.h"
#if __has_include(<flutter_sunmi_bacode_scanner/flutter_sunmi_bacode_scanner-Swift.h>)
#import <flutter_sunmi_bacode_scanner/flutter_sunmi_bacode_scanner-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "flutter_sunmi_bacode_scanner-Swift.h"
#endif

@implementation FlutterSunmiBacodeScannerPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterSunmiBacodeScannerPlugin registerWithRegistrar:registrar];
}
@end
