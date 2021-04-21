#  to compile

cd binaryTree  
javac  BSTInterface.java BSTNode.java

cd ../wiredBST
javac -cp ../ WiredBST.java

cd ../ioHandler
javac -cp ../ IOHandler.java

cd ../student
javac Student.java

cd ../treeGUI
javac -cp ../ TreePrinter.java GUIApplication.java

#  To execute compiled code, run following commands

# cd ../main
# java -cp ../ Main.java
