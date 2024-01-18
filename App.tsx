import React,{
  useState
} from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  useColorScheme,
  View,
  Button,
  NativeModules,
  TextInput
} from 'react-native';


function App(): React.JSX.Element {

  const {CustomModule} = NativeModules;
  const [source,setSource] = useState("");
  const [sourceTime,setSourceTime] = useState("");
  const [des,setDes] = useState("");
  const [desTime,setDesTime] = useState("");

  return (
    <SafeAreaView style={{
      flex: 1,
      backgroundColor: '#fff',
      justifyContent: 'center',
      alignItems: 'center',
    }}>
     <Button title="Click Me" onPress={() => {
      (async () => {
        try{
          let result = await CustomModule.createNotification();
          console.log(result);
        }catch(e) {
          console.log(e);
        }
      })();
     }} ></Button>
     <View></View>
     <View></View>
     <TextInput 
        style={styles.inputView}  
        placeholder='Source'
        onChangeText={setSource}
     ></TextInput> 
     <TextInput 
        onChangeText={setSourceTime}
        style={styles.inputView}
        placeholder='Source Time'
      ></TextInput>
      <TextInput 
        onChangeText={setDes}
        style={styles.inputView}
        placeholder='Destination'
      ></TextInput>
      <TextInput 
        onChangeText={setDesTime}
        style={styles.inputView}
        placeholder='Destination Time'
      ></TextInput>
      <Button
      title='send to native notification'
      onPress={() => {
        (async () => {
          try{
            let result = await CustomModule.updateNotification(source,sourceTime,des,desTime,"6E2486");
            console.log(result);
          }catch(e) {
            console.log(e);
          }
        })(); 
     }}
     ></Button>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
  },
  highlight: {
    fontWeight: '700',
  },
  inputView:{
    width: '100%',
    height: 40,
    backgroundColor: 'white',
    borderWidth: 1,
  }
});

export default App;
