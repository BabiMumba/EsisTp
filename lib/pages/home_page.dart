import 'package:flutter/material.dart';

import 'event_page.dart';

class HomePage extends StatelessWidget {
  const HomePage({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          "Asyncof 2023",
          style: TextStyle(
              fontFamily: "Poppins"
          ),
        ),
      ),
      body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Image.asset(
                "assets/images/logo_academia.png",
                width: 150,
                height: 250,
              ),
              const Text(
                "Mapendo",
                style: TextStyle(
                  fontFamily: "Poppins",
                  fontSize: 40,
                  color: Colors.amber,
                ),
              ),
              const Text(
                "Salon virtuel de l'informatique",
                style: TextStyle(
                    color: Colors.red
                ),
              ),
              Padding(padding: EdgeInsets.only(top: 10)),
              ElevatedButton.icon(
                onPressed: ()=> {
                  Navigator.push(
                      context,
                      PageRouteBuilder(
                          pageBuilder: (_, __, ___)=>event_page()
                      )
                  )
                },
                label: Text("Evenement")
                ,icon: Icon(Icons.calendar_month),
              )

            ],
          )
      ),
    );
  }
}