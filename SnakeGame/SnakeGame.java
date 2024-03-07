
package SnakeGame;
import javax.swing.*;

//JFrame class consist function regarding creation of frame so inheriting it in main class
public class SnakeGame extends JFrame{
    
       SnakeGame(){
           super("Snake Game");//..initializing the heading of frame
           add(new Board());//...........add method add board on frame
           pack();//.....................refrashing the frame many times
           
           setVisible(true);//.................refrashing the frame one times
           setSize(1000,1000);//....initializing the size of frame
           setLocationRelativeTo(null);//......locating the frame to center
           
       }
    
    public static void main(String[] args) {
           new SnakeGame(); //creation of object of SnakeGame so that constructor gets called
    }
    
}
