<<<<<<< HEAD


=======
>>>>>>> master
// phase 1 ended 3/25/2023
import 'dart:convert';
import 'dart:developer' as developer;
import 'dart:async';
import 'dart:developer';

import 'package:buzz_app/main.dart';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart';
import 'package:http/http.dart' as http;
<<<<<<< HEAD
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:typed_data';
=======
>>>>>>> master

/** 
 * This Class is where I put all the HTTP Requests
 * Not Fully Functional at the moment
 * GET ALL, GET ONE (I think), POST, PUT ONE LIKE are the only one's functional.
 */ ///

class Messages {
  final resultNotifier = ValueNotifier<RequestState>(RequestInitial());
  // urlPrefix the variable used in which to call the dokku backend.
<<<<<<< HEAD
  static const urlPrefix =
      'https://2023sp-phase1-6.dokku.cse.lehigh.edu'; // 'https://2023sp-phase1-6.dokku.cse.lehigh.edu'

  /* Created this final map of headers because basically all routes you use or create will need this map. Most need that
   * content type thing with the application/json and in order to verify your session they all need that authorization with
   * the sessionKey
   */
=======
  static const urlPrefix =  'https://2023sp-phase1-6.dokku.cse.lehigh.edu' ;
      //'http://2023sp-phase1-6.dokku.cse.lehigh.edu' ;
  
  //  static const urlPrefix =  'http://localhost:8998/#/'; // 'https://2023sp-phase1-6.dokku.cse.lehigh.edu'

>>>>>>> master
  final Map<String, String> headers = {
    'Content-Type': 'application/json',
    'Authorization': sessionKey
  };

  /** 
   * This Function is to GET all messages from dokku backend.
   * Used code from class tutorials and online tutorials and guides.
   * Has a return value of Future<List<TitleMessagePair>>, which I see it as a a List of the TitleMessagePairs.
   * Fully functional, don't think you will have to mess with it any!
  */ ///

  Future<List<TitleMessagePair>> makeGetRequestForAll() async {
    final response =
        await get(Uri.parse('$urlPrefix/messages'), headers: headers);

    if (response.statusCode == 200) {
      final List<TitleMessagePair> returnData;
      var res = jsonDecode(response.body);
      //print(res);
      res = res['mData'];
      if (res is List) {
        returnData = (res).map((x) => TitleMessagePair.fromJson(x)).toList();
      } else if (res is Map) {
        returnData = <TitleMessagePair>[
          TitleMessagePair.fromJson(res as Map<String, dynamic>)
        ];
      } else {
        developer.log(
            'ERROR: Unexpected json response type (was not a List or Map).');
        returnData = List.empty();
      }
      print(returnData);
      return returnData;
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      throw Exception('Did not receive success status code from request.');
    }
  }

  /** 
   * This Function is to GET one single message from dokku backend.
   * Used code from class tutorials and online tutorials and guides.
   * Didn't really test to doulbe check it is working, because as of now it is not used any!
   * Should be working or very close to fully functional.
   * May be the return value of <TitleMessagePair> that is messing this funciton up.
   * Left my other practice code in method to help if possible!
  */ ///
  Future<TitleMessagePair> makeGetRequestForOne(int id) async {
    final response =
        await get(Uri.parse('$urlPrefix/messages/$id'), headers: headers);

    if (response.statusCode == 200) {
      final jsonData = jsonDecode(response.body);
      final mData = jsonData['mData'];
      return TitleMessagePair.fromJson(mData);
    } else {
      throw Exception('Failed to load post');
    }
<<<<<<< HEAD
=======
    */
    /* 
    resultNotifier.value = RequestLoadInProgress();
    final url = Uri.parse('$urlPrefix/messages/:$id');
    Response response = await get(url);
    // Print statements only print in terminal, 
    // basically not import. Need to have these get messages
    // show on home page
    */
    if (response.statusCode == 200) {
      final TitleMessagePair returnData;
      var res = jsonDecode(response.body);
      res = res['mData'];
      //res = res['mLikes'];
      print(res);
      if (res is List) {
        returnData =
            (res).map((x) => TitleMessagePair.fromJson(x)) as TitleMessagePair;
      } else if (res is Map) {
        returnData = <TitleMessagePair>[
          TitleMessagePair.fromJson(res as Map<String, dynamic>)
        ] as TitleMessagePair;
      } else {
        developer.log(
            'ERROR: Unexpected json response type (was not a List or Map).');
        returnData = List.empty() as TitleMessagePair;
      }
      return returnData;
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      throw Exception('Did not receive success status code from request.');
    }
>>>>>>> master
  }

