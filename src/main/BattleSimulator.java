package main;

import Entity.*;

import java.awt.*;
import java.util.List;

public class BattleSimulator {

    GamePanel gp;
    Graphics2D g2;
    Font arial_15;
    int delay = 200; // Delay in milliseconds before each attack

    private String floatingText;
    private int floatingTextX;
    private int floatingTextY;
    private int duration = 0;

    private int originalX;
    private int originalY;

    private boolean battleInProgress = false;

    public BattleSimulator(GamePanel gp) {
        this.gp = gp;
    }

    // How battle work for player phase
    public void battlePlayerPhase(LightUnit player, ChaosUnit enemy) {
        battleInProgress = true;
        new Thread(() -> {
            try {
                Thread.sleep(delay);  // Half a second delay before player attacks

                UtilityTool uTool = new UtilityTool();
                boolean playerDefeated = false;
                boolean enemyDefeated = false;
                boolean playerMissed = false;

                int damagePlayer = calculateDamage(player, enemy);
                int hitPlayer = calculateHitChance(player, enemy);
                int critPlayer = calculateCriticalChance(player, enemy);

                int damageEnemy = 0;
                int hitEnemy = 0;
                int critEnemy = 0;

                // Player attacks if hit is successful
                if (uTool.getRandomNumber() <= hitPlayer) {
                    gp.ui.addLogMessage("");
                    // Critical hit check
                    if (uTool.getRandomNumber() <= critPlayer) {
                        damagePlayer *= 3; // Critical hits deal triple damage
                        if (damagePlayer > 0) {
                            gp.playSE(15);
                        }
                        else {
                            gp.playSE(16);
                        }
                        gp.ui.addLogMessage("It's a critical hit");
                    }
                    else {
                        if (damagePlayer > 0) {
                            gp.playSE(14);
                        }
                        else {
                            gp.playSE(16);
                        }
                    }
                    faceTarget(player, enemy);
                    dashForward(player);
                    getDamage(String.valueOf(damagePlayer), enemy.getX(), enemy.getY() - 2);
                    dashBack(player);
                    enemy.takeDamage(damagePlayer);
                    if (enemy.getName() != null) {
                        gp.ui.addLogMessage(player.getName() + " deals " + damagePlayer + " damage to " + enemy.getName());
                    } else if (enemy.getName() == null) {
                        gp.ui.addLogMessage(player.getName() + " deals " + damagePlayer + " damage to " + String.valueOf(enemy.getRace()) + " " + enemy.getClassName());
                    }

                    if (enemy.getHP() <= 0) {
                        enemyDefeated = true; // Enemy killed
                        enemy.Defeated();
                    }
                } else {
                    playerMissed = true;
                    faceTarget(player, enemy);
                    dashForward(player);
                    getDamage("MISS", enemy.getX() - 2, enemy.getY() - 2);
                    dashBack(player);
                    gp.playSE(17);
                    gp.ui.addLogMessage(player.getName() + " missed");
                }

                // Delay before enemy counterattack
                Thread.sleep(delay);

                // Enemy counterattacks if still alive and player is in its range
                if (!enemyDefeated && enemy.getHP() > 0) {
                    List<int[]> enemyRange = enemy.calculateStaticAttackRange();

                    // Check if player unit is in enemy unit's range
                    boolean inRange = uTool.containsTile(enemyRange, player.getCol(), player.getRow());
                    // If player is in enemy's range
                    if (inRange) {
                        damageEnemy = calculateDamage(enemy, player);
                        hitEnemy = calculateHitChance(enemy, player);
                        critEnemy = calculateCriticalChance(enemy, player);

                        if (uTool.getRandomNumber() <= hitEnemy) {
                            // Critical hit check
                            if (uTool.getRandomNumber() <= critEnemy) {
                                damageEnemy *= 3; // Critical hits deal triple damage
                                if (damageEnemy > 0) {
                                    gp.playSE(15);
                                }
                                else {
                                    gp.playSE(16);
                                }
                                gp.ui.addLogMessage("It's a critical hit");
                            }
                            else {
                                if (damageEnemy > 0) {
                                    gp.playSE(14);
                                }
                                else {
                                    gp.playSE(16);
                                }
                            }
                            faceTarget(enemy, player);
                            dashForward(enemy);
                            getDamage(String.valueOf(damageEnemy), player.getX(), player.getY() - 2);
                            dashBack(enemy);
                            player.takeDamage(damageEnemy);
                            gp.ui.addLogMessage(player.getName() + " took " + damageEnemy + " damage");

                            if (player.getHP() <= 0) {
                                playerDefeated = true; // Player unit killed
                                player.Defeated();
                            }
                        } else {
                            faceTarget(enemy, player);
                            dashForward(enemy);
                            getDamage("MISS", player.getX() - 2, player.getY() - 2);
                            dashBack(enemy);
                            gp.playSE(17);
                            if (enemy.getName() != null) {
                                gp.ui.addLogMessage(enemy.getName() + " missed the counterattack");
                            } else if (enemy.getName() == null) {
                                gp.ui.addLogMessage(String.valueOf(enemy.getRace()) + " " + enemy.getClassName() + " missed the counterattack");
                            }
                        }
                    }
                }

                // Check if player can double attack
                if (!playerDefeated && player.getHP() > 0) {
                    if (!enemyDefeated && player.getEffSpeed() - enemy.getEffSpeed() >= 5 && enemy.getHP() > 0) {
                        playerMissed = false;
                        Thread.sleep(delay);
                        // Player attacks if hit is successful
                        if (uTool.getRandomNumber() <= hitPlayer) {
                            // Critical hit check
                            if (uTool.getRandomNumber() <= critPlayer) {
                                damagePlayer *= 3; // Critical hits deal triple damage
                                if (damagePlayer > 0) {
                                    gp.playSE(15);
                                }
                                else {
                                    gp.playSE(16);
                                }
                                gp.ui.addLogMessage("It's a critical hit");
                            } else {
                                if (damagePlayer > 0) {
                                    gp.playSE(14);
                                }
                                else {
                                    gp.playSE(16);
                                }
                            }
                            faceTarget(player, enemy);
                            dashForward(player);
                            getDamage(String.valueOf(damagePlayer), enemy.getX(), enemy.getY() - 2);
                            dashBack(player);
                            enemy.takeDamage(damagePlayer);
                            if (enemy.getName() != null) {
                                gp.ui.addLogMessage(player.getName() + " deals " + damagePlayer + " damage to " + enemy.getName());
                            } else if (enemy.getName() == null) {
                                gp.ui.addLogMessage(player.getName() + " deals " + damagePlayer + " damage to " + String.valueOf(enemy.getRace()) + " " + enemy.getClassName());
                            }

                            if (enemy.getHP() <= 0) {
                                enemyDefeated = true; // Enemy killed
                                enemy.Defeated();
                            }
                        } else {
                            playerMissed = true;
                            faceTarget(player, enemy);
                            dashForward(player);
                            getDamage("MISS", enemy.getX() - 2, enemy.getY() - 2);
                            dashBack(player);
                            gp.playSE(17);
                            gp.ui.addLogMessage(player.getName() + " missed");
                        }
                    }
                }

                // Check if enemy can double attack
                if (!enemyDefeated && enemy.getHP() > 0) {
                    if (!playerDefeated && enemy.getEffSpeed() - player.getEffSpeed() >= 5 && player.getHP() > 0) {
                        Thread.sleep(delay);
                        List<int[]> enemyRange = enemy.calculateStaticAttackRange();

                        // Check if player unit is in enemy unit's range
                        boolean inRange = uTool.containsTile(enemyRange, player.getCol(), player.getRow());
                        // If player is in enemy's range
                        if (inRange) {
                            damageEnemy = calculateDamage(enemy, player);
                            hitEnemy = calculateHitChance(enemy, player);
                            critEnemy = calculateCriticalChance(enemy, player);

                            if (uTool.getRandomNumber() <= hitEnemy) {
                                // Critical hit check
                                if (uTool.getRandomNumber() <= critEnemy) {
                                    damageEnemy *= 3; // Critical hits deal triple damage
                                    if (damageEnemy > 0) {
                                        gp.playSE(15);
                                    }
                                    else {
                                        gp.playSE(16);
                                    }
                                    gp.ui.addLogMessage("It's a critical hit");
                                } else {
                                    if (damageEnemy > 0) {
                                        gp.playSE(14);
                                    }
                                    else {
                                        gp.playSE(16);
                                    }
                                }
                                faceTarget(enemy, player);
                                dashForward(enemy);
                                getDamage(String.valueOf(damageEnemy), player.getX(), player.getY() - 2);
                                dashBack(enemy);
                                player.takeDamage(damageEnemy);
                                gp.ui.addLogMessage(player.getName() + " took " + damageEnemy + " damage");

                                if (player.getHP() <= 0) {
                                    playerDefeated = true; // Player unit killed
                                    player.Defeated();
                                }
                            } else {
                                faceTarget(enemy, player);
                                dashForward(enemy);
                                getDamage("MISS", player.getX() - 2, player.getY() - 2);
                                dashBack(enemy);
                                gp.playSE(17);
                                if (enemy.getName() != null) {
                                    gp.ui.addLogMessage(enemy.getName() + " missed the counterattack");
                                } else if (enemy.getName() == null) {
                                    gp.ui.addLogMessage(String.valueOf(enemy.getRace()) + " " + enemy.getClassName() + " missed the counterattack");
                                }
                            }
                        }
                    }
                }

                // Experience calculation for player unit
                if (!playerDefeated && !playerMissed && damagePlayer > 0) {
                    boolean delay = false;
                    int experienceGained = calculateExperience(player, enemy, enemyDefeated, damagePlayer);
                    if (player.getExp() + experienceGained >= 100) {
                        delay = true;
                    }
                    player.gainExperience(experienceGained);
                    if (delay) {
                        Thread.sleep(2000);
                    }
                }

                battleInProgress = false; // Mark battle as finished
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // How battles work for enemy phase
    public void battleEnemyPhase(LightUnit player, ChaosUnit enemy) {
        battleInProgress = true;
        new Thread(() -> {
            try {
                Thread.sleep(delay);  // Half a second delay before player attacks
                UtilityTool uTool = new UtilityTool();
                boolean playerDefeated = false;
                boolean enemyDefeated = false;
                boolean playerMissed = false;

                int damageEnemy = calculateDamage(enemy, player);
                int hitEnemy = calculateHitChance(enemy, player);
                int critEnemy = calculateCriticalChance(enemy, player);

                int damagePlayer = 0;
                int hitPlayer = 0;
                int critPlayer = 0;

                // Enemy attacks if hit is successful
                if (uTool.getRandomNumber() <= hitEnemy) {
                    gp.ui.addLogMessage("");
                    // Critical hit check
                    if (uTool.getRandomNumber() <= critEnemy) {
                        damageEnemy *= 3; // Critical hits deal triple damage
                        if (damageEnemy > 0) {
                            gp.playSE(15);
                        } else {
                            gp.playSE(16);
                        }
                        gp.ui.addLogMessage("It's a critical hit");
                    } else {
                        if (damageEnemy > 0) {
                            gp.playSE(14);
                        } else {
                            gp.playSE(16);
                        }
                    }
                    faceTarget(enemy, player);
                    dashForward(enemy);
                    getDamage(String.valueOf(damageEnemy), player.getX(), player.getY() - 2);
                    dashBack(enemy);
                    player.takeDamage(damageEnemy);
                    gp.ui.addLogMessage(player.getName() + " took " + damageEnemy + " damage");

                    if (player.getHP() <= 0) {
                        playerDefeated = true; // Enemy killed
                        player.Defeated();
                    }
                } else {
                    faceTarget(enemy, player);
                    dashForward(enemy);
                    getDamage("MISS", player.getX() - 2, player.getY() - 2);
                    dashBack(enemy);
                    gp.playSE(17);
                    if (enemy.getName() != null) {
                        gp.ui.addLogMessage(enemy.getName() + " missed");
                    } else if (enemy.getName() == null) {
                        gp.ui.addLogMessage(String.valueOf(enemy.getRace()) + " " + enemy.getClassName() + " missed");
                    }
                }

                // Delay before player counterattack
                Thread.sleep(delay);

                // Player counterattacks if still alive and enemy is in its range
                if (!playerDefeated && player.getHP() > 0) {
                    List<int[]> playerRange = player.calculateStaticAttackRange();

                    // Check if player unit is in enemy unit's range
                    boolean inRange = uTool.containsTile(playerRange, enemy.getCol(), enemy.getRow());
                    // If player is in enemy's range
                    if (inRange) {
                        damagePlayer = calculateDamage(player, enemy);
                        hitPlayer = calculateHitChance(player, enemy);
                        critPlayer = calculateCriticalChance(player, enemy);

                        if (uTool.getRandomNumber() <= hitPlayer) {
                            // Critical hit check
                            if (uTool.getRandomNumber() <= critPlayer) {
                                damagePlayer *= 3; // Critical hits deal triple damage
                                if (damagePlayer > 0) {
                                    gp.playSE(15);
                                } else {
                                    gp.playSE(16);
                                }
                                gp.ui.addLogMessage("It's a critical hit");
                            } else {
                                if (damagePlayer > 0) {
                                    gp.playSE(14);
                                } else {
                                    gp.playSE(16);
                                }
                            }
                            faceTarget(player, enemy);
                            dashForward(player);
                            getDamage(String.valueOf(damagePlayer), enemy.getX(), enemy.getY() - 2);
                            dashBack(player);
                            enemy.takeDamage(damagePlayer);
                            if (enemy.getName() != null) {
                                gp.ui.addLogMessage(player.getName() + " deals " + damagePlayer + " damage to " + enemy.getName());
                            } else if (enemy.getName() == null) {
                                gp.ui.addLogMessage(player.getName() + " deals " + damagePlayer + " damage to " + String.valueOf(enemy.getRace()) + " " + enemy.getClassName());
                            }

                            if (enemy.getHP() <= 0) {
                                enemyDefeated = true; // Player unit killed
                                enemy.Defeated();
                            }
                        } else {
                            playerMissed = true;
                            faceTarget(player, enemy);
                            dashForward(player);
                            getDamage("MISS", enemy.getX() - 2, enemy.getY() - 2);
                            dashBack(player);
                            gp.playSE(17);
                            gp.ui.addLogMessage(player.getName() + " missed the counterattack");
                        }
                    }
                }

                // Check if enemy can double attack
                if (!enemyDefeated && enemy.getHP() > 0) {
                    if (!playerDefeated && enemy.getEffSpeed() - player.getEffSpeed() >= 5 && player.getHP() > 0) {
                        Thread.sleep(delay);
                        // Player attacks if hit is successful
                        if (uTool.getRandomNumber() <= hitEnemy) {
                            // Critical hit check
                            if (uTool.getRandomNumber() <= critEnemy) {
                                damageEnemy *= 3; // Critical hits deal triple damage
                                if (damageEnemy > 0) {
                                    gp.playSE(15);
                                } else {
                                    gp.playSE(16);
                                }
                                gp.ui.addLogMessage("It's a critical hit");
                            } else {
                                if (damageEnemy > 0) {
                                    gp.playSE(14);
                                } else {
                                    gp.playSE(16);
                                }
                            }
                            faceTarget(enemy, player);
                            dashForward(enemy);
                            getDamage(String.valueOf(damageEnemy), player.getX(), player.getY() - 2);
                            dashBack(enemy);
                            player.takeDamage(damageEnemy);
                            gp.ui.addLogMessage(player.getName() + " took " + damageEnemy + " damage");

                            if (player.getHP() <= 0) {
                                playerDefeated = true; // Enemy killed
                                player.Defeated();
                            }
                        } else {
                            gp.playSE(17);
                            faceTarget(enemy, player);
                            dashForward(enemy);
                            getDamage("MISS", player.getX() - 2, player.getY() - 2);
                            dashBack(enemy);
                            if (enemy.getName() != null) {
                                gp.ui.addLogMessage(enemy.getName() + " missed");
                            } else if (enemy.getName() == null) {
                                gp.ui.addLogMessage(String.valueOf(enemy.getRace()) + " " + enemy.getClassName() + " missed");
                            }
                        }
                    }
                }

                // Check if player can double attack
                if (!playerDefeated && player.getHP() > 0) {
                    if (!enemyDefeated && player.getEffSpeed() - enemy.getEffSpeed() >= 5 && enemy.getHP() > 0) {
                        Thread.sleep(delay);
                        List<int[]> playerRange = player.calculateStaticAttackRange();

                        // Check if player unit is in enemy unit's range
                        boolean inRange = uTool.containsTile(playerRange, enemy.getCol(), enemy.getRow());
                        // If player is in enemy's range
                        if (inRange) {
                            playerMissed = false;
                            damagePlayer = calculateDamage(player, enemy);
                            hitPlayer = calculateHitChance(player, enemy);
                            critPlayer = calculateCriticalChance(player, enemy);

                            if (uTool.getRandomNumber() <= hitPlayer) {
                                // Critical hit check
                                if (uTool.getRandomNumber() <= critPlayer) {
                                    damagePlayer *= 3; // Critical hits deal triple damage
                                    if (damagePlayer > 0) {
                                        gp.playSE(15);
                                    } else {
                                        gp.playSE(16);
                                    }
                                    gp.ui.addLogMessage("It's a critical hit");
                                } else {
                                    if (damagePlayer > 0) {
                                        gp.playSE(14);
                                    } else {
                                        gp.playSE(16);
                                    }
                                }
                                faceTarget(player, enemy);
                                dashForward(player);
                                getDamage(String.valueOf(damagePlayer), enemy.getX(), enemy.getY() - 2);
                                dashBack(player);
                                enemy.takeDamage(damagePlayer);
                                if (enemy.getName() != null) {
                                    gp.ui.addLogMessage(player.getName() + " deals " + damagePlayer + " damage to " + enemy.getName());
                                } else if (enemy.getName() == null) {
                                    gp.ui.addLogMessage(player.getName() + " deals " + damagePlayer + " damage to " + String.valueOf(enemy.getRace()) + " " + enemy.getClassName());
                                }

                                if (enemy.getHP() <= 0) {
                                    enemyDefeated = true; // Player unit killed
                                    enemy.Defeated();
                                }
                            } else {
                                playerMissed = true;
                                faceTarget(player, enemy);
                                dashForward(player);
                                getDamage("MISS", enemy.getX() - 2, enemy.getY() - 2);
                                dashBack(player);
                                gp.playSE(17);
                                gp.ui.addLogMessage(player.getName() + " missed the counterattack");
                            }
                        }
                    }
                }

                // Experience calculation for player unit
                if (!playerDefeated && !playerMissed && damagePlayer > 0) {
                    boolean delay = false;
                    int experienceGained = calculateExperience(player, enemy, enemyDefeated, damagePlayer);
                    if (player.getExp() + experienceGained >= 100) {
                        delay = true;
                    }
                    player.gainExperience(experienceGained);
                    if (delay) {
                        Thread.sleep(2000);
                    }
                }

                battleInProgress = false; // Mark battle as finished
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    //Methods to make units face their target
    public void faceTarget(Entity attacker, Entity defender) {
        if (defender.getX() > attacker.getX()) {
            attacker.setDirection("right");
        } else if (defender.getX() < attacker.getX()) {
            attacker.setDirection("left");
        } else if (defender.getY() > attacker.getY()) {
            attacker.setDirection("down");
        } else if (defender.getY() < attacker.getY()) {
            attacker.setDirection("up");
        }
    }

    // Method to make units dash towards their target
    public void dashForward(Entity attacker) {
        int moveDistance = gp.getTileSize() / 2; // Half a tile size

        // Store the original position before moving
        originalX = attacker.getX();
        originalY = attacker.getY();

        // Dash towards the enemy
        if (attacker.getDirection().equals("right")) {
            attacker.setX(attacker.getX() + moveDistance);
        } else if (attacker.getDirection().equals("left")) {
            attacker.setX(attacker.getX() - moveDistance);
        } else if (attacker.getDirection().equals("down")) {
            attacker.setY(attacker.getY() + moveDistance);
        } else if (attacker.getDirection().equals("up")) {
            attacker.setY(attacker.getY() - moveDistance);
        }

        // Delay to allow the player to reach the target position
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to make units return to ther position after attacking
    public void dashBack(Entity attacker) {
        attacker.setX(originalX);
        attacker.setY(originalY);
        attacker.setDirection("none");

        // Delay to allow the player to return to the original position
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to calculate damage
    public int calculateDamage(Entity attacker, Entity defender) {
        int damage = 0;

        if (attacker.getAttackType() == Entity.AttackType.Physical) {
            damage = attacker.getMight() - defender.getEffDefense();
            if (attacker.equippedWeapon.getEffectiveRace() == defender.getRace()) {
                damage = 3 * attacker.equippedWeapon.getMight() + attacker.getEffStrength() - defender.getEffDefense();
            }
            if (attacker.equippedWeapon.getEffectiveType() == defender.getUnitType()) {
                damage = 3 * attacker.equippedWeapon.getMight() + attacker.getEffStrength() - defender.getEffDefense();
            }
        } else if (attacker.getAttackType() == Entity.AttackType.Magical) {
            damage = attacker.getMight() - defender.getEffResistance();
        }

        if (damage < 0) {
            damage = 0;
        }
        return damage;
    }

    // Method to calculate hit chance
    public int calculateHitChance(Entity attacker, Entity defender) {
        int hitRate = attacker.getHitRate() - defender.getEvade();
        if (hitRate > 100) {
            hitRate = 100;
        } else if (hitRate < 0) {
            hitRate = 0;
        }
        return hitRate;
    }

    // Method to calculate critical chance
    public int calculateCriticalChance(Entity attacker, Entity defender) {
        int crit = attacker.getCritical() - defender.getLuck();
        if (crit > 100) {
            crit = 100;
        } else if (crit < 0) {
            crit = 0;
        }
        return crit;
    }

    // Method to calculate experience gained by the player unit
    private int calculateExperience(LightUnit player, ChaosUnit enemy, boolean enemyDefeated, int damageByPlayer) {
        int levelDifference = enemy.getLevel() - player.getLevel();
        int experienceGained;

        if (enemyDefeated) {
            // Experience from killing enemy
            if (levelDifference >= 0) {
                experienceGained = 20 + (levelDifference * 3);
            } else if (levelDifference == -1) {
                experienceGained = 20;
            } else {
                experienceGained = Math.max(26 + (levelDifference * 3), 7);
            }
        } else {
            // Experience from damaging enemy
            if (levelDifference >= 0) {
                experienceGained = (31 + levelDifference) / 3;
            } else if (levelDifference == -1) {
                experienceGained = 10;
            } else {
                experienceGained = Math.max((33 + levelDifference) / 3, 1);
            }
        }
        return experienceGained;
    }

    public boolean isBattleInProgress() {
        return battleInProgress;
    }

    // DAMAGE ON SCREEN UI

    public void displayDamage() {
        if (floatingText != null && duration > 0) {
            g2.setColor(Color.WHITE);
            g2.setFont(arial_15);
            g2.drawString(floatingText, floatingTextX, floatingTextY);

            // Decrease the floating text duration
            duration--;
        }

        if (duration == 0)
            floatingText = null;
    }

    // To render the damage at a specific location
    public void getDamage(String text, int x, int y) {
        floatingText = text;
        floatingTextX = x;
        floatingTextY = y;
        duration = 30;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        displayDamage();
    }
}




