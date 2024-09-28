package AI;

import Entity.*;
import main.GamePanel;

import java.util.ArrayList;

public class PathFinder {

    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;
    private final int maxCol;
    private final int maxRow;


    public PathFinder(GamePanel gp) {
        this.gp = gp;
        maxCol = gp.getMaxMapCol();
        maxRow = gp.getMaxMapRow();
        instantiateNodes();
    }

    public void instantiateNodes() {

        node = new Node[maxCol][maxRow];

        int col = 0;
        int row = 0;

        while (col < maxCol && row < maxRow){

            node[col][row] = new Node(col, row);

            col++;
            if (col == maxCol){
                col = 0;
                row++;
            }
        }
    }

    public void resetNodes(){
        int col = 0;
        int row = 0;

        while (col < maxCol && row < maxRow){
            // Reset open, checked and solid state
            node[col][row].setOpen(false);
            node[col][row].setChecked(false);
            node[col][row].setSolid(false);

            col++;
            if (col == maxCol){
                col = 0;
                row++;
            }
        }

        // Reset other settings
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(LightUnit nearestPlayer, ChaosUnit currentEnemy){
        resetNodes();

        int startCol = currentEnemy.getCol();
        int startRow = currentEnemy.getRow();

        int goalCol = nearestPlayer.getCol();
        int goalRow = nearestPlayer.getRow();

        // Set Start and Goal nodes
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while(col < maxCol && row < maxRow){
            // Set Solid nodes
            if (!gp.cChecker.NonCollisionTile(col, row)) {
                node[col][row].setSolid(true);
            }

            // Check if the tile is occupied by a unit (player or enemy)
            if (isTileOccupiedByUnit(col, row, nearestPlayer, currentEnemy)) {
                // Allow the current enemy to pass through its current position
                if (!(col == startCol && row == startRow)) {
                    node[col][row].setSolid(true);
                }
            }

            // Set Cost
            setCost(node[col][row]);

            col++;
            if(col == maxCol){
                col = 0;
                row++;
            }
        }
    }

    // Helper method to check if a tile is occupied by a unit
    private boolean isTileOccupiedByUnit(int col, int row, LightUnit nearestPlayer, ChaosUnit currentEnemy) {
        // Check player units
        for (LightUnit player : gp.LightUnits) {
            if (player != nearestPlayer && player.getCol() == col && player.getRow() == row) {
                return true; // The tile is occupied by a player unit
            }
        }

        // Check other enemy units (excluding the current enemy unit being processed)
        for (ChaosUnit enemy : gp.ChaosUnits) {
            if (enemy != currentEnemy && enemy.getCol() == col && enemy.getRow() == row) {
                return true; // The tile is occupied by another enemy unit
            }
        }

        return false; // The tile is not occupied by any unit
    }

    public void setCost(Node node){

        // G Cost
        int xDistance = Math.abs(node.getCol() - startNode.getCol());
        int yDistance = Math.abs(node.getRow() - startNode.getRow());
        node.setgCost(xDistance + yDistance);

        // H Cost
        xDistance = Math.abs(node.getCol() - goalNode.getCol());
        yDistance = Math.abs(node.getRow() - goalNode.getRow());
        node.sethCost(xDistance + yDistance);

        // F Cost
        node.setfCost(node.getgCost() + node.gethCost());
    }

    public boolean search() {
        while (!goalReached && step < 999999999) {
            int col = currentNode.getCol();
            int row = currentNode.getRow();

            // Check current node
            currentNode.setChecked(true);
            openList.remove(currentNode);

            // Open the up node
            if (row - 1 >= 0) {
                openNode(node[col][row - 1]);
            }
            // Open the left node
            if (col - 1 >= 0) {
                openNode(node[col - 1][row]);
            }
            // Open the down node
            if (row + 1 < maxRow) {
                openNode(node[col][row + 1]);
            }
            // Open the right node
            if (col + 1 < maxCol) {
                openNode(node[col + 1][row]);
            }

            // Find the best node
            int bestNodeIndex = 0;
            int bestNodeFCost = 999;

            for (int i = 0; i < openList.size(); i++) {

                // Check if this node's F cost is better
                if (openList.get(i).getfCost() < bestNodeFCost) {
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).getfCost();
                }
                // If F Cost is equal, check the G Cost
                else if (openList.get(i).getfCost() == bestNodeFCost) {
                    if (openList.get(i).getgCost() < openList.get(bestNodeIndex).getgCost()) {
                        bestNodeIndex = i;
                    }
                }
            }

            // If there is no node in the openList, end the loop
            if (openList.size() == 0){
                break;
            }

            // After the loop, openList[bestNodeIndex] is the next step (= currentNode)
            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }

        return goalReached;
    }

    public void openNode(Node node){
        if (!node.isOpen() && !node.isChecked() && !node.isSolid()){
            node.setOpen(true);
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackThePath(){

        Node current = goalNode;

        while(current != startNode){
            pathList.add(0, current);
            current = current.parent;
        }
    }

}
