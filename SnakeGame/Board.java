package SnakeGame;

import javax.swing.*;       // use to use JPanel class and its member functions
import java.awt.*;         // used to use images
import java.awt.event.*;   // used to use timer and its props

// JPanel class consist of function regarding setting of frame
public class Board extends JPanel implements ActionListener {
    //-------------------------VARIABLES---------------------------------
    //----------->> to store images
    private Image apple;
    private Image dot;
    private Image head;
    
    //---------------->> initialization of dot
    private int dots;
    private final int ALL_DOTS = 500;
    private final int DOT_SIZE = 10;
    
    //---------------->> apple positions
    private final int RANDOM_POSITION = 29;
    private int apple_x;
    private int apple_y;
    
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    
    //---------------->> variables to store direction keys 
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    //----------------->> present in game or not?
    private boolean inGame = true;

    //---------------->> control speed, delay, start, stop 
    private Timer timer;
    
    //---------------------------------- BOARD ------------------------------------
    Board() {
        addKeyListener(new TAdapter()); // notice the KEY 
        
        setBackground(Color.BLACK);     // initializee background color to black
        // setPreferredSize(new Dimension(1000, 1000));
        setFocusable(true);   // auto capture of mouse in game
        
        loadImages();   // to load the images
        initGame();     // to initialize the game
    }
    //-------------------------------- INITIALIZATION OF IMAGES -------------------
    public void loadImages() {
        ImageIcon i1 = new ImageIcon(getClass().getResource("/SnakeGame/icons/apple.png"));
        apple = i1.getImage();
        
        ImageIcon i2 = new ImageIcon(getClass().getResource("/SnakeGame/icons/dot.png"));
        dot = i2.getImage();
        
        ImageIcon i3 = new ImageIcon(getClass().getResource("/SnakeGame/icons/head.png"));
        head = i3.getImage();
    }
    

    //----------------------------- INITIALIZATION OF GAME -------------------------
    public void initGame() {
        dots = 3;   // initial number of dots is 3
        
        //initial fixing position of snake
        for (int i = 0; i < dots; i++) {
            y[i] = 250; // y axis remain constant
            x[i] = 250 - i * DOT_SIZE;  // to make sure dots are in order not overlapped
        }
        
        locateApple();  // LOCATING APPLE ON BOARD
        
        timer = new Timer(80, this);    //controling speed of snake (refreshing images at ms 80)
        timer.start();  // starting the timer
    }
    
    //--------- LOCATING THE APPLE AT RANDOM PSITION A AND Y---------------
    public void locateApple() {
        int r = (int)(Math.random() * RANDOM_POSITION);
        apple_x = r * DOT_SIZE; // random x axis value
                
        r = (int)(Math.random() * RANDOM_POSITION);
        apple_y = r * DOT_SIZE;// random y axis value
    }
    
    //--------------- PAINITING COMPONENTS HEAD, BODY, MESSAGES(GAME OVER) ---------------
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    
    //-------------- DRAWING ALL COMPONENTS 
    public void draw(Graphics g) {
        if (inGame) {   // if user in game
            g.drawImage(apple, apple_x, apple_y, this);

            for (int i = 0 ; i < dots; i++) {
                if (i == 0) {   // if 1st dot then initialize (draw) head
                    g.drawImage(head, x[i], y[i], this);
                } else {        // else draw body
                    g.drawImage(dot, x[i], y[i], this);
                }
            }

            Toolkit.getDefaultToolkit().sync(); 
        } else {    // if user is not in game 
            gameOver(g);    // consol for game over
        }
    }
    
    //.......................GAME OVER REPRESENTATION
    public void gameOver(Graphics g) {
        // Making up of message , its font, boldness and size
        String msg = "Game Over!";
        Font font = new Font("SAN_SERIF", Font.BOLD, 14);
        FontMetrics metrices = getFontMetrics(font);
        
        // setting color 
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (300 - metrices.stringWidth(msg)) / 2, 300/2);
    }
    
    //........................MOVING THE SNAKE
    public void move() {
        //moving the snake
        for (int i = dots ; i > 0 ; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        
        // if lefrDirectionis true means the pressed kay is left arraow key so, 
        // we have to move in -ve x direction thats why x[0] = x[0] - DOT_SIZE;
        // it show the snake moves to left by DOT_SIZE
        if (leftDirection) {
            x[0] = x[0] - DOT_SIZE;
        }
        // similarly for right
        if (rightDirection) {
            x[0] = x[0] + DOT_SIZE;
        }
        // similarly for up
        if (upDirection) {
            y[0] = y[0] - DOT_SIZE;
        }
        // similarly for down
        if (downDirection) {
            y[0] = y[0] + DOT_SIZE;
        }
    }

    //.............................EATING OF AN APPLE
    public void checkApple() {
        // checking that the position of head is overlapping with position of apple or not
        // if yes increase the dots and locate the apple at another postion on board
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            locateApple();
        }
    }
    
    //.............................COLLISION WITH ITSELF
    public void checkCollision() {
        for(int i = dots; i > 0; i--) {
            //IF position of any point of body is equals to the position of head then lost the match
            if (( i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }
        
        // BOUNDRY CONDITIONS SNAKE GAME
        if (y[0] >= 1000) {
            inGame = false;
        }
        if (x[0] >= 1000) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        
        // IF INGAME IS FALSE  means he lost so stop the timer
        if (!inGame) {
            timer.stop();
        }
    }
    
    // MAIN RUN OF PROGRAMME
    public void actionPerformed(ActionEvent ae) {
        if (inGame) {   // check if person is in game or not
            checkApple();// check for eating of an apple
            checkCollision();//check for collision
            move(); // moving the snake
        }
        repaint();// refreshing the frame
    }
    
    // GETTING KEYS CHOICE FROM USER
    public class TAdapter extends KeyAdapter {
        @Override   // for continue purpose
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();   //initializing the key by its code value 
            
            // if we get left key from user and rightDirection is already false
            // then we initialize left direction by true so that now snake can go in left
            // and we initialize up and down direction as false so that snkae cant go up and down

            // if snake is going to the right then he/she cant directly go to left
            // 1st snake have to go up/down then left

            if (key == KeyEvent.VK_LEFT && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            //similarly for right
            
            if (key == KeyEvent.VK_RIGHT && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            //similarly for up
            
            if (key == KeyEvent.VK_UP && (!downDirection)) {
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
            }

            //similarly for down
            
            if (key == KeyEvent.VK_DOWN && (!upDirection)) {
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
        }
    }
    
}
