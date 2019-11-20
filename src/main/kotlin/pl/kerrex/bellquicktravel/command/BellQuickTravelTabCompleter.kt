package pl.kerrex.bellquicktravel.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import pl.kerrex.bellquicktravel.KnownQuickTravelPointsStore

class BellQuickTravelTabCompleter(private val knownQuickTravelPointsStore: KnownQuickTravelPointsStore) : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        val knownLocationNames = knownQuickTravelPointsStore.getAllKnownQuickTravelPoints().map { it.name }
        return knownLocationNames.toMutableList()
    }
}