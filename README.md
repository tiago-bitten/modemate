# Minecraft Plugin: modemate 1.20.1
![Java](https://img.shields.io/badge/Java-orange?style=flat-square)
![Spigot](https://img.shields.io/badge/Spigot-yellow?style=flat-square)
![Maven](https://img.shields.io/badge/Maven-red?style=flat-square)

# About the plugin
modemate is a versatile Minecraft plugin developed in Java for the Spigot server platform (compatible with Minecraft version 1.20.1). This plugin enhances your Minecraft server by introducing a range of powerful and fun commands and crafting recipes.

## Features
### Custom commands
```sh
/mplugin <args>
/mweather <args>
/mhunger <args>
/mgmd <args>
/mban <args>
/mlife <args>
/mfly
```
### Exciting New Items and Crafts
CustomCraft introduces a variety of new items and crafting recipes to spice up your Minecraft world. Some examples of these items include:

- Snow Grenade: Craft and throw snow grenades to create a winter wonderland effect wherever they explode.

- C4 Explosives: Craft C4 explosives for controlled explosions, perfect for mining or creating excitement in-game.

- Mine Land: Create a landmine with this recipe, adding an element of danger to your server's terrain.

## Installation

To install the modemate plugin on your Minecraft server, follow these steps:

1. **Clone the Repository:**

      Open your terminal and navigate to the directory where you want to store the plugin. Then, run the following command to clone the Git repository:

      ```bash
      git clone https://github.com/tiago-bitten/modemate.git
      ```

2. **Navigate to the Plugin Directory:**

      Change your current directory to the newly cloned repository:
      ```bash
      cd modemate
      ```
3. **Build Artifacts:**

      Build the plugin artifacts using Maven.
      ```sh
      mvn clean install
      ```
4. **Copy the JAR File:**

      Locate the JAR file generated in the previous step (usually in the target or build directory) and copy it.

5. **Place the JAR File in the Plugins Directory:**

      Navigate to your Minecraft server's plugins directory and paste the JAR file there.

6. **Restart Your Minecraft Server:**

      Restart your Minecraft server to load the Modemate plugin:
      ```bash
      /rl
      ```
7. **Contribute:**

      Feel free to make changes and improvements to the plugin! If you'd like to contribute your changes, simply fork the repository, make your updates, and send a pull request. Your contributions are welcome!

## Usage
After installation, modemate commands and crafting recipes will be available for use in your Minecraft server world. Players can access these features based on their permissions, roles, or server settings.

## Author

<a href="https://github.com/tiago-bitten">
  <img src="https://avatars.githubusercontent.com/tiago-bitten" width="50" height="50">  
</a>

[tiago-bitten](https://github.com/tiago-bitten)

## License

This project is licensed under the [![License: MIT](https://img.shields.io/badge/MIT_License-brightgreen?style=flat-square)](https://github.com/tiagobitten/modemate/blob/main/LICENSE)
