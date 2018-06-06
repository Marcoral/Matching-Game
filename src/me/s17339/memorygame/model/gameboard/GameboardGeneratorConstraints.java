package me.s17339.memorygame.model.gameboard;

import java.util.List;

public class GameboardGeneratorConstraints {
	
	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/
	
	private int rows, columns;
	
	private List<Integer> allowedValues;
	private int valuesFromInclusive, valuesToExclusive;
	
	private GameboardGeneratorConstraints(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
	}
	
	public GameboardGeneratorConstraints(int rows, int columns, int valuesToExclusive) {
		this(rows, columns);
		this.valuesToExclusive = valuesToExclusive;
	}
	
	public GameboardGeneratorConstraints(int rows, int columns, List<Integer> allowedValues) {
		this(rows, columns);
		this.allowedValues = allowedValues;
	}
	
	/* ------------------------------------------------	*/
	/*					BUILDER METHODS					*/
	/* ------------------------------------------------	*/
	
	public GameboardGeneratorConstraints allowedValues(List<Integer> allowedValues) {
		this.allowedValues = allowedValues;
		return this;
	}
	
	public GameboardGeneratorConstraints valuesFrom(int indexIncluding) {
		this.valuesFromInclusive = indexIncluding;
		return this;
	}
	
	public GameboardGeneratorConstraints valuesTo(int indexExclusive) {
		this.valuesToExclusive = indexExclusive;
		return this;
	}
	
	/* ----------------------------------------	*/
	/*					GETTERS					*/
	/* ----------------------------------------	*/
	
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public List<Integer> getAllowedValues() {
		return allowedValues;
	}

	public int getValuesFromInclusive() {
		return valuesFromInclusive;
	}

	public int getValuesToExclusive() {
		return valuesToExclusive;
	}
}