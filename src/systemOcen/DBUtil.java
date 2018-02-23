package systemOcen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBUtil {

	private static Connection connection;
	private static String url ="jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
	private static String uzytkownik = "xlkruk";
	private static String haslo = "xlkruk";

	public static Connection getConnection() {
		if (connection == null) {
			try {
				connection = DriverManager
						.getConnection(url, uzytkownik, haslo);
			} catch (SQLException e) {
				System.out.println("Exception: " + e.getMessage());
				System.out.println("KONIEC PROGRAMU");
				System.exit(0);
			}
		}
		return connection;
	}

	private static boolean tableExists(Connection connection, String tableName,
			String user) {
		boolean schemaExists = false;
		String sql = "SELECT 1 cnt from ALL_ALL_TABLES WHERE TABLE_NAME = upper(?) and OWNER = upper(?)";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, tableName);
			statement.setString(2, user);
			;
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) == 1) {
					schemaExists = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return schemaExists;
	}

	public static void ocen(Connection connection, int idn, int idu, int idp,
			int ido, String ocena) {
		if (!DBUtil.nauczycielExist(connection, idn)) {
			System.out.println("Nauczyciel o idn:" + idn + " nie istnieje");
			return;
		}

		if (!DBUtil.uczenExist(connection, idu)) {
			System.out.println("Uczen o idu:" + idu + " nie istnieje");
			return;
		}

		if (!DBUtil.przedmiotExist(connection, idp)) {
			System.out.println("Przedmiot o idp:" + idp + " nie istnieje");
			return;
		}

		if (!DBUtil.ocenaExist(connection, ido)) {
			System.out.println("Ocena o ido:" + ido + " nie istnieje");
			return;
		}

		if (!(ocena.equals("C") || ocena.equals("S") || ocena.equals("c") || ocena
				.equals("s"))) {
			System.out.println("Niepoprawna wartoœæ!");
			return;
		}
		insertOcenianie(connection, idn, idu, idp, ido, ocena);
	}

	private static void insertOcenianie(Connection connection, int idn,
			int idu, int idp, int ido, String ocena) {
		String sql = "INSERT INTO OCENIANIE (idn, idu, idp, ido, rodzaj_oceny) values(?,?,?,?,upper(?))";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, idn);
			statement.setInt(2, idu);
			statement.setInt(3, idp);
			statement.setInt(4, ido);
			statement.setString(5, ocena);
			statement.execute();

		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
			return;
		}

		System.out.println("Sukces.");
	}

	private static void dropTable(Connection connection, String tableName) {
		String sql = "DROP TABLE " + tableName;
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void deleteTable(Connection connection, String tableName) {
		String sql = "DELETE FROM " + tableName;
		try {
			Statement statement = connection.createStatement();
			statement.executeQuery(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void populateUczen(Connection connection) {
		ArrayList<Uczen> uczniowie = new ArrayList<Uczen>();
		uczniowie.add(new Uczen(1, "Placek", "Jacek"));
		uczniowie.add(new Uczen(2, "Makota", "Ala"));
		uczniowie.add(new Uczen(3, "Rambo", "John"));
		uczniowie.add(new Uczen(4, "Bravo", "Johny"));
		uczniowie.add(new Uczen(5, "Monroe", "Marilyn"));
		uczniowie.add(new Uczen(6, "Burczymucha", "Stefek"));
		uczniowie.add(new Uczen(9, "Kruk", "£ukasz"));
		String uczenSql = "INSERT INTO UCZEN values(?,?,?)";
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(uczenSql);
			for (Uczen u : uczniowie) {
				statement.setInt(1, u.getIdu());
				statement.setString(2, u.getNazwiskoUcznia());
				statement.setString(3, u.getImieUcznia());
				statement.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void populateNauczyciel(Connection connection) {
		ArrayList<Nauczyciel> nauczyciele = new ArrayList<Nauczyciel>();
		nauczyciele.add(new Nauczyciel(1, "Einstein", "Albert"));
		nauczyciele.add(new Nauczyciel(2, "Banach", "Stefan"));
		nauczyciele.add(new Nauczyciel(3, "Go³ota", "Andrzej"));
		nauczyciele.add(new Nauczyciel(4, "Nobel", "Alfred"));
		String nauczycielSql = "INSERT INTO NAUCZYCIEL values(?,?,?)";

		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(nauczycielSql);
			for (Nauczyciel n : nauczyciele) {
				statement.setInt(1, n.getIdn());
				statement.setString(2, n.getNazwisko());
				statement.setString(3, n.getImie());
				statement.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void populateOcena(Connection connection) {
		ArrayList<Ocena> oceny = new ArrayList<Ocena>();
		oceny.add(new Ocena(1, "Niedostateczny", 1));
		oceny.add(new Ocena(2, "Mierny", 2));
		oceny.add(new Ocena(3, "Dostateczny", 3));
		oceny.add(new Ocena(4, "Dobry", 4));
		oceny.add(new Ocena(5, "Bardzo dobry", 5));
		oceny.add(new Ocena(6, "Celuj¹cy", 6));
		String ocenaSql = "INSERT INTO OCENA values(?,?,?)";

		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(ocenaSql);
			for (Ocena o : oceny) {
				statement.setInt(1, o.getIdo());
				statement.setString(2, o.getWartoscOpisowa());
				statement.setFloat(3, o.getWartoscNumeryczna());
				statement.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void populatePrzedmiot(Connection connection) {

		ArrayList<Przedmiot> przedmioty = new ArrayList<Przedmiot>();
		przedmioty.add(new Przedmiot(1, "JÊZYK POLSKI"));
		przedmioty.add(new Przedmiot(2, "MATEMATYKA"));
		przedmioty.add(new Przedmiot(3, "CHEMIA"));
		przedmioty.add(new Przedmiot(4, "BIOLOGIA"));
		przedmioty.add(new Przedmiot(5, "WF"));
		String przedmiotSql = "INSERT INTO PRZEDMIOT values(?,?)";
		try {
			PreparedStatement statement = connection
					.prepareStatement(przedmiotSql);
			for (Przedmiot p : przedmioty) {
				statement.setInt(1, p.getIdp());
				statement.setString(2, p.nazwaPrzedmiotu);
				statement.execute();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void createDBSchema(Connection connection) {
		String sql1 = "CREATE TABLE PRZEDMIOT(" + "idp integer not null,"
				+ "nazwa_przedmiotu varchar2(20) not null)";

		String sql2 = "CREATE TABLE NAUCZYCIEL(" + "idn integer not null,"
				+ "nazwisko_nauczyciela varchar2(30) not null,"
				+ "imie_nauczyciela varchar2(20) not null)";

		String sql3 = "CREATE TABLE UCZEN(" + "idu integer not null,"
				+ "nazwisko_ucznia varchar2(30) not null,"
				+ "imie_ucznia varchar2(20) not null)";

		String sql4 = "CREATE TABLE OCENA(" + "ido integer not null,"
				+ "wartosc_opisowa varchar2(20) not null,"
				+ "wartosc_numeryczna number(5) not null)";

		String sql5 = "CREATE TABLE OCENIANIE("
				+ "rodzaj_oceny varchar2(1) not null,"
				+ "idp integer not null," + "idn integer not null,"
				+ "idu integer not null," + "ido integer not null)";

		try {
			Statement statement = connection.createStatement();
			if (!tableExists(connection, "PRZEDMIOT", uzytkownik)) {
				statement.execute(sql1);
				populatePrzedmiot(connection);
			}

			if (!tableExists(connection, "NAUCZYCIEL", uzytkownik)) {
				statement.execute(sql2);
				populateNauczyciel(connection);
			}

			if (!tableExists(connection, "UCZEN", uzytkownik)) {
				statement.execute(sql3);
				populateUczen(connection);
			}

			if (!tableExists(connection, "OCENA", uzytkownik)) {
				statement.execute(sql4);
				populateOcena(connection);
			}

			if (!tableExists(connection, "OCENIANIE", uzytkownik)) {
				statement.execute(sql5);
			}
			;
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void initializeDBSchema(Connection connection) {
		String sql1 = "CREATE TABLE PRZEDMIOT(" + "idp integer not null,"
				+ "nazwa_przedmiotu varchar2(20) not null)";

		String sql2 = "CREATE TABLE NAUCZYCIEL(" + "idn integer not null,"
				+ "nazwisko_nauczyciela varchar2(30) not null,"
				+ "imie_nauczyciela varchar2(20) not null)";

		String sql3 = "CREATE TABLE UCZEN(" + "idu integer not null,"
				+ "nazwisko_ucznia varchar2(30) not null,"
				+ "imie_ucznia varchar2(20) not null)";

		String sql4 = "CREATE TABLE OCENA(" + "ido integer not null,"
				+ "wartosc_opisowa varchar2(20) not null,"
				+ "wartosc_numeryczna number(5) not null)";

		String sql5 = "CREATE TABLE OCENIANIE("
				+ "rodzaj_oceny varchar2(1) not null,"
				+ "idp integer not null," + "idn integer not null,"
				+ "idu integer not null," + "ido integer not null)";

		try {
			Statement statement = connection.createStatement();
			if (!tableExists(connection, "PRZEDMIOT", uzytkownik))
				statement.execute(sql1);
			deleteTable(connection, "PRZEDMIOT");
			populatePrzedmiot(connection);

			if (!tableExists(connection, "NAUCZYCIEL", uzytkownik))
				statement.execute(sql2);
			deleteTable(connection, "NAUCZYCIEL");
			populateNauczyciel(connection);

			if (!tableExists(connection, "UCZEN", uzytkownik))
				statement.execute(sql3);
			deleteTable(connection, "UCZEN");
			populateUczen(connection);

			if (!tableExists(connection, "OCENA", uzytkownik))
				statement.execute(sql4);
			deleteTable(connection, "OCENA");
			populateOcena(connection);

			if (!tableExists(connection, "OCENIANIE", uzytkownik))
				statement.execute(sql5);
			deleteTable(connection, "OCENIANIE");
			;
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void printOcena(Connection connection) {
		System.out.println("OCENY");
		String sql1 = "select * from OCENA";
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			while (rs.next()) {
				System.out.println("ido:" + rs.getInt("ido")
						+ ",\twartosc_opisowa:"
						+ rs.getString("wartosc_opisowa")
						+ ",\twartosc_numeryczna:"
						+ rs.getFloat("wartosc_numeryczna"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	};

	public static void printNauczyciel(Connection connection) {
		System.out.println("NAUCZYCIELE");
		String sql1 = "select * from NAUCZYCIEL";
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			while (rs.next()) {
				System.out.println("idn:" + rs.getInt("idn") + ",\t"
						+ rs.getString("imie_nauczyciela") + " "
						+ rs.getString("nazwisko_nauczyciela"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	};

	public static void printUczen(Connection connection) {
		System.out.println("UCZNIOWIE");
		String sql1 = "select * from UCZEN";
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			while (rs.next()) {
				System.out.println("idu:" + rs.getInt("idu") + ",\t"
						+ rs.getString("imie_ucznia") + " "
						+ rs.getString("nazwisko_ucznia"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	};

	public static void printPrzedmiot(Connection connection) {
		System.out.println("PRZEDMIOTY");
		String sql1 = "select * from PRZEDMIOT";
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			while (rs.next()) {
				System.out.println("idp:" + rs.getInt("idp") + ": "
						+ rs.getString("nazwa_przedmiotu"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	};

	public static void printWyniki(Connection connection) {
		System.out.println("WYNIKI");
		String sql1 = "select * from OCENIANIE";
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			while (rs.next()) {
				System.out.println("idn:" + rs.getInt("idn") + ", idu:"
						+ rs.getInt("idu") + ", idp:" + rs.getInt("idp")
						+ ", ido:" + rs.getInt("ido") + ", rodzaj_oceny:"
						+ rs.getString("rodzaj_oceny"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	};

	public static boolean nauczycielExist(Connection connection, int id) {
		boolean rowExists = false;
		String sql = "SELECT 1 cnt from NAUCZYCIEL WHERE idn = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) == 1) {
					rowExists = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowExists;
	}

	public static boolean ocenaExist(Connection connection, int id) {
		boolean rowExists = false;
		String sql = "SELECT 1 cnt from OCENA WHERE ido = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) == 1) {
					rowExists = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowExists;
	}

	public static boolean uczenExist(Connection connection, int id) {
		boolean rowExists = false;
		String sql = "SELECT 1 cnt from UCZEN WHERE idu = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) == 1) {
					rowExists = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowExists;
	}

	public static boolean przedmiotExist(Connection connection, int id) {
		boolean rowExists = false;
		String sql = "SELECT 1 cnt from PRZEDMIOT WHERE idp = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) == 1) {
					rowExists = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowExists;
	}

}
