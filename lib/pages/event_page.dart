import 'package:flutter/material.dart';


class event_page extends StatefulWidget {
  const event_page({super.key});

  @override
  State<event_page> createState() => _event_pageState();
}

class _event_pageState extends State<event_page> {
  final events = [
    {
      "speaker": "Babi Mumba",
      "date":"13h a 13h30",
      "subject":"le code legacy",
      "avatar":"female_ava"
    },
    {
      "speaker": "Marien Mupenda",
      "date":"14h a 14h30",
      "subject":"laravel",
      "avatar":"female_ava"
    },
    {
      "speaker": "jean luck kawel",
      "date":"15h a 15h30",
      "subject":"le base de jetpack",
      "avatar":"mal_avatar"
    },
  ];
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Evenement"),
      ),
      body: Center(
        child: ListView.builder(
            itemCount: events.length,
          itemBuilder: (context,index){

              final event = events[index];
              final avatar = event['avatar'];
              final nom = event['speaker'];
              final sujet = event['subject'];


              return  Card(
                child: ListTile(
                  leading: Image.asset("assets/images/$avatar.png"),
                  title: Text("$nom"),
                  subtitle: Text("$sujet"),
                  trailing: Icon(Icons.more_vert),
                ),
              );
          },


        ),
      ),
    );
  }
}