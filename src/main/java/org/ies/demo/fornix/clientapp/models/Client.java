package org.ies.demo.fornix.clientapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    @Column(name = "password_hashed")
    private String passwordHashed;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String nickname;

    @Column(name = "email_verificado")
    private Boolean emailVerificado;

    @Column(name = "fecha_verificado")
    private LocalDate fechaVerificado;

    @Column(name = "intentos_login")
    private Integer intentosLogin;

    @Column(name = "ultimo_intento_login")
    private LocalDateTime ultimoIntentoLogin;

    @Column(name = "bloqueado_hasta")
    private LocalDateTime bloqueadoHasta;

    @Column(name = "path_profile_picture_s3")
    private String pathProfilePictureS3;

    private String bio;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @OneToMany(mappedBy = "dev")
    private List<Game> developedGames;

    @OneToMany(mappedBy = "client")
    private List<Purchase> purchases;

    @OneToMany(mappedBy = "client")
    private List<Wishlist> wishlists;
}