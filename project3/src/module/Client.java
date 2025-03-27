package module;

public class Client {
	
	private String name;
	private int cedula;
	private int edad;
	private String estadoCivil;
	private String situacionLaboral;
	private float points;

	public Client(String name, int cedula, int edad, String estadoCivil, String situacionLaboral, int points) {
		this.name = name;
		this.cedula = cedula;
		this.edad = edad;
		this.estadoCivil = estadoCivil;
		this.situacionLaboral = situacionLaboral;
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public int getCedula() {
		return cedula;
	}

	public int getEdad() {
		return edad;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public String getSituacionLaboral() {
		return situacionLaboral;
	}

	public float getPoints() {
		return points;
	}

	public void setPoints(float points) {
		this.points = getPoints()+points;
	}
	
	
}
