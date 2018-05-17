import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class main {
    ArrayList<Cell> Cells = new ArrayList<>();
    final int Factor = 40;
    final int POPULATION_SIZE = (int)Math.pow(Factor,2);//10000;
    JFrame frame = new JFrame("Game Of Life");
    JPanel pane = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    private volatile boolean threadRunning = false;
    ArrayList<int[]> Spots = new ArrayList<>();
    boolean on = false;


    public void setUp(){
        for (int i = 0; i < (int)Math.sqrt(POPULATION_SIZE); i++) {
            for (int j = 0; j < (int)Math.sqrt(POPULATION_SIZE); j++) {
                int[] t = {i,j};
                Spots.add(t);
            }
        }
        for (int i = 0; i < POPULATION_SIZE ; i++) {
            Cell newCell = new Cell(false);
            newCell.setBlockCords(Spots.get(i));
            newCell.getBlock().addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    newCell.setAlive(true);

                    pane.revalidate();
                    frame.revalidate();
                }
            });
            newCell.getBlock().setPreferredSize(new Dimension(10, 10));

            c.gridx = newCell.getBlockCords()[0];
            c.gridy = newCell.getBlockCords()[1];

            pane.add(newCell.getBlock(),c);

            Cells.add(newCell);
        }
        pane.revalidate();
        frame.revalidate();
    }
    public void initJFrame(){
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.add(pane);

    }
    public void sleep(long timeout){
        if (timeout > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(timeout);
            }catch (Exception e){

            }
        }
    }
    public void startThread(){
        Thread t = new Thread(new Runnable(){
            @Override
            public void run(){
                threadRunning = true;
                while(threadRunning){
                    update();
                    pane.revalidate();
                    frame.revalidate();
                    sleep(500);
                }
            }
        });
        t.start();
    }
    public void controlPanel(){
        JFrame cFrame = new JFrame("Control Panel");
        JPanel cPane = new JPanel(new GridBagLayout());

        cFrame.setSize(300,200);
        cFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cFrame.setVisible(true);
        cFrame.add(cPane);

        JButton start = new JButton("Next");
        JButton Automatic = new JButton("Auto");
        JButton stop = new JButton("Stop");


        start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {

                System.out.println("clickS");
                on = true;
                update();
                pane.revalidate();
                frame.revalidate();
            }
        });
        Automatic.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("auto");
                startThread();

            }
        });

        stop.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("click");
                threadRunning = false;
                on = false;
            }
        });

        c.gridx = 1;
        c.gridy = 1;
        cPane.add(start,c);

        c.gridx = 2;
        cPane.add(stop,c);

        c.gridx = 1;
        c.gridy = 2;
        cPane.add(Automatic,c);

        cFrame.add(cPane);

        cPane.revalidate();
        cFrame.revalidate();


    }

    public int getAliveNeighborCount(Cell c){
        int index = Cells.indexOf(c);

        int alive = 0;
        int top = (index - Factor);
        int bot = index + Factor;
        int L = index - 1;
        int R = index + 1;
        int TL = (index - Factor) - 1;
        int TR = (index - Factor) + 1;
        int BR = (index + Factor) + 1;
        int BL = (index + Factor) - 1;

        int[] n8 = {top,bot, L,R,BR,BL,TL,TR};



        for(int spot : n8){
            try {
                if (Cells.get(spot).isAlive()){
                    alive++;
                }
            }
            catch (Exception e){

            }

        }



        return alive;


    }
    public void update(){
        for (Cell x:Cells) {
            x.setNextToo(getAliveNeighborCount(x));
        }

        int num;
        for (Cell c:Cells) {

           num = c.getNextToo();

            if (c.isAlive()){
                if (num < 2){
                    c.setAlive(false);
                }else if (num > 3){
                    c.setAlive(false);
                }else {
                    c.setAlive(true);
                }
            }else {
                if (num == 3){
                    c.setAlive(true);
                }
            }
        }
        pane.revalidate();
        frame.revalidate();
    }

    public static void main(String[] args){
        main run = new main();
        run.initJFrame();
        run.setUp();
        run.controlPanel();
    }

    /* rules
        For a space that is 'populated':
            Each cell with one or no neighbors dies, as if by solitude.
            Each cell with four or more neighbors dies, as if by overpopulation.
            Each cell with two or three neighbors survives.
        For a space that is 'empty' or 'unpopulated'
            Each cell with three neighbors becomes populated.
     */

}
