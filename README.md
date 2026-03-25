# LAN Multiplayer Sandbox Game

## Project Overview
A multiplayer 2D sandbox game featuring a client-server architecture, designed to operate over a Local Area Network (LAN). This project was developed as a practical implementation of network programming, real-time game state synchronization, and procedural generation. It serves as a fully functional prototype demonstrating core survival and sandbox mechanics.

## Tech Stack & Architecture
* Language: Java
* Framework: LibGDX
* Build System: Gradle
* Architecture: Authoritative Server (Client-Server model)
* Networking: TCP (reliable events) and UDP (fast position synchronization)

## Core Systems Implemented

* Network & Session Management:
Asynchronous client-server communication over TCP/UDP. The server manages player sessions and retains individual progress (inventory, position) based on a unique nickname. This allows players to disconnect and safely rejoin the ongoing session.

* Voxel Engine & World Generation:
Features real-time terrain modification. Players can destroy and place blocks dynamically. The game world is procedurally generated at the server's startup with a fixed, limited size.

* Entity & Combat System:
Includes both PvE (basic Zombie enemies) and PvP mechanics. The combat system differentiates between melee weapons (close-range collision detection) and ranged weapons (projectile spawning).

* Gameplay Mechanics:
Custom grid-based inventory and a proof-of-concept crafting system. Players can gather resources and combine them to create items and weaponry.

## Controls
* A / D: Move Left / Right
* Space: Jump
* E: Open Inventory / Crafting Menu
* Left Mouse Button (Hold): Destroy blocks
* Left Mouse Button (Click): Melee attack
* Right Mouse Button (Click): Place blocks / Shoot ranged weapon

## How to Run
The project uses Gradle. To test the environment, run the following commands in your terminal from the root directory:

1. Start the Server:
```
./gradlew core:runServer
```
2. Start the Client (Player):
```
./gradlew lwjgl3:run
```
## Network Configuration
The server listens on the following ports. If testing across multiple machines on a LAN, these ports must be allowed through the host's firewall:
* UDP: 54777
* TCP: 54555

Example UFW configuration (Linux) for a 192.168.1.0/24 subnet:
```
sudo ufw allow from 192.168.1.0/24 to any port 54777 proto udp
sudo ufw allow from 192.168.1.0/24 to any port 54555 proto tcp
```
## Technical Limitations & Retrospective
As a completed prototype, this project was designed with specific scope limitations:
* No Data Serialization: The game state (world modifications, player inventories) is stored entirely in RAM. There is no database or local file saving implemented, meaning all progress is permanently lost when the server process is terminated.
* Proof-of-Concept Crafting: The crafting system is structurally functional but contains only a few basic recipes. The rest of the crafting tree was not implemented.


---

### About the Project

This project was developed as part of the **Software Engineering** course within the **Theoretical Computer Science** program at the **Jagiellonian University (UJ)**.

## Authors

* **Karol Bielaszka**
* **Michał Mańka**
* **Kacper Paciorek**
* **Oleksandr Tymkovych**
