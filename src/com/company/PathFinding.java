package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;


public class PathFinding {
    LinkedList<Node> openList;
    ArrayList<Node> closedList;
    LinkedList<Node> wallList;
    boolean running = true;
    public boolean canMove = true;
    public boolean pathFound = true;
    int count = 0;
    Node lastNode;

    PathFinding() {
        openList = new LinkedList<>();
        closedList = new ArrayList<>();
        wallList = new LinkedList<>();
    }

    public ArrayList<Node> getClosedList() {
        return closedList;
    }

    public LinkedList<Node> getOpenList() {
        return openList;
    }

    public void addClosedList(Node node) {
        this.closedList.add(node);
    }

    public void addOpenList(Node node) {
        this.openList.push(node);
    }

    public void addWallList(Node node) {
        this.wallList.add(node);
    }

    public int searchWallList(int x, int y) {
        int location = -1;

        for (int i = 0; i < wallList.size(); i++) {
            if (wallList.get(i).getX() == x && wallList.get(i).getY() == y) {
                return i;
            }
        }
        return location;
    }

    public int searchClosedList(int x, int y) {
        int location = -1;
        for (int i = 0; i < closedList.size(); i++) {
            if (closedList.get(i).getX() == x && closedList.get(i).getY() == y) {
                return i;
            }
        }
        return location;
    }

    public int searchOpenList(int x, int y) {
        int location = -1;
        for (int i = 0; i < openList.size(); i++) {
            if (openList.get(i).getX() == x && openList.get(i).getY() == y) {
                return i;
            }
        }
        return location;
    }


