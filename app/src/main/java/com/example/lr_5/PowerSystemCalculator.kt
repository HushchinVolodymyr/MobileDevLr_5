package com.example.lr_5

data class TransmissionSystem (
    val elGasActiv: Double,
    val PL: Double,
    val PLLong: Int,
    val transmission: Double,
    val activ: Double,
    val connection: Double,
    val connectionTimes: Double
)

data class Losses (
    val costAvar: Double,
    val costPlan: Double
)

class PowerSystemCalculator {


    fun calcReliable(transmissionSystem: TransmissionSystem): String {
        val omegaOs  = transmissionSystem.elGasActiv  + transmissionSystem.PL * transmissionSystem.PLLong +
                transmissionSystem.transmission + transmissionSystem.activ + transmissionSystem.connection *
                transmissionSystem.connectionTimes

        val tBsOs  = (transmissionSystem.elGasActiv * 30 + (transmissionSystem.PL * transmissionSystem.PLLong) * 10 +
                transmissionSystem.transmission * 100 + transmissionSystem.activ * 15 +
                (transmissionSystem.connection * transmissionSystem.connectionTimes) * 2) /
                omegaOs

        var kaOs  = (omegaOs  * tBsOs ) / 8760

        var knOs  = 1.2 * ( 43 / 8760)

        var omegaDk = 2 * (kaOs * 10e-4 + knOs)

        val omegaDs = omegaDk + 0.02

        if (omegaDs < omegaOs) {
            return "Double circuit system is more reliable"
        } else if (omegaDs > omegaOs) {
            return "Single circuit system is more reliable."
        } else {
            return "Both systems are equally reliable"
        }
    }


    fun calculateLoss(losses: Losses): Double {
        val mWnedl = (0.01 * (45 * 0.01) * (5.12 * 0.01) * 6451) * 10000
        val mWnedp = ((4 * 0.01) * (5.12 * 0.01) * 6451) * 10000

        val loss = losses.costAvar * mWnedl + losses.costPlan * mWnedp

        return loss
    }
}