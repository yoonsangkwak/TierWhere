package site.yoonsang.tierwhere.src.main.search.profile.current.model


import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("assists")
    val assists: Int,
    @SerializedName("champLevel")
    val champLevel: Int,
    @SerializedName("combatPlayerScore")
    val combatPlayerScore: Int,
    @SerializedName("damageDealtToObjectives")
    val damageDealtToObjectives: Int,
    @SerializedName("damageDealtToTurrets")
    val damageDealtToTurrets: Int,
    @SerializedName("damageSelfMitigated")
    val damageSelfMitigated: Int,
    @SerializedName("deaths")
    val deaths: Int,
    @SerializedName("doubleKills")
    val doubleKills: Int,
    @SerializedName("firstBloodAssist")
    val firstBloodAssist: Boolean,
    @SerializedName("firstBloodKill")
    val firstBloodKill: Boolean,
    @SerializedName("firstInhibitorAssist")
    val firstInhibitorAssist: Boolean,
    @SerializedName("firstInhibitorKill")
    val firstInhibitorKill: Boolean,
    @SerializedName("firstTowerAssist")
    val firstTowerAssist: Boolean,
    @SerializedName("firstTowerKill")
    val firstTowerKill: Boolean,
    @SerializedName("goldEarned")
    val goldEarned: Int,
    @SerializedName("goldSpent")
    val goldSpent: Int,
    @SerializedName("inhibitorKills")
    val inhibitorKills: Int,
    @SerializedName("item0")
    val item0: Int,
    @SerializedName("item1")
    val item1: Int,
    @SerializedName("item2")
    val item2: Int,
    @SerializedName("item3")
    val item3: Int,
    @SerializedName("item4")
    val item4: Int,
    @SerializedName("item5")
    val item5: Int,
    @SerializedName("item6")
    val item6: Int,
    @SerializedName("killingSprees")
    val killingSprees: Int,
    @SerializedName("kills")
    val kills: Int,
    @SerializedName("largestCriticalStrike")
    val largestCriticalStrike: Int,
    @SerializedName("largestKillingSpree")
    val largestKillingSpree: Int,
    @SerializedName("largestMultiKill")
    val largestMultiKill: Int,
    @SerializedName("longestTimeSpentLiving")
    val longestTimeSpentLiving: Int,
    @SerializedName("magicDamageDealt")
    val magicDamageDealt: Int,
    @SerializedName("magicDamageDealtToChampions")
    val magicDamageDealtToChampions: Int,
    @SerializedName("magicalDamageTaken")
    val magicalDamageTaken: Int,
    @SerializedName("neutralMinionsKilled")
    val neutralMinionsKilled: Int,
    @SerializedName("objectivePlayerScore")
    val objectivePlayerScore: Int,
    @SerializedName("participantId")
    val participantId: Int,
    @SerializedName("pentaKills")
    val pentaKills: Int,
    @SerializedName("perk0")
    val perk0: Int,
    @SerializedName("perk0Var1")
    val perk0Var1: Int,
    @SerializedName("perk0Var2")
    val perk0Var2: Int,
    @SerializedName("perk0Var3")
    val perk0Var3: Int,
    @SerializedName("perk1")
    val perk1: Int,
    @SerializedName("perk1Var1")
    val perk1Var1: Int,
    @SerializedName("perk1Var2")
    val perk1Var2: Int,
    @SerializedName("perk1Var3")
    val perk1Var3: Int,
    @SerializedName("perk2")
    val perk2: Int,
    @SerializedName("perk2Var1")
    val perk2Var1: Int,
    @SerializedName("perk2Var2")
    val perk2Var2: Int,
    @SerializedName("perk2Var3")
    val perk2Var3: Int,
    @SerializedName("perk3")
    val perk3: Int,
    @SerializedName("perk3Var1")
    val perk3Var1: Int,
    @SerializedName("perk3Var2")
    val perk3Var2: Int,
    @SerializedName("perk3Var3")
    val perk3Var3: Int,
    @SerializedName("perk4")
    val perk4: Int,
    @SerializedName("perk4Var1")
    val perk4Var1: Int,
    @SerializedName("perk4Var2")
    val perk4Var2: Int,
    @SerializedName("perk4Var3")
    val perk4Var3: Int,
    @SerializedName("perk5")
    val perk5: Int,
    @SerializedName("perk5Var1")
    val perk5Var1: Int,
    @SerializedName("perk5Var2")
    val perk5Var2: Int,
    @SerializedName("perk5Var3")
    val perk5Var3: Int,
    @SerializedName("perkPrimaryStyle")
    val perkPrimaryStyle: Int,
    @SerializedName("perkSubStyle")
    val perkSubStyle: Int,
    @SerializedName("physicalDamageDealt")
    val physicalDamageDealt: Int,
    @SerializedName("physicalDamageDealtToChampions")
    val physicalDamageDealtToChampions: Int,
    @SerializedName("physicalDamageTaken")
    val physicalDamageTaken: Int,
    @SerializedName("playerScore0")
    val playerScore0: Int,
    @SerializedName("playerScore1")
    val playerScore1: Int,
    @SerializedName("playerScore2")
    val playerScore2: Int,
    @SerializedName("playerScore3")
    val playerScore3: Int,
    @SerializedName("playerScore4")
    val playerScore4: Int,
    @SerializedName("playerScore5")
    val playerScore5: Int,
    @SerializedName("playerScore6")
    val playerScore6: Int,
    @SerializedName("playerScore7")
    val playerScore7: Int,
    @SerializedName("playerScore8")
    val playerScore8: Int,
    @SerializedName("playerScore9")
    val playerScore9: Int,
    @SerializedName("quadraKills")
    val quadraKills: Int,
    @SerializedName("sightWardsBoughtInGame")
    val sightWardsBoughtInGame: Int,
    @SerializedName("statPerk0")
    val statPerk0: Int,
    @SerializedName("statPerk1")
    val statPerk1: Int,
    @SerializedName("statPerk2")
    val statPerk2: Int,
    @SerializedName("timeCCingOthers")
    val timeCCingOthers: Int,
    @SerializedName("totalDamageDealt")
    val totalDamageDealt: Int,
    @SerializedName("totalDamageDealtToChampions")
    val totalDamageDealtToChampions: Int,
    @SerializedName("totalDamageTaken")
    val totalDamageTaken: Int,
    @SerializedName("totalHeal")
    val totalHeal: Int,
    @SerializedName("totalMinionsKilled")
    val totalMinionsKilled: Int,
    @SerializedName("totalPlayerScore")
    val totalPlayerScore: Int,
    @SerializedName("totalScoreRank")
    val totalScoreRank: Int,
    @SerializedName("totalTimeCrowdControlDealt")
    val totalTimeCrowdControlDealt: Int,
    @SerializedName("totalUnitsHealed")
    val totalUnitsHealed: Int,
    @SerializedName("tripleKills")
    val tripleKills: Int,
    @SerializedName("trueDamageDealt")
    val trueDamageDealt: Int,
    @SerializedName("trueDamageDealtToChampions")
    val trueDamageDealtToChampions: Int,
    @SerializedName("trueDamageTaken")
    val trueDamageTaken: Int,
    @SerializedName("turretKills")
    val turretKills: Int,
    @SerializedName("unrealKills")
    val unrealKills: Int,
    @SerializedName("visionScore")
    val visionScore: Int,
    @SerializedName("visionWardsBoughtInGame")
    val visionWardsBoughtInGame: Int,
    @SerializedName("win")
    val win: Boolean
)