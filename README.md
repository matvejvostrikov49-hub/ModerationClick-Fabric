# ModerationClick-Fabric

A client-side Fabric mod for Minecraft 1.21.4.

## Features
- Moderation menu with rules 2.2–2.20.
- Durations: 30m, 60m, 180m, 6h, 12h.
- Sends `/work` when the menu opens and closes.
- Confirm sends `/tempmute <nickname> <duration> <rule>`.

## Build
Requires Java 21 and Gradle 8.12 or newer.

```sh
gradle build
```

The JAR is created in `build/libs/`. GitHub Actions also builds it on every push; download it from the latest successful workflow run under **Actions → Artifacts**.

## Nickname click integration
The chat hook currently opens the menu when the clicked chat style exposes a player nickname through its insertion metadata. Servers that do not attach insertion metadata to nicknames may need a server-format-specific chat-line parser; verify this against your server chat before using it for moderation.
