import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

import '../../messages.dart';
import '../../widgets/single_idea/message_and_comments.dart';


class SingleIdea extends StatefulWidget{
  final int mID;
  const SingleIdea (this.mID,{Key? key}) : super(key: key);

  @override
  State<SingleIdea> createState() => _SingleIdeaState();

}

class _SingleIdeaState extends State<SingleIdea>{

  late Future<TitleMessagePair> _future_title_message_pair;

  @override
  initState(){
    super.initState();
    _future_title_message_pair = messages.makeGetRequestForOne(widget.mID);
  }

  void retry(){
    setState((){
      _future_title_message_pair = messages.makeGetRequestForOne(widget.mID);
    });
  }

Future<void> addLike(int id) async {
  await messages.makePutRequestForSingleLikeCount(id);
    setState(() {});
  }
  void removeLike(int id) { // async??
    messages.makePutRequestForSingleDislike(id);
    setState(() {});
  } 

  final messages = Messages();

  @override
  Widget build(BuildContext context){

    var fb = FutureBuilder<TitleMessagePair>(
      future: _future_title_message_pair,
      builder: (BuildContext context, AsyncSnapshot<TitleMessagePair> snapshot){
        Widget child;

        if(snapshot.hasData){
          child = Expanded(
            child: ListView.builder(
              scrollDirection: Axis.vertical,
              padding: const EdgeInsets.all(16.0),
              itemCount: 1,
              itemBuilder: (context, i){
                return Column(
                  children: <Widget>[
                    ListTile(
                      tileColor:Colors.yellow,
                          leading: CircleAvatar(
                            backgroundColor: Colors.white,
                            child: Text(
                              "${snapshot.data!.id}",
                              style: const TextStyle(color: Colors.black, fontSize: 15, fontWeight: FontWeight.bold),
                              ),
                          ),
                          title: Text(
                            "Title: ${snapshot.data!.title} ",
                            style: const TextStyle(color: Colors.black, fontSize: 18, fontWeight: FontWeight.bold),
                          ),
                          subtitle: Text(
                            "Idea: ${snapshot.data!.message}",
                            style: const TextStyle(color: Colors.black, fontSize: 16, fontWeight: FontWeight.normal),
                          ),
                          
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
                                  MaterialPageRoute(builder:(context)=> MessageAndComments(snapshot.data!.id)),
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
                                    addLike(snapshot.data!.id);
                                  });
                                }, 
                                child: Icon(Icons.thumb_up, color: snapshot.data!.likes > 0 ? Colors.green : Colors.grey),
                              ),
                              MaterialButton(
                                minWidth: 10,
                                color: Colors.black,
                                //child: Icon(Icons.favorite, color: snapshot.data![i].likes > 0 ? Colors.red : Colors.grey),
                                onPressed: () {
                                  setState(() {
                                    //messages.makePutRequestForSingleDislike(snapshot.data![i].id);
                                    removeLike(snapshot.data!.id);
                                  });
                                }, 
                                child: Icon(Icons.thumb_down, color: snapshot.data!.dislikes > 0 ? Colors.red : Colors.grey),
                              ),
                              Text (
                                '${snapshot.data!.totalLikes}',
                                style: const TextStyle(color: Colors.black, fontSize: 20, height: 1.25),
                              ),
                            ],
                          ),
                    ),
                  ],
                );
              }
            ),
          );
        }else if(snapshot.hasError){
          child = Text('${snapshot.error}');
        }else{
          child = const CircularProgressIndicator();
        }
        return child;
      },
    );
    return fb;
  }

}

