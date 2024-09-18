CREATE TABLE IF NOT EXISTS m_user (
	user_id VARCHAR(50) PRIMARY KEY
	, password VARCHAR(255)
	, user_name VARCHAR(50)
	, birthday DATE
	, age INT
	, gender INT
	, department_id INT
	, role VARCHAR(50)
	, deleted_flg CHAR NOT NULL DEFAULT '0'
	, created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
	, updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS m_department (
	department_id INT PRIMARY KEY
	, department_name VARCHAR(50)
	, deleted_flg CHAR NOT NULL DEFAULT '0'
	, created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
	, updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS t_salary (
	user_id VARCHAR(50)
	, salary_ym VARCHAR(50)
	, salary INT
	, deleted_flg CHAR NOT NULL DEFAULT '0'
	, created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
	, updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
	, PRIMARY KEY(user_id, salary_ym)
);

CREATE TABLE IF NOT EXISTS t_attendance (
	user_id VARCHAR(50)
	, attendance_date DATE NOT NULL
	, deleted_flg CHAR NOT NULL DEFAULT '0'
	, created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
	, updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
	, PRIMARY KEY(user_id, attendance_date)
);