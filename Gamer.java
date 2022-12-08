package krestiki;
//����������� ����� - ����� � ����� - �������� ������ � ����������
public abstract class Gamer {
    //������ � ��� ������
    private String gamerSign;
    private String gamerName;
    //����������� - �� ����� ������ � ���
    public Gamer (String gamerSign, String gamerName) {
        this.gamerSign = gamerSign;
        this.gamerName = gamerName;
    }
    //������ ������
    public String getGamerSign() {
        return gamerSign;
    }
    //������ �����
    public String getGamerName() {
        return gamerName;
    }
    //����� - ����� ������ ���. �� ����� ���������� ������ � ����
    public void doTurn(byte turn0, byte turn1, BattleField battleField) {
        //�������� ����� ���� ��� ��������� �������� ������
        battleField.setCondition(turn0, turn1, getGamerSign());
    }

}
