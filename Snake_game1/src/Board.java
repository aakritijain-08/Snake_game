import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {

// TODO: Implement a way for the player to win

// dimension_window
private final static int BOARDWIDTH = 1000;
private final static int BOARDHEIGHT = 980;

// joint size of snake and food 
private final static int PIXELSIZE = 25;

// The total amount of pixels the game could possibly have.
// We don't want less, because the game would end before.
// We don't more because then theres no chance for player to win.

private final static int TOTALPIXELS = (BOARDWIDTH * BOARDHEIGHT)
        / (PIXELSIZE * PIXELSIZE);

// check game_running 
private boolean inGame = true;

// Timer for recording 
private Timer timer;

// game speed ...lower the value higher the speed 
// and game would be hard to finish
private static int speed = 55;

// Instances --> snake and food 
private Snake snake = new Snake();
private Food food = new Food();

public Board() {

    addKeyListener(new Keys());
    setBackground(Color.blue);
    setFocusable(true);

    setPreferredSize(new Dimension(BOARDWIDTH, BOARDHEIGHT));

    initializeGame();
}

// paint components on screen 
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    draw(g);
}

// Draw Snake & Food (Called on repaint()).
void draw(Graphics g) {
    // Only draw if the game is running / the snake is alive
    if (inGame == true) {
        g.setColor(Color.green);
        g.fillRect(food.getFoodX(), food.getFoodY(), PIXELSIZE, PIXELSIZE); // food

        // Draw_snake.
        for (int i = 0; i < snake.getJoints(); i++) {
            // snake_head
            if (i == 0) {
                g.setColor(Color.RED);
                g.fillRect(snake.getSnakeX(i), snake.getSnakeY(i),
                        PIXELSIZE, PIXELSIZE);
                // Snake_body
            } else {
                g.fillRect(snake.getSnakeX(i), snake.getSnakeY(i),
                        PIXELSIZE, PIXELSIZE);
            }
        }

        // Sync graphics
        Toolkit.getDefaultToolkit().sync();
    } else {
        // If we're not alive, then we end our game
        endGame(g);
    }
}

void initializeGame() {
    snake.setJoints(3); // set our snake's initial size
//in our case its 3
    //create snake_body
    for (int i = 0; i < snake.getJoints(); i++) {
        snake.setSnakeX(BOARDWIDTH / 2);
        snake.setSnakeY(BOARDHEIGHT / 2);
    }
    // initial snake_direction ---> right
    snake.setMovingRight(true);

    // first food 
    food.createFood();

    // timer to record games speed
    timer = new Timer(speed, this);
    timer.start();
}

// when snake is near the food like in close area
void checkFoodCollisions() {

    if ((proximity(snake.getSnakeX(0), food.getFoodX(), 20))
            && (proximity(snake.getSnakeY(0), food.getFoodY(), 20))) {

        System.out.println("intersection");
        // add joint to snake
        snake.setJoints(snake.getJoints() + 1);
        // Create new food
        food.createFood();
    }
}

// check self_collision and collision to board edges
void checkCollisions() {

    // if self_collision
    for (int i = snake.getJoints(); i > 0; i--) {

        // Snake cant intersect with itself if it's not larger than 5
        if ((i > 5)
                && (snake.getSnakeX(0) == snake.getSnakeX(i) && (snake
                        .getSnakeY(0) == snake.getSnakeY(i)))) {
            inGame = false; // then the game ends
        }
    }

    // If the snake intersects with the board edges..
    if (snake.getSnakeY(0) >= BOARDHEIGHT) {
        inGame = false;
    }

    if (snake.getSnakeY(0) < 0) {
        inGame = false;
    }

    if (snake.getSnakeX(0) >= BOARDWIDTH) {
        inGame = false;
    }

    if (snake.getSnakeX(0) < 0) {
        inGame = false;
    }

    // if game ends timer stops 
    if (!inGame) {
        timer.stop();
    }
}

void endGame(Graphics g) {

//messgae that game is over
	String message = "GAME OVER";


// new font
    Font font = new Font("COMIC SANS ", Font.BOLD, 80);
    FontMetrics metrics = getFontMetrics(font);

//color and font
    g.setColor(Color.yellow);
    g.setFont(font);

    // message
    g.drawString(message, (BOARDWIDTH - metrics.stringWidth(message)) / 2,
            BOARDHEIGHT / 2);

    System.out.println("Game Ended");

}

// continuosly till game goes on
@Override
public void actionPerformed(ActionEvent e) {
    if (inGame == true) {

        checkFoodCollisions();
        checkCollisions();
        snake.move();

        System.out.println(snake.getSnakeX(0) + " " + snake.getSnakeY(0)
                + " " + food.getFoodX() + ", " + food.getFoodY());
    }
    // repaint screen
    repaint();
}

private class Keys extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) && (!snake.isMovingRight())) {
            snake.setMovingLeft(true);
            snake.setMovingUp(false);
            snake.setMovingDown(false);
        }

        if ((key == KeyEvent.VK_RIGHT) && (!snake.isMovingLeft())) {
            snake.setMovingRight(true);
            snake.setMovingUp(false);
            snake.setMovingDown(false);
        }

        if ((key == KeyEvent.VK_UP) && (!snake.isMovingDown())) {
            snake.setMovingUp(true);
            snake.setMovingRight(false);
            snake.setMovingLeft(false);
        }

        if ((key == KeyEvent.VK_DOWN) && (!snake.isMovingUp())) {
            snake.setMovingDown(true);
            snake.setMovingRight(false);
            snake.setMovingLeft(false);
        }

        if ((key == KeyEvent.VK_ENTER) && (inGame == false)) {

            inGame = true;
            snake.setMovingDown(false);
            snake.setMovingRight(false);
            snake.setMovingLeft(false);
            snake.setMovingUp(false);

            initializeGame();
        }
    }
}

private boolean proximity(int a, int b, int closeness) {
    return Math.abs((long) a - b) <= closeness;
}

public static int getAllDots() {
    return TOTALPIXELS;
}

public static int getDotSize() {
    return PIXELSIZE;
}
}