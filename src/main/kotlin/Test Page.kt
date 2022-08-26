//open class Wood(val age: Int) {
//    fun getWoodInfo(): String {
//        return "age $age"
//    }
//}
//
//class Pine(val isSpiny: Boolean, age: Int) : Wood(age) {
//    fun getPineInfo(): String {
//        return getWoodInfo() + ", spiny $isSpiny"
//    }
//}
//
//fun main() {
//    val pine = Pine(true, 3)
//    println(pine.getPineInfo() + ", " + pine.getWoodInfo())
//}