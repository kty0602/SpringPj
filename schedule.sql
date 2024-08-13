CREATE TABLE Manager (
    managerId BIGINT AUTO_INCREMENT PRIMARY KEY,
    managerName VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    regDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Schedule (
    scheduleId BIGINT AUTO_INCREMENT PRIMARY KEY,
    contents VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    managerId BIGINT NOT NULL,
    regDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleteStatus BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (managerId) REFERENCES Manager(managerId)
);