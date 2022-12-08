package krestiki;
//Абстрактный класс - игрок в целом - родитель Игрока и Компьютера
public abstract class Gamer {
    //Значок и имя игрока
    private String gamerSign;
    private String gamerName;
    //Конструктор - на входе Значок и имя
    public Gamer (String gamerSign, String gamerName) {
        this.gamerSign = gamerSign;
        this.gamerName = gamerName;
    }
    //Геттер Значка
    public String getGamerSign() {
        return gamerSign;
    }
    //Геттер Имени
    public String getGamerName() {
        return gamerName;
    }
    //Метод - игрок делает ход. На входе координаты ячейки и поле
    public void doTurn(byte turn0, byte turn1, BattleField battleField) {
        //Вызываем метод поля для установки значения ячейки
        battleField.setCondition(turn0, turn1, getGamerSign());
    }

}
