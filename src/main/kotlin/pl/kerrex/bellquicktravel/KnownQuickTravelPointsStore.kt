package pl.kerrex.bellquicktravel

import org.bukkit.configuration.file.FileConfiguration
import java.util.concurrent.CopyOnWriteArraySet

class KnownQuickTravelPointsStore(private val config: FileConfiguration) {

    private lateinit var quickTravelPoints: CopyOnWriteArraySet<QuickTravelPointLocation>

    init {
        loadFromConfig()
    }

    private fun loadFromConfig() {
        quickTravelPoints = CopyOnWriteArraySet()

        val knownLocations = config.getMapList(LOCATION_CONFIG_PATH)

        knownLocations.forEach {
            val x = it["x"].toString().toInt()
            val y = it["y"].toString().toInt()
            val z = it["z"].toString().toInt()
            val name = it["name"].toString()
            val discoveredBy = it["discoveredBy"].toString()
            quickTravelPoints.add(QuickTravelPointLocation(x, y, z, name, discoveredBy))
        }
    }

    fun addQuickTravelPoint(x: Int, y: Int, z: Int, name: String, discoveredBy: String) {
        quickTravelPoints.add(QuickTravelPointLocation(x, y, z, name, discoveredBy))
    }

    fun removeQuickTravelPoint(x: Int, y: Int, z: Int) {
        quickTravelPoints.removeIf {
            it.x == x && it.y == y && it.z == z
        }
    }

    fun isKnownQuickTravelPoint(x: Int, y: Int, z: Int): Boolean {
        return quickTravelPoints.any { it.x == x && it.y == y && it.z == z }
    }

    fun getQuickTravelPointByName(name: String): QuickTravelPointLocation? {
        val preparedName = name.replace(Regex("\\s+"), "_").toUpperCase()
        return quickTravelPoints.find { it.name.toUpperCase() == preparedName }
    }

    fun getAllKnownQuickTravelPoints(): Set<QuickTravelPointLocation> {
        return quickTravelPoints.toSet()
    }

    fun saveToConfig() {
        val serialized = quickTravelPoints.map { it.toMap() }.toList()
        config.set(LOCATION_CONFIG_PATH, serialized)
    }

    companion object {
        const val LOCATION_CONFIG_PATH = "locations"
    }
}
