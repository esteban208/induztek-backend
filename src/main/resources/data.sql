

INSERT IGNORE INTO equipos (codigo, tipo, marca, instalacion, foto_placa_uri) VALUES
    ('TR-001', 'Transformador', 'Rhona', 'S/E Norte', NULL),
    ('TR-002', 'Transformador', 'Schneider', 'Planta Sur', NULL),
    ('RL-014', 'Relé de protección', 'ABB', 'S/E Norte', NULL),
    ('CB-207', 'Cable MT', 'Nexans', 'Planta Sur', NULL),
    ('CL-003', 'Celda de media tensión', 'Siemens', 'S/E Norte', NULL);

INSERT IGNORE INTO usuarios (username, password, role) VALUES
    ('admin', '123', 'ADMIN'),
    ('user', '123', 'USER');