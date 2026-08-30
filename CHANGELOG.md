# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## [Unreleased]

### Added
- `README.md` describing the plugin, its installation, its documentation, and how it is built and validated by hand.
- `description` and `usage` metadata for the `Fly` command in `plugin.yml`.
- A `permissions` block in `plugin.yml` declaring `FlyCommand.fly` with default `op`, matching the permission table in `USER_GUIDE.md`.

### Changed
- The `/fly` confirmation message now reads `Flight enabled.` or `Flight disabled.` instead of `Flight toggled to true` or `Flight toggled to false`.

### Fixed
- The permission-denied branch of `/fly` now returns `true`, so the plugin's own alert is the only message a player without `FlyCommand.fly` is shown.
- `onCommand` now carries the `@Override` annotation, matching `onEnable` and `onDisable`.

## [1.0]

### Added
- `/fly` command to toggle flight mode for players with the `FlyCommand.fly` permission.
