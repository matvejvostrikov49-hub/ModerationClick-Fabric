# ModerationClick-Fabric

Client-side Fabric moderation menu for Minecraft Java Edition **1.21.4** and **1.21.11**.

## Features
- Chat nickname moderation menu.
- Rules 2.2–2.20.
- Mute durations: 30m, 60m, 180m, 6h, 12h.
- Sends `/work` when the menu opens and closes.
- Confirm sends `/tempmute <nickname> <duration> <rule>`.

## Install
1. Install Fabric Loader for the exact Minecraft version.
2. Install Fabric API for that same version.
3. Download the matching `.jar` from the latest successful GitHub Actions run.
4. Put the JAR in the game's `mods` folder.

## Build locally
Requires Java 21 and Gradle 8.14.3 or newer.

The default properties build 1.21.4. To build 1.21.11:

```sh
gradle --no-daemon build \
  -Pminecraft_version=1.21.11 \
  -Pyarn_mappings=1.21.11+build.3 \
  -Ploader_version=0.18.1 \
  -Pfabric_version=0.141.6+1.21.11
```

GitHub Actions builds both targets independently and publishes two artifacts:
- `ModerationClick-Fabric-1.21.4`
- `ModerationClick-Fabric-1.21.11`

## Chat click note
The current hook opens the menu when the clicked chat style exposes a nickname through insertion metadata or a compatible click event. Some server chat formats do not expose this metadata; those formats need a format-specific parser and must be tested in-game before relying on nickname clicks.
