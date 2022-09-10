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

package org.roboquant.common

import java.time.LocalDate

/**
 * Base class for all roboquant exceptions
 *
 * @constructor
 *
 * @param msg
 */
open class RoboquantException(msg: String) : java.lang.Exception(msg)

/**
 * Unsupported exception
 *
 * @constructor
 *
 * @param msg
 */
class UnsupportedException(msg: String) : RoboquantException(msg)

/**
 * Configuration exception
 *
 * @constructor
 *
 * @param msg
 */
class ConfigurationException(msg: String) : RoboquantException(msg)

/**
 * Validation exception
 *
 * @constructor
 *
 * @param msg
 */
class ValidationException(msg: String) : RoboquantException(msg)

/**
 * Does not compute exception is thrown when a certain computation cannot deliver a result, for example because
 * the optimization doesn't converge.
 *
 * @constructor
 *
 * @param msg
 */
class DoesNotComputeException(msg: String) : RoboquantException(msg)

/**
 * No Trading exception
 *
 * @param date
 */
class NoTrading(date: LocalDate) : RoboquantException("$date is not a trading day")