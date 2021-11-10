package de.luke.naruto.database;

public class NarutoDataBase {

	public static MySQL mysql;

	public static void setUp() {
		loadMySQL();

	}

	//https://webphpmyadmin.com/

	public static void loadMySQL() {
		mysql = new MySQL("remotemysql.com", 3306, "PLScCERvj5", "PLScCERvj5", "5SQVjPa69W");
	}

}
