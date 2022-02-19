import React
import FlipperKit
import CometChatPro
import Firebase
import FBSDKCoreKit


#if FB_SONARKIT_ENABLED
private func InitializeFlipper(_ application: UIApplication?) {
    let client = FlipperClient.shared()
    let layoutDescriptorMapper = SKDescriptorMapper()
    client?.add(FlipperKitLayoutPlugin(rootNode: application, with: layoutDescriptorMapper))
    client?.add(FKUserDefaultsPlugin(suiteName: nil))
    client?.add(FlipperKitReactPlugin())
    client?.add(FlipperKitNetworkPlugin(networkAdapter: SKIOSNetworkAdapter()))
    client?.start()
}

#endif

//private func setupFb() {
//
//}

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
 
  var window: UIWindow?
  var bridge: RCTBridge!
  var moduleRegistryAdapter: UMModuleRegistryAdapter!
   let appId: String = "2015344801fbf378"
   let region: String = "eu"

  #if DEBUG
  var jsCodeLocation: URL = RCTBundleURLProvider.sharedSettings().jsBundleURL(forBundleRoot: "index", fallbackResource:nil)
  #else
  var jsCodeLocation: URL = CodePush.bundleURL()
  #endif

  func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
      RNNotifications.startMonitorNotifications()
      if FirebaseApp.app() == nil {
        FirebaseApp.configure()
      }

    ApplicationDelegate.shared.application(
                application,
                didFinishLaunchingWithOptions: launchOptions
            )
    let mySettings = AppSettings.AppSettingsBuilder().subscribePresenceForAllUsers().setRegion(region:region).build()
    CometChat(appId: appId ,appSettings: mySettings,onSuccess: { (isSuccess) in
     print("===========CometChat Pro SDK intialise successfully.===========")
     }) { (error) in
      print("===========CometChat Pro SDK failed intialise with error: \(error.errorDescription)===========")
    }
    self.moduleRegistryAdapter = UMModuleRegistryAdapter(moduleRegistryProvider: UMModuleRegistryProvider())

      let rootViewController = getVCFromModuleName("ithnain", nil, launchOptions)
      self.window = UIWindow(frame: UIScreen.main.bounds)
      self.window?.rootViewController = rootViewController
      self.window?.makeKeyAndVisible()
      return true
  }

    func getVCFromModuleName(_ moduleName: String,_ initialProperties: NSDictionary?, _ launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> UIViewController {
        var props: [NSObject : AnyObject]? = nil
        if (initialProperties != nil) {
          props = initialProperties! as [NSObject : AnyObject]
        }
      let rootView = RCTRootView(bundleURL: jsCodeLocation, moduleName: moduleName, initialProperties: props , launchOptions: launchOptions)
        let rootViewController = UIViewController()
        rootViewController.view = rootView
        return rootViewController
    }
  func navigateToRN(_ moduleName: String,_ initialProperties: NSDictionary?, _ launchOptions: [UIApplication.LaunchOptionsKey: Any]?) {
      let vc = getVCFromModuleName(moduleName, initialProperties, launchOptions)
      DispatchQueue.main.async {
          let navController = UINavigationController(rootViewController: vc)
          navController.modalPresentationStyle = .fullScreen
          navController.setNavigationBarHidden(true, animated: false)
          let topController = UIApplication.topMostViewController()
          topController?.present(navController, animated: true, completion: nil)
      }
  }
  func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
      RNNotifications.didRegisterForRemoteNotifications(withDeviceToken: deviceToken)
  }
  func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: Error) {
      RNNotifications.didFailToRegisterForRemoteNotificationsWithError(error)
  }
  func application(
      _ app: UIApplication,
      open url: URL,
      options: [UIApplication.OpenURLOptionsKey : Any] = [:]
  ) -> Bool {
      ApplicationDelegate.shared.application(
          app,
          open: url,
          sourceApplication: options[UIApplication.OpenURLOptionsKey.sourceApplication] as? String,
          annotation: options[UIApplication.OpenURLOptionsKey.annotation]
      )
  }
  func extraModules(for bridge: RCTBridge!) -> [RCTBridgeModule]! {
    var extraModules = self.moduleRegistryAdapter.extraModules(for: bridge)
     return extraModules
   }

}

extension UIApplication {
class func topMostViewController(controller: UIViewController? = UIApplication.shared.keyWindow?.rootViewController) -> UIViewController? {
if let navigationController = controller as? UINavigationController {
            return topMostViewController(controller: navigationController.visibleViewController)
        }
if let tabController = controller as? UITabBarController {
            if let selected = tabController.selectedViewController {
                return topMostViewController(controller: selected)
            }
        }
if let presented = controller?.presentedViewController {
            return topMostViewController(controller: presented)
        }
return controller
    }
}

