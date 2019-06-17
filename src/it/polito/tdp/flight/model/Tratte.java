package it.polito.tdp.flight.model;

public class Tratte {
	
	private int id1;
	private int id2;
	private double km;

	public Tratte(int id1, int id2, double km) {

		this.id1 = id1;
		this.id2 = id2;
		this.km = km;
	}

	public int getId1() {
		return id1;
	}

	public int getId2() {
		return id2;
	}

	public double getKm() {
		return km;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id1;
		result = prime * result + id2;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tratte other = (Tratte) obj;
		if (id1 != other.id1)
			return false;
		if (id2 != other.id2)
			return false;
		return true;
	}
	
	

}
