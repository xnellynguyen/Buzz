

//import 'package:buzz_app/widgets/posts_view/posts_view.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

import '../../messages.dart';
import '../../widgets/posts_view/posts_view.dart';
import 'package:image_picker/image_picker.dart';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:url_launcher/url_launcher.dart';

// StatefulWidget allows the widget to change once
// the user interacts with the program.
class InputView extends StatefulWidget {
  const InputView({super.key});

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

  final _textController3 = TextEditingController();
  // stores user text input of title into a variable
  String userPostTitle = '';
  // stores user text input of Message into a variable
  String userPostMessage = '';

  //stores base64 encoding of the image
  String userImageString = '';
  //use this for user to open image
  File? _image;

  // Method to ppen the device's image picker and select an image
  Future<void> _pickImage(ImageSource source) async {
    final pickedFile = await ImagePicker().getImage(source: source);

    setState(() {
      _image = File(pickedFile!.path);
    });
  }

  // Upload the selected image to a server
  Future<void> _uploadImage() async {
    final url = Uri.parse('https://example.com/upload');
    final request = http.MultipartRequest('POST', url);
    request.files.add(await http.MultipartFile.fromPath('file', _image!.path));
    final response = await request.send();

    if (response.statusCode == 200) {
      // Image was uploaded successfully
      // Do something with the server's response
    } else {
      // Image upload failed
      // Handle the error
    }
  }

  //method to print the whole base64 encoding
  void printWrapped(String text) {
    final pattern =
        RegExp('.{1,800}'); // break string into chunks of up to 800 characters
    pattern.allMatches(text).forEach((match) => print(match.group(0)));
  }

//checks if a url is valid so that the user can enter one and still access it
  bool isValidUrl(String url) {
    return Uri.parse(url).isAbsolute &&
        (url.startsWith('http://') || url.startsWith('https://'));
  }

  @override
  Widget build(BuildContext context) {
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

              // text input for message
              TextField(
                controller: _textController,
                decoration: InputDecoration(
                    hintText: 'Idea Title',
                    border: const OutlineInputBorder(),
                    // this is an X button at the end of the text input field
                    // that will clear all input that is currently in the field.
                    suffixIcon: IconButton(
                      onPressed: () {
                        _textController.clear();
                      },
                      icon: const Icon(Icons.clear),
                    )),
              ),
              const SizedBox(
                  height:
                      10), // all const SizedBox are just empty boxes to add spaces between widgets and fields.
              // text input for message
              TextField(
                controller: _textController2,
                decoration: InputDecoration(
                    hintText: 'Idea Message (1024 character limit)',
                    border: const OutlineInputBorder(),
                    // this is an X button at the end of the text input field
                    // that will clear all input that is currently in the field.
                    suffixIcon: IconButton(
                      onPressed: () {
                        _textController2.clear();
                      },
                      icon: const Icon(Icons.clear),
                    )),
              ),
              const SizedBox(height: 10),

//below is the text box to add a url
              TextField(
                controller: _textController3,
                decoration: InputDecoration(
                    hintText: 'URL Link',
                    border: const OutlineInputBorder(),

                    // this is an X button at the end of the text input field
                    // that will clear all input that is currently in the field.
                    suffixIcon: IconButton(
                      onPressed: () {
                        _textController3.clear();
                      },
                      icon: const Icon(Icons.clear),
                    )),
              ),
              const SizedBox(height: 10),
              /**
             * Two buttons below at the end of the InputView Page to have two functions:
             *  First: will post the Title and Message to the backend. Once pushed it will
             *      post and send you back to the home page.
             *  Second: Is just a cancel button, it will send you directly back to homepage
             *      without posting any input from user.
             */

              // MaterialButton(
              //     onPressed: () {

              //       Navigator.pop(context);
              //     },
              //   color: Colors.blue, // color of POST button, can change to whatever just type "Colors." and all your options will come up!
              //   child: const Text('Image', style: TextStyle(color: Colors.white))
              // ),
              // const SizedBox(height: 10),
              // // post button

              MaterialButton(
                onPressed: () {
                  _pickImage(ImageSource.gallery);
                },
                color: Colors.blue,
                child: const Text('Select an image',
                    style: TextStyle(color: Colors.white)),
              ),
              const SizedBox(height: 10),

              Container(
                height:
                    200, // set the height of the container as per your requirement
                child: _image == null
                    ? Center(child: Text('No image selected.'))
                    : Image.memory(_image!.readAsBytesSync()),
              ),

              // MaterialButton(
              //   onPressed: _image == null
              //       ? null
              //       : () {
              //           final bytes = _image!.readAsBytesSync();
              //           final base64Image = base64.encode(bytes);
              //           userImageString = base64Image;
              //           print(base64Image);
              //           //printWrapped(base64Image);
              //           _uploadImage();
              //           Navigator.pop(
              //               context); // Close the dialog or page after image upload
              //         },
              //   color: Colors.blue,
              //   child: const Text('Upload image',
              //       style: TextStyle(color: Colors.white)),
              // ),
              // const SizedBox(height: 10),

              // post button
              MaterialButton(
                  onPressed: () async {
                    String base64Image;
                    // messages.makePostRequest with _textController.text and _textController2.text
                    if (_image != null) {
                      final bytes = _image!.readAsBytesSync();
                      base64Image = base64.encode(bytes);
                      print(base64Image);
                      //     final String base64ImageString = utf8.decode(base64Image);
                    } else {
                      print("image null");
                      base64Image = 'n';
                    }

                    userPostTitle = _textController.text;
                    userPostMessage = _textController2.text;
                    String url = _textController3.text;

                    if (url != null) {
                      if (isValidUrl(url)) {
                        print(url);
                        print('is valid');
                      } else {
                        url = 'n';
                      }
                    }

                    /// Checks to ensure user just didn't press POST button without entering a field
                    /// each if statment is a different case of input from the user 
                    if ((userPostTitle != '' || userPostMessage != '') &&
                        (base64Image == 'n')) {
                      // print(userImageString);
                      base64Image = ''; 
                      messages.makePostRequest(
                          userPostTitle, userPostMessage, base64Image, url);
                    } else if ((userPostTitle != '' || userPostMessage != '') && url == 'n' ) {
                        url = ''; 
                      messages.makePostRequest(
                          userPostTitle, userPostMessage, base64Image, url);
                    }  else if (userPostTitle != '' || userPostMessage != ''){
                      //  base64Image = ''; 
                      //  url = ''; 
                       messages.makePostRequest(
                          userPostTitle, userPostMessage, base64Image, url);
                    }
                    else {
                      if (kDebugMode) {
                        print("post and/ or title text boxes were empty.");
                      }
                    }
                    //postView.retry();
                    /// Navigator.pop(context) pops you back to the page you came from!
                    /// Don't fully know everything there is about the Navigator function
                    Navigator.pop(context);
                  },
                  color: Colors
                      .blue, // color of POST button, can change to whatever just type "Colors." and all your options will come up!
                  child: const Text('Post',
                      style: TextStyle(color: Colors.white))),
              const SizedBox(height: 10),
              // post button

              MaterialButton(
                  onPressed: () {
                    /// Navigator.pop(context) pops you back to the page you came from!
                    /// Don't fully know everything there is about the Navigator function
                    Navigator.pop(context);
                  },
                  color: Colors.blueGrey,
                  child: const Text('Back to Home Page',
                      style: TextStyle(color: Colors.white)))
            ],
          )),
    );
  }
}
