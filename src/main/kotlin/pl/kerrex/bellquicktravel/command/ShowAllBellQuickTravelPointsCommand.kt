package pl.kerrex.bellquicktravel.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import pl.kerrex.bellquicktravel.KnownQuickTravelPointsStore

class ShowAllBellQuickTravelPointsCommand(private val knownQuickTravelPointsStore: KnownQuickTravelPointsStore) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val knownPointNames = knownQuickTravelPointsStore.getAllKnownQuickTravelPoints().map { it.name }
        knownPointNames.forEach {sender.sendMessage(it)}

        return true
    }
}