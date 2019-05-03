
public class Room {
	String roomname,amenities;

	public Room() 
	{
	roomname="none";
	amenities="none";
	}
	public Room(String roomname,String amenity)
	{
		this.roomname = roomname;
		this.amenities = amenity;
	}
	public void setRoom(String room)
	{
		this.roomname = room;
	}




	public String toString() 
	{
		return "Your room is "+roomname+"\nAmenities: "+amenities ;
	}

}
