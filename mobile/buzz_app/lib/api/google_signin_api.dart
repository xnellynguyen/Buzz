import 'package:google_sign_in/google_sign_in.dart';
import 'package:flutter/material.dart';
import '../messages.dart';

class GoogleSignInApi {
  static final _clientIDWeb =
      '691318830475-e26bc040cccn4puav2rbkpqqmqlt6tmt.apps.googleusercontent.com' ;

  static final _googleSignIn = GoogleSignIn(serverClientId: _clientIDWeb);

  //static final _googleSignIn = GoogleSignIn();
  static Future<GoogleSignInAccount?> login() => _googleSignIn.signIn();

  //static Future login() => _googleSignIn.signIn();
  static Future logout() => _googleSignIn.disconnect();
}

