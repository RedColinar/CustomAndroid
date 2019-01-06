package com.pq.unit.test

import kotlin.test.Test

class Grammar {

    @Test
    fun main() {
        loop()
        //range()
        //Constructors().toString()
    }

    private fun loop() {
        val items = listOf("apple", "banana", "kiwifruit")
        for (item in items) {
            println(item)
        }
        /*  java 中的label
            OUT: for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    if (j == 3) break OUT;
                }el
            }
        */
        loopPoint@ for (i in 1..100) {
            for (j in 1..100) {
                print("i=$i,j=$j")
                if (j == 3) break@loopPoint
            }
        }
    }

    private fun range() {
        val x = 10
        val y = 9
        if (x in y+2..y+3) {
            println("fits in range")
        }

        for (s in 1..10 step 2) {
            print(s)
        }
    }

    class Constructors(i: Int = 1) {
        init {
            println("Init block")
        }

        init {
            println("Constructor")
        }
    }

    fun testDataClass() {
        val user1 = User(1, "hhh")
        val user2 = User(2, "zzz")
        val (id, name) = user1
        user1.age
    }
}

data class User(var id: Int, var name: String) {
    var age: Int = 0
}