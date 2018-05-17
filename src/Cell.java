import javax.swing.*;
import java.awt.*;

public class Cell {
    private boolean Alive;
    private JButton Block = new JButton();
    private int[] BlockCords= new int[2];
    private int NextToo;

    public Cell(boolean alive){
        Alive = alive;

    }
    public void setBlockCords(int[] newCords){
        BlockCords = newCords;
    }
    public void updateBlock(){
        if (Alive){
            Block.setBackground(Color.BLACK);
        }else{
            Block.setBackground(Color.WHITE);
        }
    }

    public int getNextToo() {
        return NextToo;
    }

    public void setNextToo(int nextToo) {
        NextToo = nextToo;
    }

    public int[] getBlockCords() {
        return BlockCords;
    }

    public boolean isAlive() {
        return Alive;
    }

    public void setAlive(boolean alive) {
        Alive = alive;
        this.getBlock().setOpaque(true);
        if (Alive){

            this.getBlock().setIcon(new javax.swing.ImageIcon("/Users/hosnib/CodeProjects/Java/ConwaysGameOfLife/src/black.png"));
        }else {
            this.getBlock().setIcon(new javax.swing.ImageIcon("/Users/hosnib/CodeProjects/Java/ConwaysGameOfLife/src/white.png"));
        }

    }

    public JButton getBlock() {
        return Block;
    }

    public void setBlock(JButton block) {
        Block = block;
    }
}
