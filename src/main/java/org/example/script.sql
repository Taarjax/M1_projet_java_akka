drop table if exists demande;
drop table if exists compte;
drop table if exists banquier;
drop table if exists client;

create table if not exists banquier
(
    idBanquier int auto_increment
        primary key,
    nom        char(50) null
);

create table if not exists client
(
    idClient int auto_increment
        primary key,
    nom      char(50) null
);

create table if not exists compte
(
    idCompte   int auto_increment
        primary key,
    solde      int null,
    idBanquier int not null,
    idClient   int not null,
    constraint fk_diBanquier
        foreign key (idBanquier) references banquier (idBanquier),
    constraint fk_idClient
        foreign key (idClient) references client (idClient)
);

create table if not exists demande
(
    idDemande int auto_increment
        primary key,
    type      enum ('dépot', 'retrait') default (elt((floor((rand() * 2)) + 1), _utf8mb4'retrait',
                                                     _utf8mb4'dÃÂÃÂÃÂÃÂ©pot')) null,
    montant   int                       default (floor(((rand() * (800 - 0)) + 0)))   not null,
    idClient  int                                                                     null,
    idCompte  int                                                                     not null,
    constraint fk_demande_idClient
        foreign key (idClient) references client (idClient),
    constraint fk_demande_idCompte
        foreign key (idCompte) references compte (idCompte)
);


ALTER TABLE client MODIFY COLUMN idClient INT auto_increment;
ALTER TABLE banquier MODIFY COLUMN idBanquier INT auto_increment;
ALTER TABLE demande MODIFY COLUMN idDemande INT auto_increment;
ALTER TABLE compte MODIFY COLUMN idCompte INT auto_increment;


INSERT INTO client (nom) values
                             ('Michel'),
                             ('Roger'),
                             ('Bernard');

INSERT INTO banquier (nom) values
                               ('Remy'),
                               ('Tom'),
                               ('Guillaume');

INSERT INTO compte (solde, idBanquier, idClient) values
                                                     (300,1,1),
                                                     (4000,1,2),
                                                     (500,2,1),
                                                     (2000,3,3),
                                                     (2000,3,3);



