package io.github.godlegendshot.psychics.ability.knightpower

import io.github.monun.psychics.Ability
import io.github.monun.psychics.AbilityConcept
import io.github.monun.psychics.AbilityType
import io.github.monun.tap.config.Config
import io.github.monun.tap.config.Name
import net.kyori.adventure.text.Component.text
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent


//
@Name("knight-power")
class AbilityConceptKnightPower : AbilityConcept() {

    @Config
    var strengthAmplifier = 5

    @Config
    var slowAmplifier = 0

    @Config
    var damageOnDuration = 0.35

    init {
        type = AbilityType.PASSIVE
        displayName = "기사의 힘"
        description = listOf(
            text("힘${strengthAmplifier}만큼 적용"),
            text("속도${slowAmplifier}만큼 적용"),
            text("받는 피해량 ${damageOnDuration * 100}%만큼 감소"),
            text("죽음 후에도 경험치 유지")
        )
    }
}
class AbilityKnightPower : Ability<AbilityConceptKnightPower>(), Listener {
    override fun onEnable() {
        val concept = concept
        val player = esper.player
        val potion1 = PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2147483647, concept.strengthAmplifier, false, false, false)
        val potion2 = PotionEffect(PotionEffectType.SLOW, 2147483647, concept.slowAmplifier, false, false, false)
        player.addPotionEffect(potion1)
        player.addPotionEffect(potion2)

        psychic.registerEvents(this)
    }

    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent) {
        val damage = event.damage * concept.damageOnDuration
        event.damage = damage
    }
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        event.keepLevel = true
        val concept = concept
        val player = esper.player
        val potion1 = PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2147483647, concept.strengthAmplifier, false, false, false)
        val potion2 = PotionEffect(PotionEffectType.SLOW, 2147483647, concept.slowAmplifier, false, false, false)
        player.addPotionEffect(potion1)
        player.addPotionEffect(potion2)
    }
}