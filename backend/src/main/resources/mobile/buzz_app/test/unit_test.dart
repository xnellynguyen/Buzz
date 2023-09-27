import 'package:buzz_app/main.dart';
import 'package:buzz_app/views/input_view/input_view.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:buzz_app/widgets/single_idea/single_idea.dart';
import 'package:buzz_app/widgets/posts_view/posts_view.dart';

/**
 * Most are not working and just ideas I tried to create from
 * tutorials and hints online. Not totally correct, don't fully
 * understand how to create the unit tests and all the different
 * tester. functions, don't fully understand them and don't know
 * how to use that from the code I have written. Never fully works
 * the way I think it should.
 *////
void main(){

  testWidgets('Add Idea Button Changes to Input View', (tester) async {
    // Build the widget.
    await tester.pumpWidget(const MyHomePage(title: 'Buzz App',));

    // Tap the post button.
    await tester.press(find.byType(TextButton));

    // Rebuild the widget with the new item.
    await tester.pump();

    // Expect to find the item on screen.
    expect(find.byWidget(const InputView()), findsOneWidget);

    /* // Enter 'Title Test' into the TextField.
    await tester.enterText(find.byType(TextField), 'Title Test');

    // Enter 'Message Test' into the TextField.
    await tester.enterText(find.byType(TextField), 'Message Test');

    // Tap the post button.
    await tester.tap(find.byType(MaterialButton));

    // Rebuild the widget with the new item.
    await tester.pump();

    // Expect to find the item on screen.
    expect(find.text('Title Test'), findsOneWidget);

    // Expect to find the item on screen.
    expect(find.text('Message Test'), findsOneWidget); */

  });


  // Define a test. The TestWidgets function also provides a WidgetTester
  // to work with. The WidgetTester allows building and interacting
  // with widgets in the test environment.
  /* testWidgets('MyWidget has a title and message', (tester) async {
    // Create the widget by telling the tester to build it.
    await tester.pumpWidget(const MyWidget(title: 'T', message: 'M'));

    // Create the Finders.
    final titleFinder = find.text('T');
    final messageFinder = find.text('M');

    // Use the `findsOneWidget` matcher provided by flutter_test to
    // verify that the Text widgets appear exactly once in the widget tree.
    expect(titleFinder, findsOneWidget);
    expect(messageFinder, findsOneWidget);
  }); */
  /* test('Testing AddLike Function in PostView class starting with 0 likes...', () {
    // setup
    //PostView count = const PostView();
    // do
    //count.addLike(0);
    // test
    //expect (count.likeCount, 1);
  });
  test('Testing AddLike Function in PostView class Already Having 1 like...', () {
    // setup
    PostView count = PostView(likeCount: 1);
    // do
    count.addLike();
    // test
    expect (count.likeCount, 0);
  });

  test('Testing RemoveLike Function in PostView class and having 0 likes...', () {
    // setup
    PostView count = PostView(likeCount: 0);
    // do
    count.removeLike();
    // test
    expect (count.likeCount, 0);
  });

  test('Testing RemoveLike Function in PostView class and having 1 like...', () {
    // setup
    PostView count = PostView(likeCount: 1);
    // do
    count.removeLike();
    // test
    expect (count.likeCount, 0);
  });
 */

  /*testWidgets('Add and remove a todo', (tester) async {
  // Enter text code...

  // Tap the add button.
  await tester.(find.byType(TextButton));

  // Rebuild the widget after the state has changed.
  await tester.pump();

  // Expect to find the item on screen.
  expect(find.text('Add Idea'), findsOneWidget);
});*/

  /*testWidgets("Single_Idea Widget Test", (tester) async {
    await tester.pumpWidget(const InputView());
    var textField = find.byType(TextField);
    expect(textField, findsOneWidget);
    await tester.enterText(textField, 'Flutter Devs');
    expect(find.text('Flutter Devs'), findsOneWidget);
    if (kDebugMode) {
      print('FlutterDevs');
    }
    
  });*/

  
}
