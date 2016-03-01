cd bin&&
jar="../d4.jar"&&
jar cvf $jar d4/*&&
jarsigner $jar d4.applet&&
ls -l $jar
echo done

