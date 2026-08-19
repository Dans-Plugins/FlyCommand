# FlyCommand

## Description

FlyCommand is a Spigot plugin that lets permitted players toggle flight mode on and off with a single command. It adds one command, `/fly`, and nothing else — there is no configuration file and no persistent data.

## Installation

### First Time Installation

1. Download the latest `FlyCommand.jar` from the [releases page](https://github.com/Dans-Plugins/FlyCommand/releases).
2. Place the jar in the `plugins` folder of your server.
3. Restart your server.

The plugin targets Spigot / Paper with `api-version` 1.13.

## Usage

Type `/fly` to toggle your flight on or off. The plugin confirms the new state in chat.

### Documentation

- [User Guide](USER_GUIDE.md) – Installation, usage and permissions
- [Commands Reference](COMMANDS.md) – Every command the plugin handles
- [Configuration Guide](CONFIG.md) – Configuration options
- [Changelog](CHANGELOG.md) – Notable changes by release

## Support

You can find the Dans Plugins Community Discord server [here](https://discord.gg/xXtuAQ2).

### Experiencing a bug?

Please open a bug report [here](https://github.com/Dans-Plugins/FlyCommand/issues/new).

- [Known Bugs](https://github.com/Dans-Plugins/FlyCommand/issues?q=is%3Aissue+is%3Aopen+label%3Abug)

## Contributing

- [CONTRIBUTING.md](CONTRIBUTING.md)

## Testing

There is no test suite in this repository. No test framework is present, and no build system exists to host one.

The [Build](.github/workflows/build.yml) workflow checks the repository out and does nothing further, so a green check on a pull request confirms only that the repository could be cloned. It compiles nothing and runs nothing.

Every change is therefore verified by hand on a Spigot or Paper server, as described under [Development](#development). Adding a build system and an automated test suite is tracked in [issue #9](https://github.com/Dans-Plugins/FlyCommand/issues/9).

## Development

### Project Layout

The plugin is a single class. The source tree is flat — the package path sits directly at the repository root rather than under `src/main/java`.

- `me/Daniel/FlyCommand/Main.java` – the whole plugin
- `plugin.yml` – the plugin manifest, whose `main` value must stay equal to the package and class name of that file

### Building

No build tool is configured, so the jar is produced by hand against a Spigot API jar:

```bash
javac -cp spigot-api.jar -d out me/Daniel/FlyCommand/Main.java
cp plugin.yml out/
jar cvf FlyCommand.jar -C out .
```

`plugin.yml` must end up at the root of the jar, which is what the `cp` step above arranges.

Compile at a release level your server's Java runtime can load — for example `--release 8` for a Minecraft 1.13 era Spigot server. A jar compiled by a newer JDK at its default release level will fail to load on an older runtime.

### Manual Validation on a Test Server

1. Build `FlyCommand.jar` as above.
2. Copy it into a Spigot or Paper server's `plugins` folder and start the server.
3. Confirm the plugin loads and `/fly` is registered.
4. Join as an operator and run `/fly` twice, confirming that flight is enabled and then disabled and that the chat confirmation matches the new state each time.
5. Join with an account that is neither an operator nor a holder of `FlyCommand.fly` and run `/fly`, confirming the permission alert.
6. Run `/fly` from the server console, confirming that the console alert is shown and that no error is logged.

## Authors and Acknowledgement

### Developers

| Name | Main Contributions |
|------|--------------------|
| Daniel Stephenson | Creator and primary developer |

## License

This project is licensed under the [GNU General Public License v3.0](LICENSE) (GPL-3.0).

You are free to use, modify, and distribute this software, provided that:

- Source code is made available under the same license when distributed.
- Changes are documented and attributed.
- No additional restrictions are applied.

See the [LICENSE](LICENSE) file for the full text of the GPL-3.0 license.

## Project Status

The plugin is at version 1.0, as recorded in [CHANGELOG.md](CHANGELOG.md) and in `plugin.yml`. Open work is tracked on the [issues page](https://github.com/Dans-Plugins/FlyCommand/issues).
