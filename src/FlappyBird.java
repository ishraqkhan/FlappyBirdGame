/**
 * Created by Ishraq on 5/31/2016.
 */


import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.Timer;
import java.util.Random;

public class FlappyBird implements ActionListener, MouseListener, KeyListener{

    public static FlappyBird flappyBird;

    public final int WIDTH = 800;
    public final int HEIGHT = 800;

    public int ticks, yMotion, score;
    public boolean gameOver = false, started = false;

    public Renderer renderer;
    public Rectangle bird;
    public ArrayList<Rectangle> pipes;

    public FlappyBird(){
        JFrame myFrame = new JFrame();
        Timer timer = new Timer(20, this);

        renderer = new Renderer();

        myFrame.add(renderer);
        myFrame.setTitle("Flappy Bird");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(WIDTH, HEIGHT);
        myFrame.addMouseListener(this);
        myFrame.addKeyListener(this);
        myFrame.setResizable(false);
        myFrame.setVisible(true);
        bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
        pipes = new ArrayList<Rectangle>();

        addPipes(true);
        addPipes(true);
        addPipes(true);
        addPipes(true);

        timer.start();
    }

    public void addPipes(boolean start){
        Random myRandom = new Random();
        int space = 300, width = 100;
        int height = 50 + myRandom.nextInt(300);

        if (start){
            pipes.add(new Rectangle(WIDTH + width + pipes.size() * 300, HEIGHT - height - 120, width, height));
            pipes.add(new Rectangle(WIDTH + width +(pipes.size() - 1) * 300, 0, width, HEIGHT - height - space));
        }
        else{
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x + 600, HEIGHT - height - 120, width, height));
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x, 0, width, HEIGHT - height - space));
        }

    }

    public void paintPipes(Graphics g, Rectangle pipe){
        g.setColor(Color.green.darker());
        g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
    }

    public void jump(){
        if (gameOver == true){
            bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
            pipes.clear();
            yMotion = 0;
            score = 0;

            addPipes(true);
            addPipes(true);
            addPipes(true);
            addPipes(true);

            gameOver = false;
        }
        if (!started){
            started = true;
        }
        else if (!gameOver){
            if (yMotion > 0){
                yMotion = 0;
            }
            yMotion -= 12;
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        int speed = 10;
        ticks++;
        if (started) {
            for (int i = 0; i < pipes.size(); i++) {
                Rectangle pipe = pipes.get(i);
                pipe.x -= speed;
            }

            if (ticks % 2 == 0 && yMotion < 15) {
                yMotion += 2;
            }

            for (int i = 0; i < pipes.size(); i++) {
                Rectangle pipe = pipes.get(i);
                if (pipe.x + pipe.width < 0) {
                    pipes.remove(pipe);
                    if (pipe.y == 0) {
                        addPipes(false);
                    }
                }
            }

            bird.y += yMotion;

            for (Rectangle pipe : pipes) {
                if (!gameOver && pipe.y == 0 && ((bird.x + bird.width / 2 > pipe.x + pipe.width / 2 - 5) && (bird.x + bird.width / 2 < pipe.x + pipe.width / 2 + 5))) {
                    score++;
                }

                if (pipe.intersects(bird)) {
                    gameOver = true;
                    if (bird.x <= pipe.x) {
                        bird.x = pipe.x - bird.width;
                    } else {
                        if (pipe.y != 0) {
                            bird.y = pipe.y - bird.height;
                        } else if (bird.y < pipe.height) {
                            bird.y = pipe.height;
                        }
                    }

                }

                if (bird.y > HEIGHT - 120 || bird.y < 0) {
                    gameOver = true;
                }

                if (bird.y + yMotion >= HEIGHT - 120) {
                    bird.y = HEIGHT - 120 - bird.height;
                }
            }
        }
        renderer.repaint();
    }

    public void repaint(Graphics g){
        g.setColor(Color.cyan);
        g.fillRect(0,0, WIDTH, HEIGHT);

        g.setColor(Color.orange);
        g.fillRect(0, HEIGHT - 120, WIDTH, 120);

        g.setColor(Color.green);
        g.fillRect(0, HEIGHT - 120, WIDTH, 20);

        g.setColor(Color.red);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        for(Rectangle pipe : pipes){
            paintPipes(g, pipe);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 100));

        if (!started){
            g.drawString("Click to start:", 75, HEIGHT / 2 - 50);
        }
        else if (gameOver){
            g.drawString("GAME OVER", 75, HEIGHT / 2 - 50);
            g.drawString("Score: " + score, 100, HEIGHT / 2 + 50);
        }

        if (!gameOver && started){
            g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
        }

    }

    public void mouseClicked(MouseEvent e){
        jump();
    }
    public void mousePressed(MouseEvent e){

    }

    public void mouseReleased(MouseEvent e){

    }
    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e){

    }

    public void keyTyped(KeyEvent e){

    }

    public void keyPressed(KeyEvent e){

    }

    public void keyReleased(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            jump();
        }
    }

    public static void main(String[] args){
        flappyBird = new FlappyBird();
    }

}
