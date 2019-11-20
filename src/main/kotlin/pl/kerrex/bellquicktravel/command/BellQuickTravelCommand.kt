package pl.kerrex.bellquicktravel.command

import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.kerrex.bellquicktravel.KnownQuickTravelPointsStore
import java.util.logging.Logger

class BellQuickTravelCommand(
    private val logger: Logger,
    private val knownQuickTravelPointsStore: KnownQuickTravelPointsStore
) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return false
        }

        if (sender.world.name != "world") {
            sender.sendMessage("${ChatColor.RED}You can teleport only in main world!")
            return true
        }

        if (args.isEmpty()) {
            return false
        }

        val quickTravelPointName = args[0]
        knownQuickTravelPointsStore.getQuickTravelPointByName(quickTravelPointName)?.let {
            sender.noDamageTicks = 10
            sender.teleport(Location(sender.world, it.x.toDouble(), it.y+1.toDouble(), it.z.toDouble()))
            logger.info("Teleporting player ${sender.name} to location $quickTravelPointName")
        } ?: run {
            sender.sendMessage("${ChatColor.RED}Unknown location!")
        }

        return true
    }
}