package com.databaseproject;

/**
 * Room class that the RoomDAO will use as objects for each room
 * Room
 */
public class Room {
    private final int pkRoomNum;
    private final double PricePerNight;
    
    public Room(int pkRoomNum, double PricePerNight) {
        this.pkRoomNum = pkRoomNum;
        this.PricePerNight = PricePerNight;
    }

    public int getRoomNum() {return pkRoomNum;}
    public double getRoomPrice() {return PricePerNight;}
}
