package krestiki;

public class Test {
    public static void main(String[] args) {
        byte x=3;
        byte[] cond = new byte[2];
        byte[] bestMove = new byte[2];
        BattleField testBattleField = new BattleField(x);
        Player player = new Player("X");
        Computer computer = new Computer("0");
        cond[0] = 0; cond[1] = 1; testBattleField.setCondition(cond[0], cond[1], "X");

        testBattleField.printCondition();

        bestMove = computer.calculate(testBattleField);
        testBattleField.setCondition(bestMove[0], bestMove[1], "0");

        testBattleField.printCondition();


        }
    }

