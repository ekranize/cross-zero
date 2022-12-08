package krestiki;

public class Main {
    //Константа - размер поля
    public static final byte fieldSize = 3;
    public static void main(String[] args) {
        //Начать игру на поле заданного размера
        new Game().play(fieldSize);
    }
}
