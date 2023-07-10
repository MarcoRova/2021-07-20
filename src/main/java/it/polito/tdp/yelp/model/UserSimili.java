package it.polito.tdp.yelp.model;

public class UserSimili implements Comparable<UserSimili>{
	
	User u;
	double grado;
	
	public UserSimili(User u, double grado) {
		super();
		this.u = u;
		this.grado = grado;
	}

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public double getGrado() {
		return grado;
	}

	public void setGrado(double grado) {
		this.grado = grado;
	}

	@Override
	public int compareTo(UserSimili other) {
		return this.u.getName().compareTo(other.getU().getName());
	}
	
	
	

}
