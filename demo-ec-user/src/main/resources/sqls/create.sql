
CREATE TABLE user ( 
	id bigint auto_increment primary key,
	email VARCHAR(50) NOT NULL, 
	password VARCHAR(250) NOT NULL, 
	firstName VARCHAR(100) NOT NULL, 
	lastName VARCHAR(100) NOT NULL, 
	active BOOLEAN, 
	created DATE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_group ( 
	id bigint auto_increment primary key,
	`group` VARCHAR(20) NOT NULL,
	created DATE DEFAULT CURRENT_TIMESTAMP, 
	user_id bigint NOT NULL
);

alter table user_group add constraint fk_user_id_user foreign key (user_id) references user(id);
