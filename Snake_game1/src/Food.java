public class Food {

private Snake snake = new Snake();
private int foodX; // position_food ----x---
private int foodY; // position_food ----y---

// for random position of food(each time different)
private final int RANDOMPOSITION = 40;


public void createFood() {

    // give x and y some random position of food 

    int location = (int) (Math.random() * RANDOMPOSITION);
    foodX = ((location * Board.getDotSize()));

    location = (int) (Math.random() * RANDOMPOSITION);
    foodY = ((location * Board.getDotSize()));

    if ((foodX == snake.getSnakeX(0)) && (foodY == snake.getSnakeY(0))) {
        createFood();
    }
}

public int getFoodX() {

    return foodX;
}

public int getFoodY() {
    return foodY;
}
}