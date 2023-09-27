//import 'package:buzz_app/widgets/posts_view/posts_view.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

import '../../messages.dart';
import '../../widgets/posts_view/posts_view.dart';

// StatefulWidget allows the widget to change once
// the user interacts with the program.
class InputView extends StatefulWidget{
  const InputView ({super.key});

  /// This format of createState() helps avoid the error of
  /// "Avoid using private types in public APIs".
  @override
  State<InputView> createState() => _InputViewState();

}

// This class is used once a user pushes "Add Idea" button on home page.
// This class is currently working properly and the POST function is also working with the dokku backend.
/// Only issue is the new Post is not being seen automatically once you push the POST button below.
class _InputViewState extends State<InputView> {
  // Calls Messages() class to be able to utilize the POST Request
  final messages = Messages();
  // Calls PostView() class to be able to utilize its methods possibly.
  final postView = const PostView();
  // use this controller to get what the user typed for title input box
  final _textController = TextEditingController();
  // use this controller to get what the user typed for message input box
  final _textController2 = TextEditingController();
  // stores user text input of title into a variable
  String userPostTitle = '';
  // stores user text input of Message into a variable
  String userPostMessage = '';

  @override
  Widget build(BuildContext context){
    return Scaffold(
      // AppBar is the Bar at the top of the page in which you can have a title and/or picture.
      // Currently is yellow and is title Adding Idea.
      // Also, has an arrow that points backwards that returns to previous page (that is built in I believe)
      appBar: AppBar(
        title: const Text('Adding Idea'),
      ),
      // Padding for the input boxes below, and is aligned in the center.
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            /** 
             * Below are two text input boxes to get the Idea Title (first) 
             * and Idea Message (second) from the user.
             * These are currently fully functional.
             */
            // text input for title
            TextField(
              controller: _textController,
              decoration: InputDecoration(
                hintText: 'Idea Title',
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
            // text input for message
            TextField(
              controller: _textController2,
              decoration: InputDecoration(
                hintText: 'Idea Message (1024 character limit)',
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
                  userPostTitle = _textController.text;
                  userPostMessage = _textController2.text;
                  //print(userPostTitle);
                  //print(userPostMessage);

                  /// Checks to ensure user just didn't press POST button without entering a field
                  if ( userPostTitle != '' || userPostMessage != ''){
                    messages.makePostRequest(userPostTitle, userPostMessage);
                  }else {
                    if (kDebugMode) {
                      print("post and/ or title text boxes were empty.");
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