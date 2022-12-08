package krestiki;
//��������������� ����� - "����"
public class Game {
    public void play(byte fieldSize) {
        //�������� ������ ����
        BattleField battleField = new BattleField(fieldSize);
        //�������� �������
        Player player = new Player("X", "�����");
        Computer computer = new Computer("0", "���������");
        //������ ����������� ���� � ��������� �����������
        new Gui().go(battleField, player, computer);
    }
}
