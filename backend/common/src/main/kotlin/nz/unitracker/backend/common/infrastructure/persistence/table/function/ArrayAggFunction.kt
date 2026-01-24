package nz.unitracker.backend.common.infrastructure.persistence.table.function

import org.jetbrains.exposed.v1.core.ArrayColumnType
import org.jetbrains.exposed.v1.core.CustomFunction
import org.jetbrains.exposed.v1.core.ExpressionWithColumnType

/**
 * Custom SQL function wrapper for PostgreSQL's `ARRAY_AGG`.
 *
 * @param T The element type contained in the aggregated array.
 * @param expression The expression whose values will be aggregated.
 */
class ArrayAggFunction<T : Any>(
    expression: ExpressionWithColumnType<T>,
) : CustomFunction<List<T?>>(
        functionName = "ARRAY_AGG",
        columnType =
            ArrayColumnType(
                delegate = expression.columnType,
            ),
        expr = arrayOf(expression),
    )

/**
 * Convenience extension for applying the `ARRAY_AGG` SQL function
 * to any typed expression.
 *
 * @return An [ArrayAggFunction] that aggregates the values of this expression
 *         into a list.
 */
fun <T : Any> ExpressionWithColumnType<T>.arrayAgg(): ArrayAggFunction<T> = ArrayAggFunction(this)
