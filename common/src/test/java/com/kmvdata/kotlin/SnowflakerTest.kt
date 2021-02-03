package test.java.com.kmvdata.kotlin

import main.java.com.kmvdata.kotlin.Snowflaker

internal class SnowflakerTest {
    @org.junit.jupiter.api.Test
    fun genId() {
        for (i in 1..5000 ) {
            val tmp = Snowflaker.genId()
            println("genKid: $tmp")
        }
    }
}
