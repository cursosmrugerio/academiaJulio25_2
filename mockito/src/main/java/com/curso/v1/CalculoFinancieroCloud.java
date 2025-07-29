package com.curso.v1;

public interface CalculoFinancieroCloud {
	double calculoExterno(long principal, int years, float annualRate, byte compoundingsPerYear);
}
