import 'package:flutter/material.dart';

import '../../messages.dart';
import 'message_and_comments.dart';

/// https://blog.logrocket.com/how-add-list-tile-flutter/#:~:text=You%20can%20change%20the%20ListTile's,tileColor%3A%20Colors.
/// This website shows you how to add color to the ListTile
/// Also Shows how to swipe to dismiss behavior (probably would be better for mobile) but could make that delete the idea in theory.
/// Shows you how to change the height of the List Tile
/// Also a bunch of other cool functionalities you could use!!
/// 

late int mID;

// StatefulWidget allows the widget to change once
// the user interacts with the program.
class CommentsView extends StatefulWidget {
  final int mID;
  const CommentsView( this.mID, {Key? key}) : super(key: key);

  /// This format of createState() helps avoid the error of
  /// "Avoid using private types in public APIs".
  @override
  State<CommentsView> createState() => _CommentsViewState();

}

class _CommentsViewState extends State<CommentsView> {
  /// this variable holds the list of TitleMessagePairs that
  /// correspond to all the ids, titles, messages, and likecounts.
  /// Fully functional! 
  /// Pretty sure the "_" before the name makes it private.
  /// Same goes for a "_" in a method/class name as well
  late Future<List<CommentsPair>> _future_comments_pair;

  @override
  void initState() {
    super.initState();
    _future_comments_pair = messages.makeGetRequestForComments(widget.mID);
    /// method refreshes the GET All request everytime this class is called.
  }

  void retry() {
    /// method refreshes the GET All request everytime this method is called
    /// within the code, setState() refreshes the widget it is called in. 
    /// setState and knowing how to use it is very useful!
    setState(() {
      _future_comments_pair = messages.makeGetRequestForComments(widget.mID);
    });
  }

  // Calls Messages() method in messages.dart with local variable messages.
  final messages = Messages();
  /*
  /// This method calls the HTTP Request to like or unlike a post depending
  /// on its current like count.
  /// 
  /// The commented out code is what I was using to practice the like count button
  /// before we got this route! Will keep the commented code for now if you need it for any reason!
  /// Probably can delete whenever.
  /// I believe it should be all good on our end! Functional at the moment!
  void addLike(int id) { // async
    setState(() {
      messages.makePutRequestForSingleLikeCount(id);
      //likeCount = myList[id];
      //_retry();
      
      /* if ( likeCount == 1){
        removeLike(id);
      }
      else if ( likeCount == 0){
        likeCount++;
      }
      else {
        likeCount = 1;
      }
      myList[id] = likeCount; */
    });
  }
  void removeLike(int id) { // async??
    setState(() async {
      messages.makePutRequestForSingleDislike(id);
      //likeCount = myList[id];

      /*if ( likeCount == 1){
        likeCount--;
      }
      else if ( likeCount == 0){
        // do nothing -- _likeCount;
      }
      else {
        likeCount = 0;
      }
      myList[id] = likeCount;*/
    });
  } 
  
