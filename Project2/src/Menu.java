import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;



public class Menu 
{
	//user choices for inputChecker if either than the array the input checker will let it run again 
	static String[] condition_yn = {"yes","no"};
	static String[] condition_num = {"1","2","3","4","5"};
	static String[] condition_num_menu = {"1","2","3"};
	static String[] condition_num_menu3 = {"1","2"};
	static String[] condition_num_menu2 = {"1","2","3","4","5","6"};
	static String[] condition_country = {"japan","korea","china","singapore"};
	static String[] condition_num4 = {"1","2","3","4"};
	
	//Files location
	static String fileCSV = "C:\\Users\\pongsakorn tanupatra\\Desktop\\Book1.csv";
	static String fileTXT = "C:\\Users\\pongsakorn tanupatra\\git\\repository\\Project2\\booking.txt";
	
	//Config each type of room and cost
		static String[] roomType = {"Regular","Double size","Small","Connected", "Dio"};
		
		static double[] roomTypeCost = {1.0, 1.5, 1.0, 1.25, 2.0}; //rate of room 
		
		static ArrayList<Hotel> selectedHotelList = new ArrayList<Hotel>();

		static Hotel choosenHotel;
		
		static int[] amountRoomByType = new int[roomType.length];
		
		static String country;
		/**
		 * main function program flow runs in here
		 * @param args
		 * @throws IOException
		 * @throws ParseException
		 */
		public static void main(String[] args) throws IOException, ParseException 
		{
			//initialize variables
			Hotel[] hotelList = loadHotelObject();
			String user_input_string;

			Booking booking = new Booking();
			
			//defense mechanism for price of each type of room
			if (roomTypeCost.length != roomType.length)
			{
				System.out.println("Debug.Log: roomType and roomTypeCost length is not the same.\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
				System.out.println("Do not continue.");
			}
			
			do //looping menu options
			{
				//menu message 
				System.out.println("Welcome to hotel booking system.");
				System.out.println("1) Book a hotel\n2) View your booking\n3) Exit");
				user_input_string = inputChecker(condition_num_menu);
				
				if (user_input_string.contentEquals("1"))
				{
					country = countrySelecter(condition_country);
					for (Hotel hotel : hotelList)
					{
						if (hotel.getCountry().equalsIgnoreCase(country))
						{
							selectedHotelList.add((Hotel) hotel);
							System.out.println(hotel);
						}
					}
					do
					{
						System.out.println("1. Filter with star\n"
										+  "2. Filter with free breakfast\n"
										+  "3. Filter with swimming pool\n"
										+  "4. Filter with all\n"
										+  "5. Choose a hotel\n"
										+  "6. Go back to menu\n");
						user_input_string = inputChecker(condition_num_menu2);
						
						switch (user_input_string) 
						{
						  
						  case "1":
								hotelFilterStar(hotelList);
								break;
								
						  case "2":
								hotelFilterBreakfast(hotelList);
							  	break;
							  	
						  case "3":
								hotelFilterPool(hotelList);
							  	break;
						
						  case "4":
							  	hotelFilterAll(hotelList);
							  	break;
							  	
						  case "5":
							  	if (selectedHotelList.isEmpty())
							  	{
							  		System.out.println("Sorry there are no hotel.");
							  		break;
							  	}
							  	
							  	choosenHotel = booking.chooseHotel(selectedHotelList); 
							  	loadRoom();
							  	printRoom();
							  	System.out.println("1. Choose room\n2. Go back");
							  	user_input_string = inputChecker(condition_num_menu3);
							  	
							  	if(user_input_string.contains("1"))
							  	{
							  		Booking.getCustomerInfo(printRoomFinal(chooseRoom()), roomTypeCost);
							  	}
							  	else
							  	{
							  		System.out.println("Break");
							  		break;
							  	}
							  	
							  	
							  	Booking.writeInfoTofile();
							  	user_input_string = "6";
							  	break;
							  	
						  case "6":
							  	System.out.println("Go back to menu");
							  	break;
						
							  	
						}
					}while (!user_input_string.equalsIgnoreCase("6"));
				}
				else if (user_input_string.contentEquals("2"))
				{
					Booking.viewBooking(fileTXT);
				}
				
			} while (!user_input_string.contentEquals("3"));
			
			System.out.println("Thank you for using our hotel booking system.");
		}

		
		
		
		/**
		 * This function let user input until it satisfy the condition
		 * @param condition String[] consist of condition that user need to input
		 * @return user input that satisfy the condition
		 */
		public static String inputChecker(String[] condition)
		{
			String user_input = null;
			boolean key = true;
			while (key==true)
			{
				Scanner console = new Scanner(System.in); 
				String box = console.nextLine(); 
				for (int i = 0; i<condition.length; i++)
				{
					if (box.equalsIgnoreCase(condition[i]))
					{
						user_input = box;
						key=false;
						break;
					}			
					else if (i == condition.length-1 && !box.equals(condition[i]))
					{
						System.out.println("Please try again");
					}
							
				}
			
			}
			
			return user_input;
		}

		/**
		 * This function let user input until it satisfy the condition but number instead
		 * This would check for numerical input if input is not a number user can try again as many times as user want
		 * @param max the maximum number that user can get
		 * @return number that user input
		 */
		public static int intChecker(int max)
		{
			Scanner console = new Scanner(System.in);
			int userInput = 0;
			do
			{
				if (console.hasNextInt())
				{
					userInput = console.nextInt();
					if (userInput <= 0)
					{
						System.out.println("Please specify by postive numbers");
					}
					else if (userInput > max)
					{
						System.out.println("Too many, please try again.");
					}
				}
				else
				{
					System.out.println("Please try again using numbers");
					console.next();
				}
				
			}while (userInput <= 0 || userInput > max);
			System.out.println(String.format("intChecker: %d",	userInput));
			return userInput;
		}
		
		/**
		 * Let user select country 
		 * @param country this parameter is a static String[] that contain 
		 * @return country that user selected
		 */
		public static String countrySelecter(String[] country)
		{
			int userInputInt;
		
			
			System.out.println("Listing country...");
			for (int i = 0; i<country.length; i++) //printing list of countries
			{
				System.out.println(i+1+". " + country[i].substring(0, 1).toUpperCase() + country[i].substring(1)); //capitalize first letter with numbers
			}
			
			System.out.println("Please select a country by numbers e.g. 1 or 2");
			userInputInt = Integer.parseInt((inputChecker(condition_num4)));
			System.out.println(country[userInputInt-1].substring(0, 1).toUpperCase() + country[userInputInt-1].substring(1)+" has been selected.\n");

			return country[userInputInt-1];
		}
		
		/**
		 * Show hotel with specific star 
		 * @param {hotelList} : list of hotel in the selected country
		 */
		public static void hotelFilterStar(Hotel[] hotelList)
		{
			System.out.println("Please specify hotel rating e.g. 1-5:");
			String star = inputChecker(condition_num);
			selectedHotelList.clear();
			for (Hotel hotel : hotelList)
			{
				if (hotel.getCountry().equalsIgnoreCase(country) && hotel.getStar().equalsIgnoreCase(star))
				{
				 	System.out.println(hotel);
					selectedHotelList.add((Hotel) hotel);
				}
			}
			checkIfNoHotel(selectedHotelList);
		}
		
		/**
		 * show hotel with free breakfast (or no free breakfast)
		 * @param hotelList list of hotel in the selected country
		 */
		public static void hotelFilterBreakfast(Hotel[] hotelList)
		{
			System.out.println("Please specify free breakfast e.g. yes,no");
			String breakfast = inputChecker(condition_yn);
			selectedHotelList.clear();
			for (Hotel hotel : hotelList)
			{
				if (hotel.getCountry().equalsIgnoreCase(country) && hotel.getBreakfast().equalsIgnoreCase(breakfast))
				{
					System.out.println(hotel);
					selectedHotelList.add((Hotel) hotel);
				}
			}
			checkIfNoHotel(selectedHotelList);
		}
		
		/**
		 * show hotel with swimming pool (or not)
		 * @param hotelList list of hotel in the selected country
		 */
		public static void hotelFilterPool(Hotel[] hotelList)
		{
		  	System.out.println("Please specify hotel rating, swimming pool and free breakfast e.g. 5 *enter* yes *enter* no *enter*");
			String pool = inputChecker(condition_yn);
			
		  	selectedHotelList.clear();
			for (Hotel hotel : hotelList)
			{
				if (hotel.getCountry().equalsIgnoreCase(country) && hotel.getPool().equalsIgnoreCase(pool))
				{
					System.out.println(hotel);
					selectedHotelList.add((Hotel) hotel);
				}
			}
			checkIfNoHotel(selectedHotelList);
		}
		/**
		 * show hotel with all filter (very confusing to use)
		 * @param hotelList List of hotel in the selected country
		 */
		public static void hotelFilterAll(Hotel[] hotelList)
		{
			System.out.println("Please specify hotel rating, swimming pool and free breakfast e.g. 5 *enter* yes *enter* no *enter*");
			String star = inputChecker(condition_num);
			String pool = inputChecker(condition_yn);
			String breakfast = inputChecker(condition_yn);
		  	selectedHotelList.clear();
			for (Hotel hotel : hotelList)
			{
				if (hotel.getCountry().equalsIgnoreCase(country) && hotel.getStar().equalsIgnoreCase(star) && hotel.getPool().equalsIgnoreCase(pool) && hotel.getBreakfast().equalsIgnoreCase(breakfast))
				{
					System.out.println(hotel);
					selectedHotelList.add((Hotel) hotel);
				}
			}
			checkIfNoHotel(selectedHotelList);
		}
		
		/**
		 * load list of hotels from csv file into an array of hotel object and also generate random rooms here
		 * @return array of hotels (all hotel available in csv file)
		 * @throws FileNotFoundException
		 */
		static Hotel[] loadHotelObject () throws FileNotFoundException
		{
			String hotelInfo;
			Hotel[] hotelList = new Hotel[getAmountOfHotel()];
			Scanner scanner = new Scanner(new File(fileCSV));
			int count = 0;
			
			scanner.useDelimiter("\n"); //separate by line (enter)
	        while(scanner.hasNext())    	
	        {   
	        	hotelInfo = scanner.next();
	    		String[] hotelListButArray = hotelInfo.split(","); //split by comma into array
	    		hotelList[count] = new Hotel(hotelListButArray[0],hotelListButArray[1],hotelListButArray[2],
	    				hotelListButArray[3],hotelListButArray[4],hotelListButArray[5], getRandomRoom());  
	    		count++;
	    	}
	        scanner.close();
			return hotelList;
		}
		
		/**
		 * count the amount of hotel is csv file
		 * @return
		 * @throws FileNotFoundException
		 */
		static int getAmountOfHotel() throws FileNotFoundException
		{
//			String file_name = "C:\\Users\\anuto\\eclipse-workspace\\getStart\\src\\hotelBooking_BeatVer\\Book1.csv";
			int i = 0;
			Scanner scanner = new Scanner(new File(fileCSV));
			scanner.useDelimiter("\n"); //separate by space (enter)
			
	        while(scanner.hasNext())    	
	        {   
	        	scanner.next();
	        	i++;
	        }
	        scanner.close();
	        
	        return i;
		}

		/**
		 * Generate random amount and type of room. In this case I decided 20 rooms is enough.
		 * This function is being used when loading hotel since room are being randomly generated not from reading csv file
		 * @return array of room 
		 */
		public static Room[] getRandomRoom()
		{
	        int numRoom = (int) (Math.random() * 20 + 1);
	        Room[] roomList = new Room[numRoom];
	        
	        for (int i = 0; i<numRoom; i++)
	        {
	        	roomList[i] = new Room(roomType[(int) (Math.random() * roomType.length)]);
	        }

	        return roomList;
		}

		/**
		 * Count each type of room available in the hotel
		 */
		public static void loadRoom() //count each type of room to static array amountByType
		{
			int key = 0;
			
			for (String s : roomType)
			{
				for (int i = 0; i < choosenHotel.getRoomList().length ; i++)
				{
					if (choosenHotel.getRoomList()[i].getType().equalsIgnoreCase(s))
					{
						amountRoomByType[key] += 1;
					}
				}
				key ++;
			}
		}
		
		/**
		 * Print rooms that are available in the hotel. (in a nice clean way)
		 */
		public static void printRoom()
		{
			for (int i = 0; i < roomType.length; i++)
			{
				System.out.println(String.format("|%d. %12s room:  %s available", i+1, roomType[i], amountRoomByType[i]));
			}
		}
		
		/**
		 * Print rooms that user has chosen 
		 * @param roomList rooms that user has chosen
		 * @return amount of each room that user has chosen
		 */
		public static int[] printRoomFinal(Room[] roomList)
		{
			int[] roomTypeCount = new int[roomType.length];
			int key = 0;
			
			System.out.println("You have choosen: ");
			for(String s : roomType)
			{
				for(int i = 0; i < roomList.length; i++)
				{
					if(roomList[i].getType().equalsIgnoreCase(s))
					{
						roomTypeCount[key] += 1;
					}
				}
				key++;
			}
			key = 0;
			for(int num : roomTypeCount)
			{
				if(num != 0)
				{
					System.out.println(String.format("%s %12s room", num, roomType[key]));
					key++;
				}
				else
				{
					key++;
				}
			}
			return roomTypeCount;
		}
		
		/**
		 * This function took me around whole 2 day to figure this out and there are still some bugs
		 * This function let user choose any rooms user want. User can choose many type of room with specific amount which really test my coding skill. 
		 * @return Room object in array that user has chosen
		 */
		public static Room[] chooseRoom()
		{
			int roomTypeIndex;
			int amountRoom;
			int key = 0;
			
			System.out.println("How many room?: (Total)");
			Room[] chosenRooms = new Room[intChecker(Booking.hotelInfo.getRoomList().length)];
			
			if (chosenRooms.length == choosenHotel.getRoomList().length)
			{
				for (int i = 0; i < roomType.length; i++)
				{
					for (int j = 0; j < amountRoomByType[i]; j++)
					{
						chosenRooms[key] = new Room(roomType[i]);
						key++;
					}
				}
				System.out.println("All room have been reserved.");			
			}
			else if (chosenRooms.length == 1)
			{
				System.out.println(String.format("Awating %d rooms (choose one room)", 1));
				System.out.println(String.format("What type of room?: ", ""));
				roomTypeIndex = Booking.inputCheckerForArray(roomType.length);
				System.out.println(roomTypeIndex);
				if (amountRoomByType[roomTypeIndex] == 0)
				{
					System.out.println(String.format("%s room is not available at this time.", roomType[roomTypeIndex]));
				}
				else
				{
					chosenRooms[key] = new Room(roomType[roomTypeIndex]);
				}
				
			}
			else
			{
				while (key < chosenRooms.length)
				{
					System.out.println(String.format("Awating %d rooms", chosenRooms.length - key));
					
					
					System.out.println(String.format("What type of room?: ", ""));
					roomTypeIndex = Booking.inputCheckerForArray(roomType.length);
					if (amountRoomByType[roomTypeIndex] == 0)
					{
						System.out.println(String.format("%s room is not available at this time.", roomType[roomTypeIndex]));
					}
					else
					{
						System.out.println(String.format("How many?: ", ""));
						if (amountRoomByType[roomTypeIndex] < (chosenRooms.length - key))
						{
							amountRoom = intChecker(amountRoomByType[roomTypeIndex]);					
						}
						else
						{
							amountRoom = intChecker(chosenRooms.length - key);
						}

						amountRoomByType[roomTypeIndex] -= amountRoom;
						for (int i = 0; i < amountRoom; i++)
						{
							chosenRooms[key] = new Room(roomType[roomTypeIndex]);
							key++;
						}
						
						System.out.println(String.format("%d %s room have been selected.",amountRoom, roomType[roomTypeIndex]));
						printRoom();
					}
				}
			}
			System.out.println("chooseRoom Successful");
			return chosenRooms;
		}
		/**
		 * Mainly uses in filter function. There +
		 * 3maybe no hotel available with for example 1 star hotel. This function would print that there are no specified hotel.
		 * @param selectedHotel
		 */
		public static void checkIfNoHotel(ArrayList<Hotel> selectedHotel)
		{
			if (selectedHotel.isEmpty())
			{
				System.out.println("Sorry, there are no specified hotel available.");
			}
		}
		
	}