    public void findPathNow(Node start, Node end, Graphics g) throws InterruptedException {
        A:
        while (!openList.isEmpty()) {
            canMove = false;
            Node p = openList.peek();

            openList.pop();
            closedList.add(p);
            Thread.sleep(30);
            Node temp = new Node(Integer.MAX_VALUE, Integer.MAX_VALUE);
            temp.setF(Integer.MAX_VALUE);

            //checking if  the node has top

            if (p.getY() - 25 >= 0) {
                Node node = new Node(p.getX(), p.getY() - 25);

                if (node.getX() == end.getX() && end.getY() == node.getY()) {
                    node.setParent(p);
                    running = false;
                    lastNode = node;
                    System.out.println("node Found");
                    break A;
                } else if (searchClosedList(node.getX(), node.getY()) == -1 && searchWallList(node.getX(), node.getY()) == -1) {

                    node.setH(node.findDistance(node, end));
                    node.setG(p.getG() + 25);
                    node.setF(node.getG() + node.getH());

                    if (searchOpenList(node.getX(), node.getY()) != -1) {
                        int location = searchOpenList(node.getX(), node.getY());
                        if (openList.get(location).getF() > node.getF()) {
                            openList.get(location).setG(node.getG());
                            openList.get(location).setH(node.getH());
                            openList.get(location).setF(node.getF());
                        }
                    } else {

                        node.setParent(p);
                        openList.add(node);
                    }
                    canMove = true;
                }

            }

            //draw top left g cost which is how far away is the node from the starting node
            //draw middle which is the total f cost g+h cost
            //draw top right h cost which is distance from end node

            // checking if the node has bottom
            if (p.getY() + 25 <= 575) {
                Node node = new Node(p.getX(), p.getY() + 25);
                node.setH(node.findDistance(node, end));
                node.setG(p.getG() + 25);
                node.setF(node.getG() + node.getH());
                if (node.getX() == end.getX() && end.getY() == node.getY()) {
                    node.setParent(p);
                    running = false;
                    lastNode = node;
                    System.out.println("node Found");
                    break A;
                } else if (searchClosedList(node.getX(), node.getY()) == -1 && searchWallList(node.getX(), node.getY()) == -1) {
                    node.setH(node.findDistance(node, end));
                    node.setG(p.getG() + 25);
                    node.setF(node.getG() + node.getH());

                    if (searchOpenList(node.getX(), node.getY()) != -1) {
                        int location = searchOpenList(node.getX(), node.getY());
                        if (openList.get(location).getF() > node.getF()) {
                            openList.get(location).setG(node.getG());
                            openList.get(location).setH(node.getH());
                            openList.get(location).setF(node.getF());
                        }
                    } else {
                        node.setParent(p);
                        openList.add(node);
                    }
                    canMove = true;
                }
            }


            // checking if the node has left
            if (p.getX() - 25 >= 0) {

                Node node = new Node(p.getX() - 25, p.getY());

                node.setH(node.findDistance(node, end));
                node.setG(p.getG() + 25);
                node.setF(node.getG() + node.getH());
                if (node.getX() == end.getX() && end.getY() == node.getY()) {
                    node.setParent(p);
                    running = false;
                    lastNode = node;
                    System.out.println("node Found");
                    break A;
                } else if (searchClosedList(node.getX(), node.getY()) == -1 && searchWallList(node.getX(), node.getY()) == -1) {
                    node.setH(node.findDistance(node, end));
                    node.setG(p.getG() + 25);
                    node.setF(node.getG() + node.getH());

                    if (searchOpenList(node.getX(), node.getY()) != -1) {
                        int location = searchOpenList(node.getX(), node.getY());
                        if (openList.get(location).getF() > node.getF()) {
                            openList.get(location).setG(node.getG());
                            openList.get(location).setH(node.getH());
                            openList.get(location).setF(node.getF());
                        }
                    } else {
                        node.setParent(p);
                        openList.add(node);
                    }
                    canMove = true;

                }

            }


            // checking if the node has right

            if (p.getX() + 25 <= 675) {

                Node node = new Node(p.getX() + 25, p.getY());

                node.setH(node.findDistance(node, end));
                node.setG(p.getG() + 25);
                node.setF(node.getG() + node.getH());
                if (node.getX() == end.getX() && end.getY() == node.getY()) {
                    node.setParent(p);
                    running = false;
                    lastNode = node;
                    System.out.println("node Found");
                    break A;
                } else if (searchClosedList(node.getX(), node.getY()) == -1 && searchWallList(node.getX(), node.getY()) == -1) {
                    node.setH(node.findDistance(node, end));
                    node.setG(p.getG() + 25);
                    node.setF(node.getG() + node.getH());

                    if (searchOpenList(node.getX(), node.getY()) != -1) {
                        int location = searchOpenList(node.getX(), node.getY());
                        if (openList.get(location).getF() > node.getF()) {
                            openList.get(location).setG(node.getG());
                            openList.get(location).setH(node.getH());
                            openList.get(location).setF(node.getF());
                        }
                    } else {
                        node.setParent(p);
                        openList.add(node);
                    }
                    canMove = true;

                }

            }

            //checking for top left  diagonal
            if (p.getX() - 25 >= 0 && p.getY() - 25 >= 0) {

                Node node = new Node(p.getX() - 25, p.getY() - 25);

                node.setH(node.findDistance(node, end));
                node.setG(p.getG() + 25);
                node.setF(node.getG() + node.getH());
                if (node.getX() == end.getX() && end.getY() == node.getY()) {
                    node.setParent(p);
                    running = false;
                    lastNode = node;
                    System.out.println("node Found");
                    break A;
                } else if (searchClosedList(node.getX(), node.getY()) == -1 && searchWallList(node.getX(), node.getY()) == -1) {
                    node.setH(node.findDistance(node, end));
                    node.setG(p.getG() + 25);
                    node.setF(node.getG() + node.getH());

                    if (searchOpenList(node.getX(), node.getY()) != -1) {
                        int location = searchOpenList(node.getX(), node.getY());
                        if (openList.get(location).getF() > node.getF()) {
                            openList.get(location).setG(node.getG());
                            openList.get(location).setH(node.getH());
                            openList.get(location).setF(node.getF());
                        }
                    } else {
                        node.setParent(p);
                        openList.add(node);
                    }
                    canMove = true;

                }

            }


            //checking for top right diagonal
            if (p.getX() + 25 <= 675 && p.getY() - 25 >= 0) {

                Node node = new Node(p.getX() + 25, p.getY() - 25);
                node.setH(node.findDistance(node, end));
                node.setG(p.getG() + 25);
                node.setF(node.getG() + node.getH());
                if (node.getX() == end.getX() && end.getY() == node.getY()) {
                    node.setParent(p);
                    running = false;
                    lastNode = node;
                    System.out.println("node Found");
                    break A;
                } else if (searchClosedList(node.getX(), node.getY()) == -1 && searchWallList(node.getX(), node.getY()) == -1) {
                    node.setH(node.findDistance(node, end));
                    node.setG(p.getG() + 25);
                    node.setF(node.getG() + node.getH());

                    if (searchOpenList(node.getX(), node.getY()) != -1) {
                        int location = searchOpenList(node.getX(), node.getY());
                        if (openList.get(location).getF() > node.getF()) {
                            openList.get(location).setG(node.getG());
                            openList.get(location).setH(node.getH());
                            openList.get(location).setF(node.getF());
                        }
                    } else {
                        node.setParent(p);
                        openList.add(node);
                    }
                    canMove = true;

                }

            }

            //checking for bottom left diagonal
            if (p.getX() - 25 >= 0 && p.getY() + 25 <= 575) {

                Node node = new Node(p.getX() - 25, p.getY() + 25);

                node.setH(node.findDistance(node, end));
                node.setG(p.getG() + 25);
                node.setF(node.getG() + node.getH());
                if (node.getX() == end.getX() && end.getY() == node.getY()) {
                    node.setParent(p);
                    running = false;
                    lastNode = node;
                    System.out.println("node Found");
                    break A;
                } else if (searchClosedList(node.getX(), node.getY()) == -1 && searchWallList(node.getX(), node.getY()) == -1) {
                    node.setH(node.findDistance(node, end));
                    node.setG(p.getG() + 25);
                    node.setF(node.getG() + node.getH());

                    if (searchOpenList(node.getX(), node.getY()) != -1) {
                        int location = searchOpenList(node.getX(), node.getY());
                        if (openList.get(location).getF() > node.getF()) {
                            openList.get(location).setG(node.getG());
                            openList.get(location).setH(node.getH());
                            openList.get(location).setF(node.getF());
                        }
                    } else {
                        node.setParent(p);
                        openList.add(node);
                    }
                    canMove = true;

                }

            }

            //checking for bottom right diagonal
            if (p.getX() + 25 <= 675 && p.getY() + 25 <= 575) {

                Node node = new Node(p.getX() + 25, p.getY() + 25);

                node.setH(node.findDistance(node, end));
                node.setG(p.getG() + 25);
                node.setF(node.getG() + node.getH());
                if (node.getX() == end.getX() && end.getY() == node.getY()) {
                    node.setParent(p);
                    running = false;
                    lastNode = node;
                    System.out.println("node Found");
                    break A;
                } else if (searchClosedList(node.getX(), node.getY()) == -1 && searchWallList(node.getX(), node.getY()) == -1) {
                    node.setH(node.findDistance(node, end));
                    node.setG(p.getG() + 25);
                    node.setF(node.getG() + node.getH());

                    if (searchOpenList(node.getX(), node.getY()) != -1) {
                        int location = searchOpenList(node.getX(), node.getY());
                        if (openList.get(location).getF() > node.getF()) {
                            openList.get(location).setG(node.getG());
                            openList.get(location).setH(node.getH());
                            openList.get(location).setF(node.getF());
                        }
                    } else {
                        node.setParent(p);
                        openList.add(node);
                    }
                    canMove = true;
                }
            }
            openList = Sort(openList);

        }
        pathFound = canMove;
    }


    private LinkedList<Node> Sort(LinkedList<Node> openList) {
        int Switch = -1;
        Node temp;

        while (Switch != 0) {
            Switch = 0;
            for (int i = 0; i < openList.size() - 1; i++) {
                if (openList.get(i).getF() > openList.get(i + 1).getF()) {
                    temp = openList.get(i);
                    openList.remove(i);
                    openList.add(i + 1, temp);
                    Switch = 1;
                }
            }
        }
        return openList;
    }
}


