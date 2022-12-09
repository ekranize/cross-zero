import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

//Класс графического интерфейса
public class Gui {
    //Ссылки на объекты - игроки, поле, кнопки, фрейм
    Player player;
    Computer computer;
    BattleField battleField;
    JButton reGame;
    JFrame frame = new JFrame("Крестики - нолики");
    //Коллекция HashMap для хранения кнопок поля
    HashMap<String,JButton> buttons = new HashMap<String,JButton>();

    public void go(BattleField battleField, Player player, Computer computer) {
        //шрифт на кнопках
        Font bigFont = new Font("sanserif", Font.BOLD,24);
        //принимаем переданных игроков и поле
        this.player = player;
        this.computer = computer;
        this.battleField = battleField;
        //панель для кнопок
        JPanel mainPanel = new JPanel();
        //убираем компоновщик из панели
        mainPanel.setLayout(null);
        //создаем экземпляр вложенного класса - обработчика нажатия на кнопки поля
        ButtonHandler buttonHandler = new ButtonHandler();
        //кнопка начала новой игры
        reGame = new JButton("Начать заново");
        //обработчик нажатия - вложенный класс
        reGame.addActionListener(new reGameListener());
        //размеры и местоположение
        reGame.setBounds(80, 300, 140, 50);
        //скрываем ее - она понадобится только когда игра завершена
        reGame.setVisible(false);
        //добавляем ее в панель
        mainPanel.add(reGame);
        //В цикле создаем новую кнопку поля
        for (int i = 0; i < battleField.getFieldSize(); i++) {
            for (int j = 0; j < battleField.getFieldSize(); j++) {
                JButton button = new JButton();
                //ее шрифт
                button.setFont(bigFont);
                //добавляем ее в HashMap - ключом будут координаты ячейки поля
                buttons.put(i + "/" + j,button);
                //эти же координаты добавляем в "полсказку" кнопки
                button.setToolTipText(i + "/" + j);
                //вычисляем размер и местоположение кнопки
                button.setBounds(30 + j*240/battleField.getFieldSize(), 30 + i*240/battleField.getFieldSize(), 180/battleField.getFieldSize(), 180/battleField.getFieldSize());
                //обработчик у всех одинаковый - вложенный класс
                button.addActionListener(buttonHandler);
                //добапвляем кнопку на панель
                mainPanel.add(button);
            }
        }
        //действие при закрытии фрейма - завершение приложения
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //добавляем панель с полем в центр фрейма (панель займет всё пространство фрейма)
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        //размер фрейма (окна)
        frame.setSize(300,400);
        //местоположение - по центру экрана
        frame.setLocationRelativeTo(null);
        //стартуем
        frame.setVisible(true);
    }
    //Вложенный класс для обработчика событий нажатия на любую кнопку поля
    public class ButtonHandler implements ActionListener {
        //само событие
        public void actionPerformed (ActionEvent a) {
            //вытаскиваем из "подсказки" нажатой кнопки координаты ячейки поля
            String[] pTurn = ((JButton)(a.getSource())).getToolTipText().split("/");
            //вызываем метод осуществления хода игрока - ставим крестик в соответствующей ячейке
            player.doTurn(Byte.parseByte(pTurn[0]), Byte.parseByte(pTurn[1]), battleField);
            ((JButton)(a.getSource())).setText("X");
            //Enabled этой кнопки - false
            ((JButton)(a.getSource())).setEnabled(false);
            //Проверяем, есть ли факт выигрыша
            //нет
            if (!checkWinner()) {
                //вычисляем оптимальный ход компьютера
                byte[] cTurn = computer.calculate(battleField);
                //делаем ход компьютера - ставим нолик
                computer.doTurn(cTurn[0], cTurn[1], battleField);
                buttons.get(cTurn[0] + "/" + cTurn[1]).setText("0");
                //Enabled этой кнопки - false
                buttons.get(cTurn[0] + "/" + cTurn[1]).setEnabled(false);
            }
            //Проверяем, есть ли факт выигрыша
            checkWinner();

        }
        //метод для проверки факта выигрыша
        private boolean checkWinner () {
            //спрашиваем у Поля об этом
            if (battleField.checkWinnerSign().equals("[X]") || battleField.checkWinnerSign().equals("[0]")) {
                //Если кто-то выиграл сообщаем об этом в заголовке окна
                frame.setTitle("Победитель - " + battleField.checkWinnerSign());
                //делаем все кнопки Enabled false
                for (Map.Entry<String, JButton> entry : buttons.entrySet()) {
                    entry.getValue().setEnabled(false);
                }
                //активируем кнопку переигровки
                reGame.setVisible(true);
                return true;
            } else
            //Если условие победы не достигнуто, но поле заполнено
            if (battleField.isFull() == 9) {
                //сообщаем в заголовке окна о ничье
                frame.setTitle("НИЧЬЯ!");
                //делаем все кнопки Enabled false
                for (Map.Entry<String, JButton> entry : buttons.entrySet()) {
                    entry.getValue().setEnabled(false);
                }
                //активируем кнопку переигровки
                reGame.setVisible(true);
                return true;
            }
            return false;
        }
    }
    //вложенный класс с обработчиком нажатия кнопки переигровки
    public class reGameListener implements ActionListener {
        //сам обработчик
        public void actionPerformed(ActionEvent a) {
            //возвращаем заголовок окна по умолчанию
            frame.setTitle("Крестики - нолики");
            //скрываем кнопку переигровки
            ((JButton)(a.getSource())).setVisible(false);
            //все кнопки поля - снова готовы к бою
            for (Map.Entry<String, JButton> entry : buttons.entrySet()) {
                entry.getValue().setEnabled(true);
                entry.getValue().setText("");
            }
            //очищаем поле для игры
            battleField.clearBattleField();

        }
    }
}
