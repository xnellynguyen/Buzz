import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

import '../../messages.dart';

/**
 * This view may help you start an edit view, such as when a user
 * pushes the edit button the page changes similar to Input View,
 * where they can change the message or whatever is allowed to change,
 * and then you can use PUT to udpate it on backend!
 * I had the idea of doing something similar to Input View with
 * the text fields and everything but somehow make it start with the title
 * and message already in the text box (idk how to do that though lol).
 * 
 * Also I would use a similar layout when you have to have a login!
 * And then make the login the first page the user sees before the homepage.
 * Then the login will somehow communicate with the database to see if that person's
 * username and password matches up, but we may be using Google API which may be easier 
 * than that, but not 100% sure!
 */

// StatefulWidget allows the widget to change once
// the user interacts with the program.
/* class EditView extends StatefulWidget{
  const EditView ({super.key});

  @override
  // ignore: library_private_types_in_public_api
  _EditViewState createState() => _EditViewState();

}

// This class is used once a user pushes "Add Idea" button on home page.
// This class is currently working properly and the POST function is also working with the dokku backend.
class _EditViewState extends State<EditView> {
  
  @override
  Widget build(BuildContext context){
    
  }
}  */