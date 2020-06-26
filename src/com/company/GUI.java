package com.company;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

public class GUI extends JFrame{
    Node startNode,endNode;
    PathFinding pathFinding;
    private boolean ready = false;
    private boolean showToStart = false;
    private boolean pathfindingDone = false;
    int size;


    public  GUI(){
        size = 25;
        this.setTitle("A* Pathfinding Visualization");
        this.getContentPane().setPreferredSize(new Dimension(700, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        Board board = new Board();
        this.setContentPane(board);
        Click click = new Click();
        pathFinding = new PathFinding();
        addKeyListener(click);
        addMouseListener(click);
        addMouseMotionListener(click);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        this.revalidate();
        this.repaint();
    }


    public class Board extends JPanel{
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int r = randomWithRange(0, 255);
            int G = randomWithRange(0, 255);
            int b = randomWithRange(0, 255);
            for (int i = 0; i < getHeight(); i = i + size) {
                    g.setColor(Color.lightGray);
                    for (int j = 0; j < getWidth(); j = j + size) {

                        g.drawRect(j, i, size, size);
                    }
                }

            // Prompting user To start PathFinding now

            if(showToStart){
                Color flicker = new Color(r, G, b);
                g.setColor(flicker);
                g.setFont(new Font("serif", Font.BOLD, 25));
                g.drawString("All Set Press Enter To Start",getHeight()/3,getWidth()/7);
            }



                if(ready==true&&!pathFinding.openList.isEmpty()){
                    //async function for draw closedList to work
                        CompletableFuture.runAsync(() -> {
                            try {
                                pathFinding.findPathNow(startNode,endNode,g);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        });
                    ready=false;
                }


          //Drawing all the Closed Nodes

            if(!pathFinding.closedList.isEmpty()) {
                for (int i = 0; i < pathFinding.closedList.size(); i++) {
                    int x = pathFinding.closedList.get(i).getX();
                    int y = pathFinding.closedList.get(i).getY();
                    g.setColor(Color.RED);
                    g.fillRect(x, y, 24, 24);
                    g.setColor(Color.white);
                    //draw F
                    g.setFont(new Font("serif", Font.CENTER_BASELINE, 10));
                    g.drawString("" + pathFinding.closedList.get(i).getF(), x + 7, y + 22);
                    //draw G
                    g.setFont(new Font("serif", Font.BOLD, 8));
                    g.drawString("" + pathFinding.closedList.get(i).getG(), x + 2, y + 10);
                    //draw H
                    g.setFont(new Font("serif", Font.BOLD, 8));
                    g.drawString("" + pathFinding.closedList.get(i).getH(), x + 12, y + 10);
                }
            }

            // If Path is found Drawing It

            if(!pathFinding.running&&pathFinding.lastNode!=null){
                Node temp =pathFinding.lastNode.getParent();
                while(temp!=null){
                    Color flicker = new Color(r, G, b);
                    g.setColor(flicker);
                    g.fillRect(temp.getX(),temp.getY(),24,24);
                    temp = temp.getParent();
                }
                pathfindingDone=true;
                Color flicker = new Color(r, G, b);
                g.setColor(flicker);
                g.setFont(new Font("serif", Font.BOLD, 25));
                g.drawString("Press Space Restart",getHeight()/3,getWidth()/7);
            }

            //Draw all the Walls

            if(!pathFinding.wallList.isEmpty()){
                for (int i = 0; i <pathFinding.wallList.size() ; i++) {
                    g.setColor(Color.black);
                    g.fillRect(pathFinding.wallList.get(i).getX(), pathFinding.wallList.get(i).getY(), 24, 24);
                }
            }

            //Draw Start Node

            if(startNode!=null){

                g.setColor(Color.green);
                g.fillRect(startNode.getX(),startNode.getY(), size-1, size-1);
            }

            //Draw End Node

            if(endNode!=null){

                g.setColor(Color.red);
                g.fillRect(endNode.getX(),endNode.getY(),size-1,size-1);
            }

            //If path is Not Found
            if(!pathFinding.canMove&&!pathFinding.pathFound&&pathFinding.lastNode==null){
                Color flicker = new Color(r, G, b);
                g.setColor(flicker);
                g.setFont(new Font("serif", Font.BOLD, 25));
                g.drawString("Cannot Find the Path Press Space To Restart",getWidth()/7,getHeight()/7);
                pathfindingDone=true;
            }
            }

    }
    public class Click implements KeyListener, MouseInputListener,MouseMotionListener{

        char keyPressed = ' ';

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            keyPressed=e.getKeyChar();

            if(keyPressed==KeyEvent.VK_ENTER){
                ready=true;
                showToStart = false;
            }
            if(keyPressed==KeyEvent.VK_SPACE&&pathfindingDone){
                pathFinding = new PathFinding();
                startNode = null;
                endNode = null;
                pathfindingDone=false;
                ready =false;
                showToStart = false;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(keyPressed=='s') {
                if (startNode == null) {
                    int xRollover = e.getX() % size;
                    int yRollover = e.getY() % size;

                    startNode = new Node(e.getX()-xRollover,e.getY()-yRollover-size);
                    startNode.setF(0);
                    startNode.setG(0);
                }
                else{
                    int xRollover = e.getX() % size;
                    int yRollover = e.getY() % size;
                    startNode.setXY(e.getX()-xRollover,e.getY()-yRollover-size);
                }
            }
            if(keyPressed=='e') {
                if (endNode == null) {
                    int xRollover = e.getX() % size;
                    int yRollover = e.getY() % size;

                    endNode = new Node(e.getX()-xRollover,e.getY()-yRollover-size);

                    startNode.setH(startNode.findDistance(startNode,endNode));
                    pathFinding.addOpenList(startNode);
                    showToStart = true;
                }
                else{
                    int xRollover = e.getX() % size;
                    int yRollover = e.getY() % size;
                    endNode.setXY(e.getX()-xRollover,e.getY()-yRollover-size);
                    int x = startNode.getX()-endNode.getX();
                    int y = startNode.getY()-endNode.getY();
                    int h = (x*x)+(y*y);
                    startNode.setH(startNode.findDistance(startNode,endNode));
                    showToStart = true;
                }
            }
            keyPressed=' ';
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int xRollover = e.getX() - (e.getX() % size);
            int yRollover = e.getY() - (e.getY() % size);
            Node node = new Node(xRollover, yRollover);
            if(SwingUtilities.isLeftMouseButton(e)) {
                pathFinding.addWallList(node);
            }
            if(SwingUtilities.isRightMouseButton(e)){
                if(pathFinding.searchWallList(xRollover,yRollover)!=-1){
                    pathFinding.wallList.remove(pathFinding.searchWallList(xRollover,yRollover));
                }
            }
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }
    int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
