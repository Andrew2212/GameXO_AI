GameXO_AI
=========

Opus magnum of Hexlet_TeamAI

Консольный клиент для разработки и тестирования ИИ
- рисует поле любого размера
- проверяет любое количество символов (длина победной строки)
- mode Human_vs_Human, Human_vs_Bot, Bot_vs_BotEnemy

Выбор ИИ для Bot & BotEnemy - в конструкторах PlayerBot & PlayerBotEnemy 
пакета blackbox.players (хардкодно выбрать WayEnum (GARDNER/MINIMAX/SPARE))
По дефолту 
- PlayerBot:: iBrainAI = new Spare(fieldSize,numChecked);
- PlayerBotEnemy:: iBrainAI = new Minimax(fieldSize,numChecked)

Пакеты
- 'blackbox' - черный ящик с игрой (можно не заглядывать)
- 'ai.gardnerway' - пакет для ИИ по Гарднеру
- 'ai.minimax' - пакет для ИИ на основе MiniMax
- 'ai.spare' - пакет для ИИ на основе random (or dokwork - maybe later)

Все варианты ИИ реализуют интерфейс IBrainAI

public interface IBrainAI {
      int[] findMove(char[][] fieldMatrix);
}

В метод передается текущее состояние поля 
char[][] fieldMatrix 
а не координаты последнего хода противника,
чтобы обращаться к PlayerBot & AI только в одном месте кода
(там где бот должен совершать ход)

Класс GetterLastEnemyMove - костыль, позволяющий из char[][] fieldMatrix
выдрать координаты последнего хода противника ИИ (т.е. того с кем ИИ играет).
- public int[] getLastEnemyMove(char[][] fieldMatrix) {...}

PlayerBot & PlayerBotEnemy implements IPlayer инициализируют 
IBrainAI iBrainAI в своем конструкторе (подключают разный мозг).
-  iBrainAI = new Gardner/Minimax/Spare(fieldSize,numChecked); 
iBrainAI находит лучший ход методом  
- findMove(char[][] fieldMatrix)
И Plaeyr методом int[] doMove() вводит ход в игру
возвращают координаты наилучшего хода, просчитанного ИИ 

Все работает и пишет и рисует в консоль.
