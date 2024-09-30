package AI;

public class Node {

    Node parent; //Parent node
    private int col; // Column position of the node
    private int row; // Row position of the node
    private int gCost; // G cost: distance from the start node to this node
    private int hCost; // H cost: heuristic estimate from this node to the goal node
    private int fCost; // F cost: the sum of gCost and hCost in A* pathfinding
    private boolean solid; // Indicates if this node is solid
    private boolean open;  // Marks if the node is currently in the open list for pathfinding
    private boolean checked; // Marks if the node has already been checked during pathfinding

    public Node(int col, int row){
        this.col = col;
        this.row = row;
    }

    // GETTERS & SETTERS

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getgCost() {
        return gCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

    public int gethCost() {
        return hCost;
    }

    public void sethCost(int hCost) {
        this.hCost = hCost;
    }

    public int getfCost() {
        return fCost;
    }

    public void setfCost(int fCost) {
        this.fCost = fCost;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
