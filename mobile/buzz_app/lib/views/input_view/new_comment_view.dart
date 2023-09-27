// //import 'package:buzz_app/widgets/posts_view/posts_view.dart';
// import 'package:flutter/foundation.dart';
// import 'package:flutter/material.dart';

// import '../../messages.dart';
// import '../../widgets/single_idea/message_and_comments.dart';
// import 'package:buzz_app/widgets/single_idea/single_idea.dart';

// late int mID;

// // StatefulWidget allows the widget to change once
// // the user interacts with the program.
// class NewCommentView extends StatefulWidget {
//   final int mID;
//   const NewCommentView(this.mID, {Key? key}) : super(key: key);

//   /// This format of createState() helps avoid the error of
//   /// "Avoid using private types in public APIs".
//   @override
//   State<NewCommentView> createState() => _NewCommentViewState();
// }

// // This class is used once a user pushes "Add Idea" button on home page.
// // This class is currently working properly and the POST function is also working with the dokku backend.
// /// Only issue is the new Post is not being seen automatically once you push the POST button below.
// class _NewCommentViewState extends State<NewCommentView> {
//   // Calls Messages() class to be able to utilize the POST Request
//   // Calls Messages() method in messages.dart with local variable messages.
//   final messages = Messages();
//   // use this controller to get what the user typed for comment input box
//   final _textController = TextEditingController();
//   // stores user text input of comment into a variable
//   String userPostComment = '';

//   late Future<List<TitleMessagePair>> _future_title_message_pair;

//   @override
//   void initState() {
//     super.initState();
//     _future_title_message_pair = messages.makeGetRequestForAll();

//     /// method refreshes the GET All request everytime this class is called.
//   }

//   void retry() {
//     /// method refreshes the GET All request everytime this method is called
//     /// within the code, setState() refreshes the widget it is called in.
//     /// setState and knowing how to use it is very useful!
//     setState(() {
//       _future_title_message_pair = messages.makeGetRequestForAll();
//     });
//   }

//   @override
//   Widget build(BuildContext context) {
//     var fb = FutureBuilder<List<TitleMessagePair>>(
//       future: _future_title_message_pair,
//       builder: (BuildContext context,
//           AsyncSnapshot<List<TitleMessagePair>> snapshot) {
//         return Scaffold(
//           // AppBar is the Bar at the top of the page in which you can have a title and/or picture.
//           // Currently is yellow and is title Adding Idea.
//           // Also, has an arrow that points backwards that returns to previous page (that is built in I believe)
//           appBar: AppBar(
//             title: const Text('Creating Comment'),
//           ),
//           // Padding for the input boxes below, and is aligned in the center.
//           body: Padding(
//               padding: const EdgeInsets.all(20.0),
//               child: Column(
//                 mainAxisAlignment: MainAxisAlignment.center,
//                 children: [
//                   /** 
//              * Below are two text input boxes to get the Idea Title (first) 
//              * and Idea Message (second) from the user.
//              * These are currently fully functional.
//              */
//                   // text input for title
//                   TextField(
//                     controller: _textController,
//                     decoration: InputDecoration(
//                         hintText: 'Idea Title',
//                         border: const OutlineInputBorder(),
//                         // this is an X button at the end of the text input field
//                         // that will clear all input that is currently in the field.
//                         suffixIcon: IconButton(
//                           onPressed: () {
//                             _textController.clear();
//                           },
//                           icon: const Icon(Icons.clear),
//                         )),
//                   ),
//                   const SizedBox(
//                       height:
//                           10), // all const SizedBox are just empty boxes to add spaces between widgets and fields.
//                   /**
//              * Two buttons below at the end of the InputView Page to have two functions:
//              *  First: will post the comment to the backend. Once pushed it will
//              *      post and send you back to the comments page for that specific idea/message.
//              *  Second: Is just a cancel button, it will send you directly back to idea/message
//              *      without posting any input from user.
//              */
//                   // post button
//                   MaterialButton(
//                       onPressed: () {
//                         // messages.makPostRequestForComments with _textController.text
//                         userPostComment = _textController.text;
//                         print(userPostComment);

