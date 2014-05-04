package util;

public class Pair{

	private int first;
	private int second;
	
	public Pair(int first, int second){
		this.first = first;
		this.second = second;
	}
	
	public int getFirst(){
		return first;
	}
	
	public int getSecond(){
		return second;
	}
	
	public boolean equals(Pair pair){
		return this.first == pair.first && this.second == pair.second;
	}
}
