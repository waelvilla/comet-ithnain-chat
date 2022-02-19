
@objc(Navigation)
class Navigation: NSObject {
  
  @objc
  func navigateToChat(_ props: NSDictionary) -> Void {
    let chatVC = CometChatViewController()
    chatVC.userId = props["userId"] as? String ?? ""
    chatVC.userName = props["userName"] as? String ?? ""

    DispatchQueue.main.async {
      let navController = UINavigationController(rootViewController: chatVC)
      navController.modalPresentationStyle = .fullScreen
      let topController = UIApplication.topMostViewController()
      topController?.present(navController, animated: true, completion: nil)
    }
  }
  
  @objc
   func goBack() -> Void {
     DispatchQueue.main.async {
       let topController = UIApplication.topMostViewController()
       topController?.dismiss(animated: true, completion: nil)
     }
   }
  
}
