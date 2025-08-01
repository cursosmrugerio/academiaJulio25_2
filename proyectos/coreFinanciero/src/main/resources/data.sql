-- Datos iniciales para el sistema Core Financiero
-- Migración de datos base del sistema PL/SQL
-- 
-- NOTA: Este archivo contiene fechas dinámicas usando CURRENT_DATE para las empresas del cliente HTTP/2
-- Esto permite que las demostraciones funcionen automáticamente durante los próximos 5 días
-- sin necesidad de modificar manualmente las fechas

-- Insertar parámetros del sistema
INSERT INTO PFIN_PARAMETRO (CVE_GPO_EMPRESA, CVE_EMPRESA, CVE_MEDIO, F_MEDIO) VALUES 
('GRP001', 'EMP001', 'SYSTEM', '2025-01-15'),
('GRP001', 'EMP002', 'SYSTEM', '2025-01-15'),
('GRP002', 'EMP003', 'SYSTEM', '2025-01-15'),
-- Datos para el cliente HTTP/2 (fecha actual del sistema)
('001', '001', 'SYSTEM', CURRENT_DATE),
('999', '999', 'SYSTEM', CURRENT_DATE),
('002', '003', 'SYSTEM', CURRENT_DATE);

-- Insertar días festivos para México 2025
INSERT INTO PFIN_DIA_FESTIVO (CVE_GPO_EMPRESA, CVE_EMPRESA, CVE_PAIS, F_DIA_FESTIVO) VALUES 
-- Días festivos nacionales México 2025
('GRP001', 'EMP001', 'MX', '2025-01-01'),  -- Año Nuevo
('GRP001', 'EMP001', 'MX', '2025-02-03'),  -- Día de la Bandera
('GRP001', 'EMP001', 'MX', '2025-03-17'),  -- Natalicio de Benito Juárez
('GRP001', 'EMP001', 'MX', '2025-05-01'),  -- Día del Trabajo
('GRP001', 'EMP001', 'MX', '2025-09-16'),  -- Día de la Independencia
('GRP001', 'EMP001', 'MX', '2025-11-17'),  -- Día de la Revolución Mexicana
('GRP001', 'EMP001', 'MX', '2025-12-25'),  -- Navidad

-- Repetir para otras empresas
('GRP001', 'EMP002', 'MX', '2025-01-01'),
('GRP001', 'EMP002', 'MX', '2025-02-03'),
('GRP001', 'EMP002', 'MX', '2025-03-17'),
('GRP001', 'EMP002', 'MX', '2025-05-01'),
('GRP001', 'EMP002', 'MX', '2025-09-16'),
('GRP001', 'EMP002', 'MX', '2025-11-17'),
('GRP001', 'EMP002', 'MX', '2025-12-25'),

('GRP002', 'EMP003', 'MX', '2025-01-01'),
('GRP002', 'EMP003', 'MX', '2025-02-03'),
('GRP002', 'EMP003', 'MX', '2025-03-17'),
('GRP002', 'EMP003', 'MX', '2025-05-01'),
('GRP002', 'EMP003', 'MX', '2025-09-16'),
('GRP002', 'EMP003', 'MX', '2025-11-17'),
('GRP002', 'EMP003', 'MX', '2025-12-25'),

-- Días festivos para empresas del cliente HTTP/2
('001', '001', 'MX', '2025-01-01'),
('001', '001', 'MX', '2025-02-03'),
('001', '001', 'MX', '2025-03-17'),
('001', '001', 'MX', '2025-05-01'),
('001', '001', 'MX', '2025-09-16'),
('001', '001', 'MX', '2025-11-17'),
('001', '001', 'MX', '2025-12-25'),

('999', '999', 'MX', '2025-01-01'),
('999', '999', 'MX', '2025-02-03'),
('999', '999', 'MX', '2025-03-17'),
('999', '999', 'MX', '2025-05-01'),
('999', '999', 'MX', '2025-09-16'),
('999', '999', 'MX', '2025-11-17'),
('999', '999', 'MX', '2025-12-25'),

('002', '003', 'MX', '2025-01-01'),
('002', '003', 'MX', '2025-02-03'),
('002', '003', 'MX', '2025-03-17'),
('002', '003', 'MX', '2025-05-01'),
('002', '003', 'MX', '2025-09-16'),
('002', '003', 'MX', '2025-11-17'),
('002', '003', 'MX', '2025-12-25');

