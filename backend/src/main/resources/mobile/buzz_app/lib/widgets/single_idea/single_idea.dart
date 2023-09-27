import 'package:flutter/material.dart';

import '../../messages.dart';

// import '../../Like_Counter.dart';

/**
 * This class and code is not being used at all!
 * I didn't want to get rid of it in case you could use it 
 * when someone comments and you have to pull up a single idea?
 * I am not really sure any exact uses for this code, 
 * but it was a lot, so if anything can be used as a practice area
 * or to look at different code I played around with.
 * Wasn't the right idea for me in the beginning to try to use this code
 * to show all the posts.
 * If no use to it after your phase, I would consider deleting it to
 * eliminate some code debt!!
 * 
 * I didn't comment everything fully on this program because it is not 
 * currently useful to the app!!
 *////

// StatefulWidget allows the widget to change once
// the user interacts with the program.
class SingleIdea extends StatefulWidget {
  // Currently passes in an id to relate to the id of the message in the backend.
  final int id; 
  const SingleIdea(this.id, {super.key});

  @override
  // ignore: library_private_types_in_public_api
  SingleIdeaState createState() => SingleIdeaState();

}

class SingleIdeaState extends State<SingleIdea> {
  late Future<TitleMessagePair> _future_title_message_pair;

  // Uses these methods to set a variable to GET the Request for a single id (message)
  @override
  void initState() {
    super.initState();
    _future_title_message_pair = messages.makeGetRequestForOne(widget.id);
  }

  void _retry() {
    setState(() {
      _future_title_message_pair = messages.makeGetRequestForOne(widget.id);
    });
  }

  final messages = Messages();
  int likeCount; // = the like count from messages.makeGetRequestForOne(widget.id);
  SingleIdeaState({this.likeCount = 0});
  // methods to add and remove a like on a single message.
  // Remove a Like doesn't get called directly in the program, but only through addLike
  // once there is already a like on that message.
  // Will have to change down the road to multiple users can like and remove their own like.
  // But this simplicity should be a goood starting point.
  void addLike() {
    //setState(() {
      if ( likeCount == 1){
        removeLike();
      }
      else if ( likeCount == 0){
        likeCount++;
      }
      else {
        likeCount = 1;
      }
      
    //}
    //);
  }
  void removeLike() {
    //setState(() {
      if ( likeCount == 1){
        likeCount--;
      }
      else if ( likeCount == 0){
        // do nothing -- _likeCount;
      }
      else {
        likeCount = 0;
      }
    //});
  }

