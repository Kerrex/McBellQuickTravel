package pl.kerrex.bellquicktravel.listener

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import pl.kerrex.bellquicktravel.KnownQuickTravelPointsStore
import pl.kerrex.bellquicktravel.PendingApprovalStore
import java.util.logging.Logger

class BellQuickTravelEventListener(private val logger: Logger,
                                   private val knownQuickTravelPointsStore: KnownQuickTravelPointsStore,
                                   private val pendingApprovalStore: PendingApprovalStore) : Listener {

    @EventHandler
    fun playerRightClickedBellHandler(playerInteractEvent: PlayerInteractEvent) {
        if (playerInteractEvent.action != Action.RIGHT_CLICK_BLOCK) {
            return
        }

        val clickedBlock = playerInteractEvent.clickedBlock
        clickedBlock?.let {
            if (!isBell(clickedBlock) || isKnownQuickTravelPoint(clickedBlock)) {
                return
            }
            val player = playerInteractEvent.player
            pendingApprovalStore.addPendingApproval(player, clickedBlock)
            logger.info("Quick travel point x:${clickedBlock.x}, y:${clickedBlock.y}, z:${clickedBlock.z} is pending approval from player ${player.name}")
            player.sendMessage("${ChatColor.RED}You have found new Quick Travel Point! Type /qt-register <name> to register this point globally!")
        }
    }

    @EventHandler
    fun bellDestroyed(blockDestroyedEvent: BlockBreakEvent) {
        val block = blockDestroyedEvent.block
        if (!isBell(block)) {
            return
        }

        if (knownQuickTravelPointsStore.isKnownQuickTravelPoint(block.x, block.y, block.z)) {
            logger.info("Quick travel point x:${block.x}, y:${block.y}, z:${block.z} has been destroyed by player ${blockDestroyedEvent.player.name}")
            knownQuickTravelPointsStore.removeQuickTravelPoint(block.x, block.y, block.z)
        }
    }

    private fun isKnownQuickTravelPoint(clickedBlock: Block) =
        knownQuickTravelPointsStore.isKnownQuickTravelPoint(clickedBlock.x, clickedBlock.y, clickedBlock.z)

    private fun isBell(clickedBlock: Block): Boolean {
        return clickedBlock.type == Material.BELL
    }
}