-- Insertar días de liquidación base para enero 2025
-- Fechas T+0 (mismo día)
INSERT INTO PFIN_DIA_LIQUIDACION (CVE_GPO_EMPRESA, CVE_EMPRESA, CVE_LIQUIDACION, F_INFORMACION, F_LIQUIDACION) VALUES 
('GRP001', 'EMP001', 'T+0', '2025-01-15', '2025-01-15'),
('GRP001', 'EMP001', 'T+0', '2025-01-16', '2025-01-16'),
('GRP001', 'EMP001', 'T+0', '2025-01-17', '2025-01-17'),

-- Fechas T+1 (día siguiente hábil)
('GRP001', 'EMP001', 'T+1', '2025-01-15', '2025-01-16'),
('GRP001', 'EMP001', 'T+1', '2025-01-16', '2025-01-17'),
('GRP001', 'EMP001', 'T+1', '2025-01-17', '2025-01-20'),  -- Lunes siguiente

-- Fechas T+2 (dos días hábiles)
('GRP001', 'EMP001', 'T+2', '2025-01-15', '2025-01-17'),
('GRP001', 'EMP001', 'T+2', '2025-01-16', '2025-01-20'),
('GRP001', 'EMP001', 'T+2', '2025-01-17', '2025-01-21'),

-- Fechas T+3 (tres días hábiles)
('GRP001', 'EMP001', 'T+3', '2025-01-15', '2025-01-20'),
('GRP001', 'EMP001', 'T+3', '2025-01-16', '2025-01-21'),
('GRP001', 'EMP001', 'T+3', '2025-01-17', '2025-01-22'),

-- AYER (fecha anterior)
('GRP001', 'EMP001', 'AYER', '2025-01-16', '2025-01-15'),
('GRP001', 'EMP001', 'AYER', '2025-01-17', '2025-01-16'),
('GRP001', 'EMP001', 'AYER', '2025-01-20', '2025-01-17');

-- Insertar préstamos de ejemplo
-- Solo un préstamo por empresa debido a la clave primaria compuesta (CVE_GPO_EMPRESA, CVE_EMPRESA)
INSERT INTO SIM_PRESTAMO (CVE_GPO_EMPRESA, CVE_EMPRESA, ID_PRESTAMO, CVE_DIVISA, IMP_PRESTAMO) VALUES 
('GRP001', 'EMP001', 1001, 'MXN', 1000000.00),
('GRP001', 'EMP002', 2001, 'MXN', 2000000.00),
('GRP002', 'EMP003', 3001, 'MXN', 500000.00),
-- Préstamos para empresas del cliente HTTP/2
('001', '001', 10001, 'MXN', 5000000.00),
('999', '999', 99001, 'MXN', 1500000.00),
('002', '003', 20301, 'MXN', 3000000.00);

-- Insertar pre-movimientos de ejemplo
INSERT INTO PFIN_PRE_MOVIMIENTO (
    CVE_GPO_EMPRESA, CVE_EMPRESA, ID_PRE_MOVIMIENTO, F_OPERACION, F_LIQUIDACION, F_APLICACION,
    ID_CUENTA, ID_PRESTAMO, CVE_DIVISA, CVE_OPERACION, IMP_NETO, PREC_OPERACION, TIPO_CAMBIO,
    CVE_MEDIO, CVE_MERCADO, TX_NOTA, ID_GRUPO, ID_MOVIMIENTO, CVE_USUARIO, SIT_PRE_MOVIMIENTO,
    NUM_PAGO_AMORTIZACION
) VALUES 
(
    'GRP001', 'EMP001', 10001, '2025-01-15', '2025-01-16', '2025-01-16',
    100001, 1001, 'MXN', 'PAGO', 50000.00, 0, 0,
    'TRANSFERENCIA', 'PRESTAMO', 'Pago de amortización préstamo 1001', 1, 0, 'USUARIO01', 'NP',
    1
),
(
    'GRP001', 'EMP001', 10002, '2025-01-15', '2025-01-17', '2025-01-17',
    100002, 1001, 'MXN', 'INTERES', 1250.00, 0, 1.00,
    'CHEQUE', 'CREDITO', 'Pago de intereses préstamo 1001', 1, 0, 'USUARIO01', 'NP',
    NULL
),
(
    'GRP001', 'EMP002', 20001, '2025-01-16', '2025-01-17', '2025-01-17',
    200001, 2001, 'MXN', 'CARGO', 15000.00, 0, 0,
    'EFECTIVO', 'TESORERIA', 'Cargo por comisión administrativa', 2, 0, 'USUARIO02', 'NP',
    NULL
);

