# FlyCommand Commands

| Command | Permission | Description |
|---------|------------|-------------|
| `/fly` | `FlyCommand.fly` | Toggles flight mode on or off for the executing player. Player-only; the console and any other non-player sender, such as a command block, are rejected with an alert. Also available as `/flycommand:fly`, which reaches FlyCommand even if another plugin provides `/fly`. |

## `/fly` messages

| Situation | Message | Effect |
|-----------|---------|--------|
| Player with `FlyCommand.fly`, flight was off | `Flight enabled.` | Flight is allowed. |
| Player with `FlyCommand.fly`, flight was on | `Flight disabled.` | Flight is disallowed. |
| Player without `FlyCommand.fly` | `Alert: Permission 'FlyCommand.fly' required.` | Flight is unchanged. |
| Server console, or any other non-player sender such as a command block | `Alert: Can't be used by console.` | Nothing else happens. |
