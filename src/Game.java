//Вспомогательный класс - "Игра"
public class Game {
    public void play(byte fieldSize) {
        //Создание нового поля
        BattleField battleField = new BattleField(fieldSize);
        //Создание игроков
        Player player = new Player("X", "Игрок");
        Computer computer = new Computer("0", "Компьютер");
        //начало графической игры с заданными параметрами
        new Gui().go(battleField, player, computer);
    }
}
