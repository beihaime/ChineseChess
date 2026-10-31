# ChineseChess · 中国象棋

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://openjdk.org/)
[![JavaFX](https://img.shields.io/badge/JavaFX-27-blue.svg)](https://openjfx.io/)
[![Maven](https://img.shields.io/badge/Build-Maven-red.svg)](https://maven.apache.org/)
[![JUnit](https://img.shields.io/badge/Test-JUnit%205-green.svg)](https://junit.org/junit5/)
[![GitHub stars](https://img.shields.io/github/stars/beihaime/ChineseChess?style=social)](https://github.com/beihaime/ChineseChess/stargazers)

A complete Chinese Chess (Xiangqi) game written in Java, featuring a clean architecture, full rule engine, JavaFX GUI, and unit tests.

一个用 Java 实现的完整中国象棋游戏，具备清晰架构、完整规则引擎、JavaFX 图形界面与单元测试。

> 学习项目：通过实现中国象棋，练习 Java、面向对象设计、游戏逻辑与软件架构。

---

## Features · 当前功能

### Core Rules · 核心规则
- 完整棋盘表示与初始布局
- 全部棋子走法（车、马、象、士、帅、炮、兵）
- 马腿、象眼、过河、九宫限制
- **将军检测**（Check）
- **将死 / 困毙**（Checkmate / Stalemate）
- **将帅对面**（Flying General）
- 走子后不能自将的合法性检查

### Game Logic · 游戏逻辑
- 红黑回合制
- 走子历史记录
- 撤销（Undo） / 重做（Redo）
- 游戏状态（进行中 / 将军 / 将死 / 困毙）

### User Interface · 图形界面
- JavaFX 棋盘绘制（棋盘线、楚河汉界、九宫斜线）
- 鼠标点选走子
- 选中棋子高亮
- 合法走法提示
- 选子 / 走子音效
- 侧边信息面板（当前回合、游戏状态）

### Engineering · 工程化
- Maven 构建
- JUnit 5 单元测试（规则引擎 + 游戏逻辑）
- GitHub Actions（Windows 安装包构建）
- jpackage 打包可执行程序

---

## Architecture · 项目结构

```
src/main/java/com/beihaime/chinesechess/
├── model/          # 棋盘、棋子、位置、红黑方
├── rule/           # RuleEngine — 所有走法与规则判定
├── game/           # Game — 回合、历史、撤销、胜负状态
├── ui/             # JavaFX 界面（棋盘、信息面板、音效）
└── Main.java       # 入口
```

核心设计思想：
- **RuleEngine** 专注规则，不依赖 UI
- **Game** 管理游戏流程与状态
- **UI** 只负责展示与交互

---

## Requirements · 运行环境

- **Java 25**
- **Maven 3.8+**

---

## How to Run · 运行方式

### 1. 编译 & 运行 GUI

```bash
mvn clean javafx:run
```

### 2. 打包可执行 JAR

```bash
mvn clean package
java -jar target/ChineseChess-1.0-SNAPSHOT.jar
```

### 3. 运行测试

```bash
mvn test
```

---

## Screenshots · 截图

> （建议后续补充实际运行截图）

---

## Roadmap · 未来计划

- [ ] AI 对手（Minimax / Alpha-Beta）
- [ ] 更完善的 UI（走子动画、更好的棋子材质、记谱面板）
- [ ] 棋谱导出（中文记谱 / ICCS）
- [ ] 计时器与认输/求和
- [ ] 长将 / 长捉规则
- [ ] 网络对战（可选）

---

## Tech Stack · 技术栈

| Component       | Technology          |
|-----------------|---------------------|
| Language        | Java 25             |
| Build           | Maven               |
| GUI             | JavaFX 27           |
| Testing         | JUnit 5             |
| Packaging       | jpackage + WiX      |
| CI              | GitHub Actions      |

---

## License

MIT License

---

**Author**: [beihaime](https://github.com/beihaime)
