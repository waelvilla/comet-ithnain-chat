package com.ithnain.ithnainbeta;

import android.app.Application;
import android.content.Context;
import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
// import com.imagepicker.ImagePickerPackage;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.soloader.SoLoader;
import com.facebook.FacebookSdk;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Arrays;
import com.ithnain.ithnainbeta.generated.BasePackageList;
import org.unimodules.adapters.react.ModuleRegistryAdapter;
import org.unimodules.adapters.react.ReactModuleRegistryProvider;
import org.unimodules.core.interfaces.SingletonModule;
import com.microsoft.codepush.react.CodePush;
import com.facebook.react.modules.i18nmanager.I18nUtil;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.core.AppSettings;
import com.cometchat.pro.models.User;
import com.cometchat.pro.uikit.ui_settings.UIKitSettings;

import com.reactnativecommunity.rctaudiotoolkit.AudioPackage;
import com.wix.reactnativenotifications.RNNotificationsPackage;
import com.oblador.vectoricons.VectorIconsPackage;
import org.devio.rn.splashscreen.SplashScreenReactPackage;
// import com.brentvatne.react.ReactVideoPackage;
import io.github.elyx0.reactnativedocumentpicker.DocumentPickerPackage;

public class MainApplication extends Application implements ReactApplication {
  private final ReactModuleRegistryProvider mModuleRegistryProvider = new ReactModuleRegistryProvider(new BasePackageList().getPackageList(), null);
  private String appID = "1995222bd7a6ff6c"; // Replace with your App ID
  private String region = "eu"; // Replace with your App Region ("eu" or "us")
  String UID = "8c53e4ed-023e-41bf-85fd-747fa3eaeefc"; // Replace with the UID of the user to login
  String authKey = "490b23575a1d6503d06173135beacf339a859b76"; // Replace with your App Auth Key

  private final ReactNativeHost mReactNativeHost =
      new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
          return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
          @SuppressWarnings("UnnecessaryLocalVariable")
          List<ReactPackage> packages = new PackageList(this).getPackages();
          // Packages that cannot be autolinked yet can be added manually here, for example:
          // packages.add(new MyReactNativePackage());
          List<ReactPackage> unimodules = Arrays.<ReactPackage>asList(
            new ModuleRegistryAdapter(mModuleRegistryProvider)
          );
          packages.addAll(unimodules);
          packages.add(new CometChatPackage());
          return packages;
        }

        @Override
        protected String getJSMainModuleName() {
          return "index";
        }


        // 2. Override the getJSBundleFile method in order to let
        // the CodePush runtime determine where to get the JS
        // bundle location from on each app start
        @Override
        protected String getJSBundleFile() {
            return CodePush.getJSBundleFile();
        }
      };

  @Override
  public ReactNativeHost getReactNativeHost() {
    return mReactNativeHost;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    // FORCE LTR
    I18nUtil sharedI18nUtilInstance = I18nUtil.getInstance();
    sharedI18nUtilInstance.allowRTL(getApplicationContext(), false);
    SoLoader.init(this, /* native exopackage */ false);
    initializeFlipper(this, getReactNativeHost().getReactInstanceManager());

    // Creating Notification Channels
    createNotificationChannels();

    initCometChat(this);
  }

  public void initCometChat(Context context) {
    AppSettings appSettings= new AppSettings.AppSettingsBuilder()
    .subscribePresenceForAllUsers()
    .setRegion(region)
    .autoEstablishSocketConnection(true)
    .build();
  
  CometChat.init(context, appID,appSettings, new CometChat.CallbackListener<String>() {
    @Override
    public void onSuccess(String successMessage) {
      Log.d("COMETCHAT", "Initialization completed successfully");
      UIKitSettings.setAppID(appID);
      UIKitSettings.setAuthKey(authKey);
      CometChat.setSource("ui-kit","android","java");
      loginUser();
    }
    @Override
    public void onError(CometChatException e) {
      Log.d("COMETCHAT", "Initialization failed with exception: " + e.getMessage());
    }
  });
    UIKitSettings uiKitSettings = new UIKitSettings(this);
  }

  public void loginUser() {
    if (CometChat.getLoggedInUser() == null) {
        CometChat.login(UID, authKey, new CometChat.CallbackListener<User>() {

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
  public void createNotificationChannels() {
    // https://github.com/invertase/react-native-firebase/issues/2791
    NotificationChannel newMessageChannel = new NotificationChannel("newMessage", "newMessage",
        NotificationManager.IMPORTANCE_HIGH);
    newMessageChannel.setShowBadge(true);
    newMessageChannel.setDescription("");
    newMessageChannel.enableVibration(true);
    newMessageChannel.setVibrationPattern(new long[] { 400, 200, 400 });
    newMessageChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);

    NotificationChannel appointmentChnnel = new NotificationChannel("appointment", "appointment",
        NotificationManager.IMPORTANCE_HIGH);
    appointmentChnnel.setShowBadge(true);
    appointmentChnnel.setDescription("");
    appointmentChnnel.enableVibration(true);
    appointmentChnnel.setVibrationPattern(new long[] { 400, 200, 400 });
    appointmentChnnel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);

    NotificationManager manager = getSystemService(NotificationManager.class);
    manager.createNotificationChannel(newMessageChannel);
    manager.createNotificationChannel(appointmentChnnel);
  }

  /**
   * Loads Flipper in React Native templates. Call this in the onCreate method with something like
   * initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
   *
   * @param context
   * @param reactInstanceManager
   */
  private static void initializeFlipper(
      Context context, ReactInstanceManager reactInstanceManager) {
    if (BuildConfig.DEBUG) {
      try {
        /*
         We use reflection here to pick up the class that initializes Flipper,
        since Flipper library is not available in release mode
        */
        Class<?> aClass = Class.forName("com.ithnain.ithnainbeta.ReactNativeFlipper");
        aClass
            .getMethod("initializeFlipper", Context.class, ReactInstanceManager.class)
            .invoke(null, context, reactInstanceManager);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }
}
