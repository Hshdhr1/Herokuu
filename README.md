# Heroku Userbot for Minecraft (Fabric)

Advanced Minecraft mod that integrates Telegram userbot functionality, allowing you to control your account and bridge chats directly from Minecraft.

## Features
- **Telegram Integration**: Full MTProto client running inside Minecraft (via TDLib).
- **Minecraft Commands**: Control the userbot with `/heroku` command.
- **Dual Authentication**: Support for Web-based (localhost) and QR-code login.
- **Chat Bridge**: Bidirectional synchronization between Minecraft and Telegram.
- **Modular Commands**: Powerful command system (e.g., `.help`, `.info`, `.eval`).
- **Persistent Storage**: Secure JSON-based database in a dedicated `heroku/` folder.

## Installation
1. Install **Fabric Loader** for Minecraft **1.21.1** (or compatible).
2. Download the latest mod JAR.
3. Place it in your `.minecraft/mods` folder.
4. Run Minecraft and use `/heroku` to begin setup.

## Commands
- `/heroku`: Start userbot with web-based authentication (localhost:8080).
- `/heroku no-web`: Start userbot with QR-code authentication in console.
- `/heroku stop`: Stop the userbot and web server.

## Credits
- Based on the original **Heroku Userbot** for Telegram.
- Powered by **Fabric API**, **TDLib**, **NanoHTTPD**, and **ZXing**.
