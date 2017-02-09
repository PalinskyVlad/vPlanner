package by.gomel.iba.vPlanner;

import java.util.Date;

public class Main {
	
	public static void main(String[] args) {
		Date start = new Date();
		start.setMonth(start.getMonth() + 1);
		Date end = new Date();
		end.setMonth(end.getMonth() + 1);
		end.setDate(end.getDate() + 1);
		
		System.out.println("start " + start);
		System.out.println("end " + end);
	}

}
