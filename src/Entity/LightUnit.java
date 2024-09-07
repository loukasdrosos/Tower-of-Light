package Entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class LightUnit extends Entity{
    KeyHandler keyH;

    protected int moveDelayThreshold = 7; // Number of frames to wait before moving

    protected boolean isSelected = false;  // Track if the unit is selected
    protected boolean isMoving = false;  // Track if the unit is moving

    public LightUnit (GamePanel gp, KeyHandler keyH, int startCol, int startRow) {
        super(gp);
        this.keyH = keyH;       // Reference to the key handler
        this.col =  startCol;   // Initial column position
        this.row = startRow;    // Initial row position
        x = getX(col);          // Calculate initial x position based on column
        y = getY(row);          // Calculate initial y position based on row
        preCol = col;           // Set previous column to current column
        preRow = row;           // Set previous row to current row
        setupStats();
        setupGrowthRates();

        // Load unit's images for animations
        try { loadImage(); }
        catch (Exception e){
            System.out.println("Exception loadImage, LightUnit not loading properly");
        }
    }

    // Method to set up the unit's stats
    @Override
    public void setupStats() {
        name = "Alm";
        className = "Prince";
        level = 1;
        exp = 0;
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
        description = new String[]{"The prince of ", "the kingdom of", "Valentia and the", "one worthy to wield", "the divine blade", "Lightbringer."} ;
    }

    // Method to set up the player's growth rates
    @Override
    public void setupGrowthRates() {
        HPGrowthRate = 75;
        strengthGrowthRate = 65;
        magicGrowthRate = 35;
        skillGrowthRate = 60;
        speedGrowthRate = 65;
        luckGrowthRate = 50;
        defenseGrowthRate = 45;
        resistanceGrowthRate = 25;
        // Total Growth Rates = 420
    }

    // Method to select a player unit (LightUnit) based on the cursor's position
    public void SelectPlayerUnit() {
        if (keyH.isAPressed()) {
            // Check if a player is currently selected
            if (gp.selectedUnit == null) {
                // Check if the cursor's position matches the position of this player unit (LightUnit)
                if (gp.cursor.getCol() == this.col && gp.cursor.getRow() == this.row && !this.wait) {
                    gp.selectedUnit = this; // Select player unit
                    isSelected = true; //Activate the selected player unit
                    isMoving = true; // Allow player to move
                    gp.playSE(5);
                }
            }
        }
    }

    // Method to deselect the currently selected player unit and reset its position
    public void UnselectPlayerUnit() {
        if (keyH.isZPressed()) {
            // Only proceed if a unit is selected, is marked as selected, and is currently moving
            if (gp.selectedUnit != null && isSelected && isMoving) {
                resetPosition(); // Return player to starting position
                gp.selectedUnit = null; // Deselect the player
                gp.playSE(6);
            }
        }
    }

    // Method to end the turn of the currently selected player unit
    public void endSelectedUnitTurn() {
        if (keyH.isWPressed()) {
            // Only proceed if a unit is selected, is marked as selected, and is currently moving
            if (gp.selectedUnit != null && isSelected && !wait && isMoving && gp.cChecker.noPlayerOnTile(col, row)) {
                endTurn(); // End player's turn
                gp.selectedUnit = null; // Deselect the player
            }
        }
    }

    /* Calculate all valid tiles the unit can move to within its movement range with the use of Breadth-First-Search (BFS)
    BFS is well-suited for this scenario because explores all possible moves level by level, which means it considers
    all closer tiles before moving on to further ones. This is useful in grid-based games where movement range is limited */
    public List<int[]> calculateValidMoves() {
        List<int[]> validMoves = new ArrayList<>();

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
            validMoves.add(new int[]{currentCol, currentRow});

            // Checks all possible movement directions (up, down, left, right) from the current tile
            int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            for (int[] dir : directions) {
                int newCol = currentCol + dir[0];
                int newRow = currentRow + dir[1];
                int newDistance = currentDistance + 1;

                // Check if the new tile is within the map bounds, not visited, within movement range, not wall and not occupied by an enemy unit
                if (gp.cChecker.isWithinMap(newCol, newRow) && !visited[newCol][newRow] &&
                        newDistance <= movement && gp.cChecker.NonCollisionTile(newCol, newRow) && gp.cChecker.noEnemyOnTile(newCol, newRow)) {
                    // If the tile is valid, add it to the queue to be explored
                    queue.add(new int[]{newCol, newRow, newDistance});
                    visited[newCol][newRow] = true; // Mark the tile as visited
                }
            }
        }
        return validMoves; // Return the list of all valid move tiles
    }

    @Override
    public void move() {
        // Check if the unit is not in a waiting state, is selected, and is allowed to move
        if (gp.selectedUnit != null && !wait && isSelected && isMoving) {
            moveDelayCounter++; // Increment the move delay counter

            // Check if the delay counter has reached or exceeded the move delay threshold
            if (moveDelayCounter >= moveDelayThreshold) {
                moveDelayCounter = 0; // Reset the move delay counter

                // Calculate all possible valid moves from the current position
                List<int[]> validMoves = calculateValidMoves();

                // Initialize the target position with the current position
                int targetCol = col;
                int targetRow = row;

                // Determine the intended move direction based on key presses
                if (keyH.isUpPressed()) {
                    direction = "up";
                } else if (keyH.isDownPressed()) {
                    direction = "down";
                } else if (keyH.isLeftPressed()) {
                    direction = "left";
                } else if (keyH.isRightPressed()) {
                    direction = "right";
                } else if (!keyH.isUpPressed() && !keyH.isDownPressed() &&
                        !keyH.isLeftPressed() && !keyH.isRightPressed()) {
                    direction = "none"; // If no key is pressed, stop movement
                }

                // Update target position based on the direction
                switch (direction) {
                    case "up":
                        targetRow = row - 1; // Move up
                        break;
                    case "down":
                        targetRow = row + 1; // Move down
                        break;
                    case "left":
                        targetCol = col - 1; // Move left
                        break;
                    case "right":
                        targetCol = col + 1; // Move right
                        break;
                }

                // Check if the target position is within the list of valid moves
                boolean canMove = false;
                for (int[] move : validMoves) {
                    if (move[0] == targetCol && move[1] == targetRow) {
                        canMove = true; // The move is valid
                        break;
                    }
                }

                // If the move is valid and the unit is actually moving to a new position
                if (canMove && (col != targetCol || row != targetRow)) {
                    // Update column and row positions
                    col = targetCol;
                    row = targetRow;
                    updatePosition(); // Update pixel position based on the new tile position

                    // Play the movement sound effect only if the position has changed
                    if (mounted) {
                        gp.playSE(8);
                    } else if (armored) {
                        gp.playSE(7);
                    } else {
                        gp.playSE(4);
                    }
                }
            }
        }
    }

    // Method to start the unit's turn, enabling actions
    @Override
    public void startTurn() {
        wait = false;
    }

    // Method to end the unit's turn, disabling actions and resetting state
    @Override
    public void endTurn() {
        isMoving = false;   // Stop the unit's movement
        wait = true;        // Set the unit to a waiting state
        preCol = col;       // Update the previous column to the current column
        preRow = row;       // Update the previous row to the current row
        direction = "none"; // Reset the direction
        isSelected = false; // Deselect the unit
    }

    // Method to reset the unit's position to the previous position
    public void resetPosition() {
        isMoving = false;     // Stop the unit's movement
        col = preCol;   // Revert to the previous column
        row = preRow;   // Revert to the previous row
        x = getX(col);  // Update the x position in pixels
        y = getY(row);  // Update the y position in pixels
        direction = "none"; // Reset the direction
        isSelected = false;   // Deselect the unit
    }

    // Method to update the unit's state, called every frame
    @Override
    public void update() {
        // Allow movement only during the player's phase
        if (gp.TurnM.getPlayerPhase()) {
            move();
            SelectPlayerUnit();
            UnselectPlayerUnit();
            endSelectedUnitTurn();
        }

        // Update sprite animation
        spriteCounter++;
        // Toggle between sprite frames
        if (spriteCounter > 20) {
            if (spriteNum == 1) {
                spriteNum = 2;
            }
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;  // Reset the sprite counter
        }
    }

    //Load images for the unit's animations
    @Override
    public void loadImage() {
        up1 = setup("/LightUnits/Prince/Human_Prince_Up_1");
        up2 = setup("/LightUnits/Prince/Human_Prince_Up_2");
        down1 = setup("/LightUnits/Prince/Human_Prince_Down_1");
        down2 = setup("/LightUnits/Prince/Human_Prince_Down_2");
        left1 = setup("/LightUnits/Prince/Human_Prince_Left_1");
        left2 = setup("/LightUnits/Prince/Human_Prince_Left_2");
        right1 = setup("/LightUnits/Prince/Human_Prince_Right_1");
        right2 = setup("/LightUnits/Prince/Human_Prince_Right_2");
        default1 = setup("/LightUnits/Prince/Human_Prince_Default_1");
        default2 = setup("/LightUnits/Prince/Human_Prince_Default_2");
        portrait = setupPortrait("/LightUnits/Prince/Human_Prince_Portrait");
    }

    // Draw Light Unit Window
    public void drawLightUnitScreen(Graphics2D g2) {
        int x = 52 * 16;
        int y = 5 * 16;
        int width = 15 * 16 + 12;
        int height = 31 * 16 + 10;
        g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        g2.fillRoundRect(x, y, width, height, 25, 25);
    }

    // Draw Light Unit Portrait
    public void drawLightUnitPortrait(Graphics2D g2) {
        BufferedImage image = null;
        int x = 52 * 16;
        int y = 5 * 16;
        int width = 8 * gp.getTileSize();
        int height = 8 * gp.getTileSize();

        drawLightUnitScreen(g2);
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

    // Draw Light Unit's stats
    public void drawLightUnitStats(Graphics2D g2) {
        // Coordinates for text next to the portrait
        int textX = 52 * 16 + 8 * gp.getTileSize() + 10; // Right side of the portrait
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
        g2.drawString("Exp: " + exp + "/" + maxExp, textX, textY + 4 * lineHeight);

        // Coordinates for stats below the portrait
        int statsX = 52 * 16 + 5; // Align with the portrait's X position
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

    public void drawLightUnitDetails (Graphics2D g2) {
        int textX = 52 * 16 + 8 * gp.getTileSize() + 4; // Right side of the portrait
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


    // Method to draw the Light Unit on the screen
    @Override
    public void draw(Graphics2D g2) {
        drawUnitAnimation(g2);

        if (gp.TurnM.getPlayerPhase()) {
            if (gp.selectedUnit == null) {
                if (gp.cursor.getCol() == col && gp.cursor.getRow() == row) {
                    drawLightUnitPortrait(g2);
                    if (keyH.isQPressed()) {
                        drawLightUnitStats(g2);
                    } else {
                        drawLightUnitDetails(g2);
                    }
                }
            } else if (gp.selectedUnit != null && isSelected) {
                drawLightUnitPortrait(g2);
                if (keyH.isQPressed()) {
                    drawLightUnitStats(g2);
                } else {
                    drawLightUnitDetails(g2);
                }
            }
        }
    }


    // Getters && Setters

    public boolean getIsSelected () { return isSelected; } // Return whether the unit is selected

    public void setIsSelected (boolean x) { this.isSelected = x; } // Set whether the unit is selected

    public boolean getIsMoving () { return isMoving; } // Return whether the unit is moving

    public void setIsMoving(boolean x) { this.isMoving = x; } // Set whether the unit is moving
}