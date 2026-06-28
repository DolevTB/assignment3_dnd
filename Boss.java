package assignment3_dnd;

import java.util.List;

public class Boss extends Monster implements HeroicUnit {
    private int ability_frequency;
    private int combat_ticks;
    private Hero player; //sliha kim


    public Boss(char tile, String name, int hp_pool, int atk_pts, int def_pts, int exp_val, int vision_range, int ability_frequency) {
        super(tile, name, hp_pool, atk_pts, def_pts, exp_val, vision_range);
        this.ability_frequency = ability_frequency;
        this.combat_ticks = 0;
    }

     public void setPlayer(Hero player) {
        this.player = player;
    }

    @Override
    public boolean CastAbility(List<Enemy> validTargets, Hero player) {
        int abilityDamage = this.atk_pts;
        int defenseRoll = (int)(Math.random() * (player.def_pts + 1));
        return Attack(player, abilityDamage, defenseRoll);
    }

   @Override
    public Position Move(Position bossPos, Position playerPos) {
        if (player == null) return super.Move(bossPos, playerPos);

        double rangeToPlayer = bossPos.Range(playerPos);
        if (rangeToPlayer < GetVisionRange()) {
            if (combat_ticks == ability_frequency) {
                combat_ticks = 0;
                CastAbility(null, player);
                return bossPos;
            } else {
                combat_ticks++;
                return super.Move(bossPos, playerPos);
            }
        } else {
            combat_ticks = 0;
            return super.Move(bossPos, playerPos);
        }
    }
}