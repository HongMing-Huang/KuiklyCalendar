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
import com.tencent.kuikly.core.base.ComposeAttr

/**
 * 日历组件属性配置类
 *
 * 通过 DSL 风格设置日历的各种属性：
 * ```
 * Calendar {
 *     attr {
 *         initialDate(2025, 7, 23)
 *         viewMode = CalendarViewMode.MONTH
 *         selectionMode = CalendarSelectionMode.SINGLE
 *         selectedColor = Color(0xFF4A90D9)
 *     }
 * }
 * ```
 */
class CalendarAttr : ComposeAttr() {

    // === 日期配置 ===

    /** 初始显示年份，取值范围 > 0 或 0（0 表示使用系统当前年份），默认 `0` */
    var initialYear: Int = 0

    /** 初始显示月份，取值范围 1-12 或 0（0 表示使用系统当前月份），默认 `0` */
    var initialMonth: Int = 0

    /** 初始选中日期，取值范围 1-31 或 0（0 表示不预选中任何日期），默认 `0` */
    var initialDay: Int = 0

    /**
     * 设置初始日期
     *
     * @param year 年份
     * @param month 月份 (1-12)
     * @param day 日期 (1-31)，默认 1
     */
    fun initialDate(year: Int, month: Int, day: Int = 1) {
        initialYear = year
        initialMonth = month
        initialDay = day
    }

    // === 视图配置 ===

    /** 日历视图模式，默认 [CalendarViewMode.MONTH]（月视图） */
    @Deprecated("Calendar 仅支持月视图，周视图请使用 WeekCalendar，年视图请使用 YearCalendar")
    var viewMode: CalendarViewMode = CalendarViewMode.MONTH

    /** 日期选择模式，默认 [CalendarSelectionMode.SINGLE]（单选），可选 SINGLE / MULTI / RANGE */
    var selectionMode: CalendarSelectionMode = CalendarSelectionMode.SINGLE

    /** 周起始日，`0` = 周日开始，`1` = 周一开始，默认 `1`（周一） */
    var firstDayOfWeek: Int = 1

    /** 是否显示月份前后切换箭头按钮，默认 `true` */
    var showNavigationArrows: Boolean = true

    // === 样式配置 ===

    /** 选中日期的圆形背景色，默认 `Color(0xFF4A90D9)`（蓝色） */
    var selectedColor: Color = Color(0xFF4A90D9)

    /** 今日标记颜色，渲染为日期外围边框圆圈，默认 `Color(0xFFFF3B30)`（红色） */
    var todayColor: Color = Color(0xFFFF3B30)

    /** 星期表头行文字颜色，默认 `Color(0xFF999999)`（灰色） */
    var weekHeaderColor: Color = Color(0xFF999999)

    /** 当月日期文字颜色，默认 `Color(0xFF333333)`（深灰色） */
    var currentMonthTextColor: Color = Color(0xFF333333)

    /** 非当月日期（上月/下月占位）文字颜色，默认 `Color(0xFFCCCCCC)`（浅灰色） */
    var otherMonthTextColor: Color = Color(0xFFCCCCCC)

    /** 选中状态日期的文字颜色，默认 [Color.WHITE]（白色） */
    var selectedTextColor: Color = Color.WHITE

    /** 头部月份标题文字颜色，默认 `Color(0xFF333333)`（深灰色） */
    var headerTextColor: Color = Color(0xFF333333)

    /** 日期格子大小（正方形边长，单位 dp），`0` 表示自动按容器宽度 7 等分计算，默认 `0f` */
    var cellSize: Float = 0f

    /** 头部标题区域高度（单位 dp），默认 `44f` */
    var headerHeight: Float = 44f

    /** 星期表头行高度（单位 dp），默认 `32f` */
    var weekHeaderHeight: Float = 32f

    /** 日历组件整体左右内边距（单位 dp），默认 `15f` */
    var horizontalPadding: Float = 15f

    // === 事件标记 ===

    /** 事件标记列表，传入 [CalendarEventMark] 列表，在对应日期下方渲染彩色指示点，默认空列表 */
    var eventMarks: List<CalendarEventMark> = emptyList()

    // === 国际化 ===

    /** 国际化配置，默认 [CalendarLocale.chinese]（中文），可选 english/japanese/korean */
    var locale: CalendarLocale = CalendarLocale.chinese()
}
