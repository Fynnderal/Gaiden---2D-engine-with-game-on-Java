# Gaiden Engine & Game

A lightweight 2D game engine and adventure game built from scratch in Java using JavaFX. The project demonstrates the architecture of fundamental game systems—rendering, physics, and state management—entirely from scratch, avoiding heavy external frameworks. 

For further details regarding project requirements and architecture, refer to `userdoc.pdf`  within the repository.





https://github.com/user-attachments/assets/830a445d-db0e-4b7e-a2be-f741d8e3eaee




---

## 🛠️ Core Engine & Technical Specifications

This project implements several low-level game engine subsystems from scratch:
* **Custom Rendering & Animation Loop:** Handles high-performance 2D sprite rendering and frame-by-frame character animations.
* **Dynamic Map Generation & Viewport Culling:** Features real-time generation of the game world layout paired with a culling mechanism that dynamically removes out-of-bounds graphical regions (map cutting).
* **Custom Font & UI Subsystem:** A manual system for rendering customized pixel fonts, dynamic text blocks, heads-up displays (HUD), and user interface elements.
* **Dialogue Engine:** A fully custom branching dialogue infrastructure capable of handling interactive text sequences and selection trees between the player and NPCs.
* **Low-Level Collision System:** High-accuracy real-time collision detection handling entity-to-wall boundaries and structural grid limits.
* **State Serialization (Save/Load System):** A persistence engine that writes and reads game progress and player state directly to the disk, paired with seamless custom loading and transition screens.
* **AI Subsystem:** Distinct behavior states for non-player characters, driving enemy pathfinding, awareness triggers, and state routines across multiple levels.

---

## 🎮 Gameplay Mechanics & Systems

* **Core Objective:** The primary goal on each level is to successfully navigate the layout and locate the exit elevator.
* **Health & Survival:** The user monitors the player's health bar, which decreases upon sustaining hits from hostile entities and can be replenished using various food items or medical supplies.
* **Dynamic Dialogue Panel:** Displays live text conversations between the main character and interactive entities, offering player-selectable answer pathways.
* **Advanced Inventory System:** Players open a specialized inventory grid to inspect collected gear. The system uses an interactive selection pointer and provides situational contextual action hints at the bottom-right of the screen for equipping, using, discarding, or combining assets.

### Interactive Environmental Elements
* **Vending Machines:** Automated machines found scattered across the layout that dispense a single item, though accessing them requires a screwdriver.
* **Ventilation Shafts:** Networked air ducts that form pathways between rooms or unlock hidden secret chambers, requiring a tool to open.
* **Secured Doors:** Standard locked barriers block passage and require matching keys, while oxidized rusty doors mandate the use of a heavy crowbar.

---

## ⚔️ Combat & Entity Index

### Arsenal Specs
* **Pistol:** A highly accurate single-shot firearm that deals substantial damage per hit and utilizes standard pistol rounds.
* **Shotgun:** A devastating spread weapon that discharges multiple scattered pellets simultaneously]. While individual pellets inflict less damage than a pistol round, the full blast delivers extreme power at close range using shotgun shells.

### Hostile & Friendly AI Behaviors
* **Zombie (AI Tier 1):** A weak, slow-moving threat with low maximum health that immediately targets and pursues the player upon establishing visual contact.
* **Watchman (AI Tier 2):** A dangerous, blind elite enemy boasting massive health reserves and devastating attack power. It relies entirely on acoustic detection, patrolling the zone dynamically if it detects audible movements, making avoidance vital to bypass them.
* **Merchant (Neutral NPC):** A passive trader who exchanges valuable weapons and survival items for tea bags collected throughout the zone.

---

## 🎒 Item Registry & Crafting Mechanics

| Item | Primary Function / Interaction |
| :--- | :--- |
| **Teabag** | Used as currency with the Merchant or combined with water to brew functional tea. |
| **Water** | Consumable item that restores a minor quantity of the player's health bar. |
| **Tea** | Crafted item that instantly replenishes exactly 40% of the player's total health capacity[cite: 1]. |
| **Chocolate Bar** | Quick snack item that restores 10% of maximum health[cite: 1]. |
| **Medkit** | The ultimate medical supply asset capable of providing massive health restoration[cite: 1]. |
| **Screwdriver** | Structural tool required to open ventilation shafts and extract items from vending machines[cite: 1]. |
| **Duct Tape** | Maintenance item explicitly used to repair a damaged or broken screwdriver[cite: 1]. |
| **Key** | Standard security item used to unlock normal closed doors[cite: 1]. |
| **Crowbar** | Heavy utility item necessary to break open heavy, rusty doors[cite: 1]. |

---

## 🕹️ Controls Guide

The game employs a hybrid keyboard and mouse setup for full combat and tactical inventory navigation[cite: 1].

### Tactical Action Controls
| Action | Key / Input |
| :--- | :--- |
| Move Character Up | **W**[cite: 1] |
| Move Character Down | **S**[cite: 1] |
| Move Character Left | **A**[cite: 1] |
| Move Character Right | **D**[cite: 1] |
| Aim Weapon | **Hold Left Mouse Button (LMB)**[cite: 1] |
| Fire Weapon | **Right Mouse Button (RMB)**[cite: 1] |
| Control Crosshair | **Mouse Movement**[cite: 1] |
| Open / Close Inventory Panel | **TAB**[cite: 1] |
| Equip Highlighted Asset | **R**[cite: 1] |
| Activate / Use Highlighted Asset | **F**[cite: 1] |
| Discard Highlighted Asset | **K**[cite: 1] |
| Combine Highlighted Assets | **T**[cite: 1] |

### UI & Dialogue Panel Navigation
| UI Action | Key / Input |
| :--- | :--- |
| Move Selection Pointer Up | **W**[cite: 1] |
| Move Selection Pointer Down | **S**[cite: 1] |
| Move Selection Pointer Left | **A**[cite: 1] |
| Move Selection Pointer Right | **D**[cite: 1] |
| Navigate Menus | **Mouse Cursor**[cite: 1] |
| Confirm Menu Selection / Dialogue Choice | **Left Mouse Button (LMB)**[cite: 1] |

---

## 🚀 Getting Started & Launch Instructions

### Prerequisites
Make sure you have a compatible Java Runtime Environment (JRE/JDK) installed on your machine[cite: 2].

### Launching the Application
The entire engine and game assets are bundled into a single standalone executable file named `Gaiden.jar`[cite: 1].

* **Method 1 (Desktop Direct Execution):** You can launch the game directly by double-clicking the `Gaiden.jar` executable file[cite: 1]. If the engine fails to load properly, attempt running the application with administrative privileges[cite: 1].
* **Method 2 (Terminal CLI Execution):** Clone or download the project files, navigate to the compiled output artifact folder (`sem/Gaiden/out/artifacts/`), and execute the following command[cite: 2]:
  ```bash
  java -jar Gaiden.jar
