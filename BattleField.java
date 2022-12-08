package krestiki;
import java.util.HashSet;
//����� - ������� ����
public class BattleField {
    //����������� �������� ����
    private byte fieldSize;
    //��������� ������ ��������� �������� ����
    private String[][] condition;
    //��������� ������������� ���� - ���������� ������� �����
    private byte isFull = 0;
    //�����������. �� ����� - �����������
    //������������� ������� ��������� ������� ("-") ��������
    public BattleField (byte fieldSize) {
        this.fieldSize = fieldSize;
        this.condition = new String[fieldSize][fieldSize];
        this.clearBattleField();
    }
    //����� ��� ������� ���� ��� �����������
    public void clearBattleField () {
        for (int i = 0; i < this.fieldSize; i++) {
            for (int j = 0; j < this.fieldSize; j++) {
                this.condition[i][j] = "-";
            }
        }
        this.isFull = 0;
    }
    //�������
    public byte getFieldSize() {
        return fieldSize;
    }

    public byte isFull() {
        return isFull;
    }
    public String[][] getCondition() {
        return condition;
    }
    //����� ��� ������ ��������� ����
    public void printCondition() {
        for (int i = 0; i < this.fieldSize; i++) {
            for (int j = 0; j < this.fieldSize; j++) {
                System.out.print(this.getCondition()[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
    //����� ��� �������� ���������� � ������ ��������� ����
    public String checkWinnerSign() {
        //4 ������� - ����������, �����������, 2 ��������������
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
            //���� ��� ������ � ������/������ ���� ����������� ("����������" � ���� �������),
            //������� ����� ����������
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
        //���� ��� ������ � ����� �� ���������� ���� ����������� ("����������" � ���� �������),
        //������� ����� ����������
        if ((winCounterDiag1.size() == 1) && (!winCounterDiag1.contains("-"))) {
            return winCounterDiag1.toString();
        }
        if ((winCounterDiag2.size() == 1) && (!winCounterDiag2.contains("-"))) {
            return winCounterDiag2.toString();
        }
        //���� ��� ����������
        return "�����";
    }
    //����� ��� ��������� �������� ������ � ����������� �� ������, ������� ������ ���
    //��������� - "�", "0" ��� "-" - "�����", "���������" ��� "������ ���" ��������������
    public void setCondition(byte cell1, byte cell2, String signature) {
        this.condition[cell1][cell2] = signature;
        if (signature.equals("-")) isFull--; else isFull++;
    }
}
