# Advanced Pivot Control Mod

The Advanced Pivot Control Mod is a client side Minecraft mod that allows you to adjust your pitch and yaw with greater
precision than the default game settings allow for.

## Prerequisites

To use the Advanced Pivot Control Mod, you will need the following:

- Fabric API
- Cloth Config
- ModMenu (optional)

## Installation

1. Download and install Fabric Loader following the instructions on the [Fabric website](https://fabricmc.net/use/).
2. Download the Advanced Angle Assist Mod JAR file from
   the [releases page](https://modrinth.com/mod/advanced-pivot-control/versions).
3. Move the JAR file to the `mods` folder in your Minecraft installation directory.
4. Launch Minecraft with the Fabric profile.

## Usage

The arrow keys are used to adjust pitch and yaw, with the default values set to 45 degrees for yaw and 30 degrees for
pitch.

### Commands

The following commands can be used to configure the mod:

- `/pitch` and `/apc pitch [int]` and : Sets the pitch to the specified value.
- `/yaw` and `/apc yaw [int]`: Sets the yaw to the specified value.
- `/angle` and `/apc angle [int] [int] `: Sets both the pitch and yaw to the specified values.
- `/lockpitch` and `/apc config lockpitch [bool]`: Locks the pitch to the current value.
- `/lockyaw` and `/apc config lockyaw [bool]`: Locks the yaw to the current value.
- `/apc config setPitchSteps [int]`: Sets the number of pitch steps.
- `/apc config setYawSteps [int]`: Sets the number of yaw steps.
- `/apc config doCommandFeedback [bool]`: Enables or disables command feedback.

### Source Code

The source code can be found on [GitHub](https://github.com/WinterWolfSV/Advanced-Pivot-Control) under the CC-BY-NC 4.0
License. More information about the license can be found under the [License](#license) section.

## License

The Advanced Pivot Control Mod is released under the CC-BY-NC-4.0 License. This license allows you to use, modify, and
distribute the mod for non-commercial purposes, as long as you credit the original author and include a copy of the
license in any redistributed versions.

## Bugs and Feedback

Bug reports can be submitted either through the
[GitHub Issues](https://github.com/WinterWolfSV/Advanced-Pivot-Control/issues)
page of the Advanced Pivot Control Mod repository or by contacting me personally over Discord (winterwolfsv).
Please
provide as much information as possible, including steps to reproduce the issue, error messages, and any relevant
screenshots or log files. Your feedback is greatly appreciated and will help improve the mod for all users.
