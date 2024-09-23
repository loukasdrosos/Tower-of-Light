package main;

import Entity.*;
import Item.*;
import java.util.Random;

public class AssetSetter {

    GamePanel gp;
    KeyHandler keyH;

    public AssetSetter(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
    }

    // Initialize player units and set their starting positions
    public void setLightUnits() {
        gp.LightUnits.add(new Prince(gp, keyH, "Alm", Entity.UnitRace.Human, 4, 47));
        gp.LightUnits.add(new Sage(gp, keyH, "Boey", Entity.UnitRace.Elf, 3, 48));
        gp.LightUnits.add(new Cleric(gp, keyH, "Shade", Entity.UnitRace.Elf, 4, 49));
        gp.LightUnits.add(new Knight(gp, keyH, "Rudolf", Entity.UnitRace.Elf, 2, 47));
        gp.LightUnits.add(new Knight(gp, keyH, "Berkut", Entity.UnitRace.Orc, 2, 49));
        gp.LightUnits.add(new Mage(gp, keyH, "Robin", Entity.UnitRace.Human, 6, 49));
        gp.LightUnits.add(new Paladin(gp, keyH, "Valbar", Entity.UnitRace.Tauren, 5, 48));
        gp.LightUnits.add(new Warrior(gp, keyH, "Ike", Entity.UnitRace.Human, 49, 49));
        gp.LightUnits.add(new DarkMage(gp, keyH, "Iago", Entity.UnitRace.Orc, 50, 50));
        gp.LightUnits.add(new Princess(gp, keyH, "Celica", Entity.UnitRace.Human, 11, 47));

        gp.tileM.findAllVisibleTiles();
    }

    // Initialize enemy units and set their starting positions
    public void setChaosUnits() {
        gp.ChaosUnits.clear();
        if (gp.getCurrentMap() == 0) {
            gp.ChaosUnits.add(new FallenHero(gp, 32, 16));
            gp.ChaosUnits.add(new FallenHero(gp, 28, 14));
            gp.ChaosUnits.add(new Titan(gp, false, 34, 15));
        }
        else if (gp.getCurrentMap() == 1) {
            gp.ChaosUnits.add(new FallenHero(gp, 32, 16));
            gp.ChaosUnits.add(new FallenHero(gp, 28, 14));
            gp.ChaosUnits.add(new Titan(gp, false, 34, 15));
        }
    }

    public void setCursor() {
        int startCursorCol = gp.LightUnits.get(0).getCol();
        int startCursorRow = gp.LightUnits.get(0).getRow();
        gp.cursor.setStartingPosition(startCursorCol, startCursorRow);
    }

    // Method to spawn enemies on valid, non-visible tiles
    public void spawnEnemies() {
        int activeBeacons = gp.TurnM.getActiveBeacons(); // Number of active Beacons of Light
        int minEnemies = 3 + (2 * activeBeacons); // Minimum number of enemies on the map
        int maxEnemies = 21 + (3 * activeBeacons); // Maximum number of enemies on the map
        Random rand = new Random();

        // Keep track of how many enemies currently exist
        int currentEnemies = gp.ChaosUnits.size();

        if (currentEnemies < minEnemies) {
            // Keep looping until we've spawned at least the minimum number of enemies
            while (currentEnemies < minEnemies) {
                // Randomly pick a tile on the map
                int targetCol = rand.nextInt(gp.getMaxMapCol());
                int targetRow = rand.nextInt(gp.getMaxMapRow());

                // Check if the tile is valid for spawning (not visible, no enemy, no player, no collision)
                if (!gp.tileM.visibleTiles[targetCol][targetRow] && gp.cChecker.validTile(targetCol, targetRow)) {
                    // Spawn the enemy type randomly based on the map and active Beacons
                    ChaosUnit enemy = spawnRandomEnemy(targetCol, targetRow, activeBeacons);
                    gp.ChaosUnits.add(enemy); // Add the enemy to the list of Chaos units
                    currentEnemies = gp.ChaosUnits.size();
                }
            }
            // If enemies are more than minimum but less than half of maximum, spawn 5 enemies
        } else if (currentEnemies >= minEnemies && currentEnemies < maxEnemies/2) {
            while (currentEnemies < maxEnemies/2) {
                // Randomly pick a tile on the map
                int targetCol = rand.nextInt(gp.getMaxMapCol());
                int targetRow = rand.nextInt(gp.getMaxMapRow());

                // Check if the tile is valid for spawning (not visible, no enemy, no player, no collision)
                if (!gp.tileM.visibleTiles[targetCol][targetRow] && gp.cChecker.validTile(targetCol, targetRow)) {
                    // Spawn the enemy type randomly based on the map and active Beacons
                    ChaosUnit enemy = spawnRandomEnemy(targetCol, targetRow, activeBeacons);
                    gp.ChaosUnits.add(enemy); // Add the enemy to the list of Chaos units
                    currentEnemies = gp.ChaosUnits.size();
                }
            }
        }
    }

