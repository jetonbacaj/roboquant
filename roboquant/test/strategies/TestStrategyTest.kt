/*
 * Copyright 2021 Neural Layer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.roboquant.strategies


import kotlin.test.*
import org.roboquant.TestData

internal class TestStrategyTest {


    @Test
    fun test() {
        val strategy = TestStrategy(5)
        val event = TestData.event()
        var signals = strategy.generate(event)
        assertFalse(signals.isEmpty())
        signals = strategy.generate(event)
        assertTrue(signals.isEmpty())
    }
}