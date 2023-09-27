//import 'package:buzz_app/widgets/posts_view/posts_view.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

import '../../messages.dart';
import '../../views/input_view/new_comment_view.dart';
import '../../widgets/single_idea/comments_view.dart';
import 'package:buzz_app/widgets/single_idea/single_idea.dart';

/// https://blog.logrocket.com/how-add-list-tile-flutter/#:~:text=You%20can%20change%20the%20ListTile's,tileColor%3A%20Colors.
/// This website shows you how to add color to the ListTile
/// Also Shows how to swipe to dismiss behavior (probably would be better for mobile) but could make that delete the idea in theory.
/// Shows you how to change the height of the List Tile
/// Also a bunch of other cool functionalities you could use!!
/// 

late int mID;

// StatefulWidget allows the widget to change once
// the user interacts with the program.
class MessageAndComments extends StatefulWidget {
  final int mID;
  const MessageAndComments( this.mID, {Key? key}) : super(key: key);

  /// This format of createState() helps avoid the error of
  /// "Avoid using private types in public APIs".
  @override
  State<MessageAndComments> createState() => _MessageAndCommentsState();

}

class _MessageAndCommentsState extends State<MessageAndComments> {
  late Future<TitleMessagePair> _future_title_message_pair;



  final messages = Messages();


  @override
  Widget build(BuildContext context){
    return Scaffold(
      // AppBar is the Bar at the top of the page in which you can have a title and/or picture.
      // Currently is yellow and is title Message.
      // Also, has an arrow that points backwards that returns to previous page (that is built in I believe)
      appBar: AppBar(
        title: const Text('Message Comments'),
      ),
      // Padding for the input boxes below, and is aligned in the center.
      body: SingleChildScrollView(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center, // Just added this. Not sure if correct.
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[

            const SizedBox(height: 10), // this is the space in between the "add idea" button and whatever is above it.
                  
              // This ClipRRect holds the button to AddIdea which you can change the formatting or look,
              // but when pushed the button sends the user to the InputView file/page where they can 
              // insert the new title and message for thier idea.
              /// Not exactly sure what all the pieces do, but most are straightforward,
              /// and you can mess around with it to see how it changes the look of the button!
            Center(
              child: ClipRRect(
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
                        padding: const EdgeInsets.fromLTRB(80.0,30.0,80.0,30.0),
                        textStyle: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                      ),
                      /// When pressed the button sends you the InputView page which is what
                      /// the Navigator.push does. 
                      /// Still needs the home page to be refreshd with the new post though
                      /// after posting from InputView page.
                      onPressed: () async {
                        await Navigator.push(
                          context,
                          MaterialPageRoute(builder:(context)=> NewCommentView(widget.mID)),
                        );
                      },
                      child: const Text('Add Comment'),
                    ),
                  ],
                ),
              ),
            ),
            const SizedBox(height:10), // puts a little space between the "add idea" button and all the posts.
            SingleIdea(widget.mID),
          ],
        ),
      ),
    );
  }
}

