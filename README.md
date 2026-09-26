# FlyCommand

## Description

FlyCommand is a Spigot plugin that lets permitted players toggle flight mode on and off with a single command. It adds one command, `/fly`, and nothing else — there is no configuration file and no persistent data.

## Installation

### First Time Installation

1. Download the latest FlyCommand jar from the [releases page](https://github.com/Dans-Plugins/FlyCommand/releases).
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

`mvn clean package` compiles the plugin and runs its test suite. The suite checks the `plugin.yml` packed into the jar: that `main` names the plugin class, that the build filled in the version, and that the `Fly` command and the `FlyCommand.fly` permission are declared. It also runs the `/fly` handler against stand-in senders, checking the toggle and its confirmations (including when the command is typed as `/flycommand:fly` or under an alias), the permission-denied alert, and the console alert. The [Build](.github/workflows/build.yml) workflow runs the same command on every push and pull request.

The tests do not start a server, so the plugin loading and `/fly` being registered are still verified by hand on a Spigot or Paper server, as described under [Manual Validation](#manual-validation-on-a-test-server).

## Development

### Project Layout

The plugin is a single class, built with Maven.

- `src/main/java/me/Daniel/FlyCommand/Main.java` – the whole plugin
- `src/main/resources/plugin.yml` – the plugin manifest, whose `main` value must stay equal to the package and class name of that file; its `version` is filled in from `pom.xml` at build time
- `src/test/java/` – tests of the packaged manifest and of the `/fly` handler

### Building

```bash
mvn clean package
```

The jar is written to `target/FlyCommand-<version>.jar`. It is compiled for Java 8 (`maven.compiler.release` in `pom.xml`) so that it loads on the oldest servers its `api-version: 1.13` admits.

### Releases

Every push to `main` that changes more than documentation republishes the rolling `dev` prerelease ([Dev Release](.github/workflows/dev-release.yml)). Stable releases are published from a verified `dev` build; a release published without a jar has one built and attached by [Release](.github/workflows/release.yml).

### Manual Validation on a Test Server

1. Build the jar as above.
2. Copy it into a Spigot or Paper server's `plugins` folder and start the server.
3. Confirm the plugin loads and `/fly` is registered.
4. Join as an operator and run `/fly` twice, confirming that flight toggles each time and that the chat confirmation reads `Flight enabled.` when flight is turned on and `Flight disabled.` when it is turned off.
5. Run `/flycommand:fly`, confirming that it toggles flight exactly as `/fly` does.
6. Join with an account that is neither an operator nor a holder of `FlyCommand.fly` and run `/fly`, confirming that `Alert: Permission 'FlyCommand.fly' required.` is shown and flight is unchanged.
7. Run `/fly` from the server console, confirming that `Alert: Can't be used by console.` is shown and that no error is logged.

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

The plugin has no stable release yet; its version is set in `pom.xml` and changes are recorded in [CHANGELOG.md](CHANGELOG.md). Open work is tracked on the [issues page](https://github.com/Dans-Plugins/FlyCommand/issues).
