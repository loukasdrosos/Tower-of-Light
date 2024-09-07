package Entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ChaosUnit extends Entity {

    protected int moveDelayThreshold = 7; // Number of frames to wait before moving
    protected int moveSpeed = 8; // Speed at which the unit moves between tiles

    protected boolean boss;
    protected boolean canMove;

    public ChaosUnit(GamePanel gp, int startCol, int startRow) {
        super(gp);
        this.col = startCol;   // Initial column position
        this.row = startRow;    // Initial row position
        x = getX(col);          // Calculate initial x position based on column
        y = getY(row);          // Calculate initial y position based on row
        preCol = col;           // Set previous column to current column
        preRow = row;           // Set previous row to current row
        setupStats();

        // Load unit's images for animations
        try {
            loadImage();
        } catch (Exception e) {
            System.out.println("Exception loadImage, ChaosUnit not loading properly");
        }
    }

    // Method to set up the unit's stats
    @Override
    public void setupStats() {
        name = "Celica";
        className = "Herald of Chaos";
        level = 1;
        MaxHP = 20;
        HP = MaxHP;
        strength = 10;
        magic = 5;
        skill = 7;
        speed = 8;
        luck = 6;
        defense = 6;
        resistance = 4;
        movement = 3;
        type = UnitType.Human;
        armored = false;
        mounted = false;
        description = new String[]{"Princess of Valentia", "and Alm's wife.", "Controlled by the", "Chaos God, she has" , "become the Herald",  "of Chaos."};
    }

    // Method to start the unit's turn (called at the beginning of its turn)
    @Override
    public void startTurn() {
        wait = false; // Allows the unit to perform actions
    }

    // Method to end the unit's turn (called at the end of its turn)
    @Override
    public void endTurn() {
        wait = true;    // Prevents the unit from performing actions
        preCol = col;   // Save the current column as the previous column
        preRow = row;   // Save the current row as the previous row
        direction = "none";  // Reset the movement direction
    }

    /* Calculate all valid tiles the unit can move to within its movement range with the use of Breadth-First-Search (BFS)
    BFS is well-suited for this scenario because explores all possible moves level by level, which means it considers
    all closer tiles before moving on to further ones. This is useful in grid-based games where movement range is limited */
    public List<int[]> calculateMovementRange() {
        List<int[]> movementRange = new ArrayList<>();

        /* Use a queue for breadth-first search (BFS) to explore tiles within the movement range
        This queue will hold the tiles to be explored, with each tile being represented by its column, row,
        and the distance from the starting position */
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{preCol, preRow, 0}); // Start from the current position with 0 distance traveled

        // Track visited tiles to prevent revisiting the same tile
        boolean[][] visited = new boolean[gp.getMaxMapCol()][gp.getMaxMapRow()];
        visited[preCol][preRow] = true; // Mark the starting position as visited

        // Continue exploring tiles until there are no more to explore
        while (!queue.isEmpty()) {
            int[] current = queue.poll(); // Get the current tile from the front of the queue
            int currentCol = current[0];
            int currentRow = current[1];
            int currentDistance = current[2];

            // Add the current tile as a valid move option
            movementRange.add(new int[]{currentCol, currentRow});

            // Checks all possible movement directions (up, down, left, right) from the current tile
            int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            for (int[] dir : directions) {
                int newCol = currentCol + dir[0];
                int newRow = currentRow + dir[1];
                int newDistance = currentDistance + 1;

                // Check if the new tile is within the map bounds, not visited, within movement range, not wall and not occupied by a player unit
                if (gp.cChecker.isWithinMap(newCol, newRow) && !visited[newCol][newRow] &&
                        newDistance <= movement && gp.cChecker.NonCollisionTile(newCol, newRow) && gp.cChecker.noPlayerOnTile(newCol, newRow)) {
                    // If the tile is valid, add it to the queue to be explored
                    queue.add(new int[]{newCol, newRow, newDistance});
                    visited[newCol][newRow] = true; // Mark the tile as visited
                }
            }
        }
        return movementRange; // Return the list of all valid move tiles
    }

    @Override
    public void move() {
        // If enemy phase
        if (!gp.TurnM.getPlayerPhase()) {
            // If the unit is allowed to move
            if (!wait) {
                endTurn();
            }
        }
    }


