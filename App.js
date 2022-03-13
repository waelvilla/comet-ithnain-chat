import React, {useEffect} from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  NativeModules,
  StyleSheet,
} from 'react-native';
import SplashScreen from 'react-native-splash-screen';

const CometChatModule = NativeModules.CometChatModule;

export default function App() {
  useEffect(() => {
    SplashScreen.hide();
  }, []);

  return (
    <View style={styles.container}>
      <Text>App.js</Text>
      <TouchableOpacity
        style={styles.button}
        onPress={() => {
          CometChatModule.navigateToChat({
            userId: '1233333',
            username: 'USERNAME',
          });
        }}>
        <Text style={{color: 'white'}}>Go to Chat</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  button: {
    borderRadius: 10,
    backgroundColor: 'blue',
    padding: 10,
    marginTop: 20,
  },
});
