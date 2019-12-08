CREATE TABLE user ( 
   id bigint auto_increment NOT NULL, 
   email VARCHAR(50) NOT NULL, 
   password VARCHAR(250) NOT NULL, 
   firstName VARCHAR(100) NOT NULL, 
   lastName VARCHAR(100) NOT NULL, 
   active BOOLEAN, 
   created DATE DEFAULT CURRENT_TIMESTAMP, 
   primary key (id)
);

CREATE TABLE user_group ( 
   user_id bigint NOT NULL, 
   group VARCHAR(20) NOT NULL,
   created DATE, 
   primary key(user_id, group)
);

alter table user_group add constraint fk_user_id_user foreign key (user_id) references user(id);