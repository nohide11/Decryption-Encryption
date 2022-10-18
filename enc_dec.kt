package tictactoe

import kotlin.system.exitProcess
import java.io.File
import java.io.BufferedReader


class Caesar(strInput1: String, key1: Int) {

    private val strInput = strInput1
    private val key: Int = key1

    private val list = ('a'..'z').toList()
    private val hash = HashMap<Char, Int>()

    fun encryption(alg: String): String {
        var output = ""
        for (i in list.indices) hash.put(list[i], i)


        return if (alg == "shift") {
            var newKey: Int = key
            while (newKey > 26) newKey -= 26
            for (i in strInput) {
                val outChar = if (list.indexOf(i) + newKey > 26) list[list.indexOf(i) + newKey - 26]
                else list[list.indexOf(i) + newKey]
                output += if (hash.contains(i)) outChar else " "
            }
            output
        } else {
            for (i in strInput) output += i + key
            output

        }
    }

    fun decryption(alg: String): String {
        var output = ""
        for (i in list.indices) hash.put(list[i], i)
        var newKey = key
        while (newKey > 26) newKey -= 26

        return if (alg == "shift") {
            var newKey: Int = key
            while (newKey > 26) newKey -= 26
            for (i in strInput) {
                val outchar = if (list.indexOf(i) - newKey < 0) {
                    list[list.indexOf(i) - newKey + 26]
                } else {
                    list[list.indexOf(i) - newKey]
                }
                output += if (hash.contains(i)) outchar else i
            }
            output
        } else {
            for (i in strInput) output += i - key
            output
        }
    }
}

fun main(args: Array<String>) {
    val bufferedReader: BufferedReader

    val strInput = if (args.contains("-in")) {
        try {
            bufferedReader = File(args[args.indexOf("-in") + 1]).bufferedReader()
            bufferedReader.close()
        } catch (e: Exception) {
            println("Error")
            exitProcess(0)
        }
        File(args[args.indexOf("-in") + 1]).bufferedReader().use { it.readText() }
    } else if (args.contains("-data")) {
        args[args.indexOf("-data") + 1]
    } else ""

    val alg = if (args.contains("-alg")) args[args.indexOf("-alg") + 1] else "shift"
    val encOrDec = if (args.contains("-mode")) args[args.indexOf("-mode") + 1] else "enc"
    val key = if (args.contains("-key")) args[args.indexOf("-key") + 1].toInt() else 0

    val out: File = if (args.contains("-out")) {
        try {
            File(args[args.indexOf("-out") + 1])
        } catch (e: Exception) {
            println("Error")
            exitProcess(0)
        }
        File(args[args.indexOf("-out") + 1])
    } else File("qwertgh.txt")

    val caesar = Caesar(strInput, key)

    when (encOrDec) {
        "enc" -> {
            println(caesar.encryption(alg))
            if (args.contains("-out")) {
                out.writeText(caesar.encryption(alg))
            }
        }
        "dec" -> {
            println(caesar.decryption(alg))
            if (args.contains("-out")) {
                out.writeText(caesar.decryption(alg))
            }
        }
        else -> exitProcess(0)
    }

    File(args[args.indexOf("-in") + 1]).bufferedReader().close()
}
