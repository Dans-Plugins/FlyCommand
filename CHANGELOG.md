# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## [Unreleased]

### Added
- A Maven build (`pom.xml`, Java 8 bytecode) with the source moved to `src/main/java` and `plugin.yml` to `src/main/resources`, where its version is filled in from the build; tests of the packaged `plugin.yml`; a `Build` workflow that compiles and tests, a `Dev Release` workflow publishing a rolling `dev` prerelease, and a `Release` workflow that attaches the jar to a release published without one (#9).
- `README.md` describing the plugin, its installation, its documentation, and how it is built and validated by hand.
- `description` and `usage` metadata for the `Fly` command in `plugin.yml`.
- A `permissions` block in `plugin.yml` declaring `FlyCommand.fly` with default `op`, matching the permission table in `USER_GUIDE.md`.
- `USER_GUIDE.md`, `COMMANDS.md`, and the README's manual-validation steps now state the exact console and permission-denied alerts that `/fly` shows, alongside the two confirmation messages.
- A `.gitignore` covering the `out/` directory, `.jar`, and `.class` files produced by the manual build described in `README.md`.

### Changed
- The `/fly` confirmation message now reads `Flight enabled.` or `Flight disabled.` instead of `Flight toggled to true` or `Flight toggled to false`.

### Fixed
- `/fly` now checks the command's registered name rather than the label that was typed, so `/flycommand:fly` and aliases set up in the server's `commands.yml` toggle flight instead of printing the usage string (#13).
- The permission-denied branch of `/fly` now returns `true`, so the plugin's own alert is the only message a player without `FlyCommand.fly` is shown.
- `onCommand` now carries the `@Override` annotation, matching `onEnable` and `onDisable`.

## [1.0]

### Added
- `/fly` command to toggle flight mode for players with the `FlyCommand.fly` permission.
