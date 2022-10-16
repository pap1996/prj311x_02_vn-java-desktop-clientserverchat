# prj311x_02_vn-java-desktop-clientserverchat

<p align="center"><a href="https://funix.edu.vn/gioi-thieu-funix/"><img src="/res/image/funix.png" width="200"/></a></p>

 This folder contains my submission to the assignment 4 `Chat App` in the course PRJ311x_02_VN (Programming Desktop App with Java), a part of the path Software Engineering provided by FUNiX.



## About the project

- This project simulates simple conversations (or chat) between Server and many Clients
- **Keyword**: _Java FX (controls, layout, file fxml, scene builder, observable collections), MVC model, Java I/O, stream, Socket programming, TCP, multi-thread_.

## Functional description
The project consists of 2 components:
1. **Client app:**
The user inferface from Client app should have below features:
- Text fields to connect to server such `host ip`, `port`
- `Username` of the client participating in the chat with **Server**
- `Connect` button to establish connection to **Server**
- Text area for displaying lines of messages
- Text field to get input and `Send` button to send
- On clicking `Connect` button, the button will be greyed out if successful connection established (and ready to chat with server). Otherwise, an alert window will pop up

![Client Interface](/res/image/client_ui.png)   


|![Client Interface](/res/image/client_ui_disconnected.png)  |![Client Interface](/res/image/client_ui_connected.png)|
| :------------- | :------------- |
| If server don't avail, an alert will appear| If connection is successfully established|

2. **Server app:**
The user inferface from Server App consists of:
- A window to display all clients successfully connected to server




## Demo

The demo video can be seen via: https://www.youtube.com/watch?v=GB74eQVRANo


## Further enhancement
