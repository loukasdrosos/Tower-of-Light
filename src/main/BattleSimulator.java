package main;

import Entity.*;

import java.util.List;

public class BattleSimulator {

    GamePanel gp;

    public BattleSimulator(GamePanel gp) {
        this.gp = gp;
    }

    public void battlePlayerPhase(LightUnit player, ChaosUnit enemy) {
        UtilityTool uTool = new UtilityTool();
        boolean playerDefeated = false;
        boolean enemyDefeated = false;

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
                gp.ui.addLogMessage("It's a critical hit");
            }
            enemy.takeDamage(damagePlayer);
            if (enemy.getName() != null) {
                gp.ui.addLogMessage(player.getName() + " deals " + damagePlayer + " damage to " + enemy.getName());
            } else if (enemy.getName() == null) {
                gp.ui.addLogMessage(player.getName() + " deals " + damagePlayer + " damage to " + String.valueOf(enemy.getRace()) + enemy.getClassName());
            }

            if (enemy.getHP() <= 0) {
                enemyDefeated = true; // Enemy killed
                enemy.Defeated();
            }
        } else {
            gp.ui.addLogMessage(player.getName() + " missed");
        }

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
                        gp.ui.addLogMessage("It's a critical hit");
                    }
                    player.takeDamage(damageEnemy);
                    gp.ui.addLogMessage(player.getName() + " took " + damageEnemy + " damage");

                    if (player.getHP() <= 0) {
                        playerDefeated = true; // Player unit killed
                        player.Defeated();
                    }
                } else {
                    if (enemy.getName() != null) {
                        gp.ui.addLogMessage(enemy.getName() + " missed the counterattack");
                    } else if (enemy.getName() == null) {
                        gp.ui.addLogMessage(String.valueOf(enemy.getRace()) + enemy.getClassName() + " missed the counterattack");
                    }
                }
            }
        }

        // Check if player can double attack
        if (!playerDefeated && player.getHP() > 0) {
            if (!enemyDefeated && player.getEffSpeed() - enemy.getEffSpeed() >= 5 && enemy.getHP() > 0) {
                // Player attacks if hit is successful
                if (uTool.getRandomNumber() <= hitPlayer) {
                    // Critical hit check
                    if (uTool.getRandomNumber() <= critPlayer) {
                        damagePlayer *= 3; // Critical hits deal triple damage
                        gp.ui.addLogMessage("It's a critical hit");
                    }
                    enemy.takeDamage(damagePlayer);
                    if (enemy.getName() != null) {
                        gp.ui.addLogMessage(player.getName() + " deals " + damagePlayer + " damage to " + enemy.getName());
                    } else if (enemy.getName() == null) {
                        gp.ui.addLogMessage(player.getName() + " deals " + damagePlayer + " damage to " + String.valueOf(enemy.getRace()) + enemy.getClassName());
                    }

                    if (enemy.getHP() <= 0) {
                        enemyDefeated = true; // Enemy killed
                        enemy.Defeated();
                    }
                }
                else {
                    gp.ui.addLogMessage(player.getName() + " missed");
                }
            }
        }

        // Check if enemy can double attack
        if (!enemyDefeated && enemy.getHP() > 0) {
            if (!playerDefeated && enemy.getEffSpeed() - player.getEffSpeed() >= 5 && player.getHP() > 0) {
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
                            gp.ui.addLogMessage("It's a critical hit");
                        }
                        player.takeDamage(damageEnemy);
                        gp.ui.addLogMessage(player.getName() + " took " + damageEnemy + " damage");

                        if (player.getHP() <= 0) {
                            playerDefeated = true; // Player unit killed
                            player.Defeated();
                        }
                    } else {
                        if (enemy.getName() != null) {
                            gp.ui.addLogMessage(enemy.getName() + " missed the counterattack");
                        } else if (enemy.getName() == null) {
                            gp.ui.addLogMessage(String.valueOf(enemy.getRace()) + enemy.getClassName() + " missed the counterattack");
                        }
                    }
                }
            }
        }

        // Experience calculation for player unit
        if (!playerDefeated && damagePlayer > 0) {
            int experienceGained = calculateExperience(player, enemy, enemyDefeated, damagePlayer);
            player.gainExperience(experienceGained);
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
        gp.ui.addLogMessage(player.getName() + " received " + experienceGained + " exp");
        return experienceGained;
    }

}




