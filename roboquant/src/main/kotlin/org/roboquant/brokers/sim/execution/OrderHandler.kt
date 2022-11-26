/*
 * Copyright 2020-2022 Neural Layer
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

package org.roboquant.brokers.sim.execution

import org.roboquant.brokers.sim.Pricing
import org.roboquant.common.Asset
import org.roboquant.orders.CreateOrder
import org.roboquant.orders.OrderState
import java.time.Instant

/**
 * Interface for any order handler. This is a sealed interface and there are only
 * two sub interfaces:
 *
 * 1. [ModifyOrderHandler] for orders that modify other orders
 * 2. [CreateOrderHandler] for orders that generate trades
 *
 */
sealed interface OrderHandler {

    /**
     * What is the order state
     */
    val state: OrderState

    /**
     * Convenience attribute to access the status
     */
    val status
        get() = state.status

    /**
     * The underlying asset
     */
    val asset: Asset
        get() = state.order.asset


}

/**
 * Interface for orders that update another order. These orders don't generate trades by themselves. Also, important
 * to note that the following logic applies:
 *
 *  - they are executed first, before any [CreateOrderHandler] orders are executed
 *  - they are always executed, even if there is no known price for the underlying asset at that moment in time
 *
 */
interface ModifyOrderHandler : OrderHandler {

    /**
     * Modify the order for the provided [handlers] and [time]
     */
    fun execute(handlers: List<CreateOrderHandler>, time: Instant)

}

/**
 * Interface for orders that (might) generate trades.
 */
interface CreateOrderHandler : OrderHandler {

    /**
     * Execute the order for the provided [pricing] and [time] and return zero or more [Execution]
     */
    fun execute(pricing: Pricing, time: Instant): List<Execution>

    /**
     * Update the order, return true if successful, false otherwise
     */
    fun update(order: CreateOrder, time: Instant) : Boolean = false

    /**
     * Cancel the order, return true if successful, false otherwise
     */
    fun cancel(time: Instant) : Boolean

}


