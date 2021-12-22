/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2016-2020 larryTheCoder and contributors
 *
 * Permission is hereby granted to any persons and/or organizations
 * using this software to copy, modify, merge, publish, and distribute it.
 * Said persons and/or organizations are not allowed to use the software or
 * any derivatives of the work for commercial use or any other means to generate
 * income, nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing
 * and/or trademarking this software without explicit permission from larryTheCoder.
 *
 * Any persons and/or organizations using this software must disclose their
 * source code and have it publicly available, include this license,
 * provide sufficient credit to the original authors of the project (IE: larryTheCoder),
 * as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,FITNESS FOR A PARTICULAR
 * PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.larryTheCoder.schematic;

import cn.nukkit.math.Vector3;

/**
 * @author tastybento
 */
class EntityObject {

    private Vector3 location;
    private byte color;
    private float yaw;
    private float pitch;
    private boolean sheared;
    private Vector3 motion;
    private int age;
    private int rabbitType;
    private int catType;
    private boolean sitting;
    private int profession;
    private boolean carryingChest;
    private boolean owned;
    private byte collarColor;
    // Paintings
    private byte facing;
    private String motive;
    // Item Frames
    private float itemDropChance;
    private byte itemRotation;
    // Coordinates for tiles
    private Double tileX = null;
    private Double tileY = null;
    private Double tileZ = null;
    // Items informations
    private Byte count = null;
    private Short damage = null;
    private String id = null;

    /**
     * @return the location
     */
    public Vector3 getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Vector3 location) {
        this.location = location;
    }

    /**
     * @return the color
     */
    public byte getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(byte color) {
        this.color = color;
    }

    /**
     * @return the yaw
     */
    public float getYaw() {
        return yaw;
    }

    /**
     * @param yaw the yaw to set
     */
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    /**
     * @return the pitch
     */
    public float getPitch() {
        return pitch;
    }

    /**
     * @param pitch
     */
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    /**
     * @return the sheared
     */
    public boolean isSheared() {
        return sheared;
    }

    /**
     * @param sheared the sheared to set
     */
    public void setSheared(boolean sheared) {
        this.sheared = sheared;
    }

    /**
     * @return the motion
     */
    public Vector3 getMotion() {
        return motion;
    }

    /**
     * @param motion the motion to set
     */
    public void setMotion(Vector3 motion) {
        this.motion = motion;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the profession
     */
    public int getProfession() {
        return profession;
    }

    /**
     * @param profession the profession to set
     */
    public void setProfession(int profession) {
        this.profession = profession;
    }

    /**
     * @return the rabbitType
     */
    public int getRabbitType() {
        return rabbitType;
    }

    /**
     * @param rabbitType the rabbitType to set
     */
    public void setRabbitType(int rabbitType) {
        this.rabbitType = rabbitType;
    }

    /**
     * @return the carryingChest
     */
    public boolean isCarryingChest() {
        return carryingChest;
    }

    /**
     * @param carryingChest the carryingChest to set
     */
    public void setCarryingChest(byte carryingChest) {
        if (carryingChest > (byte) 0) {
            this.carryingChest = true;
        }
        this.carryingChest = false;
    }

    /**
     * @return the catType
     */
    public int getCatType() {
        return catType;
    }

    /**
     * @param catType the catType to set
     */
    public void setCatType(int catType) {
        this.catType = catType;
    }

    /**
     * @return the sitting
     */
    public boolean isSitting() {
        return sitting;
    }

    /**
     * @param sitting the sitting to set
     */
    public void setSitting(byte sitting) {
        if (sitting > (byte) 0) {
            this.sitting = true;
        }
        this.sitting = false;
    }

    /**
     * @return the owned
     */
    public boolean isOwned() {
        return owned;
    }

    /**
     * @param owned the owned to set
     */
    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    /**
     * @return the collarColor
     */
    public byte getCollarColor() {
        return collarColor;
    }

    /**
     * @param collarColor the collarColor to set
     */
    public void setCollarColor(byte collarColor) {
        this.collarColor = collarColor;
    }

    /**
     * @return the facing
     */
    public byte getFacing() {
        return facing;
    }

    /**
     * @param facing the facing to set
     */
    public void setFacing(byte facing) {
        this.facing = facing;
    }

    /**
     * @return the motive
     */
    public String getMotive() {
        return motive;
    }

    /**
     * @param motive the motive to set
     */
    public void setMotive(String motive) {
        this.motive = motive;
    }

    /**
     * @return the itemDropChance
     */
    public float getItemDropChance() {
        return itemDropChance;
    }

    /**
     * @param itemDropChance the itemDropChance to set
     */
    public void setItemDropChance(float itemDropChance) {
        this.itemDropChance = itemDropChance;
    }

    /**
     * @return the itemRotation
     */
    public byte getItemRotation() {
        return itemRotation;
    }

    /**
     * @param itemRotation the itemRotation to set
     */
    public void setItemRotation(byte itemRotation) {
        this.itemRotation = itemRotation;
    }

    /**
     * @return the tileX
     */
    public Double getTileX() {
        return tileX;
    }

    /**
     * @param tileX the tileX to set
     */
    public void setTileX(Double tileX) {
        this.tileX = tileX;
    }

    /**
     * @return the tileX
     */
    public Double getTileY() {
        return tileY;
    }

    /**
     * @param tileY the tileY to set
     */
    public void setTileY(Double tileY) {
        this.tileY = tileY;
    }

    /**
     * @return the tileX
     */
    public Double getTileZ() {
        return tileZ;
    }

    /**
     * @param tileZ the tileZ to set
     */
    public void setTileZ(Double tileZ) {
        this.tileZ = tileZ;
    }

    /**
     * @return the count
     */
    public Byte getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Byte count) {
        this.count = count;
    }

    /**
     * @return the damage
     */
    public Short getDamage() {
        return damage;
    }

    /**
     * @param damage the damage to set
     */
    public void setDamage(Short damage) {
        this.damage = damage;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
}
