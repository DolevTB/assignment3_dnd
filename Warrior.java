package assignment3_dnd;

import java.util.List;

public class Warrior extends Hero {
    private int ability_cooldown;
    private int remaining_cooldown;
    
    public Warrior(String name, int hp_pool, int atk_pts, int def_pts, int ability_cooldown) {
        super(name, hp_pool, atk_pts, def_pts);
        this.ability_cooldown = ability_cooldown;
        remaining_cooldown = 0;
        this.ability_range = 3;
    }

    @Override
    public void GainExp(int exp) {
        int current_lvl = this.player_lvl;
        super.GainExp(exp);
        if(current_lvl < this.player_lvl) {
             remaining_cooldown = 0;
        }
        while(current_lvl < this.player_lvl) {
            current_lvl++;
            this.hp_pool += 5*current_lvl;
            this.hp_current = this.hp_pool;
            this.atk_pts += 2*current_lvl;
            this.def_pts += current_lvl;
        }
    }

    @Override
    public void Tick() {
        super.Tick();
        if (remaining_cooldown > 0) {
            remaining_cooldown--;
        }
    }

    @Override
    public boolean CastAbility(List<Enemy> validTargets, Hero player) { //Cast Avenger's Shield
        if (remaining_cooldown == 0) {
            remaining_cooldown = ability_cooldown;
            this.hp_current = Math.min(this.hp_current + (10 * this.def_pts), this.hp_pool);
            if (!validTargets.isEmpty()) {
                int randomIndex = (int)(Math.random() * validTargets.size());
                Enemy target = validTargets.get(randomIndex);
                int damage = this.hp_pool / 10;
                target.hp_current -= damage;
                if (target.hp_current <= 0) {
                    target.hp_current = 0;
                    sendMsg(target.name + " died.");
                }
            }
        } else {
            sendMsg(this.name + " tried to use Avenger's Shield but it's on cooldown (" + remaining_cooldown + " remaining).");
        }
        return true;
    }

    
}