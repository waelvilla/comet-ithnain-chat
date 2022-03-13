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
import com.cometchat.pro.uikit.ui_components.messages.message_list.CometChatMessageListActivity;
import com.cometchat.pro.uikit.ui_components.cometchat_ui.CometChatUI;

public class CometChatModule extends ReactContextBaseJavaModule {
    ReactApplicationContext context = getReactApplicationContext();
    CometChatModule(ReactApplicationContext context) {
       super(context);
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
        Intent intent = new Intent(context, CometChatUI.class);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // This next line crashes the app
            // context.startActivity(intent);
        }
    }
}