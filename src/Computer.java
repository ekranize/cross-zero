//Класс Компьютерного игрока - наследует класс Gamer
public class Computer extends Gamer {
    //Конструктор, как у класса Gamer
    public Computer(String gamerSign, String gamerName) {
        super(gamerSign, gamerName);
    }
    //Метод алгоритма Минимакс - рекурсия для поиска лучшего хода
    //На входе - Поле, текущая глубина поиска и отметка, чей следующий ход
    private int minimax (BattleField cBattleField, int depth, boolean isMax) {
        //Проверяем эффективность предыдущего прогнозируемого хода
        int score = checkScore(cBattleField);
        //Если уже победил Компьютер, вернуть 10 очков - значит этот прогноз хороший
        if (score == 10) return score;
        //Если уже победил Игрок, вернуть -10 очков - значит этот прогноз плохой
        if (score == -10) return score;
        //Если никто не победил и поле закончилось - значит ничья - вернуть 0 очков
        if (cBattleField.isFull() == 9) return 0;
        //Если ход Компьютера
        if (isMax) {
            //Принимаем очень маленькое количество очков по умолчанию
            int best = -1000;
            //Сканируем всё поле, ищем свободную ячейку
            for (byte i = 0; i < cBattleField.getFieldSize(); i++) {
                for (byte j = 0; j < cBattleField.getFieldSize(); j++) {
                    if (cBattleField.getCondition()[i][j].equals("-")) {
                        //Ходим в свободной ячейке Компьютером
                        cBattleField.setCondition(i,j, "0");
                        //сравниваем, где очков больше - тут или ниже в иерархии (рекурсия)
                        //При этом передаем ход оппоненту и увеличиваем глубину на 1
                        best = Math.max(best, minimax(cBattleField, depth+1, !isMax));
                        //Стираем предыдущий ход (чтобы вернуться назад и проверять другой узел "дерева")
                        cBattleField.setCondition(i,j, "-");
                    }
                }
            }
            //Возвращаем наибольшее найденное количество очков в этой ветке
            return best;
            //Если ход игрока. Тут всё аналогично ходу Компьютера, только
        } else {
            //По умолчанию принимаем очень большое количество очков
            int best = 1000;
            for (byte i = 0; i < cBattleField.getFieldSize(); i++) {
                for (byte j = 0; j < cBattleField.getFieldSize(); j++) {
                    if (cBattleField.getCondition()[i][j].equals("-")) {
                        cBattleField.setCondition(i,j, "X");
                        best = Math.min(best, minimax(cBattleField, depth+1, !isMax));
                        cBattleField.setCondition(i,j, "-");
                    }
                }
            }
            return best;
        }
    }
    //Метод для расчета лучшего хода - он как раз и вызывает алгоритм минимакс
    public byte[] calculate(BattleField cBattleField) {
        //Массив - ячейка лучшего хода, его мы и ищем
        byte[] bestMove = new byte[2];
        //По умолчанию лучшего хода нет
        bestMove[0] = -1; bestMove[1] = -1;
        //На 4 ходе компьютер может поддаться                                                       !!!!!!!!!!!!!!
        if (cBattleField.isFull() == 3) {
            while (true) {
                byte i = (byte) (Math.random() * cBattleField.getFieldSize());
                byte j = (byte) (Math.random() * cBattleField.getFieldSize());
                if (cBattleField.getCondition()[i][j].equals("-")) {
                    bestMove[0] = i; bestMove[1] = j;
                    return bestMove;
                }
            }
        }
        //Принимаем очень маленькое количество очков по умолчанию, как лучший результат
        int bestVal = -1000;
        //Сканируем всё поле, ищем свободную ячейку
        for (byte i = 0; i < cBattleField.getFieldSize(); i++) {
            for (byte j = 0; j < cBattleField.getFieldSize(); j++) {
                if (cBattleField.getCondition()[i][j].equals("-")) {
                    //Ходим в свободной ячейке Компьютером
                    cBattleField.setCondition(i,j, "0");
                    //Вычисляем количество очков вызовом Минимакса (рекурсия)
                    //При этом передаем ход Игроку и увеличиваем глубину на 1
                    int moveVal = minimax(cBattleField, 0, false);
                    //Стираем предыдущий ход (чтобы вернуться на изначальное состояние поля)
                    cBattleField.setCondition(i,j, "-");
                    //Количество очков в прогнозе больше -1000?
                    if (moveVal > bestVal) {
                        //Значит этот проноз хода - лучший
                        bestMove[0] = i; bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        //Возвращаем ячейки лучшего хода
        return bestMove;
    }
    //Метод для проверки количества очков в прогнозе
    private byte checkScore (BattleField cBattleFieldTmp) {
        if (cBattleFieldTmp.checkWinnerSign().equals("[X]")) return -10;
        if (cBattleFieldTmp.checkWinnerSign().equals("[0]")) return 10;
        return 0;
    }
}
