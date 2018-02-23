package systemOcen;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class SystemOcen {
	public static void main(String[] args) {

		Connection connection = DBUtil.getConnection();
		String opcja = "";
		boolean koniec = false;
		Scanner scn = new Scanner(System.in);

		initCfg(connection, scn);

		printWelcomeInfo();
		printMenu();
		while (!koniec) {

			opcja = "";
			try {
				opcja = scn.next();
			} catch (Exception e) {
				System.out.println("Wprowadzono niedopuszczaln� warto��!");
			}
			switch (opcja) {
			case "0":
				koniec = true;
				break;
			case "1":
				ocenianie(scn, connection);
				printMenu();
				break;
			case "2":
				DBUtil.printOcena(connection);
				printMenu();
				break;
			case "3":
				DBUtil.printNauczyciel(connection);
				printMenu();
				break;
			case "4":
				DBUtil.printUczen(connection);
				printMenu();
				break;
			case "5":
				DBUtil.printPrzedmiot(connection);
				printMenu();
				break;
			case "6":
				DBUtil.printWyniki(connection);
				printMenu();
				break;
			default:
				System.out.println("######NIEPOPRAWNA WARTO��");
				break;
			}
			

		}

		System.out.println("KONIEC PROGRAMU");
		scn.close();
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// dropDB();
	}

	private static void initCfg(Connection connection, Scanner scn) {
		String opcja;
		System.out.println("Czy Wyczy�ci� tabel� OCENIANIE? T/N");
		opcja = scn.next();
		if (opcja.equals("T")||opcja.equals("t")) {
			initDB();
		} else {
			createMissingTables(connection);
		}
	}

	private static void ocenianie(Scanner scn, Connection connection) {
		int idn, idu, idp, ido;
		String ocena;
		try {
			System.out.println("Podaj id nauczyciela:");
			idn = scn.nextInt();

			System.out.println("Podaj id ucznia:");
			idu = scn.nextInt();

			System.out.println("Podaj id przedmiotu:");
			idp = scn.nextInt();

			System.out.println("Podaj id oceny:");
			ido = scn.nextInt();

			System.out.println("Podaj rodzaj oceny (C lub S):");
			ocena = scn.next();

			DBUtil.ocen(connection, idn, idu, idp, ido, ocena);

		} catch (Exception e) {
			System.out.println("Wprowadzono niedopuszczaln� warto��!");
		}

	}

	private static void initDB() {
		DBUtil.initializeDBSchema(DBUtil.getConnection());
		System.out.println("BAZA DANYCH ZAINICJOWANA");

	}

	private static void createMissingTables(Connection connection) {
		DBUtil.createDBSchema(connection);
		System.out.println("BAZA DANYCH ZAINICJOWANA");

	}

	private static void printWelcomeInfo() {
		System.out.println("OBD - PRACA DOMOWA - OCENIANIE");

	}

	private static void printMenu() {
		System.out.println("\nWybierz opcj� wpisuj�c jej numer i wciskaj�� ENTER:");
		System.out.println("1 - OCE�");
		System.out.println("2 - Wy�wietl skal� ocen");
		System.out.println("3 - Wy�wietl nauczycieli");
		System.out.println("4 - Wy�wietl uczni�w");
		System.out.println("5 - Wy�wietl przedmioty");
		System.out.println("6 - Wy�wietl wyniki");
		System.out.println("0 - Koniec programu");

	}
}
