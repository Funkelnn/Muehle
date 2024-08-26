Mühle Spiel
============

# Mühle - Spielanleitung

## Spielbrett:

Das Mühle-Spielbrett besteht aus einem Quadrat mit 24 Kreuzungspunkten.

Jeder Spieler hat neun Spielsteine einer Farbe (meistens weiß oder schwarz).

## Spielverlauf:

1. **Setzphase:** Die Spieler setzen abwechselnd ihre Spielsteine auf die leeren Kreuzungspunkte. Dies geht solange, bis alle 18 Steine platziert sind.

2. **Zugphase:** Nachdem alle Steine platziert wurden, ziehen die Spieler abwechselnd einen ihrer Steine zu einem benachbarten Kreuzungspunkt. Dabei darf jeder Stein nur auf einen leeren Punkt bewegt werden.

3. **Mühle bilden:** Wenn ein Spieler drei seiner Steine in einer Reihe entlang einer Linie (horizontal oder vertikal) platziert, bildet er eine "Mühle". Sobald eine Mühle gebildet wird, kann der Spieler einen gegnerischen Stein entfernen, der nicht Teil einer Mühle ist.
   (Ausnahme: Gibt es jedoch keine anderen Steine mehr die keine Mühle Bilden darf man einen Stein aus der Mühle des gegners entfernen)
4. **Fliegen:** Wenn ein Spieler nur noch drei Steine hat, darf er seine Steine auf jeden freien Punkt bewegen, anstatt nur zu benachbarten Punkten.



5. **Gewonnen:** Das Spiel endet, wenn der gegnerische Spieler nur noch zwei Steine besitzt oder wenn der gegner sich nicht mehr nach der setzphase bewegen kann da er eingeschlossen ist 

# Spielbedienung am PC/Laptop

**Start:** Das Spiel beginnt automatisch einige Sekunden, nachdem die `main()` Methode aufgerufen wurde.


Mit der linken Maustaste kann man mit dem Spielbrett interagieren. Beim Setzen eines Steines genügt ein Klick auf das entsprechende Feld.

Für das Entfernen wählt man das Pokémon des Gegners auf dem Feld aus, von dem man einen Stein/Pokémon entfernen möchte.

Beim Bewegen der Steine startet man, indem man auf das Feld mit dem ausgewählten Pokémon klickt, welches man bewegen möchte. Anschließend klickt man auf das benachbarte leere Feld, zu dem man das Pokémon bewegen möchte.

Beim Springen der Steine befolgt man denselben Ablauf, nur müssen die leeren Felder, zu denen man springen möchte, nicht unbedingt mit dem aktuellen Feld benachbart sein.

Nachdem einer der beiden Spieler das Spiel verloren hat, kann man mit der Taste `r` oder `R` das Spiel neu starten.



**Spielphasen und Anzeige:**
Die jeweiligen Spielphasen wie Springen, Setzen und Bewegen sowie die Ausführung dieser Schritte werden während des Spiels auf der linken Seite neben dem Spielbrett angezeigt. Zudem wird angezeigt, welcher Spieler aktuell am Zug ist.

## Bilder

Start Menü
![Spiel Bild](\images\StartScreen.png)


Spiel
![Spiel Bild](\images\PlayingScreenShot.png)
<br>

Player one has Won!
![Spiel Bild](\images\PlayerOneWonScreenShot.png)

Player Two has Won!
![Spiel Bild](\images\PlayerTwoWonScreenShot.png)

**Externe Bibliotheken:**

1. Für die grafische Darstellung wird Processing genutzt. Weitere Informationen und Ressourcen können auf der [Processing-Seite](https://processing.org/) gefunden werden.

2. java.util.Arrays;

### Spiel Starten

Um die `main()`-Methode zu starten, müssen folgende Schritte gemacht werden:

1. Öffne die Datei Main.java.
2. Starte die `main()`.

### Code mit der Jshell Testen

Um die MuehleModel Klasse mit der Jshell zu testen, befolge die folgenden Schritte:

1. Öffne das Terminal.
2. Gib "jshell --class-path .\out\production\MuehleModel" ein und drücke Enter.
3. Gib "import pokepong.MuehleModel.MuehleModel;" ein, um die Pakete zu importieren.
4. Gib "MuehleModel muehleModel = new MuehleModel();" ein, um ein Objekt zu erzeugen.

