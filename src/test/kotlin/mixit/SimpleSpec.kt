package mixit

import junit.framework.Assert.assertEquals
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class SimpleSpec : Spek({
    describe("a calculator") {
        val calculator = SampleCalculator()

        it("should return the result of adding the first number to the second number") {
            val sum = calculator.sum(2, 4)
            assertEquals(6, sum)
        }

        it("should return the result of subtracting the second number from the first number") {
            val subtract = calculator.subtract(4, 2)
            assertEquals(2, subtract)
        }
    }
})

class SampleCalculator {
    fun sum(num1: Int, num2: Int) = num1 + num2
    fun subtract(num1: Int, num2: Int) = num1 - num2
}