-- Insertar detalles de pre-movimientos
INSERT INTO PFIN_PRE_MOVIMIENTO_DET (
    CVE_GPO_EMPRESA, CVE_EMPRESA, ID_PRE_MOVIMIENTO, CVE_CONCEPTO, IMP_CONCEPTO, TX_NOTA
) VALUES 
-- Detalles para pre-movimiento 10001
('GRP001', 'EMP001', 10001, 'CAPITAL', 45000.00, 'Pago de capital'),
('GRP001', 'EMP001', 10001, 'INTERES', 5000.00, 'Pago de intereses'),

-- Detalles para pre-movimiento 10002
('GRP001', 'EMP001', 10002, 'INTERES', 1000.00, 'Intereses ordinarios'),
('GRP001', 'EMP001', 10002, 'MORATORIO', 250.00, 'Intereses moratorios'),

-- Detalles para pre-movimiento 20001
('GRP001', 'EMP002', 20001, 'COMISION', 12000.00, 'Comisión por administración'),
('GRP001', 'EMP002', 20001, 'IVA', 3000.00, 'IVA sobre comisión');

-- Insertar más fechas de liquidación para EMP002 y EMP003
INSERT INTO PFIN_DIA_LIQUIDACION (CVE_GPO_EMPRESA, CVE_EMPRESA, CVE_LIQUIDACION, F_INFORMACION, F_LIQUIDACION) VALUES 
-- Para EMP002
('GRP001', 'EMP002', 'T+0', '2025-01-15', '2025-01-15'),
('GRP001', 'EMP002', 'T+1', '2025-01-15', '2025-01-16'),
('GRP001', 'EMP002', 'T+2', '2025-01-15', '2025-01-17'),

-- Para EMP003
('GRP002', 'EMP003', 'T+0', '2025-01-15', '2025-01-15'),
('GRP002', 'EMP003', 'T+1', '2025-01-15', '2025-01-16'),
('GRP002', 'EMP003', 'T+2', '2025-01-15', '2025-01-17'),

-- Fechas de liquidación para empresas del cliente HTTP/2 (dinámicas para 5 días)
-- Para empresa 001-001 - T+0 (mismo día)
('001', '001', 'T+0', CURRENT_DATE, CURRENT_DATE),
('001', '001', 'T+0', CURRENT_DATE + 1, CURRENT_DATE + 1),
('001', '001', 'T+0', CURRENT_DATE + 2, CURRENT_DATE + 2),
('001', '001', 'T+0', CURRENT_DATE + 3, CURRENT_DATE + 3),
('001', '001', 'T+0', CURRENT_DATE + 4, CURRENT_DATE + 4),

-- T+1 (día siguiente)
('001', '001', 'T+1', CURRENT_DATE, CURRENT_DATE + 1),
('001', '001', 'T+1', CURRENT_DATE + 1, CURRENT_DATE + 2),
('001', '001', 'T+1', CURRENT_DATE + 2, CURRENT_DATE + 3),
('001', '001', 'T+1', CURRENT_DATE + 3, CURRENT_DATE + 4),
('001', '001', 'T+1', CURRENT_DATE + 4, CURRENT_DATE + 5),

-- T+2 (dos días)
('001', '001', 'T+2', CURRENT_DATE, CURRENT_DATE + 2),
('001', '001', 'T+2', CURRENT_DATE + 1, CURRENT_DATE + 3),
('001', '001', 'T+2', CURRENT_DATE + 2, CURRENT_DATE + 4),
('001', '001', 'T+2', CURRENT_DATE + 3, CURRENT_DATE + 5),
('001', '001', 'T+2', CURRENT_DATE + 4, CURRENT_DATE + 6),

