#! /bin/bash 
echo "STARTING JFLEX COMPILING"
java -jar /home/jesfrin/Documentos/libreriasJava/jflex-1.7.0/lib/jflex-full-1.7.0.jar -d /home/jesfrin/NetBeansProjects/compi2/src/gnz/backend/analizadores lexico.flex

echo "STARTING CUP COMPILING"
java -jar /home/jesfrin/Documentos/libreriasJava/java-cup-11b.jar parser.cup 
mv parser.java /home/jesfrin/NetBeansProjects/compi2/src/gnz/backend/analizadores/parser.java
mv sym.java /home/jesfrin/NetBeansProjects/compi2/src/gnz/backend/analizadores/sym.java



