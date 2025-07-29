package com.curso.v1;

public class CalculoFinanciero {
	
	//@Autowired
	CalculoFinancieroCloud cfc; //HAS-A //MOCKITO
	
    double calcula(long principal, int years, float annualRate, byte compoundingsPerYear) {
    	//DELEGAR
        return cfc.calculoExterno(principal,years,annualRate,compoundingsPerYear);
    }


}
