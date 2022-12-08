package krestiki;

//����� ������������� ������ - ��������� ����� Gamer
public class Computer extends Gamer {
    //�����������, ��� � ������ Gamer
    public Computer(String gamerSign, String gamerName) {
        super(gamerSign, gamerName);
    }
    //����� ��������� �������� - �������� ��� ������ ������� ����
    //�� ����� - ����, ������� ������� ������ � �������, ��� ��������� ���
    private int minimax (BattleField cBattleField, int depth, boolean isMax) {
        //��������� ������������� ����������� ��������������� ����
        int score = checkScore(cBattleField);
        //���� ��� ������� ���������, ������� 10 ����� - ������ ���� ������� �������
        if (score == 10) return score;
        //���� ��� ������� �����, ������� -10 ����� - ������ ���� ������� ������
        if (score == -10) return score;
        //���� ����� �� ������� � ���� ����������� - ������ ����� - ������� 0 �����
        if (cBattleField.isFull() == 9) return 0;
        //���� ��� ����������
        if (isMax) {
            //��������� ����� ��������� ���������� ����� �� ���������
            int best = -1000;
            //��������� �� ����, ���� ��������� ������
            for (byte i = 0; i < cBattleField.getFieldSize(); i++) {
                for (byte j = 0; j < cBattleField.getFieldSize(); j++) {
                    if (cBattleField.getCondition()[i][j].equals("-")) {
                        //����� � ��������� ������ �����������
                        cBattleField.setCondition(i,j, "0");
                        //����������, ��� ����� ������ - ��� ��� ���� � �������� (��������)
                        //��� ���� �������� ��� ��������� � ����������� ������� �� 1
                        best = Math.max(best, minimax(cBattleField, depth+1, !isMax));
                        //������� ���������� ��� (����� ��������� ����� � ��������� ������ ���� "������")
                        cBattleField.setCondition(i,j, "-");
                    }
                }
            }
            //���������� ���������� ��������� ���������� ����� � ���� �����
            return best;
            //���� ��� ������. ��� �� ���������� ���� ����������, ������
        } else {
            //�� ��������� ��������� ����� ������� ���������� �����
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
    //����� ��� ������� ������� ���� - �� ��� ��� � �������� �������� ��������
    public byte[] calculate(BattleField cBattleField) {
        //������ - ������ ������� ����, ��� �� � ����
        byte[] bestMove = new byte[2];
        //�� ��������� ������� ���� ���
        bestMove[0] = -1; bestMove[1] = -1;
        //�� 4 ���� ��������� ����� ���������                                                       !!!!!!!!!!!!!!
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
        //��������� ����� ��������� ���������� ����� �� ���������, ��� ������ ���������
        int bestVal = -1000;
        //��������� �� ����, ���� ��������� ������
        for (byte i = 0; i < cBattleField.getFieldSize(); i++) {
            for (byte j = 0; j < cBattleField.getFieldSize(); j++) {
                if (cBattleField.getCondition()[i][j].equals("-")) {
                    //����� � ��������� ������ �����������
                    cBattleField.setCondition(i,j, "0");
                    //��������� ���������� ����� ������� ��������� (��������)
                    //��� ���� �������� ��� ������ � ����������� ������� �� 1
                    int moveVal = minimax(cBattleField, 0, false);
                    //������� ���������� ��� (����� ��������� �� ����������� ��������� ����)
                    cBattleField.setCondition(i,j, "-");
                    //���������� ����� � �������� ������ -1000?
                    if (moveVal > bestVal) {
                        //������ ���� ������ ���� - ������
                        bestMove[0] = i; bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        //���������� ������ ������� ����
        return bestMove;
    }
    //����� ��� �������� ���������� ����� � ��������
    private byte checkScore (BattleField cBattleFieldTmp) {
        if (cBattleFieldTmp.checkWinnerSign().equals("[X]")) return -10;
        if (cBattleFieldTmp.checkWinnerSign().equals("[0]")) return 10;
        return 0;
    }
}
