package main;

import Entity.*;
import java.util.Random;

public class AssetSetter {

    GamePanel gp;
    KeyHandler keyH;
    private int enemyLevel = 1;

    public AssetSetter(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
    }

    // Initialize player units and set their starting positions
    public void setLightUnits() {
        if (gp.getCurrentMap() == 0) {
            gp.LightUnits.add(new Prince(gp, keyH, "Alm", Entity.UnitRace.Human, 4, 47));
            gp.LightUnits.add(new Sage(gp, keyH, "Boey", Entity.UnitRace.Elf, 3, 48));
            gp.LightUnits.add(new Cleric(gp, keyH, "Shade", Entity.UnitRace.Elf, 4, 49));
            gp.LightUnits.add(new Knight(gp, keyH, "Rudolf", Entity.UnitRace.Elf, 2, 47));
            gp.LightUnits.add(new Knight(gp, keyH, "Berkut", Entity.UnitRace.Orc, 2, 49));
            gp.LightUnits.add(new Mage(gp, keyH, "Robin", Entity.UnitRace.Human, 6, 49));
            gp.LightUnits.add(new Paladin(gp, keyH, "Valbar", Entity.UnitRace.Tauren, 5, 48));
        }
        else if (gp.getCurrentMap() != 0) {
            gp.LightUnits.getFirst().setNextLevel(4, 47);
            if (gp.LightUnits.get(1) != null) {
                gp.LightUnits.get(1).setNextLevel(3, 48);
            }
            if (gp.LightUnits.get(2) != null) {
                gp.LightUnits.get(2).setNextLevel(4, 49);
            }
            if (gp.LightUnits.get(3) != null) {
                gp.LightUnits.get(3).setNextLevel(2, 47);
            }
            if (gp.LightUnits.get(4) != null) {
                gp.LightUnits.get(4).setNextLevel(2, 49);
            }
            if (gp.LightUnits.get(5) != null) {
                gp.LightUnits.get(5).setNextLevel(6,49);
            }
            if (gp.LightUnits.get(6) != null) {
                gp.LightUnits.get(6).setNextLevel(5, 48);
            }
            if (gp.LightUnits.get(7) != null) {
                gp.LightUnits.get(7).setNextLevel(6, 47);
            }
            if (gp.LightUnits.get(8) != null) {
                gp.LightUnits.get(8).setNextLevel(7, 48);
            }
            if (gp.LightUnits.get(9) != null) {
                gp.LightUnits.get(9).setNextLevel(8, 49);
            }

            if (gp.getCurrentMap() == 2) {
                gp.LightUnits.add(new Warrior(gp, keyH, "Ike", Entity.UnitRace.Human, 49, 49));
                gp.LightUnits.add(new DarkMage(gp, keyH, "Iago", Entity.UnitRace.Orc, 50, 50));
                gp.ui.addLogMessage("");
                gp.ui.addLogMessage("Ike: Unbelievable, we are lost");
                gp.ui.addLogMessage("Iago: Keep it quiet will you! We need to find the prince and the others");
                gp.ui.addLogMessage("Alm: Phew, we made it to the fourth floor");
                gp.ui.addLogMessage("Ike: Hey Iago, did you hear that?");
                gp.ui.addLogMessage("Iago: Could it be? Prince Alm is that you?");
                gp.ui.addLogMessage("Alm: Iago?");
                gp.ui.addLogMessage("Ike: Hey, i am also here!");
                gp.ui.addLogMessage("Iago: We will get to you young prince, don't worry");
                gp.ui.addLogMessage("Alm: Be careful, especially you Ike");
            }
        }

        gp.tileM.findAllVisibleTiles();
    }

