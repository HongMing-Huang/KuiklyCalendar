/*
 * Tencent is pleased to support the open source community by making KuiklyUI
 * available.
 * Copyright (C) 2025 Tencent. All rights reserved.
 * Licensed under the License of KuiklyUI;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://github.com/Tencent-TDS/KuiklyUI/blob/main/LICENSE
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tencent.kuikly.calendar

import com.tencent.kuikly.core.base.Color

/**
 * 日历日期数据类，表示一个具体的年月日。
 *
 * 月份使用 1-based（1 表示一月，12 表示十二月）。
 * 实现了 [Comparable] 接口，支持按 year -> month -> day 的自然排序。
 *
 * @param year 年份
 * @param month 月份 (1-12)
 * @param day 日期 (1-31)
 */
data class CalendarDate(
    val year: Int,
    val month: Int,
    val day: Int,
) : Comparable<CalendarDate> {

    /**
     * 按 year -> month -> day 顺序比较两个日期。
     *
     * @param other 另一个日期
     * @return 负数表示当前日期更早，0 表示相同，正数表示当前日期更晚
     */
    override fun compareTo(other: CalendarDate): Int {
        if (year != other.year) return year.compareTo(other.year)
        if (month != other.month) return month.compareTo(other.month)
        return day.compareTo(other.day)
    }

    /**
     * 验证日期是否合法。
     *
     * 检查月份是否在 1-12 范围内，日期是否在 1 到该月实际天数范围内，
     * 包含闰年二月的正确计算。
     *
     * @return `true` 如果日期合法
     */
    fun isValid(): Boolean {
        if (month < 1 || month > 12) return false
        if (day < 1) return false
        val maxDay = daysInMonth(year, month)
        return day <= maxDay
    }

    /**
     * 返回该月的天数，内部辅助方法。
     */
    private fun daysInMonth(year: Int, month: Int): Int {
        val daysInFebruary =
            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
        val daysInMonths =
            intArrayOf(31, daysInFebruary, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        return daysInMonths[month - 1]
    }
}

/**
 * 日期网格单元格数据，用于日历视图的渲染。
 *
 * 每个单元格对应日历网格中的一个位置，可能是当月日期，也可能是上月或下月的占位日期。
 * 由 [CalendarUtils.generateMonthGrid] 和 [CalendarUtils.generateWeekGrid] 生成。
 *
 * @param day 显示的日期数字，0 表示占位空格（上月或下月的灰色位置）
 * @param date 对应的 [CalendarDate]，当 [day] 为 0 时为占位日期（仍包含完整的年月日信息）
 * @param isCurrentMonth 是否属于当前显示的月份，`false` 表示上月/下月占位日期，通常灰色渲染
 * @param isToday 是否是今天，为 `true` 时通常渲染为带边框圆圈的特殊样式
 * @param hasEvent 是否有事件标记，为 `true` 时在日期下方显示彩色指示点，默认 `false`
 */
data class DayCell(
    val day: Int,
    val date: CalendarDate,
    val isCurrentMonth: Boolean,
    val isToday: Boolean,
    val hasEvent: Boolean = false,
)

/**
 * 日历视图模式。
 *
 * 控制日历组件的显示粒度。
 */
enum class CalendarViewMode {
    /** 月视图，显示完整月份的日期网格 */
    MONTH,

    /** 周视图，仅显示单周的日期 */
    WEEK,

    /** 年视图，显示全年月份概览 */
    YEAR,
}

/**
 * 日期选择模式。
 *
 * 控制用户在日历中的选择行为。
 */
enum class CalendarSelectionMode {
    /** 单选，只能选择一个日期 */
    SINGLE,

    /** 多选，可以选择多个不连续的日期 */
    MULTI,

    /** 范围选择，选择起止日期之间的连续范围 */
    RANGE,
}

/**
 * 事件标记数据，用于在日历日期上显示事件指示点。
 *
 * 通过 [CalendarAttr.eventMarks] 传入，在日历网格中对应日期下方显示彩色小圆点。
 * 支持为同一日期设置多个标记，但仅显示第一个。
 *
 * 使用示例：
 * ```
 * val mark = CalendarEventMark(
 *     date = CalendarDate(2025, 7, 15),
 *     color = Color(0xFFFF4444),  // 红色指示点
 *     label = "会议"
 * )
 * ```
 *
 * @param date 标记日期，对应日历网格中的具体日期
 * @param color 标记颜色，使用 [Color] 对象（如 `Color(0xFFFF4444)` 表示红色），渲染为 4×4 像素圆点
 * @param label 标记标签文本（可选），可用于无障碍描述或扩展 UI 显示，默认为空字符串
 */
data class CalendarEventMark(
    val date: CalendarDate,
    val color: Color,
    val label: String = "",
)

/**
 * 日期选择结果。
 *
 * 封装用户在日历中选择的日期信息，通过 [CalendarEvent.onDateSelected] 回调传递。
 *
 * - [CalendarSelectionMode.SINGLE] 模式：[dates] 包含 1 个元素
 * - [CalendarSelectionMode.MULTI] 模式：[dates] 包含所有选中的不连续日期
 * - [CalendarSelectionMode.RANGE] 模式：[dates] 包含起止范围内的所有连续日期
 *
 * @param dates 选中的日期列表，内容取决于选择模式（单选/多选/范围）
 * @param timeInMillis 首个选中日期对应的 UTC 毫秒时间戳（00:00:00），默认为 0，
 *                     由 [CalendarUtils.dateToTimeMillis] 计算
 */
data class CalendarSelection(
    val dates: List<CalendarDate>,
    val timeInMillis: Long = 0L,
)
