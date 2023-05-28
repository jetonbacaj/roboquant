/*
 * Copyright 2020-2023 Neural Layer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.roboquant.metrics

import org.roboquant.common.Asset
import org.roboquant.common.Observation
import org.roboquant.common.TimeSeries
import org.roboquant.common.Timeframe
import org.roboquant.feeds.Action
import org.roboquant.feeds.Feed
import org.roboquant.feeds.PriceAction
import org.roboquant.feeds.apply
import java.time.Instant

/**
 * Indicator calculates zero or more values based on an action and the time. Indicators are not used in a normal back
 * test, but rather come in handy when wanting to understand the outcome of some logic.
 *
 * A typical use-case is using a Chart to plot an indicator, possible together with the prices of an asset.
 */
fun interface Indicator  {

    /**
     * Calculate the indicator values
     */
    fun calculate(action: Action, time: Instant) : Map<String, Double>

    /**
     * Clear any state
     */
    fun clear() {}
}


/**
 * Apply a feed to an indicator
 */
fun Feed.apply(asset: Asset, indicator: Indicator, timeframe: Timeframe = Timeframe.INFINITE): Map<String, TimeSeries> {
    indicator.clear()
    val result = mutableMapOf<String, MutableList<Observation>>()
    val postfix = asset.symbol.lowercase()
    apply<PriceAction>(timeframe = timeframe) { action, time ->
        if (action.asset == asset) {
            val metric = indicator.calculate(action, time)
            for ((key, value) in metric) {
                val k = "$key.$postfix"
                val l = result.getOrPut(k) { mutableListOf() }
                l.add(Observation(time, value))
            }
        }
    }
    return result.mapValues { TimeSeries(it.value) }
}