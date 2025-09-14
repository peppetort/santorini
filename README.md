# 🏛️ Santorini

![Santorini](src/main/resources/img/santorini-logo.png)

A **lightweight and fast Java implementation** of the *Santorini* board game by Cranio Creations.  
The project supports both **Command Line Interface (CLI)** and **Graphical User Interface (GUI)** (JavaFX) and can be played in distributed mode over sockets.


## ✨ Features

### 🔌 Distributed Architecture
- Server and client implemented using **Java Sockets**
- Multiple concurrent game sessions supported

### 📜 Complete Rules
- Fully supports **2- and 3-player games**
- Play **with or without god powers**
- Implemented gods:
  - Apollo
  - Artemis
  - Athena
  - Atlas
  - Demeter
  - Hephaestus
  - Minotaur
  - Pan
  - Prometheus

### 💻 CLI
- Cross-platform **command-line interface**
- Tested on:
  - Linux (Bash)
  - Windows (cmd, PowerShell, Windows Terminal)
- Windows users: enable ANSI colors via registry (see below)

### 🖼️ GUI
- User interface built with **JavaFX**
- Dependencies already bundled in the deliverables

### ⏪ Undo Functionality
- During a turn, the player may **undo** to the initial state of the turn
- If mandatory moves are completed, you have **5 seconds** to undo before the turn is passed automatically

## ⚙️ Requirements

- **Java JRE 8**  
  [Download JRE 8](https://www.oracle.com/java/technologies/javase-jre8-downloads.html)  
  On Windows, ensure the `JAVA_HOME` and global `java` path are set correctly.

### CLI – Windows Setup
If you use the legacy **cmd** terminal, enable ANSI colors:

```cmd
REG ADD HKCU\CONSOLE /f /v VirtualTerminalLevel /t REG_DWORD /d 1
```

Alternatively, use **Windows Terminal** (recommended, available free from the Microsoft Store).


## 🚀 How to Start the Game

### 🖥️ Server

On Linux / macOS / Windows:

```bash
cd /path_to_Santorini_folder/deliverables
java -jar Server.jar
```

---

### 🖼️ Client – GUI

* Go to the `deliverables` folder
* Double-click:

  * **SantoriniGUI.jar** → connects to a local server (`localhost`)
  * **SantoriniGUI\_remote.jar** → connects to a remote test server


### 💻 Client – CLI

On Linux / macOS / Windows:

```bash
cd /path_to_Santorini_folder/deliverables
```

If server is on `localhost`:

```bash
java -jar SantoriniCLI.jar
```

If using a **remote test server**:

```bash
java -jar SantoriniCLI_remote.jar
```


## 🧪 Testing & Coverage

Unit tests written with **JUnit**. Coverage summary:

| **Package** | **Class Coverage** | **Line Coverage** |
| ----------- | ------------------ | ----------------- |
| CLI         | 0%                 | 0%                |
| Client      | 40%                | 12%               |
| Controller  | 100%               | 92%               |
| Exceptions  | 50%                | 50%               |
| GUI         | 0%                 | 0%                |
| Messages    | 76%                | 51%               |
| Model       | 100%               | 96%               |
| Observer    | 100%               | 37%               |
| Server      | 0%                 | 0%                |
| View        | 0%                 | 0%                |

---

## 📚 Documentation

JavaDoc is included in the repository:
Open [`deliverables/JavaDoc/index.html`](deliverables/JavaDoc/index.html) with any browser.

---

## 🏗️ Project Structure

```
Santorini/
├── deliverables/        # Packaged JARs + JavaDoc
├── src/main/java/       # Source code (MVC structure)
│   ├── client/          # Client logic (CLI + GUI)
│   ├── controller/      # Game controller
│   ├── model/           # Game logic, rules, board state
│   ├── server/          # Server logic
│   └── view/            # Views and UI handling
├── src/test/java/       # JUnit test cases
└── src/main/resources/  # Assets (logo, configs)
```