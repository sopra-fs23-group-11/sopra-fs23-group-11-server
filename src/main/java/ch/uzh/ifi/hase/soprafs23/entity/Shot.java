package ch.uzh.ifi.hase.soprafs23.entity;


import javax.persistence.*;

@Entity
@Table(name = "shots")
public class Shot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="attacker_id")
    private Player attacker;

    @ManyToOne
    @JoinColumn(name="defender_id")
    private Player defender;


    @Column (nullable = false)
    private String position;

    @Column (nullable = false)
    private boolean isHit;

    public Long getId() {
        return id;
    }

    public Player getAttacker() {
        return attacker;
    }

    public void setAttacker(Player attacker) {
        this.attacker = attacker;
    }

    public Player getDefender() {
        return defender;
    }

    public void setDefender(Player defender) {
        this.defender = defender;
    }



    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }
}
