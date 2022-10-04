package Objects;

public class Room {
    String RoomID;   // unique id for identifying a room
    int size;
    Boolean multimedia;  // multimedia available in room

    public Room(String roomID, int size, Boolean multimedia) {
        RoomID = roomID;
        this.size = size;
        this.multimedia = multimedia;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Boolean getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(Boolean multimedia) {
        this.multimedia = multimedia;
    }
    @Override
    public String toString(){
        return RoomID;
    }
}
