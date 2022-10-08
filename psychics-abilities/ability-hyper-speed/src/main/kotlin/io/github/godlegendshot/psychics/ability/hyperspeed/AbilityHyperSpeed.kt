package io.github.godlegendshot.psychics.ability.hyperspeed


import io.github.monun.psychics.AbilityConcept
import io.github.monun.psychics.ActiveAbility
import io.github.monun.tap.config.Name
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

@Name("hyper-speed")
class AbilityConceptHyperSpeed : AbilityConcept() {
    init {
        displayName = "초월가속"
        description = listOf(
            text("무적의 힘으로 속도가 무척 빨라진다."),
            text("절★대★무★적").color(NamedTextColor.YELLOW)
        )
    }
}

class AbilityHyperSpeed : ActiveAbility<AbilityConceptHyperSpeed>() ,Listener {
    override fun onEnable() {
        psychic.registerEvents(this)
    }

    override fun onCast(event: PlayerEvent, action: WandAction, target: Any?) {
        val concept = concept
        psychic.consumeMana(concept.cost)
        cooldownTime = concept.cooldownTime
        durationTime = concept.durationTime

        val player = esper.player
        val ticks = (concept.durationTime / 50L).toInt()
        val potion = PotionEffect(PotionEffectType.SPEED, ticks, 128, false, false, false)
        player.addPotionEffect(potion)
    }
}