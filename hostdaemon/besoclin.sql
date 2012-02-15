create table benutzer(name text not null unique,
                      pwd text not null,                      
                      rights text not null,                      
                      primary key (name),                      
                      check (rights = 'admin' or 'read'));                      
create table client(name text not null unique,
                    IP integer not null unique,                    
                    client_ID integer not null unique,                    
                    user text not null default 'root',                    
                    pw text not null default 'swe1234',                    
                    primary key (IP));                    
create table software(software_ID integer not null unique,
                      beschreibung text,                      
                      FTP_Pfad text not null,                      
                      primary key (software_ID));                      
create table installierte_software(ID integer not null unique,
                                   software_ID references software(software_ID)                                   
                                   on delete restrict on update restrict,
                                   client_ID references client(client_ID)
                                   on delete restrict on update restrict,
                                   software_benutzer text,
                                   passwort text,
                                   primary key (ID));   

