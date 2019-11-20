package pl.kerrex.bellquicktravel.command

import org.bukkit.ChatColor
import org.bukkit.World
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.kerrex.bellquicktravel.KnownQuickTravelPointsStore
import pl.kerrex.bellquicktravel.PendingApprovalStore
import java.util.logging.Logger

class BellQuickTravelRegisterCommand(private val logger: Logger,
                                     private val knownQuickTravelPointsStore: KnownQuickTravelPointsStore,
                                     private val pendingApprovalStore: PendingApprovalStore
) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return true
        }

        if (sender.world.name != "world") {
            return false
        }

        val playerName = sender.name
        val pendingApproval = pendingApprovalStore.getPendingApprovalForPlayer(sender.name)
        if (pendingApproval == null) {
            sender.sendMessage("${ChatColor.RED}You have no pending quick travel approvals!")
            return true
        }
        if (args.isEmpty()) {
            return false
        }

        val quickTravelPointName = args.joinToString("_").replace(Regex("\\s+"), "_")
        knownQuickTravelPointsStore.addQuickTravelPoint(pendingApproval.x, pendingApproval.y, pendingApproval.z, quickTravelPointName, playerName)
        pendingApprovalStore.removePendingApproval(playerName)
        logger.info("Successfully added quick travel point $quickTravelPointName by $playerName")
        showMessageToAllPlayersInWorld(sender.world, quickTravelPointName)
        return false
    }

    private fun showMessageToAllPlayersInWorld(world: World, quickTravelPointName: String) {
        world.players.forEach {
            it.sendTitle("Odnaleziono nowe miasto:", quickTravelPointName, 10, 100, 20)
        }
    }
}