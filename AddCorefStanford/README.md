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

Für die Ausführung

dfsafsdf


Dieses Programm wurde nur aus Eclipse heraus gestartet, da es nur für einen sehr begrenzten Zweck gedacht war. Im Moment ist im Quellcode die Eingabedatei auf "chapter1_adapted_withpost.xml" und die Ausgabedatei auf "chapter1_adapted_withpost_xml.coref" festgelegt.

Wenn die Eingabedatei (z.B. "chapter1_adapted_withpost.xml") im gleichen Ordner wie "ProgramStanToRec.java" liegt, kann man wie folgt vorgehen (Windows Kommandozeile):

1. mit dem Befehl *cd* in den entsprechenden Ordner wechseln
2. Code kompilieren mit *javac ProgramStanToRec.java*
  - Die Datei "ProgramStanToRec.class" wird in demselben Ordner erstellt
3. Code ausführen mit *java ProgramStanToRec*
  - Die Datei "chapter1_adapted_withpost_xml.coref" wird in demselben Ordner erstellt

Die Ausgabe in der Windows-Kommandozeile sollte dann so aussehen:
```
C:\project\folder>javac ProgramStanToRec.java
C:\project\folder>java ProgramStanToRec
SentenceCount:          112
MaxTokenCount:          84

CoreferenceCount:       74
CoreferencesHandled:    74

MentionCount:           390
MentionsHandled:        390

MentionSizes:           [1, 2, 3, 4, 5, 7, 8, 14, 17, 20, 39]

C:\project\folder>
```
