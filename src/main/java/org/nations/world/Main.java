package org.nations.world;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

	private static final String URL = "jdbc:mysql://localhost:3306/nations";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Please insert a country to search...");
		String userInput = sc.nextLine();
		sc.close();

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

			String sql = "SELECT countries.name, countries.country_id, regions.name, continents.name"
					+ " FROM countries " + " JOIN regions " + " ON countries.region_id = regions.region_id "
					+ " JOIN continents " + " ON regions.continent_id = continents.continent_id "
					+ " WHERE countries.name LIKE ? " + " ORDER BY countries.name ";

			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setString(1, "%" + userInput + "%");
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						final String countries = rs.getString(1);
						final int id = rs.getInt(2);
						final String regions = rs.getString(3);
						final String continents = rs.getString(4);

						System.out.println(id + " - " + countries + " | " + regions + " | " + continents);
					}
				}
			}

		} catch (Exception e) {

			System.out.println("ERROR: " + e.getMessage());

		}
	}
}