    // Method to spawn a random enemy based on the current map and active Beacons of Light
    private ChaosUnit spawnRandomEnemy(int col, int row, int activeBeacons) {
        ChaosUnit enemy = new FallenHero(gp, col, row); // Placeholder in case enemy would return null for some reason
        UtilityTool uTool = new UtilityTool();
        int random = uTool.getRandomNumber();

        if (gp.getCurrentMap() == 0) {
            if (random < 50) {
                enemy = new Titan(gp, false, col, row);
            } else {
                enemy = new FallenHero(gp, col, row);
            }
        }
        if (gp.getCurrentMap() == 1) {
            if (random <= 33) {
                enemy = new Titan(gp, false, col, row);
            } else if (random > 33 && random <= 67){
                enemy = new FallenHero(gp, col, row);
            } else if (random > 67){
                enemy = new Shaman(gp, col, row);
            }
        }
        if (gp.getCurrentMap() == 2) {
            if (gp.TurnM.getActiveBeacons() == 0) {
                if (random <= 33) {
                    enemy = new Titan(gp, false, col, row);
                } else if (random > 33 && random <= 67) {
                    enemy = new Assassin(gp, col, row);
                } else if (random > 67) {
                    enemy = new Shaman(gp, col, row);
                }
            } else {
                if (random <= 33) {
                    enemy = new ChaosKnight(gp, col, row);
                } else if (random > 33 && random <= 67){
                    enemy = new Assassin(gp, col, row);
                } else if (random > 67){
                    enemy = new Spirit(gp, col, row);
                }
            }
        }
        if (gp.getCurrentMap() == 3) {
            if (gp.TurnM.getActiveBeacons() <= 1) {
                if (random <= 33) {
                    enemy = new ChaosKnight(gp, col, row);
                } else if (random > 33 && random <= 67) {
                    enemy = new Assassin(gp, col, row);
                } else if (random > 67) {
                    enemy = new Shaman(gp, col, row);
                }
            } else {
                if (random <= 33) {
                    enemy = new ChaosKnight(gp, col, row);
                } else if (random > 33 && random <= 67){
                    enemy = new ChaosPaladin(gp, false, col, row);
                } else if (random > 67){
                    enemy = new Spirit(gp, col, row);
                }
            }
        }
        if (gp.getCurrentMap() == 4) {
            if (gp.TurnM.getActiveBeacons() <= 1) {
                if (random <= 33) {
                    enemy = new FireDragon(gp, false, col, row);
                } else if (random > 33 && random <= 67) {
                    enemy = new Spirit(gp, col, row);
                } else if (random > 67) {
                    enemy = new ChaosPaladin(gp, false, col, row);
                }
            } else {
                if (random <= 33) {
                    enemy = new FireDragon(gp, false, col, row);
                } else if (random > 33 && random <= 67){
                    enemy = new ChaosPaladin(gp, false, col, row);
                } else if (random > 67){
                    enemy = new ChaosDragon(gp, col, row);
                }
            }
        }
        if (gp.getCurrentMap() == 5) {
            if (random <= 25) {
                enemy = new FireDragon(gp, false, col, row);
            }  else if (random > 26 && random <= 50) {
                enemy = new ChaosKnight(gp, col, row);
            }  else if (random > 50 && random <= 75) {
                enemy = new ChaosPaladin(gp, false, col, row);
            }  else if (random > 75) {
                enemy = new ChaosDragon(gp, col, row);
            }
        }
        if (gp.getCurrentMap() == 6) {
            if (random <= 17) {
                enemy = new FireDragon(gp, false, col, row);
            }  else if (random > 17 && random <= 34) {
                enemy = new ChaosKnight(gp, col, row);
            }  else if (random > 34 && random <= 49) {
                enemy = new ChaosPaladin(gp, false, col, row);
            }  else if (random > 49 && random <= 67) {
                enemy = new ChaosDragon(gp, col, row);
            }  else if (random > 67 && random <= 83) {
                enemy = new Assassin(gp, col, row);
            }  else if (random > 83) {
                enemy = new Spirit(gp, col, row);
            }
        }

        return enemy;
    }
}

