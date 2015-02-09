# dh-projekt-gruppe2-dcoref/AddCorefStanford

Das Projekt *AddCorefStanford* stellt die Variante der Lösung der Aufgabe von Gruppe 2 dar, die das Modul [DCoref](http://nlp.stanford.edu/software/dcoref.shtml) der [Stanford CoreNLP](http://nlp.stanford.edu/software/corenlp.shtml) verwendet. Die grundlegende Aufgabe ist das *Hinzufügen von Koreferenzinformationen* zu den von Gruppe 1 vorbereiteten Daten. Dazu wird zunächst DCoref verwendet. Die erhaltene Ausgabe pro Kapitel wird in das mit Gruppe 3 vereinbarte Format umgeformt und zusätzlich wird ein Index erstellt, der die Koreferenzketten pro Kapitel verbindet, die zusammengehören. Die dabei verwendete Ordnerstruktur ist in diesem [Repository](https://github.com/Rostu/dh-Projekt-Gruppe1) von Gruppe 1 einsehbar.

Nach Absprache fügt Gruppe 1 die Koreferenzinformationen bereits hinzu, da sie die [Stanford CoreNLP](http://nlp.stanford.edu/software/corenlp.shtml) ebenfalls verwendet und bereits für jedes Buch laufen lässt. Trotzdem besitzt die ausführbare JAR-Datei "coref-adder.jar" im Ordner "[executable](https://github.com/cmich/dh-projekt-gruppe2-dcoref/tree/master/AddCorefStanford/executable)" eine Option (-corenlp, siehe [Anleitung](#anleitung)), die das Modul [DCoref](http://nlp.stanford.edu/software/dcoref.shtml) hinzuschaltet. Verwendet man diese Option, kann man ebenfalls einen Parameter (-pp, siehe [Anleitung](#anleitung)) an dieselbe ausführbare Datei übergeben, der Post-Processing (s. [Beschreibung](http://nlp.stanford.edu/pubs/conllst2011-coref.pdf) der Autoren) aktiviert, da dieser Schritt im Modul [DCoref](http://nlp.stanford.edu/software/dcoref.shtml) standardmäßig deaktiviert ist.

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
C:\project\folder>java -jar coref-adder.jar -folder UncleTomsCabin
Adapting XML-files for UncleTomsCabin ...
... processing chapter1.xml ... saving ... Done! [0.876 sec]
... [verkürzt]
... processing chapter45.xml ... saving ... Done! [0.171 sec]
Complete! [10.858 sec]
Creating index for UncleTomsCabin ... Complete! [3.048 sec]

C:\project\folder>
```

Will man zusätzlich vor der Umformatierung [DCoref](http://nlp.stanford.edu/software/dcoref.shtml) auf die Reintext-Dateien der Kapitel anwenden, nutzt man die Option -corenlp, entweder mit oder ohne Post-Processing-Option -pp. Also (Windows Kommandozeile):

1. mit dem Befehl *cd* in den Ordner mit der JAR-Datei "coref-adder.jar" wechseln
2. Die Verarbeitung starten mit: 
  - *java -jar coref-adder.jar -folder UncleTomsCabin -corenlp* (ohne Post-Processing)
  - *java -jar coref-adder.jar -folder UncleTomsCabin -corenlp -pp* (mit Post-Processing)

Die Ausgabe in der Windows-Kommandozeile _ohne Post-Processing_ sollte dann so aussehen:
```
C:\project\folder>java -jar coref-adder.jar -folder UncleTomsCabin -corenlp
Running Stanford CoreNLP for UncleTomsCabin
... configuring pipeline ... Done! [12.21 sec]
... annotating "chapter1.txt" ... saving ... Done! [46.312 sec]
... [verkürzt]
... annotating "chapter45.txt" ... saving ... Done! [128.804 sec]
Complete! [2893.073 sec]
Adapting XML-files for UncleTomsCabin ...
... processing chapter1.xml ... saving ... Done! [0.798 sec]
... [verkürzt]
... processing chapter45.xml ... saving ... Done! [0.201 sec]
Complete! [11.017 sec]
Creating index for UncleTomsCabin ... Complete! [3.127 sec]

C:\project\folder>
```

Die Ausgabe in der Windows-Kommandozeile _mit Post-Processing_ sollte dann so aussehen:
```
C:\project\folder>java -jar coref-adder.jar -folder UncleTomsCabin -corenlp -pp
Running Stanford CoreNLP for UncleTomsCabin ... with post-processing ...
... configuring pipeline ... Done! [11.01 sec]
... annotating "chapter1.txt" ... saving ... Done! [48.549 sec]
... [verkürzt]
... annotating "chapter45.txt" ... saving ... Done! [131.207 sec]
Complete! [2893.073 sec]
Adapting XML-files for UncleTomsCabin ...
... processing chapter1.xml ... saving ... Done! [0.901 sec]
... [verkürzt]
... processing chapter45.xml ... saving ... Done! [0.169 sec]
Complete! [12.003 sec]
Creating index for UncleTomsCabin ... Complete! [4.011 sec]

C:\project\folder>
```

### Ausgabe

Die Ausgabedateien befinden sich in den folgenden Unterordnern der bestehenden Ordnerstruktur, z.B. für "[UncleTomsCabin](https://github.com/Rostu/dh-Projekt-Gruppe1/tree/master/UncleTomsCabin)":

#### Annotierte Kapitel, unformatiert

Die unformatierten Dateien, die [DCoref](http://nlp.stanford.edu/software/dcoref.shtml) ausgibt, werden in dem Unterordner "[output/chapters](https://github.com/Rostu/dh-Projekt-Gruppe1/tree/master/UncleTomsCabin/output/chapters)" wie folgt angelegt:

- z.B. "[output/chapters/annotated/chapter1.xml](https://github.com/Rostu/dh-Projekt-Gruppe1/tree/master/UncleTomsCabin/output/chapters/annotated/post-processing)" beim Aufruf ohne Postprocessing
- z.B. "[output/chapters/annotated/post-processing/chapter1.xml](https://github.com/Rostu/dh-Projekt-Gruppe1/tree/master/UncleTomsCabin/output/chapters/annotated/post-processing)" beim Aufruf mit Postprocessing

#### Annotierte Kapitel, für Gruppe 3 formatiert

Die für Gruppe 3 formatierten Dateien der durch [DCoref](http://nlp.stanford.edu/software/dcoref.shtml) annotierten Kapitel werden in dem Unterordner "[output/chapters](https://github.com/Rostu/dh-Projekt-Gruppe1/tree/master/UncleTomsCabin/output/chapters)" wie folgt angelegt:

- z.B. "[output/chapters/extracted/UncleTomsCabin_chapter1.xml](https://github.com/Rostu/dh-Projekt-Gruppe1/tree/master/UncleTomsCabin/output/chapters/extracted)" beim Aufruf ohne Postprocessing
- z.B. "[output/chapters/extracted/post-processing/UncleTomsCabin_chapter1.xml](https://github.com/Rostu/dh-Projekt-Gruppe1/tree/master/UncleTomsCabin/output/chapters/extracted/post-processing)" beim Aufruf mit Postprocessing

#### Index

Der Index wird in dem Unterordner "[output](https://github.com/Rostu/dh-Projekt-Gruppe1/tree/master/UncleTomsCabin/output)" wie folgt angelegt:
 
- "[output/index_pp.xml](https://github.com/Rostu/dh-Projekt-Gruppe1/tree/master/UncleTomsCabin/output)" beim Aufruf ohne Postprocessing
- "[output/index_pp.xml](https://github.com/Rostu/dh-Projekt-Gruppe1/tree/master/UncleTomsCabin/output)" beim Aufruf mit Postprocessing
