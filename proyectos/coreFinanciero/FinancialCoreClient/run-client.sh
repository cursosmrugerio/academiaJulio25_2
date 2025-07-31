#!/bin/bash

# Script para ejecutar el Cliente HTTP/2 del Core Financiero
# Autor: Claude Code Assistant
# Versión: 1.0.0

JAR_FILE="target/financial-core-http2-client-1.0.0.jar"
MAIN_CLASS="com.financiero.client.FinancialCoreClientCLI"

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Función para mostrar ayuda
show_help() {
    echo -e "${BLUE}Cliente HTTP/2 para Core Financiero${NC}"
    echo -e "${BLUE}===================================${NC}"
    echo ""
    echo "Uso: $0 [COMANDO] [OPCIONES]"
    echo ""
    echo "Comandos disponibles:"
    echo "  demo                - Ejecutar demo completo"
    echo "  deposito            - Ejecutar escenario de depósito"
    echo "  cancelacion         - Ejecutar escenario de cancelación"
    echo "  consultar-saldos    - Consultar saldos actuales"
    echo "  health              - Verificar conectividad"
    echo ""
    echo "Opciones:"
    echo "  -u, --url URL       - URL del servidor (default: http://localhost:8080)"
    echo "  -g, --grupo GRUPO   - Clave grupo empresa (default: 001)"
    echo "  -e, --empresa EMP   - Clave empresa (default: 001)"
    echo "  -U, --usuario USER  - Clave usuario (default: CLI_USER)"
    echo "  -v, --verbose       - Habilitar logging detallado"
    echo "  -h, --help          - Mostrar esta ayuda"
    echo ""
    echo "Ejemplos:"
    echo "  $0 demo"
    echo "  $0 health -v"
    echo "  $0 deposito -u http://servidor:8080 -g 002 -e 003"
    echo "  $0 consultar-saldos --verbose"
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
    local cmd="$1"
    shift
    
    echo -e "${GREEN}🚀 Ejecutando Cliente HTTP/2 Core Financiero${NC}"
    echo -e "${BLUE}Comando: $cmd${NC}"
    echo ""
    
    # Construir comando Java
    JAVA_CMD="java -jar $JAR_FILE $cmd $@"
    
    # Ejecutar
    eval $JAVA_CMD
}

# Función principal
main() {
    # Si no hay argumentos, mostrar ayuda
    if [ $# -eq 0 ]; then
        show_help
        exit 0
    fi
    
    # Procesar argumentos
    case "$1" in
        -h|--help)
            show_help
            exit 0
            ;;
        demo|deposito|cancelacion|consultar-saldos|health)
            check_prerequisites
            run_client "$@"
            ;;
        *)
            echo -e "${RED}❌ Comando no reconocido: $1${NC}"
            echo ""
            show_help
            exit 1
            ;;
    esac
}

# Ejecutar función principal con todos los argumentos
main "$@"