  @override
  Widget build(BuildContext context){
    //FutureBuilder<List<TitleMessagePair>> fb;
    return Stack(
      alignment: Alignment.topLeft,
      children: <Widget>[
        Align(
          // This is the like button that is a heart, and is red with a like or gray without one.
          // When pressed adds a like through the function at the top, but removes if already has a like!
          alignment: const Alignment(.85,1),
          child: MaterialButton(
              minWidth: 15,
              color: Colors.black,
              child: Icon(Icons.favorite, color: likeCount > 0 ? Colors.red : Colors.grey),
              onPressed: () {
                setState(() {
                  addLike();
                });
              // Need to send the _likeCount to the database!
              }, 
          ),
        ),
        Align(
          alignment: const Alignment(.9,1),
          child: 
          // Prints out the likecount besides the like button.
              Text (
                '$likeCount',
                style: const TextStyle(color: Colors.black, fontSize: 20,height:1.25),

              ),
        ),
        Align(
          alignment: Alignment.topCenter,
          child: Container(
            height: 100,
            width: 1200,
            color: Colors.white,
            child: Center(
              child: FutureBuilder<TitleMessagePair>(
                future: _future_title_message_pair,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    return Text(
                      snapshot.data!.message, 
                      style: const TextStyle(
                      color: Colors.black,
                      fontWeight: FontWeight.bold,
                      fontSize: 17,
                    ),
                  );
                  } else if (snapshot.hasError) {
                    return Text('${snapshot.error}', style: const TextStyle(
                      color: Colors.black));
                  }

                  // By default, show a loading spinner.
                  return const CircularProgressIndicator();
                },
              ),
            ),
          ),
        ),
        // const SizedBox(width: 100 ),
        Align(
          alignment: const Alignment(-.9,1),
          child: Container(
            height: 40,
            width: 200,
            color: Colors.black,
            child: Center(
              child: FutureBuilder<TitleMessagePair>(
                future: _future_title_message_pair,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    return Text(
                      snapshot.data!.title, 
                      style: const TextStyle(
                      color: Colors.white,
                      fontWeight: FontWeight.bold,
                      fontSize: 15,
                    ),
                  );
                  } else if (snapshot.hasError) {
                    return Text('${snapshot.error}', style: const TextStyle(
                      color: Colors.white));
                  }

                  // By default, show a loading spinner.
                  return const CircularProgressIndicator();
                },
              ),
              /* child: Text(
                'Title', 
                style: TextStyle(
                  color: Colors.white,
                  fontWeight: FontWeight.bold,
                  fontSize: 15,
                ),
              ) snapshot.data!.title*/
            ),
          ),
        ),
        // const SizedBox(height: 100 ),
      ],
    );
    /*return SizedBox(
      width: 1000, // needs to be smaller for mobile? overflow of pixels
      height: 100,
        child: DecoratedBox(
          decoration: BoxDecoration(
            color: Colors.blue,
            shape: BoxShape.rectangle,
            border: Border.all(color: Colors.white, width: 2),
          ),
          child: FutureBuilder<TitleMessagePair>(
            future: _future_title_message_pair,
            builder: (context, snapshot) {
              if (snapshot.hasData) {
                return Text(snapshot.data!.title, selectionColor: Colors.white);
              } else if (snapshot.hasError) {
                return Text('${snapshot.error}', selectionColor: Colors.white);
              }

              // By default, show a loading spinner.
              return const CircularProgressIndicator();
            },
          ),
          */
          /***
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              // Starting from here and ending when it says return child,
              // I got this code idea from the tutorial but not currently working as hoped.

              fb = FutureBuilder<TitleMessagePair>(
                future: _future_title_message_pair,
                builder: (BuildContext context, AsyncSnapshot<List<TitleMessagePair>> snapshot) {
                  Widget child;

                  if (snapshot.hasData) {
                    // developer.log('`using` ${snapshot.data}', name: 'my.app.category');
                    // create  listview to show one row per array element of json response
                    child = ListView.builder(
                        //shrinkWrap: true, //expensive! consider refactoring. https://api.flutter.dev/flutter/widgets/ScrollView/shrinkWrap.html
                        padding: const EdgeInsets.all(16.0),
                        itemCount: snapshot.data!.length,
                        itemBuilder: /*1*/ (context, i) {
                          return Column(
                            children: <Widget>[
                              ListTile(
                                title: Text( //${widget.id}
                                  "Idea: ${widget.id}   Title:${snapshot.data![widget.id].title}  Message: ${snapshot.data![widget.id].message}",
                                  // snapshot.data![i].str,
                                  style: const TextStyle(color: Colors.white, fontSize: 15),
                                ),
                              ),
                              //const Divider(height: 1.0),
                            ],
                          );
                        });
                  } else if (snapshot.hasError) { // newly added
                    child = Text('${snapshot.error}');
                  } else {
                    // awaiting snapshot data, return simple text widget
                    // child = Text('Calculating answer...');
                    child = const CircularProgressIndicator(); //show a loading spinner.
                  }
                  return child;
                },
              ),
              */
              //return fb;
              // Had this idea in ListTile as well but it returns a List<TitleMessagePair>
              // instead of the expected String return.
              /*ListTile(
                title: Text(messages.makeGetRequestForOne(widget.id) as String, // This should get replaced with makeGetRequestForOne(int id) I think.
                              style:const TextStyle(color: Colors.white,fontWeight:FontWeight.w400, height:1, fontSize: 20),
                              textAlign: TextAlign.start,)
                ),*/
              // Mostly unneeded, used as a placeholder when building. Can still utilize or use as needed.
              /*const SizedBox(width: 50 ),
              DecoratedBox(
                position: DecorationPosition.background,
                decoration: BoxDecoration(
                  color: Colors.white,
                  shape: BoxShape.rectangle,
                  border: Border.all(color: Colors.yellow, width: 2),
                ),
                  child: const Text('Message', // This should get replaced with makeGetRequestForOne(int id) I think for message
                    style:TextStyle(color: Colors.black, fontSize: 15,height:1),
                    textAlign: TextAlign.center,
                  ),
              ),*/

              // This is the like button that is a heart, and is red with a like or gray without one.
              // When pressed adds a like through the function at the top, but removes if already has a like!
              //const SizedBox(width: 50 ), // needs to be smaller for mobile? overflow of pixels
              /***
              MaterialButton(
                minWidth: 15,
                color: Colors.black,
                child: Icon(Icons.favorite, color: likeCount > 0 ? Colors.red : Colors.grey),
                onPressed: () {
                  setState(() {
                    addLike();
                  });
                  // Need to send the _likeCount to the database!
                }, 
              ),
              // Prints out the likecount besides the like button.
              Text (
                '$likeCount',
                style: const TextStyle(color: Colors.white, fontSize: 15,height:1),

              ),
              */
              // Here is a possible delete button idea or practice when needed. Not fully functional.
              /*const SizedBox(width: 50 ), // needs to be smaller for mobile? overflow of pixels
              MaterialButton(
                minWidth: 15,
                color: Colors.grey,
                child: const Text('Delete Idea'),
                onPressed: () {
                  // calls delete function for this individ. message
                }, 
              ),*/
            //],
          //),
        //)
    //);
  }
  
}
