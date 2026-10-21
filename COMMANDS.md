# FlyCommand Commands

| Command | Permission | Description |
|---------|------------|-------------|
| `/fly` | `FlyCommand.fly` | Toggles flight mode on or off for the executing player. Player-only; the console is rejected with an alert. |

## `/fly` messages

| Situation | Message | Effect |
|-----------|---------|--------|
| Player with `FlyCommand.fly`, flight was off | `Flight enabled.` | Flight is allowed. |
| Player with `FlyCommand.fly`, flight was on | `Flight disabled.` | Flight is disallowed. |
| Player without `FlyCommand.fly` | `Alert: Permission 'FlyCommand.fly' required.` | Flight is unchanged. |
| Server console | `Alert: Can't be used by console.` | Nothing else happens. |
