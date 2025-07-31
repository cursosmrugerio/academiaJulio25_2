package com.financiero.client.scenarios;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Resultado de la ejecución de un escenario demostrativo
 */
public class ScenarioResult {
    
    private final String scenarioName;
    private final LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean success;
    private String summary;
    private String error;
    private final List<ScenarioStep> steps;
    
    public ScenarioResult(String scenarioName) {
        this.scenarioName = scenarioName;
        this.startTime = LocalDateTime.now();
        this.success = false;
        this.steps = new ArrayList<>();
    }
    
    /**
     * Agrega un paso al resultado del escenario
     */
    public void addStep(String stepName, String description) {
        steps.add(new ScenarioStep(stepName, description, LocalDateTime.now()));
    }
    
    /**
     * Agrega un paso con indicador de éxito/error
     */
    public void addStep(String stepName, String description, boolean stepSuccess) {
        steps.add(new ScenarioStep(stepName, description, LocalDateTime.now(), stepSuccess));
    }
    
    /**
     * Marca el escenario como completado
     */
    public void complete() {
        this.endTime = LocalDateTime.now();
    }
    
    /**
     * Obtiene la duración total del escenario
     */
    public long getDurationMillis() {
        LocalDateTime end = endTime != null ? endTime : LocalDateTime.now();
        return java.time.Duration.between(startTime, end).toMillis();
    }
    
    /**
     * Genera un reporte detallado del escenario
     */
    public String generateReport() {
        complete();
        
        StringBuilder report = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        
        report.append("\\n").append("=".repeat(80)).append("\\n");
        report.append(String.format("REPORTE DE ESCENARIO: %s\\n", scenarioName.toUpperCase()));
        report.append("=".repeat(80)).append("\\n");
        
        report.append(String.format("Inicio: %s\\n", startTime.format(formatter)));
        report.append(String.format("Fin: %s\\n", endTime.format(formatter)));
        report.append(String.format("Duración: %d ms\\n", getDurationMillis()));
        report.append(String.format("Estado: %s\\n", success ? "✅ EXITOSO" : "❌ FALLÓ"));
        
        if (summary != null) {
            report.append(String.format("Resumen: %s\\n", summary));
        }
        
        if (error != null) {
            report.append(String.format("Error: %s\\n", error));
        }
        
        report.append("\\n").append("-".repeat(80)).append("\\n");
        report.append("PASOS EJECUTADOS:\\n");
        report.append("-".repeat(80)).append("\\n");
        
        for (int i = 0; i < steps.size(); i++) {
            ScenarioStep step = steps.get(i);
            String status = step.isSuccess() ? "✅" : "❌";
            report.append(String.format("%d. %s %s [%s]\\n", 
                                      i + 1, status, step.getName(), step.getTimestamp().format(formatter)));
            report.append(String.format("   %s\\n", step.getDescription()));
            if (!step.isSuccess() && step.getError() != null) {
                report.append(String.format("   Error: %s\\n", step.getError()));
            }
            report.append("\\n");
        }
        
        report.append("=".repeat(80)).append("\\n");
        
        return report.toString();
    }
    
    /**
     * Genera un resumen corto del escenario
     */
    public String generateSummary() {
        return String.format("[%s] %s - %s (%d pasos, %d ms)", 
                           success ? "✅" : "❌",
                           scenarioName,
                           success ? "EXITOSO" : "FALLÓ",
                           steps.size(),
                           getDurationMillis());
    }
    
    // Getters y Setters
    public String getScenarioName() { return scenarioName; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public List<ScenarioStep> getSteps() { return steps; }
    
    /**
     * Paso individual de un escenario
     */
    public static class ScenarioStep {
        private final String name;
        private final String description;
        private final LocalDateTime timestamp;
        private final boolean success;
        private String error;
        
        public ScenarioStep(String name, String description, LocalDateTime timestamp) {
            this(name, description, timestamp, true);
        }
        
        public ScenarioStep(String name, String description, LocalDateTime timestamp, boolean success) {
            this.name = name;
            this.description = description;
            this.timestamp = timestamp;
            this.success = success;
        }
        
        public ScenarioStep(String name, String description, LocalDateTime timestamp, String error) {
            this.name = name;
            this.description = description;
            this.timestamp = timestamp;
            this.success = false;
            this.error = error;
        }
        
        // Getters
        public String getName() { return name; }
        public String getDescription() { return description; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public boolean isSuccess() { return success; }
        public String getError() { return error; }
    }
}