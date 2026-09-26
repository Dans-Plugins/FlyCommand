# FlyCommand User Guide

## What is FlyCommand?

FlyCommand is a Spigot plugin that lets permitted players toggle flight mode on and off with a single command.

## Installation

1. Download the latest FlyCommand jar from the [Releases](https://github.com/Dans-Plugins/FlyCommand/releases) page.
2. Place the JAR in your server's `plugins/` folder.
3. Restart the server.

## Usage

Type `/fly` to toggle your flight on or off. The plugin will confirm the new state in chat with `Flight enabled.` or `Flight disabled.`

`/fly` can only be used by a player:

- Running it from the server console shows `Alert: Can't be used by console.` and does nothing else.
- Running it as a player without the `FlyCommand.fly` permission shows `Alert: Permission 'FlyCommand.fly' required.` and leaves flight unchanged.

## Permissions

| Permission | Default | Description |
|------------|---------|-------------|
| `FlyCommand.fly` | `op` | Allows the player to toggle flight. |

## Support

Open a [GitHub issue](https://github.com/Dans-Plugins/FlyCommand/issues) to report bugs or request features.
