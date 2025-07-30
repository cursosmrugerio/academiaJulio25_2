-- Datos iniciales para el sistema Core Financiero
-- Migración de datos base del sistema PL/SQL

-- Insertar parámetros del sistema
INSERT INTO PFIN_PARAMETRO (CVE_GPO_EMPRESA, CVE_EMPRESA, CVE_MEDIO, F_MEDIO) VALUES 
('GRP001', 'EMP001', 'SYSTEM', '2025-01-15'),
('GRP001', 'EMP002', 'SYSTEM', '2025-01-15'),
('GRP002', 'EMP003', 'SYSTEM', '2025-01-15');

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
('GRP002', 'EMP003', 'MX', '2025-12-25');

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
INSERT INTO SIM_PRESTAMO (CVE_GPO_EMPRESA, CVE_EMPRESA, ID_PRESTAMO, CVE_DIVISA, IMP_PRESTAMO) VALUES 
('GRP001', 'EMP001', 1001, 'MXN', 1000000.00),
('GRP001', 'EMP001', 1002, 'USD', 50000.00),
('GRP001', 'EMP001', 1003, 'MXN', 750000.00),
('GRP001', 'EMP002', 2001, 'MXN', 2000000.00),
('GRP001', 'EMP002', 2002, 'EUR', 25000.00),
('GRP002', 'EMP003', 3001, 'MXN', 500000.00);

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
    100002, 1002, 'USD', 'INTERES', 1250.00, 0, 20.50,
    'CHEQUE', 'CREDITO', 'Pago de intereses préstamo 1002', 1, 0, 'USUARIO01', 'NP',
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
('GRP002', 'EMP003', 'T+2', '2025-01-15', '2025-01-17');