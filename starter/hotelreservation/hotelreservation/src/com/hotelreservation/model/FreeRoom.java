package com.hotelreservation.model;

  public class FreeRoom extends Room {
    private  FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber,0.0,roomType);


    }

    @Override
    public String toString() {
        return "FreeRoom{" +
                "roomNumber='" + roomNumber + '\'' +
                ", price=" + price +
                ", roomType=" + roomType +
                '}';
    }
}

