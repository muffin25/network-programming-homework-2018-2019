
My chat application:
It works by first runnung the servertest class to activate the chat server, then running the other 3 client test classes to add clients to the chat.Each client will get his desicnated folder in witch there are some files.The clients type a message and send it to the server, after that the server sends the message to all clients with the port number of the client infront.If one client types "Send file" the server will return "Sending..." and all other clients wont be able to type any more.The client that is sending then needs to enter the name of a file in his folder(only the name of his file) that is smaller than 63kbytes(its a udp server so i want it to be small).After entering the name the client will auto send the file to the server, and then the server will send it to all clients, creating files with the same name in their folders.(The file must not already exist in them).If all goes well then a File sent message will appear and everyone will be able to type again.

# Chat Application using multicasting 
You need to implement a multicast chat application in which each participant can send and 
receive chat messages from all other participants. Before sending message the participant 
must be asked by the programm what type of message he should send.[TEXT, IMAGE, VIDEO].



# HTTP Server 
You need to implement HTTP Server which can handle GET and POST methods. You can use some browser for the client side.

Example with GET method:
If the browser send the following request: localhost:8080/batman.png
The server should search for image named batman.png on the machine and send the image(or whatever file is requested) to the browser.
If the file is not present it should send a user friendly error to the browser.
Different file types that server should be able to handle are videos, images, text files.

Example with POST method:
You should be able to upload a random file to the server.

# How to submit the homework

In order to submit your homework you must have github account. If you don't have github account you have to create one. Next you have to download the version control system - GIT.
You can do it from the official web site https://git-scm.com/downloads. 

After you did the aforementioned steps you are ready to proceed.

In order to create copy of the repository you should **fork** (Click Fork button in the top right corner) the repository.  By forking the repository you allow me to view the history of your work. (**Uploading the whole project functionality at once will count as cheating**).

Now after you have copied the repository you can download it locally and start working on the task you have choosen.
In order to download the project locally you need to use the following command:
git clone https://github.com/YOUR_USERNAME/network-programming-homework-2018-2019.git

Inside the repository you forked, create a file named with your faculty number. For example **81XXX**.

Your source code should be in package **com.fmi.mpr.hw.chat** or **com.fmi.mpr.hw.http** depending on the task you have choosen.

For more information how to upload code from your local repository to the master repository you can use the following link https://git-scm.com/docs.

The end date of the homework is: **02 January 2019**.
