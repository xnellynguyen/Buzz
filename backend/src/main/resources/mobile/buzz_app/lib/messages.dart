// phase 1 ended 3/25/2023 
import 'dart:convert';
import 'dart:developer' as developer;
import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:http/http.dart';

/** 
 * This Class is where I put all the HTTP Requests
 * Not Fully Functional at the moment
 * GET ALL, GET ONE (I think), POST, PUT ONE LIKE are the only one's functional.
 *////

class Messages {
  final resultNotifier = ValueNotifier<RequestState>(RequestInitial());
  // urlPrefix the variable used in which to call the dokku backend.
  static const urlPrefix =  'http://2023sp-phase1-6.dokku.cse.lehigh.edu'; // 'http://2023sp-phase1-6.dokku.cse.lehigh.edu'

  /** 
   * This Function is to GET all messages from dokku backend.
   * Used code from class tutorials and online tutorials and guides.
   * Has a return value of Future<List<TitleMessagePair>>, which I see it as a a List of the TitleMessagePairs.
   * Fully functional, don't think you will have to mess with it any!
  *////
  
  Future<List<TitleMessagePair>> makeGetRequestForAll() async {
    final response = await get(Uri.parse('$urlPrefix/messages'));

    if (response.statusCode == 200){
      final List<TitleMessagePair> returnData;
      var res = jsonDecode(response.body);
      //print(res);
      res = res['mData'];
      if (res is List ){
        returnData = (res).map( (x) => TitleMessagePair.fromJson(x) ).toList();
      }
      else if ( res is Map ){
        returnData = <TitleMessagePair>[TitleMessagePair.fromJson(res as Map<String,dynamic>)];
      }
      else {
        developer.log('ERROR: Unexpected json response type (was not a List or Map).');
        returnData = List.empty();
      }
      return returnData;
    }
    else {
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
  *////
  Future<TitleMessagePair> makeGetRequestForOne(int id) async {
    final response = await get(Uri.parse('$urlPrefix/messages/$id'));

    /* if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      return TitleMessagePair.fromJson(jsonDecode(response.body));
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      throw Exception('Failed to load post');
    }
    */
    /* 
    resultNotifier.value = RequestLoadInProgress();
    final url = Uri.parse('$urlPrefix/messages/:$id');
    Response response = await get(url);
    // Print statements only print in terminal, 
    // basically not import. Need to have these get messages
    // show on home page
    */
    if (response.statusCode == 200){
      final TitleMessagePair returnData;
      var res = jsonDecode(response.body);
      res = res['mData'];
      //res = res['mLikes'];
      print(res);
      if (res is List ){
        returnData = (res).map( (x) => TitleMessagePair.fromJson(x) ) as TitleMessagePair;
      }
      else if ( res is Map ){
        returnData = <TitleMessagePair>[TitleMessagePair.fromJson(res as Map<String,dynamic>)] as TitleMessagePair;
      }
      else {
        developer.log('ERROR: Unexpected json response type (was not a List or Map).');
        returnData = List.empty() as TitleMessagePair;
      }
      return returnData;
    }
    else {
        // If the server did not return a 200 OK response,
        // then throw an exception.
        throw Exception('Did not receive success status code from request.');
      }
    
    
  }

  /** 
   * This Function is to PUT a single messages like count to dokku backend.
   * Basically, update that individual message's like count depending on the current like count.
   * The backend's method may have to change its funcitonality at some point,
   * but this method should be fine!
   * Used code from class tutorials and online tutorials and guides.
   * Passes in one int parameter representing the id of the idea.
   * This function is currently fully funcitonal.
  *////
  Future<void> makePutRequestForSingleLikeCount(int id) async {
    final response = await put(Uri.parse('$urlPrefix/likes/$id'));

    if (response.statusCode == 200){
      print("PUT -- Changed Like Count Success of ID: $id");
    }
    else {
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
  *////
  Future<void> makePostRequest(String title, String message) async {
    resultNotifier.value = RequestLoadInProgress();
    final url = Uri.parse('$urlPrefix/messages');
    
    final headers = {"Content-type": "application/json"};
    final response = await post(url, 
      headers: headers, 
      body: jsonEncode({
        //'mid': 0, // not sure how to pass in the id if I don't know which message it is.
        'mTitle': title,    
        'mMessage': message,
        'mLikes': 0,
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
    final headers = {"Content-type": "application/json"};
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
    final response = await delete(url);
    if (kDebugMode) {
      print('DELETE--Status code: ${response.statusCode}');
    }
    if (kDebugMode) {
      print('DELETE--Body: ${response.body}');
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
  final String title; 
  /// The string representation of the message
  /// Will want to get rid of final when you start editing your messages!! (similar to likes)
  final String message;
  /// the int representation of the amount of likes
  int likes;

  TitleMessagePair({
    required this.id,
    required this.title,
    required this.message,
    required this.likes,
  });

  factory TitleMessagePair.fromJson(Map<String, dynamic> json) {
    return TitleMessagePair(
      id: json['mId'],
      title: json['mSubject'],
      message: json['mMessage'],
      likes: json['mLikes']
    );
  }
}