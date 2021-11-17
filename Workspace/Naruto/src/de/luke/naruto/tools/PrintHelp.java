package de.luke.naruto.tools;

import java.text.DecimalFormat;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class PrintHelp {

	private static DecimalFormat format = new DecimalFormat("0.00");

	public static void PrintLocation(String name, Location loc) {

		System.out.println(name + "x/y/z " + format.format(loc.getX()) + "/" + format.format(loc.getY()) + "/" + format.format(loc.getZ()));

	}

	public static void PrintVector(String name, Vector vec) {

		System.out.println(name + "x/y/z " + format.format(vec.getX()) + "/" + format.format(vec.getY()) + "/" + format.format(vec.getZ()));

	}

	public static void PrintDouble(String name, double db) {

		System.out.println(name + " " + format.format(db));

	}

	public static void Print(String str) {

		System.out.println(str);

	}

	public static void Print(Long str) {

		System.out.println(str);

	}

	public static void Print(int str) {

		System.out.println(str);

	}
}
