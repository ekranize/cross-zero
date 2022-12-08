package krestiki;
import java.util.HashSet;
//Класс - игровое поле
public class BattleField {
    //Размерность игрового поля
    private byte fieldSize;
    //Двумерный массив состояния игрового поля
    private String[][] condition;
    //Состояние заполненности поля - количество занятых ячеек
    private byte isFull = 0;
    //Конструктор. На входе - размерность
    //Инициализация массива состояния пустыми ("-") ячейками
    public BattleField (byte fieldSize) {
        this.fieldSize = fieldSize;
        this.condition = new String[fieldSize][fieldSize];
        this.clearBattleField();
    }
    //метод для очистки поля для переигровки
    public void clearBattleField () {
        for (int i = 0; i < this.fieldSize; i++) {
            for (int j = 0; j < this.fieldSize; j++) {
                this.condition[i][j] = "-";
            }
        }
        this.isFull = 0;
    }
    //Геттеры
    public byte getFieldSize() {
        return fieldSize;
    }

    public byte isFull() {
        return isFull;
    }
    public String[][] getCondition() {
        return condition;
    }
    //Метод для вывода состояния поля
    public void printCondition() {
        for (int i = 0; i < this.fieldSize; i++) {
            for (int j = 0; j < this.fieldSize; j++) {
                System.out.print(this.getCondition()[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
    //Метод для проверки победителя в текщем состоянии поля
    public String checkWinnerSign() {
        //4 ХэшСета - построчный, постобцовый, 2 подиагональных
        HashSet<String> winCounterHorizontal = new HashSet<>();
        HashSet<String> winCounterVertical = new HashSet<>();
        HashSet<String> winCounterDiag1 = new HashSet<>();
        HashSet<String> winCounterDiag2 = new HashSet<>();
        for (int i = 0; i < fieldSize; i++) {
            winCounterHorizontal.clear();
            winCounterVertical.clear();
            for (int j = 0; j < fieldSize; j++) {
                winCounterHorizontal.add(condition[i][j]);
                winCounterVertical.add(condition[j][i]);
            }
            //Если все ячейки в строке/стобце были одинаковыми ("свернулись" в один элемент),
            //вернуть этого победителя
            if ((winCounterHorizontal.size() == 1) && (!winCounterHorizontal.contains("-"))) {
                return winCounterHorizontal.toString();
            }
            if ((winCounterVertical.size() == 1) && (!winCounterVertical.contains("-"))) {
                return winCounterVertical.toString();
            }
        }
        for (int i = 0; i < fieldSize; i++) {
            winCounterDiag1.add(condition[i][i]);
            winCounterDiag2.add(condition[i][fieldSize - i - 1]);
        }
        //Если все ячейки в одной из диагоналей были одинаковыми ("свернулись" в один элемент),
        //вернуть этого победителя
        if ((winCounterDiag1.size() == 1) && (!winCounterDiag1.contains("-"))) {
            return winCounterDiag1.toString();
        }
        if ((winCounterDiag2.size() == 1) && (!winCounterDiag2.contains("-"))) {
            return winCounterDiag2.toString();
        }
        //Если нет победителя
        return "Пусто";
    }
    //Метод для установки значения ячейки в зависимости от игрока, который сделал ход
    //Сигнатура - "Х", "0" или "-" - "игрок", "компьютер" или "убрать ход" соответственно
    public void setCondition(byte cell1, byte cell2, String signature) {
        this.condition[cell1][cell2] = signature;
        if (signature.equals("-")) isFull--; else isFull++;
    }
}
