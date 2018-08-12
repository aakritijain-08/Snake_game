public class Snake {

// snake_body
private final int[] x = new int[Board.getAllDots()];
private final int[] y = new int[Board.getAllDots()];

// snake_direction
private boolean movingLeft = false;
private boolean movingRight = false;
private boolean movingUp = false;
private boolean movingDown = false;

private int joints = 0; // the no of joints the snake has initially is 3 and then it adds up
                      

public int getSnakeX(int index) {
    return x[index];
}

public int getSnakeY(int index) {
    return y[index];
}

public void setSnakeX(int i) {
    x[0] = i;
}

public void setSnakeY(int i) {
    y[0] = i;
}

public boolean isMovingLeft() {
    return movingLeft;
}

public void setMovingLeft(boolean movingLeft) {
    this.movingLeft = movingLeft;
}

public boolean isMovingRight() {
    return movingRight;
}

public void setMovingRight(boolean movingRight) {
    this.movingRight = movingRight;
}

public boolean isMovingUp() {
    return movingUp;
}

public void setMovingUp(boolean movingUp) {
    this.movingUp = movingUp;
}

public boolean isMovingDown() {
    return movingDown;
}

public void setMovingDown(boolean movingDown) {
    this.movingDown = movingDown;
}

public int getJoints() {
    return joints;
}

public void setJoints(int j) {
    joints = j;
}

public void move() {
    for (int i = joints; i > 0; i--) {

        // joint increased or say up by 1
        x[i] = x[(i - 1)];
        y[i] = y[(i - 1)];
    }

    // snake_direction -> left
    if (movingLeft) {
        x[0] -= Board.getDotSize();
    }
    // snake_direction -> right
    if (movingRight) {
        x[0] += Board.getDotSize();
    }
    // snake_direction -> down
    if (movingDown) {
        y[0] += Board.getDotSize();
    }
    // snake_direction -> up
    if (movingUp) {
        y[0] -= Board.getDotSize();
    }

    // here the dot size is the joint ...it adds up in the snake length 
}
 }