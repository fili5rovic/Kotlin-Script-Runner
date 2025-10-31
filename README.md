# Kotlin Script Executor

A cross-platform JavaFX-based GUI tool that allows users to write, execute, and monitor **Kotlin scripts** in real time.  
The tool provides an integrated **code editor** with syntax highlighting and a **console output pane** that displays live execution results.

---

## Overview

This project fulfills the task of developing a GUI-based scripting tool in **Java** that supports executing **Kotlin** scripts using the `kotlinc -script` command.  
It enables users to enter, execute, and observe script output side-by-side — designed to behave similarly to a lightweight IDE.

---

## Features

### Core Requirements
- **Editor pane** - based on `CodeArea` from RichTextFX, supports typing and editing Kotlin code.
- **Output pane** – displays both standard output and error output from the running script.
- **Script saving** – the current script is written to a `.kts` file before execution.
- **Execution** – uses `kotlinc -script <file>` to run the script via a background process.
- **Live output streaming** – output is redirected line-by-line as the script runs.
- **Error handling** – compilation or runtime errors are displayed in red within the output pane.
- **Running indicator** – visual indication when a script is currently executing.
- **Exit code indicator** – shows if the last execution ended with a non-zero exit code.

### Additional Functionality
- **Syntax highlighting** – Kotlin keywords (such as `fun`, `val`, `var`, `if`, `else`, `when`, `for`, `while`, `return`, `class`) are highlighted in color for readability.
- **Clickable errors** – when an error message includes a location (e.g., `script:2:1`), clicking it positions the caret at the corresponding line and column in the editor.
- **Cross-platform** – works on Windows, macOS, and Linux (UTF-8 encoding ensured across all systems).
- **Responsive UI** – non-blocking execution using background threads, allowing continued editing during long runs.

---

## Technology Stack

- **Language:** Java 17  
- **GUI Framework:** JavaFX  
- **Editor Component:** RichTextFX  
- **Build Tool:** Maven  
- **Scripting Backend:** Kotlin Compiler (`kotlinc -script`)

---

### Prerequisites
- Java 17 or newer installed  
- Kotlin compiler (`kotlinc`) available in system PATH  
- Maven installed  
