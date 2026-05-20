package assignment3_dnd;

public class Warrior extends Hero {
    private int ability_cooldown;
    private int remaining_cooldown;
    
    public Warrior(String name, int hp_pool, int atk_pts, int def_pts, int ability_cooldown) {
        super(name, hp_pool, atk_pts, def_pts);
        this.ability_cooldown = ability_cooldown;
        this.remaining_cooldown = 0;
    }

    @Override
    public void GainExp(int exp) {
        int current_lvl = GetPlayerLvl();
        super.GainExp(exp);
        if(current_lvl < GetPlayerLvl()) {
             remaining_cooldown = 0;
        }
        while(current_lvl < GetPlayerLvl()) {
            current_lvl++;
            SetHpPool(GetHpPool() + 5*current_lvl);
            SetHpCurrent(GetHpPool());
            SetAtkPts(GetAtkPts() + 2*current_lvl);
            SetDefPts(GetDefPts() + current_lvl);
        }
    }

    @Override
    public void Tick() {
        super.Tick();
        if (remaining_cooldown > 0) {
            remaining_cooldown--;
        }
    }

    //TEMPORARY - not sure if necessary yet
    @Override
    public void Combat(Unit target) {}

    @Override
    public void CastAbility() { //Cast Avenger's Shield
        if (remaining_cooldown == 0) {
            remaining_cooldown = ability_cooldown;
            SetHpCurrent(GetHpCurrent() + 10*GetDefPts());
            //find all enemies in range < 3. atk one of them (chosen randomly) for 10% of warrior's hp pool.
        }
        else {
            //ability is on cooldown
        }
    }
}