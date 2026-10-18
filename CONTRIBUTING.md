# Contributing to FlyCommand

Thank you for your interest in contributing!

## Workflow

1. Fork the repository.
2. Create a feature branch from `main`: `git checkout -b feature/my-change`
3. Make your changes.
4. Run `mvn clean package`, which compiles the plugin and runs its tests; the [Build](.github/workflows/build.yml) workflow runs the same command on your pull request. If you change how `/fly` behaves, also follow the README's [manual validation steps](README.md#manual-validation-on-a-test-server) on a test server.
5. Open a pull request against `main`.
6. Reference the related GitHub issue in your pull request description.

## Code Style

- Follow existing Java conventions in the codebase.
- Keep changes focused and minimal.

## Reporting Issues

Open a [GitHub issue](https://github.com/Dans-Plugins/FlyCommand/issues) with a clear description of the bug or feature request.
