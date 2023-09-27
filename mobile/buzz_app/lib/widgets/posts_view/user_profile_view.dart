import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

import '../../messages.dart';

class UserProfileView extends StatefulWidget{
  const UserProfileView ({super.key});

  @override
  State<UserProfileView> createState() => _UserProfileViewState();

}

class _UserProfileViewState extends State<UserProfileView>{

  late Future<ProfileDetailsPair> _future_profile_details_pair;

  @override
  void initState(){
    super.initState();
    _future_profile_details_pair = messages.makeGetRequestForProfileDetails() as Future<ProfileDetailsPair>;
  }

  void retry(){
    setState((){
      _future_profile_details_pair = messages.makeGetRequestForProfileDetails() as Future<ProfileDetailsPair>;
    });
  }

  //Call to messages() for...
  final messages = Messages();

  @override
  Widget build(BuildContext context){
    retry();

    var fb = FutureBuilder<ProfileDetailsPair>(
      future: _future_profile_details_pair,
      builder: (BuildContext context, AsyncSnapshot<ProfileDetailsPair> snapshot){
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
                      key: UniqueKey(),
                      tileColor: Colors.yellow,
                      title: const Text(
                        'Username',
                        style: TextStyle(color: Colors.black, fontSize: 18, fontWeight: FontWeight.bold),
                      ),
                      subtitle: Text(
                        snapshot.data!.username,
                        style: const TextStyle(color: Colors.black, fontSize: 16, fontWeight: FontWeight.bold),
                      ),
                    ),
                    ListTile(
                      key: UniqueKey(),
                      tileColor: Colors.yellow,
                      title: const Text(
                        'Email',
                        style: TextStyle(color: Colors.black, fontSize: 18, fontWeight: FontWeight.bold),
                      ),
                      subtitle: Text(
                        snapshot.data!.email,
                        style: const TextStyle(color: Colors.black, fontSize: 16, fontWeight: FontWeight.bold),
                      ),
                    ),
                    ListTile(
                      key: UniqueKey(),
                      tileColor: Colors.yellow,
                      title: const Text(
                        'Gender Identity',
                        style: TextStyle(color: Colors.black, fontSize: 18, fontWeight: FontWeight.bold),
                      ),
                      subtitle: Text(
                        snapshot.data!.genderIdentity ?? '',
                        style: const TextStyle(color: Colors.black, fontSize: 16, fontWeight: FontWeight.bold),
                      ),
                    ),
                    ListTile(
                      key: UniqueKey(),
                      tileColor: Colors.yellow,
                      title: const Text(
                        'Sexual Orientation',
                        style: TextStyle(color: Colors.black, fontSize: 18, fontWeight: FontWeight.bold),
                      ),
                      subtitle: Text(
                        snapshot.data!.sexualOrientation ?? '',
                        style: const TextStyle(color: Colors.black, fontSize: 16, fontWeight: FontWeight.bold),
                      ),
                    ),
                    ListTile(
                      key: UniqueKey(),
                      tileColor: Colors.yellow,
                      title: const Text(
                        'Bio',
                        style: TextStyle(color: Colors.black, fontSize: 18, fontWeight: FontWeight.bold),
                      ),
                      subtitle: Text(
                        snapshot.data!.bio ?? '',
                        style: const TextStyle(color: Colors.black, fontSize: 16, fontWeight: FontWeight.bold),
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

