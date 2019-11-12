import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;



public class Booking 
{
	//variables
	static Hotel hotelInfo;
	
	//also variables
	static String customer_name;
	static String checkin_date;
	static String checkout_date;
	static int totalPrice;
	
	static Room[] roomChosen;
	

	/**
	 * Read csv file that contain customer's booking
	 * @param fileName Location of customer's booking file
	 * @throws FileNotFoundException
	 */
	public static void viewBooking(String fileName) throws FileNotFoundException
	{
		Scanner console = new Scanner(new File(fileName));
		console.useDelimiter("\n");
		if (!console.hasNext())
		{
			System.out.println("Sorry, there is no data.\n");
		}
		else 
		{
			while(console.hasNext())
			{
				System.out.println(console.next());
			
			}
		}
	}
	
	/**
	 * Let user fill their information such as name and date of arrival
	 * @param roomList List of room that customer has chosen
	 * @param roomTypeCost cost of room as rating to calculate total price
	 * @throws ParseException
	 */
	public static void getCustomerInfo(int[] roomList, double[] roomTypeCost) throws ParseException
	{
		Scanner console = new Scanner(System.in);
		
		// get name checkindate checkoutdate from user
		System.out.println("Please fill in your information.\n"
						+  "Name: ");
		customer_name = console.nextLine();
		System.out.println("Check-in date: e.g. dd/mm/yyyy 20/05/2019");
		checkin_date = validateJavaDate();
		System.out.println("Check-out date: e.g. dd/mm/yyy 23/05/2019");
		checkout_date = validateDateDiff(checkin_date, roomList, roomTypeCost);
		
		//display customer name checkinoutdate
		System.out.println("Your hotel: " + hotelInfo.getName()+"\n"
						+  "Name: "+customer_name+"\n"
						+  "Check-in date: " + checkin_date+"\n"
						+  "Check-out date: "+checkout_date+"\n"
						+  "Total price   : "+totalPrice+" bath\n");
	}
	
	/**
	 * Let user select their hotel from list that has been sorted or not
	 * @param hotelList list of hotel that has been sorted or not
	 * @return hotel that user has chosen
	 */
	public Hotel chooseHotel(ArrayList<Hotel> hotelList)
	{
		System.out.println("Please select hotels by numbers");

		for (int i = 0; i<hotelList.size(); i++) //looping array showing hotel list
        {
        	System.out.println(i+1 + ". "+hotelList.get(i));
        }
		
		Booking.hotelInfo = hotelList.get(inputCheckerForArray(hotelList.size()));
		System.out.println(Booking.hotelInfo.getName()+" has been chosen.");
		
		return Booking.hotelInfo;
	}
	
	/**
	 * Validating input make it in range of array index
	 * @param arraySize length of the array
	 * @return array index
	 */
 	public static int inputCheckerForArray(int arraySize)

	{
		Scanner console = new Scanner(System.in);
		int userInput = -1;
		
		do
		{
			if (console.hasNextInt())
			{
				userInput = console.nextInt();
				userInput -= 1;

				if (userInput >= arraySize || userInput < 0)
				{
					System.out.println( "Please try again.");
				}
			}
			else
			{
				System.out.println("Please try again using numbers");
				console.next();
			}
			
		}while (userInput >= arraySize  || userInput < 0);
		
		return userInput;
	}
	
 	/**
 	 * write all customer info to file
 	 * @throws IOException
 	 */
	public static void writeInfoTofile() throws IOException
	{
		FileWriter fstream = new FileWriter("booking.txt",true);
        BufferedWriter out = new BufferedWriter(fstream); //buffer class name out
        out.append("name          : "+customer_name);
        out.newLine();
        out.append(hotelInfo.getName());// Writing all customer info
        out.newLine();
        out.append("country       : "+hotelInfo.getCountry());
        out.newLine();
        out.append("Hotel rating  : "+hotelInfo.getStar());
        out.newLine();       
        out.append("check-in date : "+checkin_date);
        out.newLine();
        out.append("check-out date: "+checkout_date);
        out.newLine();
        out.append("Total price   : "+totalPrice+"\n");
        out.newLine();
        out.close();
        System.out.println("Writting successful.\n");
	}

	/**
	 * calculate total price
	 * @param day amount of day that customer spend a night on a hotel
	 * @param roomList list of room that customer has chosen
	 * @param roomTypeCost cost of each type of room (rating)
	 */
	public static void getTotalPrice(long day, int[] roomList, double[] roomTypeCost)
	{
		double roomPrice = 0;
		for (int i = 0; i<roomList.length; i++)
		{
			if(roomList[i] != 0)
			{
				roomPrice += roomList[i] * (Integer.parseInt(hotelInfo.getPrice()) * roomTypeCost[i]);
			}
		}
		
		totalPrice = (int) (day * roomPrice);
	}
	
	/**
	 * validate the check-in date. The date cannot be in the past.
	 * @return check-in date as a string
	 */
	public static String validateJavaDate()
	{
		int key=1;
		String strDate = null;
		Scanner console = new Scanner(System.in);
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
		sdfrmt.setLenient(false);
		while (key ==1)
		{
			strDate = console.nextLine();
			try 
			{
				Date javaDate = sdfrmt.parse(strDate);
				if (javaDate.getTime() < today.getTime())
				{
					System.out.println("Error: Booking to the past is invalid.\nPlease try again.");
				}
				else
				{
					key = 0;
				}
			} 
			catch (ParseException e) 
			{
	//			e.printStackTrace();
				System.out.println("!!!");
				System.out.println("Date format is not correct.\nPlease try again.");
			}
		}
		return strDate;
	}
	
	/**
	 * find difference of two dates which are check-in date and check-out date (here is where everything start to mess up)
	 * @param day check - in date
	 * @param roomList list of room that customer has chosen
	 * @param roomTypeCost price of each type of room (rating)
	 * @return days that customer stay in hotel
	 * @throws ParseException
	 */
	public static String validateDateDiff(String day, int[] roomList, double[] roomTypeCost) throws ParseException
	{
		int key =1;
		Scanner console = new Scanner(System.in);
		String strDate = null;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.setLenient(false);
		Date d1 = format.parse(day);
		Date d2 = null;
		
		
		while (key ==1) 
		{
			strDate = console.nextLine();
			try 
			{
				d2 = format.parse(strDate);
				if (d2.getTime() < d1.getTime())
				{
					System.out.println("Check-out Date cannot be sooner than Check-in Date.\nPlease try again.");
				}
				else
				{
					key =0;
				}
			} 
			catch (ParseException e)
			{
//				e.printStackTrace();
				System.out.println("!!!");
				System.out.println("Date format is not correct.\nPlease try again.");
			}
		}
		long diffDays = d2.getTime() - d1.getTime();
		getTotalPrice(diffDays / (24 * 60 * 60 * 1000), roomList, roomTypeCost);
		return strDate;
	}
	

}