-- Para empresa 999-999
('999', '999', 'T+0', CURRENT_DATE, CURRENT_DATE),
('999', '999', 'T+0', CURRENT_DATE + 1, CURRENT_DATE + 1),
('999', '999', 'T+0', CURRENT_DATE + 2, CURRENT_DATE + 2),
('999', '999', 'T+0', CURRENT_DATE + 3, CURRENT_DATE + 3),
('999', '999', 'T+0', CURRENT_DATE + 4, CURRENT_DATE + 4),
('999', '999', 'T+1', CURRENT_DATE, CURRENT_DATE + 1),
('999', '999', 'T+1', CURRENT_DATE + 1, CURRENT_DATE + 2),
('999', '999', 'T+1', CURRENT_DATE + 2, CURRENT_DATE + 3),
('999', '999', 'T+1', CURRENT_DATE + 3, CURRENT_DATE + 4),
('999', '999', 'T+1', CURRENT_DATE + 4, CURRENT_DATE + 5),
('999', '999', 'T+2', CURRENT_DATE, CURRENT_DATE + 2),
('999', '999', 'T+2', CURRENT_DATE + 1, CURRENT_DATE + 3),
('999', '999', 'T+2', CURRENT_DATE + 2, CURRENT_DATE + 4),
('999', '999', 'T+2', CURRENT_DATE + 3, CURRENT_DATE + 5),
('999', '999', 'T+2', CURRENT_DATE + 4, CURRENT_DATE + 6),

-- Para empresa 002-003
('002', '003', 'T+0', CURRENT_DATE, CURRENT_DATE),
('002', '003', 'T+0', CURRENT_DATE + 1, CURRENT_DATE + 1),
('002', '003', 'T+0', CURRENT_DATE + 2, CURRENT_DATE + 2),
('002', '003', 'T+0', CURRENT_DATE + 3, CURRENT_DATE + 3),
('002', '003', 'T+0', CURRENT_DATE + 4, CURRENT_DATE + 4),
('002', '003', 'T+1', CURRENT_DATE, CURRENT_DATE + 1),
('002', '003', 'T+1', CURRENT_DATE + 1, CURRENT_DATE + 2),
('002', '003', 'T+1', CURRENT_DATE + 2, CURRENT_DATE + 3),
('002', '003', 'T+1', CURRENT_DATE + 3, CURRENT_DATE + 4),
('002', '003', 'T+1', CURRENT_DATE + 4, CURRENT_DATE + 5),
('002', '003', 'T+2', CURRENT_DATE, CURRENT_DATE + 2),
('002', '003', 'T+2', CURRENT_DATE + 1, CURRENT_DATE + 3),
('002', '003', 'T+2', CURRENT_DATE + 2, CURRENT_DATE + 4),
('002', '003', 'T+2', CURRENT_DATE + 3, CURRENT_DATE + 5),
('002', '003', 'T+2', CURRENT_DATE + 4, CURRENT_DATE + 6);

-- Insertar catálogo de operaciones
INSERT INTO PFIN_CAT_OPERACION (CVE_GPO_EMPRESA, CVE_EMPRESA, CVE_OPERACION, TX_DESCRIPCION, CVE_AFECTA_SALDO, ST_ESTATUS, TX_OBSERVACIONES) VALUES 
-- Operaciones que incrementan saldo (créditos)
('GRP001', 'EMP001', 'DEPOSITO', 'Depósito en efectivo', 'I', 'A', 'Operación de depósito'),
('GRP001', 'EMP001', 'ABONO', 'Abono a cuenta', 'I', 'A', 'Abono general'),
('GRP001', 'EMP001', 'INTERES', 'Intereses ganados', 'I', 'A', 'Intereses a favor del cliente'),

-- Operaciones que decrementan saldo (débitos)
('GRP001', 'EMP001', 'RETIRO', 'Retiro en efectivo', 'D', 'A', 'Operación de retiro'),
('GRP001', 'EMP001', 'CARGO', 'Cargo por comisión', 'D', 'A', 'Cargo general'),
('GRP001', 'EMP001', 'PAGO', 'Pago de préstamo', 'D', 'A', 'Pago de amortización'),

-- Operaciones que no afectan saldo
('GRP001', 'EMP001', 'CONSULTA', 'Consulta de saldo', 'N', 'A', 'Solo consulta'),
('GRP001', 'EMP001', 'REPORTE', 'Generación de reporte', 'N', 'A', 'Solo reporte'),

-- Para otras empresas
('GRP001', 'EMP002', 'DEPOSITO', 'Depósito en efectivo', 'I', 'A', 'Operación de depósito'),
('GRP001', 'EMP002', 'RETIRO', 'Retiro en efectivo', 'D', 'A', 'Operación de retiro'),
('GRP001', 'EMP002', 'CARGO', 'Cargo por comisión', 'D', 'A', 'Cargo general'),

('GRP002', 'EMP003', 'DEPOSITO', 'Depósito en efectivo', 'I', 'A', 'Operación de depósito'),
('GRP002', 'EMP003', 'RETIRO', 'Retiro en efectivo', 'D', 'A', 'Operación de retiro'),

