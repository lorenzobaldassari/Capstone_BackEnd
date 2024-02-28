//package LorenzoBaldassari.Capstone.Entities;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//
//import java.util.UUID;
//
//@Entity
//@Table(name = "commenti")
//@Getter
//public class Commento {
//
//    @GeneratedValue
//    @Id
//    private UUID uuid;
//    private String contenuto;
//
//    @ManyToOne
//    @JoinColumn(name = "id_utente")
//    private Utente utente;
//    @ManyToOne
//    @JoinColumn(name = "id_post")
//    private Post post;
//
//
//    public void setContenuto(String contenuto) {
//        this.contenuto = contenuto;
//    }
//}
