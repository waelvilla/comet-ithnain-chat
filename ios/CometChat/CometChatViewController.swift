import UIKit
import CometChatPro

class CometChatViewController: UIViewController  {
  var userId:String = ""
  var userName:String = ""

  @IBAction func onGoBack(_ sender: UIButton) {
      self.dismiss(animated: true, completion: nil)
  }
    @IBAction func onMoveForward(_ sender: UIButton) {
      if let appDelegate: AppDelegate = UIApplication.shared.delegate as? AppDelegate{
          appDelegate.navigateToRN("UserProfile", nil, nil)
      }
    }
  override func viewDidLoad() {
    super.viewDidLoad()
    self.setupView()
  }

  private func setupView() {
    if CometChat.getLoggedInUser() == nil {
      let uid = "f884c441-1cc8-496a-85f6-3602135d91d2"
      let apiKey = "86c5df724da8e965ca6514dd662c46fabc13860d"
      CometChat .login(UID: uid, apiKey: apiKey, onSuccess: { (user) in

        print("===========Login successful : " + user.stringValue())
       self.openChat()

      }) { (error) in
        
        print("===========Login failed with error: " + error.errorDescription);

      }
    } else {
      print("USER ==============")
      print(CometChat.getLoggedInUser()?.stringValue())
      print("Is logged in ===========")
      self.openChat()
    }
    
  }
  func openChat() {
    DispatchQueue.main.async {
//    let messageList = CometChatMessageList()
//    let user = User(uid: self.userId, name: self.userName)
//    messageList.set(conversationWith: user, type: .user)
//      let navigationController = UINavigationController(rootViewController:messageList)
//    self.present(navigationController, animated:true, completion:nil)
    
//    self.navigationController?.pushViewController(messageList, animated: true)
      // self.present(navigationController, animated:true, completion:nil)

    // let cometChatUI = CometChatUI()
    // cometChatUI.setup(withStyle: .fullScreen)
    // self.present(cometChatUI, animated: true, completion: nil)
    // let conversationList = CometChatConversationList()
    // let navigationController = UINavigationController(rootViewController:conversationList)
    // conversationList.set(title:"Chats", mode: .automatic)
    // self.present(navigationController, animated:true, completion:nil)
    }
  }
}
