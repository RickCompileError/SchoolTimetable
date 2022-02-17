cd src
javac -encoding utf-8 -d ../bin/ *.java
cd ..
cd bin
jar -c -f ../jar.jar -e GUI *.class
