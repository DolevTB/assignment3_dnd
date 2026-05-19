package assignment3_dnd;

public abstract class Hero extends Unit implements HeroicUnit  {
    private int exp;
    private int player_lvl;
    
    public Hero(String name, int hp_pool, int atk_pts, int def_pts) {
        super(name, hp_pool, atk_pts, def_pts);
        this.exp = 0;
        this.player_lvl = 1;
    } 

    public void GainExp(int exp) {
        this.exp += exp;
        while (this.exp >= 50*player_lvl) {
            this.exp -= 50*player_lvl;
            player_lvl++;
            SetHpPool(GetHpPool() + 10*player_lvl);
            SetHpCurrent(GetHpPool());
            SetAtkPts(GetAtkPts() + 4*player_lvl);
            SetDefPts(GetDefPts() + player_lvl);
        }
    }

    @Override
    public void Tick() {}

    @Override
    public void Combat(Unit target) {}

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