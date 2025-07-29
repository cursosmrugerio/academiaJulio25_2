package com.curso.v2;

public class CalculoFinanciero {
	
	private CalculoFinancieroCloud cfc;
	
	//@Autowired
    public CalculoFinanciero(CalculoFinancieroCloud cfc) {
		this.cfc = cfc;
	}
	
    double calcula(long principal, int years, float annualRate, byte compoundingsPerYear) {
    	//DELEGAR            
        return cfc.calculoExterno(principal,years,annualRate,compoundingsPerYear);
                                //(1_000_000L, 10, 5.0f,     (byte)4)
                                // 954453.58
    }


}
