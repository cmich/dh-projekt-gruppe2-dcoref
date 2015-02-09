# dh-projekt-gruppe2-dcoref/AddCorefStanford

Das Projekt *AddCorefStanford* stellt die Variante der Lösung der Aufgabe von Gruppe 2 dar, die das Modul [DCoref](http://nlp.stanford.edu/software/dcoref.shtml) der [Stanford CoreNLP](http://nlp.stanford.edu/software/corenlp.shtml) verwendet. Die grundlegende Aufgabe ist das *Hinzufügen von Koreferenzinformationen* zu den von Gruppe 1 vorbereiteten Daten. Dazu wird zunächst DCoref verwendet. Die erhaltene Ausgabe pro Kapitel wird in das mit Gruppe 3 vereinbarte Format umgeformt und zusätzlich wird ein Index erstellt, der die Koreferenzketten pro Kapitel verbindet, die zusammengehören. Die dabei verwendete Ordnerstruktur ist in diesem [Repository](https://github.com/Rostu/dh-Projekt-Gruppe1) von Gruppe 1 einsehbar.

Nach Absprache fügt Gruppe 1 die Koreferenzinformationen bereits hinzu, da sie die [Stanford CoreNLP](http://nlp.stanford.edu/software/corenlp.shtml) ebenfalls verwendet und bereits für jedes Buch laufen lässt. Trotzdem besitzt die ausführbare JAR-Datei "coref-adder.jar" im Ordner "[executable](https://github.com/cmich/dh-projekt-gruppe2-dcoref/tree/master/AddCorefStanford/executable)" eine Option (-corenlp, siehe [Anleitung](#anleitung)), die das Modul [DCoref](http://nlp.stanford.edu/software/dcoref.shtml) hinzuschaltet. Verwendet man diese Option, kann man ebenfalls einen Parameter (-pp, siehe [Anleitung](#anleitung)) an dieselbe ausführbare Datei übergeben, der Postprocessing (s. [Beschreibung](http://nlp.stanford.edu/pubs/conllst2011-coref.pdf) der Autoren) aktiviert, da dieser Schritt im Modul [DCoref](http://nlp.stanford.edu/software/dcoref.shtml) standardmäßig deaktiviert ist.

## Voraussetzungen
- Java Runtime Environment, Version 1.8 ([Download](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html]adasd))

- Stanford CoreNLP, insbesondere die JAR-Datei "stanford-corenlp-3.5.0-models.jar" ([Download](http://nlp.stanford.edu/software/stanford-corenlp-full-2015-01-29.zip))

## Anleitung

### Voraussetzungen für "coref-adder.jar"

Wie bereits erwähnt, befindet sich eine ausführbare JAR-Datei "coref-adder.jar" im Ordner "[executable](https://github.com/cmich/dh-projekt-gruppe2-dcoref/tree/master/AddCorefStanford/executable)". Diese Datei muss sich neben dem Ordner "[coref-adder_lib](https://github.com/cmich/dh-projekt-gruppe2-dcoref/tree/master/AddCorefStanford/executable/coref-adder_lib)" befinden. In diesem Ordner fehlt eine JAR-Datei, die man mit der [Stanford CoreNLP](http://nlp.stanford.edu/software/corenlp.shtml) herunterladen muss. Um die Voraussetzungen für die Ausführung von "coref-adder.jar" zu erfüllen, muss man nach dem Herunterladen und Entpacken der [Stanford CoreNLP (Download)](http://nlp.stanford.edu/software/stanford-corenlp-full-2015-01-29.zip) die JAR-Datei "stanford-corenlp-3.5.0-models.jar" in den Ordner "[coref-adder_lib](https://github.com/cmich/dh-projekt-gruppe2-dcoref/tree/master/AddCorefStanford/executable/coref-adder_lib)" kopieren.

Außerdem sollte sich neben dem Ordner "[coref-adder_lib](https://github.com/cmich/dh-projekt-gruppe2-dcoref/tree/master/AddCorefStanford/executable/coref-adder_lib)" und der JAR-Datei "coref-adder.jar" ein Ordner mit den Dateien zu einem bestimmten Buch befinden z.B. "UncleTomsCabin" (s. [Ordnerstruktur](https://github.com/Rostu/dh-Projekt-Gruppe1/tree/master/UncleTomsCabin)). Dieser Beispielordner wird im für alle folgenden Beispiele während der Anleitung zur Ausführung verwendet, kann aber auch durch einen Ordner für ein entsprechend bearbeitetes anderes Buch analog ersetzt werden.

### Ausführung

Für die Ausführung wird hier zunächst die Benutzerinformation demonstriert. Man hat zwei Möglichkeiten, die JAR-Datei "coref-adder.jar" diese Informationen anzeigen zu lassen (Windows Kommandozeile):

1. mit dem Befehl *cd* in den Ordner mit der JAR-Datei "coref-adder.jar" wechseln
2. eine der folgenden Befehle eingeben:
  - *java -jar coref-adder.jar*
  - *java -jar coref-adder.jar -help*

Die Ausgabe in der Windows-Kommandozeile sollte dann so aussehen:
```
C:\project\folder>java -jar coref-adder.jar -help
usage: arguments for coref-adder.jar
 -corenlp               run Stanford CoreNLP first (optional)
 -folder <src-folder>   use given folder as source (required)
 -help                  print this message
 -pp                    include post-processing for DCoref in the CoreNLP
                        pipeline, ignored if -corenlp is not chosen
                        (optional)
C:\project\folder>
```

Um lediglich die von Gruppe 1 bereits gelieferten Daten z.B. für den Ordner "UncleTomsCabin" umzuformatieren, kann man die folgenden Schritte ausführen (Windows Kommandozeile):

1. mit dem Befehl *cd* in den Ordner mit der JAR-Datei "coref-adder.jar" wechseln
2. Die Umformatierung starten mit: *java -jar coref-adder.jar -folder UncleTomsCabin*

Die Ausgabe in der Windows-Kommandozeile sollte dann so aussehen:
```
C:\project\folder>java -jar coref-adder.jar  -folder UncleTomsCabin
Adapting XML-files for UncleTomsCabin ...
... processing chapter1.xml ... saving ... Done! [0.876 sec]
... processing chapter2.xml ... saving ... Done! [0.15 sec]
... processing chapter3.xml ... saving ... Done! [0.157 sec]
... processing chapter4.xml ... saving ... Done! [0.478 sec]
... processing chapter5.xml ... saving ... Done! [0.22 sec]
... processing chapter6.xml ... saving ... Done! [0.206 sec]
... processing chapter7.xml ... saving ... Done! [0.295 sec]
... processing chapter8.xml ... saving ... Done! [0.401 sec]
... processing chapter9.xml ... saving ... Done! [0.416 sec]
... processing chapter10.xml ... saving ... Done! [0.282 sec]
... processing chapter11.xml ... saving ... Done! [0.611 sec]
... processing chapter12.xml ... saving ... Done! [0.404 sec]
... processing chapter13.xml ... saving ... Done! [0.151 sec]
... processing chapter14.xml ... saving ... Done! [0.204 sec]
... processing chapter15.xml ... saving ... Done! [0.338 sec]
... processing chapter16.xml ... saving ... Done! [0.438 sec]
... processing chapter17.xml ... saving ... Done! [0.418 sec]
... processing chapter18.xml ... saving ... Done! [0.366 sec]
... processing chapter19.xml ... saving ... Done! [0.471 sec]
... processing chapter20.xml ... saving ... Done! [0.316 sec]
... processing chapter21.xml ... saving ... Done! [0.081 sec]
... processing chapter22.xml ... saving ... Done! [0.11 sec]
... processing chapter23.xml ... saving ... Done! [0.153 sec]
... processing chapter24.xml ... saving ... Done! [0.111 sec]
... processing chapter25.xml ... saving ... Done! [0.081 sec]
... processing chapter26.xml ... saving ... Done! [0.284 sec]
... processing chapter27.xml ... saving ... Done! [0.127 sec]
... processing chapter28.xml ... saving ... Done! [0.305 sec]
... processing chapter29.xml ... saving ... Done! [0.124 sec]
... processing chapter30.xml ... saving ... Done! [0.2 sec]
... processing chapter31.xml ... saving ... Done! [0.099 sec]
... processing chapter32.xml ... saving ... Done! [0.138 sec]
... processing chapter33.xml ... saving ... Done! [0.152 sec]
... processing chapter34.xml ... saving ... Done! [0.215 sec]
... processing chapter35.xml ... saving ... Done! [0.101 sec]
... processing chapter36.xml ... saving ... Done! [0.13 sec]
... processing chapter37.xml ... saving ... Done! [0.104 sec]
... processing chapter38.xml ... saving ... Done! [0.173 sec]
... processing chapter39.xml ... saving ... Done! [0.184 sec]
... processing chapter40.xml ... saving ... Done! [0.116 sec]
... processing chapter41.xml ... saving ... Done! [0.104 sec]
... processing chapter42.xml ... saving ... Done! [0.112 sec]
... processing chapter43.xml ... saving ... Done! [0.133 sec]
... processing chapter44.xml ... saving ... Done! [0.06 sec]
... processing chapter45.xml ... saving ... Done! [0.171 sec]
Complete! [10.858 sec]
Creating index for UncleTomsCabin ... Complete! [3.048 sec]

C:\project\folder>
```
