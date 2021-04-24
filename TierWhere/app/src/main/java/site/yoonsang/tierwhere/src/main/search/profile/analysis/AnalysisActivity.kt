package site.yoonsang.tierwhere.src.main.search.profile.analysis

import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONObject
import site.yoonsang.tierwhere.R
import site.yoonsang.tierwhere.config.BaseActivity
import site.yoonsang.tierwhere.databinding.ActivityAnalysisBinding
import site.yoonsang.tierwhere.src.main.search.profile.analysis.model.ChampionMasteryList
import site.yoonsang.tierwhere.src.main.search.profile.analysis.model.CurrentMatches
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.DecimalFormat

class AnalysisActivity : BaseActivity<ActivityAnalysisBinding>(ActivityAnalysisBinding::inflate),
    AnalysisView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showLoadingDialog(this)
        AnalysisService(this).apply {
            tryGetCurrentMatches(intent.getStringExtra("accountId")!!)
            tryGetChampionMasteryList(intent.getStringExtra("summonerId")!!)
        }
    }

    override fun getMatchListSuccess(response: CurrentMatches) {
        dismissLoadingDialog()
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
                setMasteryImage(binding.analysisSecondMasteryMasteryImage, response[1].championLevel)
                setMasteryImage(binding.analysisThirdMasteryMasteryImage, response[2].championLevel)
            }
            response.size == 2 -> {
                binding.analysisFirstMasteryPointText.text = dec.format(response[0].championPoints)
                binding.analysisSecondMasteryPointText.text = dec.format(response[1].championPoints)
                setChampionImage(binding.analysisFirstMasteryChampionImage, response[0].championId)
                setChampionImage(binding.analysisSecondMasteryChampionImage, response[1].championId)
                setMasteryImage(binding.analysisFirstMasteryMasteryImage, response[0].championLevel)
                setMasteryImage(binding.analysisSecondMasteryMasteryImage, response[1].championLevel)
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