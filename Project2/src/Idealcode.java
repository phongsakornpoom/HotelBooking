import java.io.File;
,m
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class Idealcode {// example of how to implement and use

	public static void main(String[] args) throws FileNotFoundException {
		String roomType = null, furniture, type, thing = null;
		PrintStream filePrint = new PrintStream(new File("booking.txt"));// printstream to print it into file
		int answer = 0;
		Menu menu = new Menu();
		String[] condition = { "1", "2", "3", "4" };
		String[] condition1 = { "1", "2", "3", "4", "5" };
		String[] item = { "Hair dryer", "Teapot", "Wifi", "Shampoo" };
		String[] roomTypes = { "Single bed room", "Double bed room", "Triple bed room", "Quad bedroom" };
		Scanner console = new Scanner(System.in);

		do {
			System.out.println("Please select your room type.");
			System.out.println("1. Single room \n2. Double room \n3. Twin bed room\n4. Triple bed room\n5. Quad bed room ");
			String roomselect = menu.inputChecker(condition1);
			switch (roomselect) 
			{
			case "1":
				System.out.println("Single bed room is selected");// print into the console that is selected
				roomType = roomTypes[0];
				filePrint.println("Roomtype: " + roomType);
				break;
			case "2":
				System.out.println("Double bed room is selected");
				roomType = roomTypes[1];
				filePrint.println("Roomtype: " + roomType);
				break;
			case "3":
				System.out.println("Triple bed room is selected");
				roomType = roomTypes[2];
				filePrint.println("Roomtype: " + roomType);
				break;
			case "4":
				System.out.println("Quad bedroom is selected");
				roomType = roomTypes[3];
				filePrint.println("Roomtype: " + roomType);
				break;
			default:
				;
			}
			System.out.println("Please select your amenities");
			System.out.println("1. Hair dryer\n2.Teapot\n3.Wifi\n4.Shampoo");
			String amenities = menu.inputChecker(condition);
			switch (amenities) {
			case "1":
				System.out.println("Hair dryer is selected");// print into the console that is selected
				thing = item[0];
				filePrint.println("Item: " + thing);
				break;
			case "2":
				System.out.println("Teapot is selected");
				thing = item[1];
				filePrint.println("Item: " + thing);
				break;
			case "3":
				System.out.println("Wifi is selected");
				thing = item[2];
				filePrint.println("Item: " + thing);
				break;
			case "4":
				System.out.println("Shampoo is selected");
				thing = item[3];
				filePrint.println("Item: " + thing);
			default:
				;
			}
			System.out.println("1. Confirm 2. Retry");
			answer = console.nextInt();
		} while (answer != 1);
		Room room = new Room(roomType, thing);
		System.out.println(room);
	}
}