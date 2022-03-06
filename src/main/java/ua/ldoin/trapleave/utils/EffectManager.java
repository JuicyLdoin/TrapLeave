package ua.ldoin.trapleave.utils;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class EffectManager {

    public void addEffectsFromStringList(Player player, List<String> effects) {

        for (String effect : effects)
            if (!player.hasPotionEffect(PotionEffectType.getByName(effect.split("-")[0])))
                player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect.split("-")[0]),
                        Integer.parseInt(effect.split(" ")[1]) * 20,
                        Integer.parseInt(effect.split("-")[1].split(" ")[0]) - 1));

    }
}