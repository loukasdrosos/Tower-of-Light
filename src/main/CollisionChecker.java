/* package main;

import Entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker (GamePanel gp) {
        this.gp = gp;
    }

    public void checkEntityTile(Entity entity) {
        int entityLeftWorldX = entity.getX();
        int entityRightWorldX = entity.getX() + gp.getTileSize();
        int entityTopWorldY = entity.getY();
        int entityBottomWorldY = entity.getY() + gp.getTileSize();

        int entityLeftCol = entityLeftWorldX / gp.getTileSize();
        int entityRightCol = entityRightWorldX / gp.getTileSize();
        int entityTopRow = entityTopWorldY / gp.getTileSize();
        int entityBottomRow = entityBottomWorldY / gp.getTileSize();

        int tileNum1, tileNum2;

        switch(entity.direction) {
            case "up" :
                entityTopRow = (entityTopWorldY - entity.movement) / gp.getTileSize();
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.setCollision(true);
                }
                break;
            case "down" :
                entityTopRow = (entityTopWorldY - entity.movement) / gp.getTileSize();
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.setCollision(true);
                }
                break;
            case "left" :
                entityLeftCol = (entityLeftWorldX - entity.movement) / gp.getTileSize();
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.setCollision(true);
                }
                break;
            case "right" :
                entityRightCol = (entityRightWorldX + entity.movement) / gp.getTileSize();
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.setCollision(true);
                }
                break;
        }
    }

    public void checkCursorTile(Cursor cursor) {
        int entityLeftWorldX = cursor.getX();
        int entityRightWorldX = cursor.getX() + gp.getTileSize();
        int entityTopWorldY = cursor.getY();
        int entityBottomWorldY = cursor.getY() + gp.getTileSize();

        int entityLeftCol = entityLeftWorldX / gp.getTileSize();
        int entityRightCol = entityRightWorldX / gp.getTileSize();
        int entityTopRow = entityTopWorldY / gp.getTileSize();
        int entityBottomRow = entityBottomWorldY / gp.getTileSize();

        int tileNum1, tileNum2;

        switch(cursor.direction) {
            case "up" :
                entityTopRow = (entityTopWorldY - cursor.movement) / gp.getTileSize();
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    cursor.setCollision(true);
                }
                break;
            case "down" :
                entityTopRow = (entityTopWorldY - cursor.movement) / gp.getTileSize();
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    cursor.setCollision(true);
                }
                break;
            case "left" :
                entityLeftCol = (entityLeftWorldX - cursor.movement) / gp.getTileSize();
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    cursor.setCollision(true);
                }
                break;
            case "right" :
                entityRightCol = (entityRightWorldX + cursor.movement) / gp.getTileSize();
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    cursor.setCollision(true);
                }
                break;
        }
    }
} */