/*
    //WRONG MOVEMENT METHOD
    // Method to handle the unit's movement logic
    @Override
    public void move() {
        if (!wait) { // If the unit is allowed to move
            moveDelayCounter++;  // Increment the delay counter

            // Only move if the counter reaches the threshold
            if (moveDelayCounter >= moveDelayThreshold) {
                moveDelayCounter = 0; // Reset the counter after moving

                // Determine the direction to move
                if (gp.cChecker.isWithinMap(col, row - 1) && gp.cChecker.validTile(col, row - 1)) {
                    direction = "up";
                } else if (gp.cChecker.isWithinMap(col + 1, row) && gp.cChecker.validTile(col + 1, row)) {
                    direction = "right";
                } else if (gp.cChecker.isWithinMap(col, row + 1) && gp.cChecker.validTile(col, row + 1)) {
                    direction = "down";
                } else if (gp.cChecker.isWithinMap(col - 1, row) && gp.cChecker.validTile(col - 1, row)) {
                    direction = "left";
                }

                // Determine target position based on direction
                int targetCol = col;
                int targetRow = row;

                switch (direction) {
                    case "up":
                        targetRow = row - 1;
                        break;
                    case "down":
                        targetRow = row + 1;
                        break;
                    case "left":
                        targetCol = col - 1;
                        break;
                    case "right":
                        targetCol = col + 1;
                        break;
                }

                if (targetCol == col && targetRow == row) {
                    endTurn();
                }

                // Update the ChaosUnit's position gradually
                if (targetCol != col || targetRow != row) {
                    int targetX = getX(targetCol); // Target x position
                    int targetY = getY(targetRow); // Target y position

                    // Gradually move towards the target x position
                    if (x < targetX) {
                        x += moveSpeed;
                        if (x > targetX) x = targetX;
                    } else if (x > targetX) {
                        x -= moveSpeed;
                        if (x < targetX) x = targetX;
                    }

                    // Gradually move towards the target y position
                    if (y < targetY) {
                        y += moveSpeed;
                        if (y > targetY) y = targetY;
                    } else if (y > targetY) {
                        y -= moveSpeed;
                        if (y < targetY) y = targetY;
                    }

                    // Check if the unit has reached the new tile
                    if (x == targetX && y == targetY) {
                        col = targetCol;    // Update the unit's column
                        row = targetRow;    // Update the unit's row
                        updatePosition();   // Update the col and row based on the new position
                        endTurn();          // End the turn after moving
                    }
                }
            }
        }
    }

 */

    // Method to update the unit's state (called every frame)
    @Override
    public void update() {

        // Update the sprite animation
        spriteCounter++;  // Increment the sprite counter
        if (spriteCounter > 20) {   // Change sprite every 20 frames
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;  // Reset sprite counter
        }
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Up_1");
        up2 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Up_2");
        down1 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Down_1");
        down2 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Down_2");
        left1 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Left_1");
        left2 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Left_2");
        right1 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Right_1");
        right2 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Right_2");
        default1 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Default_1");
        default2 = setup("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Default_2");
        portrait = setupPortrait("/ChaosUnits/Herald_of_Chaos/Human_Herald_of_Chaos_Portrait");
    }

    // Draw Chaos Unit Window
    public void drawChaosUnitScreen(Graphics2D g2) {
        int x = 69 * 16 - 12;
        int y = 5 * 16;
        int width = 16 * 16 + 12;
        int height = 31 * 16 + 10;
        g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        g2.fillRoundRect(x, y, width, height, 25, 25);
    }

    // Draw Chaos Unit Portrait
    public void drawChaosUnitPortrait(Graphics2D g2) {
        BufferedImage image = null;
        int x = 69 * 16 - 12;
        int y = 5 * 16;
        int width = 8 * gp.getTileSize();
        int height = 8 * gp.getTileSize();

        drawChaosUnitScreen(g2);
        // Draw a semi-transparent background
        g2.setColor(new Color(0, 0, 0, 150)); // RGBA, 150 is the transparency
        g2.fillRect(x, y, width, height);

        // Draw the portrait image
        image = portrait;
        g2.drawImage(image, x, y, null);

        // Draw a thin white line on the right and bottom sides
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2)); // thin line
        // Right side line
        g2.drawLine(x + width - 1, y, x + width - 1, y + height - 1);
        // Bottom side line
        g2.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
    }

    // Draw Chaos Unit's stats
    public void drawChaosUnitStats(Graphics2D g2) {
        // Coordinates for text next to the portrait
        int textX = 69 * 16 + 7 * gp.getTileSize() + 9; // Right side of the portrait
        int textY = 5 * 16 + 20; // Slightly below the top of the portrait
        int lineHeight = 20; // Spacing between each line of text

        // Set font for text
        g2.setFont(new Font("Arial", Font.PLAIN, 17));
        g2.setColor(Color.WHITE);

        // Draw unit's name, class name, and level next to the portrait
        g2.drawString(name, textX, textY);
        g2.drawString(String.valueOf(type), textX, textY + lineHeight);
        if (armored) {
            g2.drawString(", Armored", textX + 5, textY + lineHeight);
        }
        if (mounted) {
            g2.drawString(", Mounted", textX + 5, textY + lineHeight);
        }
        g2.drawString(className, textX, textY + 2 * lineHeight);
        g2.drawString("Level: " + level, textX, textY + 3 * lineHeight);

        // Coordinates for stats below the portrait
        int statsX = 69 * 16 - 5; // Align with the portrait's X position
        int statsY = 5 * 16 + 8 * gp.getTileSize() + 20; // Start drawing below the portrait

        // Draw the unit's stats below the portrait
        g2.drawString("HP: " + HP + "/" + MaxHP, statsX, statsY);
        g2.drawString("Strength: " + strength, statsX, statsY + lineHeight);
        g2.drawString("Magic: " + magic, statsX, statsY + 2 * lineHeight);
        g2.drawString("Skill: " + skill, statsX, statsY + 3 * lineHeight);
        g2.drawString("Speed: " + speed, statsX, statsY + 4 * lineHeight);
        g2.drawString("Luck: " + luck, statsX, statsY + 5 * lineHeight);
        g2.drawString("Defense: " + defense, statsX, statsY + 6 * lineHeight);
        g2.drawString("Resistance: " + resistance, statsX, statsY + 7 * lineHeight);
        g2.drawString("Movement: " + movement, statsX, statsY + 8 * lineHeight);
    }

    public void drawChaosUnitDetails (Graphics2D g2) {
        int textX = 69 * 16 + 7 * gp.getTileSize() + 9; // Right side of the portrait
        int textY = 5 * 16 + 20; // Slightly below the top of the portrait
        int lineHeight = 20; // Spacing between each line of text

        // Set font for text
        g2.setFont(new Font("Arial", Font.PLAIN, 13));
        g2.setColor(Color.WHITE);

        // Draw unit's description line by line
        for (String line : description) {
            g2.drawString(line, textX, textY);
            textY += lineHeight;
        }

        /*
        // Draw unit's weapons and their stats/descriptions
        g2.drawString("Weapons:", textX, textY);
        textY += lineHeight;
        for (Weapon weapon : weapons) {
            g2.drawString(weapon.getName() + " (Damage: " + weapon.getDamage() + ", Range: " + weapon.getRange() + ")", textX, textY);
            textY += lineHeight;
            g2.drawString("  " + weapon.getDescription(), textX + 20, textY);
            textY += lineHeight;
        }

        // Draw unit's skills and their descriptions
        textY += lineHeight; // Add some space between weapons and skills
        g2.drawString("Skills:", textX, textY);
        textY += lineHeight;
        for (Skill skill : skills) {
            g2.drawString(skill.getName(), textX, textY);
            textY += lineHeight;
            g2.drawString("  " + skill.getDescription(), textX + 20, textY);
            textY += lineHeight;
        }

         */
    }

    // Method to draw the Chaos Unit on the screen
    @Override
    public void draw(Graphics2D g2) {
        drawUnitAnimation(g2);

        if (gp.TurnM.getPlayerPhase()) {
            if (gp.selectedUnit == null) {
                if (gp.cursor.getCol() == col && gp.cursor.getRow() == row) {
                    drawChaosUnitPortrait(g2);
                    if (gp.keyH.isQPressed()) {
                        drawChaosUnitStats(g2);
                    } else {
                        drawChaosUnitDetails(g2);
                    }
                }
            }
        }
    }
}
