#!/bin/bash

# Script para ejecutar el Cliente HTTP/2 del Core Financiero
# Autor: Claude Code Assistant
# Versión: 1.0.0

JAR_FILE="target/financial-core-http2-client-1.0.0.jar"
MAIN_CLASS="com.financiero.client.examples.FinancialCoreClientDemo"

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Función para mostrar ayuda
show_help() {
    echo -e "${BLUE}Cliente HTTP/2 para Core Financiero - Demo Automático${NC}"
    echo -e "${BLUE}====================================================${NC}"
    echo ""
    echo "Este script ejecuta automáticamente el demo completo del Core Financiero"
    echo "con fechas dinámicas que funcionan durante los próximos 5 días."
    echo ""
    echo "Uso: $0 [OPCIONES]"
    echo ""
    echo "Opciones:"
    echo "  -h, --help          - Mostrar esta ayuda"
    echo ""
    echo "Funcionalidades del demo:"
    echo "  ✅ Conectividad HTTP/2 con el servidor"  
    echo "  ✅ Escenario de depósito completo (NP → PV → PR)"
    echo "  ✅ Escenario de cancelación de movimiento"
    echo "  ✅ Consulta de saldos antes, durante y después"
    echo "  ✅ Validación de fechas de liquidación"
    echo "  ✅ Demostración de multiplexing HTTP/2"
    echo ""
    echo "Prerequisitos:"
    echo "  • Java 21 o superior"
    echo "  • Core Financiero backend ejecutándose en localhost:8080"
    echo "  • Cliente compilado (mvn clean package)"
    echo ""
    echo "Ejemplo de uso:"
    echo "  $0                  # Ejecutar demo completo"
    echo "  $0 --help          # Mostrar esta ayuda"
}

# Función para verificar prerequisitos
check_prerequisites() {
    # Verificar Java
    if ! command -v java &> /dev/null; then
        echo -e "${RED}❌ Java no está instalado${NC}"
        echo "Por favor instala Java 21 o superior"
        exit 1
    fi
    
    # Verificar versión de Java
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
    if [ "$JAVA_VERSION" -lt 21 ]; then
        echo -e "${YELLOW}⚠️  Se recomienda Java 21 o superior (actual: $JAVA_VERSION)${NC}"
    fi
    
    # Verificar que existe el JAR
    if [ ! -f "$JAR_FILE" ]; then
        echo -e "${RED}❌ No se encuentra el archivo JAR: $JAR_FILE${NC}"
        echo "Ejecuta: mvn clean package"
        exit 1
    fi
}

# Función para ejecutar el cliente
run_client() {
    echo -e "${GREEN}🚀 Ejecutando Demo del Cliente HTTP/2 Core Financiero${NC}"
    echo -e "${BLUE}Ejecutando demo completo con fechas dinámicas${NC}"
    echo ""
    
    # Construir comando Java para ejecutar el demo
    JAVA_CMD="java -cp $JAR_FILE $MAIN_CLASS"
    
    # Ejecutar
    eval $JAVA_CMD
}

# Función principal
main() {
    # Si se pide ayuda, mostrarla
    if [ "$1" = "-h" ] || [ "$1" = "--help" ]; then
        show_help
        exit 0
    fi
    
    # Verificar prerequisitos y ejecutar demo
    check_prerequisites
    run_client
}

# Ejecutar función principal con todos los argumentos
main "$@"