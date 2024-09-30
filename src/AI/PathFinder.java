package AI;

import Entity.*;
import main.GamePanel;

import java.util.ArrayList;

public class PathFinder {

    GamePanel gp;
    Node[][] node;  // 2D array of nodes representing the map/grid for pathfinding
    ArrayList<Node> openList = new ArrayList<>(); // List of nodes to be evaluated in the pathfinding algorithm
    public ArrayList<Node> pathList = new ArrayList<>();  // List storing the final path from start to goal
    Node startNode, goalNode, currentNode; // Nodes representing the start, goal, and current position in the search
    boolean goalReached = false; // Flag indicating whether the goal has been reached
    int step = 0; // Step counter for the search process
    private final int maxCol; // Maximum number of columns in the grid
    private final int maxRow; // Maximum number of rows in the grid


    public PathFinder(GamePanel gp) {
        this.gp = gp;
        maxCol = gp.getMaxMapCol();
        maxRow = gp.getMaxMapRow();
        instantiateNodes();
    }

    // Initializes nodes for each tile on the map
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

    // Resets all nodes and pathfinding settings to prepare for a new search
    public void resetNodes(){
        int col = 0;
        int row = 0;

        // Loop through the map and reset node properties (open, checked, solid)
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

        // Clear open and path lists, reset goal flag and step count
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    // Sets up the start and goal nodes for pathfinding based on the nearest player and current enemy
    public void setNodes(LightUnit nearestPlayer, ChaosUnit currentEnemy){
        resetNodes(); // Reset node states before setting new nodes

        // Get the start (enemy) and goal (player) positions
        int startCol = currentEnemy.getCol();
        int startRow = currentEnemy.getRow();
        int goalCol = nearestPlayer.getCol();
        int goalRow = nearestPlayer.getRow();

        // Set Start and Goal nodes
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);  // Add the start node to the open list

        int col = 0;
        int row = 0;

        // Loop through the map to configure each node's state
        while(col < maxCol && row < maxRow){
            // Set Solid nodes
            if (!gp.cChecker.NonCollisionTile(col, row)) {
                node[col][row].setSolid(true);
            }

            // Mark the tile as solid if occupied by another unit (player or enemy)
            if (isTileOccupiedByUnit(col, row, nearestPlayer, currentEnemy)) {
                // Allow enemy to stand on its own tile
                if (!(col == startCol && row == startRow)) {
                    node[col][row].setSolid(true);
                }
            }

            // Calculate costs for the node
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
        // Check player units except the nearest player
        for (LightUnit player : gp.LightUnits) {
            if (player != nearestPlayer && player.getCol() == col && player.getRow() == row) {
                return true; // The tile is occupied by a player unit
            }
        }

        // Check other enemy units except the current enemy
        for (ChaosUnit enemy : gp.ChaosUnits) {
            if (enemy != currentEnemy && enemy.getCol() == col && enemy.getRow() == row) {
                return true; // The tile is occupied by another enemy unit
            }
        }

        return false; // The tile is not occupied by any unit
    }

    // Sets the G, H, and F costs for a node used in pathfinding
    public void setCost(Node node){

        // G cost: Manhattan distance from the start node
        int xDistance = Math.abs(node.getCol() - startNode.getCol());
        int yDistance = Math.abs(node.getRow() - startNode.getRow());
        node.setgCost(xDistance + yDistance);

        // H cost: estimated Manhattan distance to the goal node
        xDistance = Math.abs(node.getCol() - goalNode.getCol());
        yDistance = Math.abs(node.getRow() - goalNode.getRow());
        node.sethCost(xDistance + yDistance);

        // F cost: sum of G and H costs
        node.setfCost(node.getgCost() + node.gethCost());
    }

    // Main pathfinding search method using the A* algorithm
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

    // Adds a node to the open list if it hasn't been opened, checked, or is not solid
    public void openNode(Node node){
        if (!node.isOpen() && !node.isChecked() && !node.isSolid()){
            node.setOpen(true);
            node.parent = currentNode;
            openList.add(node);
        }
    }

    // Traces back from the goal to the start to determine the final path
    public void trackThePath(){

        Node current = goalNode;

        // Follow parent nodes to track the complete path from goal to start
        while(current != startNode){
            pathList.add(0, current); // Add each node to the path in reverse order
            current = current.parent;
        }
    }

}
