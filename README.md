# Kotlin Script Executor
A modern GUI application for writing and executing Kotlin scripts with real-time output, proper error handling, syntax highlighting, and multiple theme support.

<a href="https://www.jdeploy.com/~kotlin-script-executor?all=1" target="_blank">
  <img src="https://img.shields.io/badge/DOWNLOAD-blue?style=for-the-badge" alt="Download">
</a>


## Project Demo
https://github.com/user-attachments/assets/c75f0fd1-5436-4538-a4a2-01dd0f6c34a2
> [!NOTE]
> The demo showcases the core features of the project. For a detailed overview of all available features, visit the [Features](#features) section.

## Features
### Intuitive Graphical User Interface
The application, built with JavaFX and styled with SCSS, provides a clean and modern environment for Kotlin script development. The interface includes:
- A split-pane layout with a code editor and console output
- Menu bar with File, Edit, Windows and About options for easy navigation
- Run and Stop buttons for easily running scripts
- Status bar displaying current line and column position
- Visual indicators for script execution status
- Theme selector for personalized appearance

### Real-Time Script Execution
The execution system is optimized for responsiveness and clarity:
- **Asynchronous execution** - Scripts run without blocking the UI
- **Live output streaming** - See results as they're generated
- **Long-running script support** - Proper handling of scripts that take time to execute
- **Clear execution separation** - Visual dividers between multiple script runs
- **Exit code indication** - Color-coded status showing success or failure
- **Error highlighting** - Clear display of compilation and runtime errors

### Advanced Code Editor
The code editor provides professional-grade features:
- **Syntax highlighting** - Color-coded keywords, strings, numbers, comments, and more
- **Auto-indentation** - Smart indentation when pressing Enter
- **Line/column tracking** - Real-time cursor position display in the status bar
- **Smooth scrolling** - Custom-styled scrollbars matching the application theme
- **Multi-line editing** - Full support for complex scripts
- **Changing orientation** - Change between horizontal/vertical orientation based on your liking

### Customizable Themes
The application supports multiple carefully designed dark themes, each optimized for readability and reduced eye strain:

1. **Darcula Blue** (Default) - IntelliJ IDEA inspired theme with blue accents
2. **Monokai Night** - Popular theme with vibrant pink and yellow highlights
3. **Dracula Purple** - Modern theme featuring purple and cyan tones
4. **GitHub Dark** - Clean theme matching GitHub's dark mode aesthetic

All themes feature:
- High contrast for improved readability
- Carefully selected color palettes to reduce eye fatigue
- Consistent styling across all UI elements
- Smooth theme transitions without application restart

### File Management
Comprehensive file operations ensure your work is never lost:
- **Create new files** - Start fresh scripts with a single click
- **Open existing files** - Load `.kts` files from anywhere on your system
- **Save scripts** - Persist your work to disk
- **Temporary file handling** - Unsaved scripts are automatically saved to temporary files for execution
- **File path tracking** - The application remembers which file you're working on

## Prerequisites

- **Java 17 or higher** (JDK required for building and running)
- **Kotlin Compiler** installed and available in your system PATH
  - Download from: https://kotlinlang.org/docs/command-line.html
  - Verify installation by running: `kotlinc -version`
- **Maven** (for building from source)
- **JavaFX SDK**

## Keyboard Shortcuts

| Shortcut | Action | Description |
|----------|--------|-------------|
| **Script Execution** | | |
| `Ctrl + R` | Run Script | Execute the current script |
| `Ctrl + Q` | Stop Script | Stop the currently running script |
| **File Operations** | | |
| `Ctrl + Shift + N` | New Script | Create a new script file |
| `Ctrl + Shift + O` | Open File | Open an existing file |
| `Ctrl + S` | Save File | Save the current file |
| **Editing** | | |
| `Ctrl + Z` | Undo | Undo last action |
| `Ctrl + Y` | Redo | Redo last undone action |
| `Ctrl + X` | Cut | Cut selected text |
| `Ctrl + C` | Copy | Copy selected text |
| `Ctrl + V` | Paste | Paste text from clipboard |
| `Delete` | Delete | Delete selected text |
| `Ctrl + A` | Select All | Select all text |
| `Ctrl + Alt + L` | Format Code | Auto-indent and format code |
| **Code Navigation** | | |
| `Ctrl + D` | Delete Line | Delete current line |
| `Ctrl + Shift + ↑` | Duplicate Above | Duplicate line above current position |
| `Ctrl + Shift + ↓` | Duplicate Below | Duplicate line below current position |
| `Ctrl + ↑` | Move Up | Move line up |
| `Ctrl + ↓` | Move Down | Move line down |
| `Ctrl + /` | Toggle Comment | Comment/uncomment current line or selection |
| **Code Completion** | | |
| `Ctrl + Space` | Apply Template | Show and apply available code templates |
| `Ctrl + H` | Templates Help | View templates documentation |

## Code Templates

Code templates allow you to quickly insert common code patterns. Press `Ctrl + Space` to activate a template.

| Trigger | Description | Generated Code |
|---------|-------------|----------------|
| `p` | Print empty string | `println("");` |
| `pv` | Print variable | `println();` |
| `for` | For loop | `for (i in 1..) {`<br>`    `<br>`}` |
| `fun` | Function declaration | `fun () {`<br>`    `<br>`}` |
| `main` | Main entry point | `fun main() {`<br>`    `<br>`}` |
| `try` | Try-catch block | `try {`<br>`    `<br>`} catch (e: Exception) {`<br>`    `<br>`}` |
| `if` | If statement | `if () {`<br>`    `<br>`}` |
| `ife` | If-else statement | `if () {`<br>`    `<br>`} else {`<br>`    `<br>`}` |
| `ifel` | If-else-if-else chain | `if () {`<br>`    `<br>`} else if () {`<br>`    `<br>`} else {`<br>`    `<br>`}` |
| `sleep` | Thread sleep with exception handling | `try {`<br>`    Thread.sleep()`<br>`} catch (e: InterruptedException) {`<br>`    e.printStackTrace()`<br>`}` |

**Usage:** Type the trigger word and press `Ctrl + Space` to insert the template. The cursor will automatically position at the appropriate location for you to continue typing.

## Building and Running

### Building from Source

1. **Clone the repository**
`git clone https://github.com/yourusername/kotlin-script-executor.git`, then run
`cd kotlin-script-executor` 

2. **Build with Maven**
```mvn clean package```  

3. **Locate the JAR file**
   
   After successful build, the executable JAR will be located at:
   
   `target/scriptexecutor-1.0-SNAPSHOT.jar`

### Running the Application

#### From IDE (Recommended)

1. Open the project in IntelliJ IDEA or Eclipse
2. Locate the main class: `fili5rovic.scriptexecutor.ScriptExecutor`
3. Run the main class
> [!NOTE]
> IntelliJ IDEA is recommended as the project was developed and tested in this IDE.

#### From JAR

`java -jar scriptexecutor-1.0-SNAPSHOT.jar`

## Technical Details

### Architecture

The application follows a modular, event-driven architecture:

- **JavaFX** - Modern cross-platform GUI framework
- **RichTextFX** - Advanced text editing component with syntax highlighting support
- **Event Bus Pattern** - Decoupled component communication using a custom event system
- **Asynchronous Processing** - Non-blocking script execution using `CompletableFuture`
- **MVC Pattern** - Separation of concerns with FXML views and controllers
- **SCSS Styling** - Maintainable styles with variables and nested rules
