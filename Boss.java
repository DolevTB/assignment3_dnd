package assignment3_dnd;

public class Boss extends Monster implements HeroicUnit {
    private int ability_frequency;
    private int combat_ticks;

    public Boss(char tile, String name, int hp_pool, int atk_pts, int def_pts, int exp_val, int vision_range, int ability_frequency) {
        super(tile, name, hp_pool, atk_pts, def_pts, exp_val, vision_range);
        this.ability_frequency = ability_frequency;
        this.combat_ticks = 0;
    }

    public void CastAbility(Hero targetPlayer) {
        this.Combat(targetPlayer); 
    }

    public Position Move(Position bossPos, Position playerPos, Hero player) {
        double rangeToPlayer = bossPos.Range(playerPos);
        if (rangeToPlayer < GetVisionRange()) {
            if (combat_ticks == ability_frequency) {
                combat_ticks = 0;
                CastAbility(player);
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