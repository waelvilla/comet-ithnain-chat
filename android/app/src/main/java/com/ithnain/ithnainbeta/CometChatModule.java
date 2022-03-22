package com.ithnain.ithnainbeta;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import java.util.Map;
import java.util.HashMap;
import android.util.Log;
import android.content.Intent;
import android.app.Activity;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;
import com.cometchat.pro.uikit.ui_components.cometchat_ui.CometChatUI;
import com.cometchat.pro.uikit.ui_components.messages.message_list.CometChatMessageListActivity;
import com.cometchat.pro.uikit.ui_resources.constants.UIKitConstants;
import com.cometchat.pro.constants.CometChatConstants;

public class CometChatModule extends ReactContextBaseJavaModule {
    ReactApplicationContext context = getReactApplicationContext();
    private static ReactApplicationContext reactContext;
    CometChatModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
    }
   @Override
    public String getName() {
        return "CometChatModule";
    }
    @ReactMethod
    public void navigateToChat(ReadableMap props) {
        String userId = props.getString("userId");
        String username = props.getString("username");
        Log.d("CometChatModule", "RECEIVED PROPS:"+ userId+ ":"+username);
        Intent intent = new Intent(context, CometChatMessageListActivity.class);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(UIKitConstants.IntentStrings.UID, userId);
            intent.putExtra(UIKitConstants.IntentStrings.AVATAR,"");
            intent.putExtra(UIKitConstants.IntentStrings.STATUS, "offline");
            intent.putExtra(UIKitConstants.IntentStrings.NAME,username );
            intent.putExtra(UIKitConstants.IntentStrings.LINK,"");
            intent.putExtra(UIKitConstants.IntentStrings.TYPE, CometChatConstants.RECEIVER_TYPE_USER);
             context.startActivity(intent);
        }
    }


    @ReactMethod
    public void login(String authToken) {
            if (CometChat.getLoggedInUser() == null) {
        CometChat.login(authToken, new CometChat.CallbackListener<User>() {

          @Override
          public void onSuccess(User user) {
            Log.d("COMETCHAT", "Login Successful : " + user.toString());
      }

      @Override
        public void onError(CometChatException e) {
            Log.d("COMETCHAT", "Login failed with exception: " + e.getMessage());
      }
    });
    } else {
      // User already logged in
            Log.d("COMETCHAT", "user already logged in " );
    }
    }
}