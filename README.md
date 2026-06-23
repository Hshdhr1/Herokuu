# Heroku Userbot for Minecraft (Fabric)

Advanced Minecraft mod that integrates Telegram userbot functionality, allowing you to control your account and bridge chats directly from Minecraft.

## Features
- **Telegram Integration**: Full MTProto client running inside Minecraft.
- **Chat Bridge**: Bidirectional synchronization between Minecraft and Telegram.
- **Modular Commands**: Powerful command system (e.g., `.help`, `.eval`).
- **Persistent Storage**: Secure JSON-based database for settings and data.

## Installation
1. Install **Fabric Loader** for Minecraft **1.21.11**.
2. Download the latest mod JAR.
3. Place it in your `.minecraft/mods` folder.
4. Run Minecraft and configure your `api_id` and `api_hash` in `config/heroku.json`.

## Usage
- Use `.help` in Telegram or Minecraft chat to see available commands.
- Commands start with the configurable prefix (default: `.`).

## Credits
- Based on the original **Heroku Userbot** for Telegram.
- Powered by **Fabric API** and **TDLib**.
