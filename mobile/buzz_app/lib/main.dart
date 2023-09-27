import 'dart:io';
import 'package:buzz_app/api/google_signin_api.dart';
import 'package:buzz_app/views/input_view/edit_profile_view.dart';
import 'package:buzz_app/views/input_view/input_view.dart';
import '../../widgets/posts_view/posts_view.dart';
import '../../widgets/posts_view/User_Profile_View.dart';
import 'package:flutter/material.dart';
import '../../messages.dart';
import 'package:flutter/material.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:shared_preferences/shared_preferences.dart';
<<<<<<< HEAD
import 'dart:convert';
import 'package:http/http.dart' as http;
//import 'package:jwt_decoder/jwt_decoder.dart';



=======
import 'package:flutter/material.dart';
>>>>>>> master

class MyHttpOverrides extends HttpOverrides {
  @override
  HttpClient createHttpClient(SecurityContext? context) {
    return super.createHttpClient(context)
      ..badCertificateCallback =
          (X509Certificate cert, String host, int port) => true;
  }
}

/// https://pub.dev/packages/dartdoc -- for dartdoc guidance!
/// All my dart doc files went into doc/api but for some reason won't upload to bitbucket.

String? IdToken;
String sessionKey = '';

void main() {
  HttpOverrides.global = MyHttpOverrides();
  runApp(const MyApp());
}

/**
 * A Stateless Widget is a widget that never changes.
 * Examples such as Icon, IconButton, and Text
 */ ///
class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    // Overall App with title and primary color
    return MaterialApp(
      title: 'The Buzz',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        // Can change the color swatches of your app
        primarySwatch: Colors.yellow,
        visualDensity: VisualDensity.adaptivePlatformDensity,
        splashColor: Colors.black,

        /// This makes some buttons change color when you press them,
        /// such as the "back to home page" button in input view.
        /// Nothing crazy, just saw it in a tutorial and added it lol.
        highlightColor: Colors.yellow.withOpacity(.5),
      ),

      // Call MyHomePage to be the "home" page of the app, which is in main.dart
      // and passes in a title for a parameter.
      home: const Login(title: 'The Buzz'),

      // Multiple pages???
      routes: <String, WidgetBuilder>{
        '/UserProfile': (BuildContext context) =>
            const UserProfile(title: 'The Buzz'),
        '/Login': (BuildContext context) => const Login(title: 'The Buzz'),
      },
    );
  }
}

/** 
 * A StatefulWidget is a Widget in which can change once
 * the user interacts with the widget. Thus, it has a State 
 * object that contains fields that affect how it looks.
 * */ ///
class MyHomePage extends StatefulWidget {
  // passes in title for the home page
  const MyHomePage({super.key, required this.title});

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  // Don't think I have used this variable within this class.
  // But would be needed if I wanted to make a HTTP Request w/in this class.
  // final messages = Messages();

  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return DefaultTabController(
      length: 2, // tab length
      child: Scaffold(
        resizeToAvoidBottomInset: false,
        backgroundColor: Colors
            .white, // this command will change the background of the app. Black also looks cool!
        // AppBar is what you see at the top of the webpage or mobile app.
        // Color and title can be changed here.
        // If you want you can attempt to add a picture to this seciton of the page.
        // I couldn't figure it out during my time, don't think I was using assets correctly.
        // could probably find a guide online if you have time in the first or last week of phase 2.
        appBar: AppBar(
          // Here we take the value from the MyHomePage object that was created by
          // the App.build method, and use it to set our appbar title.
          // This displays "The Buzz" at the top of the home page on the title bar!
          title: Text(
            widget.title,
            style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 30),
          ),
          centerTitle: true,
          actions: [
            TextButton(
              style: TextButton.styleFrom(
                foregroundColor: Colors.black,
              ),
              child: const Text('Logout'),
              onPressed: () async {
                await GoogleSignInApi.logout();

                // ignore: use_build_context_synchronously
                Navigator.of(context).pushReplacement(MaterialPageRoute(
                  builder: (context) => const Login(title: 'The Buzz'),
                ));
              },
            ),
          ],
          bottom: const TabBar(
            tabs: [
              Tab(text: 'Home Page'),
              Tab(text: 'User Profile'),
            ],
          ),

          //Trying to add buttons but can't?
          /*
          actions: [
            TextButton(
              child: Text('Home Page'), 
              onPressed: (){

            })
          ] */

          // Tried to add a picture in appBar :( //
          /*flexibleSpace: Container (
            decoration: const BoxDecoration(
              image: DecorationImage(
                image: AssetImage('android/app/src/main/res/Bee_Trail.png'),
                fit: BoxFit.fitWidth,
            ),
          ),
          ), */
        ),
        /**
         * SingleChildScrollView allows the page to scroll if there is more
         * material than the size of the page, this helped get rid of any pixel
         * out of bound error from the bottom of the page I was getting.
         * Quite easy and straightforward! 
         * Basically, allows everything within it to be able to be scrolled on the page.
         */
        body: TabBarView(
          children: [
            SingleChildScrollView(
              child: Column(
                // Column is also a layout widget. It takes a list of children and
                // arranges them vertically. By default, it sizes itself to fit its
                // children horizontally, and tries to be as tall as its parent.
                //
                // Invoke "debug painting" (press "p" in the console, choose the
                // "Toggle Debug Paint" action from the Flutter Inspector in Android
                // Studio, or the "Toggle Debug Paint" command in Visual Studio Code)
                // to see the wireframe for each widget.
                //
                // Column has various properties to control how it sizes itself and
                // how it positions its children. Here we use mainAxisAlignment to
                // center the children vertically; the main axis here is the vertical
                // axis because Columns are vertical (the cross axis would be
                // horizontal).
                mainAxisAlignment: MainAxisAlignment
                    .center, // Just added this. Not sure if correct.
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  const SizedBox(
                      height:
                          10), // this is the space in between the "add idea" button and whatever is above it.

                  // This ClipRRect holds the button to AddIdea which you can change the formatting or look,
                  // but when pushed the button sends the user to the InputView file/page where they can
                  // insert the new title and message for thier idea.
                  /// Not exactly sure what all the pieces do, but most are straightforward,
                  /// and you can mess around with it to see how it changes the look of the button!
                  ClipRRect(
                    borderRadius: BorderRadius.circular(4),
                    child: Stack(
                      children: <Widget>[
                        Positioned.fill(
                          child: Container(
                            decoration: const BoxDecoration(
                              shape: BoxShape.rectangle,
                              color: Colors.grey,
                            ),
                          ),
                        ),
                        TextButton(
                          style: TextButton.styleFrom(
                            foregroundColor: Colors.black,
                            padding: const EdgeInsets.fromLTRB(
                                80.0, 30.0, 80.0, 30.0),
                            textStyle: const TextStyle(
                                fontSize: 20, fontWeight: FontWeight.bold),
                          ),

                          /// When pressed the button sends you the InputView page which is what
                          /// the Navigator.push does.
                          /// Still needs the home page to be refreshd with the new post though
                          /// after posting from InputView page.
                          onPressed: () async {
                            await Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) => const InputView()),
                            );
                          },
                          child: const Text('Add Idea'),
                        ),
                      ],
                    ),
                  ),
                  //const SizedBox(height: 50),

                  // Here is code I used to practice to get Buttons for the original
                  // HTTP Request calls.
                  // Overall practice code if you want basic buttons or things from online tutorials!
                  // Here is a Center call which will Center the widgets below it.
                  /* Center(
                    child: Wrap(
                      spacing: 50,
                      alignment: WrapAlignment.center,
                      children: const [
                            /* ElevatedButton(
                              onPressed: messages.makeGetRequestForAll,
                              child: const Text("GET"),
                            ),
                            ElevatedButton(
                              onPressed: messages.makePostRequest,
                              child: const Text('POST'),
                            ),
                            ElevatedButton(
                              onPressed: messages.makePutRequest(),
                              child: const Text('PUT'),
                            ),
                            ElevatedButton(
                              onPressed: messages.makeDeleteRequest(1),
                              child: const Text('DELETE'),
                            ),*/
                      /* const Text(
                        'Likes:',
                      ),
                      Text(
                        '$_counter',
                        style: Theme.of(context).textTheme.headlineMedium,
                      ), */
                  
                      ],
                    ),
                  ), */

                  /// This last piece goes to a different view, not different page,
                  /// and it GETs and displays all the posts in a formmatted way!
                  /// But I think this chunk of code can stay as is or very similar for
                  /// now unless big changes or add ons need to be made!
                  /// Could mess around with it to see how it changes the looks of the app though!
                  /// Not 100% certain what Expanded then Row then Widget does exactly!
                  /// Needs to be updated when user POSTs an idea though.
