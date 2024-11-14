import 'package:flutter/material.dart';
import 'package:holi/src/views/welcome_view.dart';
import 'package:google_fonts/google_fonts.dart';

void main(List<String> args) {
  runApp(const App());
}

class App extends StatelessWidget {
  const App({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(textTheme:GoogleFonts.ptSansTextTheme()),
      debugShowCheckedModeBanner: false,
      home: const WelcomeView(),
    );
  }
}
