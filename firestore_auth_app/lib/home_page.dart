import 'package:flutter/material.dart';
import 'login_page.dart';

class HomePage extends StatelessWidget {
  final String username;
  const HomePage({super.key, required this.username});

  void logout(BuildContext context) {
    Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => const LoginPage()));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Home"),
        actions: [
          IconButton(onPressed: () => logout(context), icon: const Icon(Icons.logout))
        ],
      ),
      body: Center(
        child: Text("Welcome, $username!", style: const TextStyle(fontSize: 24)),
      ),
    );
  }
}
