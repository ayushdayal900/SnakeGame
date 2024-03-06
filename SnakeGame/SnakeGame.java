
package SnakeGame;
import javax.swing.*;

public class SnakeGame extends JFrame{
    
       SnakeGame(){
           super("Snake Game");
           add(new Board());//..........add method add board on frame
           pack();//....................refrashing the frame many times
           
           setVisible(true);//..........refrashing the frame one times
           setSize(1000,1000);
           setLocationRelativeTo(null);
           
       }
    
    public static void main(String[] args) {
           new SnakeGame();
    }
    
}