    // Initialize enemy units and set their starting positions
    public void setChaosUnits() {
        gp.ChaosUnits.clear();
        int activeBeacons = gp.TurnM.getActiveBeacons(); // Number of active Beacons of Light
        int currentEnemies = gp.ChaosUnits.size();


        if (gp.getCurrentMap() == 6) {
            spawnBosses();
            gp.ui.addLogMessage("");
            gp.ui.addLogMessage("Grima: You made it here in the end");
            for (LightUnit lightUnit : gp.LightUnits) {
                if (lightUnit.getFinalMapQuote() != null) {
                    gp.ui.addLogMessage(lightUnit.getName() + ": " + lightUnit.getFinalMapQuote());
                }
            }
            gp.ui.addLogMessage("Grima: HAHAHAHA! I will be the one to rule this world!");
            gp.ui.addLogMessage("Grima: You will all perish here!");
            gp.ui.addLogMessage("Alm: Be careful Celica!");
            gp.ui.addLogMessage("Celica: Same goes to you Alm!");
        }

        Random rand = new Random();
        while (currentEnemies <= 10) {
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

    public void setMusic() {
        if (gp.getCurrentMap() == 0) {
            if (gp.TurnM.getActiveBeacons() == 0) {
                gp.playMusic(2);
            } else if (gp.TurnM.getActiveBeacons() == 3){
                gp.playMusic(3);
            }
        }
        if (gp.getCurrentMap() == 1) {
            if (gp.TurnM.getActiveBeacons() == 0) {
                gp.playMusic(2);
            } else if (gp.TurnM.getActiveBeacons() == 3) {
                gp.playMusic(4);
            }
        }
        if (gp.getCurrentMap() == 2) {
            if (gp.TurnM.getActiveBeacons() == 0) {
                gp.playMusic(5);
            } else if (gp.TurnM.getActiveBeacons() == 3) {
                gp.playMusic(3);
            }
        }
        if (gp.getCurrentMap() == 3) {
            if (gp.TurnM.getActiveBeacons() == 0) {
                gp.playMusic(5);
            } else if (gp.TurnM.getActiveBeacons() == 3) {
                gp.playMusic(6);
            }
        }
        if (gp.getCurrentMap() == 4) {
            if (gp.TurnM.getActiveBeacons() == 0) {
                gp.playMusic(7);
            } else if (gp.TurnM.getActiveBeacons() == 3) {
                gp.playMusic(8);
            }
        }
        if (gp.getCurrentMap() == 5) {
            if (gp.TurnM.getActiveBeacons() == 0) {
                gp.playMusic(7);
            } else if (gp.TurnM.getActiveBeacons() == 3) {
                gp.playMusic(9);
            }
        }
        if (gp.getCurrentMap() == 6) {
            gp.playMusic(0);
        }
    }

    public void addPrincess(int col, int row) {
        gp.LightUnits.add(new Princess(gp, keyH, "Celica", Entity.UnitRace.Human, col, row));
        gp.tileM.findAllVisibleTiles();
    }

    public void setNextMap() {
        gp.selectedUnit = null;
        gp.setNextMap();
        setMusic();
        increaseEnemyLevel();
        gp.TurnM.resetBeaconsofLight();
        gp.TurnM.resetBeaconCooldownTimer();
        gp.tileM.resetBeaconOfLightTiles();
        gp.tileM.resetItems();
        setLightUnits();
        setChaosUnits();
        setCursor();
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
            while (currentEnemies < minEnemies + 4) {
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
            gp.ui.addLogMessage("More enemies appeared");
        }
        // If enemies are more than minimum but less than half of maximum, spawn maxEnemies/2 + 5 enemies
        else if (currentEnemies >= minEnemies && currentEnemies <= maxEnemies/2) {
            while (currentEnemies < maxEnemies/2 + 5) {
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
            gp.ui.addLogMessage("More enemies appeared");
        }
    }

    public void spawnBosses() {
        Random rand = new Random();

        // Randomly pick a tile on the map
        int col = rand.nextInt(gp.getMaxMapCol());
        int row = rand.nextInt(gp.getMaxMapRow());

        while (!gp.tileM.visibleTiles[col][row] && !gp.cChecker.validTile(col, row)) {
            // Randomly pick a tile on the map
            col = rand.nextInt(gp.getMaxMapCol());
            row = rand.nextInt(gp.getMaxMapRow());
        }

        ChaosUnit enemy;
        switch (gp.getCurrentMap()) {
            case 0:
                enemy = new FireDragon(gp, true, col, row);
                break;
            case 1:
                enemy = new Necromancer(gp, col, row);
                break;
            case 2:
                enemy = new FireDragon(gp, true, col, row);
                break;
            case 3:
                enemy = new ChaosPaladin(gp, true, col, row);
                break;
            case 4:
                enemy = new Sorcerer(gp, col, row);
                break;
            case 5:
                enemy = new HeraldOfChaos(gp, col, row);
                break;
            case 6:
                enemy = new ChaosGod(gp, 26, 4);
                break;
            default:
                enemy = new FallenHero(gp, col, row); // Default enemy type
        }

        // Spawn the boss randomly based on the map
        gp.ChaosUnits.add(enemy); // Add the enemy to the list of Chaos units
        gp.ui.addLogMessage("Boss appeared");
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

    // ENEMY LEVEL

    public int getEnemyLevel() {
        return enemyLevel;
    }

    public void increaseEnemyLevel() {
        if (enemyLevel < new Entity(gp).getMaxLevel()) {
            enemyLevel++;
        }
    }
}

