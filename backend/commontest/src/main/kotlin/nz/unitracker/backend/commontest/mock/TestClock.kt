package nz.unitracker.backend.commontest.mock

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Instant

class TestClock(
    initial: Instant = Clock.System.now(),
) : Clock {
    private var current: Instant = initial

    override fun now(): Instant = current

    fun set(instant: Instant) {
        current = instant
    }

    inline fun <T> atOffset(
        offset: Duration,
        block: () -> T,
    ): T {
        val snapshot = now()
        return try {
            set(snapshot + offset)
            block()
        } finally {
            set(snapshot)
        }
    }

    inline fun <T> inFuture(
        duration: Duration,
        block: () -> T,
    ): T = atOffset(duration, block)

    inline fun <T> inPast(
        duration: Duration,
        block: () -> T,
    ): T = atOffset(-duration, block)
}

@TestConfiguration
class TestClockConfiguration {
    @Bean
    @Primary
    fun clock(): Clock = TestClock()
}

@Import(TestClockConfiguration::class)
annotation class EnableTestClock
