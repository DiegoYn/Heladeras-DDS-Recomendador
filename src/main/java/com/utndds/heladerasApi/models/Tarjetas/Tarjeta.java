package com.utndds.heladerasApi.models.Tarjetas;

import com.utndds.heladerasApi.models.Heladera.Heladera;
import com.utndds.heladerasApi.models.Rol.Rol;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dueño")
    protected Rol dueño;

    public Tarjeta() {
    }

    public Tarjeta(Rol dueño) {
        this.dueño = dueño;
    }

    public abstract boolean puedeUsarse(Heladera heladera);

    protected abstract void usar(Heladera heladera);
}