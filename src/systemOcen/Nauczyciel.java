package systemOcen;

public class Nauczyciel {
	private int idn;
	private String imie;
	private String nazwisko;

	public Nauczyciel(int idn, String nazwisko, String imie) {
		super();
		this.idn = idn;
		this.imie = imie;
		this.nazwisko = nazwisko;
	}

	public int getIdn() {
		return idn;
	}

	public void setIdn(int idn) {
		this.idn = idn;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

}
