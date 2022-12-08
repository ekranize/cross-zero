package krestiki;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

//����� ������������ ����������
public class Gui {
    //������ �� ������� - ������, ����, ������, �����
    Player player;
    Computer computer;
    BattleField battleField;
    JButton reGame;
    JFrame frame = new JFrame("�������� - ������");
    //��������� HashMap ��� �������� ������ ����
    HashMap<String,JButton> buttons = new HashMap<String,JButton>();

    public void go(BattleField battleField, Player player, Computer computer) {
        //����� �� �������
        Font bigFont = new Font("sanserif", Font.BOLD,24);
        //��������� ���������� ������� � ����
        this.player = player;
        this.computer = computer;
        this.battleField = battleField;
        //������ ��� ������
        JPanel mainPanel = new JPanel();
        //������� ����������� �� ������
        mainPanel.setLayout(null);
        //������� ��������� ���������� ������ - ����������� ������� �� ������ ����
        ButtonHandler buttonHandler = new ButtonHandler();
        //������ ������ ����� ����
        reGame = new JButton("������ ������");
        //���������� ������� - ��������� �����
        reGame.addActionListener(new reGameListener());
        //������� � ��������������
        reGame.setBounds(80, 300, 140, 50);
        //�������� �� - ��� ����������� ������ ����� ���� ���������
        reGame.setVisible(false);
        //��������� �� � ������
        mainPanel.add(reGame);
        //� ����� ������� ����� ������ ����
        for (int i = 0; i < battleField.getFieldSize(); i++) {
            for (int j = 0; j < battleField.getFieldSize(); j++) {
                JButton button = new JButton();
                //�� �����
                button.setFont(bigFont);
                //��������� �� � HashMap - ������ ����� ���������� ������ ����
                buttons.put(i + "/" + j,button);
                //��� �� ���������� ��������� � "���������" ������
                button.setToolTipText(i + "/" + j);
                //��������� ������ � �������������� ������
                button.setBounds(30 + j*240/battleField.getFieldSize(), 30 + i*240/battleField.getFieldSize(), 180/battleField.getFieldSize(), 180/battleField.getFieldSize());
                //���������� � ���� ���������� - ��������� �����
                button.addActionListener(buttonHandler);
                //���������� ������ �� ������
                mainPanel.add(button);
            }
        }
        //�������� ��� �������� ������ - ���������� ����������
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //��������� ������ � ����� � ����� ������ (������ ������ �� ������������ ������)
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        //������ ������ (����)
        frame.setSize(300,400);
        //�������������� - �� ������ ������
        frame.setLocationRelativeTo(null);
        //��������
        frame.setVisible(true);
    }
    //��������� ����� ��� ����������� ������� ������� �� ����� ������ ����
    public class ButtonHandler implements ActionListener {
        //���� �������
        public void actionPerformed (ActionEvent a) {
            //����������� �� "���������" ������� ������ ���������� ������ ����
            String[] pTurn = ((JButton)(a.getSource())).getToolTipText().split("/");
            //�������� ����� ������������� ���� ������ - ������ ������� � ��������������� ������
            player.doTurn(Byte.parseByte(pTurn[0]), Byte.parseByte(pTurn[1]), battleField);
            ((JButton)(a.getSource())).setText("X");
            //Enabled ���� ������ - false
            ((JButton)(a.getSource())).setEnabled(false);
            //���������, ���� �� ���� ��������
            //���
            if (!checkWinner()) {
                //��������� ����������� ��� ����������
                byte[] cTurn = computer.calculate(battleField);
                //������ ��� ���������� - ������ �����
                computer.doTurn(cTurn[0], cTurn[1], battleField);
                buttons.get(cTurn[0] + "/" + cTurn[1]).setText("0");
                //Enabled ���� ������ - false
                buttons.get(cTurn[0] + "/" + cTurn[1]).setEnabled(false);
            }
            //���������, ���� �� ���� ��������
            checkWinner();

        }
        //����� ��� �������� ����� ��������
        private boolean checkWinner () {
            battleField.printCondition();
            System.out.println(battleField.checkWinnerSign());
            //���������� � ���� �� ����
            if (battleField.checkWinnerSign().equals("[X]") || battleField.checkWinnerSign().equals("[0]")) {
                //���� ���-�� ������� �������� �� ���� � ��������� ����
                frame.setTitle("���������� - " + battleField.checkWinnerSign());
                //������ ��� ������ Enabled false
                for (Map.Entry<String, JButton> entry : buttons.entrySet()) {
                    entry.getValue().setEnabled(false);
                }
                //���������� ������ �����������
                reGame.setVisible(true);
                return true;
            } else
            //���� ������� ������ �� ����������, �� ���� ���������
            if (battleField.isFull() == 9) {
                //�������� � ��������� ���� � �����
                frame.setTitle("�����!");
                //������ ��� ������ Enabled false
                for (Map.Entry<String, JButton> entry : buttons.entrySet()) {
                    entry.getValue().setEnabled(false);
                }
                //���������� ������ �����������
                reGame.setVisible(true);
                return true;
            }
            return false;
        }
    }
    //��������� ����� � ������������ ������� ������ �����������
    public class reGameListener implements ActionListener {
        //��� ����������
        public void actionPerformed(ActionEvent a) {
            //���������� ��������� ���� �� ���������
            frame.setTitle("�������� - ������");
            //�������� ������ �����������
            ((JButton)(a.getSource())).setVisible(false);
            //��� ������ ���� - ����� ������ � ���
            for (Map.Entry<String, JButton> entry : buttons.entrySet()) {
                entry.getValue().setEnabled(true);
                entry.getValue().setText("");
            }
            //������� ���� ��� ����
            battleField.clearBattleField();

        }
    }
}
