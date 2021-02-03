# Snowflaker


## Introduction

Snowflaker is a kotlin project. It is used to generate primary key id with Snowflake algorithm.

The generated id is of type 'Long' (64 bits). It contains four parts:

- Part 1 (1 bit):   this bit should always be 0 to ensure that the id is a positive number.
- Part 2 (41 bits): the current system time in milliseconds.
- Part 3 (10 bits): the device id.
- Part 3 (12 bits): the sequence number in the same millisecond.

## How to?

You can find the test code in [SnowflakerTest.kt][1]

```kt
for (i in 1..5000 ) {
    val tmp = Snowflaker.genId()
    println("genKid: $tmp")
}
```


[1]:	https://github.com/kmvdata/snowflaker/blob/master/common/src/test/java/com/kmvdata/kotlin/SnowflakerTest.kt "[SnowflakerTest.kt]"