package site.yoonsang.tierwhere.src.main.search.profile.analysis

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONObject
import site.yoonsang.tierwhere.R
import site.yoonsang.tierwhere.config.BaseActivity
import site.yoonsang.tierwhere.databinding.ActivityAnalysisBinding
import site.yoonsang.tierwhere.src.main.search.profile.analysis.model.ChampionMasteryList
import site.yoonsang.tierwhere.src.main.search.profile.analysis.model.CurrentMatches
import site.yoonsang.tierwhere.src.main.search.profile.analysis.model.Match
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.DecimalFormat
import kotlin.math.round
import kotlin.math.roundToInt

class AnalysisActivity : BaseActivity<ActivityAnalysisBinding>(ActivityAnalysisBinding::inflate),
    AnalysisView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showLoadingDialog(this)
        AnalysisService(this).apply {
            tryGetCurrentMatches(intent.getStringExtra("accountId")!!)
            tryGetChampionMasteryList(intent.getStringExtra("summonerId")!!)
        }
        val game = intent.getIntExtra("game", 0)
        val win = intent.getIntExtra("win", 0)
        val lost = intent.getIntExtra("lost", 0)
        val winRate = (win.toFloat() / game.toFloat() * 10 * 100).roundToInt() / 10f
        setPieChart(win, lost)
        val kill = intent.getIntExtra("kill", 0)
        val death = intent.getIntExtra("death", 0)
        val assist = intent.getIntExtra("assist", 0)
        val cs = intent.getIntExtra("cs", 0)
        val playTime = intent.getIntExtra("playTime", 0)
        val averageKill = (kill.toFloat() / game.toFloat() * 10).roundToInt() / 10f
        val averageDeath = (death.toFloat() / game.toFloat() * 10).roundToInt() / 10f
        val averageAssist = (assist.toFloat() / game.toFloat() * 10).roundToInt() / 10f
        val kda = ((kill + assist).toFloat() / death.toFloat() * 100).roundToInt() / 100f
        val averageCs = (cs.toFloat() / game.toFloat() * 10).roundToInt() / 10f
        val averageCsPerMin = round((cs.toDouble() / playTime * 60) * 10) / 10
        binding.analysisGameCountText.text = "${game}전"
        binding.analysisWinCountText.text = "${win}승"
        binding.analysisLostCountText.text = "${lost}패"
        binding.analysisWinRateText.text = "(${winRate}%)"
        binding.analysisAverageKillText.text = averageKill.toString()
        binding.analysisAverageDeathText.text = averageDeath.toString()
        binding.analysisAverageAssistText.text = averageAssist.toString()
        binding.analysisAverageCsText.text = averageCs.toString()
        binding.analysisAverageCsPerMinText.text = "($averageCsPerMin)"
        setKDAColor(binding.analysisKdaText, kda)

        binding.analysisSummonerNameText.text = intent.getStringExtra("name")
        when {
            winRate >= 70 -> {
                binding.analysisCurrentStateText.text = "미쳐 날뛰고 있습니다!!"
            }
            winRate >= 60 -> {
                binding.analysisCurrentStateText.text = "최근 폼이 좋습니다!"
            }
            winRate >= 50 -> {
                binding.analysisCurrentStateText.text = "이 정도면 좋은데요?"
            }
            winRate >= 45 -> {
                binding.analysisCurrentStateText.text = "아직까진 괜찮아요."
            }
            winRate >= 40 -> {
                binding.analysisCurrentStateText.text = "슬럼프가 왔나봐요.."
            }
            else -> {
                binding.analysisCurrentStateText.text = "범인일 가능성이 농후합니다."
            }
        }
    }

    override fun getMatchListSuccess(response: CurrentMatches) {
        dismissLoadingDialog()
        countRole(response.matches)
    }

    override fun getMatchListFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun getChampionMasteryListSuccess(response: ChampionMasteryList) {
        val dec = DecimalFormat("#,###")
        when {
            response.size > 3 -> {
                binding.analysisFirstMasteryPointText.text = dec.format(response[0].championPoints)
                binding.analysisSecondMasteryPointText.text = dec.format(response[1].championPoints)
                binding.analysisThirdMasteryPointText.text = dec.format(response[2].championPoints)
                setChampionImage(binding.analysisFirstMasteryChampionImage, response[0].championId)
                setChampionImage(binding.analysisSecondMasteryChampionImage, response[1].championId)
                setChampionImage(binding.analysisThirdMasteryChampionImage, response[2].championId)
                setMasteryImage(binding.analysisFirstMasteryMasteryImage, response[0].championLevel)
                setMasteryImage(
                    binding.analysisSecondMasteryMasteryImage,
                    response[1].championLevel
                )
                setMasteryImage(binding.analysisThirdMasteryMasteryImage, response[2].championLevel)
            }
            response.size == 2 -> {
                binding.analysisFirstMasteryPointText.text = dec.format(response[0].championPoints)
                binding.analysisSecondMasteryPointText.text = dec.format(response[1].championPoints)
                setChampionImage(binding.analysisFirstMasteryChampionImage, response[0].championId)
                setChampionImage(binding.analysisSecondMasteryChampionImage, response[1].championId)
                setMasteryImage(binding.analysisFirstMasteryMasteryImage, response[0].championLevel)
                setMasteryImage(
                    binding.analysisSecondMasteryMasteryImage,
                    response[1].championLevel
                )
            }
            response.size == 1 -> {
                binding.analysisFirstMasteryPointText.text = dec.format(response[0].championPoints)
                setChampionImage(binding.analysisFirstMasteryChampionImage, response[0].championId)
                setMasteryImage(binding.analysisFirstMasteryMasteryImage, response[0].championLevel)
            }
        }
    }

    override fun getChampionMasteryListFailure(message: String) {
        showCustomToast(message)
    }

    private fun setKDAColor(textView: TextView, kda: Float) {
        textView.text = kda.toString()
        if (kda >= 3 && kda < 4) {
            textView.setTextColor(getColor(R.color.threePoint))
        } else if (kda >= 4 && kda < 5) {
            textView.setTextColor(getColor(R.color.fourPoint))
        } else if (kda >= 5) {
            textView.setTextColor(getColor(R.color.fivePoint))
        }
    }

    private fun setPieChart(win: Int, lost: Int) {
        val entryList = mutableListOf<PieEntry>()
        entryList.add(PieEntry(win.toFloat(), "승리"))
        entryList.add(PieEntry(lost.toFloat(), "패배"))
        val colorList = mutableListOf<Int>()
        colorList.add(getColor(R.color.win))
        colorList.add(getColor(R.color.lost))
        val pieDataSet = PieDataSet(entryList, "Win Rate")
        pieDataSet.apply {
            sliceSpace = 3f
            selectionShift = 0f
            colors = colorList
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return ""
                }
            }
        }
        val pieData = PieData(pieDataSet)
        binding.analysisWinRateGraph.apply {
            data = pieData
            isDrawHoleEnabled = true
            holeRadius = 30f
            transparentCircleRadius = 10f
            setDrawEntryLabels(false)
            legend.isEnabled = false
            description = null
            animateY(1000, Easing.EaseOutCubic)
            invalidate()
        }
    }

    private fun countRole(data: List<Match>) {
        var top = 0
        var jungle = 0
        var mid = 0
        var ad = 0
        var support = 0
        data.forEach {
            when (it.lane) {
                "TOP" -> top++
                "JUNGLE" -> jungle++
                "MID" -> mid++
            }
        }
        data.forEach {
            when (it.role) {
                "DUO_CARRY" -> ad++
                "DUO_SUPPORT" -> support++
            }
        }
        Glide.with(binding.analysisTopImage.context)
            .load("https://raw.communitydragon.org/latest/plugins/rcp-fe-lol-clash/global/default/assets/images/position-selector/positions/icon-position-top.png")
            .into(binding.analysisTopImage)
        binding.analysisTopRateText.text = "${top}게임"
        Glide.with(binding.analysisJungleImage.context)
            .load("https://raw.communitydragon.org/latest/plugins/rcp-fe-lol-clash/global/default/assets/images/position-selector/positions/icon-position-jungle.png")
            .into(binding.analysisJungleImage)
        binding.analysisJungleRateText.text = "${jungle}게임"
        Glide.with(binding.analysisMidImage.context)
            .load("https://raw.communitydragon.org/latest/plugins/rcp-fe-lol-clash/global/default/assets/images/position-selector/positions/icon-position-middle.png")
            .into(binding.analysisMidImage)
        binding.analysisMidRateText.text = "${mid}게임"
        Glide.with(binding.analysisAdImage.context)
            .load("https://raw.communitydragon.org/latest/plugins/rcp-fe-lol-clash/global/default/assets/images/position-selector/positions/icon-position-bottom.png")
            .into(binding.analysisAdImage)
        binding.analysisAdRateText.text = "${ad}게임"
        Glide.with(binding.analysisSupportImage.context)
            .load("https://raw.communitydragon.org/latest/plugins/rcp-fe-lol-clash/global/default/assets/images/position-selector/positions/icon-position-utility.png")
            .into(binding.analysisSupportImage)
        binding.analysisSupportRateText.text = "${support}게임"
    }

    private fun setChampionImage(imageView: CircleImageView, id: Int) {
        val assetManager = resources.assets
        val inputStream = assetManager.open("jsons/champion.json")
        val isr = InputStreamReader(inputStream)
        val reader = BufferedReader(isr)

        val buffer = StringBuffer()
        var line = reader.readLine()
        while (line != null) {
            buffer.append(line + "\n")
            line = reader.readLine()
        }
        val jsonData = buffer.toString()

        val jsonObject = JSONObject(jsonData)
        val championName = jsonObject.getString(id.toString())

        Glide.with(imageView.context)
            .load("http://ddragon.leagueoflegends.com/cdn/11.8.1/img/champion/${championName}.png")
            .placeholder(R.color.iron)
            .error(R.color.iron)
            .into(imageView)
    }

    private fun setMasteryImage(imageView: ImageView, level: Int) {
        val mastery = when (level) {
            1 -> R.drawable.mastery1
            2 -> R.drawable.mastery2
            3 -> R.drawable.mastery3
            4 -> R.drawable.mastery4
            5 -> R.drawable.mastery5
            6 -> R.drawable.mastery6
            7 -> R.drawable.mastery7
            else -> 0
        }
        Glide.with(imageView.context)
            .load(mastery)
            .into(imageView)
    }
}