-- Catálogo de operaciones para empresas del cliente HTTP/2
-- Para empresa 001-001
('001', '001', 'DEPOSITO', 'Depósito en efectivo', 'I', 'A', 'Operación de depósito'),
('001', '001', 'RETIRO', 'Retiro en efectivo', 'D', 'A', 'Operación de retiro'),
('001', '001', 'ABONO', 'Abono a cuenta', 'I', 'A', 'Abono general'),
('001', '001', 'CARGO', 'Cargo por comisión', 'D', 'A', 'Cargo general'),
('001', '001', 'PAGO', 'Pago de préstamo', 'D', 'A', 'Pago de amortización'),
('001', '001', 'INTERES', 'Intereses ganados', 'I', 'A', 'Intereses a favor del cliente'),
('001', '001', 'CONSULTA', 'Consulta de saldo', 'N', 'A', 'Solo consulta'),

-- Para empresa 999-999
('999', '999', 'DEPOSITO', 'Depósito en efectivo', 'I', 'A', 'Operación de depósito'),
('999', '999', 'RETIRO', 'Retiro en efectivo', 'D', 'A', 'Operación de retiro'),
('999', '999', 'ABONO', 'Abono a cuenta', 'I', 'A', 'Abono general'),
('999', '999', 'CARGO', 'Cargo por comisión', 'D', 'A', 'Cargo general'),

-- Para empresa 002-003
('002', '003', 'DEPOSITO', 'Depósito en efectivo', 'I', 'A', 'Operación de depósito'),
('002', '003', 'RETIRO', 'Retiro en efectivo', 'D', 'A', 'Operación de retiro'),
('002', '003', 'ABONO', 'Abono a cuenta', 'I', 'A', 'Abono general');

-- Insertar saldos iniciales de ejemplo
INSERT INTO PFIN_SALDO (CVE_GPO_EMPRESA, CVE_EMPRESA, F_FOTO, ID_CUENTA, CVE_DIVISA, SDO_EFECTIVO) VALUES 
-- Saldos para cuentas de EMP001
('GRP001', 'EMP001', '2025-01-15', 100001, 'MXN', 50000.00),
('GRP001', 'EMP001', '2025-01-15', 100002, 'USD', 5000.00),
('GRP001', 'EMP001', '2025-01-15', 100003, 'MXN', 25000.00),

-- Saldos para cuentas de EMP002
('GRP001', 'EMP002', '2025-01-15', 200001, 'MXN', 100000.00),
('GRP001', 'EMP002', '2025-01-15', 200002, 'EUR', 3000.00),

-- Saldos para cuentas de EMP003
('GRP002', 'EMP003', '2025-01-15', 300001, 'MXN', 75000.00),

-- Saldos iniciales para empresas del cliente HTTP/2 (dinámicos para 5 días)
-- Saldos para empresa 001-001 (saldos para fecha actual y próximos 4 días)
('001', '001', CURRENT_DATE, 1001, 'MXN', 100000.00),
('001', '001', CURRENT_DATE, 1002, 'MXN', 50000.00),
('001', '001', CURRENT_DATE, 1003, 'MXN', 25000.00),
('001', '001', CURRENT_DATE + 1, 1001, 'MXN', 100000.00),
('001', '001', CURRENT_DATE + 1, 1002, 'MXN', 50000.00),
('001', '001', CURRENT_DATE + 1, 1003, 'MXN', 25000.00),
('001', '001', CURRENT_DATE + 2, 1001, 'MXN', 100000.00),
('001', '001', CURRENT_DATE + 2, 1002, 'MXN', 50000.00),
('001', '001', CURRENT_DATE + 2, 1003, 'MXN', 25000.00),
('001', '001', CURRENT_DATE + 3, 1001, 'MXN', 100000.00),
('001', '001', CURRENT_DATE + 3, 1002, 'MXN', 50000.00),
('001', '001', CURRENT_DATE + 3, 1003, 'MXN', 25000.00),
('001', '001', CURRENT_DATE + 4, 1001, 'MXN', 100000.00),
('001', '001', CURRENT_DATE + 4, 1002, 'MXN', 50000.00),
('001', '001', CURRENT_DATE + 4, 1003, 'MXN', 25000.00),

