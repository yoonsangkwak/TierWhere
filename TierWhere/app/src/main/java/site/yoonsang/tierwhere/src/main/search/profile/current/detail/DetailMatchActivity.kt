package site.yoonsang.tierwhere.src.main.search.profile.current.detail

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import site.yoonsang.tierwhere.R
import site.yoonsang.tierwhere.config.BaseActivity
import site.yoonsang.tierwhere.databinding.ActivityDetailMatchBinding
import site.yoonsang.tierwhere.src.main.search.profile.current.detail.blue.BlueTeamAdapter
import site.yoonsang.tierwhere.src.main.search.profile.current.detail.model.DetailMatchInfo
import site.yoonsang.tierwhere.src.main.search.profile.current.detail.model.Participant
import site.yoonsang.tierwhere.src.main.search.profile.current.detail.model.ParticipantIdentity
import site.yoonsang.tierwhere.src.main.search.profile.current.detail.red.RedTeamAdapter

class DetailMatchActivity :
    BaseActivity<ActivityDetailMatchBinding>(ActivityDetailMatchBinding::inflate), DetailMatchView {

    private val bluePlayer = mutableListOf<Participant>()
    private val blueSummoner = mutableListOf<ParticipantIdentity>()
    private val redPlayer = mutableListOf<Participant>()
    private val redSummoner = mutableListOf<ParticipantIdentity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val matchId = intent.getLongExtra("matchId", 0)

        showLoadingDialog(this)
        DetailMatchService(this).tryGetDetailMatchInfo(matchId)
    }

    override fun getDetailMatchInfoSuccess(response: DetailMatchInfo) {
        dismissLoadingDialog()
        binding.detailMatchTypeText.text = when (response.queueId) {
            420 -> "솔로 랭크"
            430 -> "일반"
            440 -> "자유 랭크"
            450 -> "무작위 총력전"
            900 -> "URF"
            1020 -> "단일 모드"
            else -> "사용자 설정 게임"
        }
        binding.detailMatchPlayTimeText.text = "${response.gameDuration / 60}:${response.gameDuration % 60}"
        for (team in response.teams) {
            if (team.teamId == 100) {
                if (team.win == "Win") {
                    binding.detailBlueTeamWinText.text = "승리"
                    binding.detailBlueTeamWinText.setTextColor(getColor(R.color.win))
                } else {
                    binding.detailBlueTeamWinText.text = "패배"
                    binding.detailBlueTeamWinText.setTextColor(getColor(R.color.lost))
                }
                binding.detailBlueBaronText.text = "바론 ${team.baronKills}"
                binding.detailBlueDragonText.text = "드래곤 ${team.dragonKills}"
                binding.detailBlueTowerText.text = "타워 ${team.towerKills}"
            } else {
                if (team.win == "Win") {
                    binding.detailRedTeamWinText.text = "승리"
                    binding.detailRedTeamWinText.setTextColor(getColor(R.color.win))
                } else {
                    binding.detailRedTeamWinText.text = "패배"
                    binding.detailRedTeamWinText.setTextColor(getColor(R.color.lost))
                }
                binding.detailRedBaronText.text = "바론 ${team.baronKills}"
                binding.detailRedDragonText.text = "드래곤 ${team.dragonKills}"
                binding.detailRedTowerText.text = "타워 ${team.towerKills}"
            }
        }
        for (player in response.participants) {
            if (player.teamId == 100) bluePlayer.add(player)
            else redPlayer.add(player)
        }
        for (summoner in response.participantIdentities) {
            for (player in bluePlayer) {
                if (summoner.participantId == player.participantId) blueSummoner.add(summoner)
            }
            for (player in redPlayer) {
                if (summoner.participantId == player.participantId) redSummoner.add(summoner)
            }
        }
        val maxDamage = response.participants.maxOf {
            it.stats.totalDamageDealtToChampions
        }
        val summonerName = intent.getStringExtra("name")!!
        binding.detailBlueTeamRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = BlueTeamAdapter(context, bluePlayer, blueSummoner, response.gameDuration, summonerName, maxDamage)
        }
        binding.detailRedTeamRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RedTeamAdapter(context, redPlayer, redSummoner, response.gameDuration, summonerName, maxDamage)
        }
    }

    override fun getDetailMatchInfoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}