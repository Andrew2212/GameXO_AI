GameXO_AI
=========

Opus magnum of Hexlet_TeamAI

Консольный клиент для разработки и тестирования ИИ
- рисует поле любого размера
- проверяет любое количество символов (длина победной строки)
- mode Human_vs_Human, Human_vs_Bot, Bot_vs_Bot

Пакеты
- 'blackbox' - черный ящик с игрой (можно не заглядывать)
- 'ai.gardnerway' - пакет для ИИ по Гарднеру
- 'ai.minimaxway' - пакет для ИИ на основе MiniMax
- 'ai.hardway' - пакет для ИИ на основе MiniMax

Все варианты ИИ реализуют интерфейс IBrainAI

public interface IBrainAI {
      int[] findMove(char[][] fieldMatrix);
}

В метод передается текущее состояние поля 
char[][] fieldMatrix 
а не координаты последнего хода противника,
чтобы обращаться к PlayerBot & AI только в одном месте кода
(там где бот дожен совершать ход)

Все PlayerBot implements IPlayer инициализируют 
private IBrainAI iBrainAI 
в своем конструкторе. 
И методом public int[] doMove() возвращают 
координаты наилучшего хода, просчитанного ИИ 