-- Saldos para empresa 999-999
('999', '999', CURRENT_DATE, 9001, 'MXN', 500000.00),
('999', '999', CURRENT_DATE, 9002, 'USD', 25000.00),
('999', '999', CURRENT_DATE, 9003, 'MXN', 75000.00),
('999', '999', CURRENT_DATE + 1, 9001, 'MXN', 500000.00),
('999', '999', CURRENT_DATE + 1, 9002, 'USD', 25000.00),
('999', '999', CURRENT_DATE + 1, 9003, 'MXN', 75000.00),
('999', '999', CURRENT_DATE + 2, 9001, 'MXN', 500000.00),
('999', '999', CURRENT_DATE + 2, 9002, 'USD', 25000.00),
('999', '999', CURRENT_DATE + 2, 9003, 'MXN', 75000.00),
('999', '999', CURRENT_DATE + 3, 9001, 'MXN', 500000.00),
('999', '999', CURRENT_DATE + 3, 9002, 'USD', 25000.00),
('999', '999', CURRENT_DATE + 3, 9003, 'MXN', 75000.00),
('999', '999', CURRENT_DATE + 4, 9001, 'MXN', 500000.00),
('999', '999', CURRENT_DATE + 4, 9002, 'USD', 25000.00),
('999', '999', CURRENT_DATE + 4, 9003, 'MXN', 75000.00),

-- Saldos para empresa 002-003
('002', '003', CURRENT_DATE, 2001, 'MXN', 300000.00),
('002', '003', CURRENT_DATE, 2002, 'USD', 15000.00),
('002', '003', CURRENT_DATE + 1, 2001, 'MXN', 300000.00),
('002', '003', CURRENT_DATE + 1, 2002, 'USD', 15000.00),
('002', '003', CURRENT_DATE + 2, 2001, 'MXN', 300000.00),
('002', '003', CURRENT_DATE + 2, 2002, 'USD', 15000.00),
('002', '003', CURRENT_DATE + 3, 2001, 'MXN', 300000.00),
('002', '003', CURRENT_DATE + 3, 2002, 'USD', 15000.00),
('002', '003', CURRENT_DATE + 4, 2001, 'MXN', 300000.00),
('002', '003', CURRENT_DATE + 4, 2002, 'USD', 15000.00);

-- Pre-movimientos para empresas del cliente HTTP/2
INSERT INTO PFIN_PRE_MOVIMIENTO (
    CVE_GPO_EMPRESA, CVE_EMPRESA, ID_PRE_MOVIMIENTO, F_OPERACION, F_LIQUIDACION, F_APLICACION,
    ID_CUENTA, ID_PRESTAMO, CVE_DIVISA, CVE_OPERACION, IMP_NETO, PREC_OPERACION, TIPO_CAMBIO,
    CVE_MEDIO, CVE_MERCADO, TX_NOTA, ID_GRUPO, ID_MOVIMIENTO, CVE_USUARIO, SIT_PRE_MOVIMIENTO,
    NUM_PAGO_AMORTIZACION
) VALUES 
-- Pre-movimientos para empresa 001-001 (dinámicos para demos)
(
    '001', '001', 50001, CURRENT_DATE, CURRENT_DATE + 1, CURRENT_DATE + 1,
    1001, 10001, 'MXN', 'DEPOSITO', 15000.00, 0, 1.00,
    'TRANSFERENCIA', 'DEPOSITOS', 'Depósito demo cliente HTTP/2', 1, 0, 'CLI_USER', 'NP',
    NULL
),
(
    '001', '001', 50002, CURRENT_DATE, CURRENT_DATE + 1, CURRENT_DATE + 1,
    1002, 10001, 'MXN', 'RETIRO', 8500.00, 0, 1.00,
    'EFECTIVO', 'RETIROS', 'Retiro demo cliente HTTP/2', 2, 0, 'CLI_USER', 'NP',
    NULL
);

-- Detalles de pre-movimientos para empresa 001-001
INSERT INTO PFIN_PRE_MOVIMIENTO_DET (
    CVE_GPO_EMPRESA, CVE_EMPRESA, ID_PRE_MOVIMIENTO, CVE_CONCEPTO, IMP_CONCEPTO, TX_NOTA
) VALUES 
-- Detalles para pre-movimiento 50001 (Depósito)
('001', '001', 50001, 'DEPOSITO', 15000.00, 'Importe del depósito'),

-- Detalles para pre-movimiento 50002 (Retiro)
('001', '001', 50002, 'RETIRO', 8000.00, 'Retiro en efectivo'),
('001', '001', 50002, 'COMISION', 500.00, 'Comisión por retiro');