<<<<<<< HEAD
                  const SizedBox(height:10), // puts a little space between the "add idea" button and all the posts.
                  const PostView(),
=======
                  const SizedBox(
                      height:
                          10), // puts a little space between the "add idea" button and all the posts.
                  Center(
                    //child: const SizedBox(height: 50),
                    child: Expanded(
                        child: Row(children: const <Widget>[
                      PostView(),
                    ])),
                  ),
>>>>>>> master
                ],
              ),
            ),
            Column(
              children: <Widget>[
                const SizedBox(height: 25),
                const Text(
                  'User Profile',
                  style: TextStyle(fontWeight: FontWeight.bold, fontSize: 30),
                ),
                const UserProfileView(),
                ClipRRect(
                  borderRadius: BorderRadius.circular(3),
                  child: Stack(
                    children: <Widget>[
                      Positioned.fill(
                        child: Container(
                          decoration: const BoxDecoration(
                            shape: BoxShape.rectangle,
                            color: Colors.grey,
                          ),
                        ),
                      ),
                      TextButton(
                        style: TextButton.styleFrom(
                          foregroundColor: Colors.black,
                          padding:
                              const EdgeInsets.fromLTRB(60.0, 28.0, 60.0, 28.0),
                          textStyle: const TextStyle(
                              fontSize: 20, fontWeight: FontWeight.bold),
                        ),

                        /// When pressed the button sends you the EditProfile page which is what
                        /// the Navigator.push does.
                        /// Still needs the home page to be refreshd with the new post though
                        /// after posting from InputView page.
                        onPressed: () async {
                          await Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) => const EditProfileView()),
                          );
                        },
                        child: const Text('Edit Profile'),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}

/** 
 * A StatefulWidget is a Widget in which can change once
 * the user interacts with the widget. Thus, it has a State 
 * object that contains fields that affect how it looks.
 * */ ///
class UserProfile extends StatefulWidget {
  // passes in title for the home page
  const UserProfile({super.key, required this.title});

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  State<UserProfile> createState() => _UserProfileState();
}

class _UserProfileState extends State<UserProfile> {
  // Don't think I have used this variable within this class.
  // But would be needed if I wanted to make a HTTP Request w/in this class.
  // final messages = Messages();

  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      resizeToAvoidBottomInset: false,
      backgroundColor: Colors
          .white, // this command will change the background of the app. Black also looks cool!
      // AppBar is what you see at the top of the webpage or mobile app.
      // Color and title can be changed here.
      // If you want you can attempt to add a picture to this seciton of the page.
      // I couldn't figure it out during my time, don't think I was using assets correctly.
      // could probably find a guide online if you have time in the first or last week of phase 2.
      appBar: AppBar(
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        // This displays "The Buzz" at the top of the home page on the title bar!
        title: Text(
          widget.title,
          style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 30),
        ),
        centerTitle: true,
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: const <Widget>[
            Text(
              'User Profile',
              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 30),
            ),
          ],
        ),
      ),
    );
  }
}

class Login extends StatefulWidget {
  const Login({super.key, required this.title});

  final String title;

  @override
  State<Login> createState() => _Login();
}