*/

  @override
  Widget build(BuildContext context){
    // Code from the tutorial I tried to implement to 

    // I think I am going to use the code from the tutorial here to make thr rows
    // can change slightly and possibly get rid of single_idea.dart???
    
    // Used these few lines to test some route/HTTP Request methods. 
    // Can uncomment and see if they work at some point.
    // Future<TitleMessagePair> practice = messages.makeGetRequestForOne(1);
    // messages.makePutRequest(6, "title", "message", 1);
    // messages.makeGetRequestForSingleLikeCount(7);
    
    /// Calling _retry() right here I think helps the like button change each time
    /// it is pushed and changing the like count beside it as well!! Very important.
    /// But for some reason when I post an idea, it doesn't automatically show up like
    /// the like count changes, have to refresh page to see new ideas!:(
    retry();
    // Overall this code is from the tutorial but just fit with our needs!
    /// You will get a lot of output in the terminal of an error from using Expanded,
    /// and not having a certain body before it, I didn't understand it but if you ask 
    /// the right TA they may know the answer, but don't think it hurts the app overall as of now!
    /// For some reason, when you like or unlike a post, it may move spots within the List of posts,
    /// not exactly sure why, but it doesn't happen everytime, but an odd event that I didn't take much 
    /// time to figure out and possibly fix.
    /// I'd assume we would want most recent posts at top? Or ones with most likes?
    var fb = FutureBuilder<List<CommentsPair>>(
      future: _future_comments_pair,
      builder: (BuildContext context, AsyncSnapshot<List<CommentsPair>> snapshot) {
        Widget child;

        if (snapshot.hasData){
          //child: Flex(direction: Axis.vertical,
            child = Expanded(
              child: ListView.builder(
                scrollDirection: Axis.vertical,
                shrinkWrap: true,
                padding: const EdgeInsets.all(16.0),
                itemCount: snapshot.data!.length,
                itemBuilder: (context, i) {
                  return SingleChildScrollView(
                    child: Column(
                      children: <Widget>[
                        /// I have a link on top of this page if you want to learn more about ListTiles
                        /// and mess around with them, I would recommend just looking up their functionality and stuff
                        /// so you can see how they work.
                        /// As of now, I think everything is good on it, and I have some practice code of mine for a
                        /// Delete and Edit button!
                        ListTile(
                          tileColor:Colors.yellow,
                          leading: CircleAvatar(
                            backgroundColor: Colors.white,
                            child: Text(
                              "${snapshot.data![i].mID}",
                              style: const TextStyle(color: Colors.black, fontSize: 15, fontWeight: FontWeight.bold),
                              ),
                          ),
                          title: Text(
                            "Title: ${snapshot.data![i].comment} ",
                            style: const TextStyle(color: Colors.black, fontSize: 18, fontWeight: FontWeight.bold),
                          ),
                          
                          /*
                          trailing: Wrap(
                            spacing: 12, // space between two icons
                            children: <Widget>[
                              TextButton(
                                style: TextButton.styleFrom(
                                  foregroundColor: Colors.black,
                                  //padding: const EdgeInsets.fromLTRB(15.0,10.0,15.0,10.0),
                                  textStyle: const TextStyle(fontSize: 10, fontWeight: FontWeight.bold),
                                ),
                                onPressed: () async {
                                  await Navigator.push(
                                  context,
                                  MaterialPageRoute(builder:(context)=> SingleIdea(snapshot.data![i].mID)),
                                  );
                                },
                                child: const Text('Comment'),
                              ),
                              MaterialButton(
                                minWidth: 10,
                                color: Colors.black,
                                //child: Icon(Icons.favorite, color: snapshot.data![i].likes > 0 ? Colors.red : Colors.grey),
                                onPressed: () {
                                  setState(() {
                                    //messages.makePutRequestForSingleLikeCount(snapshot.data![i].id);
                                    addLike(snapshot.data![i].mID);
                                  });
                                }, 
                                child: Icon(Icons.thumb_up, color: snapshot.data![i].likes > 0 ? Colors.green : Colors.grey),
                              ),
                              MaterialButton(
                                minWidth: 10,
                                color: Colors.black,
                                //child: Icon(Icons.favorite, color: snapshot.data![i].likes > 0 ? Colors.red : Colors.grey),
                                onPressed: () {
                                  setState(() {
                                    //messages.makePutRequestForSingleDislike(snapshot.data![i].id);
                                    removeLike(snapshot.data![i].id);
                                  });
                                }, 
                                child: Icon(Icons.thumb_down, color: snapshot.data![i].likes > 0 ? Colors.red : Colors.grey),
                              ),
                              Text (
                                '${snapshot.data![i].likes}',
                                style: const TextStyle(color: Colors.black, fontSize: 20, height: 1.25),
                              ),*/
                        ),
                        // This line only makes the small divider (line) between the ListTiles,
                        // can change the height, color, thickness or whatever!
                        const Divider(height: 1.0, color: Colors.black, thickness: 2),
                      ],
                    ),
              );
                }
              ),
            );
        }
        /// Just handles cases if something is wrong with the data that is being read in!
        else if (snapshot.hasError) { // newly added
          child = Text('${snapshot.error}');
        } else {
          // awaiting snapshot data, return simple text widget
          child = const CircularProgressIndicator(); //show a loading spinner.
        }
        return child;
      },);
    return fb;
  }
}
