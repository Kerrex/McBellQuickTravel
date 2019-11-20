package pl.kerrex.bellquicktravel

import org.bukkit.plugin.java.JavaPlugin
import pl.kerrex.bellquicktravel.command.BellQuickTravelCommand
import pl.kerrex.bellquicktravel.command.BellQuickTravelRegisterCommand
import pl.kerrex.bellquicktravel.command.BellQuickTravelTabCompleter

class BellQuickTravelPlugin : JavaPlugin() {

    override fun onEnable() {
        saveDefaultConfig()

        logger.info("Bell Quick Travel plugin is enabled!")

        val knownQuickTravelPointsStore = KnownQuickTravelPointsStore(config)
        val pendingApprovalStore = PendingApprovalStore()

        getCommand("qt-register")?.setExecutor(BellQuickTravelRegisterCommand(logger, knownQuickTravelPointsStore, pendingApprovalStore))
        getCommand("qt")?.setExecutor(BellQuickTravelCommand(logger, knownQuickTravelPointsStore))
        getCommand("qt")?.tabCompleter = BellQuickTravelTabCompleter(knownQuickTravelPointsStore)


        server.scheduler.scheduleSyncRepeatingTask(this, {
            knownQuickTravelPointsStore.saveToConfig()
            saveConfig()
        }, 100, 1200)
    }
}