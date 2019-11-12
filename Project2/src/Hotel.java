
public class Hotel
{

	private String name;
	private String country;
	private String star;
	private String pool;
	private String breakfast;
	private String price;
	private Room[] roomList;
	
	

	Hotel(String name, String country, String star, String pool, String breakfast, String price, Room[] room)
	{
		this.name = name;
		this.country = country;
		this.star = star;
		this.pool = pool;
		this.breakfast = breakfast;
		this.price = price;	
		this.roomList = room;
	}
	
	String getName()
	{
		return name;
	}
	
	String getCountry()
	{
		return country;
	}
	
	String getStar()
	{
		return this.star;
	}
	
	String getPool()
	{
		return this.pool;
	}
	
	String getBreakfast()
	{
		return this.breakfast;
	}
	
	String getPrice()
	{
		return this.price;
	}
	
	public Room[] getRoomList()
	{
		return roomList;
	}

	public void setRoomList(Room[] roomList) 
	{
		this.roomList = roomList;
	}
	
	
	public String toString()
	{
		return "Country        : "+getCountry()+"\n"+
			   "Hotel name     : "+getName()+"\n"+
			   "Hotel Star     : "+getStar()+"\n"+
			   "Hotel Pool     : "+getPool()+"\n"+
			   "Hotel Breakfast: "+getBreakfast()+"\n"+
			   "Price          : "+getPrice()+"\n"+
			   "Room           : "+getRoomList().length+"\n";
	}
	

	
}