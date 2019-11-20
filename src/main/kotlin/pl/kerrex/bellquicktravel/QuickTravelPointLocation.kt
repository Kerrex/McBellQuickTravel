package pl.kerrex.bellquicktravel

data class QuickTravelPointLocation(val x: Int, val y: Int, val z: Int, val name: String, val discoveredBy: String) {
    fun toMap(): Map<String, Any> {
        return mapOf("x" to x, "y" to y, "z" to z, "name" to name, "discoveredBy" to discoveredBy)
    }
}