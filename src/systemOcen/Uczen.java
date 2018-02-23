package systemOcen;

public class Uczen {
	private int idu;
	private String nazwiskoUcznia;
	private String imieUcznia;

	public Uczen(int idu, String nazwiskoUcznia, String imieUcznia) {
		super();
		this.idu = idu;
		this.nazwiskoUcznia = nazwiskoUcznia;
		this.imieUcznia = imieUcznia;
	}

	public int getIdu() {
		return idu;
	}

	public void setIdu(int idu) {
		this.idu = idu;
	}

	public String getNazwiskoUcznia() {
		return nazwiskoUcznia;
	}

	public void setNazwiskoNauczyciela(String nazwiskoNauczyciela) {
		this.nazwiskoUcznia = nazwiskoNauczyciela;
	}

	public String getImieUcznia() {
		return imieUcznia;
	}

	public void setImieUcznia(String imieUcznia) {
		this.imieUcznia = imieUcznia;
	}

}
