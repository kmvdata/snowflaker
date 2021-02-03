package main.java.com.kmvdata.kotlin

private const val SNOWFLAKER_TAKE_BITS_12 = 0xfffL //  Take 12 bits with '1' in binary.
private const val SNOWFLAKER_TAKE_BITS_10 = 0x3ffL //  Take 10 bits with '1' in binary.
private const val SNOWFLAKER_TAKE_BITS_41 = 0x1ffffffffffL // Take 41 bits with '1' in binary.

class Snowflaker(private var deviceId: Long) {

    private var sequenceNumber: Long = 0L
    private var lastTimeMills: Long = 0L

    init {
        this.lastTimeMills = System.currentTimeMillis()
    }

    fun genId(): Long {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis == lastTimeMills) {
            sequenceNumber = sequenceNumber + 1
        } else {
            lastTimeMills = currentTimeMillis
            sequenceNumber = 0
        }

        val timeMillsBits: Long = currentTimeMillis and SNOWFLAKER_TAKE_BITS_41 shl 22
        val deviceIdBits: Long = deviceId and SNOWFLAKER_TAKE_BITS_10 shl 12
        val sequenceNumberBits: Long = sequenceNumber and SNOWFLAKER_TAKE_BITS_12
        return timeMillsBits or deviceIdBits or sequenceNumberBits
    }

    companion object {
        private var INSTANCE: Snowflaker? = null

        fun defaultFlaker(): Snowflaker {
            return Snowflaker.INSTANCE ?: synchronized(this) {
                val instance = Snowflaker(0L)

                Snowflaker.INSTANCE = instance
                // return instance
                instance
            }
        }

        fun genId(): Long {
            return Snowflaker.defaultFlaker().genId()
        }

        fun prettyBinary(number: Long): String {
            var stringNumber = number.toString(2)
            if (number > 0) {
                stringNumber = "+" + stringNumber
            }
            val needZero = 65 - stringNumber.length
            if (needZero > 0) {
                stringNumber = stringNumber[0] + "0".repeat(needZero) + stringNumber.substring(1)
            }
            return stringNumber
        }
    }
}