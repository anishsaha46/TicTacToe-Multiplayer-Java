# 🎮 Tic Tac Toe Multiplayer Game  

A **multiplayer Tic Tac Toe game** implemented in Java using **sockets** for network communication and **JavaFX** for the graphical user interface.  

![Tic Tac Toe Banner](https://via.placeholder.com/800x300?text=Tic+Tac+Toe+Multiplayer+Game)  

---

## 📌 **Table of Contents**  

- [✨ Features](#-features)  
- [⚙️ Prerequisites](#-prerequisites)  
- [🛠️ Setup](#-setup)  
- [🚀 Running the Project](#-running-the-project)  
- [🎮 Usage](#-usage)  

---

## ✨ **Features**  

✔️ Multiplayer support for **two players**  
✔️ **Real-time** gameplay using **Java sockets**  
✔️ Interactive **Graphical User Interface (GUI) with JavaFX**  
✔️ **Win, Draw, and Invalid Move notifications**  
✔️ Easy-to-use **client-server architecture**  

---

## ⚙️ **Prerequisites**  

Before running the project, ensure you have:  

✅ **Java Development Kit (JDK) 11+** → [Download JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)  
✅ **JavaFX SDK** → [Download JavaFX](https://gluonhq.com/products/javafx/)  
✅ **Apache Maven** (for dependency management) → [Download Maven](https://maven.apache.org/download.cgi)  
✅ **Git** (for cloning the repository) → [Download Git](https://git-scm.com/downloads)  

---

## 🛠️ **Setup**  

### **1️⃣ Install Required Dependencies**  
📌 **Java Development Kit (JDK)**  
sh:
sudo apt install openjdk-11-jdk  # Linux
brew install openjdk@11          # macOS
choco install openjdk11          # Windows (using Chocolatey)


📌 JavaFX SDK (if not included in your JDK)

# Set JavaFX path (example)
export PATH_TO_FX=/path/to/javafx-sdk


📌 Maven
# Verify Maven Installation
mvn -version

2️⃣ Clone the Repository
git clone https://github.com/anishsaha46/tic-tac-toe-multiplayer.git
cd tic-tac-toe-multiplayer

🚀 Running the Project

🔧 Compile the Project
mvn compile

🖥️ Run the Server
java -cp out src.Main server 8080

👥 Run the Client
java -cp out src.Main client 8080


🖥️ Using IntelliJ IDEA
1️⃣ Run the Server:

Open Main.java
Right-click on the main method → Run 'Main.main()'
In Run Configurations, set program arguments to:
Server

2️⃣ Run the Client:

Open another instance of IntelliJ IDEA (or the same project)
Right-click on the main method → Run 'Main.main()'
In Run Configurations, set program arguments to:
client

🎮 Usage
🟢 Start the Server:

The server will wait for clients to connect.
🟢 Start the Clients:

Each client connects to the server.
Wait until both players are connected.
🟢 Play the Game:

Players click on the grid to make a move.
The game will show win, draw, or invalid move notifications.



