package pl.kerrex.bellquicktravel

import org.bukkit.block.Block
import org.bukkit.entity.Player
import java.util.concurrent.ConcurrentHashMap

class PendingApprovalStore {
    private val pendingApprovalMap = ConcurrentHashMap<String, Block>()

    fun addPendingApproval(player: Player, block: Block) {
        pendingApprovalMap[player.name] = block
    }

    fun removePendingApproval(playerName: String) {
        pendingApprovalMap.remove(playerName)
    }

    fun getPendingApprovalForPlayer(playerName: String): Block? {
        return pendingApprovalMap[playerName]
    }
}