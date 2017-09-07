package com.kotlin

import java.math.BigDecimal
import java.io.File
import java.io.FileOutputStream
import java.io.FileInputStream

/**
 * Created by Ziwei on 5/20/2017.
 */

fun main(args: Array<String>) {

    val message: String = "This is a test! - Signed by Jacob Zuo"
    val encryptedMessage: ByteArray = encrypt(message, 1024)

    writeToFile("./encrypted/message.txt", encryptedMessage)
    println("Encrypted Message: ${encryptedMessage}")

    val inputStream = FileInputStream("./encrypted/message.txt")
    val savedMessage: ByteArray = inputStream.readBytes(DEFAULT_BUFFER_SIZE)
    val decryptedMessage: String = decrypt(savedMessage)

    println("Decrypted Message: ${decryptedMessage}")
    writeToFile("./decrypted/message.txt", decryptedMessage)

    println("Checksum: " + createCheckSum("D:\\Program Files (x86)\\Launchy\\readme.pdf"))
    println("Checksum: " + createCheckSum("./decrypted/message.txt"))
}

fun writeToFile(path: String = "./message.txt", content: String) {
    writeToFile(path, content.toByteArray())
}

fun writeToFile(path: String = "./message.txt", content: ByteArray) {
    val keyFile: File = File(path)
    if (keyFile.parentFile != null) {
        keyFile.parentFile.mkdirs()
    }
    keyFile.createNewFile()

    val fos: FileOutputStream = FileOutputStream(keyFile)
    fos.write(content)
    fos.close()
}

fun moneyTest() {
    val tickets = Money(BigDecimal(100), "$")
    val myTickets = tickets.copy()
    val popcorn = tickets.copy(BigDecimal(25), "EUR")

    if (moneyCompare(tickets, myTickets)) {
        println("They are the same!")
    } else {
        println("They are different!")
    }

    tickets.amount = BigDecimal(125)
    sendPayment(popcorn, People("Jacob", "Zuo"))

    val total = moneySum(mutableListOf<Money>(tickets, myTickets, popcorn))
    println("Totals are ${total.amount.toInt()} ${total.currency}")
}

fun sendPayment(money: Money?, to: People) {
    println("Sending ${money?.amount} ${money?.currency} to ${to.firstName}")
}

fun convertToDollar(money: Money): Money = when (money.currency) {
    "$" -> money
    "EUR" -> Money(money.amount * BigDecimal(1.15), "$")
    else -> throw IllegalArgumentException("Current can't be recognized.")
}

fun moneySum(money: List<Money>): Money {
    if (money.isEmpty())
        return Money(BigDecimal(0), "")

    var sum: BigDecimal = BigDecimal(0)
    var moneySum: Money = Money(0.bd, "$")
    for (i in 0..(money.size-1)) {
        moneySum.plus(money[i])
//        if (money[i].currency != "$") {
//            sum += convertToDollar(money[i]).amount
//        } else {
//            sum += money[i].amount
//        }
    }

    return Money(sum, money[0].currency)
}

fun moneyCompare(moneySrc: Money, moneyTgt: Money): Boolean {
    if (moneySrc == moneyTgt) {
        return true
    }

    return false
}

operator fun Money.plus(money: Money) {
    if (currency == money.currency) {
        Money(this.amount + money.amount, currency)
    } else {
        val moneyInDollar = convertToDollar(money)
        Money(this.amount + moneyInDollar.amount, currency)
    }
}

private val Int.bd: BigDecimal
    get() = BigDecimal(this)
