DROP TABLE IF EXISTS log;
CREATE TABLE IF NOT EXISTS log (
    received_datetime DATETIME NOT NULL,
    log_level VARCHAR(255) NOT NULL,
    ip_address VARCHAR(255) NOT NULL,
    service_name VARCHAR(255) NOT NULL,
    user_name VARCHAR(255),
    message TEXT,
    PRIMARY KEY (received_datetime, log_level, ip_address, service_name)
);