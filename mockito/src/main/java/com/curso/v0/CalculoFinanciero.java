package com.curso.v0;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CalculoFinanciero {
	
    double calcula(long principal, int years, float annualRate, byte compoundingsPerYear) {
        // Convertir las entradas a BigDecimal para precisión en los cálculos
        BigDecimal principalBD = new BigDecimal(principal);
        BigDecimal annualRateBD = new BigDecimal(annualRate / 100.0);
        BigDecimal compoundingsBD = new BigDecimal(compoundingsPerYear);
        BigDecimal yearsBD = new BigDecimal(years);

        // Calcular el factor de crecimiento compuesto (A = P(1 + r/n)^(nt))
        BigDecimal one = BigDecimal.ONE;
        BigDecimal ratePerCompounding = annualRateBD.divide(compoundingsBD, 10, RoundingMode.HALF_UP);
        BigDecimal base = one.add(ratePerCompounding);
        BigDecimal exponent = compoundingsBD.multiply(yearsBD);
        BigDecimal compoundFactor = base.pow(exponent.intValue(), new MathContext(10, RoundingMode.HALF_UP));

        // Monto final después de aplicar el interés compuesto
        BigDecimal finalAmount = principalBD.multiply(compoundFactor);

        // Ajuste de inflación (suponiendo una inflación promedio anual del 3%)
        BigDecimal inflationRate = new BigDecimal("0.03");
        BigDecimal inflationAdjustment = one.subtract(inflationRate).pow(years, new MathContext(10, RoundingMode.HALF_UP));
        BigDecimal adjustedForInflation = finalAmount.multiply(inflationAdjustment);

        // Conversión a otra moneda, suponiendo una tasa de cambio fija (ejemplo: 1.2)
        BigDecimal exchangeRate = new BigDecimal("1.2");
        BigDecimal adjustedForExchangeRate = adjustedForInflation.multiply(exchangeRate);

        // Calcular una amortización a lo largo de los años, acumulando una cuota anual (ejemplo: 5%)
        BigDecimal amortizationRate = new BigDecimal("0.05");
        BigDecimal annualPayment = principalBD.multiply(amortizationRate);
        BigDecimal totalAmortization = annualPayment.multiply(yearsBD);

        // Calcular el valor final incluyendo amortización
        BigDecimal finalValue = adjustedForExchangeRate.subtract(totalAmortization);

        // Devolver el valor final, redondeado a dos decimales
        return finalValue.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }


}