  /** 
   * This Function is to PUT a single messages like count to dokku backend.
   * Basically, update that individual message's like count depending on the current like count.
   * The backend's method may have to change its funcitonality at some point,
   * but this method should be fine!
   * Used code from class tutorials and online tutorials and guides.
   * Passes in one int parameter representing the id of the idea.
   * This function is currently fully funcitonal.
  */ ///
  Future<void> makePutRequestForSingleLikeCount(int id) async {
    final response =
        await put(Uri.parse('$urlPrefix/likes/$id'), headers: headers);

    if (response.statusCode == 200) {
      print("PUT -- Changed Like Count Success of ID: $id");
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      throw Exception('Did not receive success status code from PUT request.');
    }
  }

  Future<void> makePutRequestForSingleDislike(int id) async {
    final response =
        await put(Uri.parse('$urlPrefix/dislikes/$id'), headers: headers);

    if (response.statusCode == 200) {
      print("PUT -- Changed Like Count Success of ID: $id");
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      throw Exception('Did not receive success status code from PUT request.');
    }
  }

  /** 
   * This Function is to POST a single messages from dokku backend.
   * Used code from class tutorials and online tutorials and guides.
   * Passes in two string parameters representing the title and message of the idea.
   * This function is currently fully funcitonal.
   * Only problem with POST is the home page doesn't refresh when I post a message from INPUT VIEW,
   * I tried to get the answer from online, but if you ask the right TA, they may have the answer!
  */ ///
<<<<<<< HEAD
  Future<void> makePostRequest(
      String title, String message, String image, String link) async {
=======
  Future<void> makePostRequest(String title, String message) async {
>>>>>>> master
    resultNotifier.value = RequestLoadInProgress();
    final url = Uri.parse('$urlPrefix/messages');

    final response = await post(
      url,
      headers: headers,
      body: jsonEncode({
        //'mid': 0, // not sure how to pass in the id if I don't know which message it is.
        'mSubject': title,
        'mMessage': message,
        'mAttachment': image,
        'mLink': link
        //'mLikes': 0,
      }),
    );
    if (kDebugMode) {
      print('POST--Status code: ${response.statusCode}');
    }
    if (kDebugMode) {
      print('POST--Body: ${response.body}');
    }
  }

  // Does a PUT Request for a certain id of a message.
  // Needs worked on for next phase!!!
  // Got code from outside tutorials and guides.
  // Never tested with our dokku backend.
  // I would assume have it similar to the PUT Likes above,
  // but I believe at this time we can only UPDATE the message of a post
  // from this route, so that should make it easy!!
  // So I would only worrying about changing message and look at code from
  // backend or online to have a good PUT REQUEST method!!
  Future<void> makePutRequest(int id, String message) async {
    resultNotifier.value = RequestLoadInProgress();
    final url = Uri.parse('$urlPrefix/messages/$id');
    //String json = '{"mId":$id,"mSubject":$title,"mMessage":$message,"mLikes":$likes}';
    final response = await put(url, headers: headers, body: json);
    if (kDebugMode) {
      print('PUT--Status code: ${response.statusCode}');
    }
    if (kDebugMode) {
      print('PUT--Body: ${response.body}');
    }
  }

  // Does a Delete Request for a certain id of a message.
  // Needs worked on for next phase!!!
  // Got code from outside tutorials and guides.
  // Never tested with our dokku backend.
  // Didn't work on this much, but should be straightforward
  // once you look into briefly! I would assume you don't have
  // to change much, but not 100% sure!
  Future<void> makeDeleteRequest(int id) async {
    resultNotifier.value = RequestLoadInProgress();
    final url = Uri.parse('$urlPrefix/messages/$id');
    final response = await delete(url, headers: headers);
    if (kDebugMode) {
      print('DELETE--Status code: ${response.statusCode}');
    }
    if (kDebugMode) {
      print('DELETE--Body: ${response.body}');
    }
  }

<<<<<<< HEAD
  /* This function does a login post request where you take your Google ID token into 
   * a parameter and send it off to backend. In the body of thisrequest backend sends you
   * a sessionKey which is why your return value is a string.
   */
  //  Future<String> loginRequest(String? IdToken) async {
  //   final response = await http.post(Uri.parse('$urlPrefix/login/$IdToken'));

  //   if(response.statusCode == 200){
  //     var res = jsonDecode(response.body);
  //     res = res['mData'];
  //     return res;
  //   }else{
  //     throw Exception('Did not recieve success status code from request');
  //   }
  // }
  Future<String> loginRequest(String? IdToken) async {
    // print("in login request: $urlPrefix/login/$IdToken");
    final response = await http.post(Uri.parse('$urlPrefix/login/$IdToken'));
    print("in login request: $urlPrefix/login/$IdToken");
=======
  Future<String> loginRequest(String? IdToken) async {
    print("in login request: $urlPrefix/login/$IdToken");
    final response = await http.post(Uri.parse('$urlPrefix/login/$IdToken'));
>>>>>>> master
    print("response:" + response.body);
    if (response.statusCode == 200) {
      var res = jsonDecode(response.body);
      res = res['mData'];
<<<<<<< HEAD
      print("res is" + res);
=======
      print(res);
>>>>>>> master
      return res;
    } else {
      throw Exception('Did not recieve success status code from request');
    }
  }

  FutureOr<ProfileDetailsPair> makeGetRequestForProfileDetails() async {
    final response =
        await get(Uri.parse('$urlPrefix/profile'), headers: headers);

    if (response.statusCode == 200) {
      var res = jsonDecode(response.body);
      res = res['mData'];
      if (res is List) {
        throw Exception('Did not receive a single ProfileDetailsPair object.');
      } else if (res is Map<String, dynamic>) {
        return ProfileDetailsPair.fromJson(res);
      } else {
        throw Exception(
            'Error: Unexepected json response type(was not a List or Map).');
      }
    } else {
      throw Exception('Did not recieve success status code from request.');
    }
  }

  Future<void> makePutRequestForProfileDetails(
      String userGI, String userSO, String uBio) async {
    final response = await put(
      Uri.parse('$urlPrefix/profile'),
      headers: headers,
      body: jsonEncode({
        'uGenderID': userGI,
        'uSexOrientation': userSO,
        'uBio': uBio,
      }),
    );

    if (kDebugMode) {
      print('PUT--Status Code: ${response.statusCode}');
    }
    if (kDebugMode) {
      print('PUT--Body: ${response.body}');
    }
  }

<<<<<<< HEAD
  Future<void> makPostRequestForComments(String comment, int mID) async {
=======
  Future<void> makPostRequestForComments(
      String comment, int mID, int uID) async {
>>>>>>> master
    resultNotifier.value = RequestLoadInProgress();

    final response = await post(
      Uri.parse('$urlPrefix/messages/$mID'),
      headers: headers,
      body: jsonEncode({
        'cMessage': comment,
      }),
    );

    if (kDebugMode) {
      print('Post--Status Code: ${response.statusCode}');
    }
    if (kDebugMode) {
      print('Post--Body: ${response.body}');
    }
  }

<<<<<<< HEAD
  Future<List<CommentsPair>> makeGetRequestForComments(int mID) async {
    final response =
        await get(Uri.parse('$urlPrefix/comments/$mID'), headers: headers);
=======
  Future<List<CommentsPair>> makeGetRequestForComments() async {
    final response =
        await get(Uri.parse('$urlPrefix/messages'), headers: headers);
>>>>>>> master

    if (response.statusCode == 200) {
      final List<CommentsPair> returnData;
      var res = jsonDecode(response.body);
      //print(res);
      res = res['mData'];
      if (res is List) {
        returnData = (res).map((x) => CommentsPair.fromJson(x)).toList();
      } else if (res is Map) {
        returnData = <CommentsPair>[
          CommentsPair.fromJson(res as Map<String, dynamic>)
        ];
      } else {
        developer.log(
            'ERROR: Unexpected json response type (was not a List or Map).');
        returnData = List.empty();
      }
      return returnData;
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      throw Exception('Did not receive success status code from request.');
    }
  }
}

// Needed classes to Request
/// Got these methods from online tutorials, don't think I used them
/// in the first few methods I created, but currently in the last few
/// that I got from tutorials. May not be fully needed by end of phase 2
/// depending on how you did PUT and DELETE methods!
class RequestState {
  const RequestState();
}

class RequestInitial extends RequestState {}

class RequestLoadInProgress extends RequestState {}

class RequestLoadSuccess extends RequestState {
  const RequestLoadSuccess(this.body);
  final String body;
}

class RequestLoadFailure extends RequestState {}

// Created class TitleMessagePair similar to our class tutorial.
// Use to pair the title and message for the ideas.
/// I believe this should all be good and no significant changes!
///
/// One comment would be once we can edit the message, we may want
/// to get rid of the final infront of message and just have it be
/// "String message", similarly with title if we can UPDATE that at some point.
class TitleMessagePair {
  /// The int representation of the id number
  final int id;

  /// The string representation of the title
  /// Will want to get rid of final when you start editing your titles!! (similar to likes)
  final String? title;

  /// The string representation of the message
  /// Will want to get rid of final when you start editing your messages!! (similar to likes)
  final String? message;

<<<<<<< HEAD
  /// The string representation of the image url
  final String? image;

  final String? link;

  int totalLikes;

=======
>>>>>>> master
  /// the int representation of the amount of likes
  int likes;

  /// the int representation of the amount of dislikes
  int dislikes;

  TitleMessagePair({
    required this.id,
    required this.title,
    required this.message,
    required this.image,
    required this.link, 
    required this.totalLikes,
    required this.likes,
    required this.dislikes,
  });

  factory TitleMessagePair.fromJson(Map<String, dynamic> json) {
    return TitleMessagePair(
      id: json['mId'] ?? '',
      title: json['mSubject'] ?? '',
      message: json['mMessage'] ?? '',
      image: json['mAttachment'] ?? '',
      link: json['mLink'] ?? '',
      totalLikes: json['mTotalCount'] ?? 0,
      likes: json['mLikeCount'] ?? 0,
      dislikes: json['mDislikeCount'] ?? 0,
    );
  }
}

class ProfileDetailsPair {
  String username = '';

  String email = '';

  String? genderIdentity = '';

  String? sexualOrientation = '';

  String? bio = '';

  ProfileDetailsPair({
    required this.username,
    required this.email,
    required this.genderIdentity,
    required this.sexualOrientation,
    required this.bio,
  });

  factory ProfileDetailsPair.fromJson(Map<String, dynamic> json) {
    return ProfileDetailsPair(
      username: json['uName'],
      email: json['uEmail'],
      genderIdentity: json['uGenderID'],
      sexualOrientation: json['uSexOrientation'],
      bio: json['uBio'],
    );
  }
}

class CommentsPair {
  final int uID;

  String? comment;

  final int mID;

  //final int cID;

  CommentsPair({
    required this.uID,
    required this.comment,
    required this.mID,
    //required this.cID;
  });

  factory CommentsPair.fromJson(Map<String, dynamic> json) {
    return CommentsPair(
      uID: json['cUserID'],
      comment: json['cMessage'],
      mID: json['cPostID'],
      //cID: json[''],
    );
  }
}
