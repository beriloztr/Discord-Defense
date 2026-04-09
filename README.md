# Discord-Defense
Discord Defense is a music-themed defense game developed in Java. Players control a piano to defend against various "enemies" by managing energy and firing projectiles across multiple lanes.

Language: Java

Graphics: Java AWT (for Rectangle and BufferedImage)

Environment: Eclipse IDE

*Key Features*
- Enemy Varieties: Includes three distinct enemy types (Type A, B, and C) with unique speeds, health points (HP), and lane-changing behaviors (rates).
- Energy Management: Firing projectiles requires energy, forcing players to balance their attacks.
- Dynamic Collision System: Uses a Rectangle-based system to accurately detect intersections between bullets and enemies.
- Sprite Animation: Enemies feature multi-frame animations cropped from spritesheets for fluid movement.
  
*Technical Architecture*
- Abstraction: Core entities are derived from the Character abstract class, which defines shared attributes like coordinates and health. Enemy-specific logic is encapsulated in the Enemy abstract class.
- Interfaces: The CollisionChecker interface is implemented to decouple collision logic from the main character classes.
- Polymorphism: Different entities (Enemies, Bullets) override the update() method to implement their specific behaviors.
- Memory Management: Active game objects are stored in ArrayList collections; objects are dynamically removed when they are destroyed or move off-screen to optimize performance.

To Run:
Clone this repository to your local machine.
Import the project into Eclipse or your preferred Java IDE.
Run the Main class located in the GameSystem package.

[GUI&UML-DiscordDefense.pdf](https://github.com/user-attachments/files/26605162/GUI.UML-DiscordDefense.pdf)
