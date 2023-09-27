import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

import '../../messages.dart';
import '../../widgets/posts_view/user_profile_view.dart';


// StatefulWidget allows the widget to change once
// the user interacts with the program.
class EditProfileView extends StatefulWidget{
  const EditProfileView ({super.key});

  /// This format of createState() helps avoid the error of
  /// "Avoid using private types in public APIs".
  @override
  State<EditProfileView> createState() => _EditProfileViewState();

}

// This class is used once a user pushes "Edit Profile" button on Profile page.
// This class is currently working properly and the POST function is also working with the dokku backend.
/// Only issue is the new Post is not being seen automatically once you push the POST button below.
class _EditProfileViewState extends State<EditProfileView> {
  // Calls Messages() class to be able to utilize the POST Request
  final messages = Messages();
  // Calls PostView() class to be able to utilize its methods possibly.
  final userProfileView = const UserProfileView();
  // use this controller to get what the user typed your Gender Identity input box
  final _textController = TextEditingController();
  // use this controller to get what the user typed for Sexual Orientation input box
  final _textController2 = TextEditingController();
  //use this controller to get what the user types for their bio input box
  final _textController3 = TextEditingController();
  // stores user text input of gender identity into a variable
  String userGI = '';
  // stores user text input of sexual orientation into a variable
  String userSO = '';
  //stores user text input for bio into a variable
  String bio = '';

  @override
  Widget build(BuildContext context){
    return Scaffold(
      // AppBar is the Bar at the top of the page in which you can have a title and/or picture.
      // Currently is yellow and is title Editing Profile.
      // Also, has an arrow that points backwards that returns to previous page
      appBar: AppBar(
        title: const Text('Editing Profile'),
      ),
      // Padding for the input boxes below, and is aligned in the center.
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text(
              'Re-enter all three fields',
            ),
            const SizedBox(height: 10),
            /** 
             * Below are three text input boxes to change your gender identity (first), 
             * sexual orientation (second), and user bio(third).
             * These are currently fully functional.
             */
            // text input for gender identity
            TextField(
              controller: _textController,
              decoration: InputDecoration(
                hintText: 'Gender Identity',
                border: const OutlineInputBorder(),
                // this is an X button at the end of the text input field
                // that will clear all input that is currently in the field.
                suffixIcon:  
                    IconButton(
                      onPressed: () {
                        _textController.clear();
                      }, 
                      icon: const Icon(Icons.clear),
                    )
              ),
            ),
            const SizedBox(height: 10), // all const SizedBox are just empty boxes to add spaces between widgets and fields.
            // text input for sexual orientation
            TextField(
              controller: _textController2,
              decoration: InputDecoration(
                hintText: 'Sexual Orientation',
                border: const OutlineInputBorder(),
                // this is an X button at the end of the text input field
                // that will clear all input that is currently in the field.
                suffixIcon:  
                    IconButton(
                      onPressed: () {
                        _textController2.clear();
                      }, 
                      icon: const Icon(Icons.clear),
                    )
              ),
            ),
            const SizedBox(height: 10),
            // text input for user bio
            TextField(
              controller: _textController3,
              decoration: InputDecoration(
                hintText: 'User Bio',
                border: const OutlineInputBorder(),
                // this is an X button at the end of the text input field
                // that will clear all input that is currently in the field.
                suffixIcon:  
                    IconButton(
                      onPressed: () {
                        _textController3.clear();
                      }, 
                      icon: const Icon(Icons.clear),
                    )
              ),
            ),
            const SizedBox(height: 10),
            /**
             * Two buttons below at the end of the InputView Page to have two functions:
             *  First: will post the Title and Message to the backend. Once pushed it will
             *      post and send you back to the home page.
             *  Second: Is just a cancel button, it will send you directly back to homepage
             *      without posting any input from user.
             */
            // post button
            MaterialButton(
                onPressed: () {
                  // messages.makePostRequest with _textController.text and _textController2.text
                  userGI = _textController.text;
                  userSO = _textController2.text;
                  bio = _textController3.text;

                  //print(userGI);
                  //print(userSO);
                  //print(bio);

                  /// Checks to ensure user just didn't press POST button without entering a field
                  if ( userGI != '' || userSO != '' || bio != '' ){
                    messages.makePutRequestForProfileDetails(userGI, userSO, bio);
                  }else {
                    if (kDebugMode) {
                      print(" one of more text boxes were empty.");
                    }
                  }
                  //postView.retry();
                  /// Navigator.pop(context) pops you back to the page you came from!
                  /// Don't fully know everything there is about the Navigator function
                  Navigator.pop(context);
                },
              color: Colors.blue, // color of POST button, can change to whatever just type "Colors." and all your options will come up!
              child: const Text('Post', style: TextStyle(color: Colors.white))
            ),
            const SizedBox(height: 10),
            // post button
            MaterialButton(
              onPressed: () {
                /// Navigator.pop(context) pops you back to the page you came from!
                /// Don't fully know everything there is about the Navigator function
                Navigator.pop(context);
              }, 
              color: Colors.blueGrey,
              child: const Text('Back to Home Page', style: TextStyle(color: Colors.white))
            )
          ],
        )
      ),
    );
  }
}