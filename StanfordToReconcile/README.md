# dh-projekt-gruppe2-dcoref/StanfordToReconcile
Dieses Eclipse-Projekt ist nur zu dem Zweck enstanden, die DCoref-Ausgabe für den vereinbarten Textabschnitt (erste Hälfte des erste Kapitels im Buch "Uncle Tom's Cabin") in das Reconcile-Ausgabeformat umzuformen.

## Voraussetzungen
Java Runtime Environment, Version 1.8 ([Download](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html]adasd))

## Anleitung
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
