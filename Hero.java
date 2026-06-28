package assignment3_dnd;

import java.util.List;

public abstract class Hero extends Unit implements HeroicUnit  {
    protected int exp;
    protected int player_lvl;
    
    public Hero(String name, int hp_pool, int atk_pts, int def_pts) {
        super(name, hp_pool, atk_pts, def_pts);
        this.exp = 0;
        this.player_lvl = 1;
    } 

    @Override
    public void Tick() {}

    @Override
    public void Combat(Unit target) {}

    public void GainExp(int exp) {
        this.exp += exp;
        while (this.exp >= 50*player_lvl) {
            this.exp -= 50*player_lvl;
            player_lvl++;
            this.hp_pool += 10*player_lvl;
            this.hp_current = this.hp_pool;
            this.atk_pts += 4*player_lvl;
            this.def_pts += player_lvl;
        }
    }

    public List<Enemy> GetEnemiesInRange(int range) {
        //returns a list of all enemies within the specified range. 
        //range is calculated using Euclidean distance formula.
        return null; //TEMPORARY
    }
    
    //Getters and Setters
    public int GetExp() { return exp; }
    public int GetPlayerLvl() { return player_lvl; }

    public void SetExp(int exp) { 
        if (exp >= 0) {
            GainExp(exp);
        }
        else {
            //throw Exception?
        }
    }
}