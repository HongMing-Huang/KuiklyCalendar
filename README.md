# KuiklyCalendar

基于 [KuiklyUI](https://github.com/Tencent-TDS/KuiklyUI) 跨端框架构建的日历组件库，提供月视图、周视图、年视图三种视图模式，支持 SINGLE / MULTI / RANGE 三种日期选择模式，内置中/英/日/韩四语言国际化和事件标记功能。支持 Android / iOS 平台（Web / HarmonyOS 待后续版本支持）。

> 对应 Issue：[Tencent-TDS/KuiklyUI#1476](https://github.com/Tencent-TDS/KuiklyUI/issues/1476)

## 组件列表

| 类别 | DSL 函数 | 说明 |
|------|---------|------|
| **月视图** | `Calendar` | 月视图主组件，含月份标题头部、导航箭头、7 列日期网格、星期表头、今日高亮 |
| **周视图** | `WeekCalendar` | 单周视图，展示 7 天日期行，支持前后周导航 |
| **年视图** | `YearCalendar` | 年视图，12 个月双列可滚动概览，支持月份下钻 |
| **子组件** | `CalendarHeader` | 月份标题头部子组件（可独立使用） |
| | `CalendarGrid` | 日期网格子组件（可独立使用） |
| **数据模型** | `CalendarDate` | 日期值对象（year, month, day） |
| | `DayCell` | 网格单元格数据 |
| | `CalendarEventMark` | 事件标记数据（日期 + 颜色） |
| | `CalendarSelection` | 选择结果回调数据 |
| | `CalendarViewMode` | 视图模式枚举（MONTH / WEEK / YEAR） |
| | `CalendarSelectionMode` | 选择模式枚举（SINGLE / MULTI / RANGE） |
| **配置** | `CalendarAttr` | 组件属性配置（颜色、尺寸、选择模式、国际化等） |
| | `CalendarEvent` | 事件回调配置（onDateSelected、onMonthChanged） |
| **国际化** | `CalendarLocale` | 语言包（内置 zh/en/ja/ko），支持自定义格式 |
| **工具** | `CalendarUtils` | 日期计算工具（纯 Kotlin，无平台依赖） |

## 接入指南

### 1. 添加 Maven 仓库

在 `settings.gradle.kts` 中添加：

```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://repo1.maven.org/maven2/")
    }
}
```

### 2. 添加依赖

在 `build.gradle.kts` 的 `commonMain` 中添加：

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("com.tencent.kuikly-open:core:2.15.0-2.1.21")
            // 日历组件（发布后替换为实际坐标）
            // implementation("com.tencent.kuikly.calendar:kuikly-calendar:1.0.0")
        }
    }
}
```

## 使用示例

### 示例 1 - 基础月视图（单选）

```kotlin
Calendar {
    attr {
        initialYear = 2025
        initialMonth = 7
        selectionMode = CalendarSelectionMode.SINGLE
    }
    event {
        onDateSelected = { selection ->
            println("选中日期：${selection.dates}")
        }
        onMonthChanged = { year, month ->
            println("切换到：${year}年${month}月")
        }
    }
}
```

### 示例 2 - 多日期选择 + 事件标记

```kotlin
Calendar {
    attr {
        selectionMode = CalendarSelectionMode.MULTI
        eventMarks = listOf(
            CalendarEventMark(2025, 7, 5, Color(0xFFFF4444)),   // 红色标记
            CalendarEventMark(2025, 7, 10, Color(0xFF4444FF)),  // 蓝色标记
            CalendarEventMark(2025, 7, 15, Color(0xFF44FF44)),  // 绿色标记
        )
    }
    event {
        onDateSelected = { selection ->
            println("选中 ${selection.dates.size} 个日期")
        }
    }
}
```

### 示例 3 - 范围选择

```kotlin
Calendar {
    attr {
        selectionMode = CalendarSelectionMode.RANGE
        selectedColor = Color(0xFF4A90D9)
        todayColor = Color(0xFFFF6B6B)
    }
    event {
        onDateSelected = { selection ->
            println("范围：${selection.dates.first()} ~ ${selection.dates.last()}")
        }
    }
}
```

### 示例 4 - 周视图

```kotlin
WeekCalendar {
    attr {
        initialYear = 2025
        initialMonth = 7
        initialDay = 23
    }
    event {
        onDateSelected = { selection ->
            println("选中日期：${selection.dates}")
        }
    }
}
```

### 示例 5 - 年视图

```kotlin
YearCalendar {
    attr {
        initialYear = 2025
    }
    event {
        onMonthSelected = { year, month ->
            println("选择月份：${year}年${month}月")
        }
    }
}
```

### 示例 6 - 国际化

```kotlin
Calendar {
    attr {
        locale = CalendarLocale.chinese()   // 中文
        // locale = CalendarLocale.english()  // 英文
        // locale = CalendarLocale.japanese() // 日文
        // locale = CalendarLocale.korean()   // 韩文
    }
}
```

## 示例项目

完整示例代码请参见 [`CalendarDemoPage.kt`](shared/src/commonMain/kotlin/com/tencent/kuikly/calendar/demo/CalendarDemoPage.kt)。

运行 Demo：
```bash
# Android
./gradlew :androidApp:assembleDebug

# 安装到设备
./gradlew :androidApp:installDebug
```

## 相关资源

- [KuiklyUI 官方文档](https://kuikly.tds.qq.com/)
- [KuiklyUI 仓库](https://github.com/Tencent-TDS/KuiklyUI)
- [KuiklyUI Issue #1476 - 日历组件需求](https://github.com/Tencent-TDS/KuiklyUI/issues/1476)

## License

本项目遵循 [KuiklyUI License](https://github.com/Tencent-TDS/KuiklyUI/blob/main/LICENSE)。
