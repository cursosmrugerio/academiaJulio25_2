package com.curso.v2;

public interface CalculoFinancieroCloud {
	double calculoExterno(long principal, int years, float annualRate, byte compoundingsPerYear);
}
