# Train Simulator CLI

![Java Badge](68747470733a2f2f696d672e736869656c64732e696f2f62616467652f6a6176612d2532334544384230302e7376673f7374796c653d666f722d7468652d6261646765266c6f676f3d6f70656e6a646b266c6f676f436f6c6f723d7768697465.svg)

This terminal-based train simulator provides a simulated command-line experience of a train operating along Melbourne’s Pakenham line.  
Built in Java with Eclipse and thoroughly tested using JUnit, the simulator derives passenger count data from CSV files and offers train customisation and statistical reporting.

---

## Main Menu

![Main Menu Screenshot](readme-images/Main-Menu.png)

---

## Features

- **Line Simulation:** Simulates the Pakenham line from Flinders Street to Pakenham, including intermediate stations.
- **Train Configuration:** Create and delete train carriages, toggle direction and train line of choice.
- **Station Configuration:** Edit the passenger demand for each platform. For each time session of the day, add or remove station platforms.
- **Passenger Data:** Read and parse CSV files containing historical passenger counts per station, per hour.
- **Custom Schedules:** Define time session, each with their own data.
- **Real-time Statistics:** Display on-demand stats such as CO2 Emissions (overall and specific to train sections — locomotives/carriages), passengers left behind, total passengers carried, and the number of complaints.
- **JUnit Test Suite:** Comprehensive automated tests covering core functionality, data parsing, and statistical calculations.

> **Note:** The train line does not contain every station.

---

## Technology Stack

- **Language:** Java 11+
- **IDE:** Eclipse IDE 2024-06 or later
- **Build & Dependency:** Maven or Gradle (configurable)
- **Testing:** JUnit 5
- **Data:** CSV files for passenger counts

---

## Installation

### 1️⃣ Clone the repository

```bash
git clone https://github.com/<s4096770>/train-simulator-cli.git
cd train-simulator-cli
