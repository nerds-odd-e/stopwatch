package com.bodong.stopwatch;

import org.junit.Before;
import org.junit.Test;

import dalvik.annotation.TestTarget;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by asuka on 15/6/19.
 */
public class TestStopwatch {

    //    StubClock stubClock = new StubClock();
    Clock stubClock;
    Stopwatch stopwatch;
    long total;

    @Before
    public void createClockAndStopwatch() {
        stubClock = mock(Clock.class);
        when(stubClock.getCurrentTimeMillis()).thenReturn(0L);
        stopwatch = new Stopwatch(stubClock);
        total = 0;
    }

    @Test
    public void will_return_0_second_after_stopwatch_started() {
        assertEquals(0, stopwatch.secondPassed());
    }

    @Test
    public void will_return_1_second_after_stopwatch_passed_1_second() {
        passMilliseconds(1000L);

        assertEquals(1, stopwatch.secondPassed());
    }

    @Test
    public void will_return_1_second_after_stopwatch_passed_1_and_half_second() {
        passMilliseconds(1500L);

        assertEquals(1, stopwatch.secondPassed());
    }

    @Test
    public void will_return_1_second_when_start_1_second_and_then_pause_and_then_wait_for_1_second() {
        passMilliseconds(1000L);

        stopwatch.pause();

        passMilliseconds(1000L);
        assertEquals(1, stopwatch.secondPassed());
    }

    private void passMilliseconds(long milliseconds) {
        this.total += milliseconds;
        when(stubClock.getCurrentTimeMillis()).thenReturn(this.total);
    }

}
