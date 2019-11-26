# McBellQuickTravel
McBellQuickTravel is simple Minecraft plugin which adds QuickTravel to your Minecraft server.

## How does it work?
When player finds a bell in a world (which is usually spotted in villages), he can right click it to enable "register mode". When "register mode" is on, the player can type `/qt-register <new_name_of_point>` to register new Quick Travel Point by this name for all of the players.

Any player can type `/qt <name_of_the_point>` to quickly teleport to previously found quick travel point.

Also `/qt-list` command is available to list all available quick travel points.

## Installation
Just copy it to you server `plugin` folder.

No configuration is required unless you want to change the message which is send after finding new point. If that's the case, change `foundNewQuickTravelPointMessage` property in `config.yml` file (server needs to be run at least once with this plugin in order to `config.yml` appear).
