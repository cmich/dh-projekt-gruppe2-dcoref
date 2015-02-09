# dh-projekt-gruppe2-dcoref/AddCorefStanford

Das Projekt *AddCorefStanford* stellt die Variante der Lösung der Aufgabe von Gruppe 2 dar, die das Modul [DCoref](http://nlp.stanford.edu/software/dcoref.shtml) der [Stanford CoreNLP](http://nlp.stanford.edu/software/corenlp.shtml) verwendet. Die grundlegende Aufgabe ist das *Hinzufügen von Koreferenzinformationen* zu den von Gruppe 1 vorbereiteten Daten. Dazu wird zunächst DCoref verwendet. Die erhaltene Ausgabe pro Kapitel wird in das mit Gruppe 3 vereinbarte Format umgeformt und zusätzlich wird ein Index erstellt, der die Koreferenzketten pro Kapitel verbindet, die zusammengehören. Die dabei verwendete Ordnerstruktur ist in diesem [Repository](https://github.com/Rostu/dh-Projekt-Gruppe1) von Gruppe 1 einsehbar.

Nach Absprache fügt Gruppe 1 die Koreferenzinformationen bereits hinzu, da sie die [Stanford CoreNLP](http://nlp.stanford.edu/software/corenlp.shtml) ebenfalls verwendet und bereits für jedes Buch laufen lässt. Trotzdem besitzt die ausführbare JAR-Datei "coref-adder.jar" im Ordner "[executable](https://github.com/cmich/dh-projekt-gruppe2-dcoref/tree/master/AddCorefStanford/executable)" eine Option (-corenlp, siehe [Anleitung](#anleitung)), die das Modul [DCoref](http://nlp.stanford.edu/software/dcoref.shtml) hinzuschaltet. Verwendet man diese Option, kann man ebenfalls einen Parameter (-pp, siehe [Anleitung](#anleitung)) an dieselbe ausführbare Datei übergeben, der Postprocessing (s. [Beschreibung](http://nlp.stanford.edu/pubs/conllst2011-coref.pdf) der Autoren) aktiviert, da dieser Schritt im Modul [DCoref](http://nlp.stanford.edu/software/dcoref.shtml) standardmäßig deaktiviert ist.

## Voraussetzungen
- Java Runtime Environment, Version 1.8 ([Download](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html]adasd))

- Stanford CoreNLP, insbesondere die JAR-Datei "stanford-corenlp-3.5.0-models.jar" ([Download](http://nlp.stanford.edu/software/stanford-corenlp-full-2015-01-29.zip))

## Anleitung

asdfasdfdsaf

