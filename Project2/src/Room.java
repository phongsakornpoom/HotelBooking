public class Room 
{	
	String type;
	
	public Room() 
	{
		type = "Default Room";
	}
	
	public Room(String roomType)
	{
		this.type = roomType;
	}
	
	public String getType() 
	{
		return type;
	}
	
	public void setType(String type) 
	{
		this.type = type;
	}
	
	public String toString() 
	{
		return "Room Type: "+type;
	}

}