class _Login extends State<Login> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          widget.title,
          style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 30),
        ),
        centerTitle: true,
      ),
      body: Center(
        child: Column(
          children: <Widget>[
            const SizedBox(height: 100),
            const Text(
              'Welcome to The Buzz!',
              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 38),
            ),
            const SizedBox(height: 300),
            ElevatedButton(
              style: ElevatedButton.styleFrom(
                  textStyle: const TextStyle(fontSize: 20)),
              onPressed: signIn,
              child: const Text('Sign In With Google'),
            ),
          ],
        ),
      ),
    );
  }
  

<<<<<<< HEAD
/*
  Future signIn() async{
=======
  Future signIn() async {
    print('works@top');
>>>>>>> master
    final user = await GoogleSignInApi.login();
    final messages = Messages();
    if (user == null) {
      ScaffoldMessenger.of(context as BuildContext)
          .showSnackBar(const SnackBar(content: Text('Sign In Failed')));
    } else {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      prefs.setBool('isReturningUser', true);
      Navigator.of(context as BuildContext).pushReplacement(MaterialPageRoute(
        builder: (context) => const MyHomePage(
            title: 'The Buzz'), // this is where I get the google user
      ));
      print('works');

      final GoogleSignInAuthentication auth = await user.authentication;
      print('works2');
      IdToken = auth.idToken;
      print('works3 \n\n$IdToken\n\n');
      sessionKey = await messages.loginRequest(IdToken);
      print('session key: ' + sessionKey);

      if (IdToken != null) {
        print('ID TOKEN');
        int initLength = (IdToken!.length >= 500 ? 500 : IdToken!.length);
        print('ID TOKEN' + IdToken!.substring(0, initLength));
        int endLength = IdToken!.length;
        IdToken = IdToken!.substring(initLength, endLength);
        //   }
      } else {
        print('Sign-in failed');
      }
    }
  }
<<<<<<< HEAD
  */


  Future signIn() async {
    print('works@top');
    final user = await GoogleSignInApi.login();
    final messages = Messages();
    if (user == null) {
      ScaffoldMessenger.of(context as BuildContext)
          .showSnackBar(const SnackBar(content: Text('Sign In Failed')));
    } else {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      prefs.setBool('isReturningUser', true);
      Navigator.of(context as BuildContext).pushReplacement(MaterialPageRoute(
        builder: (context) => const MyHomePage(
            title: 'The Buzz'), // this is where I get the google user
      ));
      print('works');

      final GoogleSignInAuthentication auth = await user.authentication;
      print('works2');
      IdToken = auth.idToken;
      //print('works3 \n\n$IdToken\n\n');

           sessionKey = await messages.loginRequest(IdToken);
      print('session key: ' + sessionKey);
      await prefs.setString('sessionID', sessionKey);
       print('works3 \n\n$IdToken\n\n');
       if (IdToken != null) {
        print('ID TOKEN');
        // int initLength = (IdToken!.length >= 500 ? 500 : IdToken!.length);
        // print('ID TOKEN' + IdToken!.substring(0, initLength));
        // int endLength = IdToken!.length;
        // IdToken = IdToken!.substring(initLength, endLength);
         while (IdToken!.length > 0) {
            int initLength = (IdToken!.length >= 500 ? 500 : IdToken!.length);
            print(IdToken!.substring(0, initLength));
            int endLength = IdToken!.length;
            IdToken = IdToken!.substring(initLength, endLength);
        }
        //   }
      } else {
        print('Sign-in failed');
      }

    

      // if (IdToken != null) {
      //   print('ID TOKEN');
      //    while (IdToken!.length > 0) {
      //       int initLength = (IdToken!.length >= 500 ? 500 : IdToken!.length);
      //       print(IdToken!.substring(0, initLength));
      //       int endLength = IdToken!.length;
      //       IdToken = IdToken!.substring(initLength, endLength);
      //   }
      //   //   }
      // } else {
      //   print('Sign-in failed');
      // }
    }
  }
  



}
=======
}
>>>>>>> master
