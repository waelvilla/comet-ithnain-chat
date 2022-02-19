/**
 * @format
 */
import 'react-native-gesture-handler';
import React from 'react';
import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import codePush from 'react-native-code-push';


const reduxApp = () => (
    <App />
);

const codePushOption = {
  checkFrequency: codePush.CheckFrequency.ON_APP_RESUME,
  installMode: codePush.InstallMode.ON_NEXT_RESTART,
};

AppRegistry.registerComponent(appName, () => codePush(codePushOption)(reduxApp));