//                         /// Checks to ensure user just didn't press POST button without entering a field
//                         if (userPostComment != '') {
//                           itemBuilder:
//                           (context, i) {
//                             if (snapshot.hasData) {
//                               print('in SS data if');
//                               mID = snapshot.data![i].id;
//                               messages.makPostRequestForComments(
//                                   userPostComment, mID);
//                             } else {
//                               if (kDebugMode) {
//                                 print(
//                                     "post and/ or comment text boxes were empty.");
//                               }
//                             }
//                           };
//                         }

//                         /// Navigator.pop(context) pops you back to the page you came from!
//                         /// Don't fully know everything there is about the Navigator function
//                         Navigator.pop(context);
//                       },
//                       color: Colors
//                           .blue, // color of POST button, can change to whatever just type "Colors." and all your options will come up!
//                       child: const Text('Post',
//                           style: TextStyle(color: Colors.white))),
//                   const SizedBox(height: 10),

//                   // post button
//                   MaterialButton(
//                       onPressed: () {
//                         /// Navigator.pop(context) pops you back to the page you came from!
//                         /// Don't fully know everything there is about the Navigator function
//                         Navigator.pop(context);
//                       },
//                       color: Colors.blueGrey,
//                       child: const Text('Back to Comments',
//                           style: TextStyle(color: Colors.white)))
//                 ],
//               )),
//         );
//       },
//     );

//     return fb;
//   }
// }


//import 'package:buzz_app/widgets/posts_view/posts_view.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

import '../../messages.dart';
import '../../widgets/single_idea/message_and_comments.dart';
import 'package:buzz_app/widgets/single_idea/single_idea.dart';

late int mID;

// StatefulWidget allows the widget to change once
// the user interacts with the program.
class NewCommentView extends StatefulWidget {
  final int mID;
  const NewCommentView( this.mID, {Key? key}) : super(key: key);

  /// This format of createState() helps avoid the error of
  /// "Avoid using private types in public APIs".
  @override
  State<NewCommentView> createState() => _NewCommentViewState();

}

// This class is used once a user pushes "Add Idea" button on home page.
// This class is currently working properly and the POST function is also working with the dokku backend.
/// Only issue is the new Post is not being seen automatically once you push the POST button below.
class _NewCommentViewState extends State<NewCommentView> {
  // Calls Messages() class to be able to utilize the POST Request
  final messages = Messages();
  // use this controller to get what the user typed for comment input box
  final _textController = TextEditingController();
  // stores user text input of comment into a variable
  String userPostComment = '';

  @override
  Widget build(BuildContext context){
    return Scaffold(
      // AppBar is the Bar at the top of the page in which you can have a title and/or picture.
      // Currently is yellow and is title Adding Idea.
      // Also, has an arrow that points backwards that returns to previous page (that is built in I believe)
      appBar: AppBar(
        title: const Text('Creating Comment'),
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
            /**
             * Two buttons below at the end of the InputView Page to have two functions:
             *  First: will post the comment to the backend. Once pushed it will
             *      post and send you back to the comments page for that specific idea/message.
             *  Second: Is just a cancel button, it will send you directly back to idea/message
             *      without posting any input from user.
             */
            // post button
            MaterialButton(
                onPressed: () {
                  // messages.makPostRequestForComments with _textController.text
                  userPostComment = _textController.text;
                  //print(userPostComment);


                  /// Checks to ensure user just didn't press POST button without entering a field
                  if ( userPostComment != ''){
                    messages.makPostRequestForComments(userPostComment, mID);
                  }else {
                    if (kDebugMode) {
                      print("post and/ or comment text boxes were empty.");
                    }
                  }
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
              child: const Text('Back to Comments', style: TextStyle(color: Colors.white))
            )
          ],
        )
      ),
    );
  }
}