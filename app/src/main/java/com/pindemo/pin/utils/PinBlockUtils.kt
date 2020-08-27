package com.pindemo.pin.utils

import com.pindemo.R
import java.lang.Exception
import java.security.SecureRandom
import kotlin.experimental.xor

const val MIN_LENGTH = 4
const val MAX_LENGTH = 12
private const val PAN_NUMBER = "1111222233334444"
private const val CODE_LENGTH = 16
private const val BLOCK_LENGTH = 8

class PinBlockUtils {

    class InvalidPinException(val resourceId: Int) : Exception()

    fun generatePinBlock(pin: String): ByteArray {
        if (pin.length < MIN_LENGTH) {
            throw InvalidPinException(R.string.error_pin_size)
        } else if (pin.length > MAX_LENGTH) {
            throw InvalidPinException(R.string.error_pin_size)
        }
        if (!pin.contains(Regex("^[0-9]*$"))) {
            throw InvalidPinException(R.string.error_invalid_characters)
        }

        val preppedPIN = preparePIN(pin)
        val preppedPAN = preparePAN(PAN_NUMBER)
        return getBlock(preppedPIN, preppedPAN)
    }

    private fun preparePIN(pin: String): ByteArray {
        val isoCode = 3
        val isoTypeIndex = 0
        val lenIndex = 1
        val codeStartIndex = 2

        val preparedPIN = ByteArray(CODE_LENGTH)
        preparedPIN[isoTypeIndex] = isoCode.toByte()
        preparedPIN[lenIndex] = pin.length.toByte()

        pin.mapIndexed { index, c ->
            preparedPIN[index + codeStartIndex] = Character.getNumericValue(c).toByte()
        }

        val randomIndex = pin.length + codeStartIndex

        for (i in randomIndex until CODE_LENGTH) {
            preparedPIN[i] = SecureRandom().nextInt(0xF).toByte()
        }
        return preparedPIN
    }

    private fun preparePAN(pan: String): ByteArray {
        val maxPanSize = 12
        val panStartIndex = 4
        val preparedPAN = ByteArray(CODE_LENGTH)
        val shortenPAN = pan.takeLast(maxPanSize)

        shortenPAN.mapIndexed { index, c ->
            preparedPAN[index + panStartIndex] = Character.getNumericValue(c).toByte()
        }
        return preparedPAN
    }

    private fun getBlock(pin: ByteArray, pan: ByteArray): ByteArray {
        val block = ByteArray(BLOCK_LENGTH)
        var blockIndex = 0
        pin.mapIndexed { index, pinItem ->
            when {
                index % 2 == 0 -> block[blockIndex] =
                    (pinItem xor pan[index]).setHiNibbleValue() xor block[blockIndex]
                else -> {
                    block[blockIndex] =
                        (pinItem xor pan[index]).setLowNibbleValue() xor block[blockIndex]
                    blockIndex++
                }
            }
        }
